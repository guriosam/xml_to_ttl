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

		for (String file : files) {

			File f = new File(path + file);
			
			//Checking if the file is still there and isn't a folder
			if (!f.exists() || f.isDirectory()) {
				throw new FileNotFoundException();
			}

			//Filtering for XMLs only
			if (file.contains(".xml")) {
				List<String> fileLines = IO.readAnyFile(path + file);
				
				//System.out.println(path + file);

				//if (file.contains("database")) {
				//	continue;
				//}

				
				g = t.xmlToObject(path + file);
				//System.out.println(g);
				//System.out.println(g.printTTL());
			
			}
			
		}
		
		System.out.println(g.printRML());
		
		//IO.writeAnyString(path + file.replace(".xml", ".ttl"), g.printTTL());

	}

}
