package edu.pdx.cs410J.KataCartographers;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;

class KataIT extends InvokeMainTestCase {

  /**
   * Invokes the main method of {@link Kata} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain(Kata.class, args);
  }

  @Test
  void invokingMainWithNoArgumentsHasExitCodeOf1() {
    InvokeMainTestCase.MainMethodResult result = invokeMain(Kata.class);
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void invokingMainWithNoArgumentsPrintsMissingArgumentsToStandardError() {
    InvokeMainTestCase.MainMethodResult result = invokeMain(Kata.class);
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  /**
   * Series of tests following those at: https://codingdojo.org/kata/FooBarQix/
   */
  @Test
  void test1() {
    MainMethodResult result = invokeMain("1");
    assertThat(result.getTextWrittenToStandardOut(), containsString("1"));
    result = invokeMain("2");
    assertThat(result.getTextWrittenToStandardOut(), containsString("2"));
    result = invokeMain("3");
    assertThat(result.getTextWrittenToStandardOut(), containsString("FooFoo"));
    result = invokeMain("4");
    assertThat(result.getTextWrittenToStandardOut(), containsString("4"));
    result = invokeMain("5");
    assertThat(result.getTextWrittenToStandardOut(), containsString("BarBar"));
    result = invokeMain("6");
    assertThat(result.getTextWrittenToStandardOut(), containsString("Foo"));
    result = invokeMain("7");
    assertThat(result.getTextWrittenToStandardOut(), containsString("QixQix"));
    result = invokeMain("8");
    assertThat(result.getTextWrittenToStandardOut(), containsString("8"));
    result = invokeMain("9");
    assertThat(result.getTextWrittenToStandardOut(), containsString("Foo"));

    //this one below still passes because the output is "Bar*" which DOES contain the
    //substring "Bar", but in some instances tests matching the style of the examples
    //for Step 1 will probably not work (since it doesn't account for turning 0 into *)
    //(in other words, the program matches the step 2 functionality which is slightly
    //different than the step 1 functionality, which would do nothing with the 0)
    result = invokeMain("10");
    assertThat(result.getTextWrittenToStandardOut(), containsString("Bar"));
    result = invokeMain("13");
    assertThat(result.getTextWrittenToStandardOut(), containsString("Foo"));
    result = invokeMain("15");
    assertThat(result.getTextWrittenToStandardOut(), containsString("FooBarBar"));
    result = invokeMain("21");
    assertThat(result.getTextWrittenToStandardOut(), containsString("FooQix"));
    result = invokeMain("33");
    assertThat(result.getTextWrittenToStandardOut(), containsString("FooFooFoo"));
    result = invokeMain("51");
    assertThat(result.getTextWrittenToStandardOut(), containsString("FooBar"));
    result = invokeMain("53");
    assertThat(result.getTextWrittenToStandardOut(), containsString("BarFoo"));

    //Step2 tests
    result = invokeMain("101");
    assertThat(result.getTextWrittenToStandardOut(), containsString("1*1"));
    result = invokeMain("303");
    assertThat(result.getTextWrittenToStandardOut(), containsString("FooFoo*Foo"));
    result = invokeMain("105");
    assertThat(result.getTextWrittenToStandardOut(), containsString("FooBarQix*Bar"));
    result = invokeMain("10101");
    assertThat(result.getTextWrittenToStandardOut(), containsString("FooQix*"));
  }

}
