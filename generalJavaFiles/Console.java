/* 
	Mika Vohl, Krish Patel, Jeremy Chong
	03/29/2023
	Console.java
  This class resets the console and clears it for the user.
 */

 /*           Last Edited: Jeremy Chong --> 1:45 pm, Mar 27, 2023      */

class Console{
  public static void clearConsole() throws InterruptedException{
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}