Name: Wai Chan
Project 4

In this project, the appointment book application to support an appointment book server that
provides REST-ful web services to an appointment book client.

The user will enter the owner name, a description of the appointment,
the begin date in mm/dd/yyyy, begin time in hh:mm, AM or PM, the end date, and the end time in command line.
The user will have an option to enter "-print", "-README", "-host hostname", "-port port", and "-search".
The "-print" will print a description of the new appointment.
The "-README" will print the README.txt for this project and exits.
The "-host hostname" Host computer on which the server runs.
The "-port port" Port on which the server is listening.
The "-search" Appointments should be searched for.

The usage of the project: java -jar target/apptbook-client.jar [options] <args>
[options] may appear in any order.
<args> are in the order of owner -> description -> begin date -> begin time -> AM/PM -> end date -> end time -> AM/PM.