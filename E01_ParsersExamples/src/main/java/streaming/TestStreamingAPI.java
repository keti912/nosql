package streaming;

import java.io.File;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class TestStreamingAPI {

	private static void createJson() {
		JsonFactory jsonFactory = new JsonFactory();
		JsonGenerator jsonGenerator;

		try {
			jsonGenerator = jsonFactory.createGenerator(new File("student_streaming.json"), JsonEncoding.UTF8);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void readJson() {
		JsonFactory jsonFactory = new JsonFactory();
		JsonParser jsonParser;
		try {
			jsonParser = jsonFactory.createParser(new File("student_streaming.json"));
			while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
				String fieldName = jsonParser.getCurrentName();
				if ("name".equals(fieldName)) {
					jsonParser.nextToken();
					System.out.println(jsonParser.getText());
				}
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