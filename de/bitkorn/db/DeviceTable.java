package de.bitkorn.db;

import de.bitkorn.entity.DeviceEntity;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceTable {
    private static final Logger LOG4J = Logger.getRootLogger();
    private static Connection conn = null;
    public static final String table = "device";

    public DeviceTable() {
        DeviceTable.conn = JdbcAccess.getConn();
    }

    public DeviceEntity getDevice(String deviceId) {
        DeviceEntity entity = new DeviceEntity();
        try (ResultSet result = JdbcAccess.executeQuery(String.format("SELECT * FROM %s WHERE ID=%s", DeviceTable.table, deviceId))) {
            if (!result.first()) {
                return entity;
            }
            entity.init(result.getInt("index")
                    , result.getString("ID")
                    , result.getString("Hersteller")
                    , result.getInt("Medium")
                    , result.getString("AES")
                    , result.getInt("Gebaude")
                    , result.getInt("Wohnung"));
        } catch (SQLException e) {
            LOG4J.error(e.getMessage());
        }
        return entity;
    }
}
