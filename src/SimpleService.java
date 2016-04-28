import java.util.ArrayList;

public class SimpleService {

	private String name;
	private ArrayList<String> inputService;//there can be multiple of these so we need to use lists
	private ArrayList<String> outputService;//
	private ArrayList<String> nameOfVariable;//
	private ArrayList<String> inputVariable;//
	private ArrayList<String> outputVariable;//
	private SimpleService parent;//if service is a subservice
	private ArrayList<SimpleService> children;//if service has been decomposed, store subservices

	private ArrayList<String> elements;

	public SimpleService(){} // default empty constructor

	public SimpleService(String name, ArrayList<String> inputService, ArrayList<String> outputService,
			ArrayList<String> nameOfVariable, ArrayList<String> inputVariable, ArrayList<String> outputVariable) {
		super();
		this.name = name;
		this.inputService = inputService;
		this.outputService = outputService;
		this.nameOfVariable = nameOfVariable;
		this.inputVariable = inputVariable;
		this.outputVariable = outputVariable;
	}


	public SimpleService getParent() {
		return parent;
	}
	public void setParent(SimpleService parent) {
		this.parent = parent;
	}
	public ArrayList<SimpleService> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<SimpleService> children) {
		this.children = children;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the inputService
	 */
	public ArrayList<String> getInputService() {
		return inputService;
	}

	/**
	 * @param inputService the inputService to set
	 */
	public void setInputService(ArrayList<String> inputService) {
		this.inputService = inputService;
	}

	/**
	 * @return the outputService
	 */
	public ArrayList<String> getOutputService() {
		return outputService;
	}

	/**
	 * @param outputService the outputService to set
	 */
	public void setOutputService(ArrayList<String> outputService) {
		this.outputService = outputService;
	}

	/**
	 * @return the nameOfVariable
	 */
	public ArrayList<String> getNameOfVariable() {
		return nameOfVariable;
	}

	/**
	 * @param nameOfVariable the nameOfVariable to set
	 */
	public void setNameOfVariable(ArrayList<String> nameOfVariable) {
		this.nameOfVariable = nameOfVariable;
	}

	/**
	 * @return the inputVariable
	 */
	public ArrayList<String> getInputVariable() {
		return inputVariable;
	}

	/**
	 * @param inputVariable the inputVariable to set
	 */
	public void setInputVariable(ArrayList<String> inputVariable) {
		this.inputVariable = inputVariable;
	}

	/**
	 * @return the outputVariable
	 */
	public ArrayList<String> getOutputVariable() {
		return outputVariable;
	}

	/**
	 * @param outputVariable the outputVariable to set
	 */
	public void setOutputVariable(ArrayList<String> outputVariable) {
		this.outputVariable = outputVariable;
	}

	/**
	 * @return the elements
	 */
	public ArrayList<String> getElements() {
		return elements;
	}

	/**
	 * @param elements the elements to set
	 */
	public void setElements(ArrayList<String> elements) {
		this.elements = elements;
	}
	

}
