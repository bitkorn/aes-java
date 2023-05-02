package de.bitkorn.entity;

import java.util.ArrayList;

public class DeviceEntity extends AbstractEntity {
    protected int index;
    protected String id;
    protected String manufactureCode;
    protected int medium;
    protected String aesKey;
    protected int building;
    protected int apartment;

    protected ArrayList<String> manufactories = new ArrayList<>();

    public DeviceEntity() {
    }

    /**
     * @param index           index
     * @param id              ID
     * @param manufactureCode Hersteller
     * @param medium          Medium
     * @param aesKey          AES key
     * @param building        Gebäude
     * @param apartment       Wohnung
     */
    public DeviceEntity(int index, String id, String manufactureCode, int medium, String aesKey, int building, int apartment) {
        this.index = index;
        this.id = id;
        this.manufactureCode = manufactureCode;
        this.medium = medium;
        this.aesKey = aesKey;
        this.building = building;
        this.apartment = apartment;
    }

    /**
     * @param index           index
     * @param id              ID
     * @param manufactureCode Hersteller
     * @param medium          Medium
     * @param aesKey          AES key
     * @param building        Gebäude
     * @param apartment       Wohnung
     */
    public void init(int index, String id, String manufactureCode, int medium, String aesKey, int building, int apartment) {
        this.index = index;
        this.id = id;
        this.manufactureCode = manufactureCode;
        this.medium = medium;
        this.aesKey = aesKey;
        this.building = building;
        this.apartment = apartment;
    }

    public void initManufactories() {
        manufactories.add("dme");
        manufactories.add("dwz");
        manufactories.add("efe");
        manufactories.add("eie");
        manufactories.add("ine");
        manufactories.add("lug");
        manufactories.add("meh");
        manufactories.add("qds");
        manufactories.add("weh");
    }

    @Override
    public boolean isValid() {
        return index > 0 && id.length() > 0;
    }

    /**
     * @return The table name of the device (lower case manufacture code + "_" + ID) or an empty string.
     */
    public String getTableName() {
        if (id.length() < 1 || manufactureCode.length() < 1) {
            return "";
        }
        return manufactureCode.toLowerCase() + "_" + id;
    }

    public int getIndex() {
        return index;
    }

    public String getId() {
        return id;
    }

    public String getManufactureCode() {
        return manufactureCode;
    }

    public int getMedium() {
        return medium;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public int getBuilding() {
        return building;
    }

    public int getApartment() {
        return apartment;
    }
}
