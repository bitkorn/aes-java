package de.bitkorn.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AES {

    protected String cipherType = "AES/CBC/NoPadding";
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
            IvParameterSpec ivSpec = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");

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
}
