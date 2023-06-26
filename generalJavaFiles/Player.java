/* 
	Mika Vohl, Krish Patel, Jeremy Chong
	03/29/2023
	Player.java
  This class is to create a Player object. This makes it easier to compare different player's scores and sorting them by comparing each object's properties.
 */

import java.util.*;

 /*           Last Edited: Krish Patel --> 11:27 pm, Mar 28, 2023      */

//Comparable interface allows objects to be compared with one another using its properties
class Player implements Comparable<Player>{
  int score = 0;
  String username = null;

  //Constructor for the player class
  public Player(int score, String username) {
    this.score = score;
    this.username = username;
  }

  //Overrides the compareTo method of the comparable interface. This is necessary for sorting the player objects using Collections.sort()
  public int compareTo(Player otherUser) {
    return Integer.compare(this.score, otherUser.score);
  }
}