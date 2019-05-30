package objects;

public class ConfigXML {

	private String prefix;
	private String origin;
	private String separator;
	private String bnode;
	private String version;
	private String database;

	public void collectConfig(String fileLine) {

		if (fileLine.matches("<(\\w)*>[\\S ]+<\\/(\\w)*>")) {

			String aux = fileLine.substring(fileLine.indexOf(">") + 1);
			aux = aux.substring(0, aux.indexOf("<"));

			if (fileLine.contains("<version")) {
				version = aux;
			} else if (fileLine.contains("<prefix")) {
				version = aux;
			} else if (fileLine.contains("<database")) {
				database = aux;
			} else if (fileLine.contains("<origin")) {
				origin = aux;
			} else if (fileLine.contains("<bnode")) {
				bnode = aux;
			} else if (fileLine.contains("<separator")) {
				separator = aux;
			}

			System.out.println("Values: " + fileLine);

		} else {
			System.out.println("Something went wrong in ConfigXML.");
		}

	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getBnode() {
		return bnode;
	}

	public void setBnode(String bnode) {
		this.bnode = bnode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return prefix + "/" + origin + "/" + separator + bnode + version;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

}
