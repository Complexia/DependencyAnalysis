package controller;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import factory.Factory;
import model.IOVariable;
import model.ServiceNode;
import model.StronglyConnectedService;

public class CheckPropertyListener implements ActionListener
{

	JPanel programPanel = Factory.getProgramPanel();
	JPanel dataPanel = Factory.getDataPanel();
	ArrayList<StronglyConnectedService> scsList = Factory.getScsList();

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		checkProperty();

	}
	
	private void checkProperty()
	{	
		ArrayList<IOVariable> allLevel2Data= new ArrayList<IOVariable>();
		ArrayList<IOVariable> systemInputs = new ArrayList<IOVariable>();
		ArrayList<IOVariable> systemOutputs = new ArrayList<IOVariable>();
		final ArrayList<IOVariable> allInputs = new ArrayList<IOVariable>();
		allInputs.clear();
		ArrayList<IOVariable> allOutputs = new ArrayList<IOVariable>();
		// Populate the input and output lists
		
		// Go through every strongly connected service
		for (StronglyConnectedService scs : scsList) 
		{
			// Go through every elementary service node within that service
			for (ServiceNode sn : scs.getServiceNodes())
			{
				// Grab all of the system inputs from each elementary service
				for (IOVariable input : sn.getSystemInputs())
				{
					// Add the system input to our master list, unless it is already there
					if (!systemInputs.contains(input))
					{
						systemInputs.add(input);
					}
				}
				
				// Grab all of the system outputs from each elementary service
				for (IOVariable output : sn.getSystemOutputs())
				{
					// Add the system outputs to our master list, unless it is already there
					if (!systemOutputs.contains(output))
					{
						systemOutputs.add(output);
					}
				}
			}
				
			// Grab all of the data io from each elementary service
			for (IOVariable data : scs.getDataFlows())
			{
				// Add the system outputs to our master list, unless it is already there
				if (!allLevel2Data.contains(data))
				{
					allLevel2Data.add(data);
				}
			}
		}
		
		// Filter the data. System inputs and outputs do not go in both lists
		for (IOVariable data : allLevel2Data)
		{
			if (!systemInputs.contains(data))
			{
				allOutputs.add(data);
			}
			if (!systemOutputs.contains(data))
			{
				allInputs.add(data);
			}
		}

		JPanel bigPanel = new  JPanel();
		bigPanel.setLayout(new GridLayout(0,2));
		
		
		JPanel leftPanel= new JPanel();
		JPanel rightPanel= new JPanel();
		
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
			
		// Clear existing GUI elements
		programPanel.remove(dataPanel);
		
		// Start a fresh panel
		dataPanel = new JPanel();
		
		// Force each GUI item onto a new line
		dataPanel.setLayout(new BorderLayout());
		
		// Make the dataPanel scrollable
		JScrollPane jsp = new JScrollPane(dataPanel);
		
		// Lists containing the checkboxes for inputs and outputs
		final ArrayList<JCheckBox> inputCheckBoxes = new ArrayList<JCheckBox>();
		inputCheckBoxes.clear();
		final ArrayList<JCheckBox> outputCheckBoxes = new ArrayList<JCheckBox>();
		outputCheckBoxes.clear();
        
		JLabel inputLabel = new JLabel("Select Inputs");
		leftPanel.add(inputLabel);
		
		//dataPanel.add(inputLabel);
		
		// Create a checkbox for each input
        for(IOVariable input: allInputs)
        {
        	JCheckBox cb = new JCheckBox(input.getName());
        	inputCheckBoxes.add(cb);
        	//dataPanel.add(cb);
        	
        	leftPanel.add(cb);
        }
        
		JLabel outputLabel = new JLabel("Select Outputs");
		//dataPanel.add(outputLabel);
		
		rightPanel.add(outputLabel);
        
		// Create a checkbox for each output
        for(IOVariable output: allOutputs)
        {
        	JCheckBox cb = new JCheckBox(output.getName());
        	outputCheckBoxes.add(cb);
        	//dataPanel.add(cb);
        	rightPanel.add(cb);
        }
        
        bigPanel.add(leftPanel);
        bigPanel.add(rightPanel);
        dataPanel.add(bigPanel, BorderLayout.NORTH);
     
        JPanel bottomPanel = new JPanel();
        
        // Evaluate button initiates calculations
        Button evaluateButton = new Button("Evaluate");
   //     dataPanel.add(evaluateButton);
        bottomPanel.add(evaluateButton);
        
        
		// Label to show the results
		final JLabel resultLabel = new JLabel();
	//	dataPanel.add(resultLabel);
		resultLabel.setText("");
		 bottomPanel.add(resultLabel);
		
		dataPanel.add(bottomPanel, BorderLayout.SOUTH);
		// An action listener to respond to the user pressing the evaluate button
		ActionListener evaluateButtonPressedHandler = new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	
	        	// Lists of the selected inputs and outputs stored here
	        	ArrayList<String> selectedInputs = new ArrayList<String>();
	        	ArrayList<String> selectedOutputs = new ArrayList<String>();
	        	
	        	// Reads the currently selected inputs and outputs from the check boxes
	        	for (JCheckBox cb : inputCheckBoxes)
	        	{
	        		if (cb.isSelected())
	        		{
	        			selectedInputs.add(cb.getText());
	        		}
	        	}
	        	for (JCheckBox cb : outputCheckBoxes)
	        	{
	        		if (cb.isSelected())
	        		{
	        			selectedOutputs.add(cb.getText());
	        		}
	        	}
	        	
	        	// Find all 'sources' (SCS) that feed the outputs
	            ArrayList<StronglyConnectedService> sources = findSources(selectedOutputs);
	            
	            ArrayList<String> legacyWorkaroundArray = new ArrayList<String>();
	            for(IOVariable legacyWorkaroundArrayListItem : allInputs)
	            {
	            	legacyWorkaroundArray.add(legacyWorkaroundArrayListItem.getName());
	            }
	            // Find all inputs that feed the sources
	            ArrayList<String> requiredInputs = findRequiredInputs(sources, legacyWorkaroundArray);
	            
	            // Lists to store extra and missing inputs
	            ArrayList<String> extraInputs = new ArrayList<String>();
	            ArrayList<String> missingInputs = new ArrayList<String>();
	            
	            // Extra inputs are ones that are selected but not required
	            for (String data : selectedInputs)
	            {
	            	if (!requiredInputs.contains(data))
	            	{
	            		extraInputs.add(data);
	            	}
	            }
	            
	            // Missing inputs are ones that are required but not selected
	            for (String data : requiredInputs)
	            {
	            	if (!selectedInputs.contains(data))
	            	{
	            		missingInputs.add(data);
	            	}
	            }
	            
	            String resultText = "<html>Result: ";
	            
	            // First scenario: Exact match of selected and required inputs
	            if (extraInputs.size() == 0 && missingInputs.size() == 0)
	            {
	            	resultText += "Selected inputs and required inputs exactly match.<br>";
	            }
	            else // There are unmatched inputs, display them
	            {
	            	// First display extra inputs if there are any
	            	if (extraInputs.size() > 0)
	            	{
	            		// Display name of each input
	            		for (String extraInput : extraInputs)
	            		{
	            			resultText += extraInput + ", ";	
	            		}
	            		
	            		// Display informative text with correct grammar
	            		resultText += (extraInputs.size() == 1 ? "is" : "are") + " not required<br>";
	            	}
	            	
	            	// Then display missing inputs if there are any 
	            	if (missingInputs.size() > 0)
	            	{
	            		// Display name of each input
	            		for (String missingInput : missingInputs)
	            		{
	            			resultText += missingInput + ", ";	
	            		}
	            		// Display informative text
	            		resultText += "should be in the set of inputs<br>";
	            	}
	            }
	            
	            // Finally, display the required components by iterating through the sources
	            resultText += "Required Components: ";
	            for (StronglyConnectedService scs : sources)
	            {
	            	resultText += "S" + scs.getIndex() + ", ";
	            }
	            
	            resultText += "</html>";
	            
	            // Display result in label
	            resultLabel.setText(resultText);
	          }

	        };
	        
	        // Link listener to evaluate button
	        evaluateButton.addActionListener(evaluateButtonPressedHandler);
	        
	    // Fix the size of the scroll pane to enable the scrollbars
		jsp.setPreferredSize(new Dimension(795, 350));
		
		// Add the scrollpane (which contains the dataPanel) to the GUI
		Factory.displayResultCheckProperty(jsp);
	}
	
	// Returns a list of all inputs that are required for a list of scs
	protected ArrayList<String> findRequiredInputs(ArrayList<StronglyConnectedService> sources, ArrayList<String> allInputs) 
	{
		ArrayList<String> requiredInputs = new ArrayList<String>();
		
		// Go through each SCS and find the inputs that feed it
		for (StronglyConnectedService scs : sources)
		{
			for (String input: allInputs)
			{
				// Check if it both feeds the SCS and is not a duplicate
				if (canReachInput(scs, input) && !requiredInputs.contains(input))
				{
					requiredInputs.add(input);
				}
			}
		}
		return requiredInputs;
	}

	// Returns a list of all SCS that feed the outputs given
	private ArrayList<StronglyConnectedService> findSources(ArrayList<String> outputs) 
	{
		// sources list to store the end result
		ArrayList<StronglyConnectedService> sources = new ArrayList<StronglyConnectedService>();
		for (String output : outputs)
		{
			// Check every SCS we know of
			for (StronglyConnectedService scs : scsList)
			{
				// If this SCS can reach the output, it is a 'source' (also check duplicates)
				if (canReachOutput(scs, output) && !sources.contains(scs))
				{
					sources.add(scs);
				}
			}
		}
		return sources;
	}
	
	// Checks if a SCS has a link to a particular data output
	private boolean canReachOutput(StronglyConnectedService scs, String output)
	{
		// Setup an empty list of dependents (A strongly connected service that depends (directly, or indirectly) on 'scs')
		ArrayList<StronglyConnectedService> dependents = new ArrayList<StronglyConnectedService>();
		
		// Populate the dependents list with the actual dependents
		dependentSearch(scs, dependents);
		
		// Check every dependent service
		for (StronglyConnectedService service : dependents)
		{
			// If the service has an output that matches our final desintation output
			if (service.hasOutput(output))
			{
				// then this service can reach the output, therefore the scs can reach the output
				return true;
			}
		}
		
		// Could not find any dependent service that was able to reach the output
		return false;
	}
	
	// Checks if a SCS has a link to a particular data input
	private boolean canReachInput(StronglyConnectedService scs, String input)
	{
		// Setup empty list of dependencies
		ArrayList<StronglyConnectedService> dependencies = new ArrayList<StronglyConnectedService>();
		
		// Populate dependencies list using the dependency search. Finds both direct and indirect dependencies.
		dependencySearch(scs, dependencies);
		
		// Check every dependency
		for (StronglyConnectedService service : dependencies)
		{
			// If the dependency has the input we're looking for
			if (service.hasInput(input))
			{
				// then this dependency can reach the input, therefore our scs can reach the input
				return true;
			}
		}
		return false;
	}
	
	// Depth first search to find all dependencies of a scs, storing result in 'visited'
	private void dependencySearch(StronglyConnectedService scs, ArrayList<StronglyConnectedService> visited)
	{
		// Add the current scs to the visited list
		visited.add(scs);
		
		// Check all immediate dependencies of the scs
		for (StronglyConnectedService dependency : getImmediateDependencies(scs))
		{
			// If we have not searched it yet, then search it now
			if (!visited.contains(dependency))
			{
				dependencySearch(dependency, visited);
			}
		}
	}
	
	// Depth first search to find all dependents of a scs, storing result in 'visited'
	private void dependentSearch(StronglyConnectedService scs, ArrayList<StronglyConnectedService> visited)
	{
		// add the current scs to the visited list
		visited.add(scs);
		
		// Check all immediate dependents of the scs
		for (StronglyConnectedService dependent : getImmediateDependents(scs))
		{
			// If we have not searched it yet, then search it now
			if (!visited.contains(dependent))
			{
				dependentSearch(dependent, visited);
			}
		}
	}
	
	// Finds the SCS that are immediate dependencies of the target
	public ArrayList<StronglyConnectedService> getImmediateDependencies(StronglyConnectedService target) 
	{
		// Start with empty list
		ArrayList<StronglyConnectedService> dependencies = new ArrayList<StronglyConnectedService>();
		
		// Find all inputs to the target scs
		ArrayList<IOVariable> allInputsToTarget = target.getAllInputs();
		
		// Check every source in the master list
		for (StronglyConnectedService source : scsList)
		{
			// Check every input to the target scs
			for (IOVariable input : allInputsToTarget)
			{
				// If the source has an output that matches one of the inputs to the target (and we haven't already recorded it)
				if (source.hasOutput(input.getName()) && !dependencies.contains(source))
				{
					// Record it
					dependencies.add(source);
				}
			}		
		}
		return dependencies;
	}
	
	// Finds the SCS that are immediate dependents of the target
	public ArrayList<StronglyConnectedService> getImmediateDependents(StronglyConnectedService target) 
	{
		// Start with empty list
		ArrayList<StronglyConnectedService> dependents = new ArrayList<StronglyConnectedService>();	
		// Find all outputs from the target scs
		ArrayList<IOVariable> allOutputsFromTarget = target.getAllOutputs();		
		// Check every source in the master list
		for (StronglyConnectedService source : scsList)
		{
			// Check every output of the target scs
			for (IOVariable output : allOutputsFromTarget)
			{
				// If the source has an input that matches one of the outputs to the target (and we haven't already recorded it)
				if (source.hasInput(output.getName()) && !dependents.contains(source))
				{
					// record it
					dependents.add(source);
				}
			}
				
		}
		return dependents;
	}

}
