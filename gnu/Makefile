
all:
	echo "Please specify a system: windows wine linux osx"
	../gradlew clean install
windows:
	make -C .\src\main\c windowsLocal
	../gradlew clean install
wine:
	make -C src/main/c windows
	../gradlew clean install
linux:
	make -C src/main/c linux
	../gradlew clean install
linux32:
	make -C src/main/c linux32
	../gradlew clean install
linux64:
	make -C src/main/c linux64
	../gradlew clean install
arm:
	sudo apt-get install g++-arm-linux-gnueabihf g++-arm-linux-gnueabi
	make -C src/main/c arm7
	make -C src/main/c arm7HF
	../gradlew clean install
osx:
	make -C src/main/c osx
	../gradlew clean install
