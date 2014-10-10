package aima.gui.applications.search.sudoku;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * 
 * @author Abel oria, Zakaria Soliman
 *
 */
public class SudokuEvaluation {
	
	private LinkedList<LinkedList<SudokuApp>> agentsParTypeDeRecherche;
	
	private int rechercheMaximum;
	
	/** Le nombre de recherche realise par chaque jeu de sudoku avec 
	 *  profondeur d'abord */
	private LinkedList<Integer> nouedRecherchesPDA = new LinkedList<Integer>();

	/** Le nombre de recherche realise par chaque jeu de sudoku avec 
	 *  meilleur d'abord */
	private LinkedList<Integer> nouedRecherchesHC = new LinkedList<Integer>();

	/** Le nombre de recherche realise par chaque jeu de sudoku avec 
	 *  hill climbing */
	private LinkedList<Integer> nouedRecherchesMDA = new LinkedList<Integer>();

	
	
	public SudokuEvaluation(LinkedList<String> exemplairesSudoku,
			LinkedList<Integer> typesRecherche,	int rechercheMaximum){
		
			this.rechercheMaximum = rechercheMaximum;
			
			agentsParTypeDeRecherche = new LinkedList<LinkedList<SudokuApp>>();
			for(int i = 0 ; i< typesRecherche.size(); i++){
				agentsParTypeDeRecherche.add(new LinkedList<SudokuApp>());
			}
			
			resoudre(exemplairesSudoku, typesRecherche);
	}

	
	/**
	 * 
	 * @param exemplairesSudoku Exemplaire du sudoku su forme de string
	 * @param typesRecherche  Les types de recherche à pour resoudre 
	 * 			l'exemplaire du sudoku 
	 */
	private void resoudre(LinkedList<String> exemplairesSudoku,
			LinkedList<Integer> typesRecherche){		

		System.out.println("RESOLUTION DE SUDOKUS \n"
				+ "============================================================"
				+ "\nLimite de recherche:  " + rechercheMaximum);
		
		afficherEntete();
		
		for(String sudokuString : exemplairesSudoku){
			
			for(Integer i : typesRecherche){
				int j =i;
				SudokuApp sudokuApp = new SudokuApp(j, sudokuString, rechercheMaximum);
				agentsParTypeDeRecherche.get(j).add(sudokuApp);
			}		
			afficherDernierResolu();
		}
		afficherTauxSucces();
		afficherNombreDeMieuxSolution();
	}
	
	
	
	private void afficherEntete(){
		System.out.printf("%5s %32s %30s %30s \n", "nJeu", "Profondeur d'abord", 
				"Meilleur d'abord", "HillClimbing");
	}


	
	private void afficherDernierResolu(){
	
		System.out.printf("%5d", agentsParTypeDeRecherche.get(0).size()); 
	
		for(int i = 0 ; i <agentsParTypeDeRecherche.size() ; i++){
			
			SudokuApp sudokuApp = agentsParTypeDeRecherche.get(i).getLast();
			
			System.out.printf("%25s %5s", sudokuApp.getNombreTests(), 
					sudokuApp.isSucces() );
			
				//Souvegarde de nombre de noeuds explore pour chaque solution
				if(i==0){
					nouedRecherchesPDA.add(sudokuApp.getNombreTests());
				}
				else if(i==1){
					nouedRecherchesMDA.add(sudokuApp.getNombreTests());
				}
				else if(i==2){
					nouedRecherchesHC.add(sudokuApp.getNombreTests());
				}
		}		
		System.out.println("");
	}


	private void afficherTauxSucces(){
		
			double tauxProfondeurDAbord = tauxSucces(0);
			double tauxMeilleudDAbord = tauxSucces(1);
			double tauxHillClimbing = tauxSucces(2);
		
		System.out.printf("%5s %15.2f %30.2f %30.2f\n \n","Taux de succes", 
				tauxProfondeurDAbord, tauxMeilleudDAbord, tauxHillClimbing);
		
	}
	
	
//	private double tauxSucces(int typeRecherche){
//		LinkedList<SudokuApp> agents = agentsParTypeDeRecherche.get(typeRecherche);
//		double nombreDeJeux = agents.size();
//		
//		int succes = 0;
//		for(SudokuApp sa : agents ){
//			System.out.println("naction: "+sa.getActions().size());
//			System.out.println( sa.getNombreTests());
//			if(sa.getNombreTests() <  rechercheMaximum-1){
//				succes++;
//			}
//		}
//		
//		return ((double)succes)/nombreDeJeux;
//	}

	
	private double tauxSucces(int typeRecherche){
		LinkedList<Integer> nombreRecherches = new LinkedList<Integer>();
		if(typeRecherche ==0){
			nombreRecherches = nouedRecherchesPDA;
		}
		if(typeRecherche ==1){
			nombreRecherches = nouedRecherchesMDA;
		}
		if(typeRecherche ==2){
			nombreRecherches = nouedRecherchesHC;
		}
		
		LinkedList<SudokuApp> agents = agentsParTypeDeRecherche.get(typeRecherche);
		double nombreDeJeux = agents.size();
		
		int succes = 0;
		int index = 0;
		for(Integer i : nombreRecherches ){
			if(i  <  rechercheMaximum && ( typeRecherche ==0 || typeRecherche ==1)){
				succes++;
			}
			else if(typeRecherche == 2 && agents.get(index).isSucces() ){
				succes++;
			}
			index++;
		}		
		return ((double)succes)/nombreDeJeux;
	}

	
	/**
	 * Affiche une comparaison entre le nombre de fois qu'une recherche à été
	 * plus performant que les autres
	 */
	private void afficherNombreDeMieuxSolution(){
		int pda = 0;
		int mda = 0;
		int hc = 0;
		
		LinkedList<SudokuApp> agentsPDA = agentsParTypeDeRecherche.get(0);
		LinkedList<SudokuApp> agentsMDA = agentsParTypeDeRecherche.get(1);
		LinkedList<SudokuApp> agentsHC = agentsParTypeDeRecherche.get(2);
		
		for(int i = 0; i < nouedRecherchesPDA.size() ; i++ ){
			int noeudsPDA = nouedRecherchesPDA.get(i);
			int noeudsMDA = nouedRecherchesMDA.get(i);
			int noeudsHC = nouedRecherchesHC.get(i);
			
			if(noeudsPDA < noeudsMDA && agentsPDA.get(i).isSucces()){				
				if(!agentsHC.get(i).isSucces() || noeudsPDA < noeudsHC){
					pda++;					
				}
			}
			else if(noeudsMDA < noeudsPDA && agentsMDA.get(i).isSucces()){
				if(!agentsHC.get(i).isSucces() || noeudsMDA < noeudsHC){
					mda++;					
				}				
			}
			if(noeudsHC < noeudsMDA && noeudsHC < noeudsPDA 
					&& agentsHC.get(i).isSucces()){
				hc++;
			}

		}
		
		System.out.println("Comparaison de meilleur solution:\n"
				+ "   Profondeur d'abord: " + pda +" fois le meilleur\n"
				+ "   Meilleur d'bord:    " + mda +" fois le meilleur\n"
				+ "   Hill Climbing:      " + hc + " fois le meilleur\n\n");		
	}


	//========================================================
	// Execution du programe d'evaluation de resolution de 100
	// jeu sudoku qui sont lues à partir du fichier liste_de_sudokus
	//========================================================
	public static void main(String[] args) {
				
		// définition de types de recherche à realiser
		LinkedList<Integer> typesDeRecherche = new LinkedList<Integer>();
		typesDeRecherche.add(0);
		typesDeRecherche.add(1);
		typesDeRecherche.add(2);

		// Fichier contenent la liste de sudokus
//		String fichier ="src/main/java/aima/gui/applications/"
//				+ "search/sudoku/liste_de_sudokus";

		LinkedList<String> sudokusAResoudre = new LinkedList<String>();
		
		String fichier = args[0]; 

		// lecture du fichier texte contenant les jeu de sudoku (il n'y a pas
		// de vérification de la validité du fichier) et sa resolution par
		// SudokuApp
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);

			String ligne;
			int nSudokusAResoudre = Integer.parseInt(args[1]);
			int nSudokusLues = 0;
			
			while ((ligne=br.readLine())!=null && nSudokusLues < nSudokusAResoudre){				
				sudokusAResoudre.add(ligne);
				nSudokusLues++;
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		
		new SudokuEvaluation(sudokusAResoudre, typesDeRecherche, 500);
		new SudokuEvaluation(sudokusAResoudre, typesDeRecherche, 1000);
		new SudokuEvaluation(sudokusAResoudre, typesDeRecherche, 5000);
		new SudokuEvaluation(sudokusAResoudre, typesDeRecherche, 10000);
	}		
}