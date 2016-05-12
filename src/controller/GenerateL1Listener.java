package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Service;
import java.util.ArrayList;
import java.util.HashMap;

import factory.Factory;
import model.SimpleService;
import model.UploadFile;
import model.IOVariable;

/**
 * Encapsulates and implements the functionality for generating level 1 of the system.
 * works in conjunction with checkElementaryServices to create the level 1 system view.
 *
 */
public class GenerateL1Listener implements ActionListener {
	
	private Object[][] tableData;
	private Object[] columnNames;
	private ArrayList<Service> elementaryServices;
	private ArrayList<SimpleService> lstData1;

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(Factory.getL1Generated()){ Factory.displayResult(tableData, columnNames); return;}//if it was done already, just reshow the old stuff
		elementaryServices = new ArrayList<Service>();
		columnNames = new Object[] { "Main Service", "Sub Service", "inputs", "outputs", "name of variable",
				"inputs  variable", "outputs variable" };

		tableData = new Object[20][columnNames.length];
		int rowIndex = 0;
		int index = 0;
		HashMap<String, SimpleService> hashes = UploadFile.getVariablesMap();
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
		
	}

}
