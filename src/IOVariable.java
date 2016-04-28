
import java.util.List;

public class IOVariable
{
   String name;
   List<IOVariable> inputs;
   List<IOVariable> outputs;
   
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public List<IOVariable> getInputs() {
	return inputs;
}
public void setInputs(List<IOVariable> inputs) {
	this.inputs = inputs;
}
public List<IOVariable> getOutputs() {
	return outputs;
}
public void setOutputs(List<IOVariable> outputs) {
	this.outputs = outputs;
}

   

}