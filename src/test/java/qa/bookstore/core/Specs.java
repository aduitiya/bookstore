package qa.bookstore.core;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import qa.bookstore.config.Config;

public final class Specs {
    private Specs(){}

    public static RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Config.baseUrl())
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI)
                .build();
    }
}
