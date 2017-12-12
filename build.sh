compileRefrig() {
	cd ./Refrigerator
	mvn package
	cd ./target
	mv Refrigerator-1.0.jar ../../refrigerator.jar
	cd ..
	rm -rf target
	cd ..
}

compileSensor() {
	cd ./Sensor
	mvn package
	cd ./target
	mv Sensor-1.0.jar ../../sensor.jar
	cd ..
	rm -rf target
	cd ..
}

compileResponseTest() {
	cd ./HttpResponseTest
	mvn package
	cd ./target
	mv HttpResponseTest-1.0.jar ../../responseTest.jar
	cd ..
	rm -rf target
	cd ..
}

compileRefrig
compileSensor
compileResponseTest
