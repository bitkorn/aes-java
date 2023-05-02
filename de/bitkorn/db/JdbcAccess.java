package de.bitkorn.db;

import java.sql.*;
import org.apache.log4j.Logger;
import de.bitkorn.aes.Main;

public class JdbcAccess {
    private static final Logger LOG4J = Logger.getRootLogger();

    private static Connection conn = null;

    protected static boolean connect() {
        try {
            if (null != conn && !conn.isClosed()) {
                return true;
            }
            conn = DriverManager.getConnection(Main.parseXmlConfigForMysql());
            if (!conn.isClosed()) {
                return true;
            }
        } catch (SQLException ex) {
            LOG4J.error("SQLException: " + ex.getMessage());
            LOG4J.error("SQLState: " + ex.getSQLState());
            LOG4J.error("VendorError: " + ex.getErrorCode());
            System.exit(1);
        }
        return false;
    }

    public static Connection getConn() {
        if (!connect()) {
            LOG4J.error("Can not connect to de.bitkorn.db");
            System.exit(1);
        }
        return conn;
    }

    public static Statement getStatement() throws SQLException {
        return getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        return getStatement().executeQuery(query);
    }

    public static int executeUpdate(String query) throws SQLException {
        return getStatement().executeUpdate(query);
    }
}
