package model;

import java.util.ArrayList;

public class StronglyConnectedService 
{
	public int index;
	public ArrayList<ServiceNode> nodes;
	
	public StronglyConnectedService(int index) 
	{
		this.index = index;
		nodes = new ArrayList<ServiceNode>();
	}
	
	public int getIndex()
	{
		return index;
	}

	public void add(ServiceNode w) 
	{
		nodes.add(w);
	}

	public ArrayList<ServiceNode> getServiceNodes() 
	{
		return nodes;
	}

	// Check all of our essential services and see if any of them has an input that matches the one given
	public boolean hasInput(String input) 
	{
		for (ServiceNode sn : nodes)
		{
			if (sn.getService().getInputs().contains(input))
			{
				return true;
			}
		}
		return false;
	}
	
	// Check all of our essential services and see if any of them has an output that matches the one given
	public boolean hasOutput(String output) 
	{
		for (ServiceNode sn : nodes)
		{
			if (sn.getService().getOutputs().contains(output))
			{
				return true;
			}
		}
		return false;
	}

	// Compiles a list of all of the inputs to all of the essential services in me
	public ArrayList<String> getAllInputs() 
	{
		ArrayList<String> inputs = new ArrayList<String>();
		
		for (ServiceNode sn : nodes)
		{
			for (String input : sn.getService().getInputs())
			{
				// Avoid duplicates
				if (!inputs.contains(input))
				{
					inputs.add(input);
				}
			}
		}
		
		return inputs;
	}

	// Compiles a list of all of the outputs from all of the essential services in me
	public ArrayList<String> getAllOutputs() 
	{
		ArrayList<String> outputs = new ArrayList<String>();
		
		for (ServiceNode sn : nodes)
		{
			for (String output : sn.getService().getOutputs())
			{
				// Avoid duplicates
				if (!outputs.contains(output))
				{
					outputs.add(output);
				}
			}
		}
		
		return outputs;
	}
	
	// Returns a list of all dataflows that are connected to this SCS (but not internal ones)
		public ArrayList<String> getDataFlows() 
		{
			ArrayList<String> dataFlows = new ArrayList<String>();
			
			// First get all the inputs and outputs for every elementary sub-service
			ArrayList<String> allInputs = getAllInputs();
			ArrayList<String> allOutputs = getAllOutputs();
			
			// To avoid internal flows, only add inputs that are not also outputs
			for (String output : allOutputs)
			{
				if (!allInputs.contains(output))
				{
					dataFlows.add(output);
				}
			}
			
			// To avoid internal flows, only add outputs that are not also inputs
			for (String input : allInputs)
			{
				if (!allOutputs.contains(input))
				{
					dataFlows.add(input);
				}
			}
			
			return dataFlows;
		}
		
	}


