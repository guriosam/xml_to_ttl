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
	
	private String domain;

	public ColumnXML() {
		setJoins(new ArrayList<>());
		setDecodes(new ArrayList<>());
	}

	public JoinXML collectJoinView(String fileLine) {

		JoinXML join = new JoinXML();

		if (fileLine.contains("view=")) {
			String aux = fileLine.substring(fileLine.indexOf("view=\"") + 6);
			String view = aux.substring(0, aux.indexOf("\""));

			join.setView(view);
		}

		joins.add(join);

		return join;

	}

	public DecodeXML collectDecode(String fileLine) {

		DecodeXML decode = new DecodeXML();

		String expr = "";

		if (fileLine.contains("expr=")) {
			String aux = fileLine.substring(fileLine.indexOf("expr=\"") + 6);
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
	
	public DecodeXML getLastDecode() {
		if (decodes.size() == 0) {
			System.out.println("Error in Decode");
			throw new NullPointerException();
		}

		DecodeXML dec = decodes.get(decodes.size() - 1);
		return dec;
	}

	@Override
	public String toString() {
		// <column name="" label="" indexing=""/>
		String column = "            <column";

		if (name != null && !name.equals("")) {
			column += " name=\"" + name + "\"";
		}
		if (label != null && !label.equals("")) {
			column += " label=\"" + label + "\"";
		}
		if (indexing != null && !indexing.equals("")) {
			column += " identifier=\"" + indexing + "\"";
		}

		boolean f = true;

		if (joins.size() != 0) {

			column += ">\n";

			for (JoinXML join : joins) {
				column += join + "\n";
			}

			column += "            </column>";

			f = false;
		}

		if (decodes.size() != 0) {

			column += ">\n";

			for (DecodeXML d : decodes) {
				column += d + "\n";
			}

			column += "            </column>";

			f = false;
		}

		if (f) {
			column += "/>";
		}

		return column;
	}

	
	public String toStringTTL() {
		
		String ttl = "";
		
		//( anp:BLOCO_OFERTADO#SETOR rdfs:domain   anp:BLOCO_OFERTADO )
		// ( anp:BLOCO_OFERTADO#SETOR     rdfs:label      "Setor" )
		// ( anp:BLOCO_OFERTADO#SETOR     :indexing       "true" )
		
		ttl += domain + ":" + "nomeDaView#" +  name  + " rdfs:domain " + domain + ":" + "nomedaView;\n";
		ttl += domain + ":" + "nomeDaView#" + name + " rdfs:label \"" + label + "\";\n";
		ttl += domain + ":" + "nomeDaView#" + name + " rdfs:indexing " + indexing + "\";\n";
				
		return ttl;
	}
}
