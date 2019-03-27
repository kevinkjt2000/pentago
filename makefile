all : PentagoGUI.class Pentago.class RoundButton.class ComputerPlayer.class Game.class
.PHONY : all

run : all
	java PentagoGUI
.PHONY : run

PentagoGUI.class : PentagoGUI.java
	javac PentagoGUI.java

Pentago.class : Pentago.java
	javac Pentago.java

RoundButton.class : RoundButton.java
	javac RoundButton.java

ComputerPlayer.class : ComputerPlayer.java
	javac ComputerPlayer.java

Game.class : Game.java
	javac Game.java

clean :
	rm -f PentagoGui.class
.PHONY : clean