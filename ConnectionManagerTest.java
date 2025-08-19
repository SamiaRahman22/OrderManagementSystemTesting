package com.mycompany.cse430_miniproject;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConnectionManagerTest {

    private Connection conn;

    @BeforeEach
    public void setUp() {
        conn = ConnectionManager.getConnection();
    }

    @AfterEach
    public void tearDown() {
        ConnectionManager.CloseConnection();
        conn = null;
    }

    //Valid connection should not be null
    @Test
    public void testGetConnection_NotNull() {
        assertNotNull(conn, "Connection should not be null if DB is configured correctly");
    }

    //ConnectionManager should return the same instance (Singleton)
    @Test
    public void testGetConnection_SameInstance() {
        Connection conn2 = ConnectionManager.getConnection();
        assertSame(conn, conn2, "Both calls should return the same connection instance");
    }

    //Closing connection should make it unusable
    @Test
    public void testCloseConnection() {
        assertNotNull(conn);
        ConnectionManager.CloseConnection();
        assertThrows(SQLException.class, () -> conn.createStatement(),
            "Using a closed connection should throw SQLException");
    }

    //Multiple close calls should not break anything
    @Test
    public void testCloseConnectionMultipleTimes() {
        ConnectionManager.CloseConnection();
        ConnectionManager.CloseConnection(); // Should not throw error
        assertTrue(true, "Closing multiple times should not cause exception");
    }

    //Corner case: Connection should remain valid if not explicitly closed
    @Test
    public void testConnectionStillValidWithoutClose() throws SQLException {
        assertFalse(conn.isClosed(), "Connection should remain open until closed explicitly");
    }

    //Corner case: Invalid properties should not crash the system
    @Test
    public void testConnectionInvalidProperties() {
        System.setProperty("mysql.url", "jdbc:mysql://invalid:3306/fake_db");
        Connection badConn = ConnectionManager.getConnection();
        assertNotNull(badConn, "Even with invalid properties, should handle gracefully");
    }
}
