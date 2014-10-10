package aima.gui.applications.search.sudoku;

import java.util.LinkedHashSet;
import java.util.Set;
import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * 
 * @author Abel Oria, Zakaria Soliman (Modification de HeighPuzzleFunctionFactory)
 * Gère la création des class SudokuActionsFunction et SudokuResultFunction 
 *
 */
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

	/**
	 * 
	 * @author Abel Oria
	 * Permet la creations des actions pour une etat donné du sudoku en 
	 * utilisant la premier case avec le plus petit nombre de actions 
	 * possibles
	 */
	private static class SudokuActionsFunction implements ActionsFunction {
		
		/**
		 * @param Un etat du sodoku
		 * @return Liste des action pour la premier case du sudoku contenat 
		 * la valeur 0 et que a le plus petit nombre d'options parmis tous les
		 * case = 0  du sudoku 
		 */
		public Set<Action> actions(Object state) {

			Sudoku sudoku = (Sudoku) state;
			int grille[][] = sudoku.getGrille();

			Set<Action> actions = new LinkedHashSet<Action>();

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
			return actions;
		}
	}

	/**
	 * 
	 * @author Abel Oria
	 * Génère des nouvelles instances du sudoku  
	 */
	private static class SudokuResultFunction implements ResultFunction {
		
		/**
		 * @param s Une instance de sudoku
		 * @param a Une action à applique a "s" 
		 * @return Une nouvelle instance de sudoku où "s" si la action "a" n'a pas 
		 * était appliqué
		 */
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

			for(int i = 0 ; i < 9 ; i++ ){
				for(int j = 0 ; j < 9 ; j++ ){
					for(int k = 1 ; k <= 9 ; k++){

						if(Sudoku.actions.get(i+""+j+""+k).equals(a) ){
							Sudoku newSudoku = new Sudoku(newGrille);
							newSudoku.setCasse(i, j, k);
							return newSudoku;
						}
					}
				}
			}

			return s;
		}
	}
}