package aima.gui.applications.search.sudoku;

import java.util.ArrayList;
import java.util.List;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.HeuristicFunction;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
//import aima.gui.applications.search.games.EightPuzzleApp.EightPuzzleFrame;
//import aima.gui.applications.search.games.EightPuzzleApp.EightPuzzleView;
import aima.gui.framework.AgentAppFrame;
import aima.gui.framework.SimpleAgentApp;

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
	
	
	protected SearchAgent agent = null;
	protected Problem problem = null;
	private ActionsFunction actionsFunctions= null;
	private ResultFunction resulFunctions = null;
	private Sudoku sudoku =null;
	private List<Action> actions;
	private SudokuGoalTest test;
	
	
	public SudokuApp(int kindSearch, String etatInitial){
		this.sudoku = new Sudoku(etatInitial);
		this.problem = createProblem(this.sudoku);
		this.agent = createAgent(kindSearch);
		actions  = agent.getActions();
	}
	
	
	
	private Problem createProblem(Sudoku sudoku){
		test = new SudokuGoalTest();
		
		Problem problem = new Problem(sudoku,
					SudokuFunctionFactory.getActionsFunction(),
					SudokuFunctionFactory.getResultFunction(),
					test,
					new SudokuStepCostFunction());
		return problem;
	}
	
	private SearchAgent createAgent(int kindSearch){
		SearchAgent agent = null;
		
		 try {
			agent = new SearchAgent(problem, SEARCH_ALGOS.get(kindSearch));
//			System.out.println("Creations de l'agent");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		 
		 return agent;
	}
	
	public List<Action> getActions(){
				
		return actions;
	}
	
	
	public int getNombreTests(){
		return test.getNombreTests();
	}
	
	static {
		addSearchAlgorithm(" Depth First Search (Graph Search)",
				new DepthFirstSearch(new GraphSearch()));
		addSearchAlgorithm("A star Search",
				new AStarSearch(new GraphSearch(), new SudokuAstarHeuristic()));

		}
	

	public static void main(String[] args) {
	
	
	SudokuApp sudokuApp = new SudokuApp(0,	"800006304000000000040090001309060000000700006021800050002470000400008700000001040");
		
		

//		SudokuApp sudokuApp = new SudokuApp(0,	"002519436596342871314867290"
//				+	"001738649937654128648921750"
//				+	"000483967873296510469175380");
		
//		SudokuApp sudokuApp = new SudokuApp(0,	"782519436596342871314867295"
//				+	"251738649937654128648921753"
//				+	"125483967873296514469175382");

//		SudokuApp sudokuApp = new SudokuApp(0,	"000009430000000000314867000"
//				+	"000008640000000000048901703"
//				+	"025483960000090514469175302");

		List<Action> actions = sudokuApp.getActions();
		
		System.out.println("nombre de tests: " + sudokuApp.getNombreTests());				
		System.out.println("nombre d'actions trouves: " + actions.size());
		
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
	}
}
