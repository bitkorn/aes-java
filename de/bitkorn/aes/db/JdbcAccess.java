package de.bitkorn.aes.db;

import java.io.IOException;
import java.sql.*;
import org.apache.log4j.Logger;
import de.bitkorn.aes.Main;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class JdbcAccess {
    private static final Logger LOG4J = Logger.getRootLogger();

    private static Connection conn = null;

    protected static boolean connect() {
        try {
            if (null != conn && !conn.isClosed()) {
                return true;
            }
            conn = DriverManager.getConnection(JdbcAccess.parseXmlConfigForMysql());
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
            LOG4J.error("Can not connect to de.bitkorn.aes.db");
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

    /**
     * @return Something like that: "jdbc:mysql://localhost/dbname?user=root&password=secret"
     */
    public static String parseXmlConfigForMysql() {
        try {
            Document doc = new SAXBuilder().build(Main.CONF_XML_FQN);
            Element root = doc.getRootElement();
            Element sql = root.getChild("mysql");
            String dbUrl = sql.getChildText("db_url");
            String dbUser = sql.getChildText("db_user");
            String dbPasswd = sql.getChildText("db_passwd");
            String dbTimezone = sql.getChildText("time_zone");
            return dbUrl + "?user=" + dbUser + "&password=" + dbPasswd + "&serverTimezone=" + dbTimezone + "&useUnicode=true&useLegacyDatetimeCode=false";
        } catch (JDOMException | IOException e) {
            LOG4J.error(e.getMessage());
            System.exit(1);
        }
        return "";
    }
}
