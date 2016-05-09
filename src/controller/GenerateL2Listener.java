package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;

import factory.Factory;
import model.ServiceNode;
import model.StronglyConnectedService;

public class GenerateL2Listener implements ActionListener{
	
	private ArrayList <StronglyConnectedService> scsList;
	private int tarjanIndex;
	private ArrayDeque <ServiceNode> tarjanStack;
	private ArrayList<ServiceNode> nodes;
	private Object[][] tableData;
	private Object[] columnNames;

	@Override
	public void actionPerformed(ActionEvent e) {
		
		nodes = Factory.getNodes();
		scsList = new ArrayList<StronglyConnectedService>();
		tarjanIndex = 0;
		// S := empty
		tarjanStack = new ArrayDeque<ServiceNode>();

		// for each v in V do
		// if (v.index is undefined) then
		// strongconnect(v)
		// end if
		// end for

		for (ServiceNode v : nodes) {
			if (v.getTarjanIndex() == -1) {
				strongConnect(v);
			}
		}

		columnNames = new Object[] { "SCS", "Sub Service", "Type of node", "Predecessor", "Successor" };
		tableData = new Object[20][columnNames.length];
		int index, rowIndex = 0;
		// Build table of results
		// loop through each scc
		for (StronglyConnectedService scs : scsList) {

			// System.out.println("index: " + index + " rowindex " + rowIndex +
			// " each service: " + eachService.GetName());
			// System.out.println(" sub service: " + subService.GetName());
			for (ServiceNode sn : scs.getServiceNodes()) {
				index = 0;
				tableData[rowIndex][index++] = "S" + scs.getIndex();
				tableData[rowIndex][index++] = sn.getName().toString();
				tableData[rowIndex][index++] = sn.getNodeType().toString();
				tableData[rowIndex][index++] = sn.getPredecessorsString();
				tableData[rowIndex][index++] = sn.getSuccessorsString();
				rowIndex++;
			}

		}
		Factory.displayResult(tableData, columnNames);
		
	}
	
	void strongConnect(ServiceNode v){
		
		v.setTarjanIndex(tarjanIndex);
		v.setTarjanLowLink(tarjanIndex);
		tarjanIndex++;
		tarjanStack.push(v);

		// // Consider successors of v
		// for each (v, w) in E do
		// if (w.index is undefined) then
		// // Successor w has not yet been visited; recurse on it
		// strongconnect(w)
		// v.lowlink := min(v.lowlink, w.lowlink)
		// else if (w.onStack) then
		// // Successor w is in stack S and hence in the current SCC
		// v.lowlink := min(v.lowlink, w.index)
		// end if
		// end for

		for (ServiceNode w : v.getSuccessors()) {
			if (w.getTarjanIndex() == -1) {
				// Successor w has not yet been visited; recurse on it
				strongConnect(w);
				v.setTarjanLowLink(Math.min(v.getTarjanLowLink(), w.getTarjanLowLink()));
			} else if (tarjanStack.contains(w)) {
				// Successor w is in stack S and hence in the current SCC
				v.setTarjanLowLink(Math.min(v.getTarjanLowLink(), w.getTarjanIndex()));
			}
		}

		// If v is a root node, pop the stack and generate an SCC
		// if (v.lowlink = v.index) then
		// start a new strongly connected component
		// repeat
		// w := S.pop()
		// w.onStack := false
		// add w to current strongly connected component
		// until (w = v)
		// output the current strongly connected component
		// end if

		// If v is a root node, pop the stack and generate an SCC
		if (v.getTarjanLowLink() == v.getTarjanIndex()) {
			StronglyConnectedService scc = new StronglyConnectedService(scsList.size() + 1);
			ServiceNode w;
			do {
				w = tarjanStack.pop();
				scc.add(w);
			} while (w != v);

			scsList.add(scc);
		}

		// end function
	}

}
