package objects;

public class DatabaseXML {

	private String username;
	private String password;
	private String host;
	private String port;
	private String sid;
	private String dblink;
	
	public void collectDatabase(String fileLine) {
		// TODO Auto-generated method stub
		
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

	@Override
	public String toString() {
		String print = "";

		print += "map:" + " a " + "d2rq:Database ;";
		print += "d2rq:jdbcDSN\t" + "\"" + "\" ;";
		print += "d2rq:jdbcDriver  \"org.postgresql.Driver\" ;";
		print += "d2rq:password\t\"" + password + "\" ;";
		print += "username\t\"" + username + "\" ;";

		return print;
	}


}
