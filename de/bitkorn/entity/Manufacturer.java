package de.bitkorn.entity;

/**
 * Load a manufacturer from a database.
 */
public class Manufacturer {

    /**
     * Mueller = true
     */
    public boolean stripTwoStart = false;

    /**
     * Mueller = true
     */
    public boolean stripTwoEnd = false;
    public String ivMode;
    /**
     * Where the decrypted part ends in the raw
     * Mueller = 34
     */
    public int rawOffset = 30;

    /**
     * @deprecated erst die ToDo abarbeiten :)
     */
    public String computeInitVector(String raw) {
        /*
         * @todo switch(ivMode) {return herstellerspezifisch}
         *
         * Aktuell OK for Mehrle & Mueller
         */
        String iv = raw.substring(4, 8) + raw.substring(22, 30) + raw.substring(34, 38); // + 4D4D4D4D4D4D4D4D ...sagt https://www.miller-alex.de/WMbus
        System.out.println("IV: " + iv);
        // return raw.substring(4,20); // 0303030303030303 DCDCDCDCDCDCDCDC damit ist decrypted NULL
        return iv; // 5050505050505050 https://www.miller-alex.de/WMbus
    }
}
