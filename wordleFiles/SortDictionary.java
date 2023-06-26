/*
  Mika Vohl, Krish Patel, Jeremy Chong
  3/29/2023
	SortDictionary.java
	This is the java file which is used one time. The purpose of this file is to rearrange the dictionary file to be stored seperated by commas and on a seperate line according to the alphabetical order of the first letter, seperated by commas.
*/
import java.io.*;


 /*           Last Edited: Mika Vohl --> 11:05 am, Mar 27, 2023      */

class SortDictionary{
  public static void dictionary(){
    try{
      String currentLine = "";
      String lastLine = "";
      String file = "dictionary.txt";
      String newFile = "sortedDictionary.csv";
      BufferedReader readText = new BufferedReader(new FileReader(file));
      BufferedWriter writeText = new BufferedWriter(new FileWriter(newFile));
      System.out.println("test");
      do{
        lastLine = currentLine; // sets lastLine to equal the prior currentLine value
        currentLine = readText.readLine(); // sets currentLine to be the next line that is read
        if(currentLine != null){ // processes the input if it is not null
          if(lastLine == "" || currentLine.charAt(0) == lastLine.charAt(0)){ // if on the first iteration, or the currentLine starts with the same letter as the last line
            writeText.write(","+currentLine); // write to the same line
          }
          else{ // if the currentLine starts with a different letter than the last line
            writeText.write("\n"+currentLine); // write to the next line
          }
          // if same first letter, seperate with comma
        }
      }while(currentLine != null); // loop while not reading null value (stop at end of file)
      readText.close(); // closes the reader
      writeText.close(); // closes the writer
    }
    catch(IOException err){
      System.out.println(err.getMessage());
    }
  }
}