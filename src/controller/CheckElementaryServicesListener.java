package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import factory.Factory;
import model.IOVariable;
import model.SimpleService;
import model.UploadFile;

public class CheckElementaryServicesListener implements ActionListener {
	
	private Object[][] tableData;
	private Object[] columnNames;

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		HashMap<String, SimpleService> hashes = UploadFile.getVariablesMap();

		int subServiceCount = 0;

		for (SimpleService service : hashes.values()) 
		{
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
				if(Collections.disjoint(service.getNameOfVariable(),out.inputs))
				{
					SimpleService subService = new SimpleService();
					subService.setName(service.getName() + Integer.toString((service.getChildren().size())));
					subService.setInputService((ArrayList<IOVariable>)out.inputs);
					subService.setOutputService( new ArrayList<IOVariable>(Arrays.asList(out)));
					subService.setParent(service);
					subService.setIsElementary(true);
					service.addChild(subService);
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
		columnNames = new Object[] { "ServiceID ", "Generated elementary services" };
		int rowIndex = 0;
		// create a two dimensional array to store display data
		tableData = new Object[subServiceCount][columnNames.length];
		for (SimpleService serv : hashes.values()) 
		{
			tableData[rowIndex][0] = serv.getName();
			for (SimpleService servlet : serv.getChildren()) 
			{
				tableData[rowIndex][1] = servlet.getName();
				rowIndex++;
			}
			rowIndex++;
		}

		Factory.displayResult(tableData, columnNames);
		
	}
	
	private void recursiveInputAdd(SimpleService parent, SimpleService child, IOVariable out, ArrayList<IOVariable> checkedLocals)
	{
		for(IOVariable input : out.inputs)
		{
			if(parent.getNameOfVariable().contains(input))
			{
				input.setLocal(true);//should be set before but we're just making sure
				if(!checkedLocals.contains(input))
				{
					checkedLocals.add(input);
					child.addVariable(input);
					for(IOVariable varOut : input.outputs)
					{
						recursiveInputAdd(parent, child, varOut, checkedLocals);//tbh i'm confusing myself here, but i did some desk checks and the logic checks out
					}
					//if(subs.containsKey(input)) subs.get(input).add(out); else{ ArrayList<IOVariable> o = new ArrayList<IOVariable>();o.add(out); subs.put(input, o);} TODO: prolly get rid of this
				}
				
			}
		}
	}

}
