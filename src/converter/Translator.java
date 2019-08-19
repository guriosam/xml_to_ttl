package converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import objects_xml.ColumnXML;
import objects_xml.ConfigXML;
import objects_xml.DatabaseXML;
import objects_xml.DatabasesXML;
import objects_xml.ViewsXML;
import objects_xml.XMLGeneric;
import utils.IO;

public class Translator {

	private XMLGeneric xmlObject;

	private boolean skip;
	private boolean views;
	private boolean databases;
	private boolean config;

	public Translator() {
		xmlObject = new XMLGeneric();
	}

	/**
	 * Method responsible for converting the given XML (in a list of lines) to a
	 * String (in TTL) representation.
	 * 
	 * @param path to XML file
	 * @return toStringTTL of the XML file.
	 * @throws FileNotFoundException
	 */
	public String xmlToTTL(String databasePath, String xmlPath) throws FileNotFoundException {

		if (xmlObject != null) {
			return xmlObject.printTTL();
		}

		return xmlToObject(databasePath, xmlPath).printTTL();
	}
	
	public String xmlToRML(String databasePath, String xmlPath) throws FileNotFoundException {

		if (xmlObject != null) {
			return xmlObject.printTTL();
		}

		return xmlToObject(databasePath, xmlPath).printRML();
	}
	/**
	 * Method responsible for converting the given XML (in a list of lines) to a
	 * Java Object.
	 * 
	 * @param path to XML file
	 * @return a Java Object representation of the XML file
	 * @throws FileNotFoundException
	 */
	public XMLGeneric xmlToObject(String databasePath, String xmlPath) throws FileNotFoundException {

		File f1 = new File(databasePath);
		List<String> databaseLines = new ArrayList<>();

		// Checking if the file is still there and isn't a folder
		if (!f1.exists() || f1.isDirectory()) {
			throw new FileNotFoundException();
		}

		// collecting the fileLines
		databaseLines = IO.readAnyFile(databasePath);

		File f = new File(xmlPath);
		List<String> fileLines = new ArrayList<>();

		// Checking if the file is still there and isn't a folder
		if (!f.exists() || f.isDirectory()) {
			throw new FileNotFoundException();
		}

		// collecting the fileLines
		fileLines = IO.readAnyFile(xmlPath);

		skip = false;

		collectXMLInfo(databaseLines);
		collectXMLInfo(fileLines);

		return xmlObject;

	}

	private void collectXMLInfo(List<String> fileLines) {

		
		int i = 0;
		for (String fileLine : fileLines) {
			i++;
			try {
				

				fileLine = fileLine.replace(" =", "=");
				fileLine = fileLine.replace("= ", "=");

				// Checking if is a line valid for translation
				if (!validation(fileLine)) {
					continue;
				}

				if (fileLine.contains("encoding")) {
					String encoding = getEncoding(fileLine);
					xmlObject.setEncoding(encoding);
					continue;
				}

				// Removing whitespaces at the beginning
				fileLine = fileLine.substring(fileLine.indexOf("<"));

				// Checking which namespace the translator is
				if (checkLevel(fileLine)) {
					continue;
				}

				// If is in the config namespace
				if (config) {
					xmlObject.getConfigXML().collectConfig(fileLine);
				} else if (databases) {
					System.out.println(fileLine);
					if (fileLine.matches("<(\\w)*>")) {
						if (xmlObject.getDatabasesXML() == null) {
							xmlObject.setDatabasesXML(new DatabasesXML());
						}
						xmlObject.getDatabasesXML().getDatabases().add(new DatabaseXML(fileLine));
					} else if (fileLine.matches("<[/](\\w)*>(\\s)*")) {
						continue;
					} else {
						xmlObject.getDatabasesXML().getLastDatabase().collectDatabase(fileLine);
					}
				} else if (views) {
					// If is in the Views namespace
					xmlObject.collectViews(fileLine);
				}

			} catch (Exception e) {
				System.out.println(i + ":" + fileLine);
				e.printStackTrace();
			}

		}

	}

	/**
	 * Method responsible for checking the current level of the translator, if is at
	 * view, table, column level Basically helps the translator to navigates in the
	 * XML and construct the Java Object
	 * 
	 * @param fileLine
	 * @return boolean if the current line shoud be skipped
	 */
	private boolean checkLevel(String fileLine) {

		if (fileLine.matches("<config>")) {
			config = true;
			xmlObject.setConfigXML(new ConfigXML());
			return true;
		} else if (fileLine.matches("<[/]config>")) {
			config = false;
			return true;
		}

		if (fileLine.matches("<databases>")) {
			databases = true;
			xmlObject.setDatabasesXML(new DatabasesXML());
			return true;
		} else if (fileLine.matches("<[/]databases>")) {
			databases = false;
			return true;
		}

		if (fileLine.matches("<views>")) {
			xmlObject.setViewsXML(new ViewsXML());
			views = true;
			return true;
		} else if (fileLine.matches("<[/]views>")) {
			views = false;
			return true;
		}

		if (fileLine.matches("<group>")) {
			xmlObject.getLastView().getTables().getLastTable().collectGroup(fileLine);
			return true;
		} else if (fileLine.matches("<[/]group>")) {
			return true;
		}

		if (fileLine.matches("<decode>")) {
			ColumnXML columnXML = xmlObject.getLastView().getTables().getLastTable().getLastGroup().getLastColumn();

			if (columnXML != null) {
				columnXML.collectDecode(fileLine);
			}
			return true;
		} else if (fileLine.matches("<[/]decode>")) {
			return true;
		}

		return false;

	}

	/**
	 * Method responsible for checking if the current line is a valid line for
	 * translation Remove comments Remove documents namespace Remove columns with
	 * exclude tag Remove empty lines
	 * 
	 * @param fileLine
	 * @return a boolean representing if is a valid line or not
	 */
	private boolean validation(String fileLine) {

		if (fileLine.contains("<!")) {
			skip = true;
		}

		if (fileLine.contains("-->")) {
			skip = false;
			return false;
		}

		if (fileLine.contains("exclude=\"true\"")) {
			return false;
		}

		if (fileLine.contains("<documents")) {
			skip = true;
		}

		if (fileLine.contains("<file")) {
			skip = true;
		}

		if (fileLine.contains("</file")) {
			skip = false;
			return false;
		}

		if (fileLine.contains("</documents")) {
			skip = false;
			return false;
		}

		if (fileLine.trim().length() < 3) {
			return false;
		}

		if (skip) {
			return false;
		}

		return true;
	}

	/**
	 * Method responsible for collecting the encoding present in the XML line
	 * 
	 * @param line containing the encoding information
	 * @return
	 */
	private String getEncoding(String line) {
		String encoding = line.substring(line.indexOf("encoding") + 10);
		encoding = encoding.substring(0, encoding.indexOf("\""));
		return line;
	}
}
