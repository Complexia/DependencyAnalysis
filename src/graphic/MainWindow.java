package graphic;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import controller.CheckElementaryServicesListener;
import controller.GenerateL0Listener;
import controller.GenerateL1Listener;
import controller.GenerateL2Listener;
import controller.StronglyConnectedServicesListener;
import controller.UploadFileListener;
import factory.Factory;

public class MainWindow extends JFrame{
	
	
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

	// Create GUI buttons
	private JButton btnUpload;
	private JButton btnGenerateL0;
	private JButton btnCheckElementaryService;
	private JButton btnGenerateL1;
	private JButton btnFindSCS;
	private JButton btnGenerateL2;
	private JButton btnCheckProperty;
	private JButton btnRemoteComputation;
	// This Flag is used for not add Check boxes to dataPanel twice
	boolean Flag = true;
	
	public ArrayList<String> AllData = new ArrayList<String>();
	public ArrayList<JCheckBox> programCheckBoxes = new ArrayList<JCheckBox>();
	public ArrayList<JCheckBox> programCheckBoxes2 = new ArrayList<JCheckBox>();
	public ArrayList<String> DataStore = new ArrayList<String>();
	public ArrayList<String> DataStore2 = new ArrayList<String>();
	
	private JButton programButtons[];
	private JPanel programPanel;
	private JPanel dataPanel;
	private Object[][] tableData;
	private Object[] columnNames;
	
	public MainWindow(){
		
		
		setBounds(50,50,1000,400);
		setVisible(true);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		btnUpload = new JButton(BUTTON_LABEL_UPLOAD);
		btnUpload.addActionListener(new UploadFileListener());
		
		btnGenerateL0 = new JButton(BUTTON_LABEL_GENERATE_LO);
		btnGenerateL0.addActionListener(new GenerateL0Listener());
		
		btnCheckElementaryService = new JButton(BUTTON_LABEL_CHECK_ELE_SERVICE);
		btnCheckElementaryService.addActionListener(new CheckElementaryServicesListener());
		
		
		btnGenerateL1 = new JButton(BUTTON_LABEL_GENERATE_L1);
		btnGenerateL1.addActionListener(new GenerateL1Listener());
		
		btnFindSCS = new JButton(BUTTON_LABEL_FIND_SCS);
		btnFindSCS.addActionListener(new StronglyConnectedServicesListener());
		
		btnGenerateL2 = new JButton(BUTTON_LABEL_GENERATE_L2);
		btnGenerateL2.addActionListener(new GenerateL2Listener());
		
		btnCheckProperty = new JButton(BUTTON_LABEL_CHECK_PROPERTY);
		
		btnRemoteComputation = new JButton(BUTTON_LABEL_Remote_Computation);

		
		// = new JButton("Find SCS");
		
		btnCheckProperty = new JButton("Check property");
		btnRemoteComputation = new JButton("RemoteCom HighLoad");
		new JButton("RemoteCom HighPerf");

		programButtons = new JButton[] { btnUpload, btnGenerateL0, btnCheckElementaryService, btnGenerateL1, btnFindSCS,
				btnGenerateL2 };
		programPanel = new JPanel();
		programPanel.setLayout(new FlowLayout());
		final JPanel buttonPanel = new JPanel();
		dataPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setLayout(new GridLayout(11, 1));

		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
			defaults.put("Table.alternateRowColor", new Color(240, 240, 240));

	    buttonPanel.setLocation(150, 150);
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		buttonPanel.setPreferredSize(new Dimension(150, 250));
		
		
		for (JButton button : programButtons) {
			buttonPanel.add(button);
			
		}

		// Add the RemoteComputation button for High Load
		buttonPanel.add(btnRemoteComputation);
//		
				dataPanel.setPreferredSize(new Dimension(800, 350));
				dataPanel.setLayout(new BorderLayout());
				dataPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
				Factory.displayResult(tableData, columnNames);
				programPanel.add(buttonPanel);
				programPanel.add(dataPanel);
				add(programPanel);
				pack();
//				
		// Create and format GUI border
		buttonPanel.add(btnCheckProperty);
		
		dataPanel.setPreferredSize(new Dimension(800, 350));
		dataPanel.setLayout(new BorderLayout());
		dataPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		Factory.displayResult(tableData, columnNames);
		programPanel.add(buttonPanel);
		programPanel.add(dataPanel);
		add(programPanel);
		Factory.setProgramPanel(programPanel);
		Factory.setDataPanel(dataPanel);
		pack();
		
	}

		}
