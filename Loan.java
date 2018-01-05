
public class Loan {
	  private float anIntR;
	  private int NumYear;
	  private double lAmount;
	  private java.util.Date lDate;

	  /** Default constructor */
	  public Loan() {
	    this.anIntR = (float)2.5;
	    this.NumYear = 1;
	    this.lAmount =1000;
	  }

	  /** Construct a loan with specified annual interest rate,
	      number of years, and loan amount
	    */
	  public Loan(float anIntR, int NumYear,
	      double lAmount) {
	    this.anIntR = anIntR;
	    this.NumYear = NumYear;
	    this.lAmount = lAmount;
	    lDate = new java.util.Date();
	  }

	  /** Return anIntR */
	  public double getanIntR() {
	    return anIntR;
	  }

	  /** Set a new anIntR */
	  public void setanIntR(float anIntR) {
	    this.anIntR = anIntR;
	  }

	  /** Return NumYear */
	  public int getNumYear() {
	    return NumYear;
	  }

	  /** Set a new NumYear */
	  public void setNumYear(int NumYear) {
	    this.NumYear = NumYear;
	  }

	  /** Return lAmount */
	  public double getlAmount() {
	    return lAmount;
	  }

	  /** Set a newlAmount */
	  public void setlAmount(double lAmount) {
	    this.lAmount = lAmount;
	  }

	  /** Find monthly payment */
	  public double getMonthlyPayment() {
	    double monthlyInterestRate = anIntR / 1200;
	    double monthlyPayment = lAmount * monthlyInterestRate / (1 -
	      (1 / Math.pow(1 + monthlyInterestRate, NumYear * 12)));
	    return monthlyPayment;    
	  }

	  /** Find total payment */
	  public double getTotalPayment() {
	    double totalPayment = getMonthlyPayment() * NumYear * 12;
	    return totalPayment;    
	  }

	  /** Return loan date */
	  public java.util.Date getlDate() {
	    return lDate;
	  }



}
