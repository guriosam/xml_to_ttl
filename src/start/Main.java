package start;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import converter.Translator;
import utils.IO;

public class Main {

	public static void main(String args[]) throws IOException, SAXException, ParserConfigurationException {

		String path = "input/";
		List<String> files = IO.filesOnFolder(path);

		for (String file : files) {

			File f = new File(path + file);
			if (!f.exists()) {
				throw new FileNotFoundException();
			}

			if (file.contains(".xml")) {
				List<String> fileLines = IO.readAnyFile(path + file);

				if (file.contains("database")) {
					continue;
				}

				Translator t = new Translator();
				String output = t.xmlToObject(fileLines);
				// IO.writeAnyString(path + file.replace(".xml", ".ttl"), output);
			}
		}

	}

}
