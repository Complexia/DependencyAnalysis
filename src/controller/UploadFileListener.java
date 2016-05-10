package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.Provider.Service;
import java.util.List;

import javax.swing.JFileChooser;

import factory.Factory;
import model.UploadFile;

public class UploadFileListener implements ActionListener {
	
	File selectedFile = null;
	
	public File getSelectedFile(){
		return selectedFile;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		List<model.Service> lstData = null;
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(Factory.getMainWindow());
		if (result == JFileChooser.APPROVE_OPTION) {
		    selectedFile = fileChooser.getSelectedFile();
		    Factory.setSelectedFile(selectedFile);
		    try {
				
				lstData = UploadFile.readFromXML(selectedFile);
				Factory.setLstData(lstData);
				Factory.setL0Generated(false);
				Factory.setL1Generated(false);
				Factory.setL2Generated(false);
				Factory.setChkElemServGenerated(false);
				Factory.setSCSGenerated(false);
			}

			catch (Exception e1)// TODO: use correct exception type
			{
				System.out.println("error occured during reading of input file.");
				e1.printStackTrace();
			}
		    
		}
		else{
			System.out.println("File was not read");
		}
		
       
		
		
		
	}
	
	

}
