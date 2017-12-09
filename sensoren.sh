export s1_name="Milch"
export s1_initAmount="1000"
export s1_maxAmount="2000"
export s1_changeInterval="10"
export s1_changeAmount="-20"

export s2_name="Wurst"
export s2_initAmount="300"
export s2_maxAmount="500"
export s2_changeInterval="18"
export s2_changeAmount="-6"

export s3_name="Wasser"
export s3_initAmount="2000"
export s3_maxAmount="2000"
export s3_changeInterval="6"
export s3_changeAmount="-43"

export s4_name="Joghurt"
export s4_initAmount="500"
export s4_maxAmount="500"
export s4_changeInterval="7"
export s4_changeAmount="-3"

export s5_name="Butter"
export s5_initAmount="200"
export s5_maxAmount="500"
export s5_changeInterval="16"
export s5_changeAmount="-7"

export refrigeratorIP="172.16.205.159"

createSensor(){
	java -jar sensor.jar $1 $2 $3 $4 $5 $6 &
	echo 'Sensor - '$1''
	read -p "$(echo -e 'Press any key to cancel \n\n\b')" -n1 junk
	echo
}
export -f createSensor


echo "creating processes..."

gnome-terminal -x bash -c 'createSensor $s1_name $s1_initAmount $s1_maxAmount $s1_changeInterval $s1_changeAmount $refrigeratorIP; bash' &

gnome-terminal -x bash -c 'createSensor $s2_name $s2_initAmount $s2_maxAmount $s2_changeInterval $s2_changeAmount $refrigeratorIP; bash' &

gnome-terminal -x bash -c 'createSensor $s3_name $s3_initAmount $s3_maxAmount $s3_changeInterval $s3_changeAmount $refrigeratorIP; bash' &

gnome-terminal -x bash -c 'createSensor $s4_name $s4_initAmount $s4_maxAmount $s4_changeInterval $s4_changeAmount $refrigeratorIP; bash' &

gnome-terminal -x bash -c 'createSensor $s5_name $s5_initAmount $s5_maxAmount $s5_changeInterval $s5_changeAmount $refrigeratorIP; bash' &

echo "done"
