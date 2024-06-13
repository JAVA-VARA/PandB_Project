package sjspring.shop.pregAndBirthDeveloper.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "아이디 또는 비밀번호가 일치하지 않습니다.";

        if (exception.getMessage().equalsIgnoreCase("User not found")) {
            errorMessage = "없는 아이디입니다.";
        } else if (exception.getMessage().equalsIgnoreCase("Bad credentials")) {
            errorMessage = "비밀번호가 일치하지 않습니다.";
        }

        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/login?error=true").forward(request, response);
    }
}
