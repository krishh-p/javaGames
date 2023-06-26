/*
  Mika Vohl, Krish Patel, Jeremy Chong
  3/29/2023
	MakeMove.java
	This is the java file that allows the user to make a move and processes the move
*/
import java.util.InputMismatchException;
import java.util.Scanner;

 /*           Last Edited: Mika Vohl --> 2:40 pm, Mar 29, 2023      */

public class MakeMove {
    public static long mTime = 0;
  
    public boolean userMove(byte[][] board, final byte[][] COMPLETEBOARD) throws InterruptedException{
      // initializes variables
      PrintBoard printing = new PrintBoard();
      Scanner scan = new Scanner(System.in);
      String[] coordinates = null;
      String input = null, username = null;
      byte row = 0, col = 0, number = 0;
      int sTime = 0;
      
      Console.clearConsole();

      System.out.println("Your Board");
      printing.print(board);
      System.out.println();
      
      if(boardFull(board)){ // checks if the board is full using a function created below
        // if the board is full displays the winning messages
        Console.clearConsole();
        System.out.println("You won! Here is the completed board:");
        printing.print(board);
        
        //Prompts user whether they want to add their score or not
        do {
          System.out.println("Would you like to add your score to the leaderboards? Y/N");
          input = scan.nextLine().toUpperCase();
          if (!input.equals("Y") && !input.equals("N"))
            System.out.println(Colours.RED + "Enter Y or N!" + Colours.RESET);
        } while(!input.equals("Y") && !input.equals("N"));

        //If the user wants to add their score, add to leaderboard
        if (input.equals("Y")) {
          System.out.println("Enter your username!");
          username = scan.nextLine();
          sTime = getTime(mTime);
          Scoreboard.addScore("sudokuScores.txt", username, sTime);
        }

        //Allows the user to quit the game

        do {
          System.out.println("\nType in Q to leave the game");
          input = scan.nextLine().toUpperCase();
          if (!input.equals("Q"))
            System.out.println(Colours.RED+"Enter a valid input!"+Colours.RESET);
        } while(!input.equals("Q"));
          
        return false;
      }

      //Getting the user's input for the game
      while(true) {
        System.out.println("Enter the coordinate. Example: '1,9' (1 would be the x-coordinate and 9 would be the y-coordinate. 0,0 would be the top left box)");
        input = scan.nextLine().trim();
        //If the input has less/more than 3 characters, it is not valid
        if (input.length() < 3 || input.length() > 3) {
          System.out.println(Colours.RED + "Enter a valid input!" + Colours.RESET);
          continue;
        }

        // Checks if coordinate is a valid input
        if (input.contains(","))
          coordinates = input.split(",");
        else if (input.contains("."))
          coordinates = input.split(".");
        else {
          System.out.println(Colours.RED + "Enter a valid input!" + Colours.RESET);
          continue;
        }
        
        col = (byte)(Byte.parseByte(coordinates[0]) - 1);
        row = (byte)(Byte.parseByte(coordinates[1]) - 1);
        if (coordinates.length != 2 || row < 0 || row > 8 || col < 0 || col > 8)
          System.out.println(Colours.RED + "Enter a valid input!" + Colours.RESET);
        else 
          break;
      }
      
      do {
        System.out.println("Enter the number you would like to enter (1-9).\nEnter B to go back to enter a different coordinate.");
        input = scan.nextLine(); 
        if(input.trim().toLowerCase().equals("b")){ // if the user enters "b", they will be able to enter different coordinates. This works by calling the function again and returning the other one
          userMove(board, COMPLETEBOARD);
          return false;
        }
        if (input.equals("")) // if the input is empty, record it as whitespace
          input = " ";
        if (input.charAt(0) < '1' || input.charAt(0) > '9'|| input.length()>1) // display a console message if invalid number
          System.out.println(Colours.RED + "Enter a number between 1 to 9!" + Colours.RESET);
      } while (input.charAt(0) < '1' || input.charAt(0) > '9' || input.length()>1); // repeat while not a valid input
    
      number = Byte.parseByte(input); // record the number entered by the user as a byte
          
      if(validMove(number, row, col, COMPLETEBOARD)){ // use a function defined in this class to check if the move being made is valid, run the following code if valid
        board[row][col] = number; // set the board to contain the new number
        System.out.println(Colours.GREEN + "Correct!" + Colours.RESET);
        Thread.sleep(1000);
      }
      else{ // if the input is not valid
        System.out.println(Colours.RED + "Wrong move" + Colours.RESET);
        Thread.sleep(1000);
      }

      if(!userMove(board, COMPLETEBOARD)){ // call the userMove function recursively, will continue until the base case is met (board being full)
        return false;
      }
      return true;
    }

    public boolean validMove(byte testInt, byte rowNum, byte colNum, byte[][] solutionBoard){ // method that checks the validity of a given move
      if(testInt == solutionBoard[rowNum][colNum]){
        return true;
      }
      return false;
    }

    public boolean boardFull(byte[][] board){ // method that loops through every index to check if the board is full, returns true if full, false if not
      for(byte i=0; i<9; i++){
        for(byte j=0; j<9; j++){
          if(board[i][j] == 0){
            return false;
          }
        }
      }
      return true;
    }

    public static int getTime(long startTime) { // method that retrieves the time taken to complete the game
      long endTime = System.currentTimeMillis();
      long elapsedTime = endTime - startTime;
      int time = (int) elapsedTime/1000;
      return time;
    }

    public static void setTime(long startTime) {
      mTime = startTime;
    }
}
