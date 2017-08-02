package Spartan;

/** This class is the Spartan shipping forms used by employees
 * 
 * @author Matt Laidman
 *
 * @version 1.0 (April 2013)			                        */

import BasicIO.*;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.io.File;

import javax.swing.JFileChooser;


public class SpartanShipping {
	
	private String[] cart;						// array for cart items
	private int oNum, iCount;					// order number and item count
	private String cNum, fileN;					// customer number
	
	private JFileChooser fc = new JFileChooser();
	private DecimalFormat df;					// decimal format for leading zeros
	private BasicForm shipForm;					// shipping form
	private File f;
	private ASCIIDataFile cOrder, storeData;	// current order and store data files
	private ReportPrinter sConf;				// shipping confirmation report
	
	
	private SpartanShipping ( ) {
		
		try {
			fc.showOpenDialog(null);
			File file = fc.getSelectedFile();
			fileN = file.getAbsolutePath();
			storeData = new ASCIIDataFile(fileN);
		} catch (NullPointerException e){
			throw new FileNotFoundException();
		}
		
		char[] leadZ = new char[4]; 					// create leading zeros number format
	    Arrays.fill(leadZ, '0');
	    df = new DecimalFormat(String.valueOf(leadZ));
		
		shipForm = new BasicForm("Ship", "Quit");
		sConf = new ReportPrinter();
		
		buildShipForm();
		
	} // constructor
	
	
	private void ship ( ) {
		
		boolean flag = true;
		
		while (flag) {
			getOrder();
			populate();
			switch (shipForm.accept()) {
				case 0:
					shipCart();
					break;
				case 1:
					shipForm.close();
					cOrder.close();
					flag = false;
					break;
				default:
					break;
			} // switch
		} // while
		
	} // ship
	
	
	private void shipCart ( ) {

		cOrder.close();
		new File(fileN).delete();		// wanted to move files to different location to keep order count, but couldn't get to work		
		
		printSConf();	// print report
		
	} // shipCart
	
	
	private void printSConf ( ) {
		
		String t = "";
		int j = 0;
		
		resetData();
		buildSConf();
		
		for (int i = 0 ; i < iCount ; i++) {
			sConf.writeString("quantity", parseCart(i, 2));
			sConf.writeString("item", parseCart(i, 1));
			j = 0;
			t = "";
			while (j < cart[i].length() && cart[i].charAt(j) != 32) {
				j++;
				if (j < cart[i].length() && cart[i].charAt(j) == 32) {
					j+=6;
					while (j < cart[i].length() && cart[i].charAt(j) != 10) {
						t = t + cart[i].charAt(j);
						j++;
					} // while
				} // if
			} // while
			sConf.writeString("desc", t);
			sConf.newLine();
		} // for
		
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, y");
		Date date = new Date();
		String nDate = dateFormat.format(date);
		
		sConf.newLine();
		sConf.writeString("desc", "Your order shipped on: " + nDate);
		
		sConf.close();
		
	} // printSConf
	
	
	private void populate ( ) { // clear and repopulate shipping form
		
		shipForm.clearAll();
		
		shipForm.writeString("order", df.format(oNum));
		shipForm.writeLine("cart", "Q Item Description");
		for (int i = 0 ; i < 20 ; i++) {
			shipForm.writeString("cart", "-");
		} // for
		shipForm.newLine("cart");
		
		for (int i = 0 ; i < iCount ; i++) {
			shipForm.writeString("cart", cart[i] + "\n");
		} // for
		
	} // populate
	
	
	private void getOrder ( ) { // read in items in current order
		
		File[] files = new File ("Orders").listFiles();
		
		f = files[files.length - 2];
		cOrder = new ASCIIDataFile(f);
		
		oNum = cOrder.readInt();
		cNum = cOrder.readString();
		iCount = cOrder.readInt();
		cart = new String[iCount];
		
		for (int i = 0 ; i < iCount ; i++) {
			cart[i] = cOrder.readLine();
			cOrder.nextLine();
		} // for
		
	} // getOrder
	
	
	private String parseCart (int i, int num) { // parses cart and returns individual items
		
		String itemNum = "", q = "";
		int j = 0;
		
		while (j < cart[i].length() && cart[i].charAt(j) != 32) {
			q = q + cart[i].charAt(j);
			j++;
			if (j < cart[i].length() && cart[i].charAt(j) == 32) {
				j++;
				while (j < cart[i].length() && cart[i].charAt(j) != 32) {
					itemNum = itemNum + cart[i].charAt(j);
					j++;
				} // while
			} // if
		} // while
		if (num == 1) {
			return itemNum;
		} else if (num == 2) {
			return q;
		} else {
			return null;
		} // else
		
	} // parseCart
	
	
	private String parseUDat (String user, int num) {	// num indicates what information is passed back
														// 1 = first/last name
		String name = "", address = "";					// 2 = address
		String data = "", t = "";
		int count = 0, k = 1;
		char c = 0;
		
		resetData();
		
		count = storeData.readInt(); // skip to user information
			
		for (int i = 0 ; i < count ; i++) {
			storeData.nextLine();
		} // for
			
		count = storeData.readInt(); // get number of entries
		
		for (int i = 0 ; i < count ; i++) { // locate line of data for user
			t = storeData.readString();
			if (t.equals(user)) {
				data = t + " " + storeData.readLine();
			} else {
				storeData.nextLine();
			} // else
		} // for
		
		t = "";

		if (num == 1) {
			for (int i = 0 ; i < data.length() ; i++) {
				c = data.charAt(i);
				if (c == 9) {
					k++;
					if (k == 3) {
						address = t + c;
						return address;
					} else {
						t = "";	
					} // else
				} else {
					t = t + c;
				} // else
			} // for
		} // if
		
		if (num == 2) {
			for (int i = 0 ; i < data.length() ; i++) {
				c = data.charAt(i);
				if (c == 9 | c == 32) {
					if (k == 2 | k == 3) {
						name = t + c;
						if (k == 3) {
							return name;
						}// if
					} else {
						t = "";
					} // else
					if (k == 2) {
						t = t + " ";
					} // if
					k++;
				} else {
					t = t + c;
				} // else
			} // for
		} //if
		
		return null;
		
	} // parseUDat
	
	
	private void resetData ( ) { // closes and reopens data file
		
		storeData.close();
		storeData = new ASCIIDataFile(fileN);
		
	} // resetData
	
	
	private void buildSConf ( ) { // build shipping confirmation report
		
		sConf.setTitle("Shipping Notice", "Order #: " + df.format(oNum), "Customer #: " + cNum,"Name: " + parseUDat(cNum, 1), "Address: " + parseUDat(cNum, 2));
		sConf.addField("quantity", "Quantity");
		sConf.addField("item", "Item #");
		sConf.addField("desc", "Description", 40);
		
	} // buildSConf
	
	
	private void buildShipForm ( ) { // build shipping form
		
		shipForm.setTitle("Spartan Online Store Shipping");
		shipForm.addLabel("label1", "Order #:");
		shipForm.addTextField("order", 5, 100, 5);
		shipForm.setEditable("order", false);
		shipForm.addTextArea("cart", "Cart", 20, 40);
		shipForm.setEditable("cart", false);
		
	} // buildShipForm
	
	
	public static void main ( String[] args ) {new SpartanShipping().ship(); };
	
}
