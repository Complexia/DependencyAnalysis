package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
		// this is all somewhat speculative and done late at night, needs logic
		// checking but it looks to be a step in the right direction

		for (SimpleService service : hashes.values()) {
			// performing a rough and dirty clone so we have something to break
			// up into subservices without destroying the original
			SimpleService meatTray = new SimpleService("meat", service.getInputService(), service.getOutputService(),
					service.getNameOfVariable(), service.getInputVariable(), service.getOutputVariable());

			int subsIndex = 0;
			ArrayList<IOVariable> rejects = new ArrayList<IOVariable>();
			HashMap<IOVariable, ArrayList<IOVariable>> subs = new HashMap<IOVariable, ArrayList<IOVariable>>();
			ArrayList<IOVariable> meatlet = new ArrayList<IOVariable>(meatTray.getNameOfVariable());
			for (IOVariable lVar : meatlet) {
				subs.put(lVar, new ArrayList<IOVariable>());// create new
															// anonymous array
															// for the
															// subservice to be
															// made
				meatTray.removeVariable(lVar);// take it out of the checked
												// service so we don't get false
												// positives from the disjoint

				for (IOVariable out : lVar.outputs) {
					if (Collections.disjoint(out.inputs, meatTray.getNameOfVariable()))// check
																						// if
																						// output
																						// has
																						// multiple
																						// local
																						// dependencies
					{
						subs.get(subsIndex).add(out);
						meatTray.removeOutputService(out);
					} else {
						rejects.add(out);// put it in the list of outputs to be
											// made into their own elementary
											// services
						meatTray.removeOutputService(out);
					}
				}
				subsIndex += 1;

			}
			assert meatTray.getNameOfVariable().size() == 0;// sanity check to
															// ensure all locals
															// are handeled
															// first
			meatlet = new ArrayList<IOVariable>(meatTray.getOutputService());
			for (IOVariable out : meatlet) {
				rejects.add(out);
				meatTray.removeOutputService(out);
			}

			assert meatTray.getOutputService().size() == 0;// have we got all
															// the outputs
															// sorted into
															// elementaries

			// make the subservices now we have sorted out the outputs
			for (Entry<IOVariable, ArrayList<IOVariable>> outList : subs.entrySet()) {
				subServiceCount++;
				SimpleService sub = new SimpleService();
				sub.setName(service.getName() + (service.getChildren().size() + 1));
				sub.setParent(service);
				// convert single variable into a list
				ArrayList<IOVariable> lin = new ArrayList<IOVariable>();
				lin.add(outList.getKey());
				sub.setNameOfVariable(lin);
				sub.setOutputService(outList.getValue());
				ArrayList<IOVariable> ins = new ArrayList<IOVariable>();
				ins.addAll(outList.getKey().inputs);
				for (IOVariable outs : outList.getValue()) {
					ins.removeAll((ArrayList<IOVariable>) outs.inputs);// remove
																		// all
																		// elements
																		// in
																		// the
																		// input
																		// array
																		// to
																		// ensure
																		// no
																		// duplicates
					ins.addAll((ArrayList<IOVariable>) outs.inputs);// put all
																	// the
																	// inputs
																	// from the
																	// outputs
																	// into the
																	// service,
																	// i'm not
																	// entirely
																	// sure we
																	// need
																	// this.
																	// tarjan's
																	// might
																	// require
																	// it
				}
				sub.setInputService(ins);
				sub.setChildren(null);
				sub.setIsElementary(true);
			}
			// make the single output no local variable elementary services
			for (IOVariable reject : rejects) {
				subServiceCount++;
				SimpleService sub = new SimpleService();
				sub.setName(service.getName() + (service.getChildren().size() + 1));
				sub.setParent(service);
				ArrayList<IOVariable> tmp = new ArrayList<IOVariable>();
				tmp.add(reject);
				sub.setOutputService(tmp);
				sub.setInputService((ArrayList<IOVariable>) reject.inputs);
				sub.setChildren(null);
				sub.setIsElementary(true);
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
		for (SimpleService serv : hashes.values()) {
			tableData[rowIndex][0] = serv.getName();
			for (SimpleService servlet : serv.getChildren()) {
				tableData[rowIndex][1] = servlet.getName();
				rowIndex++;
			}
			rowIndex++;
		}

		Factory.displayResult(tableData, columnNames);
		
	}

}
