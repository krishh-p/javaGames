/* 
	Mika Vohl, Krish Patel, Jeremy Chong
	03/29/2023
	Scoreboard.java
  This class is displays the leaderboard and adds scores to it for Sudoku and Wordle. These scores are based on how fast each user finishes the game.
 */

import java.util.*;
import java.io.*;

 /*           Last Edited: Jeremy Chong --> 11:09 am, Mar 29, 2023      */

class Scoreboard {

  //Adds score to respective score file
  public static void addScore(String file, String username, int score) {
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
      bw.write(score + "," + username);
      bw.newLine();
      bw.close();
      sortScores(file);
    } catch(IOException err) {
      System.out.println(err.getMessage());
    }
  }

  /***************************   KEY FEATURE: Sorts the leaderboard scores   *************************************/
  /***************************************************************************************************************/
  //Sorts the score file                                                                                        ///
  public static void sortScores(String file) {                                                                  ///
    try {                                                                                                       ///
      BufferedReader br = new BufferedReader(new FileReader(file));                                             ///
      BufferedWriter bw = null;                                                                                 ///
      List<Player> scores = new ArrayList<>(); //arraylist is a resizeable array, an array list of objects      ///
      String line = null;                                                                                       ///
      String[] info = null;                                                                                     /// 
                                                                                                                ///
      do {                                                                                                      ///
        line = br.readLine();                                                                                   ///
        if (line != null) {                                                                                     ///
          info = line.split(",");                                                                               ///
          //Creates new instance of player                                                                      ///
          Player user = new Player(Integer.parseInt(info[0]), info[1]);                                         ///
                                                                                                                ///
          scores.add(user);                                                                                     ///
        }                                                                                                       ///
      } while(line != null);                                                                                    ///
                                                                                                                ///
      br.close();                                                                                               ///
                                                                                                                ///
      //Sorts elements in ascending order within a list by comparing them                                       ///
      Collections.sort(scores);                                                                                 ///
                                                                                                                ///
      //Overwrites the score txt file with new sorted list                                                      ///
      bw = new BufferedWriter(new FileWriter(file));                                                            ///
                                                                                                                ///
      for (int i = 0; i < scores.size(); i++) {                                                                 ///
        bw.write(scores.get(i).score + "," + scores.get(i).username);                                           ///
        bw.newLine();                                                                                           ///
      }                                                                                                         ///
                                                                                                                ///
      bw.close();                                                                                               ///
                                                                                                                ///
    } catch(IOException err) {                                                                                  ///
      System.out.println(err.getMessage());                                                                     ///
    }                                                                                                           ///
  }                                                                                                             ///
  /***************************************************************************************************************/

  //Prints all the scoreboards
  public static void print(String file, String gameName) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String line = null;
      String[] userInfo = null;
      byte lineNum = 1;

      System.out.println("\u001B[1m" + gameName + " leaderboard" + Colours.RESET);
      do {
        line = br.readLine();
        //In case if there are multiple empty lines in txt file
        if (line != null && line.equals(""))
          line = null;
        //Displays the first 5 players with the best scores
        if (line != null && lineNum <= 10) {
          userInfo = line.split(",");
          System.out.println(lineNum + ". " + userInfo[1] + ": " + userInfo[0] + " seconds");
        }
        lineNum++;
      } while(line != null && lineNum <= 10);
      
    } catch(IOException err) {
      System.out.println(err.getMessage());
    }
  }
  
}