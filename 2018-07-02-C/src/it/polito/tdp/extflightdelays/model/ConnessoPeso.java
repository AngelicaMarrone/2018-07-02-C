package it.polito.tdp.extflightdelays.model;

public class ConnessoPeso implements Comparable<ConnessoPeso> {

	private Airport a;
	private double peso;
	public ConnessoPeso(Airport a, double peso) {
		super();
		this.a = a;
		this.peso = peso;
	}
	public Airport getA() {
		return a;
	}
	public void setA(Airport a) {
		this.a = a;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(ConnessoPeso altro) {
	
		return (int) -(this.peso-altro.getPeso());
	}
	
}
