//package gateway.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import gateway.dto.ApiErrorResponse;
//
//public class FallBackController {
//
//    private static final String SERVICE_UNAVAILABLE_ERROR_MESSAGE = "%s service is currently unavailable, try later";
//
//    @GetMapping("/fallback/users")
//    public ResponseEntity<ApiErrorResponse> questionsFallBack() {
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                .body(getErrorMessage("Users"));
//    }
//
//    @GetMapping("/fallback/feed")
//    public ResponseEntity<ApiErrorResponse> resumeFallBack() {
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                .body(getErrorMessage("Feed"));
//    }
//
//    @GetMapping("/fallback/subs")
//    public ResponseEntity<ApiErrorResponse> analyticFallBack() {
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                .body(getErrorMessage("Subscription"));
//    }
//
//    @GetMapping("/fallback/messenger")
//    public ResponseEntity<ApiErrorResponse> accountsFallBack() {
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                .body(getErrorMessage("Messenger"));
//    }
//
//    private ApiErrorResponse getErrorMessage(String serviceName) {
//        return new ApiErrorResponse(String.format(SERVICE_UNAVAILABLE_ERROR_MESSAGE, serviceName));
//    }
//}
