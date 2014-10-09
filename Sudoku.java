package aima.gui.applications.search.sudoku;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;

/**
 * 
 * @author Abel oria
 * Representation d'une grille de sudoku
 *
 */
public class Sudoku {

	/** Tous les actiones possibles pour une grille de sudoku o� tous
	 * les cases son 0s	 */
	public static HashMap<String, Action> actions ;

	/** Tous les actiones de interchangement de cases */
	public static HashMap<Action, int[]> actionsHillClimbing ;


	private int[][] etat = null;

	public Sudoku(String etat){
		this.etat = createEtat(etat);
		creerActions();
		creerActionsHillClimbing();
	}

	public Sudoku(int[][] etat){
		this.etat = etat;
		creerActions();
		creerActionsHillClimbing();
	}

	public int[][] getGrille() {
		return etat;
	}

	public void setCasse(int i, int j, int valeur){
		etat[i][j] = valeur;
	}

	/**
	 * Affiche sur la console de sortie la matrice du sudoku sous forme d'un 
	 * grille 9x9
	 */
	public void print(){
		for(int i =0 ; i < 9; i++){
			for(int j = 0 ; j< 9; j++){

				System.out.print ( etat[i][j]+" " );
			}
			System.out.println();
		}
	}

	/**
	 * G�n�re tous les actions possibles pour une grille de Sudoku o� tous les
	 * cases sont 0 (9*9*9 actions g�n�r�s) 
	 */
	private void creerActions(){
		actions = new HashMap<String, Action>();
		for(int i = 0 ; i < 9 ; i++ ){
			for(int j = 0 ; j < 9 ; j++ ){
				for(int k = 1 ; k <= 9 ; k++){
					actions.put(i+""+j+""+k, new DynamicAction(i+""+j+""+k));
				}
			}
		}
	}


	/**
	 * G�n�re tous les actions possibles pour une grille de Sudoku qui utilis�
	 * l'acciont d'interchanger les cases de la grille
	 */	
	private void creerActionsHillClimbing(){
		actionsHillClimbing = new HashMap<Action, int[]>();
		for(int i = 0 ; i < 9 ; i++){
			for(int j = 0 ; j < 9 ; j++){		
				for(int k = 0 ; k < 9 ; k++){
					for(int l = 0 ; l < 9 ; l++){
						int casesAChanger[] = {i,j,k,l};
						actionsHillClimbing.put(
								new DynamicAction(i+""+j+" "+k+""+l),
								casesAChanger);
					}
				}
			}
		}
	}


	/**
	 * Remplisage aleatoire des cases 0 d'une grille sudoku (etat),
	 * utilis� pour g�n�rer un �tat initial pour la recherche
	 * Hill Climbing
	 */
	public void remplirAleatoirementHillClimbing(){

		//Generation de tous les nombres � placer dans une grille vide
		LinkedList<Integer> nombresARemplir = new LinkedList <Integer>();
		for(int i = 0 ; i < 9 ; i++){
			for(int j = 0 ; j < 9 ; j++){
				nombresARemplir.add(j+1);
			}
		}

		//Elimination de nombres qui se trouven dans la grille
		for(int i = 0 ; i < 9 ; i++){
			for(int j = 0 ; j < 9 ; j++){
				if(etat[i][j] != 0 ){
					Integer n = etat[i][j];
					nombresARemplir.remove(n);
				}
			}
		}

		// Remplisage de la grille de fa�on aleatoire 
		for(int i = 0 ; i < 9 ; i++){
			for(int j = 0 ; j < 9 ; j++){
				if(etat[i][j] == 0){
					int random = (int)(Math.random()*nombresARemplir.size());
					etat[i][j] = nombresARemplir.remove(random);
				}
			}
		}	
	}


	/**
	 * 
	 * @param etat Un ch�ne de carat�res contenant 81 chiffres �lus entre 0 et 9
	 * @return une matrice 9x9 contennant les 81 chiffres re�us dans etat  
	 */
	private int[][] createEtat(String etat) {

		int etat_courrant[][] = new int[9][9];
		int n = 0;

		for(int i =0 ; i < 9; i++){
			for(int j = 0 ; j< 9; j++){
				etat_courrant[i][j] = 
						Integer.parseInt( (String) etat.subSequence(n,n+1));
				n++;
			}
		}
		return etat_courrant;
	}

	/**
	 * 
	 * @param actions
	 * @param typeAction 0 pour PDA, 1 pour MDA, 2 pour Hill Climbing 
	 */
	public void remplirSolution(List<Action> actions , int typeAction) {
		if(typeAction == 0 || typeAction == 1){
			remplirSolutionPDA_MDA(actions);
		}
		else if(typeAction == 2 ){
			remplirSolutionHC(actions);
		}		
	}

	private void remplirSolutionPDA_MDA(List<Action> actions) {
		for(Action action: actions){
			for(int i = 0 ; i < 9 ; i++ ){
				for(int j = 0 ; j < 9 ; j++ ){
					for(int k = 1 ; k <= 9 ; k++){
						Action actionCourrent= new DynamicAction(i+""+j+""+k);
						if(actionCourrent.equals(action)){
							etat[i][j]  = k;
						}
					}
				}
			}		
		}		
	}

	private void remplirSolutionHC(List<Action> actions) {
		for(Action action :actions){
			if(!action.toString().endsWith("Action[name==NoOp]")){
				int i1 = Integer.parseInt(action.toString().substring(13, 14));
				int j1 = Integer.parseInt(action.toString().substring(14, 15));
				int i2 = Integer.parseInt(action.toString().substring(16, 17));
				int j2 = Integer.parseInt(action.toString().substring(17, 18));

				int temp = etat[i1][j1];
				etat[i1][j1] = etat[i2][j2];
				etat[i2][j2] = temp;
			}
		}
	}

	//========================================================
	//Evaluation de l'instatiation et de l'affichage de l'etat	
	//========================================================
	public static void main(String arg[]){
		Sudoku sudoku = new Sudoku(	"200060000007004086000001300"
				+ 	"000000040090000000480000710"
				+ 	"900078000000050002020600501");
		sudoku.print();
		sudoku.remplirAleatoirementHillClimbing();
		System.out.println("");
		sudoku.print();
	}
}