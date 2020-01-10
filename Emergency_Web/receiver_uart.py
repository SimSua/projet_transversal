import serial, json, requests

#Ce script python lit les donnees recues sur ttyUSB0 puis les ecrit dans un fichier
#To do : Envoyer les donnees sur l'API Rest
#To do : Verifier integrite des donnees
#A lancer en sudo, a besoin de pyserial pour fonctionner (sudo pip install pyserial)
SERIALPORT = "/dev/ttyS3"
#SERIALPORT = "/dev/ttyUSB0"
#SERIALPORT = "/dev/tty.usbserial-DA00G4XZ"
BAUDRATE = 115200
ser = serial.Serial()
url="http://localhost:8081/api/fires/update-intensity/"
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

def readUARTMessage(): 
    line = str(ser.readline(),"utf-8")
    print(line)
    if "<" in line or ">" in line:
        return line

def send_to_api(j):
    jo = json.loads(j)
    for i in jo:
        url_api = url+i["x"]+"/"+i["y"]
        print(url_api)
        print(i)
        payload = "{\"intensity\":9}"
        print("p = "+str(payload))

        x = requests.request("POST",url_api, data=str(payload), headers={'Content-Type': "application/json"})
        print(x.request.body)
        print(x.text)
        #print(i)

def data_to_json(data):
    json_str = "[\n"
    data = data.replace('<','')
    data = data.replace('>','')
    for i in range(len(data)):
        #print(data[i])
        if data[i] == "(":
            data_dict = {}
            i += 1
            data_dict["x"] = data[i]
            i += 2
            data_dict["y"] = data[i]
            i += 2
            data_dict["intensite"] = data[i]
            #print(data_dict)
            json_str += json.dumps(data_dict)+",\n"
    json_str = json_str[:-2] + "\n]"
    print(json_str)
    file = open("uart.log", "a+")
    file.write(json_str)
    file.close()        
    return json_str


initUART()
i = 0
total_data = ""
while True:

    data = readUARTMessage()
    if data:
        #print(data," ", i)
        total_data += data[7:-2]
        if "(5,9," in total_data: #(5,9, signifie qu'on a recu la derniere ligne
            send_to_api(data_to_json(total_data))
            i = 0
            total_data = ""
    i += 1
        
