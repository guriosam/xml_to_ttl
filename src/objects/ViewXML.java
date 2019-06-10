package objects;

import java.util.ArrayList;

public class ViewXML {

	private String origin;
	private String name;
	private String label;
	private Boolean identifier;
	private TablesXML tablesXML;

	private String prefix;

	public ViewXML(String prefix) {
		this.prefix = prefix;
	}

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

		tablesXML = new TablesXML(name);
		String repCol = "";

		if (fileLine.contains("repcol=")) {
			String aux = fileLine.substring(fileLine.indexOf("repcol=\"") + 8);
			repCol = aux.substring(0, aux.indexOf("\""));
		}

		tablesXML.setRepCol(repCol);

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
		return tablesXML;
	}

	public void setTables(TablesXML tables) {
		this.tablesXML = tables;
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

		view += tablesXML + "\n";

		view += "    </view>";

		return view;
	}

	public String toStringTTL() {
		String ttl = "";
		
		String pks = "";
		boolean f = false;
		for(TableXML t : tablesXML.getTables()) {
			if(f) {
				pks += "_";
			}
			pks += "@@" + name + "." + t.getPk() + "@@";
			f = true;
		}

		ttl += "map:" + name + " rdfs:type d2rq:ClassMap ;\n";
		ttl += "d2rq:class	vocab:" + name + " ;\n";
		ttl += "d2rq:classDefinitionLabel	vocab:" + label + " ;\n";
		ttl += "dataStorage		map:database ;\n";
		ttl += "d2rq:uriPattern		\"" + prefix + "/" +  pks +  "\" .\n\n";

		ttl += tablesXML.toStringTTL();

		return ttl;
	}

}
