package lk.ijse.a1_journeypass_backend.advisor;

import lk.ijse.a1_journeypass_backend.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWideExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseUtil handleException(Exception e){
        return new ResponseUtil(
                500,
                "Something went wrong.Please try again later.",
                e.getMessage()
        );
    }
}
