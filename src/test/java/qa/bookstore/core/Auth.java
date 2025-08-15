package qa.bookstore.core;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import qa.bookstore.config.Config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public final class Auth {
  private static String cachedToken;
  private Auth(){}
  @Step("Sign up user if needed")
  private static void signupIfNeeded() {
    RequestSpecification spec = Specs.baseSpec();
    String email = Config.email();
    String password = Config.password();
    given().spec(spec).body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password))
      .when().post("/signup")
      .then().statusCode(anyOf(is(200), is(201), is(400), is(409)));
  }
  @Step("Login and cache token")
  public static String token() {
    if (cachedToken != null) return cachedToken;
    signupIfNeeded();
    ValidatableResponse res = given().spec(Specs.baseSpec()).body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", Config.email(), Config.password()))
      .when().post("/login")
      .then().statusCode(200).body("token_type", equalToIgnoringCase("bearer")).body("access_token", notNullValue());
    cachedToken = res.extract().path("access_token");
    return cachedToken;
  }
}
