package test;

import javax.swing.JOptionPane;

public class dialogueTest {
	public static void main(String [] args) {
	String [] options = {
			"Save",
			"Save as",
			"Don't save"};
	
    JOptionPane.showOptionDialog(null, 
    	      "Configuration has been modified !",
    	      "Information",
    	      JOptionPane.INFORMATION_MESSAGE,
    	      JOptionPane.QUESTION_MESSAGE, null, 
    	      new String[]{
    				"Save",
    				"Save as",
    				"Don't save"}, 
    	      null); 

			
	
	
}
}
