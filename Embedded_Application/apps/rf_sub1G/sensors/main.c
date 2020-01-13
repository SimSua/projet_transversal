/****************************************************************************
 *   apps/rf_sub1G/simple/main.c
 *
 * sub1G_module support code - USB version
 *
 * Copyright 2013-2014 Nathael Pajani <nathael.pajani@ed3l.fr>
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *************************************************************************** */

#include "core/system.h"
#include "core/systick.h"
#include "core/pio.h"
#include "lib/stdio.h"
#include "drivers/serial.h"
#include "drivers/gpio.h"
#include "drivers/ssp.h"
#include "extdrv/cc1101.h"
#include "extdrv/status_led.h"
#include "drivers/i2c.h"


#define MODULE_VERSION	0x03
#define MODULE_NAME "RF Sub1G - USB"

#define RF_868MHz  1
#define RF_915MHz  0 
#if ((RF_868MHz) + (RF_915MHz) != 1)
#error Either RF_868MHz or RF_915MHz MUST be defined.
#endif

#define DEBUG 1
#define BUFF_LEN 60
#define RF_BUFF_LEN  60

#define SELECTED_FREQ  FREQ_SEL_48MHz
#define DEVICE_ADDRESS  0xB6 /* Addresses 0x00 and 0xFF are broadcast */
#define NEIGHBOR_ADDRESS 0xB5 /* Address of the associated device */
/***************************************************************************** */
/* Pins configuration */
/* pins blocks are passed to set_pins() for pins configuration.
 * Unused pin blocks can be removed safely with the corresponding set_pins() call
 * All pins blocks may be safelly merged in a single block for single set_pins() call..
 */
const struct pio_config common_pins[] = {
	/* UART 0 */
	{ LPC_UART0_RX_PIO_0_1,  LPC_IO_DIGITAL },
	{ LPC_UART0_TX_PIO_0_2,  LPC_IO_DIGITAL },
	/* SPI */
	{ LPC_SSP0_SCLK_PIO_0_14, LPC_IO_DIGITAL },
	{ LPC_SSP0_MOSI_PIO_0_17, LPC_IO_DIGITAL },
	{ LPC_SSP0_MISO_PIO_0_16, LPC_IO_DIGITAL },
	/* I2C 0 */
	{ LPC_I2C0_SCL_PIO_0_10, (LPC_IO_DIGITAL | LPC_IO_OPEN_DRAIN_ENABLE) },
	{ LPC_I2C0_SDA_PIO_0_11, (LPC_IO_DIGITAL | LPC_IO_OPEN_DRAIN_ENABLE) },
	ARRAY_LAST_PIO,
};

const struct pio cc1101_cs_pin = LPC_GPIO_0_15;
const struct pio cc1101_miso_pin = LPC_SSP0_MISO_PIO_0_16;
const struct pio cc1101_gdo0 = LPC_GPIO_0_6;
const struct pio cc1101_gdo2 = LPC_GPIO_0_7;

const struct pio status_led_green = LPC_GPIO_0_28;
const struct pio status_led_red = LPC_GPIO_0_29;

const struct pio button = LPC_GPIO_0_12; /* ISP button */


/***************************************************************************** */
void system_init()
{
	/* Stop the watchdog */
	startup_watchdog_disable(); /* Do it right now, before it gets a chance to break in */
	system_set_default_power_state();
	clock_config(SELECTED_FREQ);
	set_pins(common_pins);
	gpio_on();
	/* System tick timer MUST be configured and running in order to use the sleeping
	 * functions */
	systick_timer_on(1); /* 1ms */
	systick_start();
}

/* Define our fault handler. This one is not mandatory, the dummy fault handler
 * will be used when it's not overridden here.
 * Note : The default one does a simple infinite loop. If the watchdog is deactivated
 * the system will hang.
 */
void fault_info(const char* name, uint32_t len)
{
	uprintf(UART0, name);
	while (1);
}

static volatile int check_rx = 0;
void rf_rx_calback(uint32_t gpio)
{
	check_rx = 1;
}

static uint8_t rf_specific_settings[] = {
	CC1101_REGS(gdo_config[2]), 0x07, /* GDO_0 - Assert on CRC OK | Disable temp sensor */
	CC1101_REGS(gdo_config[0]), 0x2E, /* GDO_2 - FIXME : do something usefull with it for tests */
	CC1101_REGS(pkt_ctrl[0]), 0x0F, /* Accept all sync, CRC err auto flush, Append, Addr check and Bcast */
#if (RF_915MHz == 1)
	/* FIXME : Add here a define protected list of settings for 915MHz configuration */
#endif
};

/* RF config */
void rf_config(void)
{
	config_gpio(&cc1101_gdo0, LPC_IO_MODE_PULL_UP, GPIO_DIR_IN, 0);
	cc1101_init(0, &cc1101_cs_pin, &cc1101_miso_pin); /* ssp_num, cs_pin, miso_pin */
	/* Set default config */
	cc1101_config();
	/* And change application specific settings */
	cc1101_update_config(rf_specific_settings, sizeof(rf_specific_settings));
	set_gpio_callback(rf_rx_calback, &cc1101_gdo0, EDGE_RISING);
    cc1101_set_address(DEVICE_ADDRESS);
#ifdef DEBUG
	uprintf(UART0, "CC1101 RF link init done.\n\r");
#endif
}


void handle_rf_rx_data(void)
{
	uint8_t data[RF_BUFF_LEN];
	int8_t ret = 0;
	uint8_t status = 0;

	/* Check for received packet (and get it if any) */
	ret = cc1101_receive_packet(data, RF_BUFF_LEN, &status);
	/* Go back to RX mode */
	cc1101_enter_rx_mode();
	uint8_t buffer_data[BUFF_LEN-2];
	memcpy(&buffer_data, &data[2], sizeof(buffer_data));
	
//#ifdef DEBUG
	/* JSON PRINT*/
	//uprintf(UART0, "%s", buffer_data);
    /*uprintf(UART0, "RF: message: %c.\n\r", data[2]);*/
//#endif

}

static volatile uint32_t cc_tx = 0;
static volatile uint8_t cc_tx_buff[1024];
static volatile uint16_t cc_ptr = 0;
static volatile uint16_t size = 0;
void handle_uart_cmd(uint8_t c)
{
	if( c != '@'){
		cc_tx_buff[cc_ptr++] = c;
	}else{
		//cc_tx_buff[cc_ptr++] = c;
		size = cc_ptr;
		cc_ptr = 0;
		cc_tx = 1;
	}
	// if (cc_ptr < RF_BUFF_LEN) {
	// 	cc_tx_buff[size+cc_ptr++] = c;
	// } else {
    //     size = size + (uint16_t)cc_ptr;
	// 	cc_ptr = 0;
	// }
	// if (c == '@') {
	// 	cc_tx = 1;
	// 	uprintf(UART0, "@ received : cc_tx = 1\n");
	// }
	//uprintf(UART0, "Received command : %c, buffer size: %d.\n\r",c,size);
}

void send_on_rf(void)
{
	int i = 0;
    int tx_len = (int)size;
	uprintf(UART0, "RF: cc_ptr: %d\n\r", (int)size);
	uprintf(UART0, "RF: tx_len: %x\n\r", tx_len);
	size = 0;
	while(i < tx_len){	
		uint8_t cc_tx_data[RF_BUFF_LEN+3];
		uint8_t buffer[RF_BUFF_LEN];
		cc_tx_data[0]=RF_BUFF_LEN+2;
		cc_tx_data[1]=NEIGHBOR_ADDRESS;	
		cc_tx_data[2]=DEVICE_ADDRESS;
		
		memcpy(&cc_tx_data[3], &buffer, RF_BUFF_LEN);
		uprintf(UART0, "buff_tx : %s\n\r", buffer);
		memcpy(&cc_tx_data[3], &cc_tx_buff[i], RF_BUFF_LEN);
		//memcpy(&cc_tx_data[3], &cc_tx_buff, RF_BUFF_LEN);
		
		uprintf(UART0, "RF: cc_tx_data[0]: %x\n\r", cc_tx_data[0]);
		uprintf(UART0, "RF: voisin: %x\n\r", cc_tx_data[1]);
		uprintf(UART0, "RF: me: %x\n\r", cc_tx_data[2]);
		uprintf(UART0, "RF: msg: "); 
		for(uint8_t o = 0; o < RF_BUFF_LEN; o++){
			uprintf(UART0,"%c", cc_tx_data[3+o]);
		}
		uprintf(UART0, "\n\r"); 
		if (cc1101_tx_fifo_state() != 0) {
			cc1101_flush_tx_fifo();
		}
		int ret = cc1101_send_packet(cc_tx_data, RF_BUFF_LEN+3);
		i+=RF_BUFF_LEN;
	}
	//memset(&cc_tx_buff, NULL, i);
}


int main(void)
{
	system_init();
	uart_on(UART0, 115200, handle_uart_cmd);
	//i2c_on(I2C0, I2C_CLK_100KHz, I2C_MASTER);
	ssp_master_on(0, LPC_SSP_FRAME_SPI, 8, 4*1000*1000); /* bus_num, frame_type, data_width, rate */
	status_led_config(&status_led_green, &status_led_red);
	
	
	/* Radio */
	rf_config();
	int debug = 0;

	uprintf(UART0, "Sensors started <Send data from simulation Data Collect>\n\r");

	while (1) {
		uint8_t status = 0;
	
		
		/* Tell we are alive :) */
		chenillard(250);
		if(debug==1) uprintf(UART0, "250\n\r");
        //cc_tx = 1;
        if (cc_tx == 1) {

        #ifdef DEBUG
	        //uprintf(UART0, "Transmission ready to send.\n\r");
        #endif
			send_on_rf();
			cc_tx = 0;
			uprintf(UART0, "data send \n");
			//debug = 1;
		}
		/* Do not leave radio in an unknown or unwated state */
		do {
			status = (cc1101_read_status() & CC1101_STATE_MASK);
			if(debug==1) uprintf(UART0, "read  status\n\r");
		} while (status == CC1101_STATE_TX);

		if (status != CC1101_STATE_RX) {
			static uint8_t loop = 0;
			loop++;
			if (loop > 10) {
				if (cc1101_rx_fifo_state() != 0) {
					cc1101_flush_rx_fifo();
					if(debug==1) uprintf(UART0, "flush");
				}
				cc1101_enter_rx_mode();
				if(debug==1) uprintf(UART0, "rx mode");
				loop = 0;
			}
		}
		if (check_rx == 1) {
			check_rx = 0;
			if(debug==1) uprintf(UART0, "check rx");
			handle_rf_rx_data();
		}	
	}
	return 0;
}




