package objects;

import java.util.ArrayList;
import java.util.List;

public class ColumnXML {

	private String name;
	private String label;
	private Boolean indexing;
	private Boolean untagged;
	private List<JoinXML> joins;
	private List<DecodeXML> decodes;

	public ColumnXML() {
		setJoins(new ArrayList<>());
		setDecodes(new ArrayList<>());
	}

	public JoinXML collectJoinView(String fileLine) {

		JoinXML join = new JoinXML();

		if (fileLine.contains("view=")) {
			String aux = fileLine.substring(fileLine.indexOf("view=\"") + 8);
			String view = aux.substring(0, aux.indexOf("\""));

			join.setView(view);
		}

		joins.add(join);

		return join;

	}

	public DecodeXML collectDecode(String fileLine) {

		DecodeXML decode = new DecodeXML();

		String expr = "null";

		if (fileLine.contains("expr=")) {
			String aux = fileLine.substring(fileLine.indexOf("expr=\"") + 8);
			expr = aux.substring(0, aux.indexOf("\""));
		}

		decode.setExpr(expr);
		
		decodes.add(decode);

		return decode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getIndexing() {
		return indexing;
	}

	public void setIndexing(Boolean indexing) {
		this.indexing = indexing;
	}

	public Boolean getUntagged() {
		return untagged;
	}

	public void setUntagged(Boolean untagged) {
		this.untagged = untagged;
	}

	public List<JoinXML> getJoins() {
		return joins;
	}

	public void setJoins(List<JoinXML> joins) {
		this.joins = joins;
	}

	public List<DecodeXML> getDecodes() {
		return decodes;
	}

	public void setDecodes(List<DecodeXML> decodes) {
		this.decodes = decodes;
	}
	
	public JoinXML getLastJoin() {
		if (joins.size() == 0) {
			System.out.println("Error in Join");
			throw new NullPointerException();
		}

		JoinXML join = joins.get(joins.size() - 1);
		return join;
	}

}
