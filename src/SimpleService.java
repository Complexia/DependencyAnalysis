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
	private ArrayList<IOVariable> inputService;
	/**
	 * stores all output services
	 */
	private ArrayList<IOVariable> outputService;
	/**
	 * stores all local variables
	 */
	private ArrayList<IOVariable> nameOfVariable;
	/**
	 * stores all input variables
	 */
	private ArrayList<IOVariable> inputVariable;
	/**
	 * stores all output variables
	 */
	private ArrayList<IOVariable> outputVariable;
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
	public SimpleService(String name, ArrayList<IOVariable> inputService, ArrayList<IOVariable> outputService,
			ArrayList<IOVariable> nameOfVariable, ArrayList<IOVariable> inputVariable, ArrayList<IOVariable> outputVariable) {
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
	public ArrayList<IOVariable> getInputService() {
		return inputService;
	}

	/**
	 * @param inputService the inputService to set
	 */
	public void setInputService(ArrayList<IOVariable> inputService) {
		this.inputService = inputService;
	}

	/**
	 * @return the outputService
	 */
	public ArrayList<IOVariable> getOutputService() {
		return outputService;
	}

	/**
	 * @param outputService the outputService to set
	 */
	public void setOutputService(ArrayList<IOVariable> outputService) {
		this.outputService = outputService;
	}

	/**
	 * @return the nameOfVariable
	 */
	public ArrayList<IOVariable> getNameOfVariable() {
		return nameOfVariable;
	}

	/**
	 * @param nameOfVariable the nameOfVariable to set
	 */
	public void setNameOfVariable(ArrayList<IOVariable> nameOfVariable) {
		this.nameOfVariable = nameOfVariable;
	}

	/**
	 * @return the inputVariable
	 */
	public ArrayList<IOVariable> getInputVariable() {
		return inputVariable;
	}

	/**
	 * @param inputVariable the inputVariable to set
	 */
	public void setInputVariable(ArrayList<IOVariable> inputVariable) {
		this.inputVariable = inputVariable;
	}

	/**
	 * @return the outputVariable
	 */
	public ArrayList<IOVariable> getOutputVariable() {
		return outputVariable;
	}

	/**
	 * @param outputVariable the outputVariable to set
	 */
	public void setOutputVariable(ArrayList<IOVariable> outputVariable) {
		this.outputVariable = outputVariable;
	}

	/**
	 * @return the elements
	 */
	public ArrayList<IOVariable> getElements() {
		return elements;
	}

	/**
	 * @param elements the elements to set
	 */
	public void setElements(ArrayList<IOVariable> elements) {
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
	
	public Boolean removeVariable(IOVariable rm)
	{
		return nameOfVariable.remove(rm);
	}

}
