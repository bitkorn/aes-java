package de.bitkorn.aes.test;

import de.bitkorn.aes.AES;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Test {
    private static final String key = "B8AA9688DDD6528E86D8EEB45F866908";
    private static final String IV = "C5250301101055084D4D4D4D4D4D4D4D";
    private static final String encrypted = "3F1EC0BB524BEEA88234B44D7D9F7888010E24593CAD21E27CE9E15AD82E215235AB08041AC46F6E19B8BF8A0BCA66840E3B7288699BDB9F7E81A85F3152E76161AEFEFCBF9F8F5A067161DBDF48D9EEE7091DEAA6C7D78CEC1E5831E3372A98";
    // convert bytes to hex string
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) {
        byte[] key_bytes = AES.hexToBytes(key);
        byte[] IV_bytes = AES.hexToBytes(IV);
        byte[] encrypted_bytes = AES.hexToBytes(encrypted);
        byte[] decrypted_bytes = {};
        System.out.println("Key:       " + AES.bytesToHex(key_bytes));
        System.out.println("IV:        " + AES.bytesToHex(IV_bytes));
        System.out.println("Encrypted: " + AES.bytesToHex(encrypted_bytes));
        // AES decryption
        try {
            String cipherType = "AES/CBC/NoPadding";
            Cipher cipher;
            IvParameterSpec ivSpec = new IvParameterSpec(IV_bytes);
            SecretKeySpec skeySpec = new SecretKeySpec(key_bytes, "AES");
            cipher = Cipher.getInstance(cipherType);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
            decrypted_bytes = cipher.doFinal(encrypted_bytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Decrypted: " + AES.bytesToHex(decrypted_bytes));
    }
}