package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XMLObject {
	
	private String namespace;
	private List<HashMap<String, String>> fields;
	private List<XMLObject> innerXML;
	private String value;
	
	public XMLObject() {
		fields = new ArrayList<>();
		innerXML = new ArrayList<>();
	}
	
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public List<HashMap<String, String>> getFields() {
		return fields;
	}
	public void setFields(List<HashMap<String, String>> fields) {
		this.fields = fields;
	}
	public List<XMLObject> getInnerXML() {
		return innerXML;
	}
	public void setInnerXML(List<XMLObject> innerXML) {
		this.innerXML = innerXML;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	

}
