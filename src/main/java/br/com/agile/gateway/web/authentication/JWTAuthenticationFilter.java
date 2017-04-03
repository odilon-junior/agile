package br.com.agile.gateway.web.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by odilon on 29/03/17.
 */
public class JWTAuthenticationFilter  extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        Authentication authentication = TokenAuthenticationService
                .getAuthentication((HttpServletRequest)request);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }

    /**
     * Created by odilon on 29/03/17.
     */
    public static class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

        public JWTLoginFilter(String url, AuthenticationManager authManager) {
            super(new AntPathRequestMatcher(url));
            setAuthenticationManager(authManager);
        }

        @Override
        public Authentication attemptAuthentication(
                HttpServletRequest req, HttpServletResponse res)
                throws AuthenticationException, IOException, ServletException {
            Usuario creds = new ObjectMapper()
                    .readValue(req.getInputStream(), Usuario.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            Collections.emptyList()
                    )
            );
        }

        @Override
        protected void successfulAuthentication(
                HttpServletRequest req,
                HttpServletResponse res, FilterChain chain,
                Authentication auth) throws IOException, ServletException {
            TokenAuthenticationService
                    .addAuthentication(res, auth.getName());
        }
    }

}
