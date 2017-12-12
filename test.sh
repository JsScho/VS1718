createTest(){
	java -jar test.jar $1 &
	echo 'Test'
	read -p "$(echo -e 'Press any key to cancel \n\n\b')" -n1 junk
	echo
}
export -f createTest



gnome-terminal -x bash -c 'createTest $192.168.137.53; bash' &
