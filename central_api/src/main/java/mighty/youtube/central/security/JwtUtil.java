package mighty.youtube.central.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mighty.youtube.central.models.AppUser;
import mighty.youtube.central.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Autowired
    UserService userService;


    @Value("${central.security.secret.key}")
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

    public String decryptToken(String token){
        String credentials = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return credentials;
    }

    public boolean isValidToken(String token){

        String credentials = this.decryptToken(token);
        String email = credentials.split(":")[0];
        String password = credentials.split(":")[1];

        AppUser user = userService.getUserByEmail(email);

        if(user == null){
            return  false;
        }

        if (user.getPassword().equals(password)){
            return  true;
        }

        return  false;
    }


}
