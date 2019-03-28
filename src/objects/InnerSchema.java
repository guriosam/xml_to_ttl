package objects;

import java.util.HashMap;
import java.util.List;

public class InnerSchema {

	private HashMap<String, String> innerSchema;

	public void put(String workspace, String value) {
		if (innerSchema == null) {
			System.out.println("The target object you are trying to add into is null.");
			throw new NullPointerException();
		}
		
		innerSchema.put(workspace, value);
	}

	public String get(String workspace) {
		if (innerSchema == null) {
			System.out.println("The target object you are trying to get from is null.");
			throw new NullPointerException();
		}

		return innerSchema.get(workspace);
	}
}
