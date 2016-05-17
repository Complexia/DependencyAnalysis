package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import factory.Factory;
import model.IOVariable;
import model.Service;
import model.SimpleService;
import model.UploadFile;

public class CheckElemAndGenerateL1Listener implements ActionListener{
	
	private Object[][] tableData;
	private Object[] columnNames;
	//private HashMap<String, SimpleService> hashes = UploadFile.getVariablesMap();

	private int subServiceCount = 0;
	private ArrayList<Service> elementaryServices;
	private ArrayList<SimpleService> lstData1;
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//-------------------------------------checkElementaryServices--------------------------------------------------------------------------------------------------------------
		HashMap<String, SimpleService> hashes = UploadFile.getVariablesMap();

		int subServiceCount = 0;
		if(Factory.getChkElemServGenerated()){ Factory.displayResult(tableData, columnNames); return;}//if it was done already, just reshow the old stuff

		for (SimpleService service : hashes.values()) 
		{
			// !!DEPRECTAED!!
			// performing a rough and dirty clone so we have something to break
			// up into subservices without destroying the original
			//SimpleService meatTray = new SimpleService("meat", service.getInputService(), service.getOutputService(),
			//		service.getNameOfVariable(), service.getInputVariable(), service.getOutputVariable());

			int subsIndex = 0;
			/* needs to be reconfigured
			ArrayList<IOVariable> rejects = new ArrayList<IOVariable>();
			HashMap<IOVariable, ArrayList<IOVariable>> subs = new HashMap<IOVariable, ArrayList<IOVariable>>();
			ArrayList<IOVariable> meatlet = new ArrayList<IOVariable>(meatTray.getNameOfVariable());
			for (IOVariable lVar : meatlet) 
			{
				subs.put(lVar, new ArrayList<IOVariable>());
				meatTray.removeVariable(lVar);

				for (IOVariable out : lVar.outputs) {
					if (Collections.disjoint(out.inputs, meatTray.getNameOfVariable()))
					{
						subs.get(subsIndex).add(out);
						meatTray.removeOutputService(out);
					} else {
						rejects.add(out);
						meatTray.removeOutputService(out);
					}
				}
				subsIndex += 1;

			}
			assert meatTray.getNameOfVariable().size() == 0;
			*/
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
					
				}
			}



		}

		/*
		 * commented so i can test new experimental build for (Service
		 * serviceObj : lstData) { if (serviceObj.getOutputs().size() == 1) {
		 * tableData[rowIndex][0] = serviceObj.name; tableData[rowIndex][1] =
		 * serviceObj.name; rowIndex++; } else { if
		 * (serviceObj.localVariable.size() == 0) { int intCount = 1;
		 * List<String> OutputMerge = new ArrayList<String>(); OutputMerge =
		 * serviceObj.getOutputs(); while (OutputMerge.size() != 0) {
		 * List<String> Output = new ArrayList<String>();
		 * Output.add(serviceObj.getOutputs().get(0)); List<String> InputList =
		 * serviceObj.getIOstreamsByKey(Output.get(0)); InputList =
		 * removeDuplicate3(InputList); OutputMerge.removeAll(Output);
		 * List<IOVariable> VariableList = serviceObj.localVariable;
		 * CreateSubService(serviceObj, intCount, InputList, Output,
		 * VariableList); Service A5 = CreateSubService(serviceObj, intCount,
		 * InputList, Output, VariableList);
		 * serviceObj.addElementaryServices(A5); tableData[rowIndex][0] =
		 * serviceObj.name; tableData[rowIndex][1] = A5.name; //
		 * System.out.println(A5.name+A5.getInputs()+A5.getOutputs());
		 * intCount++; rowIndex++; } } else if (serviceObj.localVariable.size()
		 * == 1) { int intCount = 1; List<String> OutputMerge = new
		 * ArrayList<String>(); OutputMerge = serviceObj.getOutputs(); while
		 * (OutputMerge.size() != 0) { List<String> CheckedOutput = new
		 * ArrayList<String>(); CheckedOutput.clear(); List<String>
		 * CheckedOutput2 = new ArrayList<String>(); CheckedOutput2 =
		 * CheckType112(serviceObj, OutputMerge.get(0), CheckedOutput);
		 * 
		 * List<String> InputList = new ArrayList<String>(); for (int i = 0; i <
		 * CheckedOutput2.size(); i++) {
		 * InputList.addAll(serviceObj.getIOstreamsByKey(CheckedOutput2.get(i)))
		 * ; } InputList = removeDuplicate3(InputList); List<IOVariable>
		 * VariableMerges = new ArrayList<IOVariable>(); for (int i = 0; i <
		 * CheckedOutput2.size(); i++) {
		 * VariableMerges.addAll(FindVariablesfromOut(serviceObj,
		 * OutputMerge.get(i))); } Service A5 = CreateSubService(serviceObj,
		 * intCount, InputList, CheckedOutput2, VariableMerges);
		 * serviceObj.addElementaryServices(A5); intCount++;
		 * OutputMerge.removeAll(CheckedOutput2); tableData[rowIndex][0] =
		 * serviceObj.name; tableData[rowIndex][1] = A5.name; rowIndex++; } }
		 * else { int intCount = 1; List<String> OutputMerge = new
		 * ArrayList<String>(); OutputMerge = serviceObj.getOutputs(); while
		 * (OutputMerge.size() != 0) { List<String> CheckedOutput = new
		 * ArrayList<String>(); CheckedOutput.clear(); List<String>
		 * CheckedOutput2 = new ArrayList<String>(); CheckedOutput2 =
		 * CheckType112(serviceObj, OutputMerge.get(0), CheckedOutput);
		 * 
		 * List<String> InputList = new ArrayList<String>(); for (int i = 0; i <
		 * CheckedOutput2.size(); i++) {
		 * InputList.addAll(serviceObj.getIOstreamsByKey(CheckedOutput2.get(i)))
		 * ; } InputList = removeDuplicate3(InputList); List<IOVariable>
		 * VariableMerges = new ArrayList<IOVariable>(); for (int i = 0; i <
		 * CheckedOutput2.size(); i++) {
		 * VariableMerges.addAll(FindVariablesfromOut(serviceObj,
		 * OutputMerge.get(i))); } Service A5 = CreateSubService(serviceObj,
		 * intCount, InputList, CheckedOutput2, VariableMerges);
		 * serviceObj.addElementaryServices(A5); intCount++;
		 * OutputMerge.removeAll(CheckedOutput2); tableData[rowIndex][0] =
		 * serviceObj.name; tableData[rowIndex][1] = A5.name; rowIndex++; } } }
		 * rowIndex++; } end of old code block
		 */
		// create column name for displaying in the grid
		
		//---------------------------------------------------------------------------------------------------
		
		
//		columnNames = new Object[] { "ServiceID ", "Generated elementary services" };
//		int rowIndex = 0;
//		// create a two dimensional array to store display data
//		tableData = new Object[subServiceCount][columnNames.length];
//		for (SimpleService serv : hashes.values()) 
//		{
//			tableData[rowIndex][0] = serv.getName();
//			for (SimpleService servlet : serv.getChildren()) 
//			{
//				tableData[rowIndex][1] = servlet.getName();
//				rowIndex++;
//			}
//			//rowIndex++;
//		}
//
//		Factory.displayResult(tableData, columnNames);
		
		//-----------------------GenerateL1-------------------------------------------------------------------------------------------------------------------
		
		if(Factory.getL1Generated()){ Factory.displayResult(tableData, columnNames); return;}//if it was done already, just reshow the old stuff
		elementaryServices = new ArrayList<Service>();
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
		Factory.setElementaryServices(elementaryServices);
		Factory.setL1Generated(true);
		Factory.setChkElemServGenerated(true);
		
	
		
		
	}
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
