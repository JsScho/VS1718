export showHistoryEntries="20"

export refrigeratorIP="172.16.205.159"

createRefrigerator(){
	java -jar refrigerator.jar $1 &
	echo 'Refrigerator'
	read -p "$(echo -e 'Press any key to cancel \n\n\b')" -n1 junk
	echo
}
export -f createRefrigerator



echo "creating processes..."

gnome-terminal -x bash -c 'createRefrigerator $showHistoryEntries; bash' &

echo "done"
