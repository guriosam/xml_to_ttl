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
				System.out.println("Encoding:");
				System.out.println(encoding);
				continue;
			}

			// The first line contains the namespaces for making the @prefix.
			if (first) {
				aux += fileLine;

				// Checking if the namespaces ended or there is another line with them
				if (fileLine.contains(">")) {
					collectNamespaces(aux);
					first = false;

					System.out.println(xmlSchema.getPrefixKeySet());
				}

				System.out.println("Open Namespace:");
				System.out.println(aux);
				System.out.println();

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
					System.out.println("EndScope");
					System.out.println(fileLine);
					System.out.println();
				}

				// checking if it's a value
				else if (fileLine.contains("</")) {
					System.out.println("Value:");
					System.out.println(fileLine);
					System.out.println();
				}

				// checking if it's a attribute
				else if (fileLine.contains("=")) {
					System.out.println("Attribute:");
					System.out.println(fileLine);

					if (fileLine.trim().endsWith("/>")) {
						System.out.println("Resource:");

						String[] lineSplitted = fileLine.split(" ");

						try {

							if (lineSplitted.length == 2) {
								String firstPart = lineSplitted[0];
								String secondPart = lineSplitted[1];

								// removing '<' character at begging
								firstPart = firstPart.substring(1);
								secondPart = secondPart.substring(0, secondPart.lastIndexOf("/"));

								String object = "";
								String namespace = "";
								String resource = "";
								String prefix = "";

								namespace = secondPart.substring(0, secondPart.indexOf(":"));

								if (secondPart.contains("#")) {
									object = secondPart.substring(secondPart.indexOf("#") + 1, secondPart.length() - 1);
									resource = secondPart.substring(secondPart.indexOf("=") + 2,
											secondPart.indexOf("#") + 1);

									prefix = xmlSchema.getPrefix(resource);
									
									if(prefix == null) {
										prefix = "<" + resource + object + ">";
									} else {
										prefix = xmlSchema.getPrefix(resource) + ":" + object;
									}
									
								} else {
									resource = secondPart.substring(secondPart.indexOf("=") + 1,
											secondPart.lastIndexOf("\"") + 1);
									prefix = namespace + ":" + resource;
								}

								String outLine = firstPart + " " + prefix + ";";
								
								System.out.println(outLine);
								System.out.println();

							} else {
								System.out.println("WHICH CASE IS THAT?????");
								System.out.println("!!!!!!!!!!!!!!!!!!");
								System.out.println(fileLine);
							}
							
							System.out.println();

						} catch (Exception e) {
							e.printStackTrace();
							System.out.println(fileLine);// TODO: handle exception
						}

					}

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
