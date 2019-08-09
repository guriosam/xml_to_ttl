package objects_xml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cbvs
 * Representation of a Views tag in the XML File
 */
public class ViewsXML {

	private List<ViewXML> viewsXML;

	public ViewsXML() {
		
		setViewsXML(new ArrayList<>());
	}

	public List<ViewXML> getViewsXML() {
		return viewsXML;
	}

	public void setViewsXML(List<ViewXML> viewsXML) {
		this.viewsXML = viewsXML;
	}

	@Override
	public String toString() {
		String views = "  <views>\n";

		for (ViewXML view : viewsXML) {
			views += view + "\n";
		}

		views += "  </views>\n";

		return views;
	}

	/**
	 * Converts Java Object to String TTL
	 * @return TTL String representation of the Java Object
	 */
	public String toStringTTL() {

		String ttl = "";

		for (ViewXML v : viewsXML) {
			ttl += v.toStringTTL();
		}
		
		ttl += "\n";

		return ttl;
	}

	public String toStringRML() {
		
		String viewsRML = "";
		
		String uri = "";
		if(viewsXML != null && viewsXML.size() != 0) {
			uri = viewsXML.get(0).getPrefix();
		}
		
		viewsRML += "<#ViewsMapping>\n";
		viewsRML += "  rml:logicalSource [\n";
		viewsRML += "    rml:source \"" + uri + "\";\n";
		viewsRML += "    rml:referenceFormulation ql:xPath;\n";
		viewsRML += "    rml:iterator \"/views/view\";\n";
		viewsRML += "    ];\n";
		
		
		for(ViewXML v : viewsXML) {
			viewsRML += v.toStringRML();
		}
		
		return viewsRML;
	}

}
