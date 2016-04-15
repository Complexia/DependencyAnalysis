
//import relevant packages
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Service implements Serializable {
	//public String name = null;
	public List<IOVariable> localVariable = null;
	public List<Service> sources = null;
	public List<Service> dSources = null;
	public List<String> inputs = null;
	public List<String> outputs = null;
	public HashMap<String, List<String>> IOstreams = null;
	public HashMap<String, List<String>> DualIOstreams = null;
	public ArrayList<Service> ElementaryServices = new ArrayList<Service>();
	public List<Service> connectedServices = null;

	public Service(){
		
	}
	public String name;
	private ArrayList<String> elements;
	
	public ArrayList<String> getElements(){
		return elements;
	}
	public Service(String name, ArrayList<String> elements){
		
		this.name = name;
		this.elements = elements;
		
	}
	public List<String> getIOstreamsByKey(String Output) {
		return IOstreams.get(Output);
	}

	public HashMap<String, List<String>> getIOstreams() {
		return IOstreams;
	}

	public void setIOstreams(String list1, List<String> list2) {
		this.IOstreams.put(list1, list2);
	}

	public void setDualIOstreams(String list1, List<String> list2) {
		this.DualIOstreams.put(list1, list2);
	}

	public HashMap<String, List<String>> getDualIOstreams() {
		return DualIOstreams;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public List<IOVariable> getVariable() {
		return localVariable;
	}

	public void setVariable(List<IOVariable> localVariable2) {
		this.localVariable = localVariable2;
	}

	public List<String> getInputs() {
		return inputs;
	}

	public void setInputs(List<String> inputs) {
		this.inputs = inputs;
	}

	public List<String> getOutputs() {
		return outputs;
	}

	public List<Service> getelementaryServices() {
		return ElementaryServices;
	}

	public void setelementaryServices(ArrayList<Service> elementaryServices) {
		this.ElementaryServices = elementaryServices;
	}

	public void setOutputs(List<String> outputs) {
		this.outputs = outputs;
	}

	public void addElementaryServices(Service ServicesOne) {
		this.ElementaryServices.add(ServicesOne);
	}
}
