package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import factory.Factory;
import model.IOVariable;
import model.Service;
import model.ServiceNode;
import model.SimpleService;
import model.StronglyConnectedService;

public class FindSCSandGenerateL2Listener implements ActionListener {
	
	private Object[][] tableData;
	private Object[] columnNames;
	private ArrayList<ServiceNode> nodes;
	private ArrayList<SimpleService> elementaryServices;
	private List<SimpleService> lstData;
	private ArrayList <StronglyConnectedService> scsList;
	private int tarjanIndex;
	private ArrayDeque <ServiceNode> tarjanStack;
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(Factory.getSCSGenerated()){ Factory.displayResult(tableData, columnNames); return;}//if it was done already, just reshow the old stuff
		columnNames = new Object[] { "Service", "Type of node", "Predecessors", "Successors", "Has System inputs",
		"Has System outputs" };
        tableData = new Object[20][columnNames.length];
        int index, rowIndex = 0;
        nodes = new ArrayList<ServiceNode>();
        elementaryServices = Factory.getElementaryServices();
        // Creates the initial list of service nodes
        for (SimpleService elem : elementaryServices) {
        	ServiceNode node = new ServiceNode(elem);
        	nodes.add(node);
        }
        lstData = Factory.getLstData();

        for (SimpleService eachService : lstData) {
        	// check if service has elementary services
        	if (eachService.getElementaryServices().size() == 0) {
        		ServiceNode node = new ServiceNode(eachService);
        		nodes.add(node);
        	}
        }

        // Connect the graphs of service nodes
        for (ServiceNode n1 : nodes) {
        	ArrayList<IOVariable> outputs = n1.getService().getOutputService();
        	for (IOVariable output : outputs) {
        		// Look for matching input to this output
        		for (ServiceNode n2 : nodes) {
        			for (IOVariable input : n2.getService().getInputService()) {
        				if (input.equals(output)) {
        					// Attach nodes in graph
						if (!n1.getSuccessors().contains(n2)) {
							n1.getSuccessors().add(n2);
						}
						if (!n2.getPredecessors().contains(n1)) {
							n2.getPredecessors().add(n1);
						}
					}
				}
			}
		}
	}

	// loop through each service
	for (ServiceNode node : nodes) {
		index = 0;
		// System.out.println("index: " + index + " rowindex " + rowIndex +
		// " each service: " + eachService.GetName());
		// System.out.println(" sub service: " + subService.GetName());
		tableData[rowIndex][index++] = node.getName();
		tableData[rowIndex][index++] = node.getNodeType().toString();
		tableData[rowIndex][index++] = node.getPredecessorsString();
		tableData[rowIndex][index++] = node.getSuccessorsString();
		tableData[rowIndex][index++] = node.hasSystemInputs() ? "Yes" : "No";
		tableData[rowIndex][index++] = node.hasSystemOutputs() ? "Yes" : "No";
	
		rowIndex++;
	}
	
	Factory.setNodes(nodes);
	Factory.displayResult(tableData, columnNames);
	Factory.setSCSGenerated(true);
	
	//-------------------------------------------------------------------------------
	
	if(Factory.getL2Generated()){ Factory.displayResult(tableData, columnNames); return;}//if it was done already, just reshow the old stuff
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
	index = 0; rowIndex = 0;
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
	Factory.setL2Generated(true);
		
		
		
		
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
