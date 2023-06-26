/*
  Mika Vohl, Krish Patel, Jeremy Chong
  3/29/2023
	UserInput.java
	This is the java file which allows the user to input their guess and validates it
*/
import java.util.*;
import java.io.*;


class UserInput{

  Board boardInstance = new Board();
  public static int wordsEntered = -1;
/*           Last Edited: Jeremy Chong --> 9:45 pm, Mar 27, 2023      */
  public static void reset() { // method to reset all the variables to default values
    wordsEntered = -1;
    Board.board = new String[6][5];
    Board.solved = false;
    Board.startTime = System.currentTimeMillis();
    Board.endTime = 0;
    Board.randomWord = Board.generateWord();
  }

  /*           Last Edited: Krish Patel --> 6:47 pm, Mar 29, 2023      */
  
  public void input(){ // method to get the user input
    Scanner scan = new Scanner(System.in);
    String userWord = null;

    // gets the word entered from the user
    System.out.println("Enter a word:");
    userWord = scan.nextLine().trim().toUpperCase();

    /* THE FOLLOWING COMMENTED CODE WAS USED TO SORT THE dictionary.txt INTO LINES WITH EACH LINE HAVING A DIFFERENT STARTING LETTER FOR THE WORD USING THE dictionary METHOD IN THE SortDictionary CLASS, FOR MORE INFO ON HOW THIS WAS DONE, VIEW THE COMMENTS IN THAT METHOD */
    
    // if(userWord.equals("DICTIONARY")){
    //   SortDictionary.dictionary();
    // }
    
    while(!isValid(userWord)) { // while the word entered is not valid, it gets a new input from the user, it uses the isValid method that was created below
      System.out.println(Colours.RED + "Invalid input! Please enter a 5-character word." + Colours.RESET);
      userWord = scan.nextLine().trim().toUpperCase();
    }
    
    wordsEntered++; // adds 1 to the number of words the user has entered
    
    if(isValid(userWord)){ // if the inputted word is valid
      boardInstance.addWord(userWord, wordsEntered); // add the word to the board using the addWord method
    }
  }

/*           Last Edited: Mika Vohl --> 1:56 pm, Mar 29, 2023      */

  /************************************            KEY FEATURE: Word validity check                    *****************/
  /*********************************************************************************************************************/
  public static boolean isValid(String wordToCheck){                                                                  ///
    // Next: Check if entered word is an english valid word                                                           ///
    // To do this, get a text file of all valid english words                                                         ///
    // The best way to search this is alphabetically (possibly a file sorted with a different first letter in each line, then binary search the line you are looking for)                                                                      ///
    if (wordToCheck.length() != 5) // if the word is not 5 characters long, it returns false                          ///
      return false;                                                                                                   ///
    for(int i=0; i<5; i++){                                                                                           ///
      int asciiCharacter = wordToCheck.charAt(i);                                                                     ///
      if(asciiCharacter < 65 || (asciiCharacter > 90 && asciiCharacter < 97) || asciiCharacter > 122){ // checks if the inputted string contains only alphabetical characters, it is does not, it returns false                               ///
        return false;
      }                                                                                                               ///
    }                                                                                                                 ///
    if(!wordFound(wordToCheck)) // if the word is not an actual word, it returns false                                ///
      return false;                                                                                                   ///
    return true; // if it is an actual 5-letter word, it returns true                                                 ///
  }                                                                                                                   ///
  /*********************************************************************************************************************/
  
  public static boolean wordFound(String word){ // method to check if the word is an actual word, it takes in the user inputted word
    try{
      BufferedReader read = new BufferedReader(new FileReader("sortedDictionary.csv")); // creates 
      String currentLine = null;
      word = word.toLowerCase(); // makes the word lowercase because that's how it is in the dictionary csv file
      int asciiFirstLetter = word.charAt(0); // Each line of the csv file contains every single 5-letter word from that letter. Therefore, there are 26 lines, each line containing words with each letter
      int lineNum = (int)(asciiFirstLetter - 97); // number 0-26

      // reads the line that contains the words for the first letter of the inputted word and check if that line contains the word, if it does, it return true
      
      // binary search through the array
      for(int i=0; i<lineNum; i++)
        read.readLine();
      currentLine = read.readLine();
      if(currentLine.contains(word)){
        return true;
      }
      read.close();
    }
    catch(IOException err){
      System.out.println(err.getMessage());
    }
    return false; // if it is not an actual word, it returns false
  }
}