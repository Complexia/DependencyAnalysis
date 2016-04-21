import java.util.ArrayList;

public class SimpleService {
	
	private String name;
	private String inputService;
	private String outputService;
	private String nameOfVariable;
	
	
	
	private ArrayList<String> elements;
	
	
	public SimpleService(String name, String inputService, String outputService, String nameOfVariable){
		
		this.name = name;
		this.inputService = inputService;
		this.outputService = outputService;
		this.nameOfVariable = nameOfVariable;
		
	}


	public String getName() {
		return name;
	}


	public String getInputService() {
		return inputService;
	}


	public String getOutputService() {
		return outputService;
	}


	public String getNameOfVariable() {
		return nameOfVariable;
	}
	

}
