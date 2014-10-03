package aima.gui.applications.search.sudoku;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import aima.core.agent.Action;

public class Evaluation {

	public static void main(String[] args) {
		
		LinkedList<SudokuApp> sudokusProfondeurDAbord = new LinkedList<SudokuApp>();
		LinkedList<SudokuApp> sudokusFirstBest = new LinkedList<SudokuApp>();
		
		String chaine="";
		
		String fichier ="src/main/java/aima/gui/applications/search/sudoku/liste_de_sudokus";
		
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			int i =1;
			while ((ligne=br.readLine())!=null){
				sudokusProfondeurDAbord.add(new SudokuApp(0,ligne));
				sudokusFirstBest.add(new SudokuApp(1,ligne));
				chaine+=ligne+"\n";
				System.out.print(i +" ");
				if(i % 10 == 0){
					System.out.println("");
				}
				i++;
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
//		List<Action> actions = sudokuApp.getActions();
		System.out.println("Nombre de Jeu	Profondeur d'abord 		    Meilleur d'bord");
		int npd = 0;
		int nmd = 0;
		int egaux = 0;
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
		
		System.out.println("profondeur d'abord: " + npd +"\n"
				+ "meilleur d'bord: "+ nmd+"\n"
				+ "egaux: " + egaux);
	}
	
}
