package model;


import java.util.ArrayList;
import java.util.List;

/**
 * represents the connections between service components
 * 
 * @author Simon Miller s3353879
 * @author Roman Lobanov s3399752
 * @author Leslie Pang s3390257
 * @author Jordan Mason s3423620
 *
 */
public class IOVariable {
	// everything public because there isn't much need for encapsulation here
	public String name;
	public List<IOVariable> inputs;
	public List<IOVariable> outputs;
	public Boolean isLocal;

	/**
	 * default constructor, initialises all variables to empty arrays to avoid
	 * null pointer exceptions
	 */
	public IOVariable() {
		name = "";
		inputs = new ArrayList<IOVariable>();
		outputs = new ArrayList<IOVariable>();
	}

	/**
	 * Standard getter for the name variable
	 * 
	 * @return name of the IOVariable
	 */
	public String getName() {
		return name;
	}

	/**
	 * Standard setter for name variable
	 * 
	 * @param name
	 *            of the IOVariable
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Standard getter for inputs variable
	 * 
	 * @return inputs list of IOVariable
	 */
	public List<IOVariable> getInputs() {
		return inputs;
	}

	public void setInputs(List<IOVariable> inputs) {
		this.inputs = inputs;
	}

	/**
	 * Standard getter for outputs variable
	 * 
	 * @return the outputs
	 */
	public List<IOVariable> getOutputs() {
		return outputs;
	}

	/**
	 * Standard setter for outputs variable
	 * 
	 * @param outputs
	 *            the outputs to set
	 */
	public void setOutputs(List<IOVariable> outputs) {
		this.outputs = outputs;
	}
	
	public boolean isLocal()
	{
		return isLocal;
	}
	
	public void setLocal(boolean loc)
	{
		isLocal = loc;
	}

}