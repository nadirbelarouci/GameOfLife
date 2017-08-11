package com.Nadir.Digital.JeuDeLaVie.Model;

public class Cell {
	
	private int i;
	private int j;
	private boolean born;
	
	public Cell(int i,int j,boolean born) {
		this.i = i;
		this.j = j;
		this.born = born;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	public boolean isBorn() {
		return born;
	}
	public void setBorn(boolean born) {
		this.born = born;
	}	
}
