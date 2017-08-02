package Assign_4;


import java.util.*;
import BasicIO.*;
import static BasicIO.Formats.*;


public class PopCensus {
  
  
  private ASCIIDataFile    addresses;
  private BasicForm        household, individual;
  private ASCIIOutputFile  censusData;
  private ReportPrinter    report;
  
  
  public PopCensus ( ) {
    
    addresses = new ASCIIDataFile();
    household = new BasicForm("OK","Skip");
    individual = new BasicForm ("OK");
    censusData = new ASCIIOutputFile("CensusData.txt");
    report = new ReportPrinter();
    
  };
  
  
  private void takeCensus ( ) {
    
    int numPeople, button, age, sex, language, numM, numF, numE, numFr, numO, totP;
    String name, address;
    numM = 0;
    numF = 0;
    numE = 0;
    numFr = 0;
    numO = 0;
    totP = 0;
    
    household();
    individual();
    setUpReport();
    
    for ( ; ; ) {
      address = addresses.readLine();
      if (addresses.isEOF()) break;
      household.writeString("address", address);
      button = household.accept();
      numPeople = household.readInt("people");
      totP+=numPeople;
      if (button == 0) {
        for (int i = 1 ; i <= numPeople ; i++) {
          individual.writeInt("person", i);
          individual.accept();
          name = individual.readString("name");
          age = individual.readInt("age");
          sex = individual.readInt ("sex");
          language = individual.readInt ("language");
          switch (individual.readInt("sex")){
            case 0:
              numM++;
              break;
            case 1:
              numF++;
              break;
          }
          switch (individual.readInt("language")){
            case 0:
              numE++;
              break;
            case 1:
              numFr++;
              break;
            case 2:
              numO++;
              break;
          }
          censusData.writeLine(address + " " + numPeople + " " + name + " " + age + " " + sex + " " + language);
          censusData.newLine();
          individual.clearAll();
        }
        report.writeString("address", address);
        report.writeInt("people", numPeople);
        report.writeInt("male", numM);
        report.writeInt("female", numF);
        report.writeInt("english", numE);
        report.writeInt("french", numFr);
        report.writeInt("other", numO);
        numM = 0;
        numF = 0;
        numE = 0;
        numFr = 0;
        numO = 0;
      }
      household.clearAll();
    }
    individual.close();
    household.close();
    report.writeString("address","Total");
    report.writeDouble("people",totP);
    report.close();
  };
  
  
  private void household ( ) {
    
    household.setTitle("Household");
    household.addTextField("address", "Address ");
    household.setEditable("address",false);
    household.addTextField("people", "# People", 2);
    household.setEditable("people",true);

  };
  
  
  private void individual( ) {
    
    individual.setTitle("Individual");
    individual.addTextField("person", "Person ", 2);
    individual.setEditable("person",false);
    individual.addTextField("name", "Name    ");
    individual.setEditable("name",true);
    individual.addTextField("age", "Age        ", 4);
    individual.setEditable("age",true);
    individual.addRadioButtons("sex", "Sex", false, "Male", "Female");
    individual.addRadioButtons("language", "Language", false, "English", "French", "Other");
    
  };
  
  
  private void setUpReport () {
    
    report.setTitle("Statistics Canada", "Census Report");
    report.addField("address","Address",20);
    report.addField("people","People");
    report.addField("female","Female");
    report.addField("male","Male");
    report.addField("english","English");
    report.addField("french","French");
    report.addField("other","Other");
    
  };
 
  
  public static void main ( String[] args ) { new PopCensus().takeCensus(); };
  
  
}