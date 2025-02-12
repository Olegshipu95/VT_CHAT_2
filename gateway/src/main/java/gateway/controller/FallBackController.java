package gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import gateway.dto.ApiErrorResponse;

@RestController
public class FallBackController {

    private static final String SERVICE_UNAVAILABLE_ERROR_MESSAGE = "%s service is currently unavailable, try later";

    @GetMapping("/fallback/questions")
    public ResponseEntity<ApiErrorResponse> questionsFallBack() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(getErrorMessage("Questions"));
    }

    @GetMapping("/fallback/resume")
    public ResponseEntity<ApiErrorResponse> resumeFallBack() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(getErrorMessage("Resume"));
    }

    @GetMapping("/fallback/analytic")
    public ResponseEntity<ApiErrorResponse> analyticFallBack() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(getErrorMessage("Analytic"));
    }

    @GetMapping("/fallback/accounts")
    public ResponseEntity<ApiErrorResponse> accountsFallBack() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(getErrorMessage("Accounts"));
    }

    private ApiErrorResponse getErrorMessage(String serviceName) {
        return new ApiErrorResponse(String.format(SERVICE_UNAVAILABLE_ERROR_MESSAGE, serviceName));
    }
}
