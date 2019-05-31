package objects;

import java.util.ArrayList;
import java.util.List;

public class GroupXML {

	private String name;
	private List<ColumnXML> columns;

	public ColumnXML collectColumn(String fileLine) {
		// <column name="flow_name" label="Flow name" indexing="true"/>
		// <column name="job_algo_id" label="Algorithm" untagged="true">

		ColumnXML columnXML = new ColumnXML();

		if (fileLine.contains("name=")) {
			String aux = fileLine.substring(fileLine.indexOf("name=\"") + 6);
			String name = aux.substring(0, aux.indexOf("\""));
			columnXML.setName(name);
		}

		if (fileLine.contains("label=")) {
			String aux = fileLine.substring(fileLine.indexOf("label=\"") + 7);
			String label = aux.substring(0, aux.indexOf("\""));
			columnXML.setLabel(label);
		}

		if (fileLine.contains("indexing=")) {
			String aux = fileLine.substring(fileLine.indexOf("indexing=\"") + 10);
			String indexing = aux.substring(0, aux.indexOf("\""));
			columnXML.setIndexing(Boolean.valueOf(indexing));
		}

		if (fileLine.contains("untagged=")) {
			String aux = fileLine.substring(fileLine.indexOf("untagged=\"") + 10);
			String untagged = aux.substring(0, aux.indexOf("\""));
			columnXML.setUntagged(Boolean.valueOf(untagged));
		}

		columns.add(columnXML);

		return columnXML;

	}

	public GroupXML() {
		columns = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ColumnXML> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnXML> columns) {
		this.columns = columns;
	}

	public ColumnXML getLastColumn() {
		if (columns.size() == 0) {
			System.out.println("Error in Column");
			throw new NullPointerException();
		}

		ColumnXML columnXML = columns.get(columns.size() - 1);
		return columnXML;
	}

	@Override
	public String toString() {
		String group = "          <group";

		if (name != null && !name.equals("")) {
			group += " name=\"" + name + "\"";
		}

		group += ">\n";

		for (ColumnXML c : columns) {

			group += c + "\n";

		}

		group += "          </group>";

		return group;
	}

}
