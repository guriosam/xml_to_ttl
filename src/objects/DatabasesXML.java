package objects;

import java.util.ArrayList;
import java.util.List;

public class DatabasesXML {
	
	private List<DatabaseXML> databases;
	
	public DatabasesXML() {
		setDatabases(new ArrayList<>());
	}

	public List<DatabaseXML> getDatabases() {
		return databases;
	}

	public void setDatabases(List<DatabaseXML> databases) {
		this.databases = databases;
	}
	
	

}
