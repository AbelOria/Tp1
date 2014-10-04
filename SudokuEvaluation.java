package aima.gui.applications.search.sudoku;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * 
 * @author Abel oria
 *
 */
public class SudokuEvaluation {
	
	private LinkedList<LinkedList<SudokuApp>> agentsParTypeDeRecherche;
	
	public SudokuEvaluation(LinkedList<String> exemplairesSudoku,
			LinkedList<Integer> typesRecherche){
		
			agentsParTypeDeRecherche = new LinkedList<LinkedList<SudokuApp>>();
			for(int i = 0 ; i< typesRecherche.size(); i++){
				agentsParTypeDeRecherche.add(new LinkedList<SudokuApp>());
			}
			
			resoudre(exemplairesSudoku, typesRecherche);
	}

	
	
	private void resoudre(LinkedList<String> exemplairesSudoku,
			LinkedList<Integer> typesRecherche){		

		for(String sudokuString : exemplairesSudoku){

			
			for(Integer i : typesRecherche){
				int j =i;
				SudokuApp sa = new SudokuApp(j, sudokuString);
				agentsParTypeDeRecherche.get(j).add(sa);
				System.out.print("j: "+ j + "   " +sa.getNombreTests()+ "       ");
			}
			System.out.println( "     ");

			
			
		}
	}

	
	
	private void afficherDernierResolu(){
		for(int i = 0 ; i <agentsParTypeDeRecherche.size() ; i++){
			
			int nTest = agentsParTypeDeRecherche.get(i).getLast().getNombreTests();
			System.out.print( nTest  + "    ");
			
		}		
		
		System.out.println("");
	}
	
	
	
	public static void main(String[] args) {
				
		// définition de types de recherche à realiser
		LinkedList<Integer> typesDeRecherche = new LinkedList<Integer>();
		typesDeRecherche.add(0);
		typesDeRecherche.add(1);
//		typesDeRecherche.add(2);

		// Fichier contenent la liste de sudokus
		String fichier ="src/main/java/aima/gui/applications/"
				+ "search/sudoku/liste_de_sudokus";

		LinkedList<String> sudokusAResoudre = new LinkedList<String>();



		// lecture du fichier texte contenant les jeu de sudoku (il n'y a pas
		// de vérification de la validité du fichier) et sa resolution par
		// SudokuApp
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);

			String ligne;
			int nSudokusAResoudre = 100;
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
		
		new SudokuEvaluation(sudokusAResoudre, typesDeRecherche);
	}	
}