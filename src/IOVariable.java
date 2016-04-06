


import java.io.Serializable;
import java.util.List;

public class IOVariable implements Serializable {
	String name;
	List<String> inputs;
	List<String> outputs;

	public String GetName() {
		return name;
	}

	public void setInputs(List<String> inputs) {
		this.inputs = inputs;
	}

	public List<String> getOutputs() {
		return outputs;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getInputs() {
		return inputs;
	}

	public void setOutputs(List<String> outputs) {
		this.outputs = outputs;
	}

	@Override
	public String toString() {
		String inputStr = "";
		for (String in : inputs) {
			inputStr = in + in + ",";

		}
		String outputStr = "";

		for (String out : outputs) {
			outputStr = outputStr + out + ",";

		}
		return " name=" + name + " input:{" + inputStr + "} output :{" + outputStr + "}";
	}

}