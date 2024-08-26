package FakeStore_APITests;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Users extends BaseTest {
    private int UserId;

    public void SetUserId(int id) {
        this.UserId = id;
    }
    public int getUserId() {
        return UserId;
    }
    @Test
    public void GetUsers()
    {
        given().spec(requestSpecification).when().get("users")
                .then().spec(responseSpecification).log().all().assertThat().body(not(empty()));
    }
    @Test
    public void GetUser()
    {
        given().spec(requestSpecification).when().get("users/1")
                .then().spec(responseSpecification).log().all().assertThat()
                .body("id",equalTo(1));
    }
    @Test(priority = 1)
    public void Adduser()
    {
        File UserData=new File("src/test/resources/UserData.json");
        SetUserId(given().spec(requestSpecification).contentType(ContentType.JSON).body(UserData).
                when().post("users/")
                .then().spec(responseSpecification).log().all().
                assertThat().body("",hasKey("id")).extract().path("id"));
    }
    @Test
    public void Edituser()
    {
        File EditedUserData=new File("src/test/resources/EditedUserData.json");
        given().spec(requestSpecification).contentType(ContentType.JSON).body(EditedUserData).
                when().put("users/"+getUserId())
                .then().spec(responseSpecification).log().all().
                assertThat().body("name",hasEntry("firstname","Ahmed"));
    }
    @Test(priority = 2,dependsOnMethods = "Adduser")
    public void Deleteuser()
    {

                given().spec(requestSpecification).when().delete("users/"+getUserId())
                .then().spec(responseSpecification).log().all().
                assertThat().body(containsString("null"));
    }
}
