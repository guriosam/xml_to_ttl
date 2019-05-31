package objects;

import java.util.ArrayList;

public class ViewXML {

	private String origin;
	private String name;
	private String label;
	private Boolean identifier;
	private TablesXML tables;

	public void collectView(String viewLine) {
		// <view origin="" name="" label="" identifier="" >

		if (viewLine.contains("origin=")) {
			String aux = viewLine.substring(viewLine.indexOf("origin=\"") + 8);
			String origin = aux.substring(0, aux.indexOf("\""));
			this.origin = origin;
		}
		if (viewLine.contains("name=")) {
			String aux = viewLine.substring(viewLine.indexOf("name=\"") + 6);
			String name = aux.substring(0, aux.indexOf("\""));
			this.name = name;
		}
		if (viewLine.contains("label=")) {
			String aux = viewLine.substring(viewLine.indexOf("label=\"") + 7);
			String label = aux.substring(0, aux.indexOf("\""));
			this.label = label;
		}
		if (viewLine.contains("identifier=")) {
			String aux = viewLine.substring(viewLine.indexOf("identifier=\"") + 12);
			String identifier = aux.substring(0, aux.indexOf("\""));
			this.identifier = Boolean.valueOf(identifier);
		}
		
		
	}

	public void collectTables(String fileLine) {

		tables = new TablesXML();
		String repCol = "";

		if (fileLine.contains("repcol=")) {
			String aux = fileLine.substring(fileLine.indexOf("repcol=\"") + 8);
			repCol = aux.substring(0, aux.indexOf("\""));
		}

		tables.setRepCol(repCol);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Boolean identifier) {
		this.identifier = identifier;
	}

	public TablesXML getTables() {
		return tables;
	}

	public void setTables(TablesXML tables) {
		this.tables = tables;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Override
	public String toString() {
		String view = "    <view";

		if (origin != null && !origin.equals("")) {
			view += " origin=\"" + origin + "\"";
		}
		if (name != null && !name.equals("")) {
			view += " name=\"" + name + "\"";
		}
		if (label != null && !label.equals("")) {
			view += " label=\"" + label + "\"";
		}
		if (identifier != null && !identifier.equals("")) {
			view += " identifier=\"" + identifier + "\"";
		}

		view += ">\n";

		view += tables + "\n";
		
		view += "    </view>";
		
		return view;
	}

}
