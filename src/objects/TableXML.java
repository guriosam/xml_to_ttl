package objects;

import java.util.ArrayList;
import java.util.List;

public class TableXML {

	private String name;
	private String owner;
	private String pk;
	private String pseudoPk;
	private String where;
	private List<GroupXML> groups;

	public TableXML() {
		groups = new ArrayList<>();
	}

	public GroupXML collectGroup(String fileLine) {
		// <group name="Dados Gerais">

		GroupXML group = new GroupXML();

		if (fileLine.contains("name=")) {
			String aux = fileLine.substring(fileLine.indexOf("name=\"") + 6);
			String name = aux.substring(0, aux.indexOf("\""));
			group.setName(name);
		}

		groups.add(group);

		return group;

	}

	public void collectPk(String fileLine) {
		String aux = fileLine.substring(fileLine.indexOf(">") + 1);
		String pk = aux.substring(0, aux.indexOf("<"));
		setPk(pk);
	}

	public void collectPseudoPk(String fileLine) {
		String aux = fileLine.substring(fileLine.indexOf(">") + 1);
		String pseudoPk = aux.substring(0, aux.indexOf("<"));
		setPseudoPk(pseudoPk);
	}

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
		return groups;
	}

	public void setGroups(List<GroupXML> groups) {
		this.groups = groups;
	}

	public GroupXML getLastGroup() {
		if (groups.size() == 0) {
			System.out.println("Error in Group");
			throw new NullPointerException();
		}

		GroupXML groupXML = groups.get(groups.size() - 1);
		return groupXML;
	}

	@Override
	public String toString() {
		// <table name="CAMPO" owner="RST">
		// <pk>CAMP_CD_CAMPO</pk>

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

		for (GroupXML g : groups) {
			table += g + "\n";
		}

		table += "        </table>";

		return table;
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

}
