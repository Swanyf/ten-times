package exception;

import entity.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GloblalException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity returnException() {
        return new ResponseEntity(false,0,"请求错误");
    }

}
