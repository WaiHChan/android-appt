package edu.pdx.cs410J.KataCartographers;

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
    int argAsInt = parseInt(arg);
    String result = "";
    boolean foundMatch = false;
    //STAGE 1: Check for divisibility
    //STAGE 1a: Check divisible by 3
    if (argAsInt % 3 == 0) {
      result = result.concat("Foo");
      foundMatch = true;
    }
    //STAGE 1b: Check divisible by 5
    if (argAsInt % 5 == 0) {
      result = result.concat("Bar");
      foundMatch = true;
    }
    //STAGE 1c: Check divisible by 7
    if (argAsInt % 7 == 0) {
      result = result.concat("Qix");
      foundMatch = true;
    }
    //STAGE 2: Check each digit if 3, 5 or 7
    while (arg.length() != 0) {
      //STAGE 2a: Check if 3
      if (arg.charAt(0) == '3') {
        result = result.concat("Foo");
        foundMatch = true;
      }
      //STAGE 2b: Check if 5
      else if (arg.charAt(0) == '5') {
        result = result.concat("Bar");
        foundMatch = true;
      }
      //STAGE 2c: Check if 7
      else if (arg.charAt(0) == '7') {
        result = result.concat("Qix");
        foundMatch = true;
      }
      //STAGE 2d: Check if 0
      else if (arg.charAt(0) == '0') {
        result = result.concat("*");
        foundMatch = true;
      }
      //STAGE 2e: Check if anything ELSE and NO MATCH FOUND BEFORE
      else if (foundMatch == false){
        result = result.concat(String.valueOf(arg.charAt(0)));
      }
      //cutting down arg by one digit
      arg = arg.substring(1);
    }
    return result;
  }

}








