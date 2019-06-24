package converter;

import java.util.List;

import objects.ColumnXML;
import objects.ConfigXML;
import objects.DatabasesXML;
import objects.TTLSchema;
import objects.ViewsXML;
import objects.XMLGeneric;

public class Translator {

	private XMLGeneric xmlObject;

	private boolean skip;
	private boolean views;
	private boolean databases;
	private boolean config;

	/**
	 * Method responsible for converting the given XML (in a list of lines) to a
	 * Java Object.
	 * 
	 * @param fileLines of the given XML file
	 * @return toStringXML of the Java Object (for comparison)
	 */
	public String xmlToObject(List<String> fileLines) {

		xmlObject = new XMLGeneric();

		skip = false;

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

				// Collecting the encoding for future needs
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
					// If is in the database namespace
					xmlObject.getDatabasesXML().getLastDatabase().collectDatabase(fileLine);
				} else if (views) {
					// If is in the Views namespace
					xmlObject.collectViews(fileLine);
				}

			} catch (Exception e) {
				System.out.println(i + ":" + fileLine);
				e.printStackTrace();
			}

		}

		System.out.println(xmlObject.printTTL());

		return "";

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
		} else if (fileLine.matches("<[/]dabatases>")) {
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
