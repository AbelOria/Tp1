package aima.gui.applications.search.sudoku;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

public class SudokuFunctionFactory {

	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new SudokuActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new SudokuResultFunction();
		}
		return _resultFunction;
	}

	private static class SudokuActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			
			Sudoku sudoku = (Sudoku) state;
			int grille[][] = sudoku.getGrille();

			Set<Action> actions = new LinkedHashSet<Action>();

//  		boolean positionTrouve = false;
//			// Genere exception Java heap space
//			for(int i = 0; i < 9 ; i++){
//				for(int j = 0; j < 9 ; j++){
//					if(grille[i][j] == 0 && !positionTrouve){
//						for(int k = 1 ; k <= 9 ; k++){
//							Action casse = new DynamicAction(""+k);
//							actions.add(casse);
//						}
//						positionTrouve = true;
//					}
//				}
//			}
			

			
			
			
//			//mieux que avant mais trop de temps 
//			boolean positionTrouve = false;
//			LinkedList<String> numbers = new LinkedList<String>();
//			for(int i = 1 ; i <=9 ; i++ ){
//				numbers.add(i+"");
//			}
//
//			for(int i = 0; i < 9 ; i++){
//				for(int j = 0; j < 9 ; j++){
//					
//					if(grille[i][j] == 0 && !positionTrouve){
//					
//						positionTrouve = true;
//						
//						//Elimination de numbers dans les lignes et colonnes
//						for(int k = 0 ; k < 9 ; k++  ){
//							numbers.remove(grille[i][k]+"");
//							numbers.remove(grille[k][j]+"");
//						}
//						
//						//Elimination de numbers dans la sous-grille 3x3
//						for(int m = 0 ; m <  3 ; m++){
//							for(int n = 0  ; n <  3 ; n++ ){
//								int p = (i/3)*3 + m;
//								int q = (j/3)*3 + n;
//								numbers.remove(grille[p][q]+"");
//							}							
//						} 
//
//						//Creation des actions
//						for(String number : numbers){
//							Action casse = new DynamicAction(i+""+j+""+number);
//							actions.add(casse);							
//						}							
//					}
//				}
//			}
			

			//Bonne implementation
			int min = 9;
			
			for(int i = 0; i < 9 ; i++){
				for(int j = 0; j < 9 ; j++){
					
					if(grille[i][j] == 0 ){
											
						Set<String> numbers = new LinkedHashSet<String>();
						for(int n = 1 ; n <=9 ; n++ ){
							numbers.add(n+"");
						}

						//Elimination de numbers dans les lignes et colonnes
						for(int k = 0 ; k < 9 ; k++  ){
							numbers.remove(grille[i][k]+"");
							numbers.remove(grille[k][j]+"");
						}
						
						//Elimination de numbers dans la sous-grille 3x3
						for(int m = 0 ; m <  3 ; m++){
							for(int n = 0  ; n <  3 ; n++ ){
								int p = (i/3)*3 + m;
								int q = (j/3)*3 + n;
								numbers.remove(grille[p][q]+"");
							}							
						} 												
						
						if(numbers.size() < min ){
							min = numbers.size();
							actions.clear();
							for(String number : numbers){
								Action casse = Sudoku.actions.get(i+""+j+""+number);
								actions.add(casse);							
							}														
						}
					}
				}
			}
			
			
//			//profondeur d'abord  bad bad
//			int min = 9;
//			
//			for(int i = 0; i < 9 ; i++){
//				for(int j = 0; j < 9 ; j++){
//					
//					if(grille[i][j] == 0 ){
//											
//						Set<String> numbers = new LinkedHashSet<String>();
//						for(int n = 1 ; n <=9 ; n++ ){
//							numbers.add(n+"");
//						}
//
//						//Elimination de numbers dans les lignes et colonnes
//						for(int k = 0 ; k < 9 ; k++  ){
//							numbers.remove(grille[i][k]+"");
//							numbers.remove(grille[k][j]+"");
//						}
//						
//						//Elimination de numbers dans la sous-grille 3x3
//						for(int m = 0 ; m <  3 ; m++){
//							for(int n = 0  ; n <  3 ; n++ ){
//								int p = (i/3)*3 + m;
//								int q = (j/3)*3 + n;
//								numbers.remove(grille[p][q]+"");
//							}							
//						} 												
//						
//						for(String number : numbers){
//							Action casse = Sudoku.actions.get(i+""+j+""+number);
//							actions.add(casse);							
//						}														
//					}
//				}
//			}			
			
			return actions;
		}
	}

	private static class SudokuResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {

			Sudoku sudoku = (Sudoku) s;
			int grille[][] = sudoku.getGrille();
						
			int newGrille[][] = new int[9][9];
			for(int i= 0 ; i < 9 ; i++){
				for(int j = 0 ; j < 9 ;  j++){
					int pp = grille[i][j];
					newGrille[i][j] = pp;
				}
			}
			
//////			System.out.println("generer avec action: "+ a.toString());
			for(int i = 0 ; i < 9 ; i++ ){
				for(int j = 0 ; j < 9 ; j++ ){
					for(int k = 1 ; k <= 9 ; k++){

						if(Sudoku.actions.get(i+""+j+""+k).equals(a) ){
							Sudoku newSudoku = new Sudoku(newGrille);
							newSudoku.setCasse(i, j, k);
//////								System.out.println("generer?: "+i+j+k + "  avec action: "+ a.toString());
//////								System.out.println("enfant_genere: " + i +j +k);
							return newSudoku;
						}
					}
				}
			}

			
			
//			int ii = -1;
//			int jj = -1;
//			boolean trouve = false;
//			for(int i = 0 ; i < 9 ; i++){
//				for(int j = 0 ; j < 9 ; j++){
//					if(grille[i][j] == 0 && !trouve){
//						ii = i;
//						jj = j;
//						trouve = true;
//					}
//				}
//			}
// 
//			if(ii>=0){
//				int newGrille[][] = new int[9][9];
//				for(int i= 0 ; i < 9 ; i++){
//					for(int j = 0 ; j < 9 ;  j++){
//						int pp = grille[i][j];
//						newGrille[i][j] = pp;
//					}
//				}
//				if(sudoku.UN.equals(a)){
//					Sudoku newSudoku = new Sudoku(newGrille);
//					newSudoku.setCasse(ii, jj, 1);
//					return newSudoku;
//				}
//				else if(sudoku.DEUX.equals(a)){
//					Sudoku newSudoku = new Sudoku(newGrille);
//					newSudoku.setCasse(ii, jj, 2);
//					return newSudoku;
//				}
//				else if(sudoku.TROIS.equals(a)){
//					Sudoku newSudoku = new Sudoku(newGrille);
//					newSudoku.setCasse(ii, jj, 3);
//					return newSudoku;
//				}
//				else if(sudoku.QUATRE.equals(a)){
//					Sudoku newSudoku = new Sudoku(newGrille);
//					newSudoku.setCasse(ii, jj, 4);
//					return newSudoku;
//				}
//				else if(sudoku.CINQ.equals(a)){
//					Sudoku newSudoku = new Sudoku(newGrille);
//					newSudoku.setCasse(ii, jj, 5);
//					return newSudoku;
//				}
//				else if(sudoku.SIX.equals(a)){
//					Sudoku newSudoku = new Sudoku(newGrille);
//					newSudoku.setCasse(ii, jj, 6);
//					return newSudoku;
//				}
//				else if(sudoku.SEPT.equals(a)){
//					Sudoku newSudoku = new Sudoku(newGrille);
//					newSudoku.setCasse(ii, jj, 7);
//					return newSudoku;
//				}
//				else if(sudoku.HUIT.equals(a)){
//					Sudoku newSudoku = new Sudoku(newGrille);
//					newSudoku.setCasse(ii, jj, 8);
//					return newSudoku;
//				}
//				else if(sudoku.NEUF.equals(a)){
//					Sudoku newSudoku = new Sudoku(newGrille);
//					newSudoku.setCasse(ii, jj, 9);
//					return newSudoku;
//				}
//			}
			return s;
		}
	}
}
