# projet_transversal
Projet Transversal 4 IRC

# Sensors
   Ce microcontroleur est connecté en USB (liaison série) au Simulator Web Server qui lui envoie par USB les données au format  (colonne, ligne, intensité), les données sont celles provenant de la base de données Simulator ou généré par le module de simulation fournit. Envoie ces données via liaison RF (RF1) vers le DataCollect, en utilisant un protocole sécurisé. Le microcontrôleur est programmé en C. Les données envoyés sont une matrice de coordonnées avec des intensités allant de 0 à 9.
   
# Data Collect
  Réceptionne les données du Sensors via liaison RF, via protocole sécurisé, les informations des intensités et emplacement des feux. Ce microcontrôleur est connecté en USB (liaison série) au Emergency's Web Server, il lui envoie les données qu'il reçoit après les avoir transformés au format JSON. 

# Emergency's web server
   Le rôle de ce serveur, en python avec flask, est de lire les données provenant du Data Collect via la liaison UART, au format JSON, il a ensuite une triple fonction :
   - Faire un appel à un API REST DAO (JSON) pour enregistrer dans la base de données réelle les valeurs et emplacements des intensités des feux reçus.
   - Envoyer par messages MQTT les valeurs des instensités des feux à un Dashboard dans le cloud
   - Emergency View
   
# Dashboard
   Le Dashboard dans le cloud reçoit par messages MQTT les données d'intensités des feux, il les stockes dans sa base de données InfluxDB puis met à disposition dans un service web l'affichage des données grâce à Grafana.
   
# Emergency View
   Cette partie du Emergency's web server affiche sur un service web les données sur un Leaflet les informations concernants les feux et les camions sur une map (OpenStreetMap)
   
# Emergency manager
   Cette application Java gère l'envoie des camions sur les différents feux captés par le Data Collect et remontés par l'Emergency's web server dans la base de données. L'application récupère aussi les positions des camions lorsque la simulation les déplacent. Les informations concernant la prise en charge des feux sont écrit sur la base de données grâce à l'API Rest.
   
# Simulator
   Cette application Java gère le changement d'intensité des feux, leurs positions et celle des camions, notamment leurs déplacements. Lorsqu'un camion est proche d'un feu, le feu concerné réduit d'intensité. Les informations d'intensités de feu, de positions des feux et des camions sont envoyées dans la base de données via la Database API
   
   
# Simulator web server
   Ce serveur, en python avec Flask, il récupère les données de position et d'intensité des feux. Ainsi que de l'affichage de la Simulation View.

# Simulation View
   Cette partie du Simulator's web server affiche sur Leaflet les informations concernant la position des feux et leur intensité sur une map (OpenStreetMap) 
   
# Database API
   Cette API a pour rôle de d'enregistrer et d'envoyer les données dans la base de données (PostGreSQL), le Simulator's Web server, Le Simulator, L'Emergency's web server et l'Emergency manager s'en servent pour leurs requêtes. La base de données est séparée en deux parties : 
   - La partie Simulation (données provenant du simulateur)
   - La partie réelle (doonées récupérer depuis l'IOT et données d'envoie des camions)
   
# Docker
Pour lancer l'Emergency Web Server dans un container Docker il faut :
- Aller dans le répertoire /Emergency_Web
- Faire la commande "sudo docker build -t docker-flask ." pour créer votre image avec le code du répertoire actuel
Pour l'instant pour lancer le serveur web :
- "sudo run -p 5000:5000 python-flask python run.py "0.0.0.0""
Pour lancer la partie IOT :
- "sudo docker run -p 5000:5000 -v /dev/ttyUSB0:/dev/ttyUSB0 -v :/app --privileged python-flask python receiver_uart.py"
