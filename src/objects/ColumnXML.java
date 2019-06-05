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

	private String viewName;

	public ColumnXML(String viewName) {
		setJoins(new ArrayList<>());
		setDecodes(new ArrayList<>());
		this.viewName = viewName;
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

		if (joins.size() == 0 && decodes.size() == 0) {
			ttl += "map:" + viewName + "_" + name + "	a	d2rq:PropertyBridge ;\n";
			ttl += "d2rq:belongsToClassMap		map:" + viewName + " ;\n";
			ttl += "d2rq:column                   \"" + viewName + "." + name + "\" ;";
			ttl += "d2rq:property                 vocab:" + viewName + "_" + name + " ;";
			ttl += "d2rq:propertyDefinitionLabel	\"" + label + "\".\n";
		}

		if (joins.size() > 0) {
			/*
			 * map:job_status_history_job_id__ref a d2rq:PropertyBridge ;
			 * d2rq:belongsToClassMap map:job_status_history ; 
			 * d2rq:join "job_status_history.job_id <= jobs.id" ;
			 * d2rq:propertyDefinitionLabel "Job" ;
			 * d2rq:property vocab:job_status_history_job ;
			 * d2rq:refersToClassMap map:jobs .
			 */
			
			/*
			map:PaperConference a d2rq:PropertyBridge;
		    d2rq:belongsToClassMap map:Paper;
		    d2rq:property :journalIssue;
		    d2rq:refersToClassMap map:JournalIssue;
		    d2rq:join "Papers.Journal => Journal.JournalID";
		    d2rq:join "Papers.Issue => Journal.IssueID";
		    .
		    */


			for (JoinXML join : joins) {
				ttl += "map:" + viewName + "_" + name + "__ref		a	d2rq:PropertyBridge ;\n";
				ttl += "d2rq:belongsToClassMap		map:" + viewName + " ;\n";
				//loop conditions
				ttl += "d2rq:join                   \"" + viewName + "." + join.getColumnFrom() + "<=" + join.getView() + "."
						+ join.getColumnTo() + "\" ;";
				//end loop conditions
				ttl += " d2rq:propertyDefinitionLabel \"" + label + "\" ;";
				ttl += "d2rq:property                 vocab:" + viewName + "_" + name + " ;";
				ttl += "d2rq:refersToClassMap	map:" + join.getView() + ".\n";
			}

		}
		
		/*
			map:authorName a d2rq:PropertyBridge;
		    d2rq:belongsToClassMap map:Papers;
		    d2rq:property :authorName;
		    d2rq:column "Persons.Name";
		    d2rq:join "Papers.PaperID <= Rel_Person_Paper.PaperID";
		    d2rq:join "Rel_Person_Paper.PersonID => Persons.PerID";
		    d2rq:datatype xsd:string;
		    d2rq:propertyDefinitionLabel "name"@en;
		    d2rq:propertyDefinitionComment "Name of an author."@en;
		    .
				
		*/
		if(decodes.size() > 0) {
			
		}

		return ttl;
	}
}
