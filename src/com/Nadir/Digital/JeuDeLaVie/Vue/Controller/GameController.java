package com.Nadir.Digital.JeuDeLaVie.Vue.Controller;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.Nadir.Digital.JeuDeLaVie.Model.GameOfLife;

public class GameController {
	
	private GameOfLife gameOfLife;
	
	public GameController(GameOfLife gameOfLife) {
		this.gameOfLife =gameOfLife;
	}
	
	public GameOfLife getGameModel(){
		return gameOfLife;
	}
	
	
	public void createCell(int i,int j,int type){
		gameOfLife.createCell(i, j,type);
	}
	
	public void animate(){
		gameOfLife.nextGeneration();
	}
	
	public void clear(){
		gameOfLife.setMatrix(new int[GameOfLife.SIZE][GameOfLife.SIZE]);
		gameOfLife.updateObserver();
	}

	public void setConfigurations(int selectionIndex) {
		int matrix[][] = new int[GameOfLife.SIZE][GameOfLife.SIZE];
		switch (selectionIndex) {
		case 0:
			for (int i = 10; i <= 11; i++) {
				for (int j = 10; j <= 11; j++) {
					matrix[i][j] = 1;
					
				}
				
			}
			break;
		case 1:
			matrix[1][1] = matrix [1][2] = matrix[2][1] = matrix [3][2] = matrix[2][3] = 1;
			break;
		case 2:
			matrix[1][1] = matrix [1][2] = matrix[1][3] = matrix [2][2] = matrix[2][3] =matrix[2][4]= 1;
			break;
		case 3:
			for (int i = 10; i <= 19; i++) {
				if(i!=12 && i != 17)
					matrix[10][i] = 1;
				else{
					matrix[9][i] = 1;
					matrix[11][i] = 1;
				}
			}
			break;
		case 4:
			for (int i = 10; i <= 12; i++) {
				matrix[10][i] = 1;
				
			}
			break;
		case 5:
			for (int i = 10; i <= 11; i++) {
				for (int j = 10; j <= 11; j++) {
					matrix[i][j] = 1;
					
				}
				
			}
			
			for (int i = 12; i <= 13; i++) {
				for (int j = 12; j <= 13; j++) {
					matrix[i][j] = 1;
					
				}
			}
			break;
		case 6:
			matrix[10][10] = 1;
			matrix[11][11] = 1;
			matrix[12][9] = matrix[12][10]=matrix[12][11] = 1;
			break;
		case 7:
			matrix[10][10] = 1;
			matrix[10][13] = 1;
			matrix[12][10] = 1;
			matrix[11][14] = 1;
			matrix[12][14] = 1;
			matrix[13][14] = 1;
			matrix[13][11] = 1;
			matrix[13][12] = 1;
			matrix[13][13] = 1;
			break;
		case 8:
			
			matrix[9][11] = 1;
			matrix[10][9] = 1;
			matrix[12][9] = 1;
			matrix[10][13] = 1;
			matrix[11][14] = 1;
			matrix[12][14] = 1;
			matrix[13][14] = 1;
			matrix[13][10] = 1;
			matrix[13][11] = 1;
			matrix[13][12] = 1;
			matrix[13][13] = 1;
			break;
		case 9:
			loadConfiguration("C:/Users/Admin/Documents/JeuDeLaVie/canon.jdv");
			
			break;
			

		default:
			break;
		}
		if(selectionIndex!=9)
		gameOfLife.setMatrix(matrix);
		
		
	}
	
	public void loadConfiguration(String filePath){
		if(filePath == null)
			return;
		BufferedInputStream bufferedInputStream = null;
		int matrix[][] = new int[GameOfLife.SIZE][GameOfLife.SIZE];
		int i=0,j=0;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
			byte line[] = new byte[GameOfLife.SIZE];
			while(bufferedInputStream.read(line)!=-1){
				for (byte b : line) {
					matrix[i][j] = b;
					
					if(++j==GameOfLife.SIZE){
						
						i++;
						j=0;
					}
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
				
			gameOfLife.setMatrix(matrix);
			
			try {
				bufferedInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	public void saveConfiguration(String fileDirectory){
		
		if(fileDirectory == null)
			return;
		BufferedOutputStream bufferedOutputStream = null;
		
		
		try {
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(fileDirectory)));
			int matrix[][] = gameOfLife.getMatrix();
			byte byteMatrix[][] = new byte[GameOfLife.SIZE][GameOfLife.SIZE];
			for(int i=0;i<GameOfLife.SIZE;i++){
				for(int j=0;j<GameOfLife.SIZE;j++){
					byteMatrix[i][j] = (byte) matrix[i][j];
				}
					
			}
			
					
			for (byte[] line : byteMatrix) {
				bufferedOutputStream.write(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
				
			
			try {
				bufferedOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
}
