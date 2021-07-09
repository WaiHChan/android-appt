Name: Wai Chan
Project 2

This project will create appointments and an appointment book from a text file, and it will write back to the same file.
If the user enter "-textFile file_name", the program will read the contents from the file_name.
Then, it will create an appointment with its associated appointments.
Next, it will write the contents of an appointment book including appointments to the file_name.

If the user enter "-textFile file_name args". The program will first read and write to the file_name,
and it will read the arguments from the command line arguments, and it will create an appointment book with the appointment.

If the user does not enter "-textFile file_name", it will read the valid command line arguments,
and it will create an appointment and an appointment book.

The user will enter the owner name, a description of the appointment,
the begin date in mm/dd/yyyy, begin time in hh:mm, the end date, and the end time in command line.
The user will have an option to enter "-print", "-README", "-textFile file".
The "-print" will print a description of the new appointment.
The "-README" will print the README.txt for this project and exits.
The "-textFile file" will read the contents from "file", and write them to the "file".

The usage of the project: java -jar target/apptbook-2021.0.0.jar [options] <args>
[options] may appear in any order.
<args> are in the order of owner -> description -> begin date -> begin time -> end date -> end time.