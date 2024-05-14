package com.czabala.myhome.config.security;

//@Component
public class AuthorizationErrorFilter //extends OncePerRequestFilter
{
/*
    private final ObjectMapper objectMapper;

    public AuthorizationErrorFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            handleAuthenticationException(response, e);
        }
    }

    private void handleAuthenticationException(HttpServletResponse response, AuthenticationException e) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse("Unauthorized", e.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

 */
}
/*
@Data
@AllArgsConstructor
class ErrorResponse {
    private String error;
    private String message;

}

 */
