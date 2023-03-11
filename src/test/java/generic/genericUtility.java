package generic;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class genericUtility {

    public static JsonPath rawToJson(String response){
        JsonPath jsonPath= new JsonPath(response);
            return jsonPath;
    }

}
