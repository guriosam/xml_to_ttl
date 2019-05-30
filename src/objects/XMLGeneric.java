package objects;

import java.util.ArrayList;
import java.util.List;

public class XMLGeneric {

	private DatabasesXML databasesXML;
	private ConfigXML configXML;
	private ViewsXML viewsXML;

	public void collectViews(String fileLine) {

		TableXML tableXML;

		if (fileLine.matches("<(\\w)*([\\s]*[\\S ]*=\\\"[\\S ]+)\\\"[\\s]*>")) {

			System.out.println("Namespace: " + fileLine);

			if (fileLine.contains("<view ")) {
				ViewXML viewXML = new ViewXML();
				viewXML.collectView(fileLine);
				viewsXML.getViewsXML().add(viewXML);

			} else if (fileLine.contains("<tables ")) {
				ViewXML viewXML = getLastView();
				viewXML.collectTables(fileLine);
			} else {
				ViewXML viewXML = getLastView();
				viewXML.getTables().collect(fileLine);
			}

		} else if (fileLine.contains("<table name=")) {
			// I really don't know why, but the first table namespace of the file isn't
			// matching with the regex.
			// So I create an If clause for it.

			TablesXML tables = getLastView().getTables();

			if (tables == null) {
				tables = new TablesXML();
				tables.setRepCol("");
				getLastView().setTables(tables);
			}

			tableXML = tables.collectTable(fileLine);

		} else if (fileLine.contains("<tables repcol=")) {
			ViewXML viewXML = getLastView();
			viewXML.collectTables(fileLine);
		}

		if (fileLine.matches("<(\\w)*([\\s]*[\\S ]*=\"[\\S ]+)\"[\\s]*\\/>")) {
			System.out.println("Namespace closed: " + fileLine);

			if (fileLine.contains("column ")) {

				tableXML = getLastView().getTables().getLastTable();

				if (tableXML != null) {
					if (tableXML.getGroups() != null) {
						tableXML.getLastGroup().collectColumn(fileLine);
					}
				}

			}

		}

		if (fileLine.matches("<(\\w)*>[\\s]+<\\/(\\w)*>")) {
			System.out.println("Values: " + fileLine);

			if (fileLine.contains("<pk>")) {

				tableXML = getLastView().getTables().getLastTable();

				tableXML.collectPk(fileLine);
			}

		}

	}

	public ViewXML getLastView() {
		if (viewsXML.getViewsXML().size() == 0) {
			System.out.println("Error in View");
			throw new NullPointerException();
		}

		ViewXML viewXML = viewsXML.getViewsXML().get(viewsXML.getViewsXML().size() - 1);
		return viewXML;
	}

	public ConfigXML getConfigXML() {
		return configXML;
	}

	public void setConfigXML(ConfigXML configXML) {
		this.configXML = configXML;
	}

	@Override
	public String toString() {
		String toString = "";

		toString += configXML + "\n" + databasesXML + "\n" + viewsXML + "\n";

		return toString;
	}

	public String printTTL() {

		String prefix = "";
		prefix += "@prefix d2rq:  <http://www.wiwiss.fu-berlin.de/suhl/bizer/D2RQ/0.1#> .";
		prefix += "@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> .";
		prefix += "@prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .";
		prefix += "@prefix xsd:   <http://www.w3.org/2001/XMLSchema#> .";

		return prefix;

	}

	public DatabasesXML getDatabasesXML() {
		return databasesXML;
	}

	public void setDatabasesXML(DatabasesXML databasesXML) {
		this.databasesXML = databasesXML;
	}

}
