//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.List;
//import java.util.Properties;
//import java.sql.*;
//
//public class DataImport {
//    private static final int BATCH_SIZE = 1000;
//    private static Connection con = null;
//    private static PreparedStatement stmt = null;
//
//    private static void openDB(Properties prop) {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (Exception e) {
//            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
//            System.exit(1);
//        }
//        String url = "jdbc:postgresql://" + prop.getProperty("host") + ":"+prop.getProperty("port") + "/"+prop.getProperty("database");
//        try {
//            con = DriverManager.getConnection(url, prop);
//            if (con != null) {
//                System.out.println("Successfully connected to the database "
//                        + prop.getProperty("database") + " as " + prop.getProperty("user"));
//                con.setAutoCommit(false);
//            }
//        } catch (SQLException e) {
//            System.err.println("Database connection failed");
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }
//    }
//
//    //post
//    public static void setPrepareStatement_p() {
//        try {
//            stmt = con.prepareStatement("INSERT INTO post (postID, title,content, postTime,postCity,postCountry) " +
//                    "VALUES (?,?,?,?,?,?);");
//        } catch (SQLException e) {
//            System.err.println("Insert statement failed");
//            System.err.println(e.getMessage());
//            closeDB();
//            System.exit(1);
//        }
//    }
//    //favorite
//    public static void setPrepareStatement_favor() {
//        try {
//            stmt = con.prepareStatement("INSERT INTO favorite_post (postID, favorite) " +
//                    "VALUES (?,?);");
//        } catch (SQLException e) {
//            System.err.println("Insert statement failed");
//            System.err.println(e.getMessage());
//            closeDB();
//            System.exit(1);
//        }
//    }
//    //share
//    public static void setPrepareStatement_share() {
//        try {
//            stmt = con.prepareStatement("INSERT INTO share_post (postID, share) " +
//                    "VALUES (?,?);");
//        } catch (SQLException e) {
//            System.err.println("Insert statement failed");
//            System.err.println(e.getMessage());
//            closeDB();
//            System.exit(1);
//        }
//    }
//    //like
//    public static void setPrepareStatement_like() {
//        try {
//            stmt = con.prepareStatement("INSERT INTO like_post (postID, liked) " +
//                    "VALUES (?,?);");
//        } catch (SQLException e) {
//            System.err.println("Insert statement failed");
//            System.err.println(e.getMessage());
//            closeDB();
//            System.exit(1);
//        }
//    }
//    //category
//    public static void setPrepareStatement_cate() {
//        try {
//            stmt = con.prepareStatement("INSERT INTO categories (postID, category) " +
//                    "VALUES (?,?);");
//        } catch (SQLException e) {
//            System.err.println("Insert statement failed");
//            System.err.println(e.getMessage());
//            closeDB();
//            System.exit(1);
//        }
//    }
//    //author
//    public static void setPrepareStatement_au() {
//        try {
//            stmt = con.prepareStatement("INSERT INTO author (postID, authorName,registrationTime,authorID, phone) " +
//                    "VALUES (?,?,?,?,?);");
//        } catch (SQLException e) {
//            System.err.println("Insert statement failed");
//            System.err.println(e.getMessage());
//            closeDB();
//            System.exit(1);
//        }
//    }
//    //follow
//    public static void setPrepareStatement_follow() {
//        try {
//            stmt = con.prepareStatement("INSERT INTO followed_by (authorID, follow) " +
//                    "VALUES (?,?);");
//        } catch (SQLException e) {
//            System.err.println("Insert statement failed");
//            System.err.println(e.getMessage());
//            closeDB();
//            System.exit(1);
//        }
//    }
//    //first
//    public static void setPrepareStatement_f() {
//        try {
//            stmt = con.prepareStatement("INSERT INTO First_Reply (postID, firstContent,firstStars,firstAuthor) " +
//                    "VALUES (?,?,?,?);");
//        } catch (SQLException e) {
//            System.err.println("Insert statement failed");
//            System.err.println(e.getMessage());
//            closeDB();
//            System.exit(1);
//        }
//    }
//    //second
//    public static void setPrepareStatement_s() {
//        try {
//            stmt = con.prepareStatement("INSERT INTO Second_Reply (postID,firstAuthor,secondaryContent,secondaryStars,secondaryAuthor) " +
//                    "VALUES (?,?,?,?,?);");
//        } catch (SQLException e) {
//            System.err.println("Insert statement failed");
//            System.err.println(e.getMessage());
//            closeDB();
//            System.exit(1);
//        }
//    }
//
//    private static void closeDB() {
//        if (con != null) {
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//                con.close();
//                con = null;
//            } catch (Exception ignored) {
//            }
//        }
//    }
//
//    private static Properties loadDBUser() {
//        Properties properties = new Properties();
//        try {
//            properties.load(new InputStreamReader(new FileInputStream("resources/dbUser.properties")));
//            return properties;
//        } catch (IOException e) {
//            System.err.println("can not find db user file");
//            throw new RuntimeException(e);
//        }
//    }
//
//    //post
//    private static List<String> loadCSVFilep() {
//        try {
//            return Files.readAllLines(Path.of("/Users/huangshuo/Downloads/test.csv"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    //author
//    private static List<String> loadCSVFileA() {
//        try {
//            return Files.readAllLines(Path.of("/Users/huangshuo/Downloads/Author.csv"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    //category
//    private static List<String> loadCSVFilec() {
//        try {
//            return Files.readAllLines(Path.of("/Users/huangshuo/Downloads/category.csv"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    //favorite
//    private static List<String> loadCSVFilefa() {
//        try {
//            return Files.readAllLines(Path.of("/Users/huangshuo/Downloads/favorited.csv"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    //first
//    private static List<String> loadCSVFilefr() {
//        try {
//            return Files.readAllLines(Path.of("/Users/huangshuo/Downloads/First_Reply.csv"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    //second
//    private static List<String> loadCSVFilese() {
//        try {
//            return Files.readAllLines(Path.of("/Users/huangshuo/Downloads/second.csv"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    //like
//    private static List<String> loadCSVFileli() {
//        try {
//            return Files.readAllLines(Path.of("/Users/huangshuo/Downloads/like.csv"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    //share
//    private static List<String> loadCSVFilesh() {
//        try {
//            return Files.readAllLines(Path.of("/Users/huangshuo/Downloads/share.csv"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    //follow
//    private static List<String> loadCSVFilefo() {
//        try {
//            return Files.readAllLines(Path.of("/Users/huangshuo/Downloads/follow.csv"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//
//    //post
//    private static void loadData_p(String line) {
//        String[] lineData = line.split(";");
//        if (con != null) {
//            try {
//                stmt.setInt(1, Integer.parseInt(lineData[0]));
//                stmt.setString(2, lineData[1]);
//                stmt.setString(3, lineData[2]);
//                stmt.setString(4, lineData[3]);
//                stmt.setString(5, lineData[4]);
//                stmt.setString(6, lineData[5]);
//                stmt.executeUpdate();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//
//    //favor
//    private static void loadData_favor(String line) {
//        String[] lineData = line.split(";");
//        if (con != null) {
//            try {
//                stmt.setInt(1, Integer.parseInt(lineData[0]));
//                String temp2 = lineData[1];
//                temp2 = temp2.replace("'","");
//                temp2 = temp2.replace(" ","");
//                stmt.setString(2, temp2);
//                stmt.executeUpdate();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //like
//    private static void loadData_like(String line) {
//        String[] lineData = line.split(";");
//        if (con != null) {
//            try {
//                stmt.setInt(1, Integer.parseInt(lineData[0]));
//                String a = lineData[1];
//                a = a.replace(" ","");
//                stmt.setString(2, a);
//                stmt.executeUpdate();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //category
//    private static void loadData_cate(String line) {
//        String[] lineData = line.split(";");
//        if (con != null) {
//            try {
//                stmt.setInt(1, Integer.parseInt(lineData[0]));
//                String a = lineData[1];
//                a = a.replace(" ","");
//                stmt.setString(2, a);
//                stmt.executeUpdate();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //author
//    private static void loadData_au(String line) {
//        String[] lineData = line.split(";");
//        if (con != null) {
//            try {
//                stmt.setInt(1, Integer.parseInt(lineData[0]));
//                stmt.setString(2, lineData[1]);
//                stmt.setString(3, lineData[2]);
//                stmt.setString(4, lineData[3]);
//                stmt.setString(5, lineData[4]);
//                stmt.executeUpdate();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //follow
//    private static void loadData_follow(String line) {
//        String[] lineData = line.split(";");
//        if (con != null) {
//            try {
//                stmt.setString(1, lineData[0]);
//                String temp = "";
//                if (lineData.length==2){
//                    temp = lineData[1];
//                    temp = temp.replace(" ","");
//                }
//                stmt.setString(2, temp);
//                stmt.executeUpdate();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //first
//    private static void loadData_f(String line) {
//        String[] lineData = line.split(";");
//        if (con != null) {
//            try {
//                stmt.setInt(1, Integer.parseInt(lineData[0]));
//                stmt.setString(2, lineData[1]);
//                stmt.setInt(3, Integer.parseInt(lineData[2]));
//                stmt.setString(4, lineData[3]);
//                stmt.executeUpdate();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //share
//    private static void loadData_share(String line) {
//        String[] lineData = line.split(";");
//        if (con != null) {
//            try {
//                stmt.setInt(1, Integer.parseInt(lineData[0]));
//                String a = lineData[1];
//                a = a.replace(" ","");
//                stmt.setString(2, a);
//                stmt.executeUpdate();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //second
//    private static void loadData_s(String line) {
//        String[] lineData = line.split(";");
//        if (con != null) {
//            try {
//                stmt.setInt(1, Integer.parseInt(lineData[0]));
//                stmt.setString(2, lineData[1]);
//                stmt.setString(3, lineData[2]);
//                stmt.setInt(4, Integer.parseInt(lineData[3]));
//                stmt.setString(5, lineData[4]);
//                stmt.executeUpdate();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//
//    //clear post
//    public static void clearDataInTable_p() {
//        Statement stmt0;
//        if (con != null) {
//            try {
//                stmt0 = con.createStatement();
//                stmt0.executeUpdate("drop table post cascade;");
//                con.commit();
//                stmt0.executeUpdate("create table if not exists post(\n" +
//                        "postID int not null primary key ,\n" +
//                        "title varchar(100),\n" +
//                        "content varchar(1000),\n" +
//                        "postTime varchar(50),\n" +
//                        "postCity varchar(50),\n" +
//                        "postCountry varchar(50),\n" +
//                        "heat int default 0,\n"+
//                        "isAnonymous int default 0\n"+
//                        ");");
//                con.commit();
//                stmt0.close();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //favor
//    public static void clearDataInTable_favor() {
//        Statement stmt0;
//        if (con != null) {
//            try {
//                stmt0 = con.createStatement();
//                stmt0.executeUpdate("drop table favorite_post ;");
//                con.commit();
//                stmt0.executeUpdate("create table if not exists favorite_post(\n" +
//                        "favoriteID  serial PRIMARY KEY,\n" +
//                        "postID int not null,\n" +
//                        "favorite varchar(30),\n" +
//                        "unique(postID,favorite),\n"+
//                        "foreign key (postID) references Post(postID)\n" +
//                        ");");
//                con.commit();
//                stmt0.close();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //like
//    public static void clearDataInTable_like() {
//        Statement stmt0;
//        if (con != null) {
//            try {
//                stmt0 = con.createStatement();
//                stmt0.executeUpdate("drop table like_post ;");
//                con.commit();
//                stmt0.executeUpdate("create table if not exists like_post(\n" +
//                        "likeID  serial PRIMARY KEY,\n" +
//                        "postID int not null,\n" +
//                        "liked varchar(30),\n" +
//                        "unique(postID,liked),\n"+
//                        "foreign key (postID) references Post(postID)\n" +
//                        ");");
//                con.commit();
//                stmt0.close();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //category
//    public static void clearDataInTable_cate() {
//        Statement stmt0;
//        if (con != null) {
//            try {
//                stmt0 = con.createStatement();
//                stmt0.executeUpdate("drop table categories ;");
//                con.commit();
//                stmt0.executeUpdate("create table if not exists categories(\n" +
//                        "categoryID  serial PRIMARY KEY,\n" +
//                        "postID int not null,\n" +
//                        "category varchar(30),\n" +
//                        "unique(postID,category),\n"+
//                        "foreign key (postID) references Post(postID)\n" +
//                        ");");
//                con.commit();
//                stmt0.close();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //author
//    public static void clearDataInTable_au() {
//        Statement stmt0;
//        if (con != null) {
//            try {
//                stmt0 = con.createStatement();
//                stmt0.executeUpdate("drop table author cascade ;");
//                con.commit();
//                stmt0.executeUpdate("create table if not exists author(\n" +
//                        "postID int not null primary key,\n" +
//                        "authorName varchar(30),\n" +
//                        "registrationTime varchar(30),\n" +
//                        "authorID varchar(30) unique,\n" +
//                        "phone varchar(30),\n" +
//                        "foreign key (postID) references Post(postID)\n" +
//                        ");");
//                con.commit();
//                stmt0.close();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //follow
//    public static void clearDataInTable_follow() {
//        Statement stmt0;
//        if (con != null) {
//            try {
//                stmt0 = con.createStatement();
//                stmt0.executeUpdate("drop table followed_by ;");
//                con.commit();
//                stmt0.executeUpdate("create table if not exists followed_by(\n" +
//                        "followID  serial PRIMARY KEY,\n" +
//                        "authorID varchar(30) not null,\n" +
//                        "follow varchar(30),\n" +
//                        "unique(authorID,follow),\n"+
//                        "foreign key (authorID) references Author(authorID)\n" +
//                        ");");
//                con.commit();
//                stmt0.close();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //first
//    public static void clearDataInTable_f() {
//        Statement stmt0;
//        if (con != null) {
//            try {
//                stmt0 = con.createStatement();
//                stmt0.executeUpdate("drop table First_Reply cascade ;");
//                con.commit();
//                stmt0.executeUpdate("create table if not exists First_Reply(\n" +
//                        "firstID  serial PRIMARY KEY,\n"+
//                        "postID int not null,\n" +
//                        "firstContent varchar(500),\n" +
//                        "firstStars int,\n" +
//                        "firstAuthor varchar(30) ,\n" +
//                        "unique(postID,firstAuthor),\n"+
//                        "foreign key (postID) references Post(postID)\n" +
//                        ");");
//                con.commit();
//                stmt0.close();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //second
//    public static void clearDataInTable_s() {
//        Statement stmt0;
//        if (con != null) {
//            try {
//                stmt0 = con.createStatement();
//                stmt0.executeUpdate("drop table Second_Reply ;");
//                con.commit();
//                stmt0.executeUpdate("create table if not exists Second_Reply(\n" +
//                        "secondID  serial PRIMARY KEY,\n"+
//                        "PostID int not null,\n" +
//                        "firstAuthor varchar(30),\n" +
//                        "secondaryContent varchar(500),\n" +
//                        "secondaryStars int,\n"+
//                        "secondaryAuthor varchar(30),\n"+
//                        "unique (postID,secondaryStars,secondaryAuthor),\n"+
//                        "foreign key (postID,firstAuthor) references First_Reply(postID,firstAuthor)\n" +
//                        ");");
//                con.commit();
//                stmt0.close();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    //share
//    public static void clearDataInTable_share() {
//        Statement stmt0;
//        if (con != null) {
//            try {
//                stmt0 = con.createStatement();
//                stmt0.executeUpdate("drop table share_post ;");
//                con.commit();
//                stmt0.executeUpdate("create table if not exists share_post(\n" +
//                        "shareID  serial PRIMARY KEY,\n" +
//                        "postID int not null,\n" +
//                        "share varchar(30),\n" +
//                        "unique(postID,share),\n"+
//                        "foreign key (postID) references Post(postID)\n" +
//                        ");");
//                con.commit();
//                stmt0.close();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//
//
//
//    public static void main(String[] args) {
//
//        Properties prop = loadDBUser();
//        // Empty target table
//        openDB(prop);
//        clearDataInTable_cate();
//        clearDataInTable_favor();
//        clearDataInTable_follow();
//        clearDataInTable_au();
//        clearDataInTable_like();
//        clearDataInTable_s();
//        clearDataInTable_f();
//        clearDataInTable_share();
//        clearDataInTable_p();
//        closeDB();
//
//        long start = System.currentTimeMillis();
//        openDB(prop);
//
//        //post
//        List<String> lines = loadCSVFilep();
//        int cnt = 0;
//        setPrepareStatement_p();
//        try {
//            for (String line : lines) {
//                if (line.startsWith("PostID"))
//                    continue; // skip the first line
//                loadData_p(line);//do insert command
//                if (cnt % BATCH_SIZE == 0) {
//                    stmt.executeBatch();
//                    System.out.println("insert " + BATCH_SIZE + " data successfully!");
//                    stmt.clearBatch();
//                }
//                cnt++;
//            }
//
//            if (cnt % BATCH_SIZE != 0) {
//                stmt.executeBatch();
//                System.out.println("insert " + cnt % BATCH_SIZE + " data successfully!");
//
//            }
//            con.commit();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        //fa
//        List<String> lines2 = loadCSVFilefa();
//        cnt = 0;
//        setPrepareStatement_favor();
//        try {
//            for (String line : lines2) {
//                if (line.startsWith("PostID"))
//                    continue; // skip the first line
//                loadData_favor(line);//do insert command
//                if (cnt % BATCH_SIZE == 0) {
//                    stmt.executeBatch();
//                    System.out.println("insert " + BATCH_SIZE + " data successfully!");
//                    stmt.clearBatch();
//                }
//                cnt++;
//            }
//
//            if (cnt % BATCH_SIZE != 0) {
//                stmt.executeBatch();
//                System.out.println("insert " + cnt % BATCH_SIZE + " data successfully!");
//
//            }
//            con.commit();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        //author
//        List<String> lines8 = loadCSVFileA();
//        cnt = 0;
//        setPrepareStatement_au();
//        try {
//            for (String line : lines8) {
//                if (line.startsWith("PostID"))
//                    continue; // skip the first line
//                loadData_au(line);//do insert command
//                if (cnt % BATCH_SIZE == 0) {
//                    stmt.executeBatch();
//                    System.out.println("insert " + BATCH_SIZE + " data successfully!");
//                    stmt.clearBatch();
//                }
//                cnt++;
//            }
//
//            if (cnt % BATCH_SIZE != 0) {
//                stmt.executeBatch();
//                System.out.println("insert " + cnt % BATCH_SIZE + " data successfully!");
//
//            }
//            con.commit();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        //follow
//        List<String> lines3 = loadCSVFilefo();
//        cnt = 0;
//        setPrepareStatement_follow();
//        try {
//            for (String line : lines3) {
//                if (line.startsWith("AuthorID"))
//                    continue; // skip the first line
//                loadData_follow(line);//do insert command
//                if (cnt % BATCH_SIZE == 0) {
//                    stmt.executeBatch();
//                    System.out.println("insert " + BATCH_SIZE + " data successfully!");
//                    stmt.clearBatch();
//                }
//                cnt++;
//            }
//
//            if (cnt % BATCH_SIZE != 0) {
//                stmt.executeBatch();
//                System.out.println("insert " + cnt % BATCH_SIZE + " data successfully!");
//
//            }
//            con.commit();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        //share
//        List<String> lines4 = loadCSVFilesh();
//        cnt = 0;
//        setPrepareStatement_share();
//        try {
//            for (String line : lines4) {
//                if (line.startsWith("PostID"))
//                    continue; // skip the first line
//                loadData_share(line);//do insert command
//                if (cnt % BATCH_SIZE == 0) {
//                    stmt.executeBatch();
//                    System.out.println("insert " + BATCH_SIZE + " data successfully!");
//                    stmt.clearBatch();
//                }
//                cnt++;
//            }
//
//            if (cnt % BATCH_SIZE != 0) {
//                stmt.executeBatch();
//                System.out.println("insert " + cnt % BATCH_SIZE + " data successfully!");
//
//            }
//            con.commit();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        //first
//        List<String> lines6 = loadCSVFilefr();
//        cnt = 0;
//        setPrepareStatement_f();
//        try {
//            for (String line : lines6) {
//                if (line.startsWith("PostID"))
//                    continue; // skip the first line
//                loadData_f(line);//do insert command
//                if (cnt % BATCH_SIZE == 0) {
//                    stmt.executeBatch();
//                    System.out.println("insert " + BATCH_SIZE + " data successfully!");
//                    stmt.clearBatch();
//                }
//                cnt++;
//            }
//
//            if (cnt % BATCH_SIZE != 0) {
//                stmt.executeBatch();
//                System.out.println("insert " + cnt % BATCH_SIZE + " data successfully!");
//
//            }
//            con.commit();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        //second
//        List<String> lines5 = loadCSVFilese();
//        cnt = 0;
//        setPrepareStatement_s();
//        try {
//            for (String line : lines5) {
//                if (line.startsWith("PostID"))
//                    continue; // skip the first line
//                loadData_s(line);//do insert command
//                if (cnt % BATCH_SIZE == 0) {
//                    stmt.executeBatch();
//                    System.out.println("insert " + BATCH_SIZE + " data successfully!");
//                    stmt.clearBatch();
//                }
//                cnt++;
//            }
//
//            if (cnt % BATCH_SIZE != 0) {
//                stmt.executeBatch();
//                System.out.println("insert " + cnt % BATCH_SIZE + " data successfully!");
//
//            }
//            con.commit();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        //like
//        List<String> lines7 = loadCSVFileli();
//        cnt = 0;
//        setPrepareStatement_like();
//        try {
//            for (String line : lines7) {
//                if (line.startsWith("PostID"))
//                    continue; // skip the first line
//                loadData_like(line);//do insert command
//                if (cnt % BATCH_SIZE == 0) {
//                    stmt.executeBatch();
//                    System.out.println("insert " + BATCH_SIZE + " data successfully!");
//                    stmt.clearBatch();
//                }
//                cnt++;
//            }
//
//            if (cnt % BATCH_SIZE != 0) {
//                stmt.executeBatch();
//                System.out.println("insert " + cnt % BATCH_SIZE + " data successfully!");
//
//            }
//            con.commit();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        //category
//        List<String> lines9 = loadCSVFilec();
//        cnt = 0;
//        setPrepareStatement_cate();
//        try {
//            for (String line : lines9) {
//                if (line.startsWith("PostID"))
//                    continue; // skip the first line
//                loadData_cate(line);//do insert command
//                if (cnt % BATCH_SIZE == 0) {
//                    stmt.executeBatch();
//                    System.out.println("insert " + BATCH_SIZE + " data successfully!");
//                    stmt.clearBatch();
//                }
//                cnt++;
//            }
//
//            if (cnt % BATCH_SIZE != 0) {
//                stmt.executeBatch();
//                System.out.println("insert " + cnt % BATCH_SIZE + " data successfully!");
//
//            }
//            con.commit();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        closeDB();
//        long end = System.currentTimeMillis();
//        System.out.println(cnt + " records successfully loaded");
//        System.out.println("Time consuming ：" + (end-start)/1000.0 + "s");
//        System.out.println("Loading speed : " + (cnt * 1000L) / (end - start) + " records/s");
//
//    }
//}
//
//
//
