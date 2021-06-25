package it.polito.tdp.crimes.model;

public class Adiacenze {
	private String id1;
	private String id2;
	private int peso;
	public Adiacenze(String id1, String id2, int peso) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.peso = peso;
	}
	public String getId1() {
		return id1;
	}
	public String getId2() {
		return id2;
	}
	public int getPeso() {
		return peso;
	}
	@Override
	public String toString() {
		return "Adiacenze" + id1 + "," + id2 + " peso=" + peso + "\n";
	}
	
	

}
