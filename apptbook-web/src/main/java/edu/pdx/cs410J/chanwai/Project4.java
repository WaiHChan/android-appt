package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Date;

/**
 * The main class that parses the command line and communicates with the
 * Appointment Book server using REST.
 */
public class Project4 {

    static final String MISSING_COMMAND_LINE_ARGUMENTS = "Missing command line arguments";
    public static final String TOO_MANY_ARGUMENTS = "Too many command line arguments";
    public static final String MISSING_OWNER = "Missing Owner";
    static final String MISSING_DESCRIPTION = "Missing Description";
    static final String MISSING_BEGIN_DATE = "Missing Begin Date";
    static final String MISSING_BEGIN_TIME = "Missing Begin Time";
    static final String MISSING_END_DATE = "Missing End Date";
    static final String MISSING_END_TIME = "Missing End Time";
    static final String YEAR_OUT_OF_BOUNDS = "Year out of bounds";
    static final String MONTH_OUT_OF_BOUNDS = "Month out of bounds" ;
    static final String DAY_OUT_OF_BOUNDS = "Day out of bounds" ;
    static final String HOUR_OUT_OF_BOUNDS = "Hour out of bounds";
    static final String MINS_OUT_OF_BOUNDS = "Minutes out of bounds";
    static final String INVALID_DATE = "Invalid Date: ";
    static final String INVALID_TIME = "Invalid Time: ";
    static final String MISSING_AMPM = "Missing AM or PM";
    static final String BEGIN_DATE_AFTER_END_DATE = "Begin date occurs after End date";
    public static final String MISSING_HOST = "Missing host";
    public static final String MISSING_PORT = "Missing port";

    public static void main(String... args) {
        String hostFlag = null;
        String hostName = null;
        String portFlag = null;
        String portString = null;
        String searchFlag = null;
        String printFlag = null;
        String owner = null;
        String description = null;
        String beginDate = null;
        String beginTime = null;
        String beginAmPm = null;
        String endDate = null;
        String endTime = null;
        String endAmPm = null;
        String beginDateString = null;
        String endDateString = null;
        Date begin_date = null;
        Date end_date = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        for (String arg : args) {
            if ("-host".equals(arg)){
                hostFlag = arg;
            }else if ("-port".equals(arg)){
                portFlag = arg;
            }else if ("-search".equals(arg)) {
                searchFlag = arg;
            }else if ("-print".equals(arg)) {
                printFlag = arg;
            }else if ("-README".equals(arg)) {
                printReadme();
            }else if (hostFlag != null && hostName == null) {
                hostName = arg;
            } else if (portFlag != null && portString == null) {
                portString = arg;
            } else if (owner == null) {
                owner = arg;
            } else if (searchFlag != null && description == null) {
                description = "Invalid";
                beginDate = isDateCorrect(arg);
            } else if (searchFlag == null && description == null){
                description = arg;
            } else if (beginDate == null) {
                beginDate = isDateCorrect(arg);
            } else if (beginTime == null) {
                beginTime = isTimeCorrect(arg);
            } else if (beginAmPm == null) {
                beginAmPm = arg;
            } else if (endDate == null) {
                endDate = isDateCorrect(arg);
            } else if (endTime == null) {
                endTime = isTimeCorrect(arg);
            } else if (endAmPm == null) {
                endAmPm = arg;
            } else {
                usage("Extraneous command line argument: " + arg);
            }
        }

        if (hostName == null) {
            usage( MISSING_HOST );
        } else if ( portString == null) {
            usage( "Missing port" );
        }

        int port;
        try {
            port = Integer.parseInt( portString );
        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        if (owner == null){
            usage(MISSING_OWNER);
        }

        AppointmentBookRestClient client = new AppointmentBookRestClient(hostName, port);

        try {
            if (searchFlag == null) { //No -search
                if (printFlag == null && description == null) { // Only Owner provided
                    AppointmentBook book = client.getAppointments(owner);
                    PrettyPrinter pretty = new PrettyPrinter(new OutputStreamWriter(System.out));
                    pretty.dump(book);
                } else {
                    if(description == null){
                        usage(MISSING_DESCRIPTION);
                    }else if (beginDate == null){
                        usage(MISSING_BEGIN_DATE);
                    } else if (beginTime == null){
                        usage(MISSING_BEGIN_TIME);
                    } else if (beginAmPm == null){
                        usage(MISSING_AMPM);
                    } else if (endDate == null){
                        usage(MISSING_END_DATE);
                    } else if (endTime == null){
                        usage(MISSING_END_TIME);
                    } else if (endAmPm == null){
                        usage(MISSING_AMPM);
                    }
                    try {
                        try {
                            beginDateString = beginDate + " " + beginTime + " " + beginAmPm;
                            endDateString = endDate + " " + endTime + " " + endAmPm;
                            begin_date = df.parse(beginDate + " " + beginTime + " " + beginAmPm);
                            end_date = df.parse(endDate + " " + endTime + " " + endAmPm);
                            if (begin_date.compareTo(end_date) > 0) {
                                usage(BEGIN_DATE_AFTER_END_DATE);
                            }
                        } catch (ParseException e) {
                            usage("Can not parse the date.");
                        }
                        client.createAppointment(owner, description, beginDateString, endDateString);
                        System.out.println("Appointment added.");
                        if (printFlag != null) {
                            Appointment appointmentFromArgs = new Appointment(owner, description, begin_date, end_date);
                            System.out.println("New added appointment:");
                            System.out.println(appointmentFromArgs);
                        }
                    } catch (IOException e) {
                        error("While contacting server: " + e);
                        return;
                    }
                }
            } else { // Yes, -search
                if (beginDate == null){
                    usage(MISSING_BEGIN_DATE);
                } else if (beginTime == null){
                    usage(MISSING_BEGIN_TIME);
                } else if (beginAmPm == null){
                    usage(MISSING_AMPM);
                } else if (endDate == null){
                    usage(MISSING_END_DATE);
                } else if (endTime == null){
                    usage(MISSING_END_TIME);
                } else if (endAmPm == null){
                    usage(MISSING_AMPM);
                }

                try {
                    beginDateString = beginDate + " " + beginTime + " " + beginAmPm;
                    endDateString = endDate + " " + endTime + " " + endAmPm;
                    begin_date = df.parse(beginDate + " " + beginTime + " " + beginAmPm);
                    end_date = df.parse(endDate + " " + endTime + " " + endAmPm);
                    if (begin_date.compareTo(end_date) > 0){
                        usage(BEGIN_DATE_AFTER_END_DATE);
                    }
                }catch (ParseException e){
                    usage("Can not parse the date.");
                }
                AppointmentBook book = client.getAppointmentsBasedOnDate(owner, beginDateString, endDateString);
                PrettyPrinter pretty = new PrettyPrinter(new OutputStreamWriter(System.out));
                pretty.dump(book);
            }
        }catch (IOException | ParserException ex ) {
            error("While contacting server: " + ex);
            System.exit(1);
            return;
        }
        System.exit(0);
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java edu.pdx.cs410J..<login-id>.Project4 [options] <args>");
        err.println("  options are (options may appear in any order):");
        err.println("  -host hostname   Host of web server");
        err.println("  -port port       Port of web server");
        err.println("  -search          Appointments should be searched for");
        err.println("  -print           Prints a description of the new appointment");
        err.println("  -README          Prints a README for this project and exits");
        err.println("  args are (in this order):");
        err.println("  owner        the person who owns the appt book");
        err.println("  description  A description of the appointment");
        err.println("  begin        When the appt begins");
        err.println("  end          When the appt ends");
        err.println();

        System.exit(1);
    }

    /**
     * Open the README.txt file
     */
    private static void printReadme() {
        try (
                InputStream readme = Project4.class.getResourceAsStream("README.txt")
        ) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String txt;
            while ((txt = reader.readLine()) != null) {
                System.out.println(txt);
            }
            reader.close();
            System.exit(0);
        } catch (IOException e) {
            usage("File doesn't exist.");
        }
    }

    /**
     * Returns true or false if the date is correct
     * @param date a date needed to be check if it is within the range
     * @return Returns true or false if the date is valid or invalid
     */
    private static String isDateCorrect(String date) {
        try {
            StringTokenizer stHour = new StringTokenizer(date, "/");
            int month = -1;
            int day = -1;
            int year = -1;
            int trash = 0;

            while (stHour.hasMoreTokens()) {
                if (month == -1){
                    month = Integer.parseInt(stHour.nextToken());
                }else if (day == -1){
                    day = Integer.parseInt(stHour.nextToken());
                }else if (year == -1){
                    year = Integer.parseInt(stHour.nextToken());
                }else{
                    trash = Integer.parseInt(stHour.nextToken());
                }
            }

            if (trash != 0){
                usage(INVALID_DATE + date);
            }else if (month == -1){
                usage(INVALID_DATE + date);
            }else if (day == -1){
                usage(INVALID_DATE + date);
            }else if (year == -1){
                usage(INVALID_DATE + date);
            }

            if (year <= 0 || year >= 10000) {
                usage(YEAR_OUT_OF_BOUNDS + " " + date);
            }
            if (month <= 0 || month >= 13) {
                usage(MONTH_OUT_OF_BOUNDS + " " + date);
            }
            if (day <= 0 || day >= 32) {
                usage(DAY_OUT_OF_BOUNDS + " " + date);
            }

            return date;
        } catch (NumberFormatException ex){
            usage("Invalid Date: " + date);
            return null;
        }
    }

    /**
     * Returns true or false if the time is correct
     * @param time the appointment time needed to be checked
     * @return True or False if the time is valid or invalid
     */
    private static String isTimeCorrect(String time){
        try {
            StringTokenizer st = new StringTokenizer(time, ":");
            int hour = -1;
            int min = -1;
            int trash = 0;

            while (st.hasMoreTokens()) {
                if (hour == -1){
                    hour = Integer.parseInt(st.nextToken());
                }else if (min == -1){
                    min = Integer.parseInt(st.nextToken());
                }else{
                    trash = Integer.parseInt(st.nextToken());
                }
            }

            if (trash != 0){
                usage(INVALID_TIME + time);
            }else if (hour == -1){
                usage(INVALID_TIME + time);
            }else if (min == -1){
                usage(INVALID_TIME + time);
            }

            if (hour < 0 || hour >= 13) {
                usage(HOUR_OUT_OF_BOUNDS + " " + time);
            }
            if (min < 0 || min >= 60) {
                usage(MINS_OUT_OF_BOUNDS + " " + time);
            }
            return time;
        }catch (NumberFormatException ex){
            usage(INVALID_TIME + time);
            return null;
        }
    }

}