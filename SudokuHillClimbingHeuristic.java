package aima.gui.applications.search.sudoku;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.search.framework.HeuristicFunction;
/**
 * 
 * @author Abel Oria, Zakaria Soliman
 *
 */
public class SudokuHillClimbingHeuristic implements HeuristicFunction {

	@Override
	public double h(Object state) {
		
		Sudoku sudoku = (Sudoku) state;

		int grille[][] = sudoku.getGrille();

		int nombreConflits = 0;
		
		for(int i = 0; i < 9 ; i++){
			for(int j = 0; j < 9 ; j++){

				int courant = grille[i][j];

				//recherche de conflits dans les lignes et colonnes
				for(int k = 0 ; k < 9 ; k++  ){
					if(courant == grille[k][j] ){
						
					}
					if(courant == grille[i][k]){
						nombreConflits++;
					}
				}

				//recherche de conflits dans la sous-grille 3x3
				for(int m = 0 ; m <  3 ; m++){
					for(int n = 0  ; n <  3 ; n++ ){
						int p = (i/3)*3 + m;
						int q = (j/3)*3 + n;
						if(courant == grille[p][q]){
							nombreConflits++;
						}
					}							
				} 												
			}
		}
		return nombreConflits;
	}
}
