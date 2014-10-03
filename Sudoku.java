package aima.gui.applications.search.sudoku;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;

public class Sudoku {

	/*
	public static Action UN = new DynamicAction("1");
	public static Action DEUX = new DynamicAction("2");
	public static Action TROIS = new DynamicAction("3");
	public static Action QUATRE = new DynamicAction("4");
	public static Action CINQ = new DynamicAction("5");
	public static Action SIX = new DynamicAction("6");
	public static Action SEPT = new DynamicAction("7");
	public static Action HUIT = new DynamicAction("8");
	public static Action NEUF = new DynamicAction("9");
	*/
	
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

	private int[][] createEtat(String etat) {
		int etat_courrant[][] = new int[9][9];
		int n = 0;
		for(int i =0 ; i < 9; i++){
			for(int j = 0 ; j< 9; j++){
				etat_courrant[i][j] = Integer.parseInt( (String) etat.subSequence(n,n+1));
				n++;
			}
		}
		return etat_courrant;
	}
	
	public int[][] getGrille() {
		return etat;
	}
	
	public void print(){
		for(int i =0 ; i < 9; i++){
			for(int j = 0 ; j< 9; j++){
				
				System.out.print ( etat[i][j]+" " );
			}
			System.out.println();
		}
	}
	
	public void setCasse(int i, int j, int valeur){
		etat[i][j] = valeur;
		
	}

	
	
	//Evaluation	
	public static void main(String arg[]){
		new Sudoku(	"200060000007004086000001300"
				+ 	"000000040090000000480000710"
				+ 	"900078000000050002020600501").print();
	}
}