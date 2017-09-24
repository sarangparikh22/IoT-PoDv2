#include "Arduino.h"

#include <ESP8266WiFi.h>
#include <aREST.h>
#include "Servo.h"
#include <aREST_UI.h>         //https://github.com/esp8266/Arduino

//needed for library
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include "WiFiManager.h"          //https://github.com/tzapu/WiFiManager
aREST_UI rest = aREST_UI();
int pos;
const int bulbPin = 14;
Servo myservo;
bool isLocked=false;

bool first = false;
int lock(String command) {

//  Serial.println(command);
//
//  // Get state from command
//  int state = command.toInt();
if(!isLocked)
{
for(pos = 0; pos <= 90; pos += 1)
{
  myservo.write(pos);
  delay(30);
}
isLocked=true;
}
  return 1;
}

int unlock(String command) {

//  Serial.println(command);
//
//  // Get state from command
//  int state = command.toInt();
if(isLocked)
{
for(pos = 90; pos>=0; pos-=1)     // goes from 180 degrees to 0 degrees
{
myservo.write(pos);              // tell servo to go to position in variable 'pos'
delay(30);                       // waits 15ms for the servo to reach the position
}
isLocked=false;
}
  return 1;
}

void configModeCallback (WiFiManager *myWiFiManager) {
  Serial.println("Entered config mode");
  Serial.println(WiFi.softAPIP());
  //if you used auto generated SSID, print it
  Serial.println(myWiFiManager->getConfigPortalSSID());
}

WiFiServer server(80);

void setup() {
  Serial.begin(115200);

   myservo.attach(12);

  WiFiManager wifiManager;
  //reset settings - for testing
  //wifiManager.resetSettings();

  wifiManager.setAPCallback(configModeCallback);
  if(!wifiManager.autoConnect("PodSETUP")) {
    Serial.println("failed to connect and hit timeout");
    //reset and try again, or maybe put it to deep sleep
    ESP.reset();
    delay(1000);
  }
  Serial.println("connected!");
  rest.button(5);
  rest.function("lock",lock);
  rest.function("unlock",unlock);

  rest.set_id("1");
  WiFi.hostname("wifipod012");
  server.begin();
  Serial.println("Server started");
  Serial.println(WiFi.localIP());

}

void loop() {

  WiFiClient client = server.available();
  if (!client) {
    return;
  }
  while(!client.available()){
    delay(1);
  }
  rest.handle(client);

}
