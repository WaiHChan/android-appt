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
    boolean active = false; //Matt's idea, not quite sure how it'll work yet
    while(arg.length() != 0) {
      //STAGE 1: Check for divisibility
      //STAGE 1a: Check divisible by 3
      if (arg.charAt(0) % 3 == 0){
        active = true;
        result = result.concat("Foo");
        //STAGE 2a: Check if 3
        if (arg.charAt(0) == 3){
          result = result.concat("Foo");
        }
      }
      //STAGE 1b: Check divisible by 5
      if (arg.charAt(0) % 5 == 0){
        active = true;
        result = result.concat("Bar");
        //STAGE 2b: Check if 5
        if (arg.charAt(0) == 5){
          result = result.concat("Bar");
        }
      }
      //STAGE 1c: Check divisible by 7
      if (arg.charAt(0) % 7 == 0){
        active = true;
        result = result.concat("Qix");
        //STAGE 2c: Check if 7
        if (arg.charAt(0) == 7){
          result = result.concat("Qix");
        }
      }
      if(active != true)
      {
          result = result.concat(arg);
      }
      arg = arg.substring(1);
      //if(active)
      //{
      //  arg = arg.substring(1);
      //}
      //else
      //{
      //  arg = arg.substring(1);
      //  result = result.concat(arg);
      //}
    }
    //if(active)
    //{
    //  arg = arg.substring(1);
    //}
    //else
    //{
    //  arg = arg.substring(1);
    //  result = result.concat(arg);
    //}
    return result;
  }
}








