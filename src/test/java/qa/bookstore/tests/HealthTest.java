package qa.bookstore.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import qa.bookstore.core.Specs;
@Epic("Health") @Feature("Health endpoint")
public class HealthTest {
  @Test public void healthIsUp() {
    given().spec(Specs.baseSpec()).when().get("/health").then().statusCode(200).body("status", is("up"));
  }
}
