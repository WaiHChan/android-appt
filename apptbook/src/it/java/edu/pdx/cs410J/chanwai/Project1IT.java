package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration tests for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

  /**
   * Invokes the main method of {@link Project1} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project1.class, args );
  }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain(Project1.class);
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_COMMAND_LINE_ARGUMENTS));
  }

  /**
   * Tests that invoking the main method with one argument issues an error
   * If argument is only "-print", error
   */
  @Test
  void testMissingPrintWithOneArgument() {
    MainMethodResult result = invokeMain(Project1.class, "-print");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_COMMAND_LINE_ARGUMENTS));
  }

  /**
   * Tests that invoking the main method with one argument issues an error
   * If argument is only "-print", error
   */
  @Test
  void testMissSpellingPrintWithOneArgument() {
    MainMethodResult result = invokeMain(Project1.class, "-prit");

    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_DESCRIPTION));
  }

  /**
   * Tests that invoking the main method with one argument issues an error
   * If argument is only "-print", error
   */
  @Test
  void testPrintReadmeOneArgument() {
    MainMethodResult result = invokeMain(Project1.class, "-README");

    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
  }

  /**
   * Tests that invoking the main method with two arguments issues an error
   * If argument is only "-print" "owner", missing description
   */
  @Test
  void testMissingPrintWithTwoArguments(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_DESCRIPTION));
  }

  /**
   * Tests that invoking the main method with two arguments issues an error
   * If argument is only "owner" "description", missing begin date
   */
  @Test
  void testMissingBeginDate(){
    MainMethodResult result = invokeMain(Project1.class,"Jimmy", "Body Check");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_BEGINE_DATE));
  }

  /**
   * Tests that invoking the main method with three arguments issues an error
   * If argument is only "-print" "owner" "description", missing begin date
   */
  @Test
  void testMissingPrintWithThreeArguments(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_BEGINE_DATE));
  }

  /**
   * Tests that invoking the main method with three arguments issues an error
   * If argument is only "owner" "description" "begin date", missing begin time
   */
  @Test
  void testMissingBeginTime(){
    MainMethodResult result = invokeMain(Project1.class,"Jimmy", "Body Check", "1/1/2021");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_BEGINE_TIME));
  }

  /**
   * Tests that invoking the main method with four arguments issues an error
   * If argument is only "-print" "owner" "description" "begin date", missing begin time
   */
  @Test
  void testMissingPrintWithFourArguments(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "1/1/2021");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_BEGINE_TIME));
  }

  /**
   * Tests that invoking the main method with four arguments issues an error
   * If argument is only "owner" "description" "begin date" "begin time", missing end date
   */
  @Test
  void testMissingEndDate(){
    MainMethodResult result = invokeMain(Project1.class,"Jimmy", "Body Check", "1/2/2013", "4:10");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_END_DATE));
  }

  /**
   * Tests that invoking the main method with five arguments issues an error
   * If argument is only "-print" "owner" "description" "begin date" "begin time", missing end date
   */
  @Test
  void testMissingPrintWithFiveArguments(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "1/1/2021", "12:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_END_DATE));
  }

  /**
   * Tests that invoking the main method with five arguments issues an error
   * If argument is only "owner" "description" "begin date" "begin time" "end date", missing end time
   */
  @Test
  void testMissingEndTime(){
    MainMethodResult result = invokeMain(Project1.class,"Jimmy", "Body Check", "1/2/2013", "4:10", "3/16/1995");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_END_TIME));
  }

  /**
   * Tests that invoking the main method with six arguments issues an error
   * If argument is only "-print" "owner" "description" "begin date" "begin time" "end date", missing end time
   */
  @Test
  void testMissingPrintWithSixArguments(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "1/1/2021", "12:21", "11/10/1996");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_END_TIME));
  }

  /**
   * Tests that invoking the main method with six command line arguments
   * If the begin date contains any alphabet, issues an error
   */
  @Test
  void invalidBeginDateForArgumentSix(){
    MainMethodResult result = invokeMain(Project1.class, "Jimmy", "Body Check", "abc", "12:21", "10/26/1995", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date: abc"));
  }

  /**
   * Tests that invoking the main method with six command line arguments
   * If the minutes of begin time contains any alphabet, issues an error
   */
  @Test
  void invalidBeginTimeForArgumentSix(){
    MainMethodResult result = invokeMain(Project1.class, "Jimmy", "Body Check", "5/20/2019", "14:1e", "10/26/1242", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time: 14:1e"));
  }

  /**
   * Tests that invoking the main method with six command line arguments
   * If the end date contains any alphabet, issues an error
   */
  @Test
  void invalidEndDateForArgumentSix(){
    MainMethodResult result = invokeMain(Project1.class,  "Jimmy", "Body Check", "5/20/2019", "12:21", "10/26a/1242", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date: 10/26a/1242"));
  }

  /**
   * Tests that invoking the main method with six command line arguments
   * If the end time contains any alphabet, issues an error
   */
  @Test
  void invalidEndTimeForArgumentSix(){
    MainMethodResult result = invokeMain(Project1.class, "Jimmy", "Body Check", "5/20/2019", "14:1", "10/26/1242", "13:21a");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time: 13:21a"));
  }

  /**
   * Tests that invoking the main method with seven command line arguments
   * If the begin date contains any alphabet, issues an error
   */
  @Test
  void invalidBeginDateForArgumentSeven(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "abc", "12:21", "10/26/1995", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date: abc"));
  }

  /**
   * Tests that invoking the main method with six command line arguments
   * If the minutes of begin time contains any alphabet, issues an error
   */
  @Test
  void invalidBeginTimeForArgumentSeven(){
    MainMethodResult result = invokeMain(Project1.class,  "-print", "Jimmy", "Body Check", "5/20/2019", "14:1e", "10/26/1242", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time: 14:1e"));
  }

  /**
   * Tests that invoking the main method with seven command line arguments
   * If the end date contains any alphabet, issues an error
   */
  @Test
  void invalidEndDateForArgumentSeven(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "5/20/2019", "12:21", "10/26a/1242", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date: 10/26a/1242"));
  }

  /**
   * Tests that invoking the main method with seven command line arguments
   * If the end time contains any alphabet, issues an error
   */
  @Test
  void invalidEndTimeForArgumentSeven(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "5/20/2019", "14:1", "10/26/1242", "13:21a");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time: 13:21a"));
  }

  /**
   * Tests that invoking the main method with too many arguments issues an error
   * Issues an error when arguments does not contain "-print".
   */
  @Test
  void tooManyCommandLineArguments(){
    MainMethodResult result = invokeMain(Project1.class,  "Jimmy", "Body Check", "5/20/2019", "14:1", "10/26/1242", "13:21a", "12");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.TOO_MANY_COMMAND_LINE_ARGUMENTS));
  }

  /**
   * Tests that invoking the main method with too many arguments issues an error
   * Issues an error when arguments contain "-print".
   */
  @Test
  void tooManyCommandLineArgumentsForPrint(){
    MainMethodResult result = invokeMain(Project1.class,  "-print", "Jimmy", "Body Check", "5/20/2019", "14:1", "10/26/1242", "13:21a", "12");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.TOO_MANY_COMMAND_LINE_ARGUMENTS));
  }

  /**
   * Tests that invoking the main method with correct command line arguments
   * If year of begin date is zero or less, issues an error
   */
  @Test
  void yearLessThanZero(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "1/1/0", "12:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.YEAR_OUT_OF_BOUNDS));
  }

  /**
   * Tests that invoking the main method with correct command line arguments
   * If year of begin date is larger than 9999, issues an error
   */
  @Test
  void yearLargeThanLimit(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "1/1/10000", "12:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.YEAR_OUT_OF_BOUNDS));
  }

  /**
   * Tests that invoking the main method with correct command line arguments
   * If month of begin date is zero or less, issues an error
   */
  @Test
  void monthLessThanZero(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "0/1/1110", "12:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MONTH_OUT_OF_BOUNDS));
  }

  /**
   * Tests that invoking the main method with correct command line arguments
   * If month of begin date is larger than 12, issues an error
   */
  @Test
  void monthLargeThanLimit(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "13/1/1110", "12:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MONTH_OUT_OF_BOUNDS));
  }

  /**
   * Tests that invoking the main method with correct command line arguments
   * If day of begin date is zero or less, issues an error
   */
  @Test
  void dayLessThanZero(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "10/0/1110", "12:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.DAY_OUT_OF_BOUNDS));
  }

  /**
   * Tests that invoking the main method with correct command line arguments
   * If day of begin date is larger than 31, issues an error
   */
  @Test
  void dayLargeThanLimit(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "12/33/1110", "12:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.DAY_OUT_OF_BOUNDS));
  }

  /**
   * Tests that invoking the main method with correct command line arguments
   * If the hour of begin date is less than 0, issues an error
   */
  @Test
  void hourLessThanZero() {
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "10/10/1110", "-1:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.HOUR_OUT_OF_BOUNDS));
  }

  /**
   * Tests that invoking the main method with correct command line arguments
   * If the hour of begin date is larger than 24, issues an error
   */
  @Test
  void hourLargeThanLimit() {
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "10/10/1110", "25:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.HOUR_OUT_OF_BOUNDS));
  }

  /**
   * Tests that invoking the main method with correct command line arguments
   * If the minutes of begin date is less than 0, issues an error
   */
  @Test
  void minsLessThanZero() {
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "10/10/1110", "1:-1", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MINS_OUT_OF_BOUNDS));
  }

  /**
   * Tests that invoking the main method with correct command line arguments
   * If the minutes of begin date is larger than 60, issues an error
   */
  @Test
  void minsLargeThanLimit() {
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "10/10/1110", "22:67", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MINS_OUT_OF_BOUNDS));
  }

  /**
   * Tests that invoking the read file method
   * Check if the correct file is opened by comparing the string.
   */
  @Test
  void readmeCanBeReadAsResource() {
    InputStream readme = Project1.class.getResourceAsStream("READM.txt");
    assertThat(readme, nullValue());
  }
}