package objects;

public class XMLGeneric {

	private String encoding;
	private DatabasesXML databasesXML;
	private ConfigXML configXML;
	private ViewsXML viewsXML;

	public void collectViews(String fileLine) {

		TableXML tableXML;
		ViewXML viewXML;

		if (fileLine.matches("<(\\w)*([\\s]*[\\S ]*=\\\"[\\S ]+)\\\"[\\s]*>")) {
			// System.out.println("Namespace: " + fileLine);

			if (fileLine.contains("<view ")) {
				viewXML = new ViewXML(configXML.getPrefix());
				viewXML.collectView(fileLine);
				viewsXML.getViewsXML().add(viewXML);

			} else if (fileLine.contains("<tables ")) {
				viewXML = getLastView();
				viewXML.collectTables(fileLine);
			} else {
				viewXML = getLastView();
				viewXML.getTables().collect(fileLine);
			}

		} else if (fileLine.contains("<table name=")) {

			TablesXML tables = getLastView().getTables();

			if (tables == null) {
				tables = new TablesXML(getLastView().getName());
				tables.setRepCol("");
				getLastView().setTables(tables);
			}

			tableXML = tables.collectTable(fileLine);

		} else if (fileLine.contains("<tables repcol=")) {
			viewXML = getLastView();
			viewXML.collectTables(fileLine);
		} else if (fileLine.matches("<(\\w)*([\\s]*[\\S ]*=\"[\\S ]+)\"[\\s]*\\/>")) {
			// System.out.println("Namespace closed: " + fileLine);

			tableXML = getLastView().getTables().getLastTable();

			if (fileLine.contains("column ")) {

				if (tableXML != null) {
					if (tableXML.getGroups() != null) {
						tableXML.getLastGroup().collectColumn(fileLine);
					}
				}

			}

			if (fileLine.contains("<condition ")) {
				JoinXML joinXML = tableXML.getLastGroup().getLastColumn().getLastJoin();
				if (joinXML != null) {
					joinXML.collectColumn(fileLine);
				}
			}

			if (fileLine.contains("<entry ")) {

				ColumnXML columnXML = tableXML.getLastGroup().getLastColumn();

				if (columnXML != null) {
					columnXML.getLastDecode().collectEntry(fileLine);
				}
			}

		} else if (fileLine.matches("<(\\w)*>[\\S ]+<\\/(\\w)*>")) {
			// System.out.println("Values: " + fileLine);

			if (fileLine.contains("<pk>")) {

				tableXML = getLastView().getTables().getLastTable();
				tableXML.collectPk(fileLine);

			} else if (fileLine.contains("<pseudoPk>")) {

				tableXML = getLastView().getTables().getLastTable();

				tableXML.collectPseudoPk(fileLine);
			} else if (fileLine.contains("<where>")) {

				tableXML = getLastView().getTables().getLastTable();
				tableXML.collectWhere(fileLine);
			}

		} else if (fileLine.contains("column ")) {
			tableXML = getLastView().getTables().getLastTable();

			if (tableXML != null) {
				if (tableXML.getGroups() != null) {
					tableXML.getLastGroup().collectColumn(fileLine);
				}
			}
		} else if (fileLine.contains("view ")) {

			viewXML = new ViewXML(configXML.getPrefix());
			viewXML.collectView(fileLine);
			viewsXML.getViewsXML().add(viewXML);

		} else if (fileLine.contains("<pseudoPk>")) {

			tableXML = getLastView().getTables().getLastTable();
			tableXML.collectPseudoPk(fileLine);

		}

	}

	public ViewsXML getViewsXML() {
		return viewsXML;
	}

	public void setViewsXML(ViewsXML viewsXML) {
		this.viewsXML = viewsXML;
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

		String toString = "<mapping>\n";

		//if (configXML != null) {
		//	toString += configXML + "\n";
		//}
		//if (databasesXML != null) {
		//	toString += databasesXML + "\n";
		//}

		//if (viewsXML != null) {
		//	toString += viewsXML + "\n";
		//}

		toString += "</mapping>";

		return encoding + "\n" + toString;
	}

	public String printTTL() {

		String prefix = "";
		prefix += "@prefix d2rq:  <http://www.wiwiss.fu-berlin.de/suhl/bizer/D2RQ/0.1#> .";
		prefix += "@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> .";
		prefix += "@prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .";
		prefix += "@prefix xsd:   <http://www.w3.org/2001/XMLSchema#> .";

		/*
		@prefix vocab: <http://eras.tecgraf.puc-rio.br/> .
		@prefix jdbc:  <http://d2rq.org/terms/jdbc/> .
		@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> .
		@prefix map:   <http://eras.tecgraf.puc-rio.br/map#> .
		*/

		return prefix;

	}

	public DatabasesXML getDatabasesXML() {
		return databasesXML;
	}

	public void setDatabasesXML(DatabasesXML databasesXML) {
		this.databasesXML = databasesXML;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
