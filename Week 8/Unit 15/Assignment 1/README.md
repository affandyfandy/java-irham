# `OncePerRequestFilter` in Spring

`OncePerRequestFilter` is an abstract class provided by the Spring Framework. It ensures that a filter is executed only once per request. This is particularly useful for tasks such as authentication, logging, or encoding settings, where repeated execution of the same filter logic for a single request can be inefficient or problematic.

## Key Features
- **Single Execution**: Guarantees that the filter logic is applied only once per request, preventing duplicate processing.
- **Simplified Filter Logic**: Reduces the need for additional checks within the filter to ensure single execution.
- **Integration with Spring**: Seamlessly integrates with Spring applications, particularly in the context of Spring Boot.

## Example: Authentication Filter

Below is an example of implementing an authentication filter using `OncePerRequestFilter`.

### AuthenticationFilter.java

```java
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authToken = request.getHeader("Authorization");

        if (isValidToken(authToken)) {
            // Proceed to the next filter or the target resource
            filterChain.doFilter(request, response);
        } else {
            // Return an error response if the token is invalid
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
        }
    }

    private boolean isValidToken(String authToken) {
        // Token validation logic
        return authToken != null && authToken.equals("valid-token");
    }
}
```

1. `doFilterInternal` Method: This method contains the logic to check the authentication token.
    - **Retrieve Token**: The filter retrieves the token from the request header.
    - **Token Validation**: The token is validated using a helper method `isValidToken`.
    - **Filter Chain**: If the token is valid, the request proceeds to the next filter or resource. If not, an error response is returned.
2. `isValidToken` Method: This is a placeholder method for the actual token validation logic.

### FilterConfig.java

To register the filter in a Spring Boot application, you need to configure it as a bean.
```java
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthenticationFilter());
        registrationBean.addUrlPatterns("/secure/*"); // Apply the filter to URLs starting with /secure/
        return registrationBean;
    }
}
```
1. **Bean Registration**: The `FilterConfig` class registers the `AuthenticationFilter` as a bean using `FilterRegistrationBean`.
2. **URL Patterns**: The filter is applied to URL patterns starting with `/secure/*`. This can be adjusted based on application requirements.

## Benefits of Using `OncePerRequestFilter`
- **Guarantees Single Execution**: Ensures that the filter runs only once per request, avoiding redundant processing.
- **Eases Development**: Developers do not need to manually handle single execution checks within the filter logic.
- **Efficient Request Handling**: Improves efficiency by eliminating unnecessary repeated executions of the same filter logic.