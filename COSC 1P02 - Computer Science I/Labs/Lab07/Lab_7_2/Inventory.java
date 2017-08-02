package Lab_7_2;


import java.util.*;
import BasicIO.*;
import static BasicIO.Formats.*;


/** This class is a program to do a simple inventory control producing a report.
  * 
  * @author <your name>
  * 
  * @version 1.0 (Nov. 2012)                                                    */

public class Inventory {
    
    
    private ASCIIDataFile    invData;     // data file for inventory info
    private ASCIIDisplayer   temp;        // temporary display
//    private ReportPrinter    report;      // printer for report
    
    
    /** The constructor opens the data file for the inventory info and sets up
      * th printer for the report                                               */
    
    public Inventory ( ) {
        
        invData = new ASCIIDataFile();
        temp = new ASCIIDisplayer();  // temporary displayer
//        report = new ReportPrinter();
        
    };  // constructor
    
    
    /** This method does the day-end inventory control for a small company
      * generating a report.                                                    */
    
    private void doDayEnd ( ) {
        
        String  itemNum;  // inventory item number
        
        itemNum = invData.readString();
        temp.writeLine(itemNum);  // temporary display
        invData.close();
        temp.close();  // temporary display
//        report.close();
        
    };  // doDayEnd
        
    
    public static void main ( String[] args ) { new Inventory().doDayEnd(); };
    
    
}  // Inventory