package objects;

import java.util.HashMap;

public class Schema {

	private String encoding;
	private HashMap<String, String> prefix;
	private HashMap<String, InnerSchema> schema;
	
	public Schema() {
		this.schema = new HashMap<>();
		this.prefix = new HashMap<>();
	}

	public void addSchema(String ns, InnerSchema inner) {
		if(schema == null) {
			System.out.println("The target object you are trying to add into is null.");
			throw new NullPointerException();
		}
		schema.put(ns, inner);
	}

	public InnerSchema getSchema(String ns) {
		if (schema == null) {
			System.out.println("The target object you are trying to get from is null.");
			throw new NullPointerException();
		}
		return schema.get(ns);
	}
	
	public void putPrefix(String ns, String value) {
		if(prefix == null) {
			System.out.println("The target object you are trying to add into is null.");
			throw new NullPointerException();
		}
		prefix.put(ns, value);
	}

	public String getPrefix(String ns) {
		if (prefix == null) {
			System.out.println("The target object you are trying to get from is null.");
			throw new NullPointerException();
		}
		return prefix.get(ns);
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public void printPrefix() {
		System.out.println(prefix);
	}

	public void createSchema(String auxSchema) {
		
	}
	
	
}
