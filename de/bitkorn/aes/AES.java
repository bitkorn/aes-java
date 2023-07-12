package de.bitkorn.aes;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AES {

    protected String cipherType = "AES/CBC/NoPadding"; // AES/CBC/NoPadding
    protected Cipher cipher;
    protected String aesKey;
    protected String initVector;

    public AES(String aesKey) {
        this.aesKey = aesKey;
        try {
            cipher = Cipher.getInstance(cipherType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setInitVector(String initVector) {
        this.initVector = initVector;
    }

    public String encrypt(String original) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");

            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(original.getBytes());
            return new String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String decrypt(String encrypted) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(initVector.getBytes()); // StandardCharsets.UTF_8
            SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
//            SecretKeySpec skeySpec = new SecretKeySpec(convertStringToBinary(aesKey).getBytes(), "AES");

            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
            byte[] original = cipher.doFinal(encrypted.getBytes());
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private String xcrypt(int decryptMode, IvParameterSpec ivSpec, SecretKeySpec skeySpec, String data) {
        try {
            cipher.init(decryptMode, skeySpec, ivSpec);
            byte[] original = cipher.doFinal(data.getBytes());
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /*
     * <a href="https://gist.github.com/mythosil/1313541/2076d2f5365394190865e12e7f25cc7dfbb0466f">gist</a>
     * ######################################
     */

    public byte[] encryptBytes(byte[] data) {
        return processBytes(Cipher.ENCRYPT_MODE, aesKey, initVector, data);
    }

    public byte[] decryptBytes(byte[] data) {
        return processBytes(Cipher.DECRYPT_MODE, aesKey, initVector, data);
    }

    private byte[] processBytes(int mode, String skey, String iv, byte[] data) {
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        SecretKeySpec key = new SecretKeySpec(skey.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance(cipherType);
            cipher.init(mode, key, ivSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /*
     * https://mkyong.com/java/java-convert-string-to-binary/
     */

    public static String convertStringToBinary(String input) {

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")                         // zero pads
            );
        }
        return result.toString();

    }

    public static String prettyBinary(String binary, int blockSize, String separator) {

        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < binary.length()) {
            result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
            index += blockSize;
        }

        return result.stream().collect(Collectors.joining(separator));
    }

    /*
     * https://mkyong.com/java/how-to-convert-hex-to-ascii-in-java/
     */

    public static String convertStringToHex(String str) {

        // display in uppercase
        //char[] chars = Hex.encodeHex(str.getBytes(StandardCharsets.UTF_8), false);

        // display in lowercase, default
        char[] chars = Hex.encodeHex(str.getBytes(StandardCharsets.UTF_8));

        return String.valueOf(chars);
    }

    public static String convertHexToString(String hex) {

        String result = "";
        try {
            byte[] bytes = Hex.decodeHex(hex);
            result = new String(bytes, StandardCharsets.UTF_8);
        } catch (DecoderException e) {
            throw new IllegalArgumentException("Invalid Hex format!");
        }
        return result;
    }
}
