package aima.gui.applications.search.sudoku;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.search.framework.HeuristicFunction;

public class SudokuHillClimbingHeuristic implements HeuristicFunction {

	@Override
	public double h(Object state) {

		
		Sudoku sudoku = (Sudoku) state;

		int grille[][] = sudoku.getGrille();

		Set<Action> actions = new LinkedHashSet<Action>();

		int max1 = -3;
		int max2 = -3;

//		int caseMax[] = {-1, -1};
		
//		String caseMax1 = "";			
		LinkedList<String> casesMax1 = new LinkedList<String>();
		
//		String caseMax2 = "";
		LinkedList<String> casesMax2 = new LinkedList<String>();

		int totalConflits= 0;
		
		for(int i = 0; i < 9 ; i++){
			for(int j = 0; j < 9 ; j++){


				int courant = grille[i][j];

				// devient 0 quand la case X est verifié avec la case X,
				// une fois pour les rangées, une fois  pour les 
				// colonnes et une fois pour la sous-grille
				int nombreConflits = -3;

				//recherche de conflits dans les lignes et colonnes
				for(int k = 0 ; k < 9 ; k++  ){
					if(courant == grille[i][k] || 
							courant == grille[k][j] ){
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

				totalConflits += nombreConflits;
				// On garde les 2 case avec les plus grand nombre 
				// de conflits
				if( max1 < nombreConflits  ){
					max2 = max1;
					max1 = nombreConflits;

					String caseMax = i +""+ j;
																	
					casesMax2 = casesMax1;
					casesMax1 = new LinkedList<String>();
					casesMax1.add(caseMax);
				}					
				else if(max1 == nombreConflits){
					String caseMax = i +""+ j;
					casesMax1.add(caseMax);
				}
				else if(max2 == nombreConflits){
					String caseMax2 = i +""+ j;
					casesMax2.add(caseMax2);
				}
			}
		}

		if(casesMax1.size() > 1 ){
			for(int i = 0 ; i < casesMax1.size() ; i++){
				String c_max1_1 = casesMax1.get(i);
				for(int j = i+1 ; j < casesMax1.size() - i  ; j++){
					String c_max1_2 = casesMax1.get(j);
					actions.add(new DynamicAction(c_max1_1+" "+c_max1_2));
				}
			}				
		}
		else if(casesMax1.size() == 1){
			String c_max1 = casesMax1.get(0);
			for(String c_max2 : casesMax2){
				actions.add(new DynamicAction(c_max1+" "+c_max2));
			}
		}
/*		
		if(casesMax1.size() > 1 ){
			return 1000-(max1 + max1 + casesMax1.size());
		}		
		
		if(casesMax1.size() == 1 ){
			return 1000 -(max1 + max2 + casesMax1.size() +  casesMax2.size());
		}		
		
		return 1;
*/
		
//		System.out.println("debut h" );
//		sudoku.print();
//		System.out.println("h: totalConflits(negative) :  -" + totalConflits); 
//		System.out.println("fin h" );
		return totalConflits - max1;
	}

	
}
