package aima.gui.applications.search.sudoku;


import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Node;

public class SudokuGraphSearch extends  GraphSearch {


	private int limit;
	private int counter = 0;
	
	
//	public  SudokuGraphSearch(int limit){
//		counter = 10;
//		this.limit = limit;
//	}
	
	public  SudokuGraphSearch(){
	}
	
	@Override
	public Node popNodeFromFrontier(){
		
		if(counter >= limit ){
			return null;
		}
		counter++;
		return super.popNodeFromFrontier();
	}
	
	public int getNoeudsExplores(){
		return counter;
	}
	
	public void setRechecheMaximum(int limit){
		this.limit = limit;
	}
}
