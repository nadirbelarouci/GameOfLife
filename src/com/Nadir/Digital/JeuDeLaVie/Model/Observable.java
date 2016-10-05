package com.Nadir.Digital.JeuDeLaVie.Model;
public interface Observable {
	
	public void setObserver(Observer obs);
	public void deletObserver();
	public void updateObserver();
}
