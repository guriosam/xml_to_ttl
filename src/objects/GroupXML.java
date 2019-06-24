package objects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cbvs
 *
 *         Representation of a Group tag in the XML File
 *
 */
public class GroupXML {

	private String viewName;
	private String name;
	private List<ColumnXML> columnsXML;

	/**
	 * Method responsible for receiving a line containing a Column And translating
	 * it in a Java Object
	 * 
	 * @param fileLine of XML containing a column
	 * @return the JoinXML object representation of the fileLine
	 */
	public ColumnXML collectColumn(String fileLine) {
		// <column name="flow_name" label="Flow name" indexing="true"/>
		// <column name="job_algo_id" label="Algorithm" untagged="true">

		ColumnXML columnXML = new ColumnXML(viewName);

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

		columnsXML.add(columnXML);

		return columnXML;

	}

	public GroupXML(String viewName) {
		columnsXML = new ArrayList<>();
		this.viewName = viewName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ColumnXML> getColumns() {
		return columnsXML;
	}

	public void setColumns(List<ColumnXML> columns) {
		this.columnsXML = columns;
	}

	/**
	 * This function collects the last element of the list columnsXML
	 * 
	 * @return the last ColumnXML of the list columnsXML
	 */
	public ColumnXML getLastColumn() {
		if (columnsXML.size() == 0) {
			System.out.println("Error in Column");
			throw new NullPointerException();
		}

		ColumnXML columnXML = columnsXML.get(columnsXML.size() - 1);
		return columnXML;
	}

	@Override
	public String toString() {
		String group = "          <group";

		if (name != null && !name.equals("")) {
			group += " name=\"" + name + "\"";
		}

		group += ">\n";

		for (ColumnXML c : columnsXML) {
			group += c + "\n";
		}

		group += "          </group>";

		return group;
	}

	public String toStringTTL() {
		String ttl = "";

		for (ColumnXML c : columnsXML) {
			ttl += c.toStringTTL();
		}

		return ttl;
	}

}
