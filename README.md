# VS1718


H-da Hochschule Darmstadt
Distributed Systems Assignment 
Winter semester 2017/2018



Smart home - Refrigerator Simulation

[1 Application description](#description)<br>
--[1.1 sensor application](#sensor)<br>
--[1.2 refrigerator application](#refrigerator)<br>
[2 How to run it](#how_to_run)<br>
[3 tests](#tests)<br>
--[3.1 general test](#generalTest)<br>



<strong><a name="description"></a>1 Application description</strong>

<strong><a name="sensor"></a>1.1 Sensor application</strong>

- The sensor application represents the actual fill level of an element stored in the refrigerator. If the fill level changes, the sensor sends a UDP packet to the refrigerator application using port 3141. the udp packet information consists of the name of the product, the the amount and the timestamp when the change to the fill level took place. The sensor application requires following parameters from command line:
  - the name of the stored element (e.g. "Milk")
  - the initial amount of that element when the application is started
  - the max amount of that element possible (the actual amount cannot surpass this number at any time)
  - change interval in seconds: the sensor changes its fill level every x seconds to simulate food consumption over time.
  - change amount: how much the fill level will change with every change event ( has to be negative if food is consumed )
  - the IP address of the refrigerator (e.g. "192.168.15.2")


<strong><a name="refrigerator"></a>1.2 Refrigerator application</strong>

- The refrigerator application relies on the sensor information to keep track of the fill levels of all its elements. It cannot access the values of the sensor directly, but stores the information received by the the sensors. For each element a fill level history is kept. A user can access refrigerator content information over a webbrowser using the ip address of the refrigerator as url and specifying port 3142. the application takes 1 parameters over command line:
  - the number of history entries printed for each element when accessed over the webbrowser.


<strong><a name="how_to_run"></a>2 How to run it:</strong>

in repository base folder run "build.sh" script (building requires Maven).
change execution parameters (such as refrigerator host system ip address) in refrigerator.sh and sensor.sh files.
start program by executing refrigerator.sh and/or sensor.sh script.

Access refrigerator interface via web browser:
as url type in the ip address of the refrigerator followed by :3142 (example 192.168.5.77:3142)


<strong><a name="tests"></a>3 Tests</strong>

<strong><a name="generalTest"></a>3.1 General test</strong>

What is tested:<br>
Sensors and refrigerator are executed on different machines. It is then examined whether the html file thats received from the refrigerator shows the correct entries. 

How it is tested:<br>
Required: Two machines 1 and 2 that are in the same network. <br>
Parameters in sensors.sh and refrigerator.sh are set as described below. <br>
refrigerator.sh is started on machine 1. <br>
sensors.sh is started on machine 2. <br>
60 seconds after starting the sensors, the refrigerator content is accessed via webbrowser. <br>

refrigerator.sh: <br>
Entries shown per element= 10. <br>

sensors.sh: <br>
refrigerator ip = machine 1 ip address.<br>

s1_name="Milch"
s1_initAmount="1000"
s1_maxAmount="2000"
s1_changeInterval="10"
s1_changeAmount="-200"
<br>
s2_name="Wurst"
s2_initAmount="1000"
s2_maxAmount="2000"
s2_changeInterval="10"
s2_changeAmount="-200"
<br>
s3_name="Wasser"
s3_initAmount="1000"
s3_maxAmount="2000"
s3_changeInterval="10"
s3_changeAmount="-200"
<br>
s4_name="Joghurt"
s4_initAmount="500"
s4_maxAmount="500"
s4_changeInterval="10"
s4_changeAmount="-100"
<br>
s5_name="Butter"
s5_initAmount="500"
s5_maxAmount="500"
s5_changeInterval="10"
s5_changeAmount="-100"
<br>

Expected results:<br>
The webpage should loaded.<br>
The names of the content elements should be "Milch","Wurst","Wasser","Joghurt","Butter".<br>
All elements should have content of 0.<br>
Each element should have 6 entries in their content history.<br>
"Milch","Wurst" and "Wasser" should have 0,200,400,600,800,1000.<br>
"Joghurt" and "Butter" should have 0,100,200,300,400,500.<br>

Actual resulsts:
Matched expecation.
![resulting webpage](http://https://github.com/JsScho/VS1718/blob/master/pictures/test1Results.png)

2) A functional Junit test for the method "addHistoryEntry" in refrigerator class. makes sure that UDP packet information received by the sensors is stored appropriatly. also test for duplicates, reordering of udp packets.

3) A functional test: Some predefined HTTP requests with various flaws in the requests header are send to the webserver. the status code of the server http reponse is then compared to the expected value.
