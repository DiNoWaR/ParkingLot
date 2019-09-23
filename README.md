To run this app. do the following:

1) Install JDK 1.8


2) Install scala compiler version 2.12 and
add scala/bin directory to PATH. To make sure
you have done this properly:

Open command prompt.
Type scala

You will get following or similar output:
Welcome to Scala 2.12.8 (Java HotSpot(TM) 64-Bit Server VM, Java 11.0.1)

3)Install Sbt. It is scala build tool for packaging and running tests.

It can be install by the apt-get utility. 
See https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Linux.html

4)Go to project folder through the terminal and do the following:

Type bin/setup.
For interactive mode, type bin/parking_lot 
For text file mode, type bin/parking_lot {PATH_TO_TEXT_FILE}