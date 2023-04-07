package com.project.projectapi.services.ServiceImpl.AuthServices;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.projectapi.model.entities.User;
import com.project.projectapi.repos.UserRepository;
import com.project.projectapi.services.Interface.AuthServices.AuthService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    private Algorithm algorithm;
    private UserRepository userRepository;
    @Value("123456")
    private String jwtSecret;

    @Value("15000")
    private Long expiresInSeconds;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setAlgorithm(){
        algorithm = Algorithm.HMAC256(jwtSecret);
    }

    public String createToken(User user){
        JWTCreator.Builder builder = JWT.create()
                .withClaim("userId", user.getId())
                .withIssuedAt(new Date())
                .withIssuer("Dans-Multi-Pro");
        if (expiresInSeconds != null){
            builder.withExpiresAt(new Date(System.currentTimeMillis() + expiresInSeconds * 1000));
        }
        return builder.sign(algorithm);
    }

    @Override
    public User getUserFromToken(String token) throws JWTVerificationException, TokenExpiredException {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("Dans-Multi-Pro")
                .build();
        User user = new User();
        try{
            DecodedJWT jwt = verifier.verify(token);
            Long idUser = jwt.getClaim("userId").asLong();
            user = userRepository.findWithId(idUser);

            if(Objects.isNull(user)){
                throw new TokenExpiredException("token has expired");
            }
//            return user;
        } catch (TokenExpiredException tokenExpiredException){
            System.out.println("token has expired");
            throw new TokenExpiredException(tokenExpiredException.getMessage());
        } catch (JWTVerificationException e){
            throw new JWTVerificationException(e.getMessage());
        }
        if(user == null)return null;
        return user;
    }
}
