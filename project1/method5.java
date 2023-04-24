import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.sql.*;

public class ttest {
    private static final int BATCH_SIZE = 1000;
    private static Connection con = null;
    private static PreparedStatement stmt = null;

    private static void openDB(Properties prop) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + prop.getProperty("host") + "/" + prop.getProperty("database");
        try {
            con = DriverManager.getConnection(url, prop);
            if (con != null) {
                System.out.println("Successfully connected to the database "
                        + prop.getProperty("database") + " as " + prop.getProperty("user"));
                con.setAutoCommit(false);
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }


    public static void setPrepareStatement_s() {
        try {
            stmt = con.prepareStatement("INSERT INTO Second_Reply (postID,firstAuthor,secondaryContent,secondaryStars,secondaryAuthor) " +
                    "VALUES (?,?,?,?,?);");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception ignored) {
            }
        }
    }

    private static Properties loadDBUser() {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream("/Users/huangshuo/IdeaProjects/ca307/resources/dbUser.properties")));
            return properties;
        } catch (IOException e) {
            System.err.println("can not find db user file");
            throw new RuntimeException(e);
        }
    }


    private static List<String> loadCSVFile() {
        try {
            return Files.readAllLines(Path.of("/Users/huangshuo/Downloads/second.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private static void loadData_s(String line) {
        String[] lineData = line.split(";");
        if (con != null) {
            try {
                stmt.setInt(1, Integer.parseInt(lineData[0]));
                stmt.setString(2, lineData[1]);
                stmt.setString(3, lineData[2]);
                stmt.setInt(4, Integer.parseInt(lineData[3]));
                stmt.setString(5, lineData[4]);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }



    public static void clearDataInTable_s() {
        Statement stmt0;
        if (con != null) {
            try {
                stmt0 = con.createStatement();
                stmt0.executeUpdate("drop table Second_Reply ;");
                con.commit();
                stmt0.executeUpdate("create table if not exists Second_Reply(\n" +
                        "PostID int,\n" +
                        "firstAuthor varchar(30),\n" +
                        "secondaryContent varchar(500),\n" +
                        "secondaryStars int,\n"+
                        "secondaryAuthor varchar(30),\n"+
                        "foreign key (postID,firstAuthor) references First_Reply(postID,firstAuthor)\n" +
                        ");");
                con.commit();
                stmt0.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }



    public static void main(String[] args) {

        Properties prop = loadDBUser();
        List<String> lines = loadCSVFile();

        // Empty target table
        openDB(prop);
        clearDataInTable_s();
        closeDB();


        int cnt = 0;
        long start = System.currentTimeMillis();
        openDB(prop);
        setPrepareStatement_s();
        try {
            for (String line : lines) {
                if (line.startsWith("PostID"))
                    continue; // skip the first line
                loadData_s(line);//do insert command
                if (cnt % BATCH_SIZE == 0) {
                    stmt.executeBatch();
                    System.out.println("insert " + BATCH_SIZE + " data successfully!");
                    stmt.clearBatch();
                }
                cnt++;
            }

            if (cnt % BATCH_SIZE != 0) {
                stmt.executeBatch();
                System.out.println("insert " + cnt % BATCH_SIZE + " data successfully!");

            }
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeDB();
        long end = System.currentTimeMillis();
        System.out.println(cnt + " records successfully loaded");
        System.out.println("Time consuming ：" + (end-start) + "s");
        System.out.println("Loading speed : " + (cnt * 1000L) / (end - start) + " records/s");

    }
}

