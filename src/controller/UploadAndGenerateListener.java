package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;

import exceptions.ServiceException;
import factory.Factory;
import model.SimpleService;
import model.UploadFile;

/**
 * Encapsulates the upload and generate L0 functionality.
 * It is primarily responsible for formatting the data to be displayed on screen
 * along with prompting the user for an input file to be parsed with {@link UploadFile}
 *
 */
public class UploadAndGenerateListener implements ActionListener {
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Factory.getSystemGenerator().uploadFile();
		Factory.getSystemGenerator().generateL0();
		
		
       
		
	}

	// **************************************************************************************************************
	// Create method for removing duplicates?
	/**
	 * Removes any duplicate entries in the given list
	 * @param list the list to be cleaned out
	 */
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
		
		
		
		
	}
	
	

}
