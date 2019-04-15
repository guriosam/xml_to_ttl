package converter;

import java.util.List;

import objects.TTLSchema;
import objects.XMLObject;

public class Translator {

	private TTLSchema xmlSchema;
	private XMLObject xmlObject;

	public String xmlToObject(List<String> fileLines) {

		xmlObject = new XMLObject();

		boolean comment = false;

		for (String fileLine : fileLines) {

			try {

				if (fileLine.contains("<!")) {
					comment = true;
				}

				if (fileLine.contains("-->")) {
					comment = false;
					continue;
				}

				if (comment) {
					continue;
				}

				if (fileLine.contains("exclude=\"true\"")) {
					continue;
				}

				if (fileLine.length() < 3) {
					continue;
				}

				// Collecting the encoding for future needs
				// if (fileLine.contains("encoding")) {
				// String encoding = getEncoding(fileLine);
				// xmlSchema.setEncoding(encoding);
				// continue;
				// }

				// Removing whitespaces at the beginning
				fileLine = fileLine.substring(fileLine.indexOf("<"));

				if (fileLine.matches("<[A-Z]*[a-z]*>")) {
					// System.out.println("Opening: " + fileLine);
					
				} else if (fileLine.matches("<[/][A-Z]*[a-z]*>")) {
					// System.out.println("Closing: " + fileLine);

				}

				if (fileLine.matches("<(\\w)*([\\s]*[\\S ]*=\"[\\S ]+)\"[\\s]*>")) {
					//System.out.println("Namespace: " + fileLine);
				}

				if (fileLine.matches("<(\\w)*>[\\S ]+<\\/(\\w)*>")) {
					//System.out.println("Values: " + fileLine);
				}

			
			} catch (Exception e) {
				System.out.println(fileLine);
				e.printStackTrace();
			}
		}

		return "";

	}

	public String xmlToTTL(List<String> fileLines) {

		xmlSchema = new TTLSchema();

		boolean first = false;
		int level = -1;

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
				if (fileLine.trim().startsWith("</")) {
					level = -1;
					xmlSchema.addLine("\t.\n");
				}

				// checking if it's a value
				else if (fileLine.contains("</")) {
					xmlSchema.addLine('\t' + collectValue(fileLine));
				}

				// checking if it's a attribute
				else if (fileLine.contains("=")) {
					String temp = collectResource(fileLine);
					String[] parts = temp.split(" ");

					if (fileLine.trim().endsWith("/>")) {
						xmlSchema.addLine('\t' + temp);
					} else if (fileLine.contains("about=")) {
						level++;
						if (level == 0) {
							xmlSchema.addLine(parts[0] + " a " + parts[1]);
						} else if (level > 0) {
							xmlSchema.addLine("a " + parts[1]);
						}
					}
				}
			}

		}

		return xmlSchema.toString();

	}

	private String collectValue(String fileLine) {

		fileLine = fileLine.substring(1);

		try {
			String namespace = fileLine.substring(0, fileLine.indexOf(">"));
			String value = fileLine.substring(fileLine.indexOf(">") + 1, fileLine.indexOf("<"));

			return namespace + " \"" + value + "\"";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	private String collectResource(String fileLine) {

		String[] lineSplitted = fileLine.split(" ");

		try {

			if (lineSplitted.length == 2) {
				String firstPart = lineSplitted[0];
				String secondPart = lineSplitted[1];

				// removing '<' character at begging
				firstPart = firstPart.substring(1);

				// removing > or /> at end
				secondPart = secondPart.substring(0, secondPart.lastIndexOf("\"") + 1);

				String object = "";
				String namespace = "";
				String resource = "";
				String prefix = "";

				namespace = secondPart.substring(0, secondPart.indexOf(":"));

				if (secondPart.contains("#")) {
					object = secondPart.substring(secondPart.indexOf("#") + 1, secondPart.length() - 1);
					resource = secondPart.substring(secondPart.indexOf("=") + 2, secondPart.indexOf("#") + 1);

					prefix = xmlSchema.getPrefix(resource);

					if (prefix == null) {
						prefix = "<" + resource + object + ">";
					} else {
						prefix = xmlSchema.getPrefix(resource) + ":" + object;
					}

				} else {
					resource = secondPart.substring(secondPart.indexOf("=") + 1, secondPart.lastIndexOf("\"") + 1);
					prefix = namespace + ":" + resource;
				}

				return firstPart + " " + prefix + ";";

			} else {
				System.out.println("Case that I did not see it:");
				System.out.println(fileLine);
				System.out.println("");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(fileLine);// TODO: handle exception
		}

		return "";

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

	private String getEncoding(String line) {
		String encoding = line.substring(line.indexOf("encoding") + 10);
		encoding = encoding.substring(0, encoding.indexOf("\""));
		System.out.println(encoding);
		return encoding;
	}
}
