package aima.gui.applications.search.sudoku;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

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

//			System.out.println("DebutActionFonctins==");
			Sudoku sudoku = (Sudoku) state;
		//	sudoku.print();
			int grille[][] = sudoku.getGrille();
/*			int zero[] = {6 ,1 ,9, 5, 1, 6, 7, 8, 4 };
			int un[] = {5, 8, 7, 3, 8, 4, 9, 2, 6};
			int deux[] = {6, 4, 2, 7, 9, 2, 3, 5, 1};
			int trois[] = {3, 5, 8, 9, 6, 2, 4, 7, 9};
			int quatre[] = {4, 2, 3, 7, 3, 5, 8, 1, 6};
			int cinq[] = {7, 9, 1, 8, 4, 1, 2, 5, 3};
			int sex[] = {1, 5, 2, 4, 7, 9, 9, 6, 3};
			int sept [] = {4, 3, 9, 6, 5, 8, 7, 8, 2};
			int huit[] = {8, 6, 7, 2, 3, 1, 5, 4, 1};
*/
			
//			int zero[] = {5, 8, 4, 5, 8, 6, 3, 9, 4};
//			int un[] = {9, 7, 4, 2, 1, 6, 7, 5, 1};
//			int deux[] = {6, 4, 7, 6, 9, 1, 3, 2, 1};
//			int trois[] = {3, 2, 9, 2, 6, 1, 9, 3, 1};
//			int quatre[] = {5, 3, 9, 7, 9, 8, 5, 7, 6};
//			int cinq[] = {6, 2, 1, 8, 5, 3, 8, 5, 4};
//			int sex[] = {8, 5, 2, 4, 7, 2, 5, 2, 3};
//			int sept [] = {4, 4, 2, 6, 9, 8, 7, 6, 9};
//			int huit[] = {8, 7, 3, 3, 7, 1, 8, 4, 1};
//			
//			grille[0] = zero;
//			grille[1] = un;
//			grille[2] = deux;
//			grille[3] = trois;
//			grille[4] = quatre;
//			grille[5] = cinq;
//			grille[6] = sex;
//			grille[7] = sept;
//			grille[8] = huit;

//			sudoku.print();
					
			Set<Action> actions = new LinkedHashSet<Action>();

			int max1 = -3;
			int max2 = -3;

//			int caseMax[] = {-1, -1};
			
//			String caseMax1 = "";			
			LinkedList<String> casesMax1 = new LinkedList<String>();
			
//			String caseMax2 = "";
			LinkedList<String> casesMax2 = new LinkedList<String>();


			for(int i = 0; i < 9 ; i++){
				for(int j = 0; j < 9 ; j++){


					int courant = grille[i][j];

					// devient 0 quand la case X est verifié avec la case X,
					// une fois pour les rangées, une fois  pour les 
					// colonnes et une fois pour la sous-grille
					int nombreConflits = -3;

					//recherche de conflits dans les lignes et colonnes
					for(int k = 0 ; k < 9 ; k++  ){
						if(courant == grille[i][k] ){
							nombreConflits++;
						}
						if(courant == grille[k][j] ){
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
//					System.out.println(nombreConflits);
					// On garde les 2 case avec les plus grand nombre 
					// de conflits
					if( max1 < nombreConflits  ){

						max2 = max1;
						max1 = nombreConflits;

						String caseMax = i +""+ j;
																		
						casesMax2 = casesMax1;
						casesMax1 = new LinkedList<String>();
						casesMax1.add(caseMax);
//						System.out.println("Max1 " +max1);
//						System.out.println("Max2 " +max2);

					}					
					else if(max1 == nombreConflits){
						String caseMax = i +""+ j;
						casesMax1.add(caseMax);
//						System.out.println("Max1 " +max1);
					}
					else if(max2 < nombreConflits){
						max2= nombreConflits;
						casesMax2 = new LinkedList<String>();
						String caseMax2 = i +""+ j;
						casesMax2.add(caseMax2);
	//					System.out.println("Max2 " +max2);
					}
					else if(max2 == nombreConflits){
						String caseMax2 = i +""+ j;
						casesMax2.add(caseMax2);			
//						System.out.println("Max2 " +max2);
					}
				}
			}

//			System.out.println("casesMax1: "  + casesMax1.size());
//			System.out.println("casesMax2: "  + casesMax2.size());
			if(casesMax1.size() > 1 ){
				for(int i = 0 ; i < casesMax1.size() ; i++){
					String c_max1_1 = casesMax1.get(i);
					for(int j = i+1 ; j < casesMax1.size() - i  ; j++){
						String c_max1_2 = casesMax1.get(j);
						actions.add(new DynamicAction(c_max1_1+" "+c_max1_2));
					}
				}				
			}
//			else if(casesMax1.size() == 1){
//				String c_max1 = casesMax1.get(0);
//				for(String c_max2 : casesMax2){
//					actions.add(new DynamicAction(c_max1+" "+c_max2));
//				}
//			}
			if(casesMax2.size() >= 1){
				for(int i = 0 ; i < casesMax1.size() ; i++){
					String c_max1_1 = casesMax1.get(i);
					for(int j = 0 ; j < casesMax2.size()  ; j++){
						String c_max2_2 = casesMax2.get(j);
						actions.add(new DynamicAction(c_max1_1+" "+c_max2_2));
					}
				}

			}

//			System.out.println(actions.size());
//			System.out.println("FinActionFonctins==");
			return actions;
		}
	}

	/**
	 * 
	 * @author Abel Oria
	 * Génère des nouvelles instances du sudoku  
	 * 
	 */
	private static class SudokuResultFunction implements ResultFunction {

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

//			System.out.println(a.toString());
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
//					System.out.println("enfant cree");
					return newSudoku;
				}
			}
//			System.out.println("enfant on cree");
			return s;
		}
	}
}
