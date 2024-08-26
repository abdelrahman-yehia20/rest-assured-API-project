package FakeStore_APITests;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import static org.hamcrest.Matchers.lessThan;
public abstract class BaseTest {
    protected static RequestSpecification requestSpecification;
    protected static ResponseSpecification responseSpecification;

    @BeforeTest
    public void SetRequestSpecification()
    {
          requestSpecification=new RequestSpecBuilder().
                  setBaseUri("https://fakestoreapi.com/").build();

    }
    @BeforeTest
    public void SetResponseSpecification()
    {

        responseSpecification=new ResponseSpecBuilder().
                expectContentType("application/json").expectStatusCode(200).
               expectResponseTime((lessThan(3000L))).build();

    }
    @DataProvider
    public static Object[][] IdPriceCategory() {
        return new Object[][]{
                     {1, 109.95, "men's clothing"},
                     {14, 999.99, "electronics"},
                     {20, 12.99, "women's clothing"}
    };
    }
    @DataProvider
    public static Object[] ID() {
        return new Object[]{
                1,2,3
        };
    }

}
