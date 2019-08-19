package start;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import converter.Translator;
import objects_xml.XMLGeneric;
import utils.IO;

public class Main {

	public static void main(String args[]) throws IOException, ParserConfigurationException {

		String path = "input/";
		List<String> files = IO.filesOnFolder(path);

		XMLGeneric g = null;
		Translator t = new Translator();

		String databasePath = "";
		String filePath = "";

		for (String file : files) {

			File f = new File(path + file);

			// Checking if the file is still there and isn't a folder
			if (!f.exists() || f.isDirectory()) {
				throw new FileNotFoundException();

			}

			// Filtering for XMLs only
			if (file.contains(".xml")) {
				if (file.contains("database")) {
					databasePath = path + file;
				} else {
					filePath = path + file;
				}

			}

		}
		
		System.out.println(databasePath);
		System.out.println(filePath);

		g = t.xmlToObject(databasePath, filePath);
		//t.xmlToRML(databasePath, filePath);
		t.xmlToTTL(databasePath, filePath);
		
		

		//System.out.println(g.printRML());

		IO.writeAnyString(filePath.replace(".xml", ".ttl"), g.printTTL());

	}

}
