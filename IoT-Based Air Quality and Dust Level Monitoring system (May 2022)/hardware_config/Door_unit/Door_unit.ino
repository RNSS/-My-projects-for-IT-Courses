// Libraries:
#include <ESP8266WiFi.h>
// Variables:
// - wifi
const char* ssid = "SSS2";   
const char* password = "Sami1111";   
WiFiClient  client;

// - thingspeak
String apiKey = "VPUX1TZ7C0CBWVL0";
const char* server = "api.thingspeak.com";

// - Timer
unsigned long lastTime = 0;
unsigned long timerDelay = 1000;

// - Pins
byte door_pin = 4;

// - Other
int num = 0;

void setup() {
  Serial.begin(9600);  //Initialize serial
  // SET UP WiFi
      wifi();
}

void loop() {
if ((millis() - lastTime) > timerDelay) {
    lastTime = millis();
   // Take door sensor data
   boolean door_status = door_sensor();
   // Send data to ThingSpeak
    upload(door_status);
  }
}

// functions:

void wifi(void) {
  
    // WiFi
  delay(10);
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
  
    // Printing the ESP IP address
  Serial.println(WiFi.localIP()); 
 }
 
void upload(boolean door_status){
    // Send to Thingspeak
  if (client.connect(server,80))  // "184.106.153.149" or api.thingspeak.com
    {
        String postStr = apiKey;
        postStr +="&field1=";
        postStr += String(door_status);
        postStr += "\r\n\r\n";
        client.print("POST /update HTTP/1.1\n");
        client.print("Host: api.thingspeak.com\n");
        client.print("Connection: close\n");
        client.print("X-THINGSPEAKAPIKEY: "+apiKey+"\n");
        client.print("Content-Type: application/x-www-form-urlencoded\n");
        client.print("Content-Length: ");
        client.print(postStr.length());
        client.print("\n\n");
        client.print(postStr); 
    }   
    client.stop();  
  }  

boolean door_sensor(void)
{
  // Get door status
  boolean door_status = digitalRead(door_pin);
    Serial.print("door status ");Serial.print(num);
    if ( door_status == true){Serial.println(": Closed");num = num +1;}
    else if (door_status == false){Serial.println(": Opened");num = num +1;}
    //else {Serial.println("something is wrong");}
    return  door_status;
}
