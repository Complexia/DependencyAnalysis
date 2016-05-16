package controller;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

import factory.Factory;
import model.ServiceNode;
import model.StronglyConnectedService;



public class RemoteComHighLoadListener implements ActionListener{
	
	private ArrayList<String> AllData = new ArrayList<String>();
	private  boolean Flag = true;
	private ArrayList<JCheckBox> programCheckBoxes = new ArrayList<JCheckBox>();
	private ArrayList<JCheckBox> programCheckBoxes2 = new ArrayList<JCheckBox>();
	private ArrayList<ServiceNode> nodes;
	public ArrayList<String> DataStore = new ArrayList<String>();
	public ArrayList<String> DataStore2 = new ArrayList<String>();
	private Object[][] tableData;
	private Object[] columnNames;
	ArrayList<ArrayList<StronglyConnectedService>> RSList2 = new ArrayList<ArrayList<StronglyConnectedService>>();
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		nodes = Factory.getNodes();
		
		final JFrame f = new JFrame("Select the data");// Create frame
		
		// objects
		f.setBounds(300, 100, 600, 500);// set frame location and size
		f.setLayout(new GridLayout(20, 1));// set the Grid Layout
		f.setDefaultCloseOperation(1);
		if (Flag) // TODO: fix up whatever the reason this kluge is here
					// for.
		{
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
		
		Factory.displayResult(tableData, columnNames);
		
		
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
		tableData = new Object[Factory.getLstData().size()][columnNames.length];
		int index, rowIndex = 0;
		for (String HPC : AL) {
			index = 0;
			tableData[rowIndex][index++] = HPC;
			tableData[rowIndex][index++] = "S" + FindSCSbyString(HPC);
			tableData[rowIndex][index++] = "RS" + (FindRSbyString(FindSCSbyString(HPC)) + 1);
			rowIndex++;
		}
		Factory.displayResult(tableData, columnNames);
	}
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
	ArrayList<StronglyConnectedService> scsList;
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
	private boolean CheckContain2(ArrayList<StronglyConnectedService> A1, ArrayList<StronglyConnectedService> A2) {
		for (StronglyConnectedService E : A2) {
			if (A1.contains(E)) {
				return true;
			}
		}
		return false;
	}
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
	
	private ArrayList<StronglyConnectedService> Combine(ArrayList<StronglyConnectedService> A1,
			ArrayList<StronglyConnectedService> A2) {
		ArrayList<StronglyConnectedService> combine = new ArrayList<StronglyConnectedService>();
		combine.addAll(A1);
		combine.addAll(A2);
		removeDuplicateSCS(combine);
		return combine;
	}
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
	
	ArrayList<ArrayList<StronglyConnectedService>> RSList = new ArrayList<ArrayList<StronglyConnectedService>>();
	private void RemoteComputation(ArrayList<String> AL) {
		ArrayList<StronglyConnectedService> TotalOringinal = new ArrayList<StronglyConnectedService>();
		
		scsList = new ArrayList<StronglyConnectedService>();
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
		Factory.displayResult(tableData, columnNames);
	}

}
