package objects;

public class EntryXML {

	private String search;
	private String result;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		String entry = "                <entry";

		if (search != null && !search.equals("")) {
			entry += " search=\"" + search + "\"";
		}

		if (result != null && !result.equals("")) {
			entry += " result=\"" + result + "\"";
		}

		entry += "/>";

		return entry;
	}

}
