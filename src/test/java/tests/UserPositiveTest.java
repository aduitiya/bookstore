package tests;
import base.BasicAPITest;
import constants.ApiEndPointsKey;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import qa.bookstore.core.Specs;
import qa.bookstore.core.Auth;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Bookstore API")
@Feature("User Account - Positive")
public class UserPositiveTest extends BasicAPITest {

    @Test(description = "Health endpoint should respond 200 and status up")
    @Story("Health Check")
    public void healthCheck() {
        given()
            .spec(Specs.baseSpec())
        .when()
            .get(ApiEndPointsKey.REQUEST_URL_GET_HEALTH)
        .then()
            .statusCode(200)
            .body("status", is("up"));
    }

    @Test(description = "User can sign up successfully with a fresh email")
    @Story("Signup")
    public void signupFreshUser() {
        String email = "qa+" + System.currentTimeMillis() + "@example.com";
        String body = String.format("{\"email\":\"%s\",\"password\":\"StrongP@ssw0rd!\"}", email);
        given()
            .spec(Specs.baseSpec())
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post(ApiEndPointsKey.REQUEST_URL_CREATE_USER)
        .then()
            .statusCode(anyOf(is(200), is(201)));
    }

    @Test(description = "User can log in and receive a Bearer token")
    @Story("Login")
    public void loginValid() {
        String token = Auth.token();
        given()
            .spec(Specs.baseSpec())
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Token Smoke\",\"author\":\"QA\"}")
        .when()
            .post(ApiEndPointsKey.REQUEST_URL_ADD_BOOKS)
        .then()
            .statusCode(anyOf(is(200), is(201)))
            .body("id", notNullValue());
    }
}
