/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects_rml;

/**
 * Repressent the predicateObjectMap property of RML
 */
public class PredicateObjectMap {// implements IWritable {

	String clazz;
	String property;
	//ObjectMap objectMap;

	/**
	 * Constructor of PredicateObjectMap object
	 * 
	 * @param clazz    - name of the property domain class
	 * @param property - the property name
	 */
	public PredicateObjectMap(String clazz, String property) {
		this.clazz = clazz;
		this.property = property;
	}

	/**
	 * Construct the predicate property
	 * 
	 * @return the string the string that is the concatenation of the names of the
	 *         class and the property
	 */
	String getPredicate() {
		return String.format("%1s#%2s", clazz, property);
	}

	/**
	 * @see IWritable
	@Override
	public void write(PrintWriter printer) {
		printer.println("\t rr:predicateObjectMap [");
		printer.println(String.format("\t\t rr:predicate <%s>;", getPredicate()));
		objectMap.write(printer);
		printer.print("]");
	}
	*/

}
