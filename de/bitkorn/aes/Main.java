package de.bitkorn.aes;

import de.bitkorn.aes.entity.Manufacturer;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.text.SimpleDateFormat;

/**
 * <a href="https://www.javacodegeeks.com/2018/03/aes-encryption-and-decryption-in-javacbc-mode.html">javacodegeeks.com</a>
 * <a href="https://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html">Cipher types</a>
 */
public class Main {
    private static final Logger LOG4J = Logger.getRootLogger();
    public static final String FILE_SEPERATOR = "/"; // CodeSource auch unter Windows ein Slash
    public static final String CONFIG_DIRECTORY = getConfigDirectory();
    public static final String CONF_XML_FQN = CONFIG_DIRECTORY + "aes.xml";

    public static final SimpleDateFormat LOGTIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss::S");
    public static final SimpleDateFormat DURATIONTIME_FORMAT = new SimpleDateFormat("HH:mm:ss::S");
    public static final int BLOCK_LENGTH = 32;

    /**
     * @return absolute Pfad zum JAR-File plus endenden Slash
     */
    private static String getConfigDirectory() {
        CodeSource cs = Main.class.getProtectionDomain().getCodeSource();
        String myPath = "";
        try {
            myPath = cs.getLocation().toURI().getPath();
        } catch (URISyntaxException ex) {
            LOG4J.error(ex.getMessage());
            System.exit(1);
        }
        myPath = myPath.substring(0, myPath.lastIndexOf(FILE_SEPERATOR) + 1);
        File f = new File(myPath);
        if (!f.exists()) {
            LOG4J.error("Path does not exist: " + myPath);
            System.exit(1);
        }
        return myPath;
    }

    private static void logging() {
        try {
            SimpleLayout layout = new SimpleLayout();
            FileAppender fileAppender = new FileAppender(layout, CONFIG_DIRECTORY + "aes.log", false);
            LOG4J.addAppender(fileAppender);
            // ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF:
            LOG4J.setLevel(Level.ALL);
        } catch (Exception ex) {
            LOG4J.error(ex.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("usage: java filename.jar AES_key raw");
            System.exit(1);
        }
        String aesKey = args[0];
        String raw = args[1];
        logging();
        handleParams(aesKey, raw);
    }

    public static void handleParams(String aesKey, String raw) {
        Manufacturer manufac = new Manufacturer();
        manufac.setRaw(raw);
        AES aes = new AES(aesKey, manufac.computeInitVector());
        System.out.println("key: " + aesKey);
        System.out.println("raw: " + raw);
        System.out.println("IV: " + aes.getInitVector());
        System.out.println("L-Field: " + manufac.getLField());
        System.out.println("C-Field: " + manufac.getCField());
        System.out.println("M-Field: " + manufac.getMField());
        System.out.println("A-Field: " + manufac.getAField());
        System.out.println("CI-Field: " + manufac.getCIField());
        System.out.println("Access NO (IV pad): " + manufac.getAccessNo());
        aes.setEncryptedOffset(manufac.getEncOffset());
        aes.setEncryptedString(raw);
        System.out.println("encrypted: " + aes.getEncryptedString());
        System.out.println("decrypted: " + aes.decrypt());
    }
}