package streaming;

import java.io.File;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class TestStreaming {

	public void readKarton() {
		try {
			JsonFactory jsonFactory = new JsonFactory();
			JsonParser jsonParser = jsonFactory.createParser(new File("Karton3.json"));

			while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
				String fieldname = jsonParser.getCurrentName();
				jsonParser.nextToken();
				System.out.println(fieldname + " " + jsonParser.getText());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		TestStreaming ts = new TestStreaming();
		ts.readKarton();
	}
}
