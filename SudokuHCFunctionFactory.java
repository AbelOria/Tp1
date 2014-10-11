package aima.gui.applications.search.sudoku;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;
/**
 *  @author Abel Oria, Zakaria Soliman
 */
public class SudokuHCFunctionFactory {

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


	public static void main(String arg[]){
		ActionsFunction af = SudokuHCFunctionFactory.getActionsFunction();
		af.actions(new Sudoku("123456789123456789123456789123456789123456789123456789123456789123456789123456789"));
	}

	/**
	 * 
	 * @author Abel Oria ,Zakaria Soliman
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
			int etat[][] = sudoku.getGrille();

			Set<Action> actions = new LinkedHashSet<Action>();

			int g = 0;
			// Saut de sous-grilles 3x3
			while(g < 7){
				int k = 0;
				while( k < 7){
					
					//positionnement dans chaqeu case de la sous-grille 3x3
					for(int m = 0 ; m <  3 ; m++){
						for(int n = 0  ; n <  3 ; n++ ){
							int i_actuel = g+m;
							int j_actuel = k+n;
							String caseActuel =i_actuel+""+ j_actuel;  
							//parcours de tous la sous-grille pour generer les actions  
							for(int q = 0 ; q < 3 ; q++){
								for(int p = 0 ; p < 3 ; p++){
									int  i_changer = g+q;
									int  j_changer = k+p;
									String caseChanger =i_changer+""+ j_changer;  
									if(!caseActuel.equals(caseChanger)){
										actions.add(new DynamicAction(caseActuel+" "+caseChanger));
			//							System.out.println(new DynamicAction(caseActuel+" "+caseChanger) + "---");
										
									}
								}
							}
						}							
					}

					k += 3; 
				}g += 3; 
			}		

			//  		actions.add(new DynamicAction(c_max1_1+" "+c_max2_2));

			return actions;
		}
	}

	/**
	 * 
	 * @author Abel Oria, Zakaria Soliman
	 * Génère des nouvelles instances du sudoku  
	 * 
	 */
	private static class SudokuResultFunction implements ResultFunction {

		private static int noeudsCrees;
		/**
		 * @param s Une instance de sudoku
		 * @param a Une action à applique a "s" 
		 * @return Une nouvelle instance de sudoku où "s" si la action "a" 
		 * n'a pas était appliqué
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

			int action[] = Sudoku.actionsHillClimbing.get(a);

			int c1 = grille[action[0]][action[1]];
			int c2 = grille[action[2]][action[3]];

			if(c1 != c2 && action.length==4 && action[0] >=0 && 
					action[1] >=0 && action[2] >=0 && action[3] >=0 ){

				String action1 = action[0]+""+action[1];
				String action2 = action[2]+""+action[3];

				if(!action1.equals(action2)){
					int valeurCase1 = grille[action[0]][action[1]];
					int valeurCase2 = grille[action[2]][action[3]];

					Sudoku newSudoku = new Sudoku(newGrille);
					newSudoku.setCasse(action[0], action[1], valeurCase2);
					newSudoku.setCasse(action[2], action[3], valeurCase1);
					noeudsCrees++;
					return newSudoku;
				}
			}
			return s;
		}		
	}
}
