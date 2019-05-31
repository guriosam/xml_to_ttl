package objects;

public class JoinXML {

	private String view;
	private String columnFrom;
	private String columnTo;

	public String getColumnFrom() {
		return columnFrom;
	}

	public void setColumnFrom(String columnFrom) {
		this.columnFrom = columnFrom;
	}

	public String getColumnTo() {
		return columnTo;
	}

	public void setColumnTo(String columnTo) {
		this.columnTo = columnTo;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public void collectColumn(String fileLine) {

		if (fileLine.contains("from=")) {
			String aux = fileLine.substring(fileLine.indexOf("from=\"") + 6);
			String from = aux.substring(0, aux.indexOf("\""));

			columnFrom = from;
		}

		if (fileLine.contains("to=")) {
			String aux = fileLine.substring(fileLine.indexOf("to=\"") + 4);
			String to = aux.substring(0, aux.indexOf("\""));

			columnTo = to;
		}

	}

	@Override
	public String toString() {
		// <join view="BACIA">
		// <condition from="BACI_CD_BACIA" to="BACI_CD_BACIA"/>
		// </join>
		String join = "              <join";
		
		if (view != null && !view.equals("")) {
			join += " view=\"" + view + "\">\n";
		}
		
		join += "                <condition";

		if (columnFrom != null && !columnFrom.equals("")) {
			join += " from=\"" + columnFrom + "\"";
		}
		if (columnTo != null && !columnTo.equals("")) {
			join += " to=\"" + columnTo + "\"/>\n";
		}
		
		join += "              </join>";

		return join;
	}

}
