package model;


//import relevant packages
import java.util.ArrayList;
import java.util.List;

public class ServiceNode {
	private List<ServiceNode> successors;
	private List<ServiceNode> predecesors;
	public SimpleService service;
	private int tarjanIndex;
	private int tarjanLowLink;

//	public ServiceNode(Service s) {
//		this.service = s;
//		successors = new ArrayList<ServiceNode>();
//		predecesors = new ArrayList<ServiceNode>();
//		tarjanIndex = -1; // UNDEFINED
//	}
	
	public ServiceNode(SimpleService s) {
		this.service = s;
		successors = new ArrayList<ServiceNode>();
		predecesors = new ArrayList<ServiceNode>();
		tarjanIndex = -1; // UNDEFINED
	}

	public boolean hasSystemInputs() {
		return service.getInputService().size() > predecesors.size();
	}

	public List<IOVariable> getSystemInputs() {
		ArrayList<IOVariable> systemInputs = new ArrayList<IOVariable>();
		for (IOVariable input : service.getInputService()) {
			boolean isSystemInput = true;

			for (ServiceNode pred : predecesors) {
				if (pred.getService().getOutputService().contains(input)) {
					isSystemInput = false;
				}
			}

			if (isSystemInput) {
				systemInputs.add(input);
			}
		}
		return systemInputs;
	}

	public boolean hasSystemOutputs() {
		return service.getOutputService().size() > successors.size();
	}

	public List<IOVariable> getSystemOutputs() {
		ArrayList<IOVariable> systemOutputs = new ArrayList<IOVariable>();
		for (IOVariable output : service.getOutputService()) {
			boolean isSystemOutput = true;

			for (ServiceNode successor : successors) {
				if (successor.getService().getInputService().contains(output)) {
					isSystemOutput = false;
				}
			}

			if (isSystemOutput) {
				systemOutputs.add(output);
			}
		}
		return systemOutputs;
	}

	public List<ServiceNode> getSuccessors() {
		return successors;
	}

	public String getSuccessorsString() {
		String s = "";
		for (ServiceNode n : successors) {
			s += n.getName() + " ";
		}
		return s;
	}

	public List<ServiceNode> getPredecessors() {
		return predecesors;
	}

	public String getPredecessorsString() {
		String s = "";
		for (ServiceNode n : predecesors) {
			s += n.getName() + " ";
		}
		return s;
	}

	public NodeType getNodeType() {
		if (successors.size() == 0 && predecesors.size() == 0) {
			return NodeType.DT;
		}

		if (successors.size() == 0) {
			return NodeType.TT;
		}

		if (predecesors.size() == 0) {
			return NodeType.LT;
		}

		// This list contains a record of nodes we have searched
		ArrayList<ServiceNode> searched = new ArrayList<ServiceNode>();

		// Check paths from all successors to see if any link back to this node
		for (ServiceNode n : successors) {
			if (n.canReach(this, searched)) {
				return NodeType.N;
			}
		}

		return NodeType.T;
	}

	private boolean canReach(ServiceNode target, List<ServiceNode> alreadySearched) {
		// Add current node to search history
		alreadySearched.add(this);

		// If I am the target, we have found the target and can stop searching
		if (this == target) {
			return true;
		}

		// We are not the target, so ask all of our successors if they can reach
		// the target
		for (ServiceNode n : successors) {
			// Do not asks successors that have already been asked/searched
			if (!alreadySearched.contains(n)) {
				// If any successor can reach the target, we finish and return
				// true
				if (n.canReach(target, alreadySearched)) {
					return true;
				}
			}
		}

		// If reached here, we are not the target and none of our successors can
		// reach the target either
		return false;
	}

	public String getName() {
		return service.getName();
	}

	public SimpleService getService() {
		return service;
	}

	public int getTarjanIndex() {
		return tarjanIndex;
	}

	public void setTarjanIndex(int val) {
		tarjanIndex = val;
	}

	public void setTarjanLowLink(int val) {
		tarjanLowLink = val;
	}

	public int getTarjanLowLink() {
		return tarjanLowLink;
	}
}
