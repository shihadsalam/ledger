package com.zensar.ledger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zensar.ledger.model.Loan;

/**
 * Ledger processor which performs the loan calculation based on the input file path
 * 
 * @author shihad
 *
 */
public class LedgerProcessor {
	
	private static final String LOAN_CMD = "LOAN";
	private static final String BALANCE_CMD = "BALANCE";
	private static final String PAYMENT_CMD = "PAYMENT";
	
	private Map<String, Loan> loanMap = new HashMap<>();
	private List<String> result = new ArrayList<>();
	
	/**
	 * Processor method for the ledger processing
	 * 
	 * @param path input file path
	 * @return output after processing the input file
	 */
	public String processFile(String path) {
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file location");
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String strLine;
		List<String> words = new ArrayList<String>();
		try {
			while ((strLine = reader.readLine()) != null) {
				words = Arrays.asList(strLine.split(" "));
				if (!words.isEmpty()) {
					process(words);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception occurred while processing file");
			e.printStackTrace();
		}
		return String.join("\n", result); 
	}

	private void process(List<String> words) {
		String command = words.get(0);
		switch (command) {
			case LOAN_CMD: {
				processLoan(words);
				break;
			}
			case PAYMENT_CMD: {
				processPayment(words);
				break;
			}
			case BALANCE_CMD: {
				result.add(processBalance(words));
				break;
			}
		}
	}

	private void processLoan(List<String> words) {
		String bankName = words.get(1);
		String borrowerName = words.get(2);
		long principal = Math.round(Double.valueOf(words.get(3)));
		int tenure = Integer.valueOf(words.get(4));
		double rateOfInterest = Double.valueOf(words.get(5))/100;
		Loan loan = new Loan();
		loan.setBorrower(borrowerName);
		loan.setBank(bankName);
		loan.setPrincipal(principal);
		loan.setRateOfInterest(rateOfInterest);
		loan.setTenure(tenure);
		
		long interest = getInterest(loan);
		loan.setInterest(interest);
		long balance = principal + interest;
		loan.setBalance(balance);
		
		long emi = calculateEMI(loan);
		loan.setEmiAmount(emi);
		
		loan.setRemianingNoOfEMI(tenure * 12);
		loanMap.put(getCompositeKey(words), loan);
	}
	
	private void processPayment(List<String> words) {
		String compositeKey = getCompositeKey(words);
		long lumpsum = Long.valueOf(words.get(3));
		int emiNum = Integer.valueOf(words.get(4));
		Loan loan = loanMap.get(compositeKey);
		loan.setPaymentFlag(true);
		
		double priorEMIAmt = emiNum * loan.getEmiAmount();
		loan.setTotalEMIPaid(Math.round(priorEMIAmt));
		
		loan.setLumpsumPaid(loan.getLumpsumPaid() + lumpsum);
		loan.setEmiNumWithLumpsum(emiNum);
		
		loan.setTotalAmountPaid(loan.getTotalEMIPaid() + loan.getLumpsumPaid());
		
		long updatedLoanBalance =  Math.round(loan.getBalance() - loan.getTotalAmountPaid());
		loan.setBalance(updatedLoanBalance);
		
		double remainingNoOfEMI = Math.ceil((updatedLoanBalance / loan.getEmiAmount()) + ((updatedLoanBalance % loan.getEmiAmount()) > 0 ? 1 : 0));
		int remEMI = (int) remainingNoOfEMI;
		loan.setRemianingNoOfEMI((remEMI));
		
		loanMap.put(getCompositeKey(words), loan);
	}
	
	private String processBalance(List<String> words) {
		String compositeKey = getCompositeKey(words);
		int emiNum = Integer.valueOf(words.get(3));
		Loan loan = loanMap.get(compositeKey);
		double priorEMIAmt = emiNum * loan.getEmiAmount();
		loan.setTotalEMIPaid(Math.round(priorEMIAmt));
		long totalAmountPaid;
		
		int lumpsumEMINum = loan.getEmiNumWithLumpsum();
		if (emiNum < lumpsumEMINum) {
			totalAmountPaid = loan.getTotalEMIPaid();
			loan.setRemianingNoOfEMI(loan.getTenure() * 12 - emiNum);
		}
		else {
			double fullAmnt = loan.getPrincipal() + loan.getInterest();
			double emiAmntWithLumpsum = (loan.getEmiAmount() * emiNum) + loan.getLumpsumPaid();
			double remEMI = (fullAmnt - emiAmntWithLumpsum)/loan.getEmiAmount();
			int remainingEMI = (int) Math.ceil(remEMI);
			loan.setRemianingNoOfEMI(remainingEMI);
			totalAmountPaid = loan.getTotalEMIPaid() + loan.getLumpsumPaid();
		}
		loan.setTotalAmountPaid(totalAmountPaid);
		
		if(!loan.isPaymentFlag()) {
			loan.setRemianingNoOfEMI(loan.getTenure() * 12 - emiNum);
		}
		
		return loan.toString();
	}
	
	private String getCompositeKey(List<String> words) {
		String bankName = words.get(1);
		String borrowerName = words.get(2);
		return bankName.concat(borrowerName);
		 
	}
	
	private long getInterest(Loan loan) {
		double interest = loan.getPrincipal() * loan.getTenure() * loan.getRateOfInterest();
		return Math.round(interest); 
	}
	
	private long calculateEMI(Loan loan) {
		double emi = loan.getBalance() / (loan.getTenure() * 12);
		return (long) Math.ceil(emi);
	}
	
}
