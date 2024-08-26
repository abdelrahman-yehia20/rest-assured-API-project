package FakeStore_APITests;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Cart extends BaseTest{
    private int id=5;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    @Test
    public void TestGetAllCarts()
    {
        given().spec(requestSpecification).when().get("carts").then().spec(responseSpecification).log().all()
                .assertThat().body(not(empty())) ;   }
    @Test
    public void GetCart()
    {
        given().spec(requestSpecification).when().get("carts/"+1).then().spec(responseSpecification)
                .assertThat().body("id",equalTo(1));
    }
    @Test
    public void AddCart()
    {
      File Body=new File("src/test/resources/CartBody.json");
       setId( given().spec(requestSpecification).header("Content-Type","application/json").body(Body).
                when().post("carts/").then().spec(responseSpecification).log().all()
                .assertThat().body("",hasKey("id")).extract().path("id"));

    }
    @Test
    public void EditCart()
    { File EditedBody=new File("src/test/resources/CartEditedBody.json");
        given().spec(requestSpecification).header("Content-Type","application/json").body(EditedBody).
                when().put("carts/"+getId()).then().spec(responseSpecification).log().all()
                .assertThat().body("products.quantity",equalTo(2));

    }
    @Test
    public void DeleteCart()
    {
        given().spec(requestSpecification).when().delete("carts/"+getId()).then().assertThat()
                .body(containsString("null"));
    }
}
