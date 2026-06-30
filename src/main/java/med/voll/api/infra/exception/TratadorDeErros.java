package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex ){

        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity tratarErroBadCredentials(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity tratarErroAcessoNegado(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
    }

    private record DadosErroValidacao(String campo, String mensagem){

        public DadosErroValidacao(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }

}
