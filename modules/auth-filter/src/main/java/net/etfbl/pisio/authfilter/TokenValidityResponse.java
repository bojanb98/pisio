package net.etfbl.pisio.authfilter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidityResponse {

    private String username;

    public boolean isTokenValid() {
        return username != null && !username.isBlank();
    }
}
