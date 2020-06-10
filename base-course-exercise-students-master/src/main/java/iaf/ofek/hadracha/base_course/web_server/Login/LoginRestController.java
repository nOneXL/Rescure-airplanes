package iaf.ofek.hadracha.base_course.web_server.Login;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/login")
public class LoginRestController {
    @GetMapping("")
    public LoginDetails login(HttpServletResponse response, @CookieValue(value = "client-id", defaultValue = "") String clientId){
        if (clientId.isEmpty()) {
            clientId = UUID.randomUUID().toString();
            response.addCookie(new Cookie("client-id", clientId));
        }
        System.out.println("Client [" + clientId + "] has logged on" );
        return new LoginDetails(clientId);
    }

    private class LoginDetails{
        public String clientId;

        public LoginDetails(String clientId) {
            this.clientId = clientId;
        }
    }
}
