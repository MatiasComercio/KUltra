# KUltra
### Where madness begins
KUltra is a compiler for .kul files, containing syntax corresponding to these
files specifications. 
KUltra compiles these files into bytecode to be executed directly on the JVM.
This project was build with the help of CUP, JFlex and ASM Libraries.

## Build
To generate the KUltra compiler, you should download this project, and
once on the same folder as the main pom's file, execute:
<code>mvn clean package</code>

## Usage
To compile a .kul file, get the KUltra-jar-with-dependencies.jar
generated with the above command (should be on folder 'target'),
and run:
<code>java -jar KUltra-jar-with-dependencies.jar compile <path/to/.kul> <destination/path></code>,
where <destination/path> is an optional parameter; default destination path is the 
folder 'compiled' inside the root directory of this project.
