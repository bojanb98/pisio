package net.etfbl.pisio.authservice.api;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.authservice.model.AuthenticationRequest;
import net.etfbl.pisio.authservice.model.AuthenticationResponse;
import net.etfbl.pisio.authservice.model.TokenValidityRequest;
import net.etfbl.pisio.authservice.model.TokenValidityResponse;
import net.etfbl.pisio.authservice.service.JwtService;
import net.etfbl.pisio.authservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    @ResponseBody
    public AuthenticationResponse authenticate(@RequestBody @Validated AuthenticationRequest request) {
        if (userService.isCredentialsValid(request.getUsername(), request.getPassword())) {
            String token = jwtService.generateToken(request.getUsername());
            return new AuthenticationResponse(token);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/auth")
    @ResponseBody
    public TokenValidityResponse isTokenValid(@RequestBody @Validated TokenValidityRequest request) {
        String username = jwtService.isTokenValid(request.getToken());
        return new TokenValidityResponse(username);
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody @Validated AuthenticationRequest request) {
        boolean isRegistered = userService.addNewUser(request.getUsername(), request.getPassword());
        if (!isRegistered) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
