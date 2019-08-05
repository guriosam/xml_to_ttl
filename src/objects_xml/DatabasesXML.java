package objects_xml;

import java.util.ArrayList;
import java.util.List;

public class DatabasesXML {

	private List<DatabaseXML> databasesXML;

	public DatabasesXML() {
		setDatabases(new ArrayList<>());
	}

	public List<DatabaseXML> getDatabases() {
		return databasesXML;
	}

	public void setDatabases(List<DatabaseXML> databases) {
		this.databasesXML = databases;
	}

	/**
	 * This function collects the last element of the list databasesXML
	 * 
	 * @return the last DatabaseXML of the list databasesXML
	 */
	public DatabaseXML getLastDatabase() {
		if (databasesXML.size() == 0) {
			System.out.println("Error in Tables");
			throw new NullPointerException();
		}

		DatabaseXML databaseXML = databasesXML.get(databasesXML.size() - 1);
		return databaseXML;
	}

}
