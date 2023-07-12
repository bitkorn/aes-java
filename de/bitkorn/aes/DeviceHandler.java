package de.bitkorn.aes;

import de.bitkorn.db.DeviceTable;
import de.bitkorn.db.DevicesTable;
import de.bitkorn.entity.DeviceEntity;
import de.bitkorn.entity.Manufacturer;

import java.util.ArrayList;

public class DeviceHandler {

    /**
     * Wenn es dann gebraucht wird, mach ich es fertig :)
     */
    public static void handle(String deviceId, String aesKey) {
        DeviceTable dt = new DeviceTable();
        DeviceEntity deviceEntity = dt.getDevice(deviceId);
        deviceEntity.setAesKey(aesKey);
        System.out.println("Device: " + deviceEntity.getManufactureCode() + " " + deviceEntity.getId());
        DevicesTable devicesTable = new DevicesTable(deviceEntity);
        ArrayList<String> raws = devicesTable.getRawData();
        /*
         * @todo Den Manufacturer in Abhängigkeit vom Device, aus der Datenbank laden.
         */
        Manufacturer manufac = new Manufacturer();
        System.out.println("AES key: " + deviceEntity.getAesKey());
        AES aes = new AES(deviceEntity.getAesKey());
        for (String raw : raws) {
            System.out.println("RAW: " + raw);
            if (manufac.stripTwoStart) {
                raw = raw.substring(2);
            }
            if (manufac.stripTwoEnd) {
                raw = raw.substring(0, raw.length() - 2);
            }
            System.out.println("RAW striped: " + raw);

            aes.setInitVector(manufac.computeInitVector(raw));
            System.out.println("initVector: " + aes.initVector);

//            if (manufac.rawOffset > 0) {
//                raw = raw.substring(manufac.rawOffset);
//            }
//            System.out.println("RAW with offset: " + raw);
            /*
             * Getestet ist
             * - der komplette RAW
             * - der RAW minus LENGTH modulo BLOCK_LENGTH
             * - einzelner Block
             * @todo In Abhängigkeit vom Manufacturer in Blöcke aufteilen und bei jedem Block ein neuen IV aus dem vorigen Block bilden
             */
            int length = raw.length() - (raw.length() % Main.BLOCK_LENGTH);
            raw = raw.substring(0, length);
            System.out.println("RAW % BLOCK_LENGTH: " + raw);
            String decrypted = aes.decrypt(raw);
            System.out.println("decrypted: " + AES.convertStringToHex(decrypted));
        }
    }
}
