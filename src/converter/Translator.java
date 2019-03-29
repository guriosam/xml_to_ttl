package converter;

import java.util.HashMap;
import java.util.List;

import objects.Schema;

public class Translator {

	private Schema xmlSchema;

	public void xmlToObject(List<String> fileLines) {

		xmlSchema = new Schema();

		boolean first = false;

		String aux = "";

		for (String fileLine : fileLines) {

			if (fileLine.length() < 3) {
				continue;
			}

			// Checking if is the line of the namespaces
			if (fileLine.contains("xmlns")) {
				first = true;
			}

			// Collecting the encoding for future needs
			if (fileLine.contains("encoding")) {
				String encoding = getEncoding(fileLine);
				xmlSchema.setEncoding(encoding);
				continue;
			}

			// The first line contains the namespaces for making the @prefix.
			if (first) {
				aux += fileLine;

				// Checking if the namespaces ended or there is another line with them
				if (fileLine.contains(">")) {
					collectNamespaces(aux);
					first = false;
				}

			} else {
				// if isn't the first line or the encoding line
				// there are three other options:
				// 1. XML Attributes (resouces, about ..)
				// 2. XML Values (identifiers, labels ..)
				// 3. XML closing namespace

				// Removing whitespaces at the beginning
				fileLine = fileLine.substring(fileLine.indexOf("<"));

				// checking if it's a closing namespace
				if (fileLine.startsWith("</")) {

				}

				// checking if it's a value
				else if (fileLine.contains("</")) {

				}

				// checking if it's a attribute
				else if (fileLine.contains("=")) {
					
				}

			}

		}
		// xmlSchema.printPrefix();
	}

	private void collectNamespaces(String fileLine) {
		String[] namespaces = fileLine.split(" ");

		for (String namespace : namespaces) {
			if (namespace.contains("xmlns")) {
				String ns = namespace.substring(namespace.indexOf(":") + 1);
				ns = ns.replace("\"", "");
				ns = ns.replace(">", "");

				String[] prefix = ns.split("=");
				if (prefix.length == 2) {
					xmlSchema.putPrefix(prefix[1], prefix[0]);
				}
			}
		}
	}

	public void objectToTTL(String fileContent) {

	}

	private String getEncoding(String line) {
		String encoding = line.substring(line.indexOf("encoding") + 10);
		encoding = encoding.substring(0, encoding.indexOf("\""));
		System.out.println(encoding);
		return encoding;
	}
}
