package qa.bookstore.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
@Epic("Books") @Feature("CRUD")
public class BooksCrudTest extends BaseApiTest {
  @Test public void create_read_update_delete_book() {
    int id = given().spec(authSpec).body("{\"title\":\"Clean Code\",\"author\":\"Robert C. Martin\"}")
      .when().post("/books")
      .then().statusCode(anyOf(is(200), is(201))).contentType(ContentType.JSON).body("id", notNullValue()).extract().path("id");
    given().spec(authSpec).when().get("/books/" + id).then().statusCode(200).body("title", containsString("Clean"));
    given().spec(authSpec).body("{\"title\":\"Clean Coder\",\"author\":\"Robert C. Martin\"}")
      .when().put("/books/" + id)
      .then().statusCode(anyOf(is(200), is(204)));
    given().spec(authSpec).when().delete("/books/" + id).then().statusCode(anyOf(is(200), is(204)));
  }
}
