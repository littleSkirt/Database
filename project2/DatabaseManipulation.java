
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManipulation implements DataManipulation {
    private Connection con = null;
    private ResultSet resultSet;

    private String host = "localhost";
    private String dbname = "project1";
    private String user = "checker";
    private String pwd = "123456";
    private String port = "54321";


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
//    public List<String> findStationsByLine(int lineId) {
//        List<String> stations = new ArrayList<>();
//        String sql = "select ld.num, s.english_name, s.chinese_name\n" +
//                "from line_detail ld\n" +
//                "         join stations s on ld.station_id = s.station_id\n" +
//                "where ld.line_id = ?" +
//                "order by ld.num;";
//        try {
//            Thread.sleep(2000); //看到连接池的占用状态
//            preparedStatement = con.prepareStatement(sql);
//            preparedStatement.setInt(1, lineId);
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                stations.add(String.format("%d, %s, %s", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
//            }
//        } catch (SQLException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        return stations;
//    }
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
    public int CheckLikeList(String str) {
        int result = 0;
        String sql = "select postid from like_post" +
                "where like = ?";
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

    @Override
    public int minusOneFollow(String str) {
        int result = 0;
        String sql = "delete from followed_by where authorID = ? and follow = ?";
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

    @Override
    public int userPosting(String str) {
        int result = 0;
        String sql = "insert into post(postID, title, content, postTime, postCity, postCountry) " +
                "values (?,?,?,?,?,?)";
        String[] Info = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(Info[0]));
            preparedStatement.setString(2, Info[1]);
            preparedStatement.setString(3, Info[2]);
            preparedStatement.setString(4, Info[3]);
            preparedStatement.setString(5, Info[4]);
            preparedStatement.setString(6, Info[5]);
            //System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int userFirstReply(String str) {
        int result = 0;
        String sql = "insert into first_reply(postID, firstContent, firstStars, firstAuthor) " +
                "values (?,?,?,?)";
        String[] Info = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(Info[0]));
            preparedStatement.setString(2, Info[1]);
            preparedStatement.setInt(3, Integer.parseInt(Info[2]));
            preparedStatement.setString(4, Info[3]);

            //System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int userSecondReply(String str) {
        int result = 0;
        String sql = "insert into second_reply(postID, firstAuthor, secondaryContent, secondaryStars, secondaryAuthor)" +
                "values (?,?,?,?,?)";
        String[] Info = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(Info[0]));
            preparedStatement.setString(2, Info[1]);
            preparedStatement.setString(3, Info[2]);
            preparedStatement.setInt(4, Integer.parseInt(Info[3]));
            preparedStatement.setString(5, Info[4]);
            //System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

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

    @Override
    public String checkUserReply(String username) {
        StringBuilder sb = new StringBuilder();
        String sql = "select postID,firstcontent from first_reply where firstAuthor=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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
                sb.append(resultSet.getString("postTitle")).append("\t");
                sb.append(resultSet.getString("postContent")).append("\t");
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

    @Override
    public void deleteFirstReply(String authorName) {
        String sql = "DELETE FROM first_reply WHERE postID IN " +
                "(SELECT f.postID FROM first_reply f " +
                "JOIN (SELECT a.postID FROM post p JOIN author a ON p.postID = a.postID WHERE a.authorName = ?)x " +
                "ON x.postID = f.postID WHERE f.firstAuthor = ?) " +
                "AND firstAuthor IN " +
                "(SELECT firstAuthor FROM first_reply f " +
                "JOIN (SELECT a.postID FROM post p JOIN author a ON p.postID = a.postID WHERE a.authorName = ?)x " +
                "ON x.postID = f.postID WHERE f.firstAuthor = ?)";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, authorName);
            preparedStatement.setString(2, authorName);
            preparedStatement.setString(3, authorName);
            preparedStatement.setString(4, authorName);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " rows deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public String checkUserLike(String username) {
        StringBuilder sb = new StringBuilder();
        String sql = "select postID from like_post where liked=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("postID")).append("\t");
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
        String sql = "select postID from favorite_post where favorite=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("postID")).append("\t");
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
        String sql = "select postID from share_post where share=?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("postID")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}

