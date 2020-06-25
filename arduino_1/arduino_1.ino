#include <Servo.h>

#define SERVO_NUM 6 // number of servos

Servo rear_L;
Servo rear_R;
Servo forward_L;
Servo forward_R;
Servo turn_L;
Servo turn_R;

int b0 = 0;
int b1 = 0;
int serialInt = 0;

void setup() {
  Serial.begin(115200); // opens serial port, sets data rate bps
  
  rear_L.attach(12);
  rear_R.attach(11);
  forward_L.attach(10);
  forward_R.attach(9);
  turn_L.attach(8);
  turn_R.attach(7);
  
  rear_L.writeMicroseconds(1500);
  rear_R.writeMicroseconds(1500);
  forward_L.writeMicroseconds(1500);
  forward_R.writeMicroseconds(1500);
  turn_L.writeMicroseconds(1500);
  turn_R.writeMicroseconds(1500); 
}



void loop() {

  while (Serial.available() >= 6) {

    b0 = Serial.read();
    if (b0 < 97 || b0 > 122) continue; // skip non-letter chars
    b1 = Serial.read();
    serialInt = Serial.parseInt();

    // Serial.print("Received: ");
    // Serial.print((char)b0);
    // Serial.print((char)b1);
    // Serial.println(serialInt);

    if (b0=='b' && b1=='l') { rear_L.writeMicroseconds( serialInt ); }
    if (b0=='b' && b1=='r') { rear_R.writeMicroseconds( serialInt ); }
    if (b0=='f' && b1=='l') { forward_L.writeMicroseconds( serialInt ); }
    if (b0=='f' && b1=='r') { forward_R.writeMicroseconds( serialInt ); }
    if (b0=='t' && b1=='l') { turn_L.writeMicroseconds( serialInt ); }
    if (b0=='t' && b1=='r') { turn_R.writeMicroseconds( serialInt ); }

    Serial.print("bl:");
    Serial.print(rear_L.readMicroseconds());
    Serial.print(", br:");
    Serial.print(rear_R.readMicroseconds());
    Serial.print(", fl:");
    Serial.print(forward_L.readMicroseconds());
    Serial.print(", fr:");
    Serial.print(forward_R.readMicroseconds());
    Serial.print(", tl:");
    Serial.print(turn_L.readMicroseconds());
    Serial.print(", tr:");
    Serial.print(turn_R.readMicroseconds());
    Serial.println();
  }


}
