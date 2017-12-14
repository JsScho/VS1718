# VS1718


H-da Hochschule Darmstadt
Distributed Systems Assignment 
Winter semester 2017/2018

Smart home - Refrigerator Simulation

1 Application description<br>
--[1.1 sensor application](#sensor)<br>
--[1.2 refrigerator application](#refrigerator)<br>
[2 How to run it](#how_to_run)<br>
[3 tests](#tests)<br>

The simulation consists of 2 separate applications:

<a name="sensor"></a>Sensor application

- The sensor application represents the actual fill level of an element stored in the refrigerator. If the fill level changes, the sensor sends a UDP packet to the refrigerator application using port 3141. the udp packet information consists of the name of the product, the the amount and the timestamp when the change to the fill level took place. The sensor application requires following parameters from command line:
  - the name of the stored element (e.g. "Milk")
  - the initial amount of that element when the application is started
  - the max amount of that element possible (the actual amount cannot surpass this number at any time)
  - change interval in seconds: the sensor changes its fill level every x seconds to simulate food consumption over time.
  - change amount: how much the fill level will change with every change event ( has to be negative if food is consumed )
  - the IP address of the refrigerator (e.g. "192.168.15.2")


<a name="refrigerator"></a> Refrigerator application

- The refrigerator application relies on the sensor information to keep track of the fill levels of all its elements. It cannot access the values of the sensor directly, but stores the information received by the the sensors. For each element a fill level history is kept. A user can access refrigerator content information over a webbrowser using the ip address of the refrigerator as url and specifying port 3142. the application takes 1 parameters over command line:
  - the number of history entries printed for each element when accessed over the webbrowser.


<a name="how_to_run"></a>How to run it:

in repository base folder run "build.sh" script (building requires Maven).
change execution parameters (such as refrigerator host system ip address) in refrigerator.sh and sensor.sh files.
start program by executing refrigerator.sh and/or sensor.sh script.

Access refrigerator interface via web browser:
as url type in the ip address of the refrigerator followed by :3142 (example 192.168.5.77:3142)


<a name="tests"></a>Tests

1) Functional test: Change Run application as described in how to run it and check results in webbrowser. 

2) A functional Junit test for the method "addHistoryEntry" in refrigerator class. makes sure that UDP packet information received by the sensors is stored appropriatly. also test for duplicates, reordering of udp packets.

3) A functional test: Some predefined HTTP requests with various flaws in the requests header are send to the webserver. the status code of the server http reponse is then compared to the expected value.
