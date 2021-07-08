package edu.pdx.cs410J.KataCartographers;

/**
 * A class for getting started with a code kata
 *
 * Use IntelliJ's "Refactor | Rename..." command to change the name of this
 * class (and its tests).
 */
public class Kata {
                                                                                    

  public static void main(String[] args) {
    //System.err.println("Missing command line arguments");
    System.out.println(compute(args[0]));
    System.exit(1);
  }

  public static String compute(String arg) {
    String result = "";
    int i;
    boolean active = false; //Matt's idea, not quite sure how it'll work yet
    try {
      i = Integer.parseInt(arg);
      if (i % 3 == 0) {
        active = true;
        result = result.concat("Foo");
        //STAGE 2a: Check if
        if (arg.indexOf('3') >= 0) {
          result = result.concat("Foo");
        }
      }
      //STAGE 1b: Check divisible by 5
      if (i % 5 == 0) {
        active = true;
        result = result.concat("Bar");
        //STAGE 2b: Check if 5
        if (arg.indexOf('5') >= 0) {
          result = result.concat("Bar");
        }
      }
      //STAGE 1c: Check divisible by 7
      if (i % 7 == 0) {
        active = true;
        result = result.concat("Qix");
        //STAGE 2c: Check if 7
        if (arg.indexOf('7') >= 0) {
          result = result.concat("Qix");
        }
      }
      if (active != true) {
        result = result.concat(arg);
      }
    } catch (NumberFormatException e) {
      System.err.println("not a number");
    }
    return result;
  }

}








