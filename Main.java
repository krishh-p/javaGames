/* 
	Mika Vohl, Krish Patel, Jeremy Chong
	3/29/2023
	Main.java
	This is the main file that will execute each one the games as well as ask the user what action they want to take (i.e. play blackjack, view leaderboard, etc)
 */

import java.util.*;


class Main {
  public static boolean gameFinished = false; //we store a public variable called "gameFinished" to track when the user is done playing the specific game

  /*           Last Edited: Krish Patel --> 11:34 am, Mar 27, 2023       */

  public static void main(String[] args) throws InterruptedException { // this is the function that will act as the menu for the user. It will allow them to choose to play sudoku, wordle, blackjack, view the leaderboards, or quit the game 
    byte option = 0; // we stored the option in a byte as the user must enter a value between 1 and 5.
    Scanner scan = new Scanner(System.in); // creates the scanner object to intake user input
    boolean arcadeFinished = false;

    // gives the code to the user for how they can access each game
    do {
      Console.clearConsole();
      System.out.println("Welcome to our arcade! Enter the number for the game you would like to play! (i.e type 1 for Sudoku)");
      System.out.println("1 = Sudoku");
      System.out.println("2 = Wordle");
      System.out.println("3 = Blackjack");
      System.out.println("4 = Leaderboards");
      System.out.println("5 = Quit");
  
      while(true) // this while loop ensures that the user is entering a number between 1 and 5. We had to use a try catch to catch for the instances where the user enters a number outside of -128 to 128 range or a string
      {
        try {
          option = Byte.parseByte(scan.nextLine());
  
          while(option < 1 || option > 5)
            {
              System.out.println("You must enter either 1, 2, 3, 4, or 5!");
              option = Byte.parseByte(scan.nextLine());
            }
          break;
        }catch (NumberFormatException err) {
          System.out.println("Enter a valid number!");
        }

        // depending on what the user enters, a different action will occu according to the code listed above

        /*           Last Edited: Jeremy Chong --> 7:03 pm, Mar 27, 2023       */
      }
      switch (option) {
        case 1:
          Console.clearConsole();
          playSudoku();
          break;
        case 2:
          Console.clearConsole();
          playWordle();
          break;
        case 3:
          Console.clearConsole();
          playBlackjack();
          break;
        case 4:
          Console.clearConsole();
          enterLeaderboard();
          break;
        case 5:
          arcadeFinished = true;
          System.out.println("Thanks for playing, see you soon!");
          break;
      }
            
    } while(!arcadeFinished);
    
  }
  // this is the function used to open the leaderboard. This function will be called if the user enters "4" in the menu

    /*           Last Edited: Jeremy Chong --> 6:12 pm, Mar 27, 2023       */
  
  public static void enterLeaderboard() { // method used to view the leaderboard
    String decision = null;
    Scanner sc = new Scanner(System.in);
    
    Scoreboard.print("wordleScores.txt", "Wordle"); // this will call on the "print" method in the public Scoreboard class, which will display the wordle scoreboard
    System.out.println();
    Scoreboard.print("sudokuScores.txt", "Sudoku"); // this will call on the same method, but display the sudoku scores instead (determined by the parameters given).

    // this do while loop is used to allow the user to stop viewing the leaderboard and go back to the menu
    do {
      System.out.println("\nType in Q to leave the leaderboards");
      decision = sc.nextLine().toUpperCase();
      if (!decision.equals("Q"))
        System.out.println(Colours.RED + "Enter a valid input!" + Colours.RESET);
    } while(!decision.equals("Q"));
  }

  /*           Last Edited: Mika Vohl --> 5:19 pm, Mar 28, 2023       */
  
  public static void playSudoku() throws InterruptedException{ // method used to play Sudoku
    Scanner scan = new Scanner(System.in);
    CreateBoard board = new CreateBoard(); // Creates instance of CreateBoard class
    PrintBoard printer = new PrintBoard();
    UserBoard user = new UserBoard();
    MakeMove moveMaker = new MakeMove();
    String decision = null;
    long mTime = 0;

    // The following gets user input regarding sudoku difficulty. The input is stored as a long to prevent an error, after this the long is converted to a byte after being validated to save memory going forward
    long inputDifficulty = 1;
    System.out.println("What difficulty would you like to play on? (1 = easy, 5 = hard)");
    do{
      inputDifficulty = scan.nextLong();
      if(inputDifficulty > 5 || inputDifficulty < 1)
        System.out.println(Colours.RED+"Difficulty must be a number from 1-5"+Colours.RESET);
    }while(inputDifficulty > 5 || inputDifficulty < 1);
    byte userDifficulty = (byte)inputDifficulty;

    board.fillBoard(); // calls fillboard method of createboard class

    MakeMove.setTime(System.currentTimeMillis());
    final byte[][] BOARDANSWERS = board.board;

    // difficulty from 1-5
    byte[][] tempBoard = user.blankBoard(BOARDANSWERS, userDifficulty);
    byte[][] currentBoard = new byte[9][9];
    for(byte i=0; i<9; i++){
      for(byte j=0; j<9; j++){
        currentBoard[i][j] = tempBoard[i][j];
      }
    }
    
    moveMaker.userMove(currentBoard, BOARDANSWERS);
  }

   /*           Last Edited: Krish Patel --> 2:50 pm, Mar 23, 2023       */
  
  public static void playWordle() { // method used to play Wordle
    Scanner scan = new Scanner(System.in); // creates scanner object for user input
    String decision = null, username = null; // resets the username and the user's choice whether they want to add their score to the leaderboard
    UserInput inObject = new UserInput(); // creates an object for UserInput so we can use the methods we created in that class in this game

    // the following resets all the board components to ensure there are no errors when the user plays the game again
    gameFinished = false;
    UserInput.reset();
    Board.fillBoard();

    // the following code will continue to loop until the user enters 6 words or they solve the board
    while (UserInput.wordsEntered < 5 && !Board.solved) {
      inObject.input(); // this uses the input method we created, which allows the user to input a word
      Board.outputBoard(); // this uses the outputBoard method we created, which outputs the board for the user to see

      // if the user gets the correct word
      if (Board.solved) {
        // it tells the user their got the word and the time it took to get it
        System.out.println();
        System.out.println("Congratulations, you solved it!");
        System.out.println("You took " + Board.getTime() + " seconds to finish the game");
        // asks the user if they want to add their score to the leaderboard, uses the loop to ensure correct input is entered
        do {
          System.out.println("Would you like to add your score? Y/N");
          decision = scan.nextLine().toUpperCase();
          if (!decision.equals("Y") && !decision.equals("N"))
            System.out.println(Colours.RED+"Enter a valid input!"+Colours.RESET);
        } while(!decision.equals("Y") && !decision.equals("N"));

        // if they want to enter it, it asks for the username and adds the score using the addScore method we created, which adds the time and corresponding username to the text file
        if (decision.equals("Y")) {
          System.out.println("Enter your username!");
          username = scan.nextLine();
          Scoreboard.addScore("wordleScores.txt", username, Board.endTime);
        }
      }
      System.out.println();
    }

    // if the user is unable to get the correct word in 6 tries
    if (!Board.solved)
    {
      // tell the user that they did not solve the word and lets them know what the word was
      System.out.println();
      System.out.println(Colours.RED+"Not solved"+Colours.RESET+"\nThe word was \u001B[1m" + Board.randomWord + Colours.RESET);
    }

    gameFinished = true;

    // allows user to go back to the menu
    do {
      System.out.println("\nType in Q to leave the game");
      decision = scan.nextLine().toUpperCase();
      if (!decision.equals("Q"))
        System.out.println(Colours.RED+"Enter a valid input!"+Colours.RESET);
    } while(!decision.equals("Q"));
  }

   /*           Last Edited: Krish Patel --> 10:05 am, Mar 27, 2023       */

  public static void playBlackjack() throws InterruptedException{ // method used to play Blackjack
    String decision = null, quit = null; // resets the user's decision to hit or stand
    Scanner scan = new Scanner(System.in); // creates scanner object to get user input
    gameFinished = false;
    
    do {
      Blackjack.initializeSettings(); // resets all the cards and sums in the previous game played
      Console.clearConsole();
      System.out.println("You have $" + Blackjack.money);

      //gets the amount of money the user wants to bet by using the getBetAmount method we created
      Blackjack.getBetAmount();

      // standard first three moves in blackjack (user gets a card, then dealer, then user again)
      Blackjack.drawCard("user");
      Blackjack.drawCard("dealer");
      Blackjack.drawCard("user");
  
      decision = "H"; // set decision to "H" to start the while loop
      
      while(decision.equals("H"))
        {
          decision = Blackjack.decision(); // gets whether the user wants to hit or stand
          if(decision.equals("H"))
          {
            Blackjack.drawCard("user"); // if they want to hit, draw a card for the user
            if(Blackjack.userSum > 21)
            {
              Blackjack.lose(); // if the user busts (above 21), they lose and the loop breaks
              break;
            }
          }
          if(Blackjack.userSum == 21)
          {
            break; // break the loop once the user has a sum of 21
          }
        }
  
      if(!Blackjack.lost) // if the user has not lost yet (didn't bust)
      {
        while(Blackjack.dealerSum < 17)
        {
          Blackjack.drawCard("dealer"); // continue drawing cards to the dealer until they reach a sum of 17 or above
        }
  
        if(Blackjack.dealerSum > 21)
        {
          Blackjack.win(); // if the dealer busts, the user wins
        }
        else if(Blackjack.dealerSum > Blackjack.userSum)
        {
          Blackjack.lose(); // if the dealer didn't bust and has a higher sum than the user, the user loses
        }
        else if(Blackjack.userSum > Blackjack.dealerSum)
        {
          Blackjack.win(); // if user has a higher sum than the dealer, the user wins
        }
        else
          Blackjack.push(); // if the sums are equal, its a push and nobody wins
      }

      if(Blackjack.money == 0)
      {
        // if the user runs out of money, the game is over
        gameFinished = true;
        System.out.println(Colours.RED + "You lost all your money, better luck next time!" + Colours.RESET);
        //Allows the user to quit the game
        do {
          System.out.println("\nType in Q to exit!");
          quit = scan.nextLine().toUpperCase();
          if (!quit.equals("Q"))
            System.out.println(Colours.RED + "Enter a valid input!" + Colours.RESET);
        } while(!quit.equals("Q"));
        
      }
      else {
        // used to ask the user if they would like to make again after each game
        do {
          System.out.println("Would you like to play again? Y/N (Y for Yes, N for No)");
          decision = scan.nextLine().toUpperCase();
          if (!decision.equals("Y") && !decision.equals("N"))
            System.out.println("Please enter a valid option");
        } while(!decision.equals("Y") && !decision.equals("N"));
    
        if (decision.equals("N")) {
          gameFinished = true; 
          System.out.println("You finished with $" + Blackjack.money);
          System.out.println("See you soon!");
        }
      }
    } while(!gameFinished);
  }

}