package FindYourThingApplication.auth;

import FindYourThingApplication.entities.User;
import FindYourThingApplication.services.providers.UserProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserProvider userProvider;

    @Override
    protected void doFilterInternal(
            @NonNull
            HttpServletRequest request,
            @NonNull
            HttpServletResponse response,
            @NonNull
            FilterChain filterChain
    ) throws ServletException, IOException
    {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String email;

        if(authHeader == null || !authHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            token = authHeader.substring(7);
            email = jwtService.extractEmail(token);
        }
        catch (Exception e)
        {
            filterChain.doFilter(request, response);
            return;
        }

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            User user = userProvider.getUserFromEmail(email);
            if(jwtService.isTokenValid(token, user))
            {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
