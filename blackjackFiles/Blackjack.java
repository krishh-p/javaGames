/* 
	Mika Vohl, Krish Patel, Jeremy Chong
	3/29/2023
	Blackjava.java
	This is the file that will provided all the necessary methods to run Blackjack
 */
import java.util.*;
import java.io.*;

class Blackjack {
  // we store the user's cards and dealer's cards in a 2D array. We have 21 rows (because the max number of cards a player can get dealed in 21), and 2 columns (one for the card suit and one of the card number or name)
  public static String[][] userCards = new String[21][2];
  public static String[][] dealerCards = new String[21][2];
  // set all the game values to 0 at the start of the game
  public static byte userSum = 0, dealerSum = 0, userCardCount = 0, dealerCardCount = 0;
  public static int money = 2500; // user starts off with $2500
  public static int betAmount = 0;
  public static String hitOrStand = null;
  public static boolean lost = false;
  
   /*           Last Edited: Krish Patel --> 11:18 pm, Mar 28, 2023      */
  
  public static void initializeSettings() { // this function is used to initialize all the settings if the user chooses to play again
    for(byte i = 0; i < 21; i++)
      {
        for(byte j = 0; j < 2; j++)
          {
            // resets the user cards and dealer cards (every position in the 2D array becomes null)
            Blackjack.userCards[i][j] = null;
            Blackjack.dealerCards[i][j] = null;
          }
      }
    // resets the sums
    if (Blackjack.money == 0)
      Blackjack.money = 2500;
    Blackjack.userSum = 0;
    Blackjack.dealerSum = 0;
    Blackjack.lost = false;
  }

  public static void getBetAmount() { // method to get the amount the user wants to bet 
    
    Scanner scan = new Scanner(System.in);
    System.out.println("How much do you want to bet?");

    // ensures that the user input is a proper integer
    while(true)
    {
      try {
        Blackjack.betAmount = Integer.parseInt(scan.nextLine());
        break; // exits the loop if input is valid
      }catch (NumberFormatException err) {
        System.out.println(Colours.RED + "Enter a valid whole number! How much do you want to bet?" + Colours.RESET);
      }
    }

    while(Blackjack.betAmount < 0 || Blackjack.betAmount > Blackjack.money)
    {
      if(Blackjack.betAmount < 0)  // if the amount the user bets is less than 0, let the user know and get a valid input
      {
        System.out.println("Your bet is below $0, enter a valid bet! How much do you want to bet?");
        Blackjack.betAmount = Integer.parseInt(scan.nextLine());
      }
      else if(Blackjack.betAmount > Blackjack.money) // // if the amount the user bets is more than the amount of money that they have let the user know and get a valid input
      {
        System.out.println("Your bet is more than you have, enter a valid bet! How much do you want to bet?");
        Blackjack.betAmount = Integer.parseInt(scan.nextLine());
      }
    }
    
  }

  public static void drawCard(String player) throws InterruptedException{ // method used to draw a card, it takes in the player parameter as the same method will be used to draw a card to the dealer and to the user
    Random random = new Random(); // creates a random object from the java.util package
    // we store the number and suit number in bytes as the inputs will not above/below 128/-128
    byte number = 0;
    byte suitNum = 0;
    String suit = null;
    String numberName = null;

    Console.clearConsole();
    System.out.println(player.toUpperCase() + "'S card is being drawn...\n"); // tells the user who's card is being drawn
    Thread.sleep(1200);
    Console.clearConsole();
    Thread.sleep(200);

    // generating the number
    number = (byte)(random.nextInt(13) + 1); // gets a random number between 1 and 13

    // if the number is one, set its name to "A". Aces can either be 1 or 11. We check whether the sum is above or below 11, if its below the Ace is worth 11, if its above, its worth 1 
    if(number == 1)
    {
      numberName = "A";
      if(player.equals("user"))
      {
        if(Blackjack.userSum < 11)
          number = 11;
        else
          number = 1;
      }
      else if(player.equals("dealer"))
      {
        if(Blackjack.dealerSum <= 10)
          number = 11;
        else
          number = 1;
      }
    }

    // numbers 11, 12, 13 represent the Jack, Queen, and King. We set its value to 10 and change its number accordingly
    else if(number == 11)
    {
      number = 10;
      numberName = "J";
    }
    else if(number == 12)
    {
      number = 10;
      numberName = "Q";
    }
    else if(number == 13)
    {
      number = 10;
      numberName = "K";
    }
    else
      numberName = Integer.toString(number);

    //generating the suit

    suitNum = (byte)(random.nextInt(3)); // to get the suit, we get a random number from 0 to 3 to have 4 possible values (for the 4 possible suits)

    // depending on the number, the suit will be different
    if(suitNum == 0)
      suit = Colours.RED+"♥"+Colours.RESET;
    else if(suitNum == 1)
      suit = Colours.RED+"♦"+Colours.RESET;
    else if(suitNum == 2)
      suit = Colours.BLACK+"♣"+Colours.RESET;
    else
      suit = Colours.BLACK+"♠"+Colours.RESET;


    // if the card is being drawn to the user, add the card to the userCards array, increase their sum, and their card count
    if(player.equals("user"))
    {
      Blackjack.userCards[userCardCount][0] = numberName;
      Blackjack.userCards[userCardCount][1] = suit;
      Blackjack.userSum += number;
      Blackjack.userCardCount++;
    }

    // if the card is being drawn to the dealer, add the card to the dealerCards array, increase their sum, and their card count
    else if(player.equals("dealer"))
    {
      Blackjack.dealerCards[dealerCardCount][0] = numberName;
      Blackjack.dealerCards[dealerCardCount][1] = suit;
      Blackjack.dealerSum += number;
      Blackjack.dealerCardCount++;
    }
    
    Blackjack.displayCards(Blackjack.userCards, Blackjack.dealerCards);
  }
  //*********************** KEY FUNCTION: Displays the cards to the console ****************************///
  /*****************************************************************************************************///
  //
  public static void displayCards(String[][] userCards, String[][] dealerCards) throws InterruptedException{ // method used to display all the cards on the table                              ///
                                                                                                        ///
    // this is used to display the dealer's cards                                                       ///
    System.out.println("Dealer Total: "+ Blackjack.dealerSum);                                          ///
    System.out.println("Dealer Cards:");                                                                ///

    // iterates through every row of the dealerCards array and displays the element in that location surrounded by lines to make it look like a card                                                         ///
    for(int x = 0; x < dealerCards.length; x++)                                                         ///
      {                                                                                                 ///
        if(dealerCards[x][0] != null || dealerCards[x][1] != null)                                      ///
        {                                                                                               ///
          if(dealerCards[x][0].equals("10") == false)                                                   ///
          {                                                                                             ///
            System.out.print("|‾‾‾‾|\n| "+dealerCards[x][0]+"  |\n| " + dealerCards[x][1] +"  |\n|____|\n");
          }                                                                                             ///
          else                                                                                          ///
          {                                                                                             ///
            System.out.print("|‾‾‾‾|\n| "+dealerCards[x][0]+" |\n| " + dealerCards[x][1]+ "  |\n|____|\n");
          }                                                                                             ///
        }                                                                                               ///
      }                                                                                                 ///
    System.out.println("\n\n\n‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\n\n");
                                                                                                        ///
    // this is used to display the user's cards                                                         ///
    System.out.println("User Total: "+ Blackjack.userSum);                                              ///
    System.out.println("User Cards:\n");                                                                ///
                                                                                                        ///
    // iterates through every row of the userCards array and displays the element in that location surrounded by lines to make it look like a card
    for(int x = 0; x < userCards.length; x++)                                                           ///
      {                                                                                                 ///
        if(userCards[x][0] != null || userCards[x][1] != null)                                          /// 
        {                                                                                               ///
          if(userCards[x][0].equals("10") == false)                                                     ///
          {                                                                                             ///
            System.out.print("|‾‾‾‾|\n| "+userCards[x][0]+"  |\n| " + userCards[x][1]+ "  |\n|____|\n");///
          }                                                                                             ///
          else                                                                                          ///
          {                                                                                             ///
            System.out.print("|‾‾‾‾|\n| "+userCards[x][0]+" |\n| " + userCards[x][1] + "  |\n|____|\n");///
          }                                                                                             ///
        }                                                                                               ///
      }                                                                                                 ///
    Thread.sleep(2000);                                                                                 ///
  }                                                                                                     ///
                                                                                                        ///
  //******************************************************************************************************/

  
  public static String decision() { // method used to get a hit or stand decision from the user
    String hitOrStand = null;
    Scanner scan = new Scanner(System.in);
    System.out.println("\n\nEnter 'H' to hit 'S' to Stand"); // gets the decisions
    hitOrStand = scan.nextLine().toUpperCase();

    while(!hitOrStand.equals("S") && !hitOrStand.equals("H")) // makes sure the that the user enters H or S, if not, it asks them to enter their decision again
      {
        System.out.println("\nInvalid input, Enter 'H' to hit 'S' to Stand");
        hitOrStand = scan.nextLine().toUpperCase();
      }
    return hitOrStand; // returns the decision
  }

  public static void lose() throws InterruptedException{ // method for if the user loses the round
    Blackjack.lost = true;
    Thread.sleep(1500);
    Console.clearConsole();
    Blackjack.money -= Blackjack.betAmount; // subtracts what the user bet from their money
    System.out.println("You lose, you now have $" + Blackjack.money); // lets the user know they lost and shows how much money they now have
  }
  public static void win() throws InterruptedException{ // method for if the user wins the round
    Thread.sleep(1500);
    Console.clearConsole();
    Blackjack.money += Blackjack.betAmount; // adds what the user bet to their money
    System.out.println("You win, you now have $" + Blackjack.money); // lets the user know they won and shows how much money they now have
  }
  public static void push() throws InterruptedException{ // method for if the user pushes the round
    Thread.sleep(1500);
    Console.clearConsole();
    System.out.println("Push! you still have $" + Blackjack.money); // lets the user know they pushed and shows how much money they still have
  }
}