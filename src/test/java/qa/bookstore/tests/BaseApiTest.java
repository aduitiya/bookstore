package qa.bookstore.tests;

import io.qameta.allure.Story;
import io.qameta.allure.Step;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import qa.bookstore.core.Auth;
import qa.bookstore.core.Specs;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
public abstract class BaseApiTest {
  static { enableLoggingOfRequestAndResponseIfValidationFails(); }
  protected RequestSpecification authSpec;
  @BeforeClass(alwaysRun = true) @Step("Prepare authorized spec with Bearer token")
@Story("setUpAuth")
      public void setUpAuth() {
    String token = Auth.token();
    this.authSpec = Specs.baseSpec().headers("Authorization", "Bearer " + token).contentType(ContentType.JSON).log().ifValidationFails(LogDetail.ALL);
  }
}
