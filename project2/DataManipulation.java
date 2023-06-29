public interface DataManipulation {

    public void openDatasource();
    public void closeDatasource();
    public int addOneUser(String str);
    public int addOneLike(String str);
    public int addOneFavorite(String str);
    public int addOneShare(String str);

    int addOneShield(String str);

    String checkauthorid(String authorname);

    public int addOneFollow(String str);
    public void minusOneFollow(String authorID, String name);
    public String checkUserFollow(String username);

    String checkPostBYid(int id);

    String chechAuthor(int id);

    public int userPosting(String str, String category, String str1);

//    public int userPosting(String str, String category);
//    public int userFirstReply(String str, String operatorName);
//    public int userSecondReply(String str, String operatorName);

    int userSecondReply(int postid, String firstauthor, String secondC, int secondS, String secondA, String operatorName);

    public String checkUserPost(String username);
    public String checkUserReply(String username);
    public String getTop10Posts();
    public String getPostsByTimeRange(String startTime, String endTime);
    public String checkUserLike(String username);
    public String checkUserShare(String username);
    public String checkUserFavorite(String username);
//    public void deleteFirstReply(String firstid,String username);

    void deleteFirstReply(String firstID);

    void deleteSecondReply(String secondid);

    String checkReplyBYid(int id);

    String checkSecondReply(int id, String firstname);

    public boolean  checkUser(String username, String password);

    public int userFirstReply(String postid, String firstcontent, int firststar, String firstAUthor, String operatorName);

    public int changePhone(String str);
    public int findMaxPostId();
    public String checkPost(int id);
    public boolean checkManager(String name,String password);
    public String getPostsByKeywords(String str);
    public void deleteLike(String liked, String postID);
    public void deleteShare(String share, String postID);
    public void deleteFavorite(String favorite, String postID);
    public String checkUserSecondReply(String username);
//    public void deleteSecondReply(String secondid,String username);
    public void deletePost(String postid);

    public String getUserInfo(String username);
}
