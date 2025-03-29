package mighty.youtube.central.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {


    @Value("$(central.security.key)")
    String secretKey;

    Long expirationTime = 1200000L;

    public String generateToken (String credentials){

        String jwtToken = Jwts.builder().setSubject(credentials)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

                return jwtToken;
    }
}
