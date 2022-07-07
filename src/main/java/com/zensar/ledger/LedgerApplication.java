package com.zensar.ledger;

/**
 * Main class for invoking the Ledger processor to perform the loan calculations
 * 
 * @author shihad
 *
 */
public class LedgerApplication {
	
	/**
	 * Trigger point for starting the processing.
	 * 
	 * @param args Argument variable to pass the file location, if empty select the test file from src/main/resources folder
	 */
	public static void main(String[] args) {
		String fileLocation = "";
		if(null != args && args.length > 0) {
			fileLocation = args[0];
			System.out.println("\nStarted processing the Ledger with file: " + fileLocation);
			String result = new LedgerProcessor().processFile(fileLocation); 
			System.out.println("\nCompleted processing the Ledger, output below: \n\n" + result + "\n");
		}
		else {
			System.out.println("\nInvalid file path, please provide fully qualified filename as arguments \n");
		}
	}

}
