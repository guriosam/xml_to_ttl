package objects;

import java.util.List;

public class DecodeXML {

	private String expr;
	private List<EntryXML> entries;

	public void collectEntry(String fileLine) {

		EntryXML entry = new EntryXML();

		String search = "";
		String result = "";

		if (fileLine.contains("search=")) {
			String aux = fileLine.substring(fileLine.indexOf("search=\"") + 8);
			search = aux.substring(0, aux.indexOf("\""));
		}

		entry.setSearch(search);

		if (fileLine.contains("result=")) {
			String aux = fileLine.substring(fileLine.indexOf("result=\"") + 8);
			result = aux.substring(0, aux.indexOf("\""));
		}

		entry.setResult(result);

	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public List<EntryXML> getEntries() {
		return entries;
	}

	public void setEntries(List<EntryXML> entries) {
		this.entries = entries;
	}

}
