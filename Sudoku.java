package aima.gui.applications.search.sudoku;

import java.util.HashMap;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;

/**
 * 
 * @author Abel oria
 * Representation d'une grille de sudoku
 *
 */
public class Sudoku {
	
	/** Tous les actiones possibles pour une grille de sudoku où tous
	 * les cases son 0s	 */
	public static HashMap<String, Action> actions = null;
	
	private int[][] etat = null;
	
	public Sudoku(String etat){
		this.etat = createEtat(etat);
		creerActions();
	}
	
	public Sudoku(int[][] etat){
		this.etat = etat;
		creerActions();
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
	 * Génère tous les actions possibles pour une grille de Sudoku où tous les
	 * cases sont 0 (9*9*9 actions générés) 
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
	 * 
	 * @param etat Un chîne de caratères contenant 81 chiffres élus entre 0 et 9 
	 * @return une matrice 9x9 contennant les 81 chiffres reçus dans etat  
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

	//========================================================
	//Evaluation de l'instatiation et de l'affichage de l'etat	
	//========================================================
	public static void main(String arg[]){
		new Sudoku(	"200060000007004086000001300"
				+ 	"000000040090000000480000710"
				+ 	"900078000000050002020600501").print();
	}
}