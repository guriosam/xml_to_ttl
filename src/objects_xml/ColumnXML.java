package objects_xml;

import java.util.ArrayList;

import java.util.List;

/**
 * @author cbvs
 * 
 *         Representation of a Column tag in the XML File
 *
 */
public class ColumnXML {

	private String name;
	private String as;
	private String label;
	private Boolean indexing;
	private Boolean untagged;
	private List<JoinXML> joinsXML;
	private List<DecodeXML> decodesXML;

	private String viewName;

	public ColumnXML(String viewName) {
		setJoins(new ArrayList<>());
		setDecodes(new ArrayList<>());
		this.viewName = viewName;
	}

	/**
	 * Method responsible for receiving a line containing a Join And translating it
	 * in a Java Object
	 * 
	 * @param fileLine of XML containing a join
	 * @return the JoinXML object representation of the fileLine
	 */
	public JoinXML collectJoinView(String fileLine) {

		JoinXML join = new JoinXML();

		if (fileLine.contains("view=")) {
			String aux = fileLine.substring(fileLine.indexOf("view=\"") + 6);
			String view = aux.substring(0, aux.indexOf("\""));

			join.setView(view);
		}

		joinsXML.add(join);

		return join;

	}

	/**
	 * Method responsible for receiving a line containing a Decode And translating
	 * it in a Java Object
	 * 
	 * @param fileLine of XML containing a decode
	 * @return the DecodeXMLl object representation of the fileLine
	 */
	public DecodeXML collectDecode(String fileLine) {

		DecodeXML decode = new DecodeXML();

		String expr = "";
		String def = "";

		if (fileLine.contains("expr=")) {
			String aux = fileLine.substring(fileLine.indexOf("expr=\"") + 6);
			expr = aux.substring(0, aux.indexOf("\""));
		}

		if (fileLine.contains("default=")) {
			String aux = fileLine.substring(fileLine.indexOf("default=\"") + 8);
			def = aux.substring(0, aux.indexOf("\""));
		}

		decode.setExpr(expr);
		decode.setDef(def);

		decodesXML.add(decode);

		return decode;
	}

	/**
	 * This function collects the last element of the list joinsXML
	 * 
	 * @return the last JoinXML of the list joinsXML
	 */
	public JoinXML getLastJoin() {
		if (joinsXML.size() == 0) {
			System.out.println("Error in Join");
			throw new NullPointerException();
		}

		JoinXML join = joinsXML.get(joinsXML.size() - 1);
		return join;
	}

	/**
	 * This function collects the last element of the list decodesXML
	 * 
	 * @return the last DecodeXML of the list decodesXML
	 */
	public DecodeXML getLastDecode() {
		if (decodesXML.size() == 0) {
			System.out.println("Error in Decode");
			throw new NullPointerException();
		}

		DecodeXML dec = decodesXML.get(decodesXML.size() - 1);
		return dec;
	}

	@Override
	public String toString() {
		// <column name="" label="" indexing=""/>
		String column = "            <column";

		if (name != null && !name.equals("")) {
			column += " name=\"" + name + "\"";
		}
		if (label != null && !label.equals("")) {
			column += " label=\"" + label + "\"";
		}
		if (indexing != null) {
			column += " identifier=\"" + indexing + "\"";
		}

		boolean f = true;

		if (joinsXML.size() != 0) {

			column += ">\n";

			for (JoinXML join : joinsXML) {
				column += join + "\n";
			}

			column += "            </column>";

			f = false;
		}

		if (decodesXML.size() != 0) {

			column += ">\n";

			for (DecodeXML d : decodesXML) {
				column += d + "\n";
			}

			column += "            </column>";

			f = false;
		}

		if (f) {
			column += "/>";
		}

		return column;
	}

	/**
	 * Converts Java Object to String TTL
	 * 
	 * @return TTL String representation of the Java Object
	 */
	public String toStringTTL() {

		String ttl = "";

		if (joinsXML.size() == 0 && decodesXML.size() == 0) {
			ttl += "map:" + viewName + "_" + name + "	a	d2rq:PropertyBridge ;\n";
			ttl += "d2rq:belongsToClassMap		map:" + viewName + " ;\n";
			ttl += "d2rq:column                   \"" + viewName + "." + name + "\" ;\n";
			ttl += "d2rq:property                 vocab:" + viewName + "_" + name + " ;\n";
			ttl += "d2rq:propertyDefinitionLabel	\"" + label + "\" .\n\n";
		}

		if (joinsXML.size() > 0) {

			for (JoinXML join : joinsXML) {
				ttl += "map:" + viewName + "_" + name + "__ref		a	d2rq:PropertyBridge ;\n";
				ttl += "d2rq:belongsToClassMap		map:" + viewName + " ;\n";

				for (ConditionXML condition : join.getConditionsXML()) {
					ttl += "d2rq:join                   \"" + viewName + "." + condition.getColumnFrom() + "<="
							+ join.getView() + "." + condition.getColumnTo() + "\" ;\n";
				}

				ttl += "d2rq:propertyDefinitionLabel \"" + label + "\" ;\n";
				ttl += "d2rq:property                 vocab:" + viewName + "_" + name + " ;\n";
				ttl += "d2rq:refersToClassMap	map:" + join.getView() + " .\n\n";
			}

		}

		/*
		 * map:authorName a d2rq:PropertyBridge; d2rq:belongsToClassMap map:Papers;
		 * d2rq:property :authorName; d2rq:column "Persons.Name"; d2rq:join
		 * "Papers.PaperID <= Rel_Person_Paper.PaperID"; d2rq:join
		 * "Rel_Person_Paper.PersonID => Persons.PerID"; d2rq:datatype xsd:string;
		 * d2rq:propertyDefinitionLabel "name"@en; d2rq:propertyDefinitionComment
		 * "Name of an author."@en; .
		 * 
		 */

		/*
		 * TODO
		 */
		if (decodesXML.size() > 0) {

		}

		return ttl;
	}

	/** Gets and Sets **/

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

	public Boolean getIndexing() {
		return indexing;
	}

	public void setIndexing(Boolean indexing) {
		this.indexing = indexing;
	}

	public Boolean getUntagged() {
		return untagged;
	}

	public void setUntagged(Boolean untagged) {
		this.untagged = untagged;
	}

	public List<JoinXML> getJoins() {
		return joinsXML;
	}

	public void setJoins(List<JoinXML> joins) {
		this.joinsXML = joins;
	}

	public List<DecodeXML> getDecodes() {
		return decodesXML;
	}

	public void setDecodes(List<DecodeXML> decodes) {
		this.decodesXML = decodes;
	}

	public String getAs() {
		return as;
	}

	public void setAs(String as) {
		this.as = as;
	}

	public String toStringRML(String prefix) {

		String columnRML = "";

		if (name != null && !name.equals("")) {
			columnRML += "  rr:subjectMap [\n";
			columnRML += "    rr:template \"" + prefix + "@name\";\n";
			columnRML += "    rr:class map:Group\n";
			columnRML += "  ];\n";
		}

		if (label != null && !label.equals("")) {
			columnRML += "  rr:subjectMap [\n";
			columnRML += "    rr:template \"" + prefix + "@label\";\n";
			columnRML += "    rr:class map:Group\n";
			columnRML += "  ];\n";
		}

		if (as != null && !as.equals("")) {
			columnRML += "  rr:subjectMap [\n";
			columnRML += "    rr:template \"" + prefix + "@as\";\n";
			columnRML += "    rr:class map:Group\n";
			columnRML += "  ];\n";
		}

		if (indexing != null && !indexing.equals("")) {
			columnRML += "  rr:subjectMap [\n";
			columnRML += "    rr:template \"" + prefix + "@indexing\";\n";
			columnRML += "    rr:class map:Group\n";
			columnRML += "  ];\n";
		}
		
		
		for(JoinXML j : joinsXML) {
			//columnRML += j.toStringRML(prefix);
		}
		
		//TODO decode, not sure how to do it
		
		return columnRML;
	}

}
