# puzzle
Explorer Programming Puzzle

How to Compile Using Command Line

Pull all the code into a local folder. Say for example the folder is named *Puzzle*. Execute the following

> E:\Puzzle>"C:\Program Files\Java\jdk1.8.0_212\bin\javac" -d target\ src\main\java\com\gvnn\trb\exception\ToyRobotException.java

> E:\Puzzle>"C:\Program Files\Java\jdk1.8.0_212\bin\javac" -d target\ -cp target\ src\main\java\com\gvnn\trb\simulator\*.java

> E:\Puzzle>"C:\Program Files\Java\jdk1.8.0_212\bin\javac" -d target\ -cp target\ src\main\java\com\gvnn\trb\*.java

Assuming that your java compiler resides in Program Files\Java folder, the first line will compile the Exception class, and the second line will compile all the dependencies for Main. Once that is done, the third command will compile the Main class. Assuming everything goes smooth, you should be able to run the program from command line by moving to target directory and typing:

> E:\Puzzle\target>"C:\Program Files\Java\jdk1.8.0_212\bin\java" com.gvnn.trb.Main

## Note
Please use Java 8 for compiling the code as Java 7 will throw some weird errors and will also not be able to handle lambda code.  I used '1.8.0' for compilation.

## Compiling Test
In addition to JUnit and hamcrest jar, you will need [Mockito](https://site.mockito.org/). Continuing with the previous analogy, you can compile tests using the following command

> E:\Puzzle>"C:\Program Files\Java\jdk1.8.0_212\bin\javac" -d target\ -cp e:\Software\JUnit\junit-4.12.jar;e:\Software\JUnit\hamcrest-core-1.3.jar;target\;"E:\software\JUnit\mockito-core-2.28.2.jar";. src\test\java\*.java
