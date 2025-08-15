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
@Feature("Books - Positive")
public class BookPositiveTest extends BasicAPITest {

    private static Integer createdId;
    private final String token = Auth.token();

    @Test(description = "Create a book", priority = 1)
    @Story("Create")
    public void createBook() {
        createdId =
        given()
            .spec(Specs.baseSpec())
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Clean Code\",\"author\":\"Robert C. Martin\"}")
        .when()
            .post(ApiEndPointsKey.REQUEST_URL_ADD_BOOKS)
        .then()
            .statusCode(anyOf(is(200), is(201)))
            .contentType(ContentType.JSON)
            .body("id", notNullValue())
            .extract().path("id");
    }

    @Test(description = "Read the created book", dependsOnMethods = "createBook", priority = 2)
    @Story("Read")
    public void getBook() {
        given()
            .spec(Specs.baseSpec())
            .header("Authorization", "Bearer " + token)
        .when()
            .get(ApiEndPointsKey.REQUEST_URL_GET_BOOKS + createdId)
        .then()
            .statusCode(200)
            .body("title", containsString("Clean"));
    }

    @Test(description = "Update the book", dependsOnMethods = "getBook", priority = 3)
    @Story("Update")
    public void updateBook() {
        given()
            .spec(Specs.baseSpec())
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Clean Coder\",\"author\":\"Robert C. Martin\"}")
        .when()
            .put(ApiEndPointsKey.REQUEST_URL_UPDATE_BOOKS + createdId)
        .then()
            .statusCode(anyOf(is(200), is(204)));
    }

    @Test(description = "Delete the book", dependsOnMethods = "updateBook", priority = 4)
    @Story("Delete")
    public void deleteBook() {
        given()
            .spec(Specs.baseSpec())
            .header("Authorization", "Bearer " + token)
        .when()
            .delete(ApiEndPointsKey.REQUEST_URL_DELETE_BOOK + createdId)
        .then()
            .statusCode(anyOf(is(200), is(204)));
    }

    @Test(description = "Verify deleted book returns 404", dependsOnMethods = "deleteBook", priority = 5)
    @Story("Verify Deletion")
    public void getDeletedBookShould404() {
        given()
            .spec(Specs.baseSpec())
            .header("Authorization", "Bearer " + token)
        .when()
            .get(ApiEndPointsKey.REQUEST_URL_GET_BOOKS + createdId)
        .then()
            .statusCode(404);
    }
}
