package com.zensar.ledger.model;

/**
 * POJO class for Loan
 * 
 * @author shihad
 *
 */
public class Loan {

	private String bank;
	private String borrower;
	private long principal;
	private int tenure;
	private double rateOfInterest;
	private double balance;
	private long emiAmount;
	private int remianingNoOfEMI;
	private long interest;
	private long totalEMIPaid;
	private long totalAmountPaid;
	private int emiNumWithLumpsum;
	private long lumpsumPaid;
	private boolean paymentFlag = false;
	private static final String SPACE = " ";

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public long getPrincipal() {
		return principal;
	}

	public void setPrincipal(long principal) {
		this.principal = principal;
	}

	public int getTenure() {
		return tenure;
	}

	public void setTenure(int tenure) {
		this.tenure = tenure;
	}

	public double getRateOfInterest() {
		return rateOfInterest;
	}

	public void setRateOfInterest(double interest) {
		this.rateOfInterest = interest;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public long getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(long emiAmount) {
		this.emiAmount = emiAmount;
	}

	public int getRemianingNoOfEMI() {
		return remianingNoOfEMI;
	}

	public void setRemianingNoOfEMI(int remianingNoOfEMI) {
		this.remianingNoOfEMI = remianingNoOfEMI;
	}

	public long getInterest() {
		return interest;
	}

	public void setInterest(long interest) {
		this.interest = interest;
	}

	public long getTotalAmountPaid() {
		return totalAmountPaid;
	}

	public void setTotalAmountPaid(long totalAmountPaid) {
		this.totalAmountPaid = totalAmountPaid;
	}

	public long getTotalEMIPaid() {
		return totalEMIPaid;
	}

	public void setTotalEMIPaid(long totalEMIPaid) {
		this.totalEMIPaid = totalEMIPaid;
	}

	public long getLumpsumPaid() {
		return lumpsumPaid;
	}

	public void setLumpsumPaid(long lumpsumPaid) {
		this.lumpsumPaid = lumpsumPaid;
	}

	public int getEmiNumWithLumpsum() {
		return emiNumWithLumpsum;
	}

	public void setEmiNumWithLumpsum(int emiNumWithLumpsum) {
		this.emiNumWithLumpsum = emiNumWithLumpsum;
	}

	public boolean isPaymentFlag() {
		return paymentFlag;
	}

	public void setPaymentFlag(boolean paymentFlag) {
		this.paymentFlag = paymentFlag;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(getBank()).append(SPACE).append(getBorrower()).append(SPACE)
				.append(getTotalAmountPaid()).append(SPACE).append(getRemianingNoOfEMI()).toString();
	}

}
