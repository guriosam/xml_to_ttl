package objects;

import java.util.ArrayList;
import java.util.List;

public class JoinXML {

	private String view;

	private List<ConditionXML> conditionsXML;

	public JoinXML() {
		this.conditionsXML = new ArrayList<>();
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public void collectColumn(String fileLine) {

		ConditionXML conditionXML = new ConditionXML();

		if (fileLine.contains("from=")) {
			String aux = fileLine.substring(fileLine.indexOf("from=\"") + 6);
			String from = aux.substring(0, aux.indexOf("\""));
			conditionXML.setColumnFrom(from);
		}

		if (fileLine.contains("to=")) {
			String aux = fileLine.substring(fileLine.indexOf("to=\"") + 4);
			String to = aux.substring(0, aux.indexOf("\""));
			conditionXML.setColumnTo(to);
		}

		conditionsXML.add(conditionXML);

	}

	@Override
	public String toString() {
		String join = "              <join";

		if (view != null && !view.equals("")) {
			join += " view=\"" + view + "\">\n";
		}

		for (ConditionXML condition : conditionsXML) {
			join += "                <condition";

			if (condition.getColumnFrom() != null && !condition.getColumnFrom().equals("")) {
				join += " from=\"" + condition.getColumnFrom() + "\"";
			}
			if (condition.getColumnTo() != null && !condition.getColumnTo().equals("")) {
				join += " to=\"" + condition.getColumnTo() + "\"/>\n";
			}
		}

		join += "              </join>";

		return join;
	}

	public List<ConditionXML> getConditionsXML() {
		return conditionsXML;
	}

	public void setConditionsXML(List<ConditionXML> conditionsXML) {
		this.conditionsXML = conditionsXML;
	}
	
	
	

}
