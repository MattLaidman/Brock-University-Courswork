package LabOne;
import BasicIO.*;
import static java.lang.Math.*;

public class LabOne {
 
  public LabOne() {
    //partOne();
    partTwo();
  }
 
  
  /*private void partOne() {
  
    ASCIIDataFile in=new ASCIIDataFile("Z:\\COSC1P03\\Labs\\LabOne\\labOneA.dat");
  
    char[][] text = new char[100][100];
    int i = 0;
    String line;
  
    while (true) {
      line = in.readLine();
      if (in.isEOF()) break;
      text[i] = line.toCharArray();
      System.out.println(text[i]);
      i++;
      in.nextLine();
    }
  
    in.close();
  
   for (int j = 0 ; j < i ; j++) {
     for (char c : text[i]) {
        System.out.print(c + " ");
      }
      System.out.println();
    }
  }*/
  
  
  private void partTwo() {
    
    ASCIIDataFile in = new ASCIIDataFile();
    
    char[] line = new char[25];
    
    int rOne = (int)(Math.random()*24);
    int rTwo = (int)(Math.random()*24);
    
    for (int i = 0 ; i <= rOne ; i++) {
      if (i == rOne) {
        line = in.readLine().toCharArray();
        break;
      }
      in.nextLine();
    }
    
    System.out.println("Original Line:");
    for (char c : line) {
        System.out.print(c + " ");
    }
    
    System.out.println("\nReordered Line:");
    int j = 25 - rTwo;
    for (int i = rTwo ; i < 25 ; i++) {
      System.out.print(line[i] + " ");
    }
    for (int i = 0 ; i <= j ; i++) {
      System.out.print(line[i] + " ");
    }
    in.close();
  }
  
   
  public static void main(String[] args) {new LabOne();}
}