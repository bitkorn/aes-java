package de.bitkorn.entity;

/**
 * Load a manufacturer from a database.
 */
public class Manufacturer {

    /**
     * Mueller = true
     */
    public boolean stripTwoStart = true;

    /**
     * Mueller = true
     */
    public boolean stripTwoEnd = true;
    public String ivMode;
    /**
     * Where the decrypted part ends in the raw
     * Mueller = 34
     */
    public int rawOffset = 34;

    /**
     * @deprecated erst die ToDo abarbeiten :)
     */
    public String computeInitVector(String raw) {
        /*
         * @todo switch(ivMode) {return herstellerspezifisch}
         *
         * Aktuell OK for Mehrle & Mueller
         */
        return raw.substring(4,20); // 0303030303030303 DCDCDCDCDCDCDCDC damit ist decrypted NULL
    }
}
