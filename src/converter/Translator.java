package converter;

import java.util.HashMap;
import java.util.List;

import objects.Schema;

public class Translator {

	public void xmlToObject(List<String> fileLines) {

		Schema xmlSchema = new Schema();

		boolean first = true;

		boolean started = false;
		boolean ended = false;
		String auxSchema = "";

		String aux = "";

		for (String fileLine : fileLines) {

			// Collecting the encoding for future needs
			if (fileLine.contains("encoding")) {
				String encoding = getEncoding(fileLine);
				xmlSchema.setEncoding(encoding);
				continue;
			}

			// The first line contains the namespaces for making the @prefix.
			if (first) {
				aux += fileLine;
				// Checking if the namespaces ended or there is another line with it.
				if (fileLine.endsWith(">")) {
					first = false;
				}

				if (!first) {
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
			} else {

				
				
			}

		}
		xmlSchema.printPrefix();
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
