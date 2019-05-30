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

			/*System.out.println(path + file);
			Document xmlDocument = null;
			// create a new DocumentBuilderFactory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setNamespaceAware(true);

			try {
				// use the factory to create a documentbuilder
				DocumentBuilder builder = factory.newDocumentBuilder();

				// create a new document from file
				//File file = new File(xmlPath);
				xmlDocument = builder.parse(f);
				
			} catch (IOException | ParserConfigurationException | SAXException ex) {
				ex.printStackTrace();
			}

			if (xmlDocument == null) {
				return;
			}

			xmlDocument.setXmlStandalone(true);

			// get root
			Element mapping = xmlDocument.getDocumentElement();
			mapping.normalize();
			Element config = (Element) mapping.getElementsByTagName("config").item(0);
			
			System.out.println(config.getNodeName());
			
			
			for(int i = 0; i < config.getChildNodes().getLength(); i++) {
				Element element = (Element) config.getChildNodes().item(i);
				System.out.println(element);
				//System.out.println(node);
			}
			
			System.out.println(config.getElementsByTagName("version").item(0).getTextContent());

*/
			if (file.contains(".xml")) {
				List<String> fileLines = IO.readAnyFile(path + file);

				// String xml = "";
				// for (String l : fileLines) {
				// xml += l + "\n";
				// }

				 Translator t = new Translator();
				 String output = t.xmlToObject(fileLines);
				// IO.writeAnyString(path + file.replace(".xml", ".ttl"), output);
			}
		}

	}

}
