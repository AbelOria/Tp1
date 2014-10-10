package aima.gui.applications.search.sudoku;

import aima.core.agent.Action;
import aima.core.search.framework.StepCostFunction;
/**
 * 
 * @author AbelOria, Zakaria Soliman
 *
 */
public class SudokuStepCostFunction implements StepCostFunction {

	@Override
	public double c(Object s, Action a, Object sDelta) {
		return -5;
	}

}
