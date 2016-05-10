package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import factory.Factory;
import model.Service;
import model.ServiceNode;

public class StronglyConnectedServicesListener implements ActionListener {
	
	private Object[][] tableData;
	private Object[] columnNames;
	private ArrayList<ServiceNode> nodes;
	private ArrayList<Service> elementaryServices;
	private List<Service> lstData;

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
        for (Service elem : elementaryServices) {
        	ServiceNode node = new ServiceNode(elem);
        	nodes.add(node);
        }
        lstData = Factory.getLstData();

for (Service eachService : lstData) {
	// check if service has elementary services
	if (eachService.getelementaryServices().size() == 0) {
		ServiceNode node = new ServiceNode(eachService);
		nodes.add(node);
	}
}

// Connect the graphs of service nodes
for (ServiceNode n1 : nodes) {
	List<String> outputs = n1.getService().getOutputs();
	for (String output : outputs) {
		// Look for matching input to this output
		for (ServiceNode n2 : nodes) {
			for (String input : n2.getService().getInputs()) {
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

		
	}

}
