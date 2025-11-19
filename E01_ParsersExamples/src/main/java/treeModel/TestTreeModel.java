package treeModel;

import java.io.File;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestTreeModel {

	public static void createJson() {
		ObjectMapper om = new ObjectMapper();
		JsonNode rootNode = om.createObjectNode();
		JsonNode marksNode = om.createArrayNode();
		((ArrayNode) marksNode).add(100);
		((ArrayNode) marksNode).add(90);
		JsonNode s = om.createObjectNode();
		((ObjectNode) s).put("name", "Zika");
		((ObjectNode) s).put("age", 21);
		((ObjectNode) rootNode).set("student", s);
		((ObjectNode) rootNode).put("verified", false);
		((ObjectNode) rootNode).set("marks", marksNode);
		try {
			om.writeValue(new File("student_treeModel.json"), rootNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readJson() {
		ObjectMapper om = new ObjectMapper();
		String jsonString = "{\"student\":{\"name\":\"Zika\", \"age\":20}, \"faculty\":\"PMF\", \"verified\": false,\"marks\":[1,2,3]}";
		JsonNode rootNode;
		try {
			rootNode = om.readTree(jsonString);
			JsonNode student = rootNode.path("student");
			String name = student.path("name").textValue();
			int age = student.path("age").intValue();
			System.out.println(name + " " + age);
			JsonNode faculty = rootNode.path("faculty");
			JsonNode verified = rootNode.path("verified");
			System.out.println(faculty.textValue() + " " + verified.booleanValue());
			JsonNode marks = rootNode.path("marks");
			Iterator it = (Iterator) marks.elements();
			while (it.hasNext())
				System.out.println(it.next());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		createJson();
		readJson();
	}
}