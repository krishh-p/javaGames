/*
  Mika Vohl, Krish Patel, Jeremy Chong
  3/29/2023
	Board.java
	This is the java file which creates the wordle board and processes user input
*/
import java.util.*;
import java.io.*;

 /*           Last Edited: Krish Patel --> 11:18 pm, Mar 28, 2023      */

class Board {
  // board 2d array
  static String[][] board = new String[6][5];

  public static boolean solved = false;
  public static long startTime = System.currentTimeMillis();
  // Starts the timer when the user starts playing
  public static int endTime = 0;

  public static String generateWord() { // method to generate the word that would be the answer to the Wordle game
    String finalWord = null;
    try {
      BufferedReader br = new BufferedReader(new FileReader("words.txt")); // creates a reader object to read from the words.txt file. This files contains the possible words that the answer could be
      String word = null;

      // gets the number of words in the words.txt
      short lines = 0;
      do{
        word = br.readLine();
        if(word != null)
          lines++;
      }while(word != null);
      
      br.close(); // closes the reader


      br = new BufferedReader(new FileReader("words.txt")); // reopens the reader

      // adds all the possible answers to the allWordsArray array
      String[] allWordsArray = new String[lines];
      int currLine = 0;
      
      do{
        word = br.readLine();
        if(word != null)
        {
          allWordsArray[currLine] = word;
          currLine++;
        }
      }while(word != null);
      
      br.close(); // closes the reader
      
      Random random = new Random(); // creates a random object from java.util

      finalWord = allWordsArray[random.nextInt(lines)]; // gets a random word from the array 
      
    }catch(IOException ex) {
      System.out.println(ex.getMessage());
    }
    return finalWord.toUpperCase(); // returns the word that was randomly chosen in uppercase
  }

  public static String randomWord = Board.generateWord(); // makes that word into a public variable that can be accessed throughout the project
  
  /******************************   KEY FEATURE: Adding user's guess  ***********************************************//////
  /***********************************************************************************************************************/
  public static void addWord(String userInput, int currWordNum){ // method that adds the user's guess to the array, it takes in the user's input and the number of words they have entered thus far                                            ///
                                                                                                                        ///
    if(userInput.equals(Board.randomWord)) // if the user correctly enters the word, set solved to true                 ///
      solved = true;                                                                                                    ///
                                                                                                                        ///
    String[] wordToAdd = userInput.split(""); // splits the user's guess into an array of characters                    ///
    String[] actualWord = Board.randomWord.split(""); // splits the actual word into an array of characters             ///
    boolean containsValue = false;                                                                                      ///
                                                                                                                        ///
    // Nested for loop to check the each character of both the user input and the actual word. If the character in the correct spot, it sets the containsValue to be true, which means the letter entered is in the actual word                ///
                                                                                                                        ///
    for(byte i = 0; i < 5; i++)                                                                                         ///
    {                                                                                                                   ///
      containsValue = false;                                                                                            ///
      for(byte j = 0; j < actualWord.length; j++)                                                                       ///
      {                                                                                                                 ///
        if(actualWord[j].equals(wordToAdd[i]))                                                                          ///
        {                                                                                                               ///
          containsValue = true;                                                                                         ///
          break;                                                                                                        ///
        }                                                                                                               ///
      }                                                                                                                 ///
                                                                                                                        ///
      if(wordToAdd[i].equals(actualWord[i])) // if the letter is in the correct spot                                    ///
        board[currWordNum][i] = wordToAdd[i] + "<"; // it adds that letter to the board and adds a < character to the letter, which will help us with the colours later on                                                                    ///
      else if(containsValue) // if the letter is not in the correct spot but its in the word                            ///
        board[currWordNum][i] = wordToAdd[i] + ">"; // it adds that letter to the board and adds a > character to the letter, which will help us with the colours later on                                                                    ///
      else // if neither of the above are true, it keeps the letter the regular colour and adds the letter to the board ///
        board[currWordNum][i] = wordToAdd[i];                                                                           ///
    }                                                                                                                   ///
  }                                                                                                                     ///
  /**********************************************************************************************************************/

  public static void fillBoard() { // this method fills the board with empty boxes, we use this in the beginning of the game
    for(byte i=0; i<board.length; i++){
      for(byte j=0; j<board[i].length; j++){
        board[i][j] = "▢";
      }
    }
  }

  public static void outputBoard() { // this method prints the board out to the console
    // goes through each each character of the baord
    for(byte i = 0; i < board.length; i++) 
    {
      for(byte j = 0; j < board[i].length; j++) 
      {
        if(board[i][j].contains("<")) // if the board has the < value, it removes the character and makes that letter green
        {
          board[i][j] = board[i][j].replace("<", "");
          board[i][j] = Colours.GREEN + board[i][j] + Colours.RESET;
        } 

        else if(board[i][j].contains(">"))  // if the board has the > value, it removes the character and makes that letteryellowgreen
        { 
          board[i][j] = board[i][j].replace(">", "");
          board[i][j] = Colours.YELLOW + board[i][j] + Colours.RESET;
        }

        System.out.print(board[i][j]); // prints the character in the console

        if(j != board[i].length - 1) 
        {
          System.out.print("\t"); // tabs in between each character
        }
      }
      System.out.println(); // adds a new line at the end of each word
    }
  }

  public static int getTime() { // method used to get the amount of time it takes the user to get the correct word
    int time = 0; // initalizes the time to 0
    time = (int) (System.currentTimeMillis() - startTime)/1000; // gets the time using the currentTimeMillis mathod
    endTime = time;
    return time; // returns the time that it takes
  }
}