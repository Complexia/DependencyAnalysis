/*
This application was created in 2015 by: 
3381049 Gor;Simon Paul, 3207568 Kim;Deok Yeon, 3318707 Yahya;Zohoor and 3405533 Deng;Yang
under the guidance and supervision of Maria Spichkova.

The current iteration of this application has been edited between March and June 2016 by:
Simon Miller s3353879
Roman Lobanov s3399752
Leslie Pang s3390257
Jordan Mason s3423620
...also under the direction and guidance of Maria Spichkova.

The purpose of this application is to break down systems input from an .xml file using various analysis techniques
and then provide easy to read results.
jhgkjhgkjgkjb
 */

//Import relevant packages
import java.lang.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class DataServices extends JFrame implements ActionListener {
	// Create button labels
	private static final long serialVersionUID = 1L;
	private final String BUTTON_LABEL_GENERATE_LO = "Generate L0";
	private final String BUTTON_LABEL_UPLOAD = "Upload L0";
	private final String BUTTON_LABEL_CHECK_ELE_SERVICE = "Check Elem. Serv.";
	private final String BUTTON_LABEL_GENERATE_L1 = "Generate L1";
	private final String BUTTON_LABEL_FIND_SCS = "FindSCS";
	private final String BUTTON_LABEL_GENERATE_L2 = "Generate L2";
	private final String BUTTON_LABEL_CHECK_PROPERTY = "Check Prop.";
	private final String BUTTON_LABEL_Remote_Computation = "Remote_Computation";

	private List<Service> lstData = null;
	private File inputFile;
	private List<Service> elementaryServices = null;
	private ArrayList<ServiceNode> nodes = new ArrayList<ServiceNode>();

	public ArrayList<String> AllData = new ArrayList<String>();
	public ArrayList<JCheckBox> programCheckBoxes = new ArrayList<JCheckBox>();
	public ArrayList<JCheckBox> programCheckBoxes2 = new ArrayList<JCheckBox>();
	public ArrayList<String> DataStore = new ArrayList<String>();
	public ArrayList<String> DataStore2 = new ArrayList<String>();
	ArrayList<ArrayList<StronglyConnectedService>> RSList = new ArrayList<ArrayList<StronglyConnectedService>>();
	ArrayList<ArrayList<StronglyConnectedService>> RSList2 = new ArrayList<ArrayList<StronglyConnectedService>>();

	// Create GUI buttons
	private JButton btnUpload;
	private JButton btnGenerateLO;
	private JButton btnCheckElementaryService;
	private JButton btnGenerateL1;
	private JButton btnFindSCS;
	private JButton btnGenerateL2;
	private JButton btnCheckProperty;
	private JButton btnRemoteComputation;
	// This Flag is used for not add Check boxes to dataPanel twice
	boolean Flag = true;

	private boolean hasFileBeenChoosed = false;
	private JButton programButtons[];
	private JFileChooser fileChooser;

	private JPanel dataPanel, programPanel;
	private Object[] columnNames;
	private Object[][] tableData;

	public static void main(String[] args) {
		DataServices hello = new DataServices();
		hello.setVisible(true);
	}

	public DataServices() {
		btnUpload = new JButton(BUTTON_LABEL_UPLOAD);
		btnGenerateLO = new JButton(BUTTON_LABEL_GENERATE_LO);
		btnCheckElementaryService = new JButton(BUTTON_LABEL_CHECK_ELE_SERVICE);
		btnGenerateL1 = new JButton(BUTTON_LABEL_GENERATE_L1);
		btnFindSCS = new JButton(BUTTON_LABEL_FIND_SCS);
		btnGenerateL2 = new JButton(BUTTON_LABEL_GENERATE_L2);
		btnCheckProperty = new JButton(BUTTON_LABEL_CHECK_PROPERTY);
		btnRemoteComputation = new JButton(BUTTON_LABEL_Remote_Computation);

		btnGenerateL1 = new JButton("Generate L1");
		btnFindSCS = new JButton("Find SCS");
		btnGenerateL2 = new JButton("Generate L2");
		btnCheckProperty = new JButton("Check property");
		btnRemoteComputation = new JButton("RemoteCom HighLoad");
		new JButton("RemoteCom HighPerf");

		programButtons = new JButton[] { btnUpload, btnGenerateLO, btnCheckElementaryService, btnGenerateL1, btnFindSCS,
				btnGenerateL2 };
		programPanel = new JPanel();
		programPanel.setLayout(new FlowLayout());
		final JPanel buttonPanel = new JPanel();
		dataPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setLayout(new GridLayout(11, 1));
		// buttonPanel.setLocation(150, 150);
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		buttonPanel.setPreferredSize(new Dimension(150, 250));
		for (JButton button : programButtons) {
			buttonPanel.add(button);
			button.addActionListener(this);
		}

		// Add the RemoteComputation button for High Load
		buttonPanel.add(btnRemoteComputation);
		btnRemoteComputation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final Frame f = new Frame("Select the data");// Create frame
																// objects
				f.setBounds(300, 100, 600, 500);// set frame location and size
				f.setLayout(new GridLayout(20, 1));// set the Grid Layout
				if (Flag) {
					for (int i = 0; i < AllData.size(); i++) {
						JCheckBox CB = new JCheckBox(AllData.get(i));
						programCheckBoxes.add(CB);
					}
					Flag = false;
				}
				for (JCheckBox ok : programCheckBoxes) {
					f.add(ok);
				}
				Button okBut = new Button("Submit");// Create button
				Button okBut2 = new Button("High Performance");
				f.add(okBut2);
				okBut2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						final Frame f2 = new Frame("Select the Service");
						f2.setBounds(300, 100, 600, 500);
						f2.setLayout(new GridLayout(20, 1));
						for (int i = 0; i < nodes.size(); i++) {
							JCheckBox CB = new JCheckBox(nodes.get(i).getService().name);
							programCheckBoxes2.add(CB);
						}
						for (JCheckBox ok : programCheckBoxes2) {
							f2.add(ok);
						}
						Button okBut3 = new Button("Submit");
						f2.add(okBut3);
						f2.setVisible(true);
						okBut3.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								for (JCheckBox CB : programCheckBoxes2) {
									boolean selected = CB.isSelected();
									if (selected) {
										DataStore2.add(CB.getText());
									}
									f2.setVisible(false);
									f.setVisible(false);
								}
								RemoteComputationHP(DataStore2);
							}
						});
					}
				});
				dataPanel.setPreferredSize(new Dimension(800, 350));
				dataPanel.setLayout(new BorderLayout());
				dataPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
				displayResult(tableData, columnNames);
				programPanel.add(buttonPanel);
				programPanel.add(dataPanel);
				add(programPanel);
				pack();
				f.add(okBut);// Add the button to the frame
				f.setVisible(true);// set visible
				okBut.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						for (JCheckBox CB : programCheckBoxes) {
							boolean selected = CB.isSelected();
							if (selected) {
								DataStore.add(CB.getText());
							}
							f.setVisible(false);
						}
						RemoteComputation(DataStore);
					}
				});
			}
		});
		buttonPanel.add(btnCheckProperty);
		btnCheckProperty.addActionListener(this);
		dataPanel.setPreferredSize(new Dimension(800, 350));
		dataPanel.setLayout(new BorderLayout());
		dataPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		displayResult(tableData, columnNames);
		programPanel.add(buttonPanel);
		programPanel.add(dataPanel);
		add(programPanel);
		pack();
	}

	// display results on the screen
	private void displayResult(Object[][] tableData, Object[] columnNames) {
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
			programPanel.updateUI();
			programPanel.revalidate();
		}
	}

	private void displayResultCheckProperty(JScrollPane JSP) {
		programPanel.add(JSP);
		programPanel.updateUI();
		programPanel.revalidate();
	}

	// event by clicking on buttons
	public void actionPerformed(ActionEvent e) 
	{
		String clickBtnText = e.getActionCommand();
		if (clickBtnText.equals(BUTTON_LABEL_UPLOAD)) 
		{
			fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(this);

			if (fileChooser.getSelectedFile() != null) 
			{
				setTitle("Evaluating " + fileChooser.getSelectedFile().getName() + " in "
						+ fileChooser.getCurrentDirectory().toString());

				hasFileBeenChoosed = true;

				// upload a file
				try 
				{
					inputFile = fileChooser.getSelectedFile();
					lstData = readFromJson(inputFile);
				} 
				
				catch (JsonIOException e1) 
				{
					System.out.println("error occured during reading of input file.");
					e1.printStackTrace();
				}
			}
		} else {
			if (hasFileBeenChoosed == false) {
				JOptionPane.showMessageDialog(this, "Please select an input file");
			} else {
				// generate level 0
				if (clickBtnText.equals(BUTTON_LABEL_GENERATE_LO)) {
					generateLevel0();
				} else if (clickBtnText.equals(BUTTON_LABEL_CHECK_ELE_SERVICE)) {
					checkElementaryService();
				} else if (e.getSource().equals(btnGenerateL1)) {
					generateLevel1();
				} else if (e.getSource().equals(btnFindSCS)) {
					identifyStronglyConnectedServices();
				} else if (e.getSource().equals(btnGenerateL2)) {
					generateLevel2();
				} else if (e.getSource().equals(btnCheckProperty)) {
					checkProperty();
				} else if (e.getSource().equals(BUTTON_LABEL_Remote_Computation)) {

				}
			}
		}
	}

	// read json file
	private List<Service> readFromJson(File file) 
	{
		Gson gson = new Gson();
		List<Service> lstOfService = null;
		try 
		{
			// convert the json string back to object
			lstOfService = gson.fromJson(new BufferedReader(new FileReader(file)), new TypeToken<List<Service>>() {
			}.getType());
			// System.out.println(obj);
		} 
		
		catch (IOException e) 
		{
			System.out.println("error occured during conversion of gson to service list");
			e.printStackTrace();
		}
		
		return lstOfService;
	}

	// display level 0 in the GUI
	private void generateLevel0() {
		columnNames = new Object[] { "name of service ", "input service", "output service", "name of variable",
				"input variable", "output variable" };
		tableData = new Object[lstData.size()][columnNames.length];
		int rowIndex = 0;
		for (Service eachService : lstData) {
			tableData[rowIndex][0] = eachService.getname();
			tableData[rowIndex][1] = eachService.getInputs();
			tableData[rowIndex][2] = eachService.getOutputs();
			ArrayList<String> Name = new ArrayList<String>();
			ArrayList<List<String>> Inputs = new ArrayList<List<String>>();
			ArrayList<List<String>> Outputs = new ArrayList<List<String>>();

			for (IOVariable var : eachService.getVariable()) {
				Name.add(var.GetName());
				Inputs.add(var.getInputs());
				Outputs.add(var.getOutputs());
			}
			tableData[rowIndex][3] = Name;
			tableData[rowIndex][4] = Inputs;
			tableData[rowIndex][5] = Outputs;

			rowIndex++;
			for (String k : eachService.getInputs()) {
				if (k != null) {
					AllData.add(k);
				}
			}
			AllData.addAll(eachService.getInputs());
			for (String m : eachService.getOutputs()) {
				if (m != null) {
					AllData.add(m);
				}
			}

			AllData.addAll(eachService.getInputs());
			removeDuplicateWithOrder(AllData);
			AllData.sort(null);
		}
		displayResult(tableData, columnNames);
	}

	public static void removeDuplicateWithOrder(ArrayList<String> list) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (java.util.Iterator<String> iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element)) {
				newList.add(element);
			}
		}
		list.clear();
		list.addAll(newList);
		// System.out.println( " remove duplicate " + list);
	}

	private void checkElementaryService() {
		// create column name for displaying in the grid
		columnNames = new Object[] { "ServiceID ", "Generated elementary services" };
		int rowIndex = 0;
		// create a two dimensional array to store display data
		tableData = new Object[20][lstData.size()];
		// ArrayList<ArrayList<String>> listOfLists = new
		// ArrayList<ArrayList<String>>();
		for (Service serviceObj : lstData) {
			if (serviceObj.getOutputs().size() == 1) {
				tableData[rowIndex][0] = serviceObj.name;
				tableData[rowIndex][1] = serviceObj.name;
				rowIndex++;
			} else {
				if (serviceObj.localVariable.size() == 0) {
					int intCount = 1;
					List<String> OutputMerge = new ArrayList<String>();
					OutputMerge = serviceObj.getOutputs();
					while (OutputMerge.size() != 0) {
						List<String> Output = new ArrayList<String>();
						Output.add(serviceObj.getOutputs().get(0));
						List<String> InputList = serviceObj.getIOstreamsByKey(Output.get(0));
						InputList = removeDuplicate3(InputList);
						OutputMerge.removeAll(Output);
						List<IOVariable> VariableList = serviceObj.localVariable;
						CreateSubService(serviceObj, intCount, InputList, Output, VariableList);
						Service A5 = CreateSubService(serviceObj, intCount, InputList, Output, VariableList);
						serviceObj.addElementaryServices(A5);
						tableData[rowIndex][0] = serviceObj.name;
						tableData[rowIndex][1] = A5.name;
						// System.out.println(A5.name+A5.getInputs()+A5.getOutputs());
						intCount++;
						rowIndex++;
					}
				} else if (serviceObj.localVariable.size() == 1) {
					int intCount = 1;
					List<String> OutputMerge = new ArrayList<String>();
					OutputMerge = serviceObj.getOutputs();
					while (OutputMerge.size() != 0) {
						List<String> CheckedOutput = new ArrayList<String>();
						CheckedOutput.clear();
						List<String> CheckedOutput2 = new ArrayList<String>();
						CheckedOutput2 = CheckType112(serviceObj, OutputMerge.get(0), CheckedOutput);

						List<String> InputList = new ArrayList<String>();
						for (int i = 0; i < CheckedOutput2.size(); i++) {
							InputList.addAll(serviceObj.getIOstreamsByKey(CheckedOutput2.get(i)));
						}
						InputList = removeDuplicate3(InputList);
						List<IOVariable> VariableMerges = new ArrayList<IOVariable>();
						for (int i = 0; i < CheckedOutput2.size(); i++) {
							VariableMerges.addAll(FindVariablesfromOut(serviceObj, OutputMerge.get(i)));
						}
						Service A5 = CreateSubService(serviceObj, intCount, InputList, CheckedOutput2, VariableMerges);
						serviceObj.addElementaryServices(A5);
						// System.out.println(A5.name+A5.getInputs()+A5.getOutputs()+A5.localVariable);
						intCount++;
						OutputMerge.removeAll(CheckedOutput2);
						tableData[rowIndex][0] = serviceObj.name;
						tableData[rowIndex][1] = A5.name;
						rowIndex++;
					}
				} else {
					int intCount = 1;
					List<String> OutputMerge = new ArrayList<String>();
					OutputMerge = serviceObj.getOutputs();
					while (OutputMerge.size() != 0) {
						List<String> CheckedOutput = new ArrayList<String>();
						CheckedOutput.clear();
						List<String> CheckedOutput2 = new ArrayList<String>();
						CheckedOutput2 = CheckType112(serviceObj, OutputMerge.get(0), CheckedOutput);

						List<String> InputList = new ArrayList<String>();
						for (int i = 0; i < CheckedOutput2.size(); i++) {
							InputList.addAll(serviceObj.getIOstreamsByKey(CheckedOutput2.get(i)));
						}
						InputList = removeDuplicate3(InputList);
						List<IOVariable> VariableMerges = new ArrayList<IOVariable>();
						for (int i = 0; i < CheckedOutput2.size(); i++) {
							VariableMerges.addAll(FindVariablesfromOut(serviceObj, OutputMerge.get(i)));
						}
						Service A5 = CreateSubService(serviceObj, intCount, InputList, CheckedOutput2, VariableMerges);
						serviceObj.addElementaryServices(A5);
						// System.out.println(A5.name+A5.getInputs()+A5.getOutputs()+A5.localVariable);
						intCount++;
						OutputMerge.removeAll(CheckedOutput2);
						tableData[rowIndex][0] = serviceObj.name;
						tableData[rowIndex][1] = A5.name;
						rowIndex++;
					}
				}
			}
			rowIndex++;
		}
		displayResult(tableData, columnNames);

	}

	public List<IOVariable> FindVariablesfromOut2(Service SER, List<String> Output) {
		List<IOVariable> Flag = new ArrayList<IOVariable>();
		List<IOVariable> VariblesMerge = new ArrayList<IOVariable>();
		VariblesMerge = SER.getVariable();
		for (int i = 0; i < VariblesMerge.size(); i++) {
			for (int j = 0; j < Output.size(); j++) {
				if (VariblesMerge.get(i).getOutputs().contains(Output.get(j))) {
					Flag.add(VariblesMerge.get(i));
				}
			}
		}
		return Flag;
	}

	public void removeDuplicate(List<String> OutputMerge2) {
		for (int i = 0; i < OutputMerge2.size(); i++) {
			for (int j = OutputMerge2.size() - 1; j > i; j--) {
				if (OutputMerge2.get(i) == OutputMerge2.get(j)) {
					OutputMerge2.remove(j);
				}
			}
		}
	}

	public List<String> removeDuplicate3(List<String> OutputMerge2) {
		for (int i = 0; i < OutputMerge2.size(); i++) {
			for (int j = OutputMerge2.size() - 1; j > i; j--) {
				if (OutputMerge2.get(i) == OutputMerge2.get(j)) {
					OutputMerge2.remove(j);
				}
			}
		}
		return OutputMerge2;
	}

	public void removeDuplicate2(List<List<String>> OutputMerge2) {
		OutputMerge2.sort(null);
		for (int i = 0; i < OutputMerge2.size(); i++) {
			for (int j = OutputMerge2.size() - 1; j > i; j--) {
				if (OutputMerge2.get(i) == OutputMerge2.get(j)) {
					OutputMerge2.remove(j);
				}
			}
		}
		// return OutputMerge2;
	}

	public boolean CheckContain(List<String> OutputMerge, List<String> CheckedOutput) {
		boolean Flag = true;
		if (OutputMerge == null) {
			return Flag;
		} else {
			for (int j = 0; j < OutputMerge.size(); j++) {
				Flag = CheckedOutput.contains(OutputMerge.get(j));
				if (!Flag) {
					Flag = false;
					break;
				}
			}
			return Flag;
		}
	}

	public List<IOVariable> FindVariablesfromOut(Service SER, String Output) {
		List<IOVariable> Flag = new ArrayList<IOVariable>();
		List<IOVariable> VariblesMerge = new ArrayList<IOVariable>();
		VariblesMerge = SER.getVariable();
		for (int i = 0; i < VariblesMerge.size(); i++) {
			if (VariblesMerge.get(i).getOutputs().contains(Output)) {
				Flag.add(VariblesMerge.get(i));
			}
		}
		return Flag;
	}

	public List<String> CheckType112(Service One, String Output1, List<String> CheckedOut) {
		List<String> CheckedOutNew = new ArrayList<String>();
		CheckedOutNew.addAll(CheckedOut);
		CheckedOutNew.add(Output1);

		List<IOVariable> VariableMerges = new ArrayList<IOVariable>();
		VariableMerges = FindVariablesfromOut(One, Output1);
		List<String> OutputMerge = new ArrayList<String>();
		for (int k = 0; k < VariableMerges.size(); k++) {
			OutputMerge.addAll(VariableMerges.get(k).getOutputs());
		}
		removeDuplicate(OutputMerge);

		boolean check = CheckContain(OutputMerge, CheckedOutNew);
		if (!check) {
			List<String> Diff = new ArrayList<String>();
			for (int i = 0; i < OutputMerge.size(); i++) {
				if (!CheckedOutNew.contains(OutputMerge.get(i))) {
					Diff.add(OutputMerge.get(i));
				}
			}
			for (int k = 0; k < Diff.size(); k++) {
				CheckedOutNew = CheckType112(One, Diff.get(k), CheckedOutNew);
			}
			return CheckedOutNew;
		} else {
			return CheckedOutNew;
		}
	}

	public Service CreateSubService(Service serviceObj, Object intCounter, List<String> inputs, List<String> outputs,
			List<IOVariable> Variable) {
		Service subService;
		subService = new Service();
		subService.setVariable(Variable);
		subService.setname(serviceObj.getname() + intCounter);
		subService.setInputs(inputs);
		subService.setOutputs(outputs);
		return subService;
		// serviceObj.AddOneElementaryServices(subService);
	}

	// generate level 1
	private void generateLevel1() {
		elementaryServices = new ArrayList<Service>();
		columnNames = new Object[] { "Main Service", "Sub Service", "inputs", "outputs", "name of variable",
				"inputs  variable", "outputs variable" };

		tableData = new Object[20][columnNames.length];
		int rowIndex = 0;
		int index = 0;
		// loop through each service
		for (Service eachService : lstData) {
			// check if service has elementary services
			if (eachService.getelementaryServices().size() > 0) {
				// loop through elementary services/ sub service
				for (Service subService : eachService.getelementaryServices()) {
					index = 0;
					// System.out.println("index: " + index + " rowindex " +
					// rowIndex + " each service: " + eachService.GetName());
					// System.out.println(" sub service: " +
					// subService.GetName());
					tableData[rowIndex][index++] = eachService.getname();
					tableData[rowIndex][index++] = subService.getname();
					tableData[rowIndex][index++] = subService.getInputs();
					tableData[rowIndex][index++] = subService.getOutputs();
					ArrayList<String> Name = new ArrayList<String>();
					ArrayList<String> Inputs = new ArrayList<String>();
					ArrayList<String> Outputs = new ArrayList<String>();
					if (subService.getVariable().size() > 0) {
						for (IOVariable IOV : subService.getVariable()) {
							Name.add(IOV.GetName());
						}
						Inputs = (ArrayList<String>) subService.getInputs();
						Outputs = (ArrayList<String>) subService.getOutputs();
					}
					tableData[rowIndex][index++] = Name;
					tableData[rowIndex][index++] = Inputs;
					tableData[rowIndex][index++] = Outputs;
					elementaryServices.add(subService);
					rowIndex++;
				}
			} else // has no elementary service
			{
				index = 0;
				// System.out.println("index: " + index + " rowindex " +
				// rowIndex + " each service: " + eachService.GetName());
				tableData[rowIndex][index++] = eachService.getname();
				tableData[rowIndex][index++] = "No Sub Service";
				tableData[rowIndex][index++] = eachService.getInputs();
				tableData[rowIndex][index++] = eachService.getOutputs();
				tableData[rowIndex][index++] = "-";
				tableData[rowIndex][index++] = "-";
				tableData[rowIndex][index++] = "-";
				rowIndex++;
			}
		}
		displayResult(tableData, columnNames);
	}

	// identify the strong connected services
	private void identifyStronglyConnectedServices() {
		columnNames = new Object[] { "Service", "Type of node", "Predecessors", "Successors", "Has System inputs",
				"Has System outputs" };
		tableData = new Object[20][columnNames.length];
		int index, rowIndex = 0;
		nodes = new ArrayList<ServiceNode>();
		// Creates the initial list of service nodes
		for (Service elem : elementaryServices) {
			ServiceNode node = new ServiceNode(elem);
			nodes.add(node);
		}

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
		displayResult(tableData, columnNames);
	}

	// generate level 2
	private int tarjanIndex;
	ArrayDeque<ServiceNode> tarjanStack;
	ArrayList<StronglyConnectedService> scsList;

	private void generateLevel2() {
		scsList = new ArrayList<StronglyConnectedService>();
		tarjanIndex = 0;
		// S := empty
		tarjanStack = new ArrayDeque<ServiceNode>();

		// for each v in V do
		// if (v.index is undefined) then
		// strongconnect(v)
		// end if
		// end for

		for (ServiceNode v : nodes) {
			if (v.getTarjanIndex() == -1) {
				strongConnect(v);
			}
		}

		columnNames = new Object[] { "SCS", "Sub Service", "Type of node", "Predecessor", "Successor" };
		tableData = new Object[20][columnNames.length];
		int index, rowIndex = 0;
		// Build table of results
		// loop through each scc
		for (StronglyConnectedService scs : scsList) {

			// System.out.println("index: " + index + " rowindex " + rowIndex +
			// " each service: " + eachService.GetName());
			// System.out.println(" sub service: " + subService.GetName());
			for (ServiceNode sn : scs.getServiceNodes()) {
				index = 0;
				tableData[rowIndex][index++] = "S" + scs.getIndex();
				tableData[rowIndex][index++] = sn.getName().toString();
				tableData[rowIndex][index++] = sn.getNodeType().toString();
				tableData[rowIndex][index++] = sn.getPredecessorsString();
				tableData[rowIndex][index++] = sn.getSuccessorsString();
				rowIndex++;
			}

		}
		displayResult(tableData, columnNames);
	}

	// function strongconnect(v)
	// // Set the depth index for v to the smallest unused index
	// v.index := index
	// v.lowlink := index
	// index := index + 1
	// S.push(v)
	// v.onStack := true

	void strongConnect(ServiceNode v) {
		v.setTarjanIndex(tarjanIndex);
		v.setTarjanLowLink(tarjanIndex);
		tarjanIndex++;
		tarjanStack.push(v);

		// // Consider successors of v
		// for each (v, w) in E do
		// if (w.index is undefined) then
		// // Successor w has not yet been visited; recurse on it
		// strongconnect(w)
		// v.lowlink := min(v.lowlink, w.lowlink)
		// else if (w.onStack) then
		// // Successor w is in stack S and hence in the current SCC
		// v.lowlink := min(v.lowlink, w.index)
		// end if
		// end for

		for (ServiceNode w : v.getSuccessors()) {
			if (w.getTarjanIndex() == -1) {
				// Successor w has not yet been visited; recurse on it
				strongConnect(w);
				v.setTarjanLowLink(Math.min(v.getTarjanLowLink(), w.getTarjanLowLink()));
			} else if (tarjanStack.contains(w)) {
				// Successor w is in stack S and hence in the current SCC
				v.setTarjanLowLink(Math.min(v.getTarjanLowLink(), w.getTarjanIndex()));
			}
		}

		// If v is a root node, pop the stack and generate an SCC
		// if (v.lowlink = v.index) then
		// start a new strongly connected component
		// repeat
		// w := S.pop()
		// w.onStack := false
		// add w to current strongly connected component
		// until (w = v)
		// output the current strongly connected component
		// end if

		// If v is a root node, pop the stack and generate an SCC
		if (v.getTarjanLowLink() == v.getTarjanIndex()) {
			StronglyConnectedService scc = new StronglyConnectedService(scsList.size() + 1);
			ServiceNode w;
			do {
				w = tarjanStack.pop();
				scc.add(w);
			} while (w != v);

			scsList.add(scc);
		}

		// end function
	}

	// checking properties
	private void checkProperty() {
		ArrayList<String> allLevel2Data = new ArrayList<String>();
		ArrayList<String> systemInputs = new ArrayList<String>();
		ArrayList<String> systemOutputs = new ArrayList<String>();
		final ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.clear();
		ArrayList<String> allOutputs = new ArrayList<String>();
		// Populate the input and output lists

		// Go through every strongly connected service
		for (StronglyConnectedService scs : scsList) {
			// Go through every elementary service node within that service
			for (ServiceNode sn : scs.getServiceNodes()) {
				// Grab all of the system inputs from each elementary service
				for (String input : sn.getSystemInputs()) {
					// Add the system input to our master list, unless it is
					// already there
					if (!systemInputs.contains(input)) {
						systemInputs.add(input);
					}
				}

				// Grab all of the system outputs from each elementary service
				for (String output : sn.getSystemOutputs()) {
					// Add the system outputs to our master list, unless it is
					// already there
					if (!systemOutputs.contains(output)) {
						systemOutputs.add(output);
					}
				}
			}

			// Grab all of the data io from each elementary service
			for (String data : scs.getDataFlows()) {
				// Add the system outputs to our master list, unless it is
				// already there
				if (!allLevel2Data.contains(data)) {
					allLevel2Data.add(data);
				}
			}
		}

		// Filter the data. System inputs and outputs do not go in both lists
		for (String data : allLevel2Data) {
			if (!systemInputs.contains(data)) {
				allOutputs.add(data);
			}
			if (!systemOutputs.contains(data)) {
				allInputs.add(data);
			}
		}

		JPanel bigPanel = new JPanel();
		bigPanel.setLayout(new GridLayout(0, 2));

		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();

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

		// dataPanel.add(inputLabel);

		// Create a checkbox for each input
		for (String input : allInputs) {
			JCheckBox cb = new JCheckBox(input);
			inputCheckBoxes.add(cb);
			// dataPanel.add(cb);

			leftPanel.add(cb);
		}

		JLabel outputLabel = new JLabel("Select Outputs");
		// dataPanel.add(outputLabel);

		rightPanel.add(outputLabel);

		// Create a checkbox for each output
		for (String output : allOutputs) {
			JCheckBox cb = new JCheckBox(output);
			outputCheckBoxes.add(cb);
			// dataPanel.add(cb);
			rightPanel.add(cb);
		}

		bigPanel.add(leftPanel);
		bigPanel.add(rightPanel);
		dataPanel.add(bigPanel, BorderLayout.NORTH);

		JPanel bottomPanel = new JPanel();

		// Evaluate button initiates calculations
		Button evaluateButton = new Button("Evaluate");
		// dataPanel.add(evaluateButton);
		bottomPanel.add(evaluateButton);

		// Label to show the results
		final JLabel resultLabel = new JLabel();
		// dataPanel.add(resultLabel);
		resultLabel.setText("");
		bottomPanel.add(resultLabel);

		dataPanel.add(bottomPanel, BorderLayout.SOUTH);
		// An action listener to respond to the user pressing the evaluate
		// button
		ActionListener evaluateButtonPressedHandler = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Lists of the selected inputs and outputs stored here
				ArrayList<String> selectedInputs = new ArrayList<String>();
				ArrayList<String> selectedOutputs = new ArrayList<String>();

				// Reads the currently selected inputs and outputs from the
				// check boxes
				for (JCheckBox cb : inputCheckBoxes) {
					if (cb.isSelected()) {
						selectedInputs.add(cb.getText());
					}
				}
				for (JCheckBox cb : outputCheckBoxes) {
					if (cb.isSelected()) {
						selectedOutputs.add(cb.getText());
					}
				}

				// Find all 'sources' (SCS) that feed the outputs
				ArrayList<StronglyConnectedService> sources = findSources(selectedOutputs);

				// Find all inputs that feed the sources
				ArrayList<String> requiredInputs = findRequiredInputs(sources, allInputs);

				// Lists to store extra and missing inputs
				ArrayList<String> extraInputs = new ArrayList<String>();
				ArrayList<String> missingInputs = new ArrayList<String>();

				// Extra inputs are ones that are selected but not required
				for (String data : selectedInputs) {
					if (!requiredInputs.contains(data)) {
						extraInputs.add(data);
					}
				}

				// Missing inputs are ones that are required but not selected
				for (String data : requiredInputs) {
					if (!selectedInputs.contains(data)) {
						missingInputs.add(data);
					}
				}

				String resultText = "<html>Result: ";

				// First scenario: Exact match of selected and required inputs
				if (extraInputs.size() == 0 && missingInputs.size() == 0) {
					resultText += "Selected inputs and required inputs exactly match.<br>";
				} else // There are unmatched inputs, display them
				{
					// First display extra inputs if there are any
					if (extraInputs.size() > 0) {
						// Display name of each input
						for (String extraInput : extraInputs) {
							resultText += extraInput + ", ";
						}

						// Display informative text with correct grammar
						resultText += (extraInputs.size() == 1 ? "is" : "are") + " not required<br>";
					}

					// Then display missing inputs if there are any
					if (missingInputs.size() > 0) {
						// Display name of each input
						for (String missingInput : missingInputs) {
							resultText += missingInput + ", ";
						}
						// Display informative text
						resultText += "should be in the set of inputs<br>";
					}
				}

				// Finally, display the required components by iterating through
				// the sources
				resultText += "Required Components: ";
				for (StronglyConnectedService scs : sources) {
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
		displayResultCheckProperty(jsp);
	}

	// Returns a list of all inputs that are required for a list of scs
	protected ArrayList<String> findRequiredInputs(ArrayList<StronglyConnectedService> sources,
			ArrayList<String> allInputs) {
		ArrayList<String> requiredInputs = new ArrayList<String>();

		// Go through each SCS and find the inputs that feed it
		for (StronglyConnectedService scs : sources) {
			for (String input : allInputs) {
				// Check if it both feeds the SCS and is not a duplicate
				if (canReachInput(scs, input) && !requiredInputs.contains(input)) {
					requiredInputs.add(input);
				}
			}
		}
		return requiredInputs;
	}

	// Returns a list of all SCS that feed the outputs given
	private ArrayList<StronglyConnectedService> findSources(ArrayList<String> outputs) {
		// sources list to store the end result
		ArrayList<StronglyConnectedService> sources = new ArrayList<StronglyConnectedService>();
		for (String output : outputs) {
			// Check every SCS we know of
			for (StronglyConnectedService scs : scsList) {
				// If this SCS can reach the output, it is a 'source' (also
				// check duplicates)
				if (canReachOutput(scs, output) && !sources.contains(scs)) {
					sources.add(scs);
				}
			}
		}
		return sources;
	}

	// Checks if a SCS has a link to a particular data output
	private boolean canReachOutput(StronglyConnectedService scs, String output) {
		// Setup an empty list of dependents (A strongly connected service that
		// depends (directly, or indirectly) on 'scs')
		ArrayList<StronglyConnectedService> dependents = new ArrayList<StronglyConnectedService>();

		// Populate the dependents list with the actual dependents
		dependentSearch(scs, dependents);

		// Check every dependent service
		for (StronglyConnectedService service : dependents) {
			// If the service has an output that matches our final desintation
			// output
			if (service.hasOutput(output)) {
				// then this service can reach the output, therefore the scs can
				// reach the output
				return true;
			}
		}

		// Could not find any dependent service that was able to reach the
		// output
		return false;
	}

	// Checks if a SCS has a link to a particular data input
	private boolean canReachInput(StronglyConnectedService scs, String input) {
		// Setup empty list of dependencies
		ArrayList<StronglyConnectedService> dependencies = new ArrayList<StronglyConnectedService>();

		// Populate dependencies list using the dependency search. Finds both
		// direct and indirect dependencies.
		dependencySearch(scs, dependencies);

		// Check every dependency
		for (StronglyConnectedService service : dependencies) {
			// If the dependency has the input we're looking for
			if (service.hasInput(input)) {
				// then this dependency can reach the input, therefore our scs
				// can reach the input
				return true;
			}
		}
		return false;
	}

	// Depth first search to find all dependencies of a scs, storing result in
	// 'visited'
	private void dependencySearch(StronglyConnectedService scs, ArrayList<StronglyConnectedService> visited) {
		// Add the current scs to the visited list
		visited.add(scs);

		// Check all immediate dependencies of the scs
		for (StronglyConnectedService dependency : getImmediateDependencies(scs)) {
			// If we have not searched it yet, then search it now
			if (!visited.contains(dependency)) {
				dependencySearch(dependency, visited);
			}
		}
	}

	// Depth first search to find all dependents of a scs, storing result in
	// 'visited'
	private void dependentSearch(StronglyConnectedService scs, ArrayList<StronglyConnectedService> visited) {
		// add the current scs to the visited list
		visited.add(scs);

		// Check all immediate dependents of the scs
		for (StronglyConnectedService dependent : getImmediateDependents(scs)) {
			// If we have not searched it yet, then search it now
			if (!visited.contains(dependent)) {
				dependentSearch(dependent, visited);
			}
		}
	}

	// Finds the SCS that are immediate dependencies of the target
	public ArrayList<StronglyConnectedService> getImmediateDependencies(StronglyConnectedService target) {
		// Start with empty list
		ArrayList<StronglyConnectedService> dependencies = new ArrayList<StronglyConnectedService>();

		// Find all inputs to the target scs
		ArrayList<String> allInputsToTarget = target.getAllInputs();

		// Check every source in the master list
		for (StronglyConnectedService source : scsList) {
			// Check every input to the target scs
			for (String input : allInputsToTarget) {
				// If the source has an output that matches one of the inputs to
				// the target (and we haven't already recorded it)
				if (source.hasOutput(input) && !dependencies.contains(source)) {
					// Record it
					dependencies.add(source);
				}
			}
		}
		return dependencies;
	}

	// Finds the SCS that are immediate dependents of the target
	public ArrayList<StronglyConnectedService> getImmediateDependents(StronglyConnectedService target) {
		// Start with empty list
		ArrayList<StronglyConnectedService> dependents = new ArrayList<StronglyConnectedService>();
		// Find all outputs from the target scs
		ArrayList<String> allOutputsFromTarget = target.getAllOutputs();
		// Check every source in the master list
		for (StronglyConnectedService source : scsList) {
			// Check every output of the target scs
			for (String output : allOutputsFromTarget) {
				// If the source has an input that matches one of the outputs to
				// the target (and we haven't already recorded it)
				if (source.hasInput(output) && !dependents.contains(source)) {
					// record it
					dependents.add(source);
				}
			}

		}
		return dependents;
	}

	// Remote Computation
	private void RemoteComputation(ArrayList<String> AL) {
		ArrayList<StronglyConnectedService> TotalOringinal = new ArrayList<StronglyConnectedService>();
		for (StronglyConnectedService scs : scsList) {
			TotalOringinal.add(scs);
		}
		// Create a ArrayList to Store Service List for High Load Input
		ArrayList<StronglyConnectedService>[] L2HLI = new ArrayList[AL.size()];
		// Create a ArrayList to Store Service List for High Load Output
		ArrayList<StronglyConnectedService>[] L2HLO = new ArrayList[AL.size()];
		ArrayList<ArrayList<StronglyConnectedService>> L2HE = new ArrayList<ArrayList<StronglyConnectedService>>();
		ArrayList<StronglyConnectedService> Total = new ArrayList<StronglyConnectedService>();
		ArrayList<StronglyConnectedService> Combine = new ArrayList<StronglyConnectedService>();
		for (int i = 0; i < AL.size(); i++) {
			L2HLI[i] = getL2HLI(AL.get(i));
			L2HLO[i] = getL2HLO(AL.get(i));
			ArrayList<StronglyConnectedService> DataMerge = new ArrayList<StronglyConnectedService>();
			DataMerge.addAll(L2HLI[i]);
			DataMerge.addAll(L2HLO[i]);
			L2HE.add(DataMerge);
		}

		ArrayList<StronglyConnectedService> Mark1 = null;
		ArrayList<StronglyConnectedService> Mark2 = null;
		// Remove the empty element of the Array
		for (int i = 0; i < L2HE.size(); i++) {
			for (int j = L2HE.size() - 1; j > i; j--) {
				if ((L2HE.get(i).isEmpty())) {
					L2HE.remove(i);
				}
				if ((L2HE.get(j).isEmpty())) {
					L2HE.remove(j);
				}
			}
		}

		// Combine the two elements and mark the Two should be deleted
		for (int i = 0; i < L2HE.size(); i++) {
			for (int j = L2HE.size() - 1; j > i; j--) {
				if (CheckContain2(L2HE.get(i), L2HE.get(j))) {
					Combine = Combine(L2HE.get(i), L2HE.get(j));
					RSList.add(Combine);
					Mark1 = L2HE.get(i);
					Mark2 = L2HE.get(j);
				}
			}
		}

		L2HE.remove(Mark1);
		L2HE.remove(Mark2);
		RSList.addAll(L2HE);

		for (ArrayList<StronglyConnectedService> RSE : RefineData(RSList)) {
			Total.addAll(RSE);
		}

		ArrayList<StronglyConnectedService> Diff = new ArrayList<StronglyConnectedService>();

		TotalOringinal.removeAll(Total);
		for (StronglyConnectedService SCS1 : Total) {
			if (TotalOringinal.contains(SCS1)) {
				TotalOringinal.remove(SCS1);
			}
		}
		Diff = TotalOringinal;

		RSList.add(Diff);

		columnNames = new Object[] { "Service Name", "High Load Component", "Group name", "InputData", "OutputData" };
		tableData = new Object[20][columnNames.length];
		int index, rowIndex = 0;
		int index2 = 1;

		for (ArrayList<StronglyConnectedService> SCSList : RSList) {
			for (StronglyConnectedService SCS : SCSList) {
				index = 0;
				tableData[rowIndex][index++] = "S" + SCS.getIndex();
				tableData[rowIndex][index++] = "YES";
				tableData[rowIndex][index++] = "RS" + index2;
				tableData[rowIndex][index++] = SCS.getAllInputs();
				tableData[rowIndex][index++] = SCS.getAllOutputs();
				rowIndex++;
			}
			index2++;
		}
		displayResult(tableData, columnNames);
	}

	// Remove duplicate elements in one Array List
	private ArrayList<ArrayList<StronglyConnectedService>> RefineData(
			ArrayList<ArrayList<StronglyConnectedService>> DataList) {
		ArrayList<ArrayList<StronglyConnectedService>> DataMerge = new ArrayList<ArrayList<StronglyConnectedService>>();
		for (int i = 0; i < DataList.size(); i++) {
			for (int j = DataList.size() - 1; j > i; j--) {
				if ((DataList.get(i) == DataList.get(j))) {
					DataList.remove(j);
				} else if (DataList.get(i).containsAll(DataList.get(j))) {
					DataList.remove(j);
				} else if (DataList.get(i).size() == 0) {
					DataList.remove(i);
				}
			}
		}
		DataMerge = DataList;
		return DataMerge;
	}

	// remove the duplicate elements of SCS
	public void removeDuplicateSCS(ArrayList<StronglyConnectedService> Combine) {
		// Combine.sort(null);
		for (int i = 0; i < Combine.size(); i++) {
			for (int j = Combine.size() - 1; j > i; j--) {
				if (Combine.get(i) == Combine.get(j)) {
					Combine.remove(j);
				}
			}
		}
		// return OutputMerge2;
	}

	// Check if an array list of scs contain a element of another array list
	private boolean CheckContain2(ArrayList<StronglyConnectedService> A1, ArrayList<StronglyConnectedService> A2) {
		for (StronglyConnectedService E : A2) {
			if (A1.contains(E)) {
				return true;
			}
		}
		return false;
	}

	// Combine two arrays and remove the duplication.
	private ArrayList<StronglyConnectedService> Combine(ArrayList<StronglyConnectedService> A1,
			ArrayList<StronglyConnectedService> A2) {
		ArrayList<StronglyConnectedService> combine = new ArrayList<StronglyConnectedService>();
		combine.addAll(A1);
		combine.addAll(A2);
		removeDuplicateSCS(combine);
		return combine;
	}

	// Get the Level2 High Load Inputs
	public ArrayList<StronglyConnectedService> getL2HLI(String ALElementary) {
		ArrayList<StronglyConnectedService> ALOut = new ArrayList<StronglyConnectedService>();
		for (StronglyConnectedService scs : scsList) {
			if ((scs.getAllInputs().contains(ALElementary))) {
				if (!CheckInternal(scs, ALElementary)) {
					ALOut.add(scs);
				}
			}
		}
		return ALOut;
	}

	// Get the Level2 High Load Outputs
	public ArrayList<StronglyConnectedService> getL2HLO(String ALElementary) {
		ArrayList<StronglyConnectedService> ALOut = new ArrayList<StronglyConnectedService>();
		for (StronglyConnectedService scs : scsList) {
			if ((scs.getAllOutputs().contains(ALElementary))) {
				if (!CheckInternal(scs, ALElementary)) {
					ALOut.add(scs);
				}
			}
		}
		return ALOut;
	}

	// Check if the StronglyConnectedService contain the Data
	public boolean CheckInternal(StronglyConnectedService SCS, String Data) {
		ArrayList<ServiceNode> SCSN = new ArrayList<ServiceNode>();
		SCSN = SCS.getServiceNodes();
		for (int i = 0; i < SCSN.size(); i++) {
			for (int j = SCSN.size() - 1; j > i; j--) {
				if ((SCSN.get(i).service.outputs.contains(Data) && (SCSN.get(j).service.inputs.contains(Data)))) {
					return true;
				} else if ((SCSN.get(i).service.inputs.contains(Data)
						&& (SCSN.get(j).service.outputs.contains(Data)))) {
					return true;
				}
			}
		}
		return false;
	}

	// High performance check
	private void RemoteComputationHP(ArrayList<String> AL) {
		ArrayList<StronglyConnectedService> SCS = new ArrayList<StronglyConnectedService>();
		ArrayList<ServiceNode> SNS = new ArrayList<ServiceNode>();
		for (StronglyConnectedService source : scsList) {
			SNS = source.getServiceNodes();
			for (ServiceNode SN : SNS) {
				for (String Ser : AL) {
					if (SN.getName().equals(Ser)) {
						SCS.add(source);
					}
				}
			}
		}
		for (StronglyConnectedService source2 : SCS) {
			for (ArrayList<StronglyConnectedService> Al : RSList) {
				if (Al.contains(source2)) {
					RSList2.add(Al);
				}
			}
		}
		columnNames = new Object[] { "The name of HighPerf Component ", "StronglyConnectedService(L2)",
				"RemoteComp(L3)" };
		tableData = new Object[lstData.size()][columnNames.length];
		int index, rowIndex = 0;
		for (String HPC : AL) {
			index = 0;
			tableData[rowIndex][index++] = HPC;
			tableData[rowIndex][index++] = "S" + FindSCSbyString(HPC);
			tableData[rowIndex][index++] = "RS" + (FindRSbyString(FindSCSbyString(HPC)) + 1);
			rowIndex++;
		}
		displayResult(tableData, columnNames);
	}

	// get the SCS index by the String
	public int FindSCSbyString(String AL1) {
		int k = 0;
		for (ArrayList<StronglyConnectedService> SCSList : RSList2) {
			for (StronglyConnectedService SCS2 : SCSList) {
				for (ServiceNode SN : SCS2.getServiceNodes()) {
					if (SN.getService().name.equals(AL1)) {
						k = SCS2.index;
					}
				}
			}
		}
		return k;
	}

	// get the RS index by the String
	public int FindRSbyString(int SCS) {
		int j = 0;
		for (ArrayList<StronglyConnectedService> SCSList : RSList) {
			for (StronglyConnectedService scs : SCSList) {
				if (scs.getIndex() == SCS) {
					j = RSList.indexOf(SCSList);
				}
			}
		}
		return j;
	}

	// write to file
	// private void getJson()
	// {
	// Gson gson = new Gson();
	//
	// // convert java object to JSON format,
	// // and returned as JSON formatted string
	// String json = gson.toJson(lstData);
	//
	// try {
	// //write converted json data to a file named "file.json"
	// FileWriter writer = new FileWriter("/file.json");
	// writer.write(json);
	// writer.close();
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// System.out.println(json);
	// }
}
