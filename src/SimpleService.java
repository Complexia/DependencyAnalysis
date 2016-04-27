import java.util.ArrayList;

public class SimpleService {

	private String name;
	private String inputService;
	private String outputService;
	private String nameOfVariable;
	private String inputVariable;
	private String outputVariable;

	private ArrayList<String> elements;

	public SimpleService(){} // default empty constructor
	public SimpleService(String name, String inputService, String outputService, String nameOfVariable,
			String outputVariable, String inputVariable) {

		this.name = name;
		this.inputService = inputService;
		this.outputService = outputService;
		this.nameOfVariable = nameOfVariable;
		this.inputVariable = inputVariable;
		this.outputVariable = outputVariable;

	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the inputService
	 */
	public String getInputService() {
		return inputService;
	}

	/**
	 * @param inputService
	 *            the inputService to set
	 */
	public void setInputService(String inputService) {
		this.inputService = inputService;
	}

	/**
	 * @return the outputService
	 */
	public String getOutputService() {
		return outputService;
	}

	/**
	 * @param outputService
	 *            the outputService to set
	 */
	public void setOutputService(String outputService) {
		this.outputService = outputService;
	}

	/**
	 * @return the nameOfVariable
	 */
	public String getNameOfVariable() {
		return nameOfVariable;
	}

	/**
	 * @param nameOfVariable
	 *            the nameOfVariable to set
	 */
	public void setNameOfVariable(String nameOfVariable) {
		this.nameOfVariable = nameOfVariable;
	}

	/**
	 * @return the inputVariable
	 */
	public String getInputVariable() {
		return inputVariable;
	}

	/**
	 * @param inputVariable
	 *            the inputVariable to set
	 */
	public void setInputVariable(String inputVariable) {
		this.inputVariable = inputVariable;
	}

	/**
	 * @return the outputVariable
	 */
	public String getOutputVariable() {
		return outputVariable;
	}

	/**
	 * @param outputVariable
	 *            the outputVariable to set
	 */
	public void setOutputVariable(String outputVariable) {
		this.outputVariable = outputVariable;
	}

	/**
	 * @return the elements
	 */
	public ArrayList<String> getElements() {
		return elements;
	}

	/**
	 * @param elements
	 *            the elements to set
	 */
	public void setElements(ArrayList<String> elements) {
		this.elements = elements;
	}

}
