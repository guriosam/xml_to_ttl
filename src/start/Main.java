package start;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.json.JSONObject;
import org.json.XML;

import converter.Translator;
import utils.IO;

public class Main {

	public static void main(String args[]) throws FileNotFoundException {

		String path = "input/";
		List<String> files = IO.filesOnFolder(path);

		for (String file : files) {

			File f = new File(path + file);
			if (!f.exists()) {
				throw new FileNotFoundException();
			}

			if (file.contains(".xml")) {
				List<String> fileLines = IO.readAnyFile(path + file);
				
				String xml = "";
				for(String l : fileLines) {
					xml += l + "\n";
				}
				
				//JSONObject xmlJSONObj = XML.toJSONObject(xml);
	           // String jsonPrettyPrintString = xmlJSONObj.toString(4);
	           /// System.out.println(jsonPrettyPrintString);
				
				Translator t = new Translator();
				t.xmlToObject(fileLines);
				break;
			}
		}

	}

}
