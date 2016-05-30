# LogFileProcessing
process log files
https://github.com/mengyanank/LogFileProcessing

## Description

### Purpose
Update the file contents by prepending a cumulative line number to each line. That is, given a directory of log files, the files
are processed in alphabetical order, or order by timestamp dates, and a new file is created corrosponding to an original file.
The input file and output file have the same contents, except that the line number is prepended to each line in the output file.

The goal is to implement it in parallel fashion.

### Command line arguments
The user is expected to configure three command line arguments when running the program. They are the input directory, output
directory, and the number of threads.

### Sequential
The sequential method is provided in the program to provide the user a complete solution. If the number of threads configured by the user happens to be
one, the log file processing program will run its sequential version to process the files.

Besides, for the internal purpose, the sequential version is used to compare the performance with the parallel algorithm.

### Testing
I will also have a single executable for unit testing. It will only test the parallel version since that's the version
I'm more concerned with. The test will simply execute the program, and then check if the files generated match the established
criteria, that is, they contain the same data with the cumulative line count prepended to each line. That will be the only
test since that is all that is expected from this program.

#### Testing Framework ([JUnit](http://junit.org/))
The unit testing framework used is JUnit.

### Assumptions
The design of the parallel algorithm is under the assumption that there is 100MB total memory and the storage device can handle
multiple IO requests simultaneously.

### Environment
The program is built as a Java project in Eclipse and excuted in Eclipse on Windows. For the user to run the program, just import
it into the Eclipse, configure the command line arguments, and run.
