/*
  Mika Vohl, Krish Patel, Jeremy Chong
  3/29/2023
	CreateBoard.java
	This is the java file which recursively backtracks to creates the valid solution board with random values
*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

 /*           Last Edited: Mika Vohl --> 11:27 am, Mar 29, 2023      */

public class CreateBoard {
    byte[][] board = new byte[9][9];

    /**    KEY FUNCTION: Filling the board with random values while ensuring the board is valid    ***/
    /*************************************************************************************************/
    public boolean fillBoard(){                                                                     ///
        byte numberToSet = 0;                                                                       ///
        byte row = -1, col = -1;                                                                    ///
        boolean isEmpty = true;                                                                     ///
                                                                                                    ///
        // find the first empty cell                                                                ///
        for(byte i=0; i<9; i++){                                                                    ///
            for(byte j=0; j<9; j++) {                                                               ///
                if(board[i][j] == 0){ // if the cell is empty                                       ///
                  // set the row and col variables to be the cell coordinates                       ///
                  row = i;                                                                          ///
                  col = j;                                                                          ///
                  isEmpty = false;                                                                  ///
                  break;                                                                            ///
                }                                                                                   ///
            }                                                                                       ///
        }                                                                                           ///
        // if there are no empty cells, the board is filled                                         ///
        if(isEmpty){                                                                                ///
            return true;                                                                            ///
        }                                                                                           ///
        // randomly try different values in the cell                                                ///
        List<Byte> numbers = new ArrayList<>(); // creates a new arraylist so that it can be shuffled with Collections.shuffle (arrays cannot be shuffled this way)                                            ///
        for(byte i=1; i<=9; i++){                                                                   ///
            numbers.add(i); // add the current index number as an element of the numbers arraylist  ///
        }                                                                                           ///
        Collections.shuffle(numbers); // randomly shuffles an array of numbers 1-9                  ///
                                                                                                    ///
        for(byte i=0; i<numbers.size(); i++){                                                       ///
            numberToSet = numbers.get(i); // get the ith number in the randomly shuffled array of numbers 1-9
            if(!searchRow(row, numberToSet) && !searchCol(col, numberToSet) && !searchSquare((byte)(row - row % 3), (byte)(col - col % 3), numberToSet)){ // if the number is not in the row, column, or square               ///
                board[row][col] = numberToSet; // set the number at the given position of the board ///
                if(fillBoard()){ // recursively try to fill the rest of the board                   ///
                    return true;                                                                    ///
                }                                                                                   ///
                // backtrack if the board can't be filled with the current value                    ///
                board[row][col] = 0;                                                                ///
            }                                                                                       ///
        }                                                                                           ///
        return false;                                                                               ///
    }                                                                                               ///
  /*************************************************************************************************///

    public boolean searchRow(byte row, byte testNum){
        for(byte i=0; i<9; i++){ // loop through every index of a row of the board
            if(board[row][i] == testNum){ // check if the number at the index is the number that is being looked for
                return true;
            }
        }
        return false;
    }

    public boolean searchCol(byte col, byte testNum){
        for(byte i=0; i<9; i++){ // loop through every index of a column of the board
            if(board[i][col] == testNum){ // check if the number at the index is the number that is being looked for
                return true;
            }
        }
        return false;
    }

    public boolean searchSquare(byte row, byte col, byte testNum){
        for(byte i=row; i<row+3; i++){ // loops through the rows of a 3x3 square
            for(byte j=col; j<col+3; j++){ // loops through the columns of a 3x3 square
                if(board[i][j] == testNum){ // check if the number at the index is the number that is being looked for
                    return true;
                }
            }
        }
        return false;
    }
}
