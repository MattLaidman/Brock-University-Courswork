package Lab_9_1;


import BasicIO.*;
import static BasicIO.Formats.*;


/** This class is a program to do a simple inventory control producing a report.
  * 
  * @author David Hughes
  * 
  * @version 1.0 (Nov. 2012)                                                     */

public class Inventory3 {
    
    
    private ASCIIDataFile    invData;     // data file for inventory info
    private ASCIIPrompter    prompt;      // prompter for user input
    private ASCIIOutputFile  newInvData;  // data file for updated inventory info
    private ReportPrinter    report;      // printer for report
    
    
    /** The constructor opens the data file for the inventory info, creates the
      * prompter for user input, sets up the printer for the report and creates
      * the new inventory file.                                                  */
    
    public Inventory3 ( ) {
        
        invData = new ASCIIDataFile();
        prompt = new ASCIIPrompter();
        newInvData = new ASCIIOutputFile();
        report = new ReportPrinter();
        
    };  // constructor
    
    
    /** This method does the day-end inventory control for a small company
      * generating a report.                                                     */
    
    private void doDayEnd ( ) {
        
        String  itemNum;     // item number
        int     reorder;     // reorder point
        int     quant;       // quantity on hand
        int     sold;        // number of item sold
        int     newQuant;    // new quantity on hand
        int     numReorder;  // number of items requiring reorder
        
        setUpReport();
        numReorder = 0;
        for ( ; ; ) {
            itemNum = invData.readString();
        if ( invData.isEOF() ) break;
            reorder = invData.readInt();
            quant = invData.readInt();
            prompt.setLabel(itemNum);
            sold = prompt.readInt();
            newQuant = quant - sold;
            if ( newQuant < reorder ) {
                writeDetail(itemNum,quant,sold,newQuant,reorder);
                numReorder = numReorder + 1;
            };
            newInvData.writeString(itemNum);
            newInvData.writeInt(reorder);
            newInvData.writeInt(newQuant);
            newInvData.newLine();
        };
        writeSummary(numReorder);
        invData.close();
        newInvData.close();
        prompt.close();
        report.close();
        
    };  // doDayEnd
    
    
    /** This method sets up the report, adding all fields.                       */
    
    private void setUpReport ( ) {
        
        report.setTitle("National Widgets","Restock Report");
        report.addField("itemNum","Item #",6);
        report.addField("quant","Old",getIntegerInstance(),4);
        report.addField("sold","Sold",getIntegerInstance(),4);
        report.addField("newQuant"," New",getIntegerInstance(),4);
        report.addField("reorder","Reorder",getIntegerInstance(),7);
        
    };  // setUpReport
    
    
    /** This method generates a report detail line.
      * 
      * @param  itemNum   item number
      * @param  quant     old quantity
      * @param  sold      items sold
      * @param  newQuant  new quantity
      * @param  reorder   reorder point                                          */
    
    private void writeDetail ( String itemNum, int quant, int sold,
                               int newQuant, int reorder ) {
        
        report.writeString("itemNum",itemNum);
        report.writeDouble("quant",quant);
        report.writeDouble("sold",sold);
        report.writeDouble("newQuant",newQuant);
        report.writeDouble("reorder",reorder);
        
    };  // writeDetail
    
    
    /** This method generates the report summary.
      * 
      * @param  numReorder number of items requiring reorder                     */
    
    private void writeSummary ( int numReorder ) {
        
        report.writeLine("# Items to Reorder: "+numReorder);
        
    };  // writeSummary
    
    
    public static void main ( String[] args ) { new Inventory3().doDayEnd(); };
    
    
}  // Inventory3