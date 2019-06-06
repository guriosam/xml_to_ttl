package objects;

import java.util.ArrayList;
import java.util.List;

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

		// for (ViewXML view : viewsXML) {
		// views += view + "\n";
		// }

		views += "  </views>\n";

		return views;
	}

	public String toStringTTL() {

		String ttl = "";

		for (ViewXML v : viewsXML) {
			ttl += v.toStringTTL();
		}
		ttl += "\n";

		return ttl;
	}

}
