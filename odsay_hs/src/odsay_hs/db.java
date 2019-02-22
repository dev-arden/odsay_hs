package odsay_hs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/*Database Access Object*/
public class db {
   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://graduate.cbiz6ipldrs0.ap-northeast-2.rds.amazonaws.com:3306/graduate?useSSL=false";
   static final String USERNAME = "soo"; // DB ID
   static final String PASSWORD = "11111111"; // DB Password

   private Connection conn = null;
   private Statement stmt = null;
   private ResultSet rs = null;
   static int a = 0;// userinfo 행 수를 담을 변수
   static public ArrayList<String> buffer;
   static Vector<Integer> dbID = new Vector<Integer>();
   static Vector<Integer> transfer = new Vector<Integer>();
   static Vector<String> commonResult = new Vector<String>();
   static public ArrayList<Boolean> routeFlag = new ArrayList<Boolean>();
   static Vector<Integer> onlyone = new Vector<Integer>();

   public db() {
      System.out.print("DatabaseName Connection 연결 : ");
      try {
         Class.forName(JDBC_DRIVER);
         conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
         if (conn != null) {
            System.out.println("OK");
         } else {
            System.out.println("Failed");
         }

      } catch (ClassNotFoundException e) {
         System.out.println("Class Not Found Exection");
         e.printStackTrace();
      } catch (SQLException e) {
         System.out.println("SQL Exception");
         e.printStackTrace();
      }
   }// UserDAO

   // userinfo테이블에서 이름을 subwaynogada테이블에서 검색해서 해당하는 코드를 select하는 것,,

   public void userinfoSelect(int primarykey) {
      String query = "select code \r\n" + "from subwaynogada, userinfotest \r\n" + "where userinfotest.id = "
            + primarykey + " and subwaynogada.name = userinfotest.name";
      System.out.println(query);

      try {
         // Class.forName(JDBC_DRIVER);
         // conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
         stmt = conn.createStatement();
         // stmt.excuteQuery(SQL) : select
         // stmt.excuteUpdate(SQL) : insert, update, delete ..
         rs = stmt.executeQuery(query);
         while (rs.next()) {
            int code = rs.getInt(1);
            dbID.add(code);
            // System.out.println(code);
         }

         stmt.close();
         // conn.close();
      }
      // catch (ClassNotFoundException e) {
      // System.out.println("Class Not Found Exection");
      // }
      catch (SQLException e) {
         System.out.println("SQL Exception : " + e.getMessage());
      }
   }

   // userinfo의 행수를 뽑아내는 함수
   public void userCount() {
      String query = "SELECT COUNT(*) as cnt FROM userinfotest";

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(query);

         while (rs.next()) {
            // System.out.println("cnt : " + rs.getInt(1));
            a = rs.getInt(1);
         }
         stmt.close();
      }
      // catch (ClassNotFoundException e) {
      // System.out.println("Class Not Found Exection");
      // }
      catch (SQLException e) {
         System.out.println("SQL Exception : " + e.getMessage());
      }
   }

   // 환승 검사하는 함수
   public void isTransfer(long code) {
      String query = "select code\r\n" + "from subwaynogada\r\n" + "where name = (\r\n" + "      select name\r\n"
            + "        from subwaynogada\r\n" + "        where code = " + (int) code + "        )";
      // 여기서 db검사할때 코드를 인자로 받아와서 where 조건절에 저 파라미터인 코드를 넣어줘야 함

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(query);

         while (rs.next()) {
            // System.out.println("cnt : " + rs.getInt(1));
            // a = rs.getInt(1);
            transfer.add(rs.getInt(1));
         }
         stmt.close();
      }
      // catch (ClassNotFoundException e) {
      // System.out.println("Class Not Found Exection");
      // }
      catch (SQLException e) {
         System.out.println("SQL Exception : " + e.getMessage());
      }
   }

   // commonset안의 코드들에 해당하는 이름 뽑아내는 함수
   public void showCommonset(int commonMem) {
      int checkduplicate = 0;
      String query = "SELECT name FROM subwaynogada WHERE code=" + commonMem;

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(query);

         while (rs.next()) {
            String result = rs.getString("name");
            for (int i = 0; i < commonResult.size(); i++) {
               if (commonResult.get(i).equals(result)) {
                  checkduplicate++;
               }
            }
            if (checkduplicate == 0) {
               commonResult.add(result);
            }
         }
         stmt.close();
      } catch (SQLException e) {
         System.out.println("SQL Exception : " + e.getMessage());
      }
   }

   public void userRouteChoice() {
      String query = "SELECT userRoute FROM userinfo";

      try {
         // Class.forName(JDBC_DRIVER);
         // conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

         stmt = conn.createStatement();
         rs = stmt.executeQuery(query);

         while (rs.next()) {
            boolean flag = rs.getBoolean(1);// code = rs.getInt(1);
            routeFlag.add(flag);
         }
         stmt.close();
      } catch (SQLException e) {
         System.out.println("SQL Exception : " + e.getMessage());
      }
   }
   
   public void onlyonecode(String name) {
      String query = "select code\r\n" + 
            "from subwaynogada\r\n" + 
            "where name ="+"'"+ name +"'";
      //System.out.println(query);
      
      int check = 0;

      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(query);

         while (rs.next()) {
            check++;
            onlyone.add(rs.getInt(1));
            if(check == 1)
            {
               break;
            }
         }
         stmt.close();
      } catch (SQLException e) {
         System.out.println("SQL Exception : " + e.getMessage());
      }
   }
   
   public String rankResult(int code) {
      String query = "select name\r\n" + 
            "from subwaynogada\r\n" + 
            "where code ="+ code;
      //System.out.println(query);
   
      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(query);

         while (rs.next()) {
            return rs.getString("name");
         }
         stmt.close();
      } catch (SQLException e) {
         System.out.println("SQL Exception : " + e.getMessage());
      }
      return null;
   }
}