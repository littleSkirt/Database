import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.sql.*;

public class Loader4Transaction {
    private static Connection con = null;
    private static Statement stmt = null;

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
//            if (con != null) {
//                System.out.println("Successfully connected to the database "
//                        + prop.getProperty("database") + " as " + prop.getProperty("user"));
//            }
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
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

    private static List<String> loadTXTFile() {
        try {
            return Files.readAllLines(Path.of("/Users/huangshuo/Downloads/second.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadData(String line) {
        String[] lineData = line.split(";");
        if (lineData[1].contains("'")) {
            lineData[1] = lineData[1].replace("'", "''");
        }
        String sql = String.format("INSERT INTO Second_Reply (postID,firstAuthor,secondaryContent,secondaryStars,secondaryAuthor) " +
                        "VALUES (%s,'%s', '%s', %s, %s);",
                lineData[0], lineData[1], lineData[2], lineData[3], lineData[4]);
        try {
            if (con != null) {
                stmt = con.createStatement();
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearDataInTable() {
        Statement stmt0;
        if (con != null) {
            try {
                stmt0 = con.createStatement();
                stmt0.executeUpdate("drop table Second_Reply ;");
                stmt0.executeUpdate("create table if not exists Second_Reply(\n" +
                        "PostID int,\n" +
                        "firstAuthor varchar(30),\n" +
                        "secondaryContent varchar(500),\n" +
                        "secondaryStars int,\n"+
                        "secondaryAuthor varchar(30),\n"+
                        "foreign key (postID,firstAuthor) references First_Reply(postID,firstAuthor)\n" +
                        ");");
                stmt0.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void main(String[] args) {
        Properties prop = loadDBUser();
        List<String> lines = loadTXTFile();

        // Empty target table
        openDB(prop);
        clearDataInTable();
        closeDB();

        int cnt = 0;

        long start = System.currentTimeMillis();
        for (String line : lines) {
            if (line.startsWith("PostID"))
                continue; // skip the first line
            openDB(prop);
            loadData(line);//do insert command
            closeDB();

            cnt++;
            if (cnt % 1000 == 0) {
                System.out.println("insert " + 1000 + " data successfully!");
            }
        }

        long end = System.currentTimeMillis();
        System.out.println(cnt + " records successfully loaded");
        System.out.println("Loading speed : " + (cnt * 1000L) / (end - start) + " records/s");
    }
}



