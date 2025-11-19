package databinding;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestDataBinding {
	public static void createJson() {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> studentDataMap = new LinkedHashMap<String, Object>();
		int[] marks = { 1, 2, 3 };
		Student s = new Student();
		s.setName("Mika");
		s.setAge(21);
		studentDataMap.put("student", s);
		studentDataMap.put("faculty", "PMF");
		studentDataMap.put("verified", Boolean.FALSE);
		studentDataMap.put("marks", marks);

		try {
			mapper.writeValue(new File("student_dataBinding.json"), studentDataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readJson() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			LinkedHashMap<String, Object> mapRead = mapper.readValue(new File("student_dataBinding.json"),
					LinkedHashMap.class);
			LinkedHashMap<String, Object> so = (LinkedHashMap<String, Object>) mapRead.get("student");

			System.out.println(so.get("name") + " " + so.get("age"));
			ArrayList<Integer> marksRead = (ArrayList<Integer>) mapRead.get("marks");
			for (int i = 0; i < marksRead.size(); i++) {
				System.out.println(marksRead.get(i) + " ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		createJson();
		readJson();
	}
}