# Načini parsiranja JSON dokumenata
U projektu preuzetom sa Moodle-a nalaze se tri paketa: `databinding`, `streaming` i `treeModel`. Svaki od njih predstavlja jedan od načina parsiranja JSON dokumenata, i svaki sadrži metode `createJson()` i `readJson()`, kao i `main()` u kojem se oba metoda pozivaju.
## 1 *Streaming*
Reč je o najjednostavnijem od tri ponuđena metoda. 

Metod `createJson()`:
```java
	private static void createJson() {
		JsonFactory jsonFactory = new JsonFactory();
		JsonGenerator jsonGenerator;
		
		try {
			jsonGenerator = jsonFactory.createGenerator(new File("student_streaming.json"),JsonEncoding.UTF8);
			
			jsonGenerator.writeStartObject();
			
			jsonGenerator.writeStringField("name", "Pera");
			jsonGenerator.writeNumberField("age", 21);
			jsonGenerator.writeBooleanField("verified", false);
			
			jsonGenerator.writeFieldName("marks");
			jsonGenerator.writeStartArray();
			jsonGenerator.writeNumber(100);
			jsonGenerator.writeNumber(90);
			jsonGenerator.writeNumber(85);
			jsonGenerator.writeEndArray();
			
			jsonGenerator.writeEndObject();
			
			jsonGenerator.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
```

Metod `readJson()`, koji zapravo čita samo jedno polje - ono koje se proverava u `if` naredbi:
```java
	private static void readJson() {
		JsonFactory jsonFactory = new JsonFactory();
		JsonParser jsonParser;
		
		try {
			jsonParser = jsonFactory.createParser(new File("student_streaming.json"));
			
			while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
				String fieldName = jsonParser.getCurrentName();
				if("name".equals(fieldName)){
					jsonParser.nextToken();
					System.out.println(jsonParser.getText());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
```
## 2 *Tree Model*
JSON objekat se kod ovog modela posmatra kao stablo. Primer objekta generisanog ovim modelom:
```json
	{
	   "student":{
	      "name":"Zika",
	      "age":21
	   },
	   "verified":false,
	   "marks":[
	      100,
	      90
	   ]
	}
```

Struktura stabla JSON objekta:
```markdown-tree
root
	student
		name
		age
	verified
	marks
```

Metod `createJson()`:
```java
	public static void createJson() {
		ObjectMapper om = new ObjectMapper();
		
		JsonNode rootNode = om.createObjectNode();
		
		JsonNode marksNode = om.createArrayNode();
		((ArrayNode)marksNode).add(100);
		((ArrayNode)marksNode).add(90);
		
		JsonNode s = om.createObjectNode();
		((ObjectNode) s).put("name", "Zika");
		((ObjectNode) s).put("age", 21);
		
		((ObjectNode) rootNode).set("student", s);
		((ObjectNode) rootNode).put("verified", false);
		((ObjectNode) rootNode).set("marks", marksNode);
		
		try {
			om.writeValue(new File("student_treeModel.json"), rootNode);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
```

Metod `readJson()`:
```java
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
```

Objekt koji metod `readJson()` čita je sledeći:
```json
	{
	   "student":{
	      "name":"Zika",
	      "age":20
	   },
	   "faculty":"PMF",
	   "verified":false,
	   "marks":[
	      1, 2, 3
	   ]
	}
```
## 3 *Data Binding*

Klasa `Student`:
```java
public class Student {
	private String name;
	private int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int i) {
		this.age = i;
	}	
}
```

Metod `createJson()`:
```java
	public static void createJson() {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> studentDataMap = new LinkedHashMap<String,Object>();
		
		Student s = new Student();
		s.setName("Mika");
		s.setAge(21);
		studentDataMap.put("student", s);
		
		studentDataMap.put("faculty", "PMF");
		studentDataMap.put("verified", Boolean.FALSE);
		
		int[] marks = {1,2,3};
		studentDataMap.put("marks", marks);
		
		try {
			mapper.writeValue(new File("student_dataBinding.json"), studentDataMap);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
```

Kreirani objekat:
```json
{
   "student":{
      "name":"Mika",
      "age":21
   },
   "faculty":"PMF",
   "verified":false,
   "marks":[
      1, 2, 3
   ]
}
```

Metod `readJson()`:
```java
	public static void readJson() {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			LinkedHashMap<String, Object> mapRead = mapper.readValue(new File("student_dataBinding.json"), LinkedHashMap.class);
			LinkedHashMap<String, Object> so = (LinkedHashMap<String, Object>) mapRead.get("student");
			
			System.out.println(so.get("name")+" "+so.get("age"));
			ArrayList<Integer> marksRead = (ArrayList<Integer>) mapRead.get("marks");
			
			for(int i=0; i<marksRead.size();i++)
				System.out.println(marksRead.get(i)+" ");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
```