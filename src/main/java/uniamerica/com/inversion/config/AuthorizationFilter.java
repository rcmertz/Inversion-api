package uniamerica.com.inversion.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import uniamerica.com.inversion.service.AuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static uniamerica.com.inversion.config.HttpConstants.SIGNING_KEY;
import static uniamerica.com.inversion.config.HttpConstants.TOKEN_PREFIX;

public class AuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/users/login")) {
            filterChain.doFilter(request, response);
        } else {
            String authtorizationHeader = request.getHeader(AUTHORIZATION);
            if (authtorizationHeader != null && authtorizationHeader.startsWith(TOKEN_PREFIX)) {
                String username = null;
                String token = null;
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                try {
                    token = authtorizationHeader.substring(TOKEN_PREFIX.length());

                    Algorithm algorithm = Algorithm.HMAC256(SIGNING_KEY.getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    username = decodedJWT.getSubject();
                } catch (Exception exception) {
                    response.setHeader("error", exception.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("message", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    JwtUtils jwtUtils = new JwtUtils();
                    UserDetails userDetails = authenticationService.loadUserByUsername(username);
                    if (jwtUtils.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

                }
            }
            filterChain.doFilter(request, response);
        }
    }
}
