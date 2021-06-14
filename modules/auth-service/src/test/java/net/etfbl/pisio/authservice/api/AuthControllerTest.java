package net.etfbl.pisio.authservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.etfbl.pisio.authservice.model.AuthenticationRequest;
import net.etfbl.pisio.authservice.model.AuthenticationResponse;
import net.etfbl.pisio.authservice.model.TokenValidityRequest;
import net.etfbl.pisio.authservice.model.TokenValidityResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void loginWithValidCredentialsAndCheckTokenValidity() throws Exception {
        loginUserAndCheckToken("user", "useruser");
    }

    @Test
    public void registerAndLoginNewUser() throws Exception {
        String newUsername = "newuser";
        String newPassword = "newusernewuser";

        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername(newUsername);
        authenticationRequest.setPassword(newPassword);

        mockMvc
                .perform(
                        post("/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest))
                )
                .andExpect(status().isOk());

        loginUserAndCheckToken(newUsername, newPassword);
    }

    private void loginUserAndCheckToken(String username, String password) throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(username);
        request.setPassword(password);
        MvcResult mvcResult = mockMvc
                .perform(
                        post("/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response, "Response body must not be null");
        AuthenticationResponse authenticationResponse = objectMapper.readValue(response, AuthenticationResponse.class);

        TokenValidityRequest tokenValidityRequest = new TokenValidityRequest();
        tokenValidityRequest.setToken(authenticationResponse.getJwtToken());

        mvcResult = mockMvc
                .perform(
                        post("/auth")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(tokenValidityRequest))
                )
                .andExpect(status().isOk())
                .andReturn();

        response = mvcResult.getResponse().getContentAsString();
        TokenValidityResponse validityResponse = objectMapper.readValue(response, TokenValidityResponse.class);
        assertTrue(validityResponse.isTokenValid(), "JWT token is invalid");
    }
}
