package assignment2;

public class ComNum {
	
	private double real;
	private double imag;
	
	public ComNum(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}

	public double getReal() {
		return real;
	}

	public void setReal(double real) {
		this.real = real;
	}

	public double getImag() {
		return imag;
	}

	public void setImag(double imag) {
		this.imag = imag;
	}
	
	public ComNum add(ComNum aa) {
		double n1 = this.real + aa.getReal();
		double n2 = this.imag + aa.getImag();
		
		return new ComNum(n1, n2);
	}
	
	public ComNum times(ComNum aa) {
		double n1 = this.real * aa.getReal() - this.imag * aa.getImag();
		double n2 = this.real * aa.getImag() + this.imag * aa.getReal();
		
		return new ComNum(n1, n2);
	}
	
	public int magnitude() {
		
		int mag = 0;
		double sum = this.real * this.real + this.imag * this.imag;
		mag = (int) (Math.sqrt(sum))/100;
		
		return mag;
	}
	
	@Override
	public String toString() {
		return real + ", " + imag;
	}
	
	public String print() {
		return roundOff(real) + ", " + roundOff(imag);
	}
	
	public static int roundOff(double num) {
		
		num = num + 0.5;
		int r = (int) num;
		return r;
	}
}
