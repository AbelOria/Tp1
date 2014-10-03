package aima.gui.applications.search.sudoku;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.search.framework.HeuristicFunction;

public class SudokuAstarHeuristic implements HeuristicFunction {

	
	public SudokuAstarHeuristic(){}
	
	@Override
	public double h(Object state) {
		Sudoku sudoku = (Sudoku) state;
//		sudoku.print();
		
		int grille[][] = sudoku.getGrille();	

		int min = 10;
		
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
					}
				}
			}
		}


		if(min== 1) return -100;

		
		
		
		return min;
	}

}
