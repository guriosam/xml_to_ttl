/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects_rml;

import java.io.PrintWriter;

/**
 * Represent the LogicalSource property of the RML file
 * 
 */
public class LogicalSource {//implements IWritable {

	final String source;
	final String tableName;
	final String owner;

	/**
	 * is Constructor of LogicalSource object
	 * 
	 * @param source    - the DatabaseSource identifier
	 * @param tableName - the table name in the dataset
	 * @param owner     - the schema name in database
	 */
	public LogicalSource(String source, String tableName, String owner) {
		this.source = source;
		this.tableName = tableName;
		this.owner = owner;
	}

/*
	@Override
	public void write(PrintWriter printer) {
		printer.println("\t rml:logicalSource [");
		printer.println(String.format("\t\t rml:source <%s>;", source));
		printer.println("\t\t rr:sqlVersion rr:SQL2008;");
		printer.println(String.format("\t\t rr:tableName \"%1s.%2s\"]; ", owner, tableName));
		printer.println(" ");
	}
*/
}
