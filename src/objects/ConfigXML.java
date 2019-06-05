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
				prefix = aux;
			} else if (fileLine.contains("<database")) {
				database = aux;
			} else if (fileLine.contains("<origin")) {
				origin = aux;
			} else if (fileLine.contains("<bnode")) {
				bnode = aux;
			} else if (fileLine.contains("<separator")) {
				separator = aux;
			}

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
		
		String config = "  <config>\n";
		
		if(version != null){
			config += "    <version>" + version + "</version>\n";
		}
		
		if(prefix != null){
			config += "    <prefix>" + prefix + "</prefix>\n";
		}
		if(origin != null){
			config += "    <origin>" + origin + "</origin>\n";
		}
		if(database != null){
			config += "    <database>" + database + "</database>\n";
		}
		if(separator != null){
			config += "    <separator>" + separator + "</separator>\n";
		}
		if(bnode != null){
			config += "    <bnode>" + bnode + "</bnode>\n";
		}
		
		config += "  </config>\n";
		
		return config;
	}
	
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

}
