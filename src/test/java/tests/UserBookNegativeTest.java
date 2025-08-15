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
@Feature("User & Books - Negative")
public class UserBookNegativeTest extends BasicAPITest {

    private final String token = Auth.token();

    @Test(description = "Invalid login should be rejected")
    @Story("Login Negative - Wrong Password")
    public void invalidLoginIsRejected() {
        given()
            .spec(Specs.baseSpec())
            .contentType(ContentType.JSON)
            .body("{\"email\":\"invalid@example.com\",\"password\":\"wrong\"}")
        .when()
            .post(ApiEndPointsKey.REQUEST_URL_GENERATE_TOKEN)
        .then()
            .statusCode(anyOf(is(400), is(401), is(422)));
    }

    @Test(description = "Login with empty password should be rejected")
    @Story("Login Negative - Empty Password")
    public void loginEmptyPassword() {
        given()
            .spec(Specs.baseSpec())
            .contentType(ContentType.JSON)
            .body("{\"email\":\"invalid@example.com\",\"password\":\"\"}")
        .when()
            .post(ApiEndPointsKey.REQUEST_URL_GENERATE_TOKEN)
        .then()
            .statusCode(anyOf(is(400), is(401), is(422)));
    }

    @Test(description = "Create book without auth should be unauthorized")
    @Story("Books Negative - Create Without Auth")
    public void createWithoutAuth() {
        given()
            .spec(Specs.baseSpec())
            .contentType(ContentType.JSON)
            .body("{\"title\":\"No Auth\",\"author\":\"Anon\"}")
        .when()
            .post(ApiEndPointsKey.REQUEST_URL_ADD_BOOKS)
        .then()
            .statusCode(401);
    }

    @Test(description = "Get non-existent book should return 404")
    @Story("Books Negative - Read Non-existent")
    public void getNonExistent() {
        given()
            .spec(Specs.baseSpec())
            .header("Authorization", "Bearer " + token)
        .when()
            .get(ApiEndPointsKey.REQUEST_URL_GET_BOOKS + "999999")
        .then()
            .statusCode(404);
    }

    @Test(description = "Update non-existent book should return 404")
    @Story("Books Negative - Update Non-existent")
    public void updateNonExistent() {
        given()
            .spec(Specs.baseSpec())
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Ghost\",\"author\":\"Nobody\"}")
        .when()
            .put(ApiEndPointsKey.REQUEST_URL_UPDATE_BOOKS + "999999")
        .then()
            .statusCode(404);
    }

    @Test(description = "Delete non-existent book should return 404")
    @Story("Books Negative - Delete Non-existent")
    public void deleteNonExistent() {
        given()
            .spec(Specs.baseSpec())
            .header("Authorization", "Bearer " + token)
        .when()
            .delete(ApiEndPointsKey.REQUEST_URL_DELETE_BOOK + "999999")
        .then()
            .statusCode(404);
    }

    @Test(description = "Create book with invalid payload should be rejected (422)")
    @Story("Books Negative - Invalid Payload")
    public void createWithInvalidPayload() {
        given()
            .spec(Specs.baseSpec())
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Only Title\"}") // missing author
        .when()
            .post(ApiEndPointsKey.REQUEST_URL_ADD_BOOKS)
        .then()
            .statusCode(anyOf(is(400), is(422)));
    }

    @Test(description = "Duplicate signup attempt should be rejected on second call")
    @Story("Signup Negative - Duplicate User")
    public void signupDuplicateUser() {
        String email = "dup+" + System.currentTimeMillis() + "@example.com";
        String body = String.format("{\"email\":\"%s\",\"password\":\"StrongP@ssw0rd!\"}", email);

        // First time succeeds
        given()
            .spec(Specs.baseSpec())
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post(ApiEndPointsKey.REQUEST_URL_CREATE_USER)
        .then()
            .statusCode(anyOf(is(200), is(201)));

        // Second time should be 400/409
        given()
            .spec(Specs.baseSpec())
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post(ApiEndPointsKey.REQUEST_URL_CREATE_USER)
        .then()
            .statusCode(anyOf(is(400), is(409)));
    }

    @Test(description = "Deleting a book without auth should be unauthorized/forbidden/not found")
    @Story("Books Negative - Delete Without Auth")
    public void deleteWithoutAuth() {
        given()
            .spec(Specs.baseSpec())
        .when()
            .delete(ApiEndPointsKey.REQUEST_URL_DELETE_BOOK + "1")
        .then()
            .statusCode(anyOf(is(401), is(403), is(404)));
    }
}
