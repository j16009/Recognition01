package recognition01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DetectFacesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DetectedFaces;

public class Recognition01_main {

	public static void main(String[] args){

		VisualRecognition service = new VisualRecognition("2018-03-19");
		service.setApiKey("J16009");

		DetectFacesOptions detectFacesOptions = null;
		try {
			detectFacesOptions = new DetectFacesOptions.Builder()
					.imagesFile(new File("img/tbn.png"))
					.build();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		DetectedFaces result = service.detectFaces(detectFacesOptions).execute();
		System.out.println(result);
		String s = String.valueOf(result);
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(s);

			int age_min=node.get("images")
					.get(0).get("faces")
					.get(0).get("age")
					.get("min")
					.asInt();
			int age_max=node.get("images")
					.get(0).get("faces")
					.get(0).get("age")
					.get("max")
					.asInt();
			double age_score=node.get("images")
					.get(0).get("faces")
					.get(0).get("age")
					.get("score")
					.asDouble();
			int gender=node.get("images")
					.get(0).get("faces")
					.get(0).get("gender")
					.get("gender")
					.asInt();
			double gender_score=node.get("images")
					.get(0).get("faces")
					.get(0).get("gender")
					.get("score")
					.asDouble();

			System.out.println("age_min:"+age_min);
			System.out.println("age_max:"+age_max);
			System.out.println("age_score:"+age_score);
			System.out.println("Gender:"+gender);
			System.out.println("Gender_score:"+gender_score);

			MySQL mysql = new MySQL();
			mysql.updateImage(age_min,age_max,age_score,gender,gender_score);



		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
