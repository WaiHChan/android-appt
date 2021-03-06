package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;
import java.io.*;
import static edu.pdx.cs410J.chanwai.Project3.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;

/**
 * Integration tests for the {@link Project3} main class.
 */
class Project3IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain(Project3.class);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_COMMAND_LINE_ARGUMENTS));
    }

    /**
     * Tests that invoking the main method with one argument issues an error
     * If argument is only "-print", error
     */
    @Test
    void testMissSpellingPrintWithOneArgument() {
        MainMethodResult result = invokeMain(Project3.class, "-prit");

        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_DESCRIPTION));
    }

    /**
     * Tests that invoking the main method with one argument issues an error
     * If argument is only "-print", error
     */
    @Test
    void testMissingPrintWithOneArgument() {
        MainMethodResult result = invokeMain(Project3.class, "-print");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_COMMAND_LINE_ARGUMENTS));
    }

    /**
     * Tests that invoking the main method with two arguments issues an error
     * If argument is only "-print" "owner", missing description
     */
    @Test
    void testMissingPrintWithTwoArguments(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_DESCRIPTION));
    }

    /**
     * Tests that invoking the main method with three arguments issues an error
     * If argument is only "-print" "owner" "description", missing begin date
     */
    @Test
    void testMissingPrintWithThreeArguments(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_BEGIN_DATE));
    }

    /**
     * Tests that invoking the main method with four arguments issues an error
     * If argument is only "-print" "owner" "description" "begin date", missing begin time
     */
    @Test
    void testMissingPrintWithFourArguments(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_BEGIN_TIME));
    }

    /**
     * Tests that invoking the main method with five arguments issues an error
     * If argument is only "-print" "owner" "description" "begin date" "begin time", missing am/pm
     */
    @Test
    void testMissingPrintWithFiveArguments(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021", "12:21");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(MISSING_AMPM));
    }

    /**
     * Tests that invoking the main method with six arguments issues an error
     * If argument is only "-print" "owner" "description" "begin date" "begin time" "end date", missing end date
     */
    @Test
    void testMissingPrintWithSixArguments(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021", "12:21", "AM");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(MISSING_END_DATE));
    }

    /**
     * Tests that invoking the main method with six arguments issues an error
     * If argument is only "-print" "owner" "description" "begin date" "begin time" "end date", missing end time
     */
    @Test
    void testMissingPrintWithSevenArguments(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021", "12:21", "AM", "1/1/2021");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(MISSING_END_TIME));
    }


    /**
     * Tests that invoking the main method with six arguments issues an error
     * If argument is only "-print" "owner" "description" "begin date" "begin time" "end date", missing end am/pm
     */
    @Test
    void testMissingPrintWithEightArguments(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021", "12:21", "AM", "1/1/2021", "11:20");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(MISSING_AMPM));
    }
    /**
     * Tests that invoking the main method with seven command line arguments
     * If the begin date contains any alphabet, issues an error
     */
    @Test
    void invalidBeginDateForArgumentSeven(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "abc", "12:21", "10/26/1995", "13:21");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date: abc"));
    }


    /**
     * Tests that invoking the main method with one argument issues an error
     * If argument is only "-textFile", error
     */
    @Test
    void testMissingTextFileWithOneArgument() {
        MainMethodResult result = invokeMain(Project3.class, "-textFile");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_COMMAND_LINE_ARGUMENTS));
    }

    /**
     * Tests that invoking the main method with one argument issues an error
     * If argument is only "-textFile", error
     */
    @Test
    void testMissingTextFileWithTwoArgument() {
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "text.txt" );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_COMMAND_LINE_ARGUMENTS));
    }

    /**
     * Tests that invoking the main method with one argument
     * If argument is only "-README", print a string
     */
    @Test
    void testPrintReadmeOneArgument() {
        MainMethodResult result = invokeMain(Project3.class, "-README");

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
    }

    /**
     * Tests that invoking the main method with one argument
     * If argument is only "-README", print a string
     */
    @Test
    void testReadmeOneArgument() throws IOException {
        MainMethodResult result = invokeMain(Project3.class, "-README");

        InputStream readme = Project3.class.getResourceAsStream("README.txt");

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(readme, not(nullValue()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
        String line = reader.readLine();
        assertThat(line, containsString("Name: Wai Chan"));
    }


    /**
     * Tests that invoking the main method with two arguments issues an error
     * If argument is only "owner" "description", missing begin date
     */
    @Test
    void testMissingBeginDate(){
        MainMethodResult result = invokeMain(Project3.class,"Jimmy", "Body Check");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_BEGIN_DATE));
    }

    /**
     * Tests that invoking the main method with two arguments
     * If argument is "-README" "Owner", print a string
     */
    @Test
    void testPrintReadmeTwoArgument() {
        MainMethodResult result = invokeMain(Project3.class, "-README", "Jim");

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
    }



    /**
     * Tests that invoking the main method with three arguments issues an error
     * If argument is only "owner" "description" "begin date", missing begin time
     */
    @Test
    void testMissingBeginTime(){
        MainMethodResult result = invokeMain(Project3.class,"Jimmy", "Body Check", "1/1/2021");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_BEGIN_TIME));
    }

    /**
     * Tests that invoking the main method with three arguments
     * If argument is "-README" "Owner" "Description", print a string
     */
    @Test
    void testPrintReadmeThreeArgument() {
        MainMethodResult result = invokeMain(Project3.class, "-README", "Jim", "Description");

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
    }



    /**
     * Tests that invoking the main method with four arguments issues an error
     * If argument is only "owner" "description" "begin date" "begin time", missing end date
     */
    @Test
    void testMissingEndDate(){
        MainMethodResult result = invokeMain(Project3.class,"Jimmy", "Body Check", "1/2/2013", "4:10");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(MISSING_AMPM));
    }

    /**
     * Tests that invoking the main method with four arguments
     * If argument is "-README" "Owner" "Description" "Begin Date", print a string
     */
    @Test
    void testPrintReadmeFourArgument() {
        MainMethodResult result = invokeMain(Project3.class, "-README", "Jim", "Description", "1/1/2112");

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
    }



    /**
     * Tests that invoking the main method with five arguments issues an error
     * If argument is only "owner" "description" "begin date" "begin time" "end date", missing end time
     */
    @Test
    void testMissingEndTime(){
        MainMethodResult result = invokeMain(Project3.class,"Jimmy", "Body Check", "1/2/2013", "4:10", "AM");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(MISSING_END_DATE));
    }

    /**
     * Tests that invoking the main method with five arguments
     * If argument is "-README" "Owner" "Description" "Begin Date" "Begin Time", print a string
     */
    @Test
    void testPrintReadmeFiveArgument() {
        MainMethodResult result = invokeMain(Project3.class, "-README", "Jim", "Description", "1/1/2112", "12:46");

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
    }



    /**
     * Tests that invoking the main method with six arguments
     * If argument is "-README" "Owner" "Description" "Begin Date" "Begin Time" "End Date", print a string
     */
    @Test
    void testPrintReadmeSixArgument() {
        MainMethodResult result = invokeMain(Project3.class, "-README", "Jim", "Description", "1/1/2112", "12:46", "1/2/2221");

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
    }

    /**
     * Tests that invoking the main method with six command line arguments
     * If the begin date contains any alphabet, issues an error
     */
    @Test
    void invalidBeginDateForArgumentSix(){
        MainMethodResult result = invokeMain(Project3.class, "Jimmy", "Body Check", "abc", "12:21", "10/26/1995", "13:21");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date: abc"));
    }

    /**
     * Tests that invoking the main method with six command line arguments
     * If the minutes of begin time contains any alphabet, issues an error
     */
    @Test
    void invalidBeginTimeForArgumentSix(){
        MainMethodResult result = invokeMain(Project3.class, "Jimmy", "Body Check", "5/20/2019", "14:1e", "10/26/1242", "13:21");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time: 14:1e"));
    }

    /**
     * Tests that invoking the main method with six command line arguments
     * If the end date contains any alphabet, issues an error
     */
    @Test
    void invalidEndDateForArgumentSix(){
        MainMethodResult result = invokeMain(Project3.class,  "Jimmy", "Body Check", "5/20/2019", "12:21", "AM", "10/26a/1242");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date: 10/26a/1242"));
    }

    /**
     * Tests that invoking the main method with six command line arguments
     * If the end time contains any alphabet, issues an error
     */
    @Test
    void invalidEndTimeForArgumentSix(){
        MainMethodResult result = invokeMain(Project3.class, "Jimmy", "Body Check", "5/20/2019", "11:1", "am", "5/20/2020");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(MISSING_END_TIME));
    }

    /**
     * Tests that invoking the main method with six command line arguments
     * If the minutes of begin time contains any alphabet, issues an error
     */
    @Test
    void invalidBeginTimeForArgumentSeven(){
        MainMethodResult result = invokeMain(Project3.class,  "-print", "Jimmy", "Body Check", "5/20/2019", "14:1e", "am", "10/26/1242", "13:21");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time: 14:1e"));
    }

    /**
     * Tests that invoking the main method with seven command line arguments
     * If the end date contains any alphabet, issues an error
     */
    @Test
    void invalidEndDateForArgumentSeven(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "5/20/2019", "12:21", "AM", "10/26a/1242");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date: 10/26a/1242"));
    }

    /**
     * Tests that invoking the main method with seven command line arguments
     * If the end date contains any alphabet, issues an error
     */
    @Test
    void invalidEndDateForArgumentEight(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "-pretty", "text6.txt", "-textFile", "text1.txt", "Jimmy", "Body Check", "5/20/2019", "12:21", "AM", "10/26a/1242");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date: 10/26a/1242"));
    }

    /**
     * Tests that invoking the main method with file
     * If the file name exists, check if appointment book is null
     */
    @Test
    void fileNameNotNullFileIsNull(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "text2.txt", "Jimmy", "Body Check", "5/20/2019", "14:1", "am", "10/26/1242", "13:21");

        AppointmentBook nullBook = new AppointmentBook();

        assertThat(nullBook.getOwnerName(), equalTo(null));
        assertThat(result.getExitCode(), equalTo(1));
    }

    /**
     * Tests that invoking the main method with file
     * If the file name exists, check if appointment book owner is same as argument owner
     */
    @Test
    void canNotBeSameFileName(){
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "text1.txt", "-textFile", "text1.txt", "Owner1", "Body Check", "5/20/2019", "11:1", "PM", "10/26/2019", "12:21", "PM");

        assertThat(result.getTextWrittenToStandardError(), containsString(PRETT_FILE_CANNOT_BE_THE_SAME_AS_TEXT_FILE));
        assertThat(result.getExitCode(), equalTo(1));
    }

    /**
     * Tests that invoking the main method with file
     * If the file name exists, check if appointment book owner is same as argument owner
     */
    @Test
    void beginDateAfterEndDate(){
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "text1.txt", "Jimmy Chan", "Body Check", "5/20/2019", "11:1", "PM", "10/26/1242", "12:21", "PM");

        assertThat(result.getTextWrittenToStandardError(), containsString(BEGIN_DATE_AFTER_END_DATE));
        assertThat(result.getExitCode(), equalTo(1));
    }

    /**
     * Tests that invoking the main method with seven arguments
     * If argument is "-README" "Owner" "Description" "Begin Date" "Begin Time" "End Date" "End Time", print a string
     */
    @Test
    void testPrintReadmeSevenArgument() {
        MainMethodResult result = invokeMain(Project3.class, "-README", "Jim", "Description", "1/1/2112", "12:46", "am", "1/2/2221", "1:51");

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
    }

    /**
     * Tests that invoking the main method with more than seven arguments
     * If argument is "-README" "Owner" "Description" "Begin Date" "Begin Time" "End Date" "End Time" args, print readme.txt
     */
    @Test
    void tooManyArgumentWithReadme() {
        MainMethodResult result = invokeMain(Project3.class, "-README", "Jim", "Description", "1/1/2112", "12:46", "am", "1/2/2221", "1:51", "am", "12");

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
    }

    /**
     * Tests that invoking the main method with more than seven arguments
     * If argument is "-option" "Owner" "Description" "Begin Date" "Begin Time" "End Date" "End Time" args, raise an error
     */
    @Test
    void tooManyArgument() {
        MainMethodResult result = invokeMain(Project3.class, "Jim", "Description", "1/1/2112", "12:46", "PM", "1/2/2221", "1:51", "PM", "a");

        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(TOO_MANY_COMMAND_LINE_ARGUMENTS));
    }

     /**
     * Tests that invoking the main method with correct command line arguments
     * If missing month, issues an error
     */
    @Test
    void missingBeginMonth(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "/1/2010", "12:21", "am", "11/10/1996", "13:21", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_DATE + "/1/2010"));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If missing month, issues an error
     */
    @Test
    void missingBeginMonthTwo(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "/", "12:21", "am", "11/10/1996", "13:21", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_DATE));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If missing day, issues an error
     */
    @Test
    void missingBeginDay(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1//2010", "12:21", "11/10/1996", "13:21");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_DATE));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If missing day, issues an error
     */
    @Test
    void missingBeginDayTwo(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1//", "12:21", "11/10/1996", "13:21");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_DATE));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If missing day, issues an error
     */
    @Test
    void missingEndDay(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2010", "12:21", "PM", "11//1996", "13:21", "PM");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_DATE));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If missing year, issues an error
     */
    @Test
    void missingBeginYear(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/", "12:21", "11/10/1996", "13:21");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_DATE));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If missing year, issues an error
     */
    @Test
    void missingEndYear(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/1995", "12:21", "PM", "11/10/", "13:21", "PM");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_DATE));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If it is too many begin date, issues an error
     */
    @Test
    void tooManyBeginDate(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021/1", "12:21", "11/10/1996", "13:21");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_DATE + "1/1/2021/1"));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If it is too many end date, issues an error
     */
    @Test
    void tooManyEndDate(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021", "12:21", "PM", "11/10/1996/1", "13:21", "PM");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_DATE));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If missing minutes, issues an error
     */
    @Test
    void missingBeginHour(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021", ":21", "11/10/1996", "13:21");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_TIME));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If missing minutes, issues an error
     */
    @Test
    void missingEndHour(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021", "11:21", "AM", "11/10/1996", ":21", "PM");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_TIME));
    }


    /**
     * Tests that invoking the main method with correct command line arguments
     * If missing minutes, issues an error
     */
    @Test
    void missingHourTwo(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021", "11:21", "PM", "11/10/1996", ":", "PM");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_TIME));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If missing minutes, issues an error
     */
    @Test
    void missingBeginMin(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021", "11:", "11/10/1996", "13:21");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_TIME));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If missing minutes, issues an error
     */
    @Test
    void missingEndMin(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021", "11:23", "PM", "11/10/1996", "13:", "PM");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_TIME));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If it is too many begin time, issues an error
     */
    @Test
    void tooManyBeginTime(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021", "12:21:2", "11/10/1996", "13:21");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_TIME + "12:21:2"));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If it is too many end time, issues an error
     */
    @Test
    void tooManyEndTime(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/2021", "12:21", "PM", "11/10/1996", "13:21:23", "PM");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(INVALID_TIME + "13:21:23"));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If year of begin date is zero or less, issues an error
     */
    @Test
    void yearLessThanZero(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/0", "10:21", "pm", "11/10/1996", "12:21", "pm");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(YEAR_OUT_OF_BOUNDS));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If year of begin date is larger than 9999, issues an error
     */
    @Test
    void yearLargeThanLimit(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "1/1/10000", "12:21", "pm", "11/10/1996", "13:21", "pm");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(YEAR_OUT_OF_BOUNDS));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If month of begin date is zero or less, issues an error
     */
    @Test
    void monthLessThanZero(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "0/1/1110", "12:21", "pm", "11/10/1996", "13:21", "pm");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MONTH_OUT_OF_BOUNDS));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If month of begin date is larger than 12, issues an error
     */
    @Test
    void monthLargeThanLimit(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "13/1/1110", "12:21", "pm", "11/10/1996", "13:21", "pm");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MONTH_OUT_OF_BOUNDS));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If day of begin date is zero or less, issues an error
     */
    @Test
    void dayLessThanZero(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "10/0/1110", "12:21", "pm", "11/10/1996", "13:21", "pm");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.DAY_OUT_OF_BOUNDS));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If day of begin date is larger than 31, issues an error
     */
    @Test
    void dayLargeThanLimit(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "12/33/1110", "12:21", "pm", "11/10/1996", "13:21", "pm");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.DAY_OUT_OF_BOUNDS));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If the hour of begin date is less than 0, issues an error
     */
    @Test
    void hourLessThanZero() {
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "10/10/1110", "-12:21", "pm", "11/10/1996", "13:21", "pm");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.HOUR_OUT_OF_BOUNDS));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If the hour of begin date is larger than 24, issues an error
     */
    @Test
    void hourLargeThanLimit() {
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "10/10/1110", "13:21", "pm", "11/10/1996", "13:21", "pm");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.HOUR_OUT_OF_BOUNDS));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If the minutes of begin date is less than 0, issues an error
     */
    @Test
    void minsLessThanZero() {
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "10/10/1110", "1:-12", "pm", "11/10/1996", "13:21", "pm");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MINS_OUT_OF_BOUNDS));
    }

    /**
     * Tests that invoking the main method with correct command line arguments
     * If the minutes of begin date is larger than 60, issues an error
     */
    @Test
    void minsLargeThanLimit() {
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "10/10/1110", "10:67", "AM", "11/10/1996", "11:21", "PM");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MINS_OUT_OF_BOUNDS));
    }

    /**
     * Tests that invoking the read file method
     * Check if the correct file is opened by comparing the string.
     */
    @Test
    void readmeCanBeReadAsResource() {
        InputStream readme = Project3.class.getResourceAsStream("READM.txt");
        assertThat(readme, nullValue());
    }

    /**
     * Tests that invoking the toString method from appointment class
     * Check if toString() returns the correct string
     */
    @Test
    void checkAssignmentToString(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Body Check", "10/10/1110", "10:32", "PM", "11/10/1996", "9:21", "PM");

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("\"Body Check\" from 10/10/10, 10:32 PM until 11/10/96, 9:21 PM"));
    }

    /**
     * Tests that invoking the toString method from appointment class
     * Check if toString() returns the correct string
     */
    @Test
    void checkAssignmentToStringTwo(){
        MainMethodResult result = invokeMain(Project3.class, "-print", "Jimmy", "Eye", "10/10/1110", "10:32", "AM", "11/10/1996", "1:21", "PM");

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Eye from 10/10/10, 10:32 AM until 11/10/96, 1:21 PM"));
    }

    /**
     * Tests that invoking the toString method from appointment class
     * if no option, do nothing
     */
    @Test
    void argsWithoutPrint(){
        MainMethodResult result = invokeMain(Project3.class,"Jimmy", "Eye", "10/10/1110", "10:32", "AM", "11/10/1996", "1:21", "PM");

        assertThat(result.getExitCode(), equalTo(0));
    }

    /**
     * Tests that invoking the main method with file
     * Check if file is empty, check if appointment book is null
     */
    @Test
    void addAnAppointmentBook(@TempDir File dir) throws IOException {

        File textFile = new File(dir, "appointments.txt");

        String description1 = "Appointment 1";
        MainMethodResult result = invokeMain(Project3.class, "-textFile", textFile.getAbsolutePath(), "Owner", description1, "7/7/2021", "10:00", "PM", "7/7/2021", "11:00", "PM");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        String textFileContents = Files.readString(textFile.toPath());
        assertThat(textFileContents, containsString(description1));
    }


    /**
     * Tests that invoking the main method with file
     * If the file name exists, check if appointment book owner is same as argument owner
     */
    @Test
    void fileNameDifferentThanArgName(@TempDir File dir) throws IOException {
        File textFile = new File(dir, "appointments.txt");

        String description1 = "Appointment 1";
        MainMethodResult result = invokeMain(Project3.class, "-textFile", textFile.getAbsolutePath(), "Owner", description1, "7/7/2021", "10:00", "PM", "7/7/2021", "11:00", "PM");
        MainMethodResult re = invokeMain(Project3.class, "-textFile", textFile.getAbsolutePath(), "Jimmy Chan", "Body Check", "5/20/2019", "11:1", "PM", "10/26/2019", "12:21", "PM");

        assertThat(re.getTextWrittenToStandardError(), containsString(OWNER_NAME_NOT_EQUAL));
        assertThat(re.getExitCode(), equalTo(1));
    }

    /**
     * Tests that invoking the main method with file
     * Check if file is empty, check if appointment book is null
     */
    @Test
    void addAnAppointmentBookToPretty(@TempDir File dir) throws IOException {

        File textFile = new File(dir, "appointments.txt");
        File prettyFile = new File(dir, "pretty.txt");
        String description1 = "Appointment 1";
        MainMethodResult result = invokeMain(Project3.class, "-pretty", prettyFile.getAbsolutePath(), "-textFile", textFile.getAbsolutePath(), "Owner", description1, "7/7/2021", "12:00", "aM", "7/7/2021", "01:00", "PM");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        String textFileContents = Files.readString(textFile.toPath());
        String prettyFileContents = Files.readString(prettyFile.toPath());
        assertThat(textFileContents, containsString(description1));
        assertThat(prettyFileContents, containsString("Appointment Book: Owner\tTotal Appointments: 1"));
    }
}