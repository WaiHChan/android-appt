package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * An integration test for {@link Project4} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "1026");

    /**
     * Tests that invoking the main method with no arguments issues an error
     * Check missing host
     */
    @Test
    void noCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_HOST));
    }

    /**
     * Tests that invoking the main method with one arguments issues an error
     * Check missing port
     */
    @Test
    void oneCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_PORT));
    }

    /**
     * Tests that invoking the main method with one arguments issues an error
     * Check post is not number
     */
    @Test
    void postIsNotNumber() {
        //MainMethodResult result = invokeMain(Project4.class, "-host", HOSTNAME, "-post", PORT, word);
        //assertThat(result.getTextWrittenToStandardError(), containsString("Port \"" + word + "\" must be an integer"));
        try {
            invokeMain(Project4.class, "-host", HOSTNAME, "-port", "String");
            //fail("Expected a RestException to be thrown");

        } catch (NumberFormatException ex) {
            RestException cause = (RestException) ex.getCause();
            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
            //assertThat(result.getTextWrittenToStandardError(), containsString("Port \"" + word + "\" must be an integer"));
        }
    }

    /**
     * Tests that invoking the main method with missing owner issues an error
     */
    @Test
    void missingOwner(){
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_OWNER));
    }

//    /**
//     * Tests that invoking the main method with one arguments issues an error
//     * If owner provided, pretty print all owner's appointments.
//     */
//    @Test
//    void noSearchOnlyOwnerPresentPrettyPrint(){
//        String owner = "Jim";
//        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner);
//        assertThat(result.getTextWrittenToStandardOut(), containsString(""));
//
//    }

    /**
     * Tests that invoking the main method with missing description issues an error
     */
    @Test
    void noSearchOwnerAppointmentNotFound(){
        String owner = "Jim";
        try {
            invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, owner);
        } catch (UncaughtExceptionInMain ex) {
            RestException cause = (RestException) ex.getCause();
            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
        }
    }

    /**
     * Tests that invoking the main method with missing begin date issues an error
     */
    @Test
    void noSearchMissingBeginDate(){
        String owner = "Jim";
        String description = "Poker";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner, description);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_BEGIN_DATE));
    }

    /**
     * Tests that invoking the main method with missing begin time issues an error
     */
    @Test
    void noSearchMissingBeginTime(){
        String owner = "Jim";
        String description = "Poker";
        String begin_date = "1/1/2012";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner, description, begin_date);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_BEGIN_TIME));
    }

    /**
     * Tests that invoking the main method with missing begin AM/PM issues an error
     */
    @Test
    void noSearchMissingBeginAM(){
        String owner = "Jim";
        String description = "Poker";
        String beginDate = "1/1/2012";
        String beginTime = "11:30";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner, description, beginDate, beginTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_AMPM));
    }

    /**
     * Tests that invoking the main method with missing end date issues an error
     */
    @Test
    void noSearchMissingEndDate(){
        String owner = "Jim";
        String description = "Poker";
        String beginDate = "1/1/2012";
        String beginTime = "11:30";
        String am = "AM";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner, description, beginDate, beginTime, am);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_END_DATE));
    }

    /**
     * Tests that invoking the main method with missing end time issues an error
     */
    @Test
    void noSearchMissingEndTime(){
        String owner = "Jim";
        String description = "Poker";
        String beginDate = "1/1/2012";
        String beginTime = "11:30";
        String am = "AM";
        String endDate = "1/1/2013";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner, description, beginDate, beginTime, am, endDate);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_END_TIME));
    }

    /**
     * Tests that invoking the main method with missing end am/pm issues an error
     */
    @Test
    void noSearchMissingEndAM(){
        String owner = "Jim";
        String description = "Poker";
        String beginDate = "1/1/2012";
        String beginTime = "11:30";
        String am = "AM";
        String endDate = "1/1/2013";
        String endTime = "11:30";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner, description, beginDate, beginTime, am, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_AMPM));
    }

    /**
     * Tests that invoking the main method with the begin time happen after the end time issues an error
     */
    @Test
    void noSearchBeginAfterEnd(){
        String owner = "Jim";
        String description = "Poker";
        String beginDate = "1/1/2012";
        String beginTime = "11:30";
        String am = "AM";
        String endDate = "1/1/2011";
        String endTime = "11:30";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner, description, beginDate, beginTime, am, endDate, endTime, am);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.BEGIN_DATE_AFTER_END_DATE));
    }

    /**
     * Tests that invoking the main method with failing to parse date issues an error
     */
    @Test
    void noSearchCannotParseTheDate(){
        String owner = "Jim";
        String description = "Poker";
        String beginDate = "1/1/2012";
        String beginTime = "11:30";
        String am = "Ax";
        String endDate = "1/1/2013";
        String endTime = "11:30";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner, description, beginDate, beginTime, am, endDate, endTime, am);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Can not parse the date."));
    }

    /**
     * Tests that invoking the main method with correct args will create an appointment
     */
    @Test
    void noSearchCreateAppointment(){
        String owner = "Jim";
        String description = "Poker";
        String beginDate = "1/1/2012";
        String beginTime = "11:30";
        String am = "AM";
        String endDate = "1/1/2013";
        String endTime = "11:30";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner, description, beginDate, beginTime, am, endDate, endTime, am);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Appointment added."));
    }

    /**
     * Tests that invoking the main method with correct args will create an appointment
     */
    @Test
    void noSearchCreateAppointmentPrint(){
        String owner = "Jim";
        String description = "Poker";
        String beginDate = "1/1/2012";
        String beginTime = "11:30";
        String am = "AM";
        String endDate = "1/1/2013";
        String endTime = "11:30";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-print", owner, description, beginDate, beginTime, am, endDate, endTime, am);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString(description + " from"));
    }

    /**
     * Tests that invoking the main method with -search and missing begin date issues an error
     */
    @Test
    void searchMissingBeginDate(){
        String owner = "Jim";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", owner);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_BEGIN_DATE));
    }

    /**
     * Tests that invoking the main method with -search and missing begin time issues an error
     */
    @Test
    void searchMissingBeginTime(){
        String owner = "Jim";
        String beginDate = "1/1/2012";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", owner, beginDate);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_BEGIN_TIME));
    }

    /**
     * Tests that invoking the main method with -search and missing begin AM issues an error
     */
    @Test
    void searchMissingBeginAm(){
        String owner = "Jim";
        String beginDate = "1/1/2012";
        String beginTime = "11:10";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", owner, beginDate, beginTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_AMPM));
    }

    /**
     * Tests that invoking the main method with -search and missing end Date issues an error
     */
    @Test
    void searchMissingEndDate(){
        String owner = "Jim";
        String beginDate = "1/1/2012";
        String beginTime = "11:10";
        String am = "AM";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", owner, beginDate, beginTime, am);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_END_DATE));
    }

    /**
     * Tests that invoking the main method with -search and missing end time issues an error
     */
    @Test
    void searchMissingEndTime(){
        String owner = "Jim";
        String beginDate = "1/1/2012";
        String beginTime = "11:10";
        String am = "AM";
        String endDate = "1/1/2013";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", owner, beginDate, beginTime, am, endDate);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_END_TIME));
    }

    /**
     * Tests that invoking the main method with -search and missing end am/pm issues an error
     */
    @Test
    void searchMissingEndAm(){
        String owner = "Jim";
        String beginDate = "1/1/2012";
        String beginTime = "11:10";
        String am = "AM";
        String endDate = "1/1/2013";
        String endTime = "11:10";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", owner, beginDate, beginTime, am, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_AMPM));
    }

    /**
     * Tests that invoking the main method with -search begin time happen after end time issues an error
     */
    @Test
    void searchBeginAfterEnd(){
        String owner = "Jim";
        String beginDate = "1/1/2014";
        String beginTime = "11:10";
        String am = "AM";
        String endDate = "1/1/2013";
        String endTime = "11:10";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", owner, beginDate, beginTime, am, endDate, endTime, am);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.BEGIN_DATE_AFTER_END_DATE));
    }

    /**
     * Tests that invoking the main method with -search begin time happen after end time issues an error
     */
    @Test
    void searchCannotParseDate(){
        String owner = "Jim";
        String beginDate = "1/1/2012";
        String beginTime = "11:10";
        String am = "AS";
        String endDate = "1/1/2013";
        String endTime = "11:10";
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", owner, beginDate, beginTime, am, endDate, endTime, am);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Can not parse the date."));
    }


//
//    @Test
//    void test4AddDefinition() {
//        String word = "WORD";
//        String definition = "DEFINITION";
//
//        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, word, definition );
//        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
//        String out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(Messages.definedWordAs(word, definition)));
//
//        result = invokeMain( Project4.class, HOSTNAME, PORT, word );
//        out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(Messages.formatDictionaryEntry(word, definition)));
//
//        result = invokeMain( Project4.class, HOSTNAME, PORT );
//        out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(Messages.formatDictionaryEntry(word, definition)));
//    }
}