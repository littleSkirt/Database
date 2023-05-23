public interface DataManipulation {

    public void openDatasource();
    public void closeDatasource();
    public int addOneUser(String str);
    public int addOneLike(String str);

    int CheckLikeList(String str);

    public int addOneFavorite(String str);
    public int addOneShare(String str);
    public int addOneFollow(String str);
    public int minusOneFollow(String str);
    public String checkUserFollow(String username);
    public int userPosting(String str);
    public int userFirstReply(String str);
    public int userSecondReply(String str);
    public String checkUserPost(String username);
    public String checkUserReply(String username);
    public String getTop10Posts();
    public String getPostsByTimeRange(String startTime, String endTime);
    public void deleteFirstReply(String authorName);
    public boolean  checkUser(String username,String password);
    public int findMaxPostId();

    String checkUserShare(String username);

    String checkUserFavorite(String username);

    String checkUserLike(String username);
}
