package com.hyy.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveClient {

   private static final String DRIVER = "org.apache.hadoop.hive.jdbc.HiveDriver";

   private String connStr;
   private Connection conn;
   private Statement stmt;

   public HiveClient() {
      try {
         Class.forName(DRIVER);
         this.connStr = "jdbc:hive://10.12.12.71:10000/default";
         conn = DriverManager.getConnection(connStr, "", "");
         stmt = conn.createStatement();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public ResultSet executeQuery(String hql) {
      try {
         return this.stmt.executeQuery(hql);
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }

   public static void main(String[] args) throws SQLException {
      System.out.println("start...");
      HiveClient client = new HiveClient();
      ResultSet rs = client.executeQuery("show tables");
      while (rs.next()) {
         System.out.println(rs.getString(1));
      }
      System.out.println("end.");
   }
}
