package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
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

  @Test
  void testMissingPrintArguments() {
    MainMethodResult result = invokeMain(Project1.class, "-print");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_COMMAND_LINE_ARGUMENTS));
  }

  @Test
  void testMissingDescription(){
    MainMethodResult result = invokeMain(Project1.class, "Jimmy");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_DESCRIPTION));
  }

  @Test
  void testMissingPrintWithTwoArguments(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_DESCRIPTION));
  }

  @Test
  void testMissingBeginDate(){
    MainMethodResult result = invokeMain(Project1.class,"Jimmy", "Body Check");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_BEGINE_DATE));
  }

  @Test
  void testMissingPrintWithThreeArguments(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_BEGINE_DATE));
  }

  @Test
  void testMissingBeginTime(){
    MainMethodResult result = invokeMain(Project1.class,"Jimmy", "Body Check", "1/1/2021");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_BEGINE_TIME));
  }

  @Test
  void testMissingPrintWithFourArguments(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "1/1/2021");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_BEGINE_TIME));
  }

  @Test
  void testMissingEndDate(){
    MainMethodResult result = invokeMain(Project1.class,"Jimmy", "Body Check", "1/2/2013", "4:10");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_END_DATE));
  }

  @Test
  void testMissingPrintWithFiveArguments(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "1/1/2021", "12:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_END_DATE));
  }

  @Test
  void testMissingEndTime(){
    MainMethodResult result = invokeMain(Project1.class,"Jimmy", "Body Check", "1/2/2013", "4:10", "3/16/1995");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_END_TIME));
  }

  @Test
  void testMissingPrintWithSixArguments(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "1/1/2021", "12:21", "11/10/1996");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_END_TIME));
  }

  @Test
  void yearLessThanZero(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "1/1/0", "12:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.YEAR_OUT_OF_BOUNDS));
  }

  @Test
  void yearLargeThanZero(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "1/1/10000", "12:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.YEAR_OUT_OF_BOUNDS));
  }

  @Test
  void monthLessThanZero(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "0/1/1110", "12:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MONTH_OUT_OF_BOUNDS));
  }

  @Test
  void monthLargeThanZero(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "13/1/1110", "12:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MONTH_OUT_OF_BOUNDS));
  }

  @Test
  void dayLessThanZero(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "10/0/1110", "12:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.DAY_OUT_OF_BOUNDS));
  }

  @Test
  void dayLargeThanZero(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "12/33/1110", "12:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.DAY_OUT_OF_BOUNDS));
  }

  @Test
  void invalidBeginDateForArgumentSix(){
    MainMethodResult result = invokeMain(Project1.class, "Jimmy", "Body Check", "abc", "12:21", "10/26/1995", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date: abc"));
  }

  @Test
  void invalidEndDateForArgumentSix(){
    MainMethodResult result = invokeMain(Project1.class,  "Jimmy", "Body Check", "5/20/2019", "12:21", "10/26a/1242", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date: 10/26a/1242"));
  }

  @Test
  void invalidBeginDateForArgumentSeven(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "abc", "12:21", "10/26/1995", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date: abc"));
  }

  @Test
  void invalidEndDateForArgumentSeven(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "5/20/2019", "12:21", "10/26a/1242", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date: 10/26a/1242"));
  }

  @Test
  void hourLessThanZero() {
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "10/10/1110", "-1:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.HOUR_OUT_OF_BOUNDS));
  }

  @Test
  void hourLargeThanZero() {
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "10/10/1110", "25:21", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.HOUR_OUT_OF_BOUNDS));
  }

  @Test
  void minsLessThanZero() {
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "10/10/1110", "1:-1", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MINS_OUT_OF_BOUNDS));
  }

  @Test
  void minsLargeThanZero() {
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "10/10/1110", "22:67", "11/10/1996", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MINS_OUT_OF_BOUNDS));
  }

  @Test
  void invalidBeginTimeForArgumentSix(){
    MainMethodResult result = invokeMain(Project1.class, "Jimmy", "Body Check", "5/20/2019", "14:1e", "10/26/1242", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time: 14:1e"));
  }

  @Test
  void invalidEndTimeForArgumentSix(){
    MainMethodResult result = invokeMain(Project1.class, "Jimmy", "Body Check", "5/20/2019", "14:1", "10/26/1242", "13:21a");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time: 13:21a"));
  }

  @Test
  void invalidBeginTimeForArgumentSeven(){
    MainMethodResult result = invokeMain(Project1.class,  "-print", "Jimmy", "Body Check", "5/20/2019", "14:1e", "10/26/1242", "13:21");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time: 14:1e"));
  }

  @Test
  void invalidEndTimeForArgumentSeven(){
    MainMethodResult result = invokeMain(Project1.class, "-print", "Jimmy", "Body Check", "5/20/2019", "14:1", "10/26/1242", "13:21a");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time: 13:21a"));
  }

  @Test
  void tooManyCommandLineArguments(){
    MainMethodResult result = invokeMain(Project1.class,  "Jimmy", "Body Check", "5/20/2019", "14:1", "10/26/1242", "13:21a", "12");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.TOO_MANY_COMMAND_LINE_ARGUMENTS));
  }



}