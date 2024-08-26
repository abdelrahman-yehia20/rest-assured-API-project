package FakeStore_APITests;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

public class Login extends BaseTest{
     @Test
    public void GetToken()
    {
        HashMap<String ,String> body=new HashMap<>();
        body.put("username","mor_2314");
        body.put("password","83r5^_");
        given().spec(requestSpecification).contentType(ContentType.JSON).
                body(body).when().post("auth/login").then().
                spec(responseSpecification).assertThat().body("",hasKey("token"));

    }
    @Test
    public void GetToken2()
    {

        File LoginData=new File("src/test/resources/LoginData.json");
        given().spec(requestSpecification).contentType(ContentType.JSON).body(LoginData).
                when().post("auth/login").then().
                spec(responseSpecification).assertThat().body("",hasKey("token"));

    }
}
