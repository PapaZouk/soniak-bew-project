package org.kainos.ea.api;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.TokenStreamFactory;
import com.nimbusds.jose.produce.JWSSignerFactory;
import com.nimbusds.jose.util.Base64URL;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultSignerFactory;
import io.jsonwebtoken.impl.crypto.JwtSigner;
import io.jsonwebtoken.impl.crypto.SignerFactory;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.kainos.ea.resources.EmployeeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.nimbusds.jose.util.Base64URL.encode;

public class EncryptionService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    private static final KeyPair keys = Keys.keyPairFor(SignatureAlgorithm.RS512);

    public String encryptionExample1()
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        Base64.Encoder encoder = Base64.getEncoder();
        String header = "{\"alg\":\"HS256\"}";
        String payload = "{\"sub\":\"Rafal\"}";

        String encodedHeader = encode(header).decodeToString();
        String encodedPayload = encode(payload).decodeToString();

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");

        SecretKey key = keyFactory.generateSecret(new PBEKeySpec("test".toCharArray(), "salt".getBytes(StandardCharsets.UTF_8), 1000, 256));

        byte[] signature = HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, key.getEncoded()).doFinal();

        String concatenated = encodedHeader + '.' + encodedPayload;
        Base64URL encode = encode(signature);
        String compact = concatenated + '.' + encode;

        LOG.info("### ENCRYPTED {}", compact);
        return compact;
    }

    public Map<String, String> singKey(String subject) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Map<String, String> result = new HashMap<>();
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomKey = new byte[32];
        secureRandom.nextBytes(randomKey);
        System.out.println(new String(randomKey));

        String keyValue = "yt0WnBpsuuKEBzl/SH0PWPljXg7q++AOZu4d4h/VwKRM3brgDikivceG0CJI5dbg";
        Key key = new SecretKeySpec(
                randomKey,
                HmacAlgorithms.HMAC_SHA_256.getName());
        String signature = Jwts.builder().setSubject(subject).signWith(key).compact();

        result.put(keyValue, signature);
        return result;
    }

    public Map<byte[], String> generateSignature(String message) throws NoSuchAlgorithmException {
        Map<String, Object> claims = new HashMap<>();
        Date createdDate = Calendar.getInstance().getTime();
        Date expireDate = Date.from(createdDate.toInstant().plus(8, ChronoUnit.HOURS));
        String keyValue = "yt0WnBpsuuKEBzl/SH0PWPljXg7q++AOZu4d4h/VwKRM3brgDikivceG0CJI5dbg";

        SecureRandom secureRandom = new SecureRandom();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        keyGenerator.init(256, secureRandom);

        SecretKey encoded = keyGenerator.generateKey();

        System.out.println(new String(keys.getPublic().getEncoded()));

        String signed = Jwts.builder()
                .setClaims(claims)
                .setSubject(message)
                .setIssuedAt(createdDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, keys.getPublic().getEncoded())
                .compact();
        Map<byte[], String> result = new HashMap<>();
        result.put(keys.getPublic().getEncoded(), signed);
        return result;
    }

    public String decryptMessage(String message) {
        return Jwts.parserBuilder()
                .setSigningKey(keys.getPublic().getEncoded())
                .build()
                .parseClaimsJws(message)
                .getBody()
                .getSubject();
    }

    public String buildHeader() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("someKey", "someValue");

        Map<String, Object> claims = new HashMap<>();
        claims.put("someClaim", "claimValue");
        return Jwts.builder()
                .setHeaderParam("hey", "yo!")
                .setHeader(headers)
                .setPayload("some payload")
                .setClaims(claims)
                .compact();
    }
}
