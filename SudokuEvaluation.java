package aima.gui.applications.search.sudoku;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import aima.core.agent.Action;

/**
 * 
 * @author Abel oria
 *
 */
public class SudokuEvaluation {

	public static void main(String[] args) {
		
		/** Liste d'agents qui utilisent la recherche prodondeur d'abord */
		LinkedList<SudokuApp> sudokusProfondeurDAbord = 
				new LinkedList<SudokuApp>();

		/** Liste d'agents qui utilisent la recherche meilleur d'abord */
		LinkedList<SudokuApp> sudokusFirstBest = new LinkedList<SudokuApp>();
		
		
		String fichier ="src/main/java/aima/gui/applications/"
				+ "search/sudoku/liste_de_sudokus";
		
		// lecture du fichier texte contenant les jeu de sudoku (il n'y a pas
		// de vérification de la validité du fichier) et sa resolution par
		// SudokuApp
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);

			String ligne;
			int sudokusResolus =1;
			
			while ((ligne=br.readLine())!=null){
				
				sudokusProfondeurDAbord.add(new SudokuApp(0,ligne));
				sudokusFirstBest.add(new SudokuApp(1,ligne));
				
				System.out.print(sudokusResolus +" ");			
				if(sudokusResolus % 10 == 0){
					System.out.println("");
				}
				sudokusResolus++;
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		

		System.out.println("Nombre de Jeu	Profondeur d'abord 		    Meilleur d'bord");
		int npd = 0;
		int nmd = 0;
		int egaux = 0;

		//Affichage du nombre de noeuds développés pour chaque algorithme
		//de recherche
		for(int i = 0; i < sudokusProfondeurDAbord.size() ; i++ ){
			SudokuApp pd =   sudokusProfondeurDAbord.get(i);
			SudokuApp md =   sudokusFirstBest.get(i);
			
			System.out.println(i+1 + "           " + pd.getNombreTests() + "                "+ md.getNombreTests());
			
			if(pd.getNombreTests() > md.getNombreTests()){
				npd++;
			}
			else if(pd.getNombreTests() < md.getNombreTests()){
				nmd++;
			}
			else{
				egaux++;
			}
		}
		
		//Affichage de comparations de performance des algorithmes
		System.out.println("profondeur d'abord: " + npd +"\n"
				+ "meilleur d'bord: "+ nmd+"\n"
				+ "egaux: " + egaux);
	}	
}