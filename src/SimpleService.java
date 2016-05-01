import java.util.ArrayList;

/**
 * Represents the services of the system
 * @author Simon Miller s3353879
 * @author Roman Lobanov s3399752
 * @author Leslie Pang s3390257
 * @author Jordan Mason s3423620
 *
 */
public class SimpleService {

	/**
	 * stores the service name
	 */
	private String name;
	/**
	 * stores all input services
	 */
	private ArrayList<String> inputService;
	/**
	 * stores all output services
	 */
	private ArrayList<String> outputService;
	/**
	 * stores all local variables
	 */
	private ArrayList<String> nameOfVariable;
	/**
	 * stores all input variables
	 */
	private ArrayList<String> inputVariable;
	/**
	 * stores all output variables
	 */
	private ArrayList<String> outputVariable;
	/**
	 * stores the parent service this was decomposed from
	 */
	private SimpleService parent;
	/**
	 * stores all services decomposed from this service
	 */
	private ArrayList<SimpleService> children;
	/**
	 * value to store whether service is elementary or not
	 */
	private Boolean isElementary;

	/**
	 * stores all elements, currently unused
	 */
	private ArrayList<String> elements;

	/**
	 * default constructor used whenever a service is needed but the info to populate it is not readily available yet
	 */
	public SimpleService(){} // default empty constructor

	/**
	 * constructor used when all initial information is available from conception
	 * @param name
	 * @param inputService
	 * @param outputService
	 * @param nameOfVariable
	 * @param inputVariable
	 * @param outputVariable
	 */
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


	

	/**
	 * @return the parent
	 */
	public SimpleService getParent() {
		return parent;
	}

	/**
	 * @param parent the parent that was decomposed
	 */
	public void setParent(SimpleService parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public ArrayList<SimpleService> getChildren() {
		return children;
	}

	/**
	 * @param children the children that were decomposed from this service
	 */
	public void setChildren(ArrayList<SimpleService> children) {
		this.children = children;
	}
	
	public void addChild(SimpleService child)
	{
		children.add(child);
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

	/**
	 * @return value whether service is Elementary or not
	 */
	public Boolean IsElementary() {
		return isElementary;
	}

	/**
	 * @param isElementary set result of analysis
	 */
	public void setIsElementary(Boolean isElementary) {
		this.isElementary = isElementary;
	}
	

}
