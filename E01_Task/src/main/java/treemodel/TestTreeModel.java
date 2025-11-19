package treemodel;

import java.io.File;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestTreeModel {

	public void readKarton() {
		ObjectMapper om = new ObjectMapper();

		StringBuilder sb = new StringBuilder();

		File f = new File("Karton.json");
		try {

			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				sb.append(line);
			}

			sc.close();
			String input = sb.toString();
			JsonNode rootNode = om.readTree(input);

			JsonNode p = rootNode.path("pacijent");
			System.out.println("Broj kartona " + p.path("brojKartona") + ", ime i prezime: " + p.path("ime") + " "
					+ p.path("prezime"));
			System.out.println();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void writeKarton() {
		ObjectMapper om = new ObjectMapper();
		JsonNode k = om.createObjectNode();

		JsonNode p = om.createObjectNode();
		((ObjectNode) p).put("brojKartona", "2/2");
		((ObjectNode) p).put("ime", "Mira");
		((ObjectNode) p).put("prezime", "Miric");

		JsonNode l = om.createObjectNode();
		((ObjectNode) l).put("ime", "Sava");
		((ObjectNode) l).put("prezime", "Savic");

		JsonNode d1 = om.createObjectNode();
		((ObjectNode) d1).put("naziv", "grip");
		((ObjectNode) d1).put("sifra", "sss");

		JsonNode d2 = om.createObjectNode();
		((ObjectNode) d2).put("naziv", "kasalj");
		((ObjectNode) l).put("sifra", "k0");

		ArrayNode d = om.createArrayNode();
		((ArrayNode) d).add(d1);
		((ArrayNode) d).add(d2);

		((ObjectNode) k).set("pacijent", p);
		((ObjectNode) k).set("lekar", l);
		((ObjectNode) k).set("dijagnoze", d);
		try {
			om.writeValue(new File("Karton3.json"), k);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TestTreeModel ttm = new TestTreeModel();
		ttm.readKarton();
		ttm.writeKarton();
	}
}
