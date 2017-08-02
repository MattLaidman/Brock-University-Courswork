#!/bin/bash
echo
echo "Scene files must be in the appropriate subfolders."
echo "See sample scene file, SampleScene.txt, or documentation."
echo "Images are saved to their respective subfolders."
echo
echo "Select file to run:"



num=-1
while [ $num -ne 0 ]
do
	echo
	echo "1. Colours test (Part One)"
	echo "2. Sillhouette (Part B)"
	echo "3. Full Scene (Part The Third)"
	echo "0. Quit"
	echo
	read num </dev/tty
	if [ $num == 1 ]; then
		cd PartOne
		./compile.sh
		./gencolours.py
		cd ..
	elif [ $num == 2 ]; then
		cd PartB
		./compile.sh
		./gensillhouette.py
		cd ..
	elif [ $num == 3 ]; then
		cd PartTheThird
		./compile.sh
		./genscene.py
		cd ..
	fi
done