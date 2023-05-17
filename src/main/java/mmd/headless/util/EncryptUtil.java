package mmd.headless.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class EncryptUtil {

    private static final String KEY = "mmd";

    public static String alg = "AES/CBC/PKCS5Padding";
    private final String aesKey = "12345678910111213";
    private final String iv = aesKey.substring(0, 16); // 16byte

    /**
     * 비밀번호 암호화 모듈 SHA512
     */
    public static String encryptSHA512(String targetWord) throws Exception {
        String sha = "";
        targetWord = targetWord + KEY;
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-512");
            sh.update(targetWord.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            sha = sb.toString();

        } catch (Exception e) {
            sha = null;
        }

        return sha;
    }

    /**
     * 비밀번호 암호화 모듈 AES256
     */
    public String encryptAES256(String text) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * 비밀번호 복호화 모듈 AES256
     */
    public String decryptAES256(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
    }
}