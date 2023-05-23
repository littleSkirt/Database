import java.util.*;
import java.text.*;


public class Client {

    public static void main(String[] args) {
        System.out.println("Your choice : register  or  login");
        Scanner in = new Scanner(System.in);
        String op = in.next();
        DataManipulation dm = new DataFactory().createDataManipulation(args[0]);
        dm.openDatasource();
        String username = null;
        String password;
        String phone;
        String level;
        boolean flag1 = true;
        while (flag1) {
            switch (op) {
                case "register":
                    System.out.println("Enter Your username:");
                    username = in.next();
                    System.out.println("Enter Your password:");
                    password = in.next();
                    System.out.println("Enter Your phone:");
                    phone = in.next();
                    System.out.println("Enter Your level:");
                    level = in.next();
                    try {
                        String str = "username;password;phone;level";
                        str = str.replace("username", username);
                        str = str.replace("password", password);
                        str = str.replace("phone", phone);
                        str = str.replace("level", level);
                        dm.addOneUser(str);
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                    flag1 = false;
                    System.out.println("Success!!");
                    break;
                case "login":
                    System.out.println("Enter Your username:");
                    username = in.next();
                    System.out.println("Enter Your password:");
                    password = in.next();
                    if (dm.checkUser(username, password)) {
                        System.out.println("Invalid! Checking Your Input!");
                    } else {
                        flag1 = false;
                    }
                    break;
                default:
                    System.out.println("Invalid!");
            }
        }
        System.out.println("Welcome the bbs!");
        System.out.println("Here is something you can do");
        System.out.print("1.  addOneUser\n2.  addOneLike\n3.  addOneFavorite\n4.  addOneShare\n5.  addOneFollow\n");
        System.out.print("6.  minusOneFollow\n7.  checkUserFollow\n8.  userPosting\n9.  userFirstReply\n");
        System.out.print("10. userSecondReply\n11. checkUserPost\n12. checkUserReply\n");
        System.out.print("13. getTop10Posts\n14. getPostsByTimeRange\n15. deleteFirstReply\n16. exit\n");
        System.out.print("17. checkUserLike\n18. checkUserShare\n19. checkUserFavorite\n");
        boolean flag = true;
        while (flag) {
            System.out.println("Enter what you want to do:");
            String doing = in.next();
            switch (doing) {
                case "addOneLike":
                    System.out.println("Enter the post you like:");
                    String num1 = in.next();
                    String str1 = "num;username";
                    str1 = str1.replace("num", num1);
                    str1 = str1.replace("username", username);
                    dm.addOneLike(str1);
                    System.out.println("Success!!");
                    break;

                case "addOneFavorite":
                    System.out.println("Enter the post you favorite:");
                    String num2 = in.next();
                    String str2 = "num;username";
                    str2 = str2.replace("num", num2);
                    str2 = str2.replace("username", username);
                    dm.addOneFavorite(str2);
                    System.out.println("Success!!");
                    break;

                case "addOneShare":
                    System.out.println("Enter the post you want to share:");
                    String num3 = in.next();
                    String str3 = "num;username";
                    str3 = str3.replace("num", num3);
                    str3 = str3.replace("username", username);
                    dm.addOneShare(str3);
                    System.out.println("Success!!");
                    break;

                case "addOneFollow":
                    System.out.println("Enter the authorID you want to follow:");
                    String num4 = in.next();
                    String str4 = "num;username";
                    str4 = str4.replace("num", num4);
                    str4 = str4.replace("username", username);
                    dm.addOneFollow(str4);
                    System.out.println("Success!!");
                    break;

                case "minusOneFollow":
                    System.out.println("Enter the authorID you want to unfollow:");
                    String num5 = in.next();
                    String str5 = "num;username";
                    str5 = str5.replace("num", num5);
                    str5 = str5.replace("username", username);
                    dm.minusOneFollow(str5);
                    System.out.println("Success!!");
                    break;

                case "userPosting":
                    int postId = dm.findMaxPostId();
                    System.out.println("Enter your postTitle:");
                    String a = in.next();
                    System.out.println("Enter your postContent:");
                    String b = in.next();
                    System.out.println("Enter your postTime:");
                    String c = in.next();
                    System.out.println("Enter your postCity:");
                    String d = in.next();
                    System.out.println("Enter your postCountry:");
                    String f = in.next();
                    String str6 = "postID; title, content; postTime; postCity; postCountry";
                    str6 = str6.replace("postID", Integer.toString(postId));
                    str6 = str6.replace("title", a);
                    str6 = str6.replace("content", b);
                    str6 = str6.replace("postTime", c);
                    str6 = str6.replace("postCity", d);
                    str6 = str6.replace("postCountry", f);
                    dm.userPosting(str6);
                    System.out.println("Success!!");
                    break;

                case "userFirstReply":
                    System.out.println("Enter the postId you want to reply:");
                    String g = in.next();
                    System.out.println("Enter your replyContent:");
                    String h = in.next();
                    System.out.println("Enter your stars:");
                    String i = in.next();
                    String str7 = "postID, firstContent, firstStars, firstAuthor";
                    str7 = str7.replace("firstContent", g);
                    str7 = str7.replace("firstContent", h);
                    str7 = str7.replace("firstStars", i);
                    str7 = str7.replace("firstAuthor", username);
                    dm.userFirstReply(str7);
                    System.out.println("Success!!");
                    break;

                case "userSecondReply":
                    System.out.println("Enter the postId you want to reply:");
                    String j = in.next();
                    System.out.println("Enter the author of the reply:");
                    String k = in.next();
                    System.out.println("Enter your replyContent:");
                    String l = in.next();
                    System.out.println("Enter your stars:");
                    String m = in.next();
                    String str8 = "postID;firstAuthor; secondaryContent; secondaryStars; secondaryAuthor";
                    str8 = str8.replace("postID", j);
                    str8 = str8.replace("firstAuthor", k);
                    str8 = str8.replace("secondaryContent", l);
                    str8 = str8.replace("secondaryStars", m);
                    str8 = str8.replace("secondaryAuthor", username);
                    dm.userSecondReply(str8);
                    System.out.println("Success!!");
                    break;

                case "checkUserPost":
                    System.out.println(dm.checkUserPost(username));
                    break;

                case "checkUserFollow":
                    System.out.println(dm.checkUserFollow(username));
                    break;

                case "checkUserReply":
                    System.out.println(dm.checkUserReply(username));
                    break;

                case "getTop10Posts":
                    System.out.println(dm.getTop10Posts());
                    break;

                case "getPostsByTimeRange":
                    System.out.println("Enter the time range you want : ");
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String before = in.next();
                    String after = in.next();
                    try {
                        if (sdf.parse(before).getTime() > sdf.parse(before).getTime()) {
                            System.out.println("Invalid!Checking Your Input!");
                        } else {
                            System.out.println(dm.getPostsByTimeRange(before, after));
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "deleteFirstReply":
                    String str = "username;username;username;username";
                    str = str.replace("username", username);
                    dm.deleteFirstReply(str);
                    break;

                case "checkUserLike":
                    System.out.println(dm.checkUserLike(username));
                    break;

                case "checkUserFavorite":
                    System.out.println(dm.checkUserFavorite(username));
                    break;

                case "checkUserShare":
                    System.out.println(dm.checkUserShare(username));
                    break;

                case "exit":
                    flag = false;
                    System.out.println("Good Bye!");
                    break;
            }
        }
        dm.closeDatasource();
    }
}

