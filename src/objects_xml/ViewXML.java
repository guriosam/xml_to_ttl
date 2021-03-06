package objects_xml;

/**
 * @author cbvs
 * 
 *         Representation of a View tag in the XML File
 */
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

	/**
	 * Method responsible for receiving a line containing a View
	 * <view origin="" name="" label="" identifier="" > And translating it in a Java
	 * Object
	 * 
	 * @param viewLine containing the view tag
	 */
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

	/**
	 * Method responsible for receiving a line containing a View <tables repcol="">
	 * And translating it in a Java Object
	 * 
	 * @param fileLine containing a tables tag
	 */
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

		if (tablesXML != null) {

			view += tablesXML + "\n";

		}

		view += "    </view>";

		return view;
	}

	/**
	 * Converts Java Object to String TTL
	 * 
	 * @return TTL String representation of the Java Object
	 */
	public String toStringTTL() {
		String ttl = "";

		String pks = "";
		boolean f = false;

		if (tablesXML == null) {
			return "\n";
		}

		for (TableXML t : tablesXML.getTables()) {
			if (f) {
				pks += "_";
			}

			if (t == null) {
				continue;
			}

			String pk = "";

			if (t.getPk() != null && !t.getPk().equals("")) {
				pk = t.getPk();
			} else if (t.getPseudoPk() != null && !t.getPseudoPk().equals("")) {
				pk = t.getPseudoPk();
			}

			pks += "@@" + name + "." + pk + "@@";

			f = true;
		}

		ttl += "map:" + name + " a	 d2rq:ClassMap ;\n";
		ttl += "d2rq:class	vocab:" + name + " ;\n";
		ttl += "d2rq:classDefinitionLabel	vocab:" + label + " ;\n";
		ttl += "dataStorage		map:database ;\n";
		ttl += "d2rq:uriPattern		\"" + prefix + "/" + pks + "\" .\n\n";

		ttl += tablesXML.toStringTTL();

		return ttl;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String toStringRML() {

		String viewRML = "";

		if (origin != null && !origin.equals("")) {
			viewRML += "  rr:subjectMap [\n";
			viewRML += "    rr:template \"" + prefix + "@origin\";\n";
			viewRML += "    rr:class map:Views\n";
			viewRML += "  ];\n";
		}

		if (name != null && !name.equals("")) {
			viewRML += "  rr:subjectMap [\n";
			viewRML += "    rr:template \"" + prefix + "@name\";\n";
			viewRML += "    rr:class map:Views\n";
			viewRML += "  ];\n";
		}

		if (label != null && !label.equals("")) {
			viewRML += "  rr:subjectMap [\n";
			viewRML += "    rr:template \"" + prefix + "@label\";\n";
			viewRML += "    rr:class map:Views\n";
			viewRML += "  ];\n";
		}

		if (identifier != null && !identifier.equals("")) {
			viewRML += "  rr:subjectMap [\n";
			viewRML += "    rr:template \"" + prefix + "@identifier\";\n";
			viewRML += "    rr:class map:Views\n";
			viewRML += "  ];\n";
		}

		viewRML += "  rr:predicateObjectMap [\n";
		viewRML += "    rr:predicate map:tables;\n";
		viewRML += "    rr:objectMap [\n";
		viewRML += "      rr:parentTriplesMap <#TablesMapping> \n";
		viewRML += "    ]\n";
		viewRML += "  ].\n";

		viewRML += tablesXML.toStringRML(prefix);

		return viewRML;
	}

}
