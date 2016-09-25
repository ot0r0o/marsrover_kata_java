Mars Rover kata
=============

This code is a Java solution for the Mars Rover kata. It has been developed with:
> - Java 8
> - Spring Boot 1.4
> - Maven

How it works
----------------
- Given a initial size of the plateau (x,y) which indicates the upper-right position.
- Given a initial position and direction of the rover (x,y,d). The direction facing by the rover is showed like (N,E,S,W).
- The rover executes the commands based on an array of characters.
- To turn left we use an L, to turn right an R and M to move forward.
- The command execution is secuential, it means if there is a list of rovers it is executed one at a time.
- The output shows the final position and facing direction of each rover.

Input & output example:

|    Input   |    Output   |
|:----------:|:-----------:|
| 5 5        | 1 3 N 5 1 E |
| 1 2 N      |
| LMLMLMLMM  |
| 3 3 E      |
| MMRMMRMRRM |

----------

Compile solution
------------------------

On UNIX system:
> $ ./mvnw clean install

On Windows:
> $ mvnw.cmd clean install

After testing and compilation, an executable JAR (marsrover-VERSION.jar) can be found in target folder.

Use modes
-------------

This implementation offers different modes to reach the final solution:

>- **File input**
On this mode we need to specify an input file wich contains all the needed data to move the rovers over the plateau.
>>$ java -jar marsrover-VERSION.jar --file=FILE_LOCATION

>- **Keyboard input**
On this mode we can specify needed data through keyboard. First the upper-right position of the plateau followed by rovers information (initial position and commands). Follow instructions.
>>$ java -jar marsrover-VERSION.jar --keyboard

>- **Remote**
On this solution an embeded Tomcat server is launched. The data is send through a REST end-point. It can be made as follows:
>>$ java -jar marsrover-VERSION.jar --web

>>$ curl -i -H "Content-Type: application/json" -X POST -d '{"grid":"5 5","rovers":[["1 2 N","LMLMLMLMM"],["3 3 E","MMRMMRMRRM"]]}' http://localhost:8080/remote


