export refrigeratorIP="192.168.137.53"
export testDuration="20000"

createTest(){
	java -jar HttpReponseTest-1.0.jar $1 $2 &
	echo 'Test'
	read -p "$(echo -e 'Press any key to cancel \n\n\b')" -n1 junk
	echo
}
export -f createTest



gnome-terminal -x bash -c 'createTest $refrigeratorIP $testDuration; bash' &
