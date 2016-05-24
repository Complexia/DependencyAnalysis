/**
 * 
 */
package model;

import java.io.File;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;

import factory.Factory;

/**
 * This class is responsible for handling the generation of the various system levels.
 * Designed to provide a centralised location for the business logic and allow
 * more transparent method calling.
 *
 */
public class SystemGenerator
{

	/**
	 * Default constructor. No information is needed as this will only run methods without storing data
	 */
	public SystemGenerator()
	{
		
	}
	
	/**
	 * Prompts the user for an input file and resets the generated level flags to allow the program
	 * to generate new levels based on the new input.
	 * Currently this only accepts XML files but it will later be extended to include other file types.
	 */
	public void uploadFile()
	{
		
		List<SimpleService> lstData = null;
		
		File selectedFile = null;
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(Factory.getMainWindow());
		if (result == JFileChooser.APPROVE_OPTION) {
		    selectedFile = fileChooser.getSelectedFile();
		    Factory.setSelectedFile(selectedFile);
		    try {
				
				lstData = UploadFile.readFromXML(selectedFile);
				Factory.setLstData(lstData);
				Factory.setL0Generated(false);
				Factory.setL1Generated(false);
				Factory.setL2Generated(false);
				Factory.setChkElemServGenerated(false);
				Factory.setSCSGenerated(false);
			}

			catch (Exception e1)// catch any of the general exceptions
			{
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
		    
		}
		else{
			System.out.println("File was not read");
		}
	}
	
	/**
	 * Responsible for creating the first and most basic level of the system with no decompositions.
	 * If an input file hasn't been chosen, it runs the uploadFile method to prompt the user.
	 */
	public void generateL0()
	{
		if(Factory.getSelectedFile() == null) uploadFile();
		Object[][] tableData = null;
		Object[] columnNames = null;
		
		if(Factory.getL0Generated()){ Factory.displayResult(tableData, columnNames); return;}//if it was done already, just reshow the old stuff
		columnNames = new Object[] { "Name of Service", "Input Data", "Output Data", "Name of Variable",
				"Input Variable", "Output Variable" };

		tableData = new Object[20][columnNames.length];
		int rowIndex = 0;

		int index = 0;
		
		HashMap<String, SimpleService> hashes = UploadFile.getVariablesMap();
		Factory.setLstData(new ArrayList<SimpleService>(hashes.values()));
		int size, count;
		for (SimpleService eachService : hashes.values()) {

			index = 0;
			String string = "";

			tableData[rowIndex][index++] = eachService.getName();
			size = eachService.getInputService().size()-1;
			count = 0;
			for (model.IOVariable str : eachService.getInputService()) 
			{
				if(str == null) continue;
				string += count < size? str.name + ",": str.name;
				count++;
			}
			tableData[rowIndex][index++] = string;
			string = "";// gotta clean the variable out between uses or you get
						// the previous data as well
			size = eachService.getOutputService().size()-1;
			count = 0;
			for (model.IOVariable str : eachService.getOutputService()) 
			{
				if(str == null) continue;
				string += count < size? str.name + ",": str.name;
				count++;
			}
			tableData[rowIndex][index++] = string;
			string = "";
			size = eachService.getNameOfVariable().size()-1;
			count = 0;
			for (model.IOVariable str : eachService.getNameOfVariable()) 
			{
				if(str == null) continue;
				string += count < size? str.name + ",": str.name;
				count++;
			}
			tableData[rowIndex][index++] = string;
			string = "";
			size = eachService.getInputVariable().size()-1;
			count = 0;
			for (model.IOVariable str : eachService.getInputVariable()) 
			{
				if(str == null) continue;
				string += count < size? str.name + ",": str.name;
				count++;
			}
			tableData[rowIndex][index++] = string;
			string = "";
			size = eachService.getOutputVariable().size()-1;
			count = 0;
			for (model.IOVariable str : eachService.getOutputVariable()) 
			{
				if(str == null) continue;
				string += count < size? str.name + ",": str.name;
				count++;
			}
			tableData[rowIndex][index++] = string;
			string = "";

			rowIndex++;

		}
		Factory.displayResult(tableData, columnNames);
		Factory.setL0Generated(true);
	}
	
	/**
	 * Creates the L1 decomposition for the input system.
	 * If generateL0 hasn't been executed then it does so before continuing.
	 * The name is a slight misnomer and is named so by the previous team.
	 */
	public void checkElementaryServices()
	{
		if(Factory.getL0Generated() == false) generateL0();
		Object[][] tableData = null;
		Object[] columnNames = null;
		
		ArrayList<SimpleService> elementaryServices = null;
		ArrayList<SimpleService> lstData1;
		
		HashMap<String, SimpleService> hashes = UploadFile.getVariablesMap();

		int subServiceCount = 0;
		if(Factory.getChkElemServGenerated()){ Factory.displayResult(tableData, columnNames); return;}//if it was done already, just reshow the old stuff

		for (SimpleService service : hashes.values()) 
		{
			
			int subsIndex = 0;
			
			HashMap<IOVariable, ArrayList<IOVariable>> subs = new HashMap<IOVariable, ArrayList<IOVariable>>();//TODO: throw away, probs not needed
			ArrayList<IOVariable>outs = new ArrayList<IOVariable>(service.getOutputService());
			ArrayList<IOVariable>checkedLocals = new ArrayList<IOVariable>();//somewhere to keep track of what was evaluated
			for (IOVariable out : outs) 
			{
				if(out == null) continue;//skip it. workaround for now to get it displaying then i'll track down where the null is getting in
				//TODO: properly debug the source and reason for the null objects in the lists
				if(Collections.disjoint(service.getNameOfVariable(),out.inputs))
				{
					SimpleService subService = new SimpleService();
					subService.setName(service.getName() + Integer.toString((service.getChildren().size())));
					subService.setInputService((ArrayList<IOVariable>)out.inputs);
					subService.setOutputService( new ArrayList<IOVariable>(Arrays.asList(out)));
					subService.setParent(service);
					subService.setIsElementary(true);
					service.addChild(subService);
					subServiceCount++;
					
					elementaryServices.add(subService);
				}
				else
				{
					SimpleService subService = new SimpleService();
					recursiveInputAdd(service, subService, out, checkedLocals);
					ArrayList<IOVariable> subInputs = new ArrayList<IOVariable>();
					//build list of service inputs
					for (IOVariable outputs : subService.getOutputService()) 
					{
						subInputs.removeAll((ArrayList<IOVariable>) outputs.inputs);
						subInputs.addAll((ArrayList<IOVariable>) outputs.inputs);
					}
					for (IOVariable outputs : subService.getNameOfVariable()) 
					{
						subInputs.removeAll((ArrayList<IOVariable>) outputs.inputs);
						subInputs.addAll((ArrayList<IOVariable>) outputs.inputs);
					}
					//take out the local vars that were included
					subInputs.removeAll(subService.getNameOfVariable());
					
					ArrayList<IOVariable> subOutputs = new ArrayList<IOVariable>();
					for(IOVariable var : subService.getNameOfVariable())
					{
						subOutputs.removeAll((ArrayList<IOVariable>) var.outputs);
						subOutputs.addAll((ArrayList<IOVariable>) var.outputs);
					}
					subService.setName(service.getName() + Integer.toString((service.getChildren().size())));
					subService.setInputService(subInputs);
					subService.setOutputService(subOutputs);
					subService.setParent(service);
					subService.setIsElementary(true);
					service.addChild(subService);
					subServiceCount++;
					
					elementaryServices.add(subService);
					
				}
			}



		}
		
		Factory.setChkElemServGenerated(true);
		Factory.setElementaryServices(elementaryServices);
	}
	
	/**
	 * displays the L1 system view.
	 * If checkElementaryServices hasn't been executed, then it does so before continuing.
	 */
	public void generateL1()
	{
		if(Factory.getChkElemServGenerated() == false) checkElementaryServices();
		Object[][] tableData = null;
		Object[] columnNames = null;
		//private HashMap<String, SimpleService> hashes = UploadFile.getVariablesMap();

		int subServiceCount = 0;
		ArrayList<SimpleService> elementaryServices;
		ArrayList<SimpleService> lstData1;
		
		HashMap<String, SimpleService> hashes = UploadFile.getVariablesMap();
		
		if(Factory.getL1Generated()){ Factory.displayResult(tableData, columnNames); return;}//if it was done already, just reshow the old stuff
		elementaryServices = new ArrayList<SimpleService>();
		columnNames = new Object[] { "Main Service", "Sub Service", "inputs", "outputs", "name of variable",
				"inputs  variable", "outputs variable" };

		tableData = new Object[20][columnNames.length];
		int rowIndex = 0;
		int index = 0;
		//HashMap<String, SimpleService> hashes = UploadFile.getVariablesMap();
		// loop through each service
		/*
		 * lstData.add(new Service()); lstData1 = new
		 * ArrayList<SimpleService>(); String nameOfService = (String)
		 * UploadFile.getVariablesMap().get("nameOfService"); String inputData =
		 * (String) UploadFile.getVariablesMap().get("inputService"); String
		 * outputData = (String)
		 * UploadFile.getVariablesMap().get("outputService"); String
		 * nameOfVariable = (String)
		 * UploadFile.getVariablesMap().get("nameOfVariable"); String
		 * inputVariable = (String)
		 * UploadFile.getVariablesMap().get("inputVariable"); String
		 * outputVariable = (String)
		 * UploadFile.getVariablesMap().get("outputVariable");
		 * 
		 * SimpleService service1 = new SimpleService(nameOfService, inputData,
		 * outputData, nameOfVariable, inputVariable, outputVariable);
		 * lstData1.add(service1);
		 */
		int size, count;
		for (SimpleService eachService : hashes.values()) {

			// essentially a copy paste of generateL0 but using the subservices
			// as input

			// loop through elementary services/ sub service
			 
			for (SimpleService service : eachService.getChildren()) {
				index = 0;
				String string = "";

				tableData[rowIndex][index++] = eachService.getName();
				tableData[rowIndex][index++] = service.getName();
				
				size = service.getInputService().size()-1;//make it the value of the last index
				count = 0;
				for (IOVariable str : service.getInputService()) 
				{
					string += count < size? str.name + ",": str.name;
					count++;
				}
				tableData[rowIndex][index++] = string;
				
				string = "";// gotta clean the variable out between uses or you
							// get the previous data as well				
				size = service.getOutputService().size()-1;
				count = 0;
				for (IOVariable str : service.getOutputService()) 
				{
					string += count < size? str.name + ",": str.name;
					count++;
				}
				tableData[rowIndex][index++] = string;
				
				string = "";				
				size = service.getNameOfVariable().size()-1;//make it the value of the last index
				count = 0;
				for (IOVariable str : service.getNameOfVariable()) 
				{
					string += count < size? str.name + ",": str.name;
					count++;
				}
				tableData[rowIndex][index++] = string;
				
				string = "";				
				size = service.getInputVariable().size()-1;//make it the value of the last index
				count = 0;
				for (IOVariable str : service.getInputVariable()) 
				{
					string += count < size? str.name + ",": str.name;
					count++;
				}
				tableData[rowIndex][index++] = string;
				
				string = "";				
				size = service.getOutputVariable().size()-1;//make it the value of the last index
				count = 0;
				for (IOVariable str : service.getOutputVariable()) 
				{
					string += count < size? str.name + ",": str.name;
					count++;
				}
				tableData[rowIndex][index++] = string;
				string = "";

				rowIndex++;
			}

			// index = 0;
			// System.out.println("index: " + index + " rowindex " +
			// rowIndex + " each service: " + eachService.GetName());
			// rowIndex++;

		}
		Factory.displayResult(tableData, columnNames);
		Factory.setL1Generated(true);
	}
	
	/**
	 * Runs through the list of connected IOVariables and connects all the inputs together, recursively running itself
	 * on local variables so all related local variables are associated to the same subservice.
	 * @param parent The parent service being decomposed
	 * @param child the subservice being created
	 * @param out The local variable that starts the recursion chain.
	 * @param checkedLocals Reference to the list keeping track of local variables that have been checked to prevent looping recursive calls.
	 */
	private void recursiveInputAdd(SimpleService parent, SimpleService child, IOVariable out, ArrayList<IOVariable> checkedLocals)
	{
		for(IOVariable input : out.inputs)
		{
			if(parent.getNameOfVariable().contains(input))
			{
				input.setLocal(true);//should be set before but we're just making sure
				if(!checkedLocals.contains(input))//probably where the bug is occurring HERE!!!!!--------------
				{
					checkedLocals.add(input);
					child.addVariable(input);
					for(IOVariable varOut : input.outputs)
					{
						recursiveInputAdd(parent, child, varOut, checkedLocals);//tbh i'm confusing myself here, but i did some desk checks and the logic checks out
					}
					//if(subs.containsKey(input)) subs.get(input).add(out); else{ ArrayList<IOVariable> o = new ArrayList<IOVariable>();o.add(out); subs.put(input, o);} TODO: probably get rid of this
				}
				
			}
		}
	}

}
