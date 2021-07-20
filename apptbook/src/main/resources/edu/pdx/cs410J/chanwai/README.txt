Name: Wai Chan
Project 3

This project will create appointments and an appointment book from a text file, and it will write back to the same file.
The project will also print an appointment book with its appointments in a nicer format to a file.

If the user enter "-textFile file_name1 -pretty file_name2", the program will read the contents from the file_name1.
Then, it will create an appointment with its associated appointments.
Next, it will write the contents of an appointment book including appointments to the file_name1.
Finally, it will write the contents of file_name1 to file_name2 in a nicer format.

The user will enter the owner name, a description of the appointment,
the begin date in mm/dd/yyyy, begin time in hh:mm, the end date, and the end time in command line.
The user will have an option to enter "-print", "-README", "-textFile file".
The "-print" will print a description of the new appointment.
The "-README" will print the README.txt for this project and exits.
The "-textFile file" will read the contents from "file", and write them to the "file".
The "-pretty file" will write the contents of the appointment book to the "file".

The usage of the project: java -jar target/apptbook-2021.0.0.jar [options] <args>
[options] may appear in any order.
<args> are in the order of owner -> description -> begin date -> begin time -> end date -> end time.