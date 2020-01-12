
import serial, requests, time, json
url="http://localhost:8081/api/fires/position/get"


# send serial message
# Don't forget to establish the right serial port ******** ATTENTION
SERIALPORT = "/dev/ttyS3"
#SERIALPORT = "/dev/ttyUSB0"
#SERIALPORT = "/dev/tty.usbserial-DA00G4XZ"
BAUDRATE = 115200
ser = serial.Serial()

def initUART():
    # ser = serial.Serial(SERIALPORT, BAUDRATE)
    ser.port=SERIALPORT
    ser.baudrate=BAUDRATE
    ser.bytesize = serial.EIGHTBITS #number of bits per bytes
    ser.parity = serial.PARITY_NONE #set parity check: no parity
    ser.stopbits = serial.STOPBITS_ONE #number of stop bits
    ser.timeout = None          #block read

    # ser.timeout = 0             #non-block read
    # ser.timeout = 2              #timeout block read
    ser.xonxoff = False     #disable software flow control
    ser.rtscts = False     #disable hardware (RTS/CTS) flow control
    ser.dsrdtr = False       #disable hardware (DSR/DTR) flow control
    #ser.writeTimeout = 0     #timeout for write
    print ("Starting Up Serial Monitor")
    try:
        ser.open()
    except serial.SerialException:
        print("Serial {} port not available".format(SERIALPORT))
        exit()

def sendUARTMessage(msg):
    ser.write(msg.encode())
    print("Message <" + msg + "> sent to micro-controller." )

initUART()
while True:
    #r = requests.get(url)
    response = requests.get(url).text
    #print(response)
    jo = json.loads(response)
    print(jo)
    for i in jo:
        print(i[u'intensity'])
        sendUARTMessage("(%d,%d,%d)" %(int(i[u'line']), int(i[u'column']), int(i[u'intensity'])))
    sendUARTMessage("@")
    time.sleep(5)


