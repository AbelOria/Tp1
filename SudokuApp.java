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
 * @author abel Oria,Zakaria Soliman
 * Agent de recherche pour les problèmes de sudoku
 * 
 */
public class SudokuApp{

	/** List of supported search algorithm names. */
	protected static List<String> SEARCH_NAMES = new ArrayList<String>();

	/** List of supported search algorithms. */
	protected static List<Search> SEARCH_ALGOS = new ArrayList<Search>();

	/** Liste d'actions menant à la solution du problème donné */
	private List<Action> actions;

	private Sudoku sudoku;
	protected Problem problem;
	protected SearchAgent agent;
	private int typeRecherche;

		private static GraphSearch gs1 = new GraphSearch();
		private static GraphSearch gs2 = new GraphSearch();
//	private static SudokuGraphSearch gs1;
//	private static SudokuGraphSearch gs2;	
	private static HillClimbingSearch hc = new HillClimbingSearch( new SudokuHillClimbingHeuristic());

	private  SudokuHCFunctionFactory sudokuHCFunctionFactory;

	public SudokuApp(int kindSearch, String etatInitial, 
			int maximumNoeudesARechercher){

//		hc = new HillClimbingSearch( new SudokuHillClimbingHeuristic());
		hc.setRechercheMaximum(maximumNoeudesARechercher);
//		gs1 = new SudokuGraphSearch(maximumNoeudesARechercher);
//		gs2 = new SudokuGraphSearch(maximumNoeudesARechercher);

		typeRecherche = kindSearch;
		setAlgorithmeRecherche(maximumNoeudesARechercher); 

		sudoku = new Sudoku(etatInitial);
		if(kindSearch == 2){
			sudoku.remplirAleatoirementHillClimbing3x3();
			problem = createProblemHillClimbing(sudoku);
		}
		else{
			problem = createProblem(sudoku);
		}
		agent = createAgent(kindSearch);
		actions  = agent.getActions();		
	}	


	private static void setAlgorithmeRecherche(int maximumNoeudesARechercher) {

		gs1.setRechecheMaximum(maximumNoeudesARechercher);
		gs2.setRechecheMaximum(maximumNoeudesARechercher);
		hc.setRechercheMaximum(maximumNoeudesARechercher);

		addSearchAlgorithm(" Depth First Search (Graph Search)",
				new DepthFirstSearch(gs1));

		addSearchAlgorithm("A star Search",
				new AStarSearch(gs2, new SudokuAstarHeuristic()));

		addSearchAlgorithm("Hill Climbing Search", 	hc);
	}

	/** Adds a new item to the list of supported search algorithms. */
	public static void addSearchAlgorithm(String name, Search algo) {
		SEARCH_NAMES.add(name);
		SEARCH_ALGOS.add(algo);
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
//			return hc.getNoeudsExplores();
			return hc.getNodesExpanded();
		}
		return 0;
	}

	public boolean isSucces(){
		if(typeRecherche == 2){
			return !hc.getOutcome().toString().equals("FAILURE");
		}
		return actions.size() > 0 ;
	}

	public void remplirSolution(){
		sudoku.remplirSolution(actions, typeRecherche );
	}

	public void printSudoku(){
		sudoku.print();
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



	private void printActions() {
		// Print MDA et PDA

		System.out.println("Liste des actions: ");

		if(typeRecherche == 0 || typeRecherche== 1){
			int nAction = 1;
			for(Action action: actions){
				for(int i = 0 ; i < 9 ; i++ ){
					for(int j = 0 ; j < 9 ; j++ ){
						for(int k = 1 ; k <= 9 ; k++){
							Action actionCourrent= new DynamicAction(i+""+j+""+k);
							if(actionCourrent.equals(action)){
								System.out.printf("%3d) %s \n",
										nAction,
										"case: (" + i + "," + j + ")   " 
												+"remplir: " + k );
								nAction++;
							}
						}
					}
				}

			}
		}
		//Print  Hill Climbing
		else if(typeRecherche == 2){
			int nAction = 1;
			for(Action action :actions){
				if(!action.toString().endsWith("Action[name==NoOp]")){
					//System.out.println(action.toString());				
					int i1 = Integer.parseInt(action.toString().substring(13, 14));
					int j1 = Integer.parseInt(action.toString().substring(14, 15));
					int i2 = Integer.parseInt(action.toString().substring(16, 17));
					int j2 = Integer.parseInt(action.toString().substring(17, 18));

					System.out.printf("%3d) %s\n",
							nAction,
							"Interchanger: ("+i1+","+j1+") avec ("+i2+","+j2+")");
					nAction++;
				}
			}
		}
	}


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
		//args[0]:  Type de recherche ( 0 | 1 | 2 )
		//args[1]:  Exemplare de sudoku (81 caractères de 0 à 9)
		//args[2]:  Nombre maximum de noeuds à explorer 

		SudokuApp sudokuApp = new SudokuApp(Integer.parseInt(args[0]), 
				args[1], 
				Integer.parseInt(args[2]));

		List<Action> actions = sudokuApp.getActions();

		System.out.printf("%s %5d \n", 
				"Nombre de tests:          ", sudokuApp.getNombreTests());				
		System.out.printf("%s %5d \n", 
				"Nombre d'actions trouves: ", actions.size());


		sudokuApp.printActions();
		System.out.println("\nProblème original");		
		sudokuApp.printSudoku();
		sudokuApp.remplirSolution();
		System.out.println("\nProblème résolu");
		sudokuApp.printSudoku();
		System.out.println("\nIs success?: " +sudokuApp.isSucces());
	}
}