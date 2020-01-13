import serial, json, requests, sys
import paho.mqtt.client as mqtt

# 4 args needed 
#arg 0 = program name
#arg 1 = url
#arg 2 = tty
#arg 3 = broker mqtt

if len(sys.argv) != 4:
    print("Missing args")
    sys.exit()
else:
    url="http://"+str(sys.argv[1])+"/api/fires/update-intensity/"
    SERIALPORT="/dev/"+str(sys.argv[2])
    mqtt_host=str(sys.argv[3])

print("Receiver ready to receive data")
#Ce script python lit les donnees recues sur ttyUSB0 puis les ecrit dans un fichier
#To do : Envoyer les donnees sur l'API Rest
#To do : Verifier integrite des donnees
#A lancer en sudo, a besoin de pyserial pour fonctionner (sudo pip install pyserial)
#SERIALPORT = "/dev/ttyS3"
#SERIALPORT = "/dev/ttyUSB0"
#SERIALPORT = "/dev/tty.usbserial-DA00G4XZ"
BAUDRATE = 115200
ser = serial.Serial()
valx = []
for i in range(6):
	valy = []
	for j in range(10):
		valy.append(0)
	valx.append(valy)

#url="http://localhost:8081/api/fires/update-intensity/"

def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))

    # Subscribing in on_connect() means that if we lose the connection and
    # reconnect then subscriptions will be renewed.
    client.subscribe("$SYS/#")

def load_key():
    """
    Loads the key from the current directory named `key.key`
    """
    return open("key.key", "rb").read()

#Caeser Cypher	
def Encryption(s,k):
    encstr=""
    for i in s:
        encstr=encstr+chr(ord(i)+k+30)
    return encstr

def Decryption(p,k):
    #p=Encryption(s,k)
    #print(p)
    decstr=""
    try:
        for i in p:
            decstr=decstr+chr(ord(i)-k-30)
    except:
        print("Erreur dans le decryptage de "+i)

    return decstr

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
    line = str(ser.readline())
    print(line)
    if "<" in line or ">" in line:
        return line

def send_to_api(j, client):
    try:
        jo = json.loads(j)
        for i in jo:
		#print(i["x"]+"	"+i["y"])
		#if not valx[int(i["x"])]:
			#last_value[int(i["x"])][int(i["y"])] = i["intensite"]
           #if str(i["x"]).isnumeric() and str(i["y"]).isnumeric() and str(i["intensite"]).isnumeric():
		if int(i["intensite"]) != valx[int(i["x"])][int(i["y"])]:
			url_api = url+i["x"]+"/"+i["y"]
			print(url_api)
			print(i)
			payload = "{\"intensity\": "+str(i["intensite"])+"}"
			print("p = "+json.dumps(payload))
		        x = requests.request("POST",url_api, data=payload, headers={'Content-Type': "application/json"})
			valx[int(i["x"])][int(i["y"])] = int(i["intensite"])
			print(x.text)
		#client.publish("sensors", payload=json.dumps(i), qos=0, retain=False)
                	print(x.request.body)
               
                #print(i)
    except TypeError as e:
	print(e)
        print("Error send to api")

def decrypt_total_data(d, k):
    if k!=False:
      dec=""
      for i in range(0, len(total_data), 7):
          dec += Decryption(d[i:i+7], k)
      return dec
    return d

def data_to_json(data):
    try:
        json_str = "[\n"
        print(data)
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
    except:
        print("Error data to json")
        return ""


initUART()
#client = mqtt.Client()
#client.on_connect = on_connect
#client.connect(mqtt_host, 1883, 60)
total_data = ""
k = int(load_key())
i = 0

while True:
    data = readUARTMessage()
    if len(data)!=0:
        data = data.replace('<','')
        data = data.replace('>','')
        data = data.replace('msg : ','')
        data = data.replace('\r','')
        data = data.replace('\n','')
        print(data + " "+ str(i))
        total_data += data
        if i == 6: #(5,9, signifie qu'on a recu la derniere ligne
            send_to_api(data_to_json(decrypt_total_data(total_data, False)), "oui")
            total_data = ""
            i=0
        else:
            i += 1

        
