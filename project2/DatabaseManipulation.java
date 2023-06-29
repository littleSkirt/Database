
import Util.DBUtil;

import java.sql.*;

public class DatabaseManipulation implements DataManipulation {
    private Connection con = null;
    private ResultSet resultSet;

    private DBUtil util;

    private String host = "localhost";
    private String dbname = "project1";
    private String user = "checker";
    private String pwd = "123456";
    private String port = "54321";

    public DatabaseManipulation(DBUtil util) {
        this.util = util;
    }
    @Override
    public void openDatasource() {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (Exception e) {
            System.err.println("Cannot find the PostgreSQL driver. Check CLASSPATH.");
            System.exit(1);
        }

        try {
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
            con = DriverManager.getConnection(url, user, pwd);

        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void closeDatasource() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //register
    @Override
    public int addOneUser(String str) {
        int result = 0;
        String sql = "insert into users(username, password,phone,level) " +
                "values (?,?,?,?)";
        String[] Info = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, Info[0]);
            preparedStatement.setString(2, Info[1]);
            preparedStatement.setString(3, Info[2]);
            preparedStatement.setString(4, Info[3]);
            //System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int addOneLike(String str) {
        int result = 0;
        String sql = "insert into like_post(postID, liked) " +
                "values (?,?)";
        String[] Info = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(Info[0]));
            preparedStatement.setString(2, Info[1]);
            //System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int addOneFavorite(String str) {
        int result = 0;
        String sql = "insert into favorite_post(postID, favorite) " +
                "values (?,?)";
        String[] Info = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(Info[0]));
            preparedStatement.setString(2, Info[1]);
            //System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int addOneShare(String str) {
        int result = 0;
        String sql = "insert into share_post(postID, share) " +
                "values (?,?)";
        String[] Info = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(Info[0]));
            preparedStatement.setString(2, Info[1]);
            //System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int addOneShield(String str) {
        int result = 0;
        String sql = "insert into blacklist(blackname, operatorname) " +
                "values (?,?)";
        String[] Info = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, Info[0]);
            preparedStatement.setString(2, Info[1]);
            //System.out.println(preparedStatement.toString());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public String checkauthorid(String authorname) {
        StringBuilder sb = new StringBuilder();
        String sql = "select authorid from author where authorname = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, authorname);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("1");
                sb.append(resultSet.getString("authorid")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Authorid"+sb);
        return sb.toString();
    }

    @Override
    public int addOneFollow(String str) {
        int result = 0;
        String sql = "insert into followed_by(authorID, follow) " +
                "values (?,?)";
        String[] Info = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, Info[0]);
            preparedStatement.setString(2, Info[1]);
            //System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //delete
    @Override
    public void minusOneFollow(String authorID, String name) {
        String sql = "delete from followed_by where authorID = ? and follow = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, authorID);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //profile
    @Override
    public String checkUserFollow(String username) {
        StringBuilder sb = new StringBuilder();
        String sql = "select a.authorName from followed_by f join author a on f.authorID = a.authorid where f.follow = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("authorName")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //postID,title,content by postid
    @Override
    public String checkPostBYid(int id) {
        StringBuilder sb = new StringBuilder();
        String sql = "select postID,title,content from post where postid =?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("postID")).append("\t");
                sb.append(resultSet.getString("title")).append("\t");
                sb.append(resultSet.getString("content")).append("\t");
                sb.append(resultSet.getString("content")).append("\t");
                sb.append(resultSet.getString("content")).append("\t");
                sb.append(resultSet.getString("content")).append("\t");

                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //content find author by post id
    @Override
    public String chechAuthor(int id){
        StringBuilder sb = new StringBuilder();
        String sql = "select a.authorname from post p join author a on a.postid =p.postid where p.postid =?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("authorname")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //search
    @Override
    public int userPosting(String str, String category, String str1) {
        int result = 0;
        String sql = "INSERT INTO post(postID, title, content, postTime, postCity, postCountry, isAnonymous) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String categorySql = "INSERT INTO categories(postID, category) VALUES (?, ?)";
        String[] Info = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(Info[0]));
            preparedStatement.setString(2, Info[1]);
            preparedStatement.setString(3, Info[2]);
            preparedStatement.setString(4, Info[3]);
            preparedStatement.setString(5, Info[4]);
            preparedStatement.setString(6, Info[5]);
            preparedStatement.setInt(7, Integer.parseInt(Info[6]));

            PreparedStatement categoryStatement = con.prepareStatement(categorySql);
            categoryStatement.setInt(1, Integer.parseInt(Info[0]));
            categoryStatement.setString(2, category);

            String authorSql = "INSERT INTO author(postID, authorName, registrationTime, authorID, phone) VALUES (?, ?, ?, ?, ?)";
            String[] Info1 = str1.split(";");
            if (Integer.parseInt(Info[6]) == 0) { // 如果isAnonymous为1，则执行INSERT INTO author语句
                PreparedStatement authorStatement = con.prepareStatement(authorSql);
                authorStatement.setInt(1, Integer.parseInt(Info[0]));
                authorStatement.setString(2, Info1[0]);
                authorStatement.setString(3, Info[3]);
                authorStatement.setString(4, Info1[1]); // 这里可以根据需要设置默认值id
                authorStatement.setString(5, Info1[2]); // 这里可以根据需要设置默认值phone
                authorStatement.executeUpdate(); // 执行INSERT INTO author语句
            }else if(Integer.parseInt(Info[6]) == 1){
                PreparedStatement authorStatement = con.prepareStatement(authorSql);
                authorStatement.setInt(1, Integer.parseInt(Info[0]));
                authorStatement.setString(2, "Anonymous");
                authorStatement.setString(3, Info[3]);
                authorStatement.setString(4, "0000000000"); // 这里可以根据需要设置默认值
                authorStatement.setString(5, "0000000000"); // 这里可以根据需要设置默认值
//                authorStatement.executeUpdate();
            }


            result = preparedStatement.executeUpdate();
            categoryStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //insert first reply
    @Override
    public int userFirstReply(String postid, String firstcontent, int firststar, String firstAUthor, String operatorName) {
        int result = 0;
        String selectSql = "SELECT a.authorname FROM post p JOIN author a ON a.postID = p.postID AND authorName NOT IN (SELECT blackName FROM blacklist WHERE operatorName = ?)";
        try {
            PreparedStatement selectStatement = con.prepareStatement(selectSql);
            selectStatement.setString(1, operatorName);
            ResultSet resultSet = selectStatement.executeQuery();
            boolean found = false;
            while (resultSet.next()) {
                if (resultSet.getString(1) == firstAUthor) {
                    found = true;
                    break;
                }
            }
            if (found) {
                String insertSql = "INSERT INTO first_reply(postID, firstContent, firstStars, firstAuthor) VALUES (?,?,?,?)";
                PreparedStatement insertStatement = con.prepareStatement(insertSql);
                insertStatement.setInt(1, Integer.parseInt(postid));
                insertStatement.setString(2, firstcontent);
                insertStatement.setInt(3, firststar);
                insertStatement.setString(4, firstAUthor);
                result = insertStatement.executeUpdate();
            } else {
                System.out.println("PostID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //content
    @Override
    public int changePhone(String str) {
        int result = 0;
        String sql = "UPDATE users SET phone = ? WHERE username = ?";
        String[] Info = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, Info[0]);
            preparedStatement.setString(2, Info[1]);
            preparedStatement.setString(2, Info[1]);


            //System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //content
    //add second reply
    @Override
    public int userSecondReply(int postid, String firstauthor, String secondC, int secondS, String secondA, String operatorName) {
        int result = 0;
        String selectSql = "SELECT blackName FROM blacklist WHERE operatorName = ?";
        String insertSql = "INSERT INTO second_reply(postID, firstAuthor, secondaryContent, secondaryStars, secondaryAuthor) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement selectStatement = con.prepareStatement(selectSql);
            selectStatement.setString(1, operatorName);
            ResultSet resultSet = selectStatement.executeQuery();
            boolean found = false;
            while (resultSet.next()) {
                if (resultSet.getString(1) == firstauthor) {
                    found = true;
                    break;
                }
            }
            if (found) {
                PreparedStatement insertStatement = con.prepareStatement(insertSql);
                insertStatement.setInt(1, postid);
                insertStatement.setString(2, firstauthor);
                insertStatement.setString(3, secondC);
                insertStatement.setInt(4, secondS);
                insertStatement.setString(5, secondA);
                result = insertStatement.executeUpdate();
            } else {
                System.out.println("PostID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String checkUserPost(String username) {
        StringBuilder sb = new StringBuilder();
        String sql = "select p.postID,p.title,p.content from post p join Author A on p.postID = A.postID where authorName=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("postID")).append("\t");
                sb.append(resultSet.getString("title")).append("\t");
                sb.append(resultSet.getString("content")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //check by id content
    @Override
    public String checkPost(int id) {
        StringBuilder sb = new StringBuilder();
        String sql = "select p.postID,p.title,p.content from post p join Author A on p.postID = A.postID where authorName=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("postID")).append("\t");
                sb.append(resultSet.getString("title")).append("\t");
                sb.append(resultSet.getString("content")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //profile
    @Override
    public String checkUserReply(String username) {
        StringBuilder sb = new StringBuilder();
        String sql = "select firstid,postID,firstcontent from first_reply where firstAuthor=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("firstid")).append("\t");
                sb.append(resultSet.getString("postID")).append("\t");
                sb.append(resultSet.getString("firstcontent")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public String checkUserSecondReply(String username) {
        StringBuilder sb = new StringBuilder();
        String sql = "select second,postID,secondarycontent from second_reply where secondaryAuthor=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("secondid")).append("\t");
                sb.append(resultSet.getString("postID")).append("\t");
                sb.append(resultSet.getString("secondarycontent")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public String checkUserFavorite(String username) {
        StringBuilder sb = new StringBuilder();
        String sql = "select P.postID,title from favorite_post f join post p on f.postID = p.postid where favorite=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("postID")).append("\t");
                sb.append(resultSet.getString("title")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    @Override
    public String checkUserShare(String username) {
        StringBuilder sb = new StringBuilder();
        String sql = "select P.postID,title from share_post s join Post P on P.postID = s.postID where share=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("postID")).append("\t");
                sb.append(resultSet.getString("title")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public String checkUserLike(String username) {
        StringBuilder sb = new StringBuilder();
        String sql = "select P.postID,title from like_post l join Post P on l.postID = P.postID where liked=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("postID")).append("\t");
                sb.append(resultSet.getString("title")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void deleteLike(String liked, String postID) {
        String sql = "delete from like_post where liked=? and postID=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, liked);
            preparedStatement.setInt(2, Integer.parseInt(postID));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteShare(String share, String postID) {
        String sql = "delete from share_post where share=? and postID=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, share);
            preparedStatement.setInt(2, Integer.parseInt(postID));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFavorite(String favorite, String postID) {
        String sql = "delete from favorite_post where favorite=? and postID=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, favorite);
            preparedStatement.setInt(2, Integer.parseInt(postID));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTop10Posts() {
        StringBuilder sb = new StringBuilder();
        String sql = "select * from Post order by heat desc limit 10";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("postID")).append("\t");
                sb.append(resultSet.getString("title")).append("\t");
                sb.append(resultSet.getString("content")).append("\t");
                sb.append(resultSet.getString("postTime")).append("\t");
                sb.append(resultSet.getString("postCity")).append("\t");
                sb.append(resultSet.getString("postCountry")).append("\t");
                sb.append(resultSet.getString("heat")).append("\t");
                sb.append(resultSet.getString("isAnonymous")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    @Override
    public String getPostsByTimeRange(String startTime, String endTime) {
        StringBuilder sb = new StringBuilder();
        String sql = "select * from Post where postTime between ? and ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, startTime);
            preparedStatement.setString(2, endTime);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("postID")).append("\t");
                sb.append(resultSet.getString("Title")).append("\t");
                sb.append(resultSet.getString("Content")).append("\t");
                sb.append(resultSet.getString("postTime")).append("\t");
                sb.append(resultSet.getString("postCity")).append("\t");
                sb.append(resultSet.getString("postCountry")).append("\t");
                sb.append(resultSet.getString("heat")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //profile
    @Override
    public String getUserInfo(String name) {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM Author WHERE authorname = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("Authorid")).append("\t");
                sb.append(resultSet.getString("Phone")).append("\t");
                sb.append(resultSet.getString("registrationtime")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //searcg
    @Override
    public String getPostsByKeywords(String str) {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM Post WHERE content LIKE ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "%" + str + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("postID")).append("\t");
                sb.append(resultSet.getString("Title")).append("\t");
                sb.append(resultSet.getString("Content")).append("\t");
                sb.append(resultSet.getString("postTime")).append("\t");
                sb.append(resultSet.getString("postCity")).append("\t");
                sb.append(resultSet.getString("postCountry")).append("\t");
                sb.append(resultSet.getString("heat")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //manager delete
    @Override
    public void deletePost(String postid){
        String sql = "delete from post where postID=? ";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(postid));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFirstReply(String firstID){
        String sql = "delete from first_reply where postID=?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, firstID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteSecondReply(String secondid) {
        String sql = "delete from second_reply where secondID=?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, secondid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String checkSecondReply(int postid,String firstname) {
        StringBuilder sb = new StringBuilder();
        String sql = "select secondid,secondaryauthor,postID,secondarycontent " +
                "from second_reply where postid = ? and firstauthor=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, postid);
            preparedStatement.setString(2, firstname);
            resultSet = preparedStatement.executeQuery();
            System.out.println("result"+resultSet);
            while (resultSet.next()) {
                sb.append(resultSet.getString("secondid")).append("\t");
                sb.append(resultSet.getString("secondaryauthor")).append("\t");
                sb.append(resultSet.getString("postID")).append("\t");
                sb.append(resultSet.getString("secondarycontent")).append("\t");
                sb.append(System.lineSeparator());
                System.out.println(sb);
            }
            System.out.println("final"+sb);
        } catch (SQLException e) {
            System.out.println("NOT find");
            e.printStackTrace();
        }
        return sb.toString();
    }

    //check all first reply
    @Override
    public String checkReplyBYid(int id) {
        StringBuilder sb = new StringBuilder();
        String sql = "select firstAuthor,firstid,firstcontent from first_reply where postid=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("firstAUthor")).append("\t");
                sb.append(resultSet.getString("firstid")).append("\t");
                sb.append(resultSet.getString("firstcontent")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public boolean  checkUser(String username,String password) {
        StringBuilder sb = new StringBuilder();
        String sql = "select username from users where username=? and password=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("username")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString().length()==0;
    }
    @Override
    public int findMaxPostId() {
        int maxPostId = 0;
        String sql = "SELECT MAX(postid) AS max_postid FROM post";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                maxPostId = resultSet.getInt("max_postid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxPostId;
    }

    @Override
    public boolean checkManager(String name,String password){
        StringBuilder sb = new StringBuilder();
        String sql ="select username from users where username =?and password =? and level<>'user'";
        try{
            PreparedStatement preparedStatement=con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("username")).append("\t");
                sb.append(System.lineSeparator());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return  sb.toString().length()==0;  //该用户为user时返回true
    }

}

