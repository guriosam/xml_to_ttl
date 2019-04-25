package objects;

import java.util.List;

public class XMLGeneric {
	
	private DatabaseXML databaseXML;
	private ConfigXML configXML;
	private List<ViewXML> viewsXML;
	
	public DatabaseXML getDatabaseXML() {
		return databaseXML;
	}
	public void setDatabaseXML(DatabaseXML databaseXML) {
		this.databaseXML = databaseXML;
	}
	public ConfigXML getConfigXML() {
		return configXML;
	}
	public void setConfigXML(ConfigXML configXML) {
		this.configXML = configXML;
	}
	public List<ViewXML> getViewsXML() {
		return viewsXML;
	}
	public void setViewsXML(List<ViewXML> viewsXML) {
		this.viewsXML = viewsXML;
	}
	
	public void printTTL() {
		
		String prefix = "";
		prefix += "@prefix d2rq:  <http://www.wiwiss.fu-berlin.de/suhl/bizer/D2RQ/0.1#> .";
		prefix += "@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> ."; 
		prefix += "@prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .";
		prefix += "@prefix xsd:   <http://www.w3.org/2001/XMLSchema#> .";
		
		
	}

}
