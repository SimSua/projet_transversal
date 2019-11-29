# projet_transversal
Projet Transversal 4 IRC

# Sensors
   Ce microcontroleur est connecté en USB (liaison série) au Simulator Web Server qui lui envoie par USB les données au format  (colonne, ligne, intensité), les données sont celles provenant de la base de données Simulator ou généré par le module de simulation fournit. Envoie ces données via liaison RF (RF1) vers le DataCollect, en utilisant un protocole sécurisé. Le microcontrôleur est programmé en C. Les données envoyés sont une matrice de coordonnées avec des intensités allant de 0 à 9.
# Data Collect
  Réceptionne les données du Sensors via liaison RF, via protocole sécurisé, les informations des intensités et emplacement des feux. Ce microcontrôleur est connecté en USB (liaison série) au Emergency's Web Server, il lui envoie les données qu'il reçoit après les avoir transformés au format JSON. 

# Emergency's web server
   Le rôle de ce serveur est de lire les données provenant du Data Collect via la liaison UART, au format JSON, il a ensuite une double fonction :
   - Faire un appel à un API REST DAO (JSON) pour enregistrer dans la base de données réelle les valeurs et emplacements des intensités des feux reçus.
   - Envoyer par messages MQTT les valeurs des instensités des feux à un Dashboard dans le cloud
   
# Dashboard
# Emergency View

# Emergency manager
# Simulator
# Simulator web server
# Simulation View
# Database Api
