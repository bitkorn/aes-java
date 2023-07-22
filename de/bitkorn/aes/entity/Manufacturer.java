package de.bitkorn.aes.entity;

import de.bitkorn.aes.AES;

import java.nio.charset.StandardCharsets;

/**
 * Load a manufacturer from a database.
 *
 * @todo Die Werte der verschiedenen Hersteller sollen aus einer Config kommen. Z.B. die Positionen der IV Teile oder der RAW Offset.
 */
public class Manufacturer extends AbstractManufacturer {

    /**
     * Where the encrypted part start
     * Mueller = 34
     */
    public int rawOffset = 30;

    @Override
    public int getEncOffset() {
        return this.rawOffset;
    }

    public String getLField() {
        return raw.substring(0, 2);
    }

    @Override
    public int getLFieldPlain() {
        byte[] bytes = AES.hexToBytes(getLField());
        System.out.print("Byte Array : ");
        for (byte aByte : bytes) {
            System.out.print(aByte + " ");
        }
        System.out.println();
        return bytes[0];
    }

    public String getCField() {
        return raw.substring(2, 4);
    }

    @Override
    public String getCFieldPlain() {
        return AES.hexToAscii(getCField());
    }

    public String getMField() {
        return raw.substring(4, 8);
    }

    @Override
    public String getMFieldPlain() {
        return AES.hexToAscii(getMField());
    }

    public String getAField() {
        return raw.substring(8, 20);
    }

    @Override
    public String getAFieldPlain() {
        return AES.hexToAscii(getAField());
    }

    @Override
    public String getCIField() {
        return raw.substring(24, 26);
    }

    @Override
    public String getAccessNo() {
        /*
         * substring(27, 29) OMS-Spec
         *
         */
        return raw.substring(38, 40);
    }

    /**
     * M Field + A Field + 8 bytes Acces No
     * Aktuell OK for Mehrle & Mueller
     * + "4D4D4D4D4D4D4D4D" ...sagt <a href="https://www.miller-alex.de/WMbus">miller-alex.de/WMbus</a>
     *
     * @return The IV as HEX String.
     */
    public String computeInitVector() {
        return getMField() + getAField() + getAccessNo().repeat(8);
    }
}
