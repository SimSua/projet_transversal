
import serial, requests, time, json, sys
from cryptography.fernet import Fernet

# 3 args needed 
#arg 0 = program name
#arg 1 = url
#arg 2 = tty

if len(sys.argv) != 3:
    sys.exit()
else:
    url="http://"+str(sys.argv[1])+"/api/sim/fires/position/get"
    SERIALPORT="/dev/"+str(sys.argv[2])

print("Sender ready to send data")
# send serial message
# Don't forget to establish the right serial port ******** ATTENTION
#SERIALPORT = "/dev/ttyS3"
#SERIALPORT = "/dev/ttyUSB0"
#SERIALPORT = "/dev/tty.usbserial-DA00G4XZ"
BAUDRATE = 115200
ser = serial.Serial()

def load_key():
    """
    Loads the key from the current directory named `key.key`
    """
    return open("key.key", "rb").read()

#Caeser Cypher	
def encrypt(s,k):
    encstr=""
    for i in s:
        encstr=encstr+chr(ord(i)+k+30)
    return encstr

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

def sendUARTMessage(msg, f):
    if f:
        m = encrypt(msg, int(f))
    else:
        m = msg
    ser.write(m.encode())
    print("Message <" + str(m) + "> sent to micro-controller." )
   

initUART()
while True:
    #r = requests.get(url)
    f = load_key()   
    print(f)
    response = requests.get(url).text
    #print(response)
    jo = json.loads(response)
    #print(jo)
    for i in jo:
        #print("(%d,%d,%d)" %(int(i[u'line']), int(i[u'column']), int(i[u'intensity'])))
        sendUARTMessage("(%d,%d,%d)" %(int(i[u'line']), int(i[u'column']), int(i[u'intensity'])), f)
    sendUARTMessage("@", False)
    time.sleep(5)


