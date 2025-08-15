package base;

import io.qameta.allure.Step;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class BasicAPITest {

    @BeforeSuite(alwaysRun = true)
    @Step("Starting API Test Suite")
    public void beforeSuite() {
        System.out.println("=== Starting API Test Suite ===");
    }

    @AfterSuite(alwaysRun = true)
    @Step("Finishing API Test Suite")
    public void afterSuite() {
        System.out.println("=== Finished API Test Suite ===");
    }
}