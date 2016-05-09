package factory;

import java.awt.Dimension;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


import graphic.MainWindow;
import model.Service;
import model.ServiceNode;

public class Factory {
	
	static MainWindow mainWindow;
//	static ButtonPanel buttonPanel;
//	static ButtonPanel1 buttonPanel1;
	static File selectedFile;
	static JPanel dataPanel;
	static JPanel programPanel;
	static ArrayList<Service> elementaryServices;
	static List<model.Service> lstData;
	static ArrayList<ServiceNode> nodes;
	
	public static void setNodes(ArrayList<ServiceNode> nodes1){
		nodes = nodes1;
	}
	
	public static ArrayList<ServiceNode> getNodes(){
		return nodes;
	}
	
	public static void setLstData(List<model.Service> lstData1){
		
		lstData = lstData1;
		
		
	}
	
	public static List<model.Service> getLstData(){
		
		return lstData;
		
		
	}
	
	public static ArrayList<Service> getElementaryServices(){
		return elementaryServices;
	}
	
	public static void setElementaryServices(ArrayList<Service> elementaryServices1){
		elementaryServices = elementaryServices1;
	}
	
	public static void setDataPanel(JPanel dataPanel1){
		dataPanel = dataPanel1;
	}
	
	public static void setProgramPanel(JPanel programPanel1){
		programPanel = programPanel1;
	}
	
	public static void setSelectedFile(File selectedFile1){
		
		selectedFile = selectedFile1;
		
	}
	
	public static void displayResult(Object[][] tableData, Object[] columnNames) {
		if (tableData != null && columnNames != null) {
			programPanel.remove(dataPanel);
			dataPanel = new JPanel();
			JTable table = new JTable(tableData, columnNames);
			table.setSize(800, 350);
			table.setPreferredScrollableViewportSize(new Dimension(800, 350));
			table.setFillsViewportHeight(true);
			dataPanel.add(new JScrollPane(table));
			dataPanel.setPreferredSize(new Dimension(795, 350));
			programPanel.add(dataPanel);
			//mainWindow.updateUI();
			programPanel.revalidate();
			programPanel.repaint();
		}
	}
	
	public static File getSelectedFile(){
		return selectedFile;
	}
	
	public static void setMainWindow(MainWindow mainWindow1){
		mainWindow = mainWindow1;
	}
	
//	public static void setButtonPanel(ButtonPanel buttonPanelX){
//		buttonPanel = buttonPanelX;
//		Factory.getMainWindow().getMainPanel().add(buttonPanel);
//		Factory.getMainWindow().getMainPanel().repaint();
//	}
	
//	public static void setButtonPanel1(ButtonPanel1 buttonPanel1X){
//		buttonPanel1 = buttonPanel1X;
//		
//	}
//	
	public static MainWindow getMainWindow(){
		return mainWindow;
	}
//	
//	public static  ButtonPanel getButtonPanel(){
//		return buttonPanel;
//	}
//	
//	public static ButtonPanel1 getButtonPanel1(){
//		return buttonPanel1;
//	}
	
	
	

}
