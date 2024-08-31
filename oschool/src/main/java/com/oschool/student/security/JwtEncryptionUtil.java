/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class JwtEncryptionUtil {


    @Value("${pre.enc.code}")
    protected static String preEncryptionKey ="faaba824718011eeb9620242ac120002";

    @Value("${post.enc.code}")
    protected static String postEncryptionKey = "dd6acda2718111rfb962jda8asd" ;

    /**
     * Encrypts a JWT (JSON Web Token) by encoding it in Base64 and appending pre and post encryption keys.
     *
     * @param jwt The JWT to be encrypted.
     * @return The encrypted JWT as a String.
     */
    public static String encrypt(String jwt) {
        try {
            // Encode the JWT in Base64 format
            String base64EncodedJwt = Base64.getEncoder().encodeToString(jwt.getBytes());

            // Append preEncryptionKey and postEncryptionKey to the encoded JWT
            return preEncryptionKey + base64EncodedJwt + postEncryptionKey;
        } catch (Exception e) {
            // Handle any exceptions by throwing a RuntimeException with the error message
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Decrypts an encrypted JWT (JSON Web Token) by removing the pre and post encryption keys
     * and decoding the remaining Base64-encoded string.
     *
     * @param encrypted The encrypted JWT to be decrypted.
     * @return The decrypted JWT as a String if it was properly encrypted, otherwise the original input.
     */
    public static String decrypt(String encrypted) {
        try {
            // Check if the encrypted string starts with preEncryptionKey and ends with postEncryptionKey
            if (encrypted.startsWith(preEncryptionKey) && encrypted.endsWith(postEncryptionKey)) {

                // Remove the preEncryptionKey and postEncryptionKey from the encrypted string
                String trimmedJwt = encrypted.substring(preEncryptionKey.length(), encrypted.length() - postEncryptionKey.length());

                // Decode the trimmed Base64-encoded JWT
                byte[] decodedBytes = Base64.getDecoder().decode(trimmedJwt);

                // Convert the decoded bytes back to a String and return
                return new String(decodedBytes);
            } else {
                // If not properly encrypted, return the original input
                return encrypted;
            }
        } catch (Exception e) {
            // Handle any exceptions by throwing a RuntimeException with the error message
            throw new RuntimeException(e.getMessage());
        }
    }


}