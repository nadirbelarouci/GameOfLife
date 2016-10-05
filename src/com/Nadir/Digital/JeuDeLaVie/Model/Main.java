package com.Nadir.Digital.JeuDeLaVie.Model;
import com.Nadir.Digital.JeuDeLaVie.Vue.GameVue;
import com.Nadir.Digital.JeuDeLaVie.Vue.Controller.GameController;

public class Main {

	  public static void main(String[] args)  {
		  
		  //creation du model
		  GameOfLife gameOfLife = new GameOfLife();
		  // creation du controlleur 
		  GameController gameController = new GameController(gameOfLife);
		  // creation du vue
		  GameVue gameVueVue = new GameVue(gameController);
	  }
	

}
