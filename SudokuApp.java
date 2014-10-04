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
	private SudokuGoalTest test;
	protected SearchAgent agent;

	/** Liste d'actions menant à la solution du problème donné */
	private List<Action> actions;
	
	static {
		addSearchAlgorithm(" Depth First Search (Graph Search)",
				new DepthFirstSearch(new GraphSearch()));
		addSearchAlgorithm("A star Search",
				new AStarSearch(new GraphSearch(), new SudokuAstarHeuristic()));
		addSearchAlgorithm("Hill Climbing Search",
				new HillClimbingSearch( new SudokuHillClimbingHeuristic()));
		}
	
	
	public SudokuApp(int kindSearch, String etatInitial){
		sudoku = new Sudoku(etatInitial);
		if(kindSearch == 2){
			sudoku.remplirAleatoirementHillClimbing();
			problem = createProblemHillClimbing(sudoku);
		}
		else{
			problem = createProblem(this.sudoku);
		}
		agent = createAgent(kindSearch);
		actions  = agent.getActions();
	}	
	
	public List<Action> getActions(){		
		return actions;
	}
	
	
	public int getNombreTests(){
		return test.getNombreTests();
	}
		
	public boolean isAlive(){
		return agent.isAlive();
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
	
		test = new SudokuGoalTest();
		
		Problem problem = new Problem(sudoku,
					SudokuFunctionFactory.getActionsFunction(),
					SudokuFunctionFactory.getResultFunction(),
					test,
					new SudokuStepCostFunction());
		return problem;
	}
	

	private  Problem createProblemHillClimbing(Sudoku sudoku){
		
		test = new SudokuGoalTest();

		Problem problem = new Problem(sudoku,
				SudokuHCFunctionFactory.getActionsFunction(),
				SudokuHCFunctionFactory.getResultFunction(),
				test,
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
			+ 	"002470000400008700000001040");
		

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
	}
}
