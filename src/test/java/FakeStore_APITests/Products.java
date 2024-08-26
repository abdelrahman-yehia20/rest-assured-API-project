package FakeStore_APITests;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class Products extends BaseTest{

private   int ID;
    @Test(dataProvider = "IdPriceCategory")
    public void GetAllProduct(int id,double price,String category)
    {
        int index=id-1;
      given().spec(requestSpecification)
              .when().get("/products").then().spec(responseSpecification).
              assertThat().body("["+index+"]"+".id",equalTo(id),
                      "["+index+"]"+".price",equalTo((float)price)
          ,"["+index+"]"+".category",equalTo(category)).time(lessThan(3000L));;
    }
    @Test(dataProvider ="ID" )
    public void GetProduct(int id)
    {
        given().spec(requestSpecification).when().get("/products/"+id).then().
                assertThat().body("id",equalTo(id)).time(lessThan(3000L));
    }
    @Test
    public void SortProducts()
    {
        int id1=given().spec(requestSpecification).queryParam("sort","desc").
                when().get("/products").then().extract().path("[2].id");
        int id2=given().spec(requestSpecification).queryParam("sort","desc").
                when().get("/products").then().extract().path("[3].id");
        int id3=given().spec(requestSpecification).queryParam("sort","desc").
                when().get("/products").then().spec(responseSpecification).time(lessThan(3000L)).
                extract().path("[4].id");
        Assert.assertTrue(id1>id2&&id2>id3);
    }

    @Test
    public void AddProducts()
    {
        HashMap<String ,String> Info=new HashMap<>();
        Info.put("title","hi");
        Info.put("price","12");
        Info.put("description","new");
        Info.put("category","electronic");

       ID=given().spec(requestSpecification).contentType(ContentType.JSON).body(Info).
               when().post("/products").then().spec(responseSpecification).
               assertThat().body("title",equalTo("hi"),
                       "",hasEntry("description","new")).time(lessThan(3000L)).
               extract().path("id");



}
    @Test
    public void UpdateProduct()
    {
        HashMap<String ,String> Info=new HashMap<>();
        Info.put("title","Car");
        Info.put("price","12");
        Info.put("description","new");
        Info.put("category","electronic");

        given().spec(requestSpecification).contentType(ContentType.JSON).body(Info).
                when().put("/products/"+ID).then().spec(responseSpecification).
                assertThat().body("title",equalTo("Car"),
                        "",hasEntry("description","new"));
    }
    @Test
    public void DeleteProduct()
    {

       given().spec(requestSpecification).when().
              delete("/products/"+ID).then().spec(responseSpecification).assertThat().
               body(containsString("null"));
    }




}
