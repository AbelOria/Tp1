package aima.gui.applications.search.sudoku;

import aima.core.search.framework.GoalTest;

public class SudokuGoalTest implements GoalTest {

	private int nombreTests = 0;

	public boolean isGoalState(Object state) {
		nombreTests++;
		boolean goal = true;

		Sudoku sudoku = (Sudoku) state;
		
		int grille[][] = sudoku.getGrille();
		
		//Evaluation des lignes est colonnes: l'addition de
		//chaque ligne et colonne doit �tre 45
		for(int i = 0 ; i < 9 ; i++){
			int accuLigne = 0;
			int accuColonne = 0;
			for(int j = 0 ; j <9; j++){
				accuLigne += grille[i][j];
				accuColonne += grille[j][i];
			}
			
			if(accuLigne != 45 || accuColonne != 45){
				goal = false;
				break;
			}
		}

		//Evaluation des 9 sous grilles 3x3:  l'addition de
		//chaque grille doit �tre 45
		for(int i = 0 ; i < 9 ; i+=3  ){
			for(int j = 0 ; j < 9 ; j += 3){
				
				int accuGrille3x3 = 0;
				
				for(int k = 0; k < 3 ; k++){
					for(int l = 0 ; l <3 ; l++){
						accuGrille3x3 += grille[i+k][j+l];
					}
				}
				if(accuGrille3x3 != 45){
					goal = false;
					break;
				}
			}
		}
//		System.out.println("++++++isGoal:  " + goal + "   nombreTest: "+ nombreTests);
		return goal;
	}
	
	
	public int getNombreTests(){
		return nombreTests;
	}
	
	//Evaluation de la fonction isGoalState()
	public static void main(String arg[]){
		Sudoku pasBon = new Sudoku(		"200060000007004086000001300"
									+	"000000040090000000480000710"
									+ 	"900078000000050002020600501");

		Sudoku bon = new Sudoku(	"782519436596342871314867295"
								+	"251738649937654128648921753"
								+	"125483967873296514469175382");

		SudokuGoalTest test = new SudokuGoalTest();
//		System.out.println(test.isGoalState(pasBon));
//		System.out.println(test.isGoalState(bon));
	} 
}