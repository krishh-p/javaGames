/*
  Mika Vohl, Krish Patel, Jeremy Chong
  3/29/2023
	PrintBoard.java
	This is the java file which will print the given board when the print method is called
*/

 /*           Last Edited: Mika Vohl --> 11:07 am, Mar 28, 2023      */

public class PrintBoard {
    public void print(byte[][] givenBoard) throws InterruptedException{
      // define the initial board from public initial board from another class
      final byte[][] STARTBOARD = UserBoard.startingBoard;
        for(byte i=0; i<9; i++){
            if(i %3 == 0){
                System.out.println();
            }
            for(byte j=0; j<9; j++){
                if((j+1) % 3 == 0){ //Separates the sub 3x3 grids
                    if(givenBoard[i][j] == 0){
                        System.out.print("_    ");
                    }
                    else{
                      // if the initial board has an underscore at this position, print the following in green
                      if(STARTBOARD[i][j] == 0)
                        System.out.print(Colours.GREEN+givenBoard[i][j]+Colours.RESET+"    ");
                      else
                        System.out.print(givenBoard[i][j]+"    ");
                    }
                }
                else{
                    if(givenBoard[i][j] == 0){
                        System.out.print("_  ");
                    }
                    else{
                      // if the initial board has an underscore at this position, print the following in green
                      if(STARTBOARD[i][j] == 0)
                        System.out.print(Colours.GREEN+givenBoard[i][j]+Colours.RESET+"  ");
                      else
                        System.out.print(givenBoard[i][j]+"  ");
                    }
                }
            }
            System.out.println();
        }
    }
}