package factory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import graphic.MainWindow;
import model.Service;
import model.ServiceNode;
import model.SystemGenerator;

/**
 * Contains all necessary global data in a centralised location for ease of access and modification.
 *
 */
public class Factory {
	
	/**
	 * The root JFrame containing the GUI
	 */
	static MainWindow mainWindow;
//	static ButtonPanel buttonPanel;
//	static ButtonPanel1 buttonPanel1;
	static SystemGenerator systemGenerator;
	/**
	 * The file chosen by the user
	 */
	static File selectedFile = null;
	/**
	 * Jpanel that contains and displays all output data
	 */
	static JPanel dataPanel;
	/**
	 * Container for all the other GUI components
	 */
	static JPanel programPanel;
	/**
	 * Container for the GUI buttons
	 */
	static JToolBar buttonPanel;
	/**
	 * List containing all identified elementary services
	 */
	static ArrayList<Service> elementaryServices;
	/**
	 * List containing all the services
	 */
	static List<model.Service> lstData;
	/**
	 * List containing all service nodes
	 */
	static ArrayList<ServiceNode> nodes;
	/**
	 * Boolean to track whether L0 was previously generated for the current file
	 */
	static Boolean L0Generated = false;
	/**
	 * Boolean to track whether L1 was previously generated for the current file
	 */
	static Boolean L1Generated = false;
	/**
	 * Boolean to track whether check elementary services was previously run for the current file
	 */
	static Boolean chkElemServGenerated = false;
	/**
	 * Boolean to track whether L2 was previously generated for the current file
	 */
	static Boolean L2Generated = false;
	/**
	 * Boolean to track whether Strongly connected services was previously run for the current file
	 */
	static Boolean SCSGenerated = false;
	/**
	 * @return the l0Generated
	 */
	public static Boolean getL0Generated() {
		return L0Generated;
	}

	/**
	 * @param l0Generated the l0Generated to set
	 */
	public static void setL0Generated(Boolean l0Generated) {
		L0Generated = l0Generated;
	}

	/**
	 * @return the l1Generated
	 */
	public static Boolean getL1Generated() {
		return L1Generated;
	}

	/**
	 * @param l1Generated the l1Generated to set
	 */
	public static void setL1Generated(Boolean l1Generated) {
		L1Generated = l1Generated;
	}

	/**
	 * @return the chkElemServGenerated
	 */
	public static Boolean getChkElemServGenerated() {
		return chkElemServGenerated;
	}

	/**
	 * @param chkElemServGenerated the chkElemServGenerated to set
	 */
	public static void setChkElemServGenerated(Boolean chkElemServGenerated) {
		Factory.chkElemServGenerated = chkElemServGenerated;
	}

	/**
	 * @return the l2Generated
	 */
	public static Boolean getL2Generated() {
		return L2Generated;
	}

	/**
	 * @param l2Generated the l2Generated to set
	 */
	public static void setL2Generated(Boolean l2Generated) {
		L2Generated = l2Generated;
	}

	/**
	 * @return the sCSGenerated
	 */
	public static Boolean getSCSGenerated() {
		return SCSGenerated;
	}

	/**
	 * @param sCSGenerated the sCSGenerated to set
	 */
	public static void setSCSGenerated(Boolean sCSGenerated) {
		SCSGenerated = sCSGenerated;
	}

	
	
	/**
	 * Default setter for nodes.
	 * @param nodes1 input list of nodes to set
	 */
	public static void setNodes(ArrayList<ServiceNode> nodes1){
		nodes = nodes1;
	}
	
	/**
	 * @return A list of the currently stored nodes
	 */
	public static ArrayList<ServiceNode> getNodes(){
		return nodes;
	}
	
	/**
	 * @param lstData1 list of services to set
	 */
	public static void setLstData(List<model.Service> lstData1){
		
		lstData = lstData1;
		
		
	}
	
	/**
	 * @return list of stored services
	 */
	public static List<model.Service> getLstData(){
		
		return lstData;
		
		
	}
	
	/**
	 * @return list of elementary services
	 */
	public static ArrayList<model.Service> getElementaryServices(){
		return elementaryServices;
	}
	
	/**
	 * @param elementaryServices1 list of elementary services to set
	 */
	public static void setElementaryServices(ArrayList<Service> elementaryServices1){
		elementaryServices = elementaryServices1;
	}
	
	/**
	 * @param dataPanel1 JPanel to set as data panel
	 */
	public static void setDataPanel(JPanel dataPanel1){
		dataPanel = dataPanel1;
	}
	
	/**
	 * @param programPanel1 JPanel to set as program container
	 */
	public static void setProgramPanel(JPanel programPanel1){
		programPanel = programPanel1;
	}
	
	/**
	 * @return the buttonPanel
	 */
	public static JToolBar getButtonPanel() {
		return buttonPanel;
	}

	/**
	 * @param buttonPanel the buttonPanel to set
	 */
	public static void setButtonPanel(JToolBar buttonPanel) {
		Factory.buttonPanel = buttonPanel;
	}

	/**
	 * @param selectedFile1 the input file to set
	 */
	public static void setSelectedFile(File selectedFile1){
		
		selectedFile = selectedFile1;
		
	}
	
	/**
	 * Tabulate input data from any of the generate methods and display it in
	 * a clear table form
	 * @param tableData The data to input into the table
	 * @param columnNames The names of the columns of the table
	 */
	public static void displayResult(Object[][] tableData, Object[] columnNames) {
		if (tableData != null && columnNames != null) {
			programPanel.remove(dataPanel);
			programPanel.remove(buttonPanel);
			dataPanel = new JPanel();
			JTable table = new JTable(tableData, columnNames);
			table.setSize(800, 350);
			table.setPreferredScrollableViewportSize(new Dimension(800, 350));
			//table.setFillsViewportHeight(true);
			dataPanel.add(new JScrollPane(table));
			dataPanel.setPreferredSize(new Dimension(795, 350));
			programPanel.add(buttonPanel, BorderLayout.PAGE_START);
			programPanel.add(dataPanel, BorderLayout.CENTER);
			//mainWindow.updateUI();
			programPanel.revalidate();
			programPanel.repaint();
		}
	}
	
	/**
	 * @return current input file
	 */
	public static File getSelectedFile(){
		return selectedFile;
	}
	
	/**
	 * @param mainWindow1 JFrame to set as the program GUI root pane
	 */
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
	/**
	 * @return the program GUI root JFrame
	 */
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

	/**
	 * @return the systemGenerator
	 */
	public static SystemGenerator getSystemGenerator()
	{
		return systemGenerator;
	}

	/**
	 * @param systemGenerator the systemGenerator to set
	 */
	public static void setSystemGenerator(SystemGenerator systemGenerator)
	{
		Factory.systemGenerator = systemGenerator;
	}
	
	
	

}
