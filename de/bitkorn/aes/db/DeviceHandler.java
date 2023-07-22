package de.bitkorn.aes.db;

import de.bitkorn.aes.AES;
import de.bitkorn.aes.Main;
import de.bitkorn.aes.entity.DeviceEntity;
import de.bitkorn.aes.entity.Manufacturer;

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
        Manufacturer manufac = new Manufacturer();
        System.out.println("AES key: " + deviceEntity.getAesKey());
        for (String raw : raws) {
            System.out.println("RAW: " + raw);
            manufac.setRaw(raw);
            AES aes = new AES(deviceEntity.getAesKey(), manufac.computeInitVector());
            /*
             * Getestet ist
             * - der komplette RAW
             * - der RAW minus LENGTH modulo BLOCK_LENGTH
             * - einzelner Block
             */
            int length = raw.length() - (raw.length() % Main.BLOCK_LENGTH);
            raw = raw.substring(0, length);
            System.out.println("RAW % BLOCK_LENGTH: " + raw);
            aes.setEncryptedOffset(30);
            aes.setEncryptedString(raw);
            System.out.println("decrypted: " + aes.decrypt());
        }
    }
}
