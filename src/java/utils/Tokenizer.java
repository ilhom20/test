/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;




import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class Tokenizer {

//    private static Logger _logger = Logger.getLogger(Tokenizer.class);

    public static String encript(String text) {
        try {
            return new BASE64Encoder().encode(text.getBytes("UTF-8"));
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static String decript(String text) {
        try {
            return new String(new BASE64Decoder().decodeBuffer(text), "UTF-8");
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static String getHash(String message) {
        try {
            return Utils.getStringDigest(message + System.currentTimeMillis());
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static String getSign() {
        try {
            return Jwts.builder()
                    .setIssuer("sighn")
                    .setSubject("login")
                    .setExpiration(new Date())
                    .signWith(SignatureAlgorithm.HS256, "gasbalance")
                    .compact().split("\\.")[2];
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static String getBasicToken(String token_id, String jsonData) {
        try {
            return Jwts.builder()
                    .setId(token_id)
                    .setSubject(jsonData)
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30 * 01))
                    .signWith(SignatureAlgorithm.HS256, "gasbalance")
                    .compact();
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static String getBarerToken(String token_id, String jsonData) {
        try {
            return Jwts.builder()
                    .setId(token_id)
                    .setSubject(jsonData)
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 05 * 01))
                    .signWith(SignatureAlgorithm.HS256, "hgtbalance")
                    .compact();
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static Claims parseBasicToken(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey("gasbalance")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static Claims parseBarerToken(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey("hgtbalance")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

}
