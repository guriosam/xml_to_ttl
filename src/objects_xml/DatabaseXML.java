package objects_xml;

public class DatabaseXML {

	private String username;
	private String password;
	private String host;
	private String port;
	private String sid;
	private String dblink;
	private String driver;
	private String schema;
	private String database;

	public DatabaseXML(String fileLine) {
		this.database = fileLine.substring(1, fileLine.length() - 1);
	}

	/**
	 * Method responsible for receiving a line containing the values of the Database
	 * tag and translating into a Java Object
	 * 
	 * @param fileLine of XML containing a Database value
	 */
	public void collectDatabase(String fileLine) {

		if (fileLine.matches("<(\\w)*>[\\S ]+<\\/(\\w)*>")) {

			String aux = fileLine.substring(fileLine.indexOf(">") + 1);
			aux = aux.substring(0, aux.indexOf("<"));

			if (fileLine.contains("<driver")) {
				setDriver(aux);
			} else if (fileLine.contains("<host")) {
				host = aux;
			} else if (fileLine.contains("<port")) {
				port = aux;
			} else if (fileLine.contains("<database")) {
				setDatabase(aux);
			} else if (fileLine.contains("<schema")) {
				setSchema(aux);
			} else if (fileLine.contains("<user")) {
				username = aux;
			} else if (fileLine.contains("password")) {
				setPassword(aux);
			} else if (fileLine.contains("driver")) {
				setDriver(aux);
			}

		} else {
			System.out.println("Something went wrong in DatabaseXML.");
			System.out.println(fileLine);
		}

	}

	/**
	 * Converts Java Object to String TTL
	 * 
	 * @return TTL String representation of the Java Object
	 */
	public String toStringTTL() {

		String toString = "map:database a d2rq:Database ;\n";
		toString += "d2rq:jdbcDSN	jdbc:\"" + driver + "://" + host + "/" + database + "\n";
		toString += "d2rq:jdbcDriver  \"org.postgresql.Driver\" ;\n";
		toString += "d2rq:password    \"" + password + "\" ;\n";
		toString += "d2rq:username    \"" + username + "\" . \n";

		/*
		 * map:database a d2rq:Database ; d2rq:jdbcDSN
		 * "jdbc:postgresql://quiowpost.tecgraf.puc-rio.br/ERAS" ; d2rq:jdbcDriver
		 * "org.postgresql.Driver" ; d2rq:password "#Ink897$" ; d2rq:username "postgres"
		 * .
		 */

		return toString;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getDblink() {
		return dblink;
	}

	public void setDblink(String dblink) {
		this.dblink = dblink;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

}
