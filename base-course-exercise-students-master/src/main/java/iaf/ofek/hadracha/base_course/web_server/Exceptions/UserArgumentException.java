package iaf.ofek.hadracha.base_course.web_server.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserArgumentException extends Exception{
    public UserArgumentException() {
    }

    public UserArgumentException(String message) {
        super(message);
    }

    public UserArgumentException(Throwable cause) {
        super(cause);
    }
}
