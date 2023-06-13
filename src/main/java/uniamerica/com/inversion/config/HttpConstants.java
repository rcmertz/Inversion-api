package uniamerica.com.inversion.config;

public final class HttpConstants {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 10 * 60 * 60;

    public static final long REFRESH_TOKEN_VALIDITY_SECONDS = 30 * 60 * 60;
    public static final String SIGNING_KEY = "secret";
    public static final String TOKEN_PREFIX = "Bearer ";

    private HttpConstants() { }
}
