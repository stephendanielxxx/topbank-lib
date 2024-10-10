package com.digimaster.jwt;

import com.digimaster.model.UserData;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JwtUtil {

    public Claims getClaims(final String token) {
        try {
            SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecretUtils.JWT_SECRET));
            Claims body = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    public String generateToken(String id) {
        Claims claims = Jwts.claims().setSubject(id);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + SecretUtils.tokenValidity;
        Date exp = new Date(expMillis);
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecretUtils.JWT_SECRET));

        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(secret, SignatureAlgorithm.HS512).compact();
    }

    public String generateToken(String id, long validity) {
        Claims claims = Jwts.claims().setSubject(id);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + validity;
        Date exp = new Date(expMillis);
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecretUtils.JWT_SECRET));

        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(secret, SignatureAlgorithm.HS512).compact();
    }

    public String generateToken(String id, int roleId, int instituteId, long validity) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecretUtils.JWT_SECRET));
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + validity;
        Date exp = new Date(expMillis);
        return Jwts.builder()
                .setSubject(id)
                .setExpiration(exp)
                .claim("roleId", roleId)
                .claim("instituteId", instituteId)
                .signWith(SignatureAlgorithm.HS512, secret )
                .compact();
    }

    public String generateRefreshToken(String id, int roleId, int instituteId, long validity) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecretUtils.JWT_SECRET_REFRESH));
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + SecretUtils.refreshTokenValidity;
        Date exp = new Date(expMillis);
        return Jwts.builder()
                .setSubject(id)
                .setExpiration(exp)
                .claim("roleId", roleId)
                .claim("instituteId", instituteId)
                .signWith(SignatureAlgorithm.HS512, secret )
                .compact();
    }

    public String generateRefreshToken(String id) {
        Claims claims = Jwts.claims().setSubject(id);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + SecretUtils.refreshTokenValidity;
        Date exp = new Date(expMillis);
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecretUtils.JWT_SECRET_REFRESH));

        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(secret, SignatureAlgorithm.HS512).compact();
    }

    public String generateRefreshToken(String id, long validity) {
        Claims claims = Jwts.claims().setSubject(id);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + validity;
        Date exp = new Date(expMillis);
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecretUtils.JWT_SECRET_REFRESH));

        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(secret, SignatureAlgorithm.HS512).compact();
    }

    public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Jwts.parser().setSigningKey(SecretUtils.JWT_SECRET).parseClaimsJws(token);
        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }

    public void validateRefreshToken(final String refreshToken) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Jwts.parser().setSigningKey(SecretUtils.JWT_SECRET_REFRESH).parseClaimsJws(refreshToken);
        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT refreshToken");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Expired JWT refreshToken");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT refreshToken");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }

    public UserData getTokenData(String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Claims body = Jwts.parser().setSigningKey(SecretUtils.JWT_SECRET).parseClaimsJws(token).getBody();
            int roleId = (int) body.get("roleId");
            int instituteId = (int) body.get("instituteId");
            int userId = Integer.parseInt(body.getSubject());
            return new UserData(userId, roleId, instituteId);
        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT refreshToken");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Expired JWT refreshToken");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT refreshToken");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }

    public UserData getRefreshTokenData(String refreshToken) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Claims body = Jwts.parser().setSigningKey(SecretUtils.JWT_SECRET_REFRESH).parseClaimsJws(refreshToken).getBody();
            int roleId = (int) body.get("roleId");
            int instituteId = (int) body.get("instituteId");
            int userId = Integer.parseInt(body.getSubject());
            return new UserData(userId, roleId, instituteId);
        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT refreshToken");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Expired JWT refreshToken");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT refreshToken");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }

}
