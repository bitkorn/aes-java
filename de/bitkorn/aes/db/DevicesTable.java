package de.bitkorn.aes.db;

import de.bitkorn.aes.entity.DeviceEntity;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DevicesTable {
    private static final Logger LOG4J = Logger.getRootLogger();
    public static final String column = "RAW_Daten";
    DeviceEntity device;
    protected String tableName;

    public DevicesTable(DeviceEntity device) {
        this.device = device;
        tableName = this.device.getTableName();
        System.out.println("DevicesTable.tableName: " + tableName);
    }

    public ArrayList<String> getRawData() {
        ArrayList<String> raws = new ArrayList<>();
        try (ResultSet result = JdbcAccess.executeQuery(String.format("SELECT %s FROM %s", column, tableName))) {
            if (!result.first()) {
                return raws;
            }
            do {
                raws.add(result.getString(column));
            } while (result.next());
        } catch (SQLException e) {
            LOG4J.error(e.getMessage());
        }
        return raws;
    }
}
