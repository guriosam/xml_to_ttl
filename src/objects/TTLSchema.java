package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TTLSchema {

	private String encoding;
	private HashMap<String, String> prefix;
	private List<String> lines;

	public TTLSchema() {
		this.prefix = new HashMap<>();
		this.lines = new ArrayList<>();
	}

	public void putPrefix(String ns, String value) {
		prefix.put(ns, value);
	}

	public String getPrefix(String ns) {
		return prefix.get(ns);
	}

	public void addLine(String value) {
		lines.add(value);
	}

	public String getLine(int position) {
		return lines.get(position);
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void printPrefix() {
		System.out.println(prefix);
	}

	public Set<String> getPrefixKeySet() {
		return prefix.keySet();
	}

	@Override
	public String toString() {

		String output = "";

		for (String key : prefix.keySet()) {
			output += "@prefix " + prefix.get(key) + ":<" + key + ">.\n";
		}

		output += "\n";

		for (int i = 0; i < lines.size() - 1; i++) {
			output += lines.get(i) + "\n";
		}

		return output;

	}
}
