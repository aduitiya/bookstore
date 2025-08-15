package qa.bookstore.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) PROPS.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }
    private Config(){}

    public static String baseUrl() {
        String v = System.getProperty("baseUrl");
        if (v != null && !v.isBlank()) return v;
        v = System.getenv("BASE_URL");
        if (v != null && !v.isBlank()) return v;
        v = PROPS.getProperty("baseUrl");
        return (v != null && !v.isBlank()) ? v : "http://localhost:8000";
    }

    public static String email() {
        String v = System.getProperty("EMAIL");
        if (v != null && !v.isBlank()) return v;
        v = System.getenv("EMAIL");
        if (v != null && !v.isBlank()) return v;
        v = PROPS.getProperty("EMAIL");
        return (v != null && !v.isBlank()) ? v : "qa+local@example.com";
    }

    public static String password() {
        String v = System.getProperty("PASSWORD");
        if (v != null && !v.isBlank()) return v;
        v = System.getenv("PASSWORD");
        if (v != null && !v.isBlank()) return v;
        v = PROPS.getProperty("PASSWORD");
        return (v != null && !v.isBlank()) ? v : "StrongP@ssw0rd!";
    }
}
