import BasicIO.*;

/** This class doesn't, ut is supposed to display the UI of text editor
 * that will allow the user to specify a type of edit they would like
 * to make and change text in the document.
 *
 * @author Matt Laidman
 *
 * @version 1.0 (February 2013)									             */


public class IV {

	private BasicForm ivui;
	
	
	public IV ( ) {
		
		ivui = new BasicForm ("Apply Edit", "Exit");

	} // constructor
	
	
	private void editText ( ) {
		
		Node[] list = new Node[15];
		buildUI ();
		int sButton = ivui.accept();
		
		while (true) {
			
			ivui.writeInt("S", -1);
			ivui.writeInt("E", -1);
			sButton = ivui.accept();
			if (sButton == 1) {
				break;
			}
			else {
				edit();
			}
		}
		ivui.close();
		
	}
	
	/** This method build the form whihc is used for the UI of the
	 * text editor
	 */
	
	private void buildUI ( ) {
		
		String[] RadioLabel = {"Insert","Delete","Replace"};
		
		ivui.setTitle("IV");
		ivui.addTextField("L", "Line ", 4, 15, 10);
		ivui.addTextField("S", "Start", 4, 80, 10);
		ivui.addTextField("E", "End  ", 4, 150, 10);
		ivui.addTextField("T", "Text", 30);
		ivui.addRadioButtons("action", "Edit Action", false, RadioLabel);
		ivui.addTextArea("O", "Output", 20, 40);
		ivui.setEditable("O", false);
		
	}
	
	/** This method was my start at setting up and figuring out the
	 * nodes and pointers. 
	 */
	
	private void edit( ) {
		
		String text ="test";
		
		char c;
		Node next = new Node (text.charAt(0), null);
		Node p = next;
		
		for (int i = 1 ; i <= text.length() ; i++) {
			
			c = text.charAt(i);
			p=p.next;
			ivui.writeChar ("O", p.c);
		}

	}
	
	
	public static void main ( String[] args ) {new IV().editText(); };
	
}