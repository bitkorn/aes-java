package de.bitkorn.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    protected String cipherType = "AES/CBC/NoPadding"; // AES/CBC/NoPadding
    protected Cipher cipher;
    protected String aesKey;
    protected String initVector;
    public int encryptedOffset;
    protected String encryptedString;

    public AES(String aesKey, String initVector) {
        this.aesKey = aesKey;
        this.initVector = initVector;
        try {
            cipher = Cipher.getInstance(cipherType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getInitVector() {
        return initVector;
    }

    public void setEncryptedOffset(int encryptedOffset) {
        this.encryptedOffset = encryptedOffset;
    }

    public void setEncryptedString(String encryptedString) {
        this.encryptedString = subStringBlock16(removeCRCFields(encryptedString).substring(encryptedOffset));
    }

    public String getEncryptedString() {
        return encryptedString;
    }

    protected String removeCRCFields(String hex) {
        StringBuilder sb = new StringBuilder(hex);
        sb.delete(106, 110);
        sb.delete(92, 96);
        sb.delete(56, 60);
        sb.delete(20, 24);
        return sb.toString();
    }

    protected String subStringBlock16(String in) {
        int length = in.length();
        if ((length % 16) != 0) {
            length -= length % 16;
        }
        return in.substring(0, length);
    }

    public String encrypt(String original) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(hexToBytes(initVector));
            SecretKeySpec skeySpec = new SecretKeySpec(hexToBytes(aesKey), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(hexToBytes(original));
            return bytesToHex(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String decrypt() {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(hexToBytes(initVector)); // StandardCharsets.UTF_8
            SecretKeySpec skeySpec = new SecretKeySpec(hexToBytes(aesKey), "AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
            byte[] original = cipher.doFinal(hexToBytes(encryptedString));
            return bytesToHex(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // convert bytes to hex string
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    // convert hex string to bytes
//    public static byte[] hexToBytes(final String hex) {
//        if ((hex.length() % 2) != 0)
//            throw new IllegalArgumentException("Input string must contain an even number of characters");
//        final byte result[] = new byte[hex.length() / 2];
//        final char enc[] = hex.toCharArray();
//        for (int i = 0; i < enc.length; i += 2) {
//            StringBuilder curr = new StringBuilder(2);
//            curr.append(enc[i]).append(enc[i + 1]);
//            result[i / 2] = (byte) Integer.parseInt(curr.toString(), 16);
//        }
//        return result;
//    }

    public static byte[] hexToBytes(final String hex) {
        if ((hex.length() % 2) != 0)
            throw new IllegalArgumentException("Input string must contain an even number of characters");
        final byte result[] = new byte[hex.length() / 2];
        for (int i = 0; i < result.length; i++) {
            int index = i * 2;
            result[i] = (byte) Integer.parseInt(hex.substring(index, index + 2), 16);
        }
        return result;
    }

    public static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }
}
