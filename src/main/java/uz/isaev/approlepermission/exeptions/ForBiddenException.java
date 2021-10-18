package uz.isaev.approlepermission.exeptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN)
@AllArgsConstructor
@NoArgsConstructor
public class ForBiddenException extends RuntimeException{
    private String type;
    private String message;
}
