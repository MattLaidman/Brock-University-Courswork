import BasicIO.*;

/** This class inputs data from a questions bank and asks users a pre-defined
 *  amount. It will also keep a running score, and print out the individual
 *  scores and the overrall average
 *
 * @author Matt Laidman
 *
 * @version 1.0 (January 2013)									             */

public class Question {

	private BasicForm questions, submission;
	private ReportPrinter report;
	ASCIIDataFile qBank;
	
	int numQ, numT; 	// The number of questions in the questions bank, and number of questions on test
	int[] cAnswer;		// The correct answer for the question
	String[] question;  // The list of possible questions
	String[][] answers; // The list of possible answers for each question
	boolean[] asked;	// whether or not the question has been asked yet
	
	/** The constructor opens the data, and creates the report and forms                */
	
	public Question ( ) {
		
		report = new ReportPrinter();
		report.setTitle("COSC 1P02", "Test Results");
		questions = new BasicForm ("OK");	
		submission = new BasicForm ("OK", "Quit");
		qBank = new ASCIIDataFile ("C:\\Users\\Matt\\Desktop\\Test.dat");

	} // constructor

	/** This method begins the test, and asks for the student number of the student taking it
	 * 	and then calls the method askQuestions to get the scores and averages.                 */
	
	private void startTest ( ) {
		
		int sNumber,  score, average; // Student number, score on current test, and	running total/average	
		int sButton, sNum;

		sButton = 0;
		score = 0;
		sNum = 0;
		average = 0;			
		
		numQ = qBank.readInt();
		numT = qBank.readInt();
		
		cAnswer = new int[numQ];
		question = new String[numQ];
		answers = new String[numQ][4];
		asked = new boolean[numQ];
		
		for (int i=0 ; i<numQ ; i++) {
			question[i] = qBank.readLine();
			question[i] = question[i].replace("#", "\n");
			for (int j=0 ; j<=3 ; j++) {
				answers[i][j] = qBank.readLine();
			} // for
			switch (qBank.readChar()) {
				case 'A':
					cAnswer[i] = 0;
					break;
				case 'B':
					cAnswer[i] = 1;
					break;
				case 'C':
					cAnswer[i] = 2;
					break;
				case 'D':
					cAnswer[i] = 3;
					break;
			} // switch
		} // for
		
		buildQForm();
		buildSForm();
		
		
		while (true) {
			for (int i=0 ; i<numQ ; i++) {
				asked[i] = false;
			} // for
			sButton = submission.accept();
			if (sButton == 1) {
				break;
			} // if
			sNumber = submission.readInt("sNum");
			submission.setEditable("sNum", false);
			score = askQuestions();
			submission.writeInt("score", score);
			report.addField ("sField"+sNum, 10);
			report.writeInt("sField"+sNum, sNumber);
			report.addField ("score"+sNum, 5);
			report.writeInt("score"+sNum, score);
			report.newLine();
			sNum++;
			average+=score;
			submission.accept();
			submission.clearAll();
			submission.setEditable("sNum", true);
		} // while
		if (sNum != 0) {
			report.addField ("number", 10);
			report.writeInt("number", sNum+1);
			report.addField ("average", 10);
			report.writeInt("average", (average/sNum+1));
			report.close();
		} // if
		submission.close();
	} // startTest
	
	/** Asks the user random questions from the question bank, calcuates the score
	 *  and then returns it to be added to the average                             */
	
	private int askQuestions () {
		
		int i, score, rNum;
		
		i = 0;
		score = 0; // score for current test being taken
		rNum = 0;
		
		while (i < 5) {
			rNum = (int)(Math.random()*numQ);
			if (!asked[rNum]) {
				questions.writeLine("qBox", question[rNum]); // Sorry, I have no clue what is causing this to go out of bounds
				questions.writeLine("a1", answers[rNum][0]);
				questions.writeLine("a2", answers[rNum][1]);
				questions.writeLine("a3", answers[rNum][2]);
				questions.writeLine("a4", answers[rNum][3]);
				asked[rNum] = true;
				questions.accept();
				if (questions.readInt("aBox") == cAnswer[rNum]) {
					score++;
				} // if
				questions.clearAll();
				i++;
			} // if
		} // while
		questions.close();
		return score; // returns value to be added to average
		
	} // askQuestions
	
	/** This method builds form used for the questions */
	
	private void buildQForm ( ) {
		    
		questions.setTitle("Question");
		questions.addLabel ("qLabel", "Question");
		questions.addTextArea("qBox", 10, 75);
		questions.setEditable("qBox", false);
		questions.addRadioButtons("aBox", "Answer", true, "a", "b", "c", "d");
		questions.addTextField("a1", 70, 60, 244);
		questions.addTextField("a2", 70, 60, 269);
		questions.addTextField("a3", 70, 60, 294);
		questions.addTextField("a4", 70, 60, 319);
		
	  } // buildQForm
	  
	/** This method builds form used for the subission (student number + score) */
	
	  private void buildSForm ( ) {
		  
		  submission.setTitle("1P02");
		  submission.addTextField("sNum", "Student #:", 10);
		  submission.addTextField("score", "Score:", 4);
		  submission.setEditable("score", false);
		  
	  } // buildSForm
	
	public static void main ( String[] args ) {new Question().startTest(); };	
} // Question
