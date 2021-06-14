package net.etfbl.pisio.fileservice.config;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.authfilter.TokenValidationService;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class AuthenticationFilter extends HttpFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final int PREFIX_LENGTH = TOKEN_PREFIX.length();

    private final TokenValidationService tokenValidationService;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            abortWithUnauthorized(response);
            return;
        }

        String tokenValue = authorizationHeader.substring(PREFIX_LENGTH).trim();

        if (!tokenValidationService.isTokenValid(tokenValue)) {
            abortWithUnauthorized(response);
            return;
        }

        chain.doFilter(request, response);
    }

    private void abortWithUnauthorized(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
