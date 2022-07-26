package tests;

import javax.swing.*;

public class JoptionPaneTest {
	
   public static void main(String[] args) {
	   
      String[] options = new String[] {"Yes", "No", "Maybe", "Cancel"};
      int response = JOptionPane.showOptionDialog(null, "Message", "Title",
          JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
          null, options, options[0]);
      
      System.out.println(response);
      switch (response) {
      case 0:
    	  System.out.println("Yes");
    	  break;
      case 1:
    	  System.out.println("No");
    	  break;
      case 2:
    	  System.out.println("Mby");
    	  break;
      case 3:
    	  System.out.println("Cancel");
    	  break;
    	  
      }
      System.exit(0);
   }
}
