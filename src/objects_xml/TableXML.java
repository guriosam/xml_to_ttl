package objects_xml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cbvs
 *
 *         Representation of a Table tag in the XML File
 *
 */
public class TableXML {

	private String name;
	private String owner;
	private String pk;
	private String pseudoPk;
	private String where;
	private List<GroupXML> groupsXML;

	private String viewName;

	public TableXML(String viewName) {
		groupsXML = new ArrayList<>();
		this.viewName = viewName;
	}

	/**
	 * Method responsible for receiving a line containing a Group <group name="">
	 * And translating it in a Java Object
	 * 
	 * @param fileLine of XML containing a group
	 * @return the GroupXML object representation of the fileLine
	 */
	public GroupXML collectGroup(String fileLine) {
		GroupXML group = new GroupXML(viewName);

		if (fileLine.contains("name=")) {
			String aux = fileLine.substring(fileLine.indexOf("name=\"") + 6);
			String name = aux.substring(0, aux.indexOf("\""));
			group.setName(name);
		}

		groupsXML.add(group);

		return group;

	}

	/**
	 * Method responsible for receiving a line containing a Pk And translating it in
	 * a Java Object
	 * 
	 * @param fileLine of XML containing a pk
	 */
	public void collectPk(String fileLine) {
		String aux = fileLine.substring(fileLine.indexOf(">") + 1);
		String pk = aux.substring(0, aux.indexOf("<"));
		setPk(pk);
	}

	/**
	 * Method responsible for receiving a line containing a PseudoPk And translating
	 * it in a Java Object
	 * 
	 * @param fileLine of XML containing a pseudopk
	 */
	public void collectPseudoPk(String fileLine) {
		String aux = fileLine.substring(fileLine.indexOf(">") + 1);
		String pseudoPk = aux.substring(0, aux.indexOf("<"));
		setPseudoPk(pseudoPk);
	}

	/**
	 * Method responsible for receiving a line containing a Where And translating it
	 * in a Java Object
	 * 
	 * @param fileLine of XML containing a where
	 */
	public void collectWhere(String fileLine) {
		String aux = fileLine.substring(fileLine.indexOf(">") + 1);
		String where = aux.substring(0, aux.indexOf("<"));
		setWhere(where);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public List<GroupXML> getGroups() {
		return groupsXML;
	}

	public void setGroups(List<GroupXML> groups) {
		this.groupsXML = groups;
	}

	public GroupXML getLastGroup() {
		if (groupsXML.size() == 0) {
			System.out.println("Error in Group");
			throw new NullPointerException();
		}

		GroupXML groupXML = groupsXML.get(groupsXML.size() - 1);
		return groupXML;
	}

	@Override
	public String toString() {
		String table = "        <table";

		if (name != null && !name.equals("")) {
			table += " name=\"" + name + "\"";
		}

		if (owner != null && !owner.equals("")) {
			table += " owner=\"" + owner + "\"";
		}

		table += ">\n";

		if (pk != null && !pk.equals("")) {
			table += "          <pk>" + pk + "</pk>\n";
		}

		if (pseudoPk != null && !pseudoPk.equals("")) {
			table += "          <pseudoPk>" + pseudoPk + "</pseudoPk>\n";
		}

		if (where != null && !where.equals("")) {
			table += "          <where>" + where + "</where>\n";
		}

		for (GroupXML g : groupsXML) {
			table += g + "\n";
		}

		table += "        </table>";

		return table;
	}

	/**
	 * Converts Java Object to String TTL
	 * 
	 * @return TTL String representation of the Java Object
	 */
	public String toStringTTL() {
		String ttl = "";

		for (GroupXML g : groupsXML) {
			ttl += g.toStringTTL();
		}

		return ttl;
	}

	public String getPseudoPk() {
		return pseudoPk;
	}

	public void setPseudoPk(String pseudoPk) {
		this.pseudoPk = pseudoPk;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String toStringRML(String prefix) {

		String tableRML = "";

		if (name != null && !name.equals("")) {
			tableRML += "  rr:subjectMap [\n";
			tableRML += "    rr:template \"" + prefix + "@name\";\n";
			tableRML += "    rr:class map:Table\n";
			tableRML += "  ];\n";
		}

		if (owner != null && !owner.equals("")) {
			tableRML += "  rr:subjectMap [\n";
			tableRML += "    rr:template \"" + prefix + "@owner\";\n";
			tableRML += "    rr:class map:Table\n";
			tableRML += "  ];\n";
		}

		if (pk != null && !pk.equals("")) {
			tableRML += "  rr:predicateObjectMap [\n";
			tableRML += "    rr:predicate map:pk;\n";
			tableRML += "    rr:objectMap [\n";
			tableRML += "      rr:reference\".\";\n";
			tableRML += "    ]\n";
			tableRML += "  ]\n";
		}

		if (pseudoPk != null && !pseudoPk.equals("")) {
			tableRML += "  rr:predicateObjectMap [\n";
			tableRML += "    rr:predicate map:pseudoPk;\n";
			tableRML += "    rr:objectMap [\n";
			tableRML += "      rr:reference\".\";\n";
			tableRML += "    ]\n";
			tableRML += "  ]\n";
		}

		tableRML += "  rr:predicateObjectMap [\n";
		tableRML += "    rr:predicate map:group;\n";
		tableRML += "    rr:objectMap [\n";
		tableRML += "      rr:parentTriplesMap <#GroupMapping> \n";
		tableRML += "    ]\n";
		tableRML += "  ].\n";
		
		for(GroupXML g : groupsXML) {
			tableRML += g.toStringRML(prefix);
		}

		return tableRML;
	}

}
