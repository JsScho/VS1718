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
--[3.2 content history test](#contentHistoryTest)<br>
--[3.3 http response quality test](#httpQualityTest)<br>


<strong><a name="description"></a>1 Application description</strong>

<strong><a name="sensor"></a>1.1 Sensor application</strong>

- The sensor application represents the actual fill level of an element stored in the refrigerator. If the fill level changes, the sensor sends a UDP packet to the refrigerator application using port 3141. the udp packet information consists of the name of the product, the the amount and the timestamp when the change to the fill level took place. The sensor application requires following parameters from command line:
  - the name of the stored element (e.g. "Milk")
  - the initial amount of that element when the application is started
  - the max amount of that element possible (the actual amount cannot surpass this number at any time)
  - change interval in seconds: the sensor changes its fill level every x seconds to simulate food consumption over time.
  - change amount: how much the fill level will change with every change event (has to be negative if food is consumed)
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

Actual results:
Matched expecation.
![resulting webpage](https://github.com/JsScho/VS1718/tree/master/pictures/test1Results.png)


<strong><a name="contentHistoryTest"></a>3.2 Content History test</strong>

What is tested:
Refrigerator method "addToContentHistory". after receiving a udp packet from one of the sensors, this method is called with parameters from udp package (sensor name, amount,timestamp).
The refrigerator keeps a content history for each sensor, with entries ordered by time they were registered by the sensor. The newest entry in history is the current amount. When the mehod is called and the information received by the sensor is not a duplicate of an already existing entry in history, the new entry is inserted to the content history, position depending on its timestamp. 

How it is tested:
JUnit test class RefrigeratorTest. A number of method calls is performed, comparing expected with actual history length using assertEquals method. For now it is only tested, IF an entry is added to history, not at what position. Test is run automatically during compilation.

Expected results:
As described in ![RefrigeratorTest.java](https://github.com/JsScho/VS1718/blob/master/Refrigerator/src/test/java/refrigerator/RefrigeratorTest.java)

Actual results:
Matching expectation.


<strong><a name="httpQualityTest"></a>3.3 Http quality test</strong>

What is tested: 
The webserver integrated into the refrigerator is supposed to answer Http-requests with appropriate responses. For example if the http method, the file path, the accepted formats by the client are not supported or there is a syntax error in the request, the webserver has to inform the client about that using the status code in the http reponse.

How it is tested:
The Java application HttpResponseTest included in this repository. For every test case it opens a tcp connection to the server and sends a defined http-request. the status code received is then compared to the expected status code. HttpResponseTest can be built by executing build.sh. After setting the refrigerator ip address in test.sh and starting the refrigerator application, the test can be started using test.sh.

Expected results: 
As described in ![HttpResponseTest.java (method responseQuality)](https://github.com/JsScho/VS1718/blob/master/HttpResponseTest/src/main/java/httptest/HttpResponseTest.java)

Actual results:
Matching expectation.







3) A functional test: Some predefined HTTP requests with various flaws in the requests header are send to the webserver. the status code of the server http reponse is then compared to the expected value.
