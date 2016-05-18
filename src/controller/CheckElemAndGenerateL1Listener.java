package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import factory.Factory;
import model.IOVariable;
import model.Service;
import model.SimpleService;
import model.UploadFile;

public class CheckElemAndGenerateL1Listener implements ActionListener{
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		

		Factory.getSystemGenerator().checkElementaryServices();
		Factory.getSystemGenerator().generateL1();
		
	
		
		
	}
	

}
