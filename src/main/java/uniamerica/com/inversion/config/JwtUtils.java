package uniamerica.com.inversion.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uniamerica.com.inversion.entity.Usuario;

import java.util.Date;


import static uniamerica.com.inversion.config.HttpConstants.*;


@Component
public class JwtUtils {
    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getExpiresAt();
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SIGNING_KEY.getBytes());
    }

    private DecodedJWT getDecodedJWT(String token) {
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();

        return verifier.verify(token);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateAccessToken(Usuario usuario, String issuer) {
        return doGenerateAccessToken(
                usuario.getEmail(),
                issuer
        );
    }

    private String doGenerateAccessToken(String subject, String issuer) {

        Algorithm algorithm = getAlgorithm();
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username.equals(userDetails.getUsername())
                        && !isTokenExpired(token));
    }
}
