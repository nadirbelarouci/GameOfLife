package com.Nadir.Digital.JeuDeLaVie.Model;
import java.util.ArrayList;



public class GameOfLife implements Observable{
	
	//la taille du matrice 
	public static final int SIZE=1000;
	private int matrix[][] = new int [SIZE][SIZE];
	
	//la list des observateurs qui observe notre model
	private ArrayList<Observer> listObservers = new ArrayList<Observer>();
	// une liste de cellule celle qui est utilsé dans notre algortithme 
	
	private ArrayList<Cell> cells ;
	
	/**
	 * la classe à un seul constructeur 
	 * son role est d'initialiser la matrice avec une petit droite 
	 * NB: une cellule vivante prend la valeur 1 sinon 0
	 * */
	public GameOfLife() {
		cells = new ArrayList<Cell>();	
	}
	
	/**
	 * cette méthod calcule le nombre de voisin d'une cellule , 
	 * elle applique les règles des jeux , en ajoutant cette cellule a notre liste soit avec une état mourante ou naissance 
	 * @param i position de la cellule dans une colonne 
	 * @param j position de la cellule dans une ligne
	 */
	public void  calculNbrNeighbours(int i ,int j){
		int cellNbr = 0;
		try{		
			// deux boucle pour calculer le nbr de cellule vivantes dans le voisinage 
			for(int k=(i==0)?0:-1;(i==(SIZE-1))?k<1:k<=1;k++){
				for(int l=(j==0)?0:-1;(j==(SIZE-1))?l<1:l<=1;l++){
					
					if(matrix[i+k][j+l]==1){
						if(l!=0 || k!=0){
							cellNbr++;
						}
					}
				}
			}
			
			//application des règles naissance si 3 , survivre si 2 ou 3 	
			if(cellNbr<2||cellNbr>3 && matrix[i][j] == 1){
				cells.add(new Cell(i, j, false));
			}else if(cellNbr == 3 && matrix[i][j] == 0){
				cells.add(new Cell(i, j, true));
			}
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}	
	}
	
	/**
	 * la méthode nextGeneration applique  l'algorithme 
	 * 
	 * */
	public void nextGeneration(){
		// initialisation a liste 
		cells = new ArrayList<Cell>();
		// pour chaque on calcule le nbr de voisins 
		for(int i=0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				calculNbrNeighbours(i, j);			
			}
		}
		
		// pour chaque cellule modifié on fait les modification nécéssaire 
		for (Cell cell : cells) {
			if(cell.isBorn()){
				matrix[cell.getI()][cell.getJ()] = 1;		
			}else{
				matrix[cell.getI()][cell.getJ()] = 0;
			}
		}
		updateObserver();
	}

	
	/**
	 * getter
	 * @return matrix
	 * */
	public int[][] getMatrix() {
		return matrix;
	}
	/**
	 * setter
	 * @param matrix : matrice 
	 * */
	
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
		updateObserver();
	}
	
	/**
	 * la méthode creatCell cree ou tu une cellule 
	 * 
	 * @param i position de la cellule dans une colonne 
	 * @param j position de la cellule dans une ligne
	 * @param type 
	 * */
	public void createCell(int i,int j,int type){
		try {
			if(type == 1){
				matrix[i][j] = 1;
			}	
			else if(type == 3)
				matrix[i][j] = 0;
		} catch (IndexOutOfBoundsException e) {
		}
		updateObserver();
	}

	/**
	 * ajout d'un observateur a notre liste d'observateur
	 * @param obs : Observateur 
	 * */
	@Override
	public void setObserver(Observer obs) {
		this.listObservers.add(obs);
		
	}
	
	/**
	 * notification de chaque observateur pour qu'il se change 
	 * */
	@Override
	public void updateObserver() {
		for (Observer obs : listObservers) {
				obs.update();
		}
		
	}
	/**
	 * suppression de tout les observateur 
	 * */
	@Override
	public void deletObserver() {
		listObservers = new ArrayList<Observer>();		
	}
}
