#include "Arduino.h"

#include <ESP8266WiFi.h>
#include <aREST.h>
#include <aREST_UI.h>         //https://github.com/esp8266/Arduino
//#include <ESP8266mDNS.h>
#include <Servo.h>

//needed for library
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include "WiFiManager.h"          //https://github.com/tzapu/WiFiManager
aREST_UI rest = aREST_UI();
const int bulbPin = 14;

Servo myservo;
bool first = false;
int pos;
int bulbOn(String command) {

digitalWrite(bulbPin, LOW);
  return 1;
}

int bulbOff(String command) {

digitalWrite(bulbPin, HIGH);

  return 1;
}



void configModeCallback (WiFiManager *myWiFiManager) {
  Serial.println("Entered config mode");
  Serial.println(WiFi.softAPIP());
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
    ESP.reset();
    delay(1000);
  }

  Serial.println("connected!");


  rest.button(5);
  rest.function("lock",lock);
  rest.function("unlock",unlock);

  // Give name and ID to device
  rest.set_id("1");
  // rest.set_name("esp8266");

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
