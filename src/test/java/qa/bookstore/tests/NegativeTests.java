package qa.bookstore.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import qa.bookstore.core.Specs;
@Epic("Books") @Feature("Negative")
public class NegativeTests {
  @Test public void invalid_login_is_rejected() {
    given().spec(Specs.baseSpec()).body("{\"email\":\"invalid@example.com\",\"password\":\"wrong\"}")
      .when().post("/login").then().statusCode(anyOf(is(400), is(401), is(422)));
  }
  @Test public void delete_without_auth_is_forbidden() {
    given().spec(Specs.baseSpec()).when().delete("/books/1").then().statusCode(anyOf(is(401), is(403), is(404)));
  }
}
