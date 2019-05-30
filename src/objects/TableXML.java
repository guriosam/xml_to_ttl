package objects;

import java.util.ArrayList;
import java.util.List;

public class TableXML {

	private String name;
	private String owner;
	private String pk;
	private List<GroupXML> groups;

	public TableXML() {
		groups = new ArrayList<>();
	}

	public GroupXML collectGroup(String fileLine) {
		// <group name="Dados Gerais">

		GroupXML group = new GroupXML();

		if (fileLine.contains("name=")) {
			String aux = fileLine.substring(fileLine.indexOf("name=\"") + 8);
			String name = aux.substring(0, aux.indexOf("\""));

			group.setName(name);
		}

		groups.add(group);
		
		return group;

	}

	public void collectPk(String fileLine) {
		// <pk>id</pk>

		String aux = fileLine.substring(fileLine.indexOf(">") + 1);
		String pk = aux.substring(0, aux.indexOf("<"));

		setPk(pk);
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
			System.out.println("Error in View");
			throw new NullPointerException();
		}

		GroupXML groupXML = groups.get(groups.size() - 1);
		return groupXML;
	}

}
