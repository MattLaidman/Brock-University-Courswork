package RunBigInt;

import BasicIO.*;
import Numbers.*;


public class RunBigInt {
    
    
    private ASCIIDisplayer  display;
    
    
    public RunBigInt ( ) {
        
        BigInt  i, j, k;
        
        display = new ASCIIDisplayer(20,60);
        i = new ArrayBigInt(123456);
        j = new ArrayBigInt(654321);
        k = i.add(j);
        display.writeString(i + "+" + j + "=" + k);        
        display.newLine();
        i = new ArrayBigInt(777777);
        j = new ArrayBigInt(654321);
        k = i.sub(j);
        display.writeString(i + "-" + j + "=" + k);        
        display.newLine();
        i = new ArrayBigInt(999999999);
        j = new ArrayBigInt(1);
        k = i.add(j);
        display.writeString(i + "+" + j + "=" + k);        
        display.newLine();
        i = new ArrayBigInt(1000000000);
        j = new ArrayBigInt(1);
        k = i.sub(j);
        display.writeString(i + "-" + j + "=" + k);        
        display.newLine();
        i = new ArrayBigInt(1);
        j = new ArrayBigInt(999999999);
        k = i.add(j);
        display.writeString(i + "+" + j + "=" + k);        
        display.newLine();
        i = new ArrayBigInt(1000000000);
        j = new ArrayBigInt(999999999);
        k = i.sub(j);
        display.writeString(i + "-" + j + "=" + k);        
        display.newLine();
        i = new ArrayBigInt(0);
        j = new ArrayBigInt(0);
        k = i.add(j);
        display.writeString(i + "+" + j + "=" + k);        
        display.newLine();
        i = new ArrayBigInt(999999999);
        j = new ArrayBigInt(999999999);
        k = i.sub(j);
        display.writeString(i + "-" + j + "=" + k);        
        display.newLine();
        i = new ArrayBigInt(111111111);
        j = new ArrayBigInt(9);
        k = i.mul(j);
        display.writeString(i + "*" + j + "=" + k);        
        display.newLine();
        i = new ArrayBigInt(999999999);
        j = new ArrayBigInt(111111111);
        k = i.div(j);
        display.writeString(i + "/" + j + "=" + k);        
        display.newLine();
        i = new ArrayBigInt(9999999);
        j = new ArrayBigInt(9999999);
        k = i.mul(j);
        display.writeString(i + "*" + j + "=" + k);        
        display.newLine();
        i = new ArrayBigInt(99999980000001L);
        j = new ArrayBigInt(9999999);
        k = i.div(j);
        display.writeString(i + "/" + j + "=" + k);        
        display.newLine();
        i = new ArrayBigInt(12345679);
        j = new ArrayBigInt(27);
        k = i.mul(j);
        display.writeString(i + "*" + j + "=" + k);        
        display.newLine();
        i = new ArrayBigInt(333333333);
        j = new ArrayBigInt(12345679);
        k = i.div(j);
        display.writeString(i + "/" + j + "=" + k);        
        display.newLine();
        try {
            i = new ArrayBigInt(-1);
            display.writeString("-1 works");
        }
        catch ( InvalidBigIntException e ) {
            display.writeString("-1 crashes");
        };
        display.newLine();
        i = new ArrayBigInt(999999999);
        j = new ArrayBigInt(1000000000);
        display.writeString(i + "-" + j + "=");        
        try {
            k = i.sub(j);
            display.writeString("=" + k);
        }
        catch ( InvalidBigIntException e ) {
            display.writeString("crashes");
        };
        display.newLine();
        display.close();
        
    };  // constructor
    
    
    public static void main ( String[] args ) { new RunBigInt(); };
    
    
}
