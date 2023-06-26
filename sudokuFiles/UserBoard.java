/*
  Mika Vohl, Krish Patel, Jeremy Chong
  3/29/2023
	UserBoard.java
	This is the java file which removes random values from the solution board to create the user's board.
*/
import java.util.Random;

 /*           Last Edited: Mika Vohl --> 8:21 pm, Mar 29, 2023      */

public class UserBoard {

  public static byte[][] startingBoard = new byte[9][9];
  
  public byte[][] blankBoard(byte[][] completeBoard, byte difficulty) throws InterruptedException{
      Random rand = new Random();
      PrintBoard printer = new PrintBoard(); // new instance of the PrintBoard class
      byte row = 0, col = 0;

      // The following block of code was necessary as using the assignment operator created a reference to the original array rather than a copy of it. This code creates a copy of the array
      for (byte i = 0; i < 9; i++) {
          for (byte j = 0; j < 9; j++) {
              startingBoard[i][j] = completeBoard[i][j];
          }
      }
      // This removes makes the user board by removing some numbers from the completeBoard. The amount of numbers removed are dependant on the specified difficulty
      for(byte i=0; i<difficulty*3+16; i++){
          row = (byte)rand.nextInt(9); // picks random row
          col = (byte)rand.nextInt(9); // picks random col
          if(startingBoard[row][col] != 0){ // checks to see if the coordinate is already empty, if not, make it empty
              startingBoard[row][col] = 0;
          }
          else{
              i--; // if the position was already empty, remove one from the count so the count does not go up without removing a number
          }
      }
      return startingBoard;
  }
}