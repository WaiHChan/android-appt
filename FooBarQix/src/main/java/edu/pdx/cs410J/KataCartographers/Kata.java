package edu.pdx.cs410J.KataCartographers;

import static java.lang.Integer.bitCount;
import static java.lang.Integer.parseInt;

/**
 * A class for getting started with a code kata
 *
 * Use IntelliJ's "Refactor | Rename..." command to change the name of this
 * class (and its tests).
 */
public class Kata {
                                                                                    

  public static void main(String[] args) {
    if(args.length == 0) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }
    else {
      System.out.println(compute(args[0]));
      System.exit(1);
    }
  }

  public static String compute(String arg) {
    String originalArg = arg; //for if no match is found at end, STAGE 3
    int argAsInt = parseInt(arg);
    String result = "";
    boolean foundDivisibleMatch = false;
    boolean foundEqualsMatch = false;
    boolean foundZero = false;
    //STAGE 1: Check for divisibility
    //STAGE 1a: Check divisible by 3
    if (argAsInt % 3 == 0) {
      result = result.concat("Foo");
      //foundEqualsMatch = true;
      foundDivisibleMatch = true;
    }
    //STAGE 1b: Check divisible by 5
    if (argAsInt % 5 == 0) {
      result = result.concat("Bar");
      //foundEqualsMatch = true;
      foundDivisibleMatch = true;
    }
    //STAGE 1c: Check divisible by 7
    if (argAsInt % 7 == 0) {
      result = result.concat("Qix");
      //foundEqualsMatch = true;
      foundDivisibleMatch = true;
    }
    //STAGE 2: Check each digit if 3, 5 or 7
    while (arg.length() != 0) {
      //STAGE 2a: Check if 3
      if (arg.charAt(0) == '3') {
        result = result.concat("Foo");
        foundEqualsMatch = true;
      }
      //STAGE 2b: Check if 5
      else if (arg.charAt(0) == '5') {
        result = result.concat("Bar");
        foundEqualsMatch = true;
      }
      //STAGE 2c: Check if 7
      else if (arg.charAt(0) == '7') {
        result = result.concat("Qix");
        foundEqualsMatch = true;
      }
      //STAGE 2d: Check if 0
      else if (arg.charAt(0) == '0') {
        result = result.concat("*");
        foundEqualsMatch = true;
        foundZero = true;
      }
      //STAGE 2e: check if anything else
      //secondary stage for numbers like "101", which should be "1*1"
      //but came out as "*" before
      //ended up being pretty complicated, what with hading to add "foundDivisibleMatch"
      //because of numbers like "10101" which came out as "FooQix*1" before I fixed it
      else if ((foundDivisibleMatch == false) && (foundEqualsMatch == false || foundZero == true)){
        result = result.concat(String.valueOf(arg.charAt(0)));
        //disabling these as a test
        //foundDivisibleMatch = false;
        //foundEqualsMatch = false; //keeping this false for numbers like "101" so we don't get "1*"
        //foundZero = false; //resetting this as a test
      }
      //cutting down arg by one digit
      arg = arg.substring(1);
      //foundDivisibleMatch = false; //resetting this after the first pass
    }
    //STAGE 3: Check if the digit is anything ELSE and NO MATCH WAS FOUND BEFORE
    if(foundDivisibleMatch == false && foundEqualsMatch == false && foundZero == false) {
      //caused a problem with numbers like "11" just outputting "1"
      //result = result.concat(String.valueOf(originalArg.charAt(0)));
      //we need the full string back if there are NO matches
      result = originalArg;
    }
    return result;
  }

}








