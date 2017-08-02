package Spartan;

/** This class is the front end of the Spartan Online Store that is used by customers
 * 
 * @author Matt Laidman
 *
 * @version 1.0 (April 2013)			                        */

import BasicIO.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.io.*;
import javax.swing.*;


public class Spartan {

	private String[][] catalogue;									// array that stores all the catalogue items
	private String[] cart = new String[1];							// array that store all items in the cart
	private String enteredUser, enteredPass, fileN;						// user name and password used to sign in
	private int fNum, cCount = 0, iCount = 0;						// order number, count of items in cart, and count of items in catalogue
	private double total= 0;										// total cost of order
	
	private JFileChooser fc = new JFileChooser();
	private DecimalFormat df;										// DecimalFormat for leading zeroes in order numbers
	private NumberFormat f = NumberFormat.getCurrencyInstance();	// NumberFormat for dollars
	private BasicForm login, storeFront;							// BasicForms for login, and storefront
	private ASCIIDataFile storeData;								// DataFile containing catalogue/user information
	private ReportPrinter oConf;									// order confirmation report
	
	
	private Spartan ( ) {
		
		File folder = new File ("Orders\\");						// create folder for orders if it doesn't exist
		if (!folder.exists()) {
			folder.mkdirs();
		} // if
		
		try {
			fc.showOpenDialog(null);
			File file = fc.getSelectedFile();
			fileN = file.getAbsolutePath();
			storeData = new ASCIIDataFile(fileN);
		} catch (NullPointerException e){
			throw new FileNotFoundException();
		}
		
		char[] leadZ = new char[4];									// create leading zeros format
	    Arrays.fill(leadZ, '0');
	    df = new DecimalFormat(String.valueOf(leadZ));
		
		login = new BasicForm("Submit", "Cancel");					// build forms and report
		storeFront = new BasicForm("Add", "Remove", "Done");
		oConf = new ReportPrinter();
		
		buildLogin();
		buildStore();
		
	} // constructor
	
	
	private void runStore ( ) {
		
		boolean flag = true;
		
		if (getLogin ()) {
			while (flag == true) {
				populate();
				switch (storeFront.accept()) {
					case 0:
						addItem();
						break;
					case 1:
						removeItem();
						break;
					case 2:
						if (isCartEmpty()) {
							submitOrder();
						} // if
						storeFront.close();
						storeData.close();
						flag = false;
						break;
					default:
						break;
				} // switch
			} // while
		} // if
		
	} // runStore
	
	
	private void submitOrder ( ) {
		
		fNum = 0;
		
		File fName = new File("Orders", df.format(fNum) + ".txt");
		
		while (fName.exists()) {
			fNum++;
			fName = new File("Orders", df.format(fNum) + ".txt");
		} // while
		writeOrder();												// write order to file to be shipped
		
		buildOConf();												// build/print order confirmation
		printOConf();
		
	} // submitOrder
	
	
	private void writeOrder () {
		
		ASCIIOutputFile fName = new ASCIIOutputFile ("Orders\\" + df.format(fNum) + ".txt");
		File tfName = new File("Orders", df.format(fNum) + ".txt");
		
		try {
			tfName.createNewFile();
		} catch (IOException e) {
			throw new FileNotCreatedException();
		} // catch
		
		fName.writeString(df.format(fNum));
		fName.newLine();
		fName.writeString(enteredUser);
		fName.newLine();
		fName.writeInt(cCount);
		fName.newLine();
		for (int i = 0 ; i < cCount ; i++) {
			fName.writeLine(parseCart(i, 2) + " " + parseCart(i, 1) + " " + parseIDat(parseCart(i, 1), 2));
			fName.newLine();
		} // for
		
		fName.close();
		
	} // writeOrder
	
	
	private void printOConf ( ) {
		
		for (int i = 0 ; i < cCount ; i++) {
			oConf.writeString("quantity", parseCart(i, 2));
			oConf.writeString("item", parseCart(i, 1));
			oConf.writeString("desc", parseIDat(parseCart(i, 1), 2));
			oConf.writeString("price", f.format(Double.parseDouble(parseIDat(parseCart(i, 1), 1))));
			oConf.writeString("total", f.format(Double.parseDouble(parseIDat(parseCart(i, 1), 1))*Double.parseDouble(parseCart(i, 2))));
			oConf.newLine();
		} // for
		oConf.newLine();
		oConf.writeString("desc", "Total:");
		oConf.writeString("total", f.format(total));
		
		oConf.close();
		
	} // printOConf
	
	
	private boolean isCartEmpty ( ) { // returns true if cart is empty, false if not
		
		if (cart.length != 1) {
			return true;
		} else {
			return false;
		} // else
		
	} // isCartEmpty
	
	
	private void removeItem() { // removes entered quantity of entered item from cart
		
		String itemNum = "";
		int quantity = 0, newQ = 0;
		
		itemNum = storeFront.readString("item");
		quantity = storeFront.readInt("quantity");
		
		for (int i = 0 ; i < cCount ; i++) {
			if (itemNum.equals(parseCart(i, 1))) {
				newQ = Math.max(Integer.parseInt(parseCart(i, 2)) - quantity, 0);
				if (newQ != 0) {
					cart[i] = newQ + "  -  " + itemNum + "  -  $ " + parseIDat(itemNum, 1) + "\t-  " + parseIDat(itemNum, 2);
					total = total - (Double.parseDouble(parseIDat(itemNum, 1)) * Math.min(Integer.parseInt(parseCart(i, 2)), quantity));
				} else {
					for (int k = i ; k < cCount ; k++) {
						cart[k] = cart[k+1];
						if (k + 1 == cCount) {
							cart[k] = null;
							cart = decrementedCart();
						} // if
					} // for
				} // else
				break;
			} // if
		} // for
		
	} // removeItem
	
	
	private void addItem ( ) { // adds entered quantity of entered item to cart
		
		String itemNum = "";
		int quantity = 0, newQ = 0;
		boolean flag = true;
		
		itemNum = storeFront.readString("item");
		quantity = storeFront.readInt("quantity");
		
		if (cart.length == 1) {
			addNewItem(itemNum, quantity);
		} else {
			for (int i = 0 ; i < cCount ; i++) {
				if (itemNum.equals(parseCart(i, 1))) {
					newQ = Integer.parseInt(parseCart(i, 2)) + quantity;
					cart[i] = newQ + "  -  " + itemNum + "  -  $ " + parseIDat(itemNum, 1) + "\t-  " + parseIDat(itemNum, 2);
					total = total + (Double.parseDouble(parseIDat(itemNum, 1))*quantity);
					flag = false;
				} // if
			} // for
			if (flag) {
				addNewItem(itemNum, quantity);
			} // if
		} // else
		
		populate();
		
	} // addItem
	
	
	private void addNewItem (String itemNum, int quantity) { // adds items if not already >= 1 in cart
		
		if (parseIDat(itemNum, 1) != null && !parseIDat(itemNum, 1).isEmpty() && quantity != 0) {
			cart[cCount] = quantity + "  -  " + itemNum + "  -  $ " + parseIDat(itemNum, 1) + "\t-  " + parseIDat(itemNum, 2);
			cart = incrementedCart();
			total = total + (Double.parseDouble(parseIDat(itemNum, 1))*quantity);
		} // if
		
	} // addNewItem
	
	
	private String[] decrementedCart ( ) { // creates cart array with decremented size
		
		String[] iCart = new String[cart.length - 1];
		cCount--;
		
		for (int i = 0 ; i < iCart.length ; i++) {
			iCart[i] = cart[i];
		} // for
		
		return iCart;
		
	} // decrementedCart
	
	
	private String[] incrementedCart ( ) { // creates cart array with incremented size
		
		String[] iCart = new String[cart.length + 1];
		cCount++;
		
		for (int i = 0 ; i < cart.length ; i++) {
			iCart[i] = cart[i];
		} // for
		
		return iCart;
		
	} // incrementedCart
	
	
	private String parseCart (int i, int num) {		// parses cart and returns individual parts
													// num indicates what information is passed back
		String tItemNum = "", tQ = "";				// 1 = itemNum
		int j = 0;									// 2 = quantity
		
		while (j < cart[i].length() && cart[i].charAt(j) != 32) {
			tQ = tQ + cart[i].charAt(j);
			j++;
			if (j < cart[i].length() && cart[i].charAt(j) == 32) {
				j+=5;
				while (j < cart[i].length() && cart[i].charAt(j) != 32) {
					tItemNum = tItemNum + cart[i].charAt(j);
					j++;
				} // while
			} // if
		} // while
		if (num == 1) {
			return tItemNum;
		} else if (num == 2) {
			return tQ;
		} else {
			return null;
		} // else

	} // parseCart
	
	
	private String parseIDat (String itemNum, int num) {	// parses catalogue data and returns individual parts
															// num indicates what information is passed back
		String t = "";										// 1 = price
															// 2 = description
		resetData();
		
		storeData.readString();

		for (int i = 0 ; i < iCount ; i++) { // locate line of data for item
			t = storeData.readString();
			if (t.equals(itemNum)) {
				if (num == 1) {
					return storeData.readString();
				} // if
				if (num == 2) {
					storeData.readString();
					return storeData.readLine();
				} // if
			} // if
			storeData.nextLine();
		} // for
		return null;
		
	} // parseIDat
	
	
	private void populate ( ) { // clear form and repopulate with data
		
		storeFront.clearAll();
		
		populateCat();
		populateCart();
		
		if (total != 0) {
			storeFront.writeString("total", f.format(total));
		} // if
		
		for (int i = 0 ; i < cCount ; i++) {
			storeFront.writeLine("cart", cart[i]);
			storeData.nextLine();
		} // for
		
	} // populate
	
	
	private void populateCat ( ) { // populates catalogue section
		
		resetData();
		
		iCount = storeData.readInt();
		catalogue = new String[iCount][3];
		
		for (int i = 0 ; i < iCount ; i++) {
			catalogue[i][0] = storeData.readString();
			catalogue[i][1] = storeData.readString();
			catalogue[i][2] = storeData.readLine();
		} // for
		
		storeFront.writeLine("catalogue", "Item #\t Price\t\t   Description");
		for (int i = 0 ; i < 20 ; i++) {
			storeFront.writeString("catalogue", "-");
		} // for
		storeFront.newLine("catalogue");
		for (int i = 0 ; i < iCount ; i++) {
			for (int j = 0 ; j < 3 ; j++) {
				storeFront.writeString("catalogue", catalogue[i][j]);
				if (j == 0) {
					storeFront.writeString("catalogue", " -  $");
				} else if (j == 1) {
					storeFront.writeString("catalogue", "\t- ");
				} else if (j == 2) {
					storeFront.newLine("catalogue");
				} // else if
			} // for
		} // for
		
	} // populateCat
	
	
	private void populateCart ( ) { // populates cart
		
		storeFront.writeLine("cart", "Q     Item #   Price\t   Description");
		for (int i = 0 ; i < 20 ; i++) {
			storeFront.writeString("cart", "-");
		} // for
		storeFront.newLine("cart");
		
	} // populateCart

	
	private boolean getLogin ( ) { // gets login information
		
		while (true) {
			if (login.accept() == 0) {
				enteredUser = login.readString("user");
				enteredPass = login.readString("pass");
				if (validLogin()) {
					login.close();
					return true;				
				} // if
			} else {
				login.close();
				return false;
			} // else
		} // while
		
	} // getLogin
	
	
	private boolean validLogin ( ) { // checks if login is valid
		
		if (enteredUser.equals("") | enteredPass.equals("")) {
			return false;
		} else {
			return enteredPass.equals(parseUDat(enteredUser, 1));
		} // else

	} // validLogin
	

	private String parseUDat (String user, int num) {	// parses user data and returns individual parts
														// num indicates what information is passed back
		String cPass = "", name = "", address = "";		// 1 = correct password
		String data = "", t = "";						// 2 = first/last name
		int count = 0, k = 1;							// 3 = address
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
					t = "";
				} else {
					t = t + c;
				} // else
				if (i == data.length()-1) {
					cPass = t;
					return cPass;
				} // if
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
		
		if (num == 3) {
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
		
		return null;
		
	} // parseUDat
	
	
	private void resetData ( ) { // closes and reopens data file
		
		storeData.close();
		storeData = new ASCIIDataFile(fileN);
		
	} // resetData
	
	
	private void buildOConf ( ) { // build order confirmation report
		
		oConf.setTitle("Order Confirmation", "Order #: " + df.format(fNum), "Customer #: " + enteredUser,"Name: " + parseUDat(enteredUser, 2), "Address: " + parseUDat(enteredUser, 3));
		oConf.addField("quantity", "Quantity");
		oConf.addField("item", "Item #");
		oConf.addField("desc", "Description", 40);
		oConf.addField("price", "Amount", 12);
		oConf.addField("total", "Total", 12);
		
	} // buildOConf
	
	
	private void buildLogin ( ) { // build login form
		
		login.setTitle("Login");
		login.addTextField("user", "Username");
		login.addTextField("pass", "Password");
		
	} // buildLogin
	
	
	private void buildStore ( ) { //build main store form
		
		storeFront.setTitle("Spartan Online Store");
		storeFront.addTextArea("catalogue", "Catalogue", 20, 40, 20, 10);
		storeFront.setEditable("catalogue", false);
		storeFront.addTextArea("cart", "Cart", 20, 40, 320, 10);
		storeFront.setEditable("cart", false);
		storeFront.addLabel("label1", "Item #:", 20, 390);
		storeFront.addTextField("item", 5, 65, 390);
		storeFront.addLabel("label2", "Quantity:", 110, 390);
		storeFront.addTextField("quantity", 5, 165, 390);
		storeFront.addLabel("label3", "Order Total:", 320, 390);
		storeFront.addTextField("total", 10, 395, 390);
		storeFront.setEditable("total", false);
		
	} // buildStore
	
	
	public static void main ( String[] args ) {new Spartan().runStore(); };
	
}