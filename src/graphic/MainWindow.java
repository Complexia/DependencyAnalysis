package graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import controller.CheckElemAndGenerateL1Listener;
import controller.CheckElementaryServicesListener;
import controller.FindSCSandGenerateL2Listener;
import controller.GenerateL0Listener;
import controller.GenerateL2Listener;
import controller.RemoteComHighLoadListener;
import controller.UploadAndGenerateListener;
import factory.Factory;

/**
 * The main body of the GUI, it holds all the elements and functions as the root component.
 * MainWindow uses the javax.swing library for all of its graphics with the exception of a few legacy components.
 *
 */
public class MainWindow extends JFrame {

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

	/**
	 * Default constructor, defines the standard GUI layout
	 */
	public MainWindow() {

		setBounds(50, 50, 1000, 400);
		setVisible(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		btnUpload = new JButton(BUTTON_LABEL_UPLOAD);
		btnUpload.addActionListener(new UploadAndGenerateListener());

//		btnGenerateL0 = new JButton(BUTTON_LABEL_GENERATE_LO);
//		btnGenerateL0.addActionListener(new GenerateL0Listener());

//		btnCheckElementaryService = new JButton(BUTTON_LABEL_CHECK_ELE_SERVICE);
//		btnCheckElementaryService.addActionListener(new CheckElementaryServicesListener());

		btnGenerateL1 = new JButton(BUTTON_LABEL_GENERATE_L1);
		btnGenerateL1.addActionListener(new CheckElemAndGenerateL1Listener());

//		btnFindSCS = new JButton(BUTTON_LABEL_FIND_SCS);
//		btnFindSCS.addActionListener(new StronglyConnectedServicesListener());

		btnGenerateL2 = new JButton(BUTTON_LABEL_GENERATE_L2);
		btnGenerateL2.addActionListener(new FindSCSandGenerateL2Listener());

		btnCheckProperty = new JButton(BUTTON_LABEL_CHECK_PROPERTY);

		btnRemoteComputation = new JButton(BUTTON_LABEL_Remote_Computation);
		btnRemoteComputation.addActionListener(new RemoteComHighLoadListener());

		// = new JButton("Find SCS");

		btnCheckProperty = new JButton("Check property");
		//btnRemoteComputation = new JButton("RemoteCom HighLoad");
		new JButton("RemoteCom HighPerf");

		// JPanel northPanel = new JPanel(new GridLayout(2, 1));
		// buttonPanel.add(northPanel, BorderLayout.NORTH);

		programButtons = new JButton[] { btnUpload,  btnGenerateL1, 
				btnGenerateL2 };
		programPanel = new JPanel();
		programPanel.setLayout(new BorderLayout());
		final JToolBar buttonPanel = new JToolBar();
		buttonPanel.setFloatable(false);
		dataPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setLayout(new GridLayout(1, 11));
		// add(buttonPanel, BorderLayout.NORTH);
		// add(dataPanel, BorderLayout.SOUTH);

		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
			defaults.put("Table.alternateRowColor", new Color(240, 240, 240));

		//buttonPanel.setLocation(5, 5);
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		buttonPanel.setPreferredSize(new Dimension(750, 50));

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
		programPanel.add(buttonPanel, BorderLayout.PAGE_START);
		programPanel.add(dataPanel, BorderLayout.CENTER);
		add(programPanel);
		// add(buttonPanel(), BorderLayout.NORTH);
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
		Factory.setButtonPanel(buttonPanel);
		pack();

	}

}
