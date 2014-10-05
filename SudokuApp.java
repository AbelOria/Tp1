package aima.gui.applications.search.sudoku;

import java.util.ArrayList;
import java.util.List;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.HillClimbingSearch.SearchOutcome;
import aima.core.search.uninformed.DepthFirstSearch;

/**
 * 
 * @author abel Oria
 * Agent de recherche pour les problèmes de sudoku
 * 
 */
public class SudokuApp{

	/** List of supported search algorithm names. */
	protected static List<String> SEARCH_NAMES = new ArrayList<String>();

	/** List of supported search algorithms. */
	protected static List<Search> SEARCH_ALGOS = new ArrayList<Search>();

	/** Adds a new item to the list of supported search algorithms. */
	public static void addSearchAlgorithm(String name, Search algo) {
		SEARCH_NAMES.add(name);
		SEARCH_ALGOS.add(algo);
	}
	
	private Sudoku sudoku;
	protected Problem problem;

	protected SearchAgent agent;

	/** Liste d'actions menant à la solution du problème donné */
	private List<Action> actions;

	private int typeRecherche;
	private static GraphSearch gs1 = new GraphSearch();
	private static GraphSearch gs2 = new GraphSearch();

	private static HillClimbingSearch hc = new HillClimbingSearch( new SudokuHillClimbingHeuristic());
	
	
	private static void setAlgorithmeRecherche(int maximumNoeudesARechercher) {

		gs1.setRechecheMaximum(maximumNoeudesARechercher);
		
		gs2.setRechecheMaximum(maximumNoeudesARechercher);
		
		hc.setRechercheMaximum(maximumNoeudesARechercher);
		
		addSearchAlgorithm(" Depth First Search (Graph Search)",
				new DepthFirstSearch(gs1));
		
		addSearchAlgorithm("A star Search",
				new AStarSearch(gs2, new SudokuAstarHeuristic()));
		
//		addSearchAlgorithm("Hill Climbing Search",
//				new HillClimbingSearch( new SudokuHillClimbingHeuristic()));
		addSearchAlgorithm("Hill Climbing Search", 	hc);
	}
	
	
	public SudokuApp(int kindSearch, String etatInitial, 
			int maximumNoeudesARechercher){
		
		typeRecherche = kindSearch;
		setAlgorithmeRecherche(maximumNoeudesARechercher); 
		
		sudoku = new Sudoku(etatInitial);
		if(kindSearch == 2){
			sudoku.remplirAleatoirementHillClimbing();
			problem = createProblemHillClimbing(sudoku);
		}
		else{
			problem = createProblem(sudoku);
		}
		agent = createAgent(kindSearch);
		actions  = agent.getActions();		
	}	
	
	public List<Action> getActions(){		
		return actions;
	}
	
	
	public int getNombreTests(){

		if(typeRecherche == 0){
			return gs1.getNoeudsExplores();
		}
		else if (typeRecherche == 1){
			return gs2.getNoeudsExplores();
		}
		else if(typeRecherche == 2){
			return hc.getNoeudsExplores();
		}
		return 0;
	}
	
	public boolean isSucces(){
		if(typeRecherche == 2){
			return !hc.getOutcome().toString().equals("FAILURE");
		}
		return actions.size() > 0 ;
	}
		

	/**
	 * 
	 * @param searchType
	 * @return Un SearcheAgent pour un problème de sudoku en uttilisant le type 
	 * de recherche indique par "searhType" 
	 * 
	 */
	private SearchAgent createAgent(int searchType){
		SearchAgent agent = null;
		
		 try {
			agent = new SearchAgent(problem, SEARCH_ALGOS.get(searchType));
		} catch (Exception e) {
			e.printStackTrace();
		}		
		 
		 return agent;
	}

	/**
	 * 
	 * @param Sudoku état initial du sudoku à resoudre
	 * @return Un problème contenant le état initial du sudoku
	 * 
	 */
	private Problem createProblem(Sudoku sudoku){

		Problem problem = new Problem(sudoku,
					SudokuFunctionFactory.getActionsFunction(),
					SudokuFunctionFactory.getResultFunction(),
					new SudokuGoalTest(),
					new SudokuStepCostFunction());
		return problem;
	}
	

	private  SudokuHCFunctionFactory sudokuHCFunctionFactory;
	
	

	private  Problem createProblemHillClimbing(Sudoku sudoku){

		Problem problem = new Problem(sudoku,
				sudokuHCFunctionFactory.getActionsFunction(),
				sudokuHCFunctionFactory.getResultFunction(),
				new SudokuGoalTest(),
				new SudokuStepCostFunction());
		
		return problem;		
	}
	
	//========================================================
	//Evaluation SudokuApp	
	//========================================================
	public static void main(String[] args) {
	
	
	SudokuApp sudokuApp = new SudokuApp(2,	
				"800006304000000000040090001"
			+ 	"309060000000700006021800050"
			+ 	"002470000400008700000001040", 10000);
		

//		SudokuApp sudokuApp = new SudokuApp(2,	
//					"002519436596342871314867290"
//				+	"001738649937654128648921750"
//				+	"000483967873296510469175380");

	
//		SudokuApp sudokuApp = new SudokuApp(0,	
//					"782519436596342871314867295"
//				+	"251738649937654128648921753"
//				+	"125483967873296514469175382");

	
//		SudokuApp sudokuApp = new SudokuApp(0,	
//					"000009430000000000314867000"
//				+	"000008640000000000048901703"
//				+	"025483960000090514469175302");

		
		List<Action> actions = sudokuApp.getActions();
		
		System.out.println("nombre de tests: " + sudokuApp.getNombreTests());				
		System.out.println("nombre d'actions trouves: " + actions.size());
		
		//Affichage des actions qui menent à l'état but 
		for(Action action: actions){
			for(int i = 0 ; i < 9 ; i++ ){
				for(int j = 0 ; j < 9 ; j++ ){
					for(int k = 1 ; k <= 9 ; k++){
						Action actionCourrent= new DynamicAction(i+""+j+""+k);
						if(actionCourrent.equals(action)){
							System.out.print(k);						}
					}
				}
			}
		
		}
		
		
		//HC
		for(Action action :actions){
			System.out.println(action.toString());
		}
		
		System.out.println("isSucces: " +sudokuApp.isSucces());
	}
}
