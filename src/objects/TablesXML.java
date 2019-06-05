package objects;

import java.util.ArrayList;
import java.util.List;

public class TablesXML {

	private List<TableXML> tables;
	private String repCol;
	private String viewName;

	//Auxiliar fields
	private TableXML tableXML;
	private GroupXML groupXML;
	private ColumnXML columnXML;
	private JoinXML joinXML;
	private DecodeXML decodeXML;

	public TablesXML(String viewName) {
		tables = new ArrayList<>();
		this.viewName = viewName;
	}

	public TableXML collectTable(String fileLine) {
		// <table name="jobs" owner="public">

		TableXML table = new TableXML(viewName);

		if (fileLine.contains("name=")) {
			String aux = fileLine.substring(fileLine.indexOf("name=\"") + 6);
			String name = aux.substring(0, aux.indexOf("\""));

			table.setName(name);
		}

		if (fileLine.contains("owner=")) {
			String aux = fileLine.substring(fileLine.indexOf("owner=\"") + 7);
			String owner = aux.substring(0, aux.indexOf("\""));

			table.setOwner(owner);
		}

		tables.add(table);

		return table;

	}

	public void collect(String fileLine) {

		if (fileLine.contains("<table ")) {
			collectTable(fileLine);
		}

		if (fileLine.contains("<group ")) {

			tableXML = getLastTable();

			if (tableXML != null) {
				
				groupXML = tableXML.collectGroup(fileLine);
			}

		}

		if (fileLine.contains("<column ")) {

			tableXML = getLastTable();

			if (tableXML != null) {
				
				groupXML = tableXML.getLastGroup();
				columnXML = groupXML.collectColumn(fileLine);
			}
		}

		if (fileLine.contains("<join ")) {

			tableXML = getLastTable();
			columnXML = tableXML.getLastGroup().getLastColumn();
			

			if (columnXML != null) {
				joinXML = columnXML.collectJoinView(fileLine);
			}
		}

		if (fileLine.contains("<decode ")) {
			if (columnXML != null) {
				columnXML.collectDecode(fileLine);
			}
		}

	}

	public List<TableXML> getTables() {
		return tables;
	}

	public void setTables(List<TableXML> tables) {
		this.tables = tables;
	}

	public String getRepCol() {
		return repCol;
	}

	public void setRepCol(String repCol) {
		this.repCol = repCol;
	}

	public TableXML getLastTable() {
		if (tables.size() == 0) {
			System.out.println("Error in Tables");
			throw new NullPointerException();
		}

		TableXML tableXML = tables.get(tables.size() - 1);
		return tableXML;
	}

	@Override
	public String toString() {
		String tabl = "      <tables";

		if (repCol != null && !repCol.equals("")) {
			tabl += " repcol=\"" + repCol + "\"";
		}

		tabl += ">\n";

		for (TableXML table : tables) {
			tabl += table + "\n";
		}

		tabl += "      </tables>";

		return tabl;
	}
	
	public String toStringTTL() {
		
		String ttl = "";
		
		/*
		map:jobs__label  a              d2rq:PropertyBridge ;
        d2rq:belongsToClassMap  map:jobs ;
        d2rq:pattern            "jobs_@@jobs.id@@" ;
        d2rq:property           rdfs:label .
		*/
		
		ttl += "map:" + viewName + "__label a 	d2rq:PropertyBridge ;\n";
		ttl += "d2rq:belongsToClassMap 		map:" + viewName + " ;\n";
		ttl += "d2rq:pattern		\"" + "@@" + viewName + "." + repCol + " ;\n";
		ttl += "d2rq:property		rdfs:label .\n";
		
		
		
		return ttl;
	}

}
