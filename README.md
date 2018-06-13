# OCR-parser
The solution I wrote is written in java 8, and I used the eclipse IDE to build and run my solution.
It uses FileIo to read in input (The business card info) and returns output to a different file.

The input file is simply named "input.txt" while the output file is named "output.txt"
To change or test different inputs, simply edit the input.txt file. Only one input is supported at a time.
If using eclipse, make sure the files are located in the project folder, and not the source folder.
You can set a custom file path for the input file by editing line 12. The same can be done for the output file by editing line 16.
When the desired input is inside of the input file, simply run the program to get the output in the output file.
There is no need to manually clear the output file.

If using an IDE, simply make sure the the input and output filesare in the right location and run.

If using the command line, you may need to set file locations to a custom location.
The program can be compiled and ran using the following commands:
                      javac BusinessCardParser.java ContactInfo.java
                      java BusinessCardParser

Please feel free to contact me with any issues!
