/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author Thanh Dang
 */

public class DBContext {
    private String serverName, dbName, portNumber, username, password, imagePath, fullPath;
    
    public DBContext() throws Exception {
       try {
           InitialContext initial = new InitialContext();
           Object obj = initial.lookup("java:comp/env");
           Context context = (Context) obj;
           
           serverName = context.lookup("serverName").toString();
           dbName = context.lookup("dbName").toString();
           portNumber = context.lookup("portNumber").toString();
           imagePath = context.lookup("imagePath").toString();
           fullPath = context.lookup("fullPath").toString();
           username = context.lookup("username").toString();
           password = context.lookup("password").toString();
       } catch (Exception e) {
           throw e;
       }
    }
    
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName + ";";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, username, password);
    }
    
    public void closeConnection(ResultSet rs, PreparedStatement ps, Connection connection) throws SQLException {
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
        if (ps != null && !ps.isClosed()) {
            ps.close();
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    
    public String getImagePath() throws Exception {
        return imagePath;
    }
    
    public String getFullPath() throws Exception {
        return fullPath;
    }
}
