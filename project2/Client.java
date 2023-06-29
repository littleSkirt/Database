import Util.ProxoolUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.text.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


public class Client {
    static DataManipulation dm;
    static boolean isManager = false;
//    static Toolkit toolkit = Toolkit.getDefaultToolkit();
//    static Dimension screenSize = toolkit.getScreenSize();
//    static int screenWidth = screenSize.width;
//    static int screenHeight = screenSize.height;

    public static void success() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(100, 100);
        // 设置窗口位置
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
//        frame.add(new JLabel("Success"));
        JButton jButton = new JButton("Success");
        frame.add(jButton);
        jButton.addActionListener(e1 -> {
            frame.dispose();
        });
    }

    public static void fail() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(100, 100);
        // 设置窗口位置
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
//        frame.add(new JLabel("Fail"));
        JButton jButton = new JButton("Fail");
        frame.add(jButton);
        jButton.addActionListener(e1 -> {
            frame.dispose();
        });
    }

    public static void content(String postid, String username) {
        int intid = Integer.parseInt(postid);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Content");
        frame.setSize(400, 300);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel(new BorderLayout());
        frame.getContentPane().add(panel);

        JPanel cUPpanel = new JPanel();
        cUPpanel.setLayout(new BoxLayout(cUPpanel, BoxLayout.Y_AXIS));
        String authorname = dm.chechAuthor(intid);
        cUPpanel.add(new JLabel("---------------------"));
        cUPpanel.add(new JLabel("Author name"));
        cUPpanel.add(new JLabel(authorname));//author name
        cUPpanel.add(new JLabel("---------------------"));
        JButton profile = new JButton("Profile");
        JButton follow = new JButton("Follow");
        JButton shield = new JButton("Shield");
        cUPpanel.add(profile);
        cUPpanel.add(follow);
        cUPpanel.add(shield);
        JButton quit = new JButton("  QUIT  ");
        cUPpanel.add(quit);
        quit.addActionListener(e -> {
            frame.dispose();
        });
        panel.add(cUPpanel, BorderLayout.WEST);
        int[] clickCount1 = {0};
        follow.addActionListener(e -> {
            clickCount1[0]++;
            if (clickCount1[0] == 1) {
                // 第一次点击执行的操作
                String authorid = dm.checkauthorid(authorname);
                String str1 = "num;username";
                str1 = str1.replace("num", authorid);
                str1 = str1.replace("username", username);
                try {
                    dm.addOneFollow(str1);
                    follow.setForeground(Color.RED);
                } catch (Exception e1) {
                    fail();
                    System.err.println(e1);
                }
                success();
            } else if (clickCount1[0] == 2) {
                try {
                    dm.minusOneFollow(postid, username);
                    follow.setForeground(Color.black);
                    clickCount1[0] = 0; // 重置计数器
                } catch (Exception e1) {
                    fail();
                    System.err.println(e1);
                }
                success();
            }

        });
        int[] clickCount5 = {0};
        shield.addActionListener(e -> {
            clickCount5[0]++;
            if (clickCount5[0] == 1) {
                    String str1 = "blackName;username";
                    str1 = str1.replace("blackName", authorname);
                    str1 = str1.replace("username", username);
                    // 将该用户加入黑名单
                    dm.addOneShield(str1);
                    shield.setForeground(Color.red);
                    success();
            } else if (clickCount5[0] == 2) {
                shield.setForeground(Color.black);
                success();
                clickCount5[0] = 0; // 重置计数器
            }
        });
            //ifmanager,can view the profile
        profile.addActionListener(e -> {
            if (isManager){
                profile(authorname);
                success();
            }else {
                fail();
            }
        });
        JTextArea contentArea = new JTextArea();
//        contentArea.setEditable(false); // 禁止编辑
//        contentArea.setLineWrap(true); // 自动换行
//        contentArea.setWrapStyleWord(true); // 按单词换行
        String contents = dm.checkPostBYid(intid); //获取帖子内容
        contentArea.setText(contents); // 将帖子内容设置到文本区域中
//        frame.getContentPane().add(contentArea);
        panel.add(contentArea, BorderLayout.CENTER);

        JPanel eastPlane = new JPanel();
        eastPlane.setLayout(new BoxLayout(eastPlane, BoxLayout.Y_AXIS));
        JButton like = new JButton("   Like     ");
        eastPlane.add(like);
        JButton share = new JButton("    Share    ");
        eastPlane.add(share);
        JButton favorite = new JButton("   Favorite   ");
        eastPlane.add(favorite);
        JButton delete = new JButton("    Delete    ");
        eastPlane.add(delete);
        JButton reply1 = new JButton("First Replys");
        eastPlane.add(reply1);
        panel.add(eastPlane, BorderLayout.EAST);

        // 定义计数器变量
        int[] clickCount2 = {0};
        like.addActionListener(e -> {
            clickCount2[0]++;
            if (clickCount2[0] == 1) {
                String str1 = "num;username";
                str1 = str1.replace("num", postid);
                str1 = str1.replace("username", username);
                try{
                    dm.addOneLike(str1);
                    like.setForeground(Color.red);
                    success();
                }catch (Exception e1){
                    fail();
                    System.out.println(e1);
                }
            } else if (clickCount2[0] == 2) {
                // 将该帖子ID和当前用户的用户名从点赞列表中删除
                try{
                    dm.deleteLike(username, postid);
                    like.setForeground(Color.black);
                    clickCount2[0] = 0; // 重置计数器
                    success();
                }catch (Exception e1){
                    fail();
                    System.out.println(e1);
                }


            }
        });

        int[] clickCount3 = {0};
        favorite.addActionListener(e -> {
            clickCount3[0]++;
            if (clickCount3[0] == 1) {
                // 第一次点击执行的操作
                // 将该帖子ID和当前用户的用户名添加到收藏列表中
                String str1 = "num;username";
                str1 = str1.replace("num", postid);
                str1 = str1.replace("username", username);
                try {
                    dm.addOneFavorite(str1);
                    favorite.setForeground(Color.RED);
                } catch (Exception e1) {
                    fail();
                    System.err.println(e1);
                }
                success();
            } else if (clickCount3[0] == 2) {
                // 第二次点击执行的操作
                try {
                    dm.deleteFavorite(username, postid);
                    favorite.setForeground(Color.black);
                } catch (Exception e1) {
                    fail();
                    System.err.println(e1);
                }
                success();
                clickCount3[0] = 0; // 重置计数器
            }
        });

        int[] clickCount4 = {0};
        share.addActionListener(e -> {
            clickCount4[0]++;
            if (clickCount4[0] == 1) {
                // 第一次点击执行的操作
                // 将该帖子ID和当前用户的用户名添加到收藏列表中
                try {
                    String str1 = "num;username";
                    str1 = str1.replace("num", postid);
                    str1 = str1.replace("username", username);
                    dm.addOneShare(str1);
                    share.setForeground(Color.RED);
                } catch (Exception e1) {
                    fail();
                    System.err.println(e1);
                }
                success();
            } else if (clickCount4[0] == 2) {
                // 第二次点击执行的操作
                try {
                    dm.deleteShare(username, postid);
                    share.setForeground(Color.black);
                } catch (Exception e1) {
                    fail();
                    System.err.println(e1);
                }
                success();
                clickCount4[0] = 0; // 重置计数器
            }
        });

        delete.addActionListener(e1 -> {
            if (!isManager) {
                fail();
            } else {
                dm.deletePost(postid);
                success();
            }
        });

        reply1.addActionListener(e1 -> {
            JFrame subframe = new JFrame();
            subframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            subframe.setLocationRelativeTo(null);
            subframe.setSize(400,500);
            subframe.setLocationRelativeTo(null);
            subframe.setVisible(true);

            JPanel replypanel = new JPanel();
            subframe.getContentPane().add(replypanel);
            JPanel uppanle = new JPanel(new FlowLayout());
            uppanle.add(new JLabel("First Reply"));
            JButton quit2 = new JButton("QUIT");
            uppanle.add(quit2);
            quit2.addActionListener(e -> {
                subframe.dispose();
            });
            replypanel.add(uppanle, BorderLayout.NORTH);

            JTextArea textArea = new JTextArea(5, 20);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setSize(350, 400);
            textArea.setText(dm.checkReplyBYid(intid));//check by id
//            System.out.println(dm.checkReplyBYid(intid));
            replypanel.add(scrollPane, BorderLayout.CENTER);

            JPanel downpanel = new JPanel();
            downpanel.setLayout(new BoxLayout(downpanel,BoxLayout.Y_AXIS));
            replypanel.add(downpanel, BorderLayout.SOUTH);
            JTextField field1 = new JTextField(20);//insert author name
            JButton viewDetail = new JButton("View Detail");
            JTextField field2 = new JTextField(20);//add content
            JButton addReply = new JButton("Add a First Reply");
            JTextField field3 = new JTextField(20);
            JButton deletein = new JButton("DELETE");
            downpanel.add(new JLabel("Please enter the content."));
            downpanel.add(field1);
            downpanel.add(new JLabel("Please enter the star"));
            JTextField field4 = new JTextField(10);
            downpanel.add(field4);
            downpanel.add(addReply);
            downpanel.add(field2);
            downpanel.add(viewDetail);
            downpanel.add(field3);
            downpanel.add(deletein);
            addReply.addActionListener(e -> {
                int star = Integer.parseInt(field4.getText());
                String content = field2.getText();
                try {
                    dm.userFirstReply(postid,content,star,username,authorname);
                    success();
                }catch (Exception e2) {
                    System.err.println(e2);
                    fail();
                }
//               dm.userFirstReply();
            });
            deletein.addActionListener(e -> {
                String firstid = field3.getText();
                try {
                    dm.deleteFirstReply(firstid);
                    success();
                }catch (Exception e3) {
                    fail();
                    System.err.println(e3);
                }
            });
            viewDetail.addActionListener(e -> {
                JFrame sencondFrame = new JFrame();
                sencondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                sencondFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
                sencondFrame.setLocationRelativeTo(null);
                sencondFrame.setVisible(true);
                sencondFrame.setSize(300,300);
                String firstAuthorName = field1.getText();
                JPanel mainSecond = new JPanel();
                sencondFrame.getContentPane().add(mainSecond);
                mainSecond.add(new JLabel("Sencond Reply"), BorderLayout.NORTH);

                JTextArea textArea1 = new JTextArea(5, 20);
                JScrollPane scrollPane1 = new JScrollPane(textArea1);
//                scrollPane1.setSize(300, 400);
                System.out.println(dm.checkSecondReply(intid,firstAuthorName));
                textArea1.setText(dm.checkSecondReply(intid,firstAuthorName));//check by id
//                textArea1.setSize(300, 400);
                mainSecond.add(scrollPane1, BorderLayout.CENTER);

                JPanel downPanel = new JPanel();
                downPanel.setLayout(new BoxLayout(downPanel,BoxLayout.Y_AXIS));
                JTextField secondContent = new JTextField(20);
                JTextField secondStar = new JTextField(10);
                downPanel.add(secondContent);
                downPanel.add(secondStar);
                JButton add = new JButton("Add Second Reply");
                downPanel.add(add);
                JButton quit1 = new JButton("QUIT");
                downPanel.add(quit1);
                mainSecond.add(downPanel,BorderLayout.SOUTH);
                quit1.addActionListener(e2 -> {
                    sencondFrame.dispose();
                });
                add.addActionListener(e2 -> {
                    int sstar = Integer.parseInt(secondStar.getText());
                    try{
                        dm.userSecondReply(intid,firstAuthorName,secondContent.getText(),sstar,username,firstAuthorName);
                        success();
                    }catch (Exception e3){
                        fail();
                        System.err.println(e3);
                    }
                });

                JTextField deletesecond = new JTextField(20);//insert
                JButton sdelete = new JButton("DELETE");
                downPanel.add(deletesecond);
                downPanel.add(sdelete);
                sdelete.addActionListener(e2 -> {
                    try{
                        dm.deleteSecondReply(deletesecond.getText());
                        success();
                    }catch (Exception e3){
                        fail();
                        System.err.println(e3);
                    }
                });
            });
        });
    }

    public static void profile(String username) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
        frame.setTitle("Profile");
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel(new BorderLayout());
        frame.getContentPane().add(panel);

        JPanel uppanel = new JPanel();
        uppanel.setLayout(new BoxLayout(uppanel, BoxLayout.Y_AXIS));
        uppanel.add(new JLabel("---------------------------------"));
        JLabel user = new JLabel("  username: " + username);uppanel.add(user);
        String str1 = dm.getUserInfo(username);
        System.out.println(str1);
        uppanel.add(new JLabel("---------------------------------"));
        uppanel.add(new JLabel("  userid:" + str1.split("\t")[0] ));
        uppanel.add(new JLabel("  phone: " + str1.split("\t")[1]));
        uppanel.add(new JLabel("  registeration time: " ));
        uppanel.add(new JLabel("  "+str1.split("\t")[2]));
        JButton changePhone = new JButton("Change Phone number");
        uppanel.add(changePhone);
        JButton quit = new JButton("QUIT");
        uppanel.add(quit);
        quit.addActionListener(e -> {
            frame.dispose();
        });
        panel.add(uppanel, BorderLayout.NORTH);

        JPanel downpanel = new JPanel();
        downpanel.setLayout(new BoxLayout(downpanel,BoxLayout.Y_AXIS));
        downpanel.add(new JLabel("---------------------------------"));
        downpanel.add(new JLabel("  View your profile"));
        JButton posts = new JButton("Posts");
        downpanel.add(posts);
        JButton like = new JButton("Like");
        downpanel.add(like);
        JButton share = new JButton("Share");
        downpanel.add(share);
        JButton favorite = new JButton("Favorite");
        downpanel.add(favorite);
        JButton follow = new JButton("Follow");
        downpanel.add(follow);
        JButton reply1 = new JButton("First Replys");
        downpanel.add(reply1);
        JButton reply2 = new JButton("Second Replys");
        downpanel.add(reply2);
        downpanel.add(new JLabel("---------------------------------"));
        downpanel.add(new JLabel("---------------------------------"));
        panel.add(downpanel, BorderLayout.SOUTH);

        changePhone.addActionListener(e -> {
            // 创建一个新的 JFrame 对象，作为输入新电话号码的界面
            JFrame phoneframe = new JFrame();
            phoneframe.setTitle("Change Phone");
            phoneframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            phoneframe.setSize(400, 150);
            phoneframe.setVisible(true);
            phoneframe.setLocationRelativeTo(null);
            // 添加一个标签和输入框，用于输入新的电话号码
            JPanel phonemain = new JPanel(new BorderLayout());
            phoneframe.add(phonemain);
            phonemain.add(new JLabel("Please enter the new phone number"), BorderLayout.NORTH);
            JTextField newnumber = new JTextField(30);
            phonemain.add(newnumber, BorderLayout.CENTER);
            // 添加一个按钮，用于提交新的电话号码
            JButton ok = new JButton("OK");
            phonemain.add(ok, BorderLayout.SOUTH);
            ok.addActionListener(e1 -> {
                try {
                    String phonestr = "phone;username";
                    phonestr = phonestr.replace("phone",newnumber.getText());
                    phonestr = phonestr.replace("username",username);
                    System.out.println(phonestr);
                    dm.changePhone(phonestr);
                }catch (Exception e2){
                    fail();
                    System.err.println(e2);
                }
                success();
                phoneframe.dispose();
            });
            JButton quitButton = new JButton("QUIT");
            phonemain.add(quitButton);
            // 按下 QUIT 按钮后，退出程序
            quitButton.addActionListener(e3 -> {
                phoneframe.dispose();
                System.exit(0);
            });

        });
        //quit button 还没好
        like.addActionListener(e1 -> {
            // 创建一个新的 JFrame 对象，作为显示用户喜欢的文章列表的界面
            JFrame subframe = new JFrame();
            subframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            subframe.setSize(400, 300);
            subframe.setVisible(true);
            subframe.setLocationRelativeTo(null);
            // 获取用户收藏的文章列表
            String userLike = dm.checkUserLike(username.toString());
            // 创建一个文本域，用于显示用户喜欢的文章列表
            JTextArea likeList = new JTextArea(userLike);
            likeList.setEditable(false);
            // 创建一个滚动条，用于滚动显示文章列表
            JScrollPane scrollPane = new JScrollPane(likeList);
            subframe.getContentPane().add(scrollPane, BorderLayout.CENTER);
            // 添加一个面板，用于放置删除按钮、输入框和退出按钮
            JPanel panel1 = new JPanel(new FlowLayout());
            subframe.getContentPane().add(panel1, BorderLayout.SOUTH);
            // 添加一个标签和输入框，用于输入要删除文章的编号
            JLabel label = new JLabel("请输入要删除的文章编号：");
            panel1.add(label);
            JTextField postplace1 = new JTextField(10);
            panel1.add(postplace1);
            // 添加一个删除按钮
            JButton deleteButton = new JButton("DELETE");
            panel1.add(deleteButton);
            // 添加一个退出按钮
            JButton quitButton = new JButton("QUIT");
            panel1.add(quitButton);
            // 按下 QUIT 按钮后，退出程序
            quitButton.addActionListener(e3 -> {
                subframe.dispose();
                System.exit(0);
            });
            // 按下 DELETE 按钮后，执行删除操作
            deleteButton.addActionListener(e2 -> {
                String num1 = postplace1.getText();
                dm.deleteLike(username.toString(), num1);
                success();
            });
        });
        favorite.addActionListener(e1 -> {
            // 创建一个新的 JFrame 对象，作为显示用户收藏的文章列表的界面
            JFrame subframe = new JFrame();
            subframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            subframe.setSize(400, 300);
            subframe.setVisible(true);
            subframe.setLocationRelativeTo(null);
            // 获取用户收藏的文章列表
            String userFavorite = dm.checkUserFavorite(username.toString());
            // 创建一个文本域，用于显示用户收藏的文章列表
            JTextArea favoriteList = new JTextArea(userFavorite);
            favoriteList.setEditable(false);
            // 创建一个滚动条，用于滚动显示文章列表
            JScrollPane scrollPane = new JScrollPane(favoriteList);
            subframe.getContentPane().add(scrollPane, BorderLayout.CENTER);
            // 添加一个面板，用于放置删除按钮、输入框和退出按钮
            JPanel panel1 = new JPanel(new FlowLayout());
            subframe.getContentPane().add(panel1, BorderLayout.SOUTH);
            // 添加一个标签和输入框，用于输入要删除文章的编号
            JLabel label = new JLabel("请输入要删除的文章编号：");
            panel1.add(label);
            JTextField postplace1 = new JTextField(10);
            panel1.add(postplace1);
            // 添加一个删除按钮
            JButton deleteButton = new JButton("DELETE");
            panel1.add(deleteButton);
            // 添加一个退出按钮
            JButton quitButton = new JButton("QUIT");
            panel1.add(quitButton);
            // 按下 QUIT 按钮后，退出程序
            quitButton.addActionListener(e3 -> {
                subframe.dispose();
                System.exit(0);
            });
            // 按下 DELETE 按钮后，执行删除操作
            deleteButton.addActionListener(e2 -> {
                String num1 = postplace1.getText();
                dm.deleteFavorite(username.toString(), num1);
                success();
            });
        });
        share.addActionListener(e1 -> {
            // 创建一个新的 JFrame 对象，作为显示用户分享的文章列表的界面
            JFrame subframe = new JFrame();
            subframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            subframe.setSize(400, 300);
            subframe.setVisible(true);
            subframe.setLocationRelativeTo(null);
            // 获取用户分享的文章列表
            String userShare = dm.checkUserShare(username.toString());
            // 创建一个文本域，用于显示用户分享的文章列表
            JTextArea shareList = new JTextArea(userShare);
            shareList.setEditable(false);
            // 创建一个滚动条，用于滚动显示文章列表
            JScrollPane scrollPane = new JScrollPane(shareList);
            subframe.getContentPane().add(scrollPane, BorderLayout.CENTER);
            // 添加一个面板，用于放置删除按钮、输入框和退出按钮
            JPanel spanel = new JPanel(new FlowLayout());
            subframe.getContentPane().add(spanel, BorderLayout.SOUTH);
            // 添加一个标签和输入框，用于输入要删除文章的编号
            JLabel label = new JLabel("请输入要删除的文章编号：");
            spanel.add(label);
            JTextField postplace1 = new JTextField(10);
            spanel.add(postplace1);
            // 添加一个按钮，用于删除分享的文章
            JButton deleteButton = new JButton("DELETE");
            spanel.add(deleteButton);
            // 添加一个退出按钮
            JButton quitButton = new JButton("QUIT");
            spanel.add(quitButton);
            // 按下 QUIT 按钮后，退出程序
            quitButton.addActionListener(e3 -> {
                subframe.dispose();
                System.exit(0);
            });
            // 按下 DELETE 按钮后，执行删除操作
            deleteButton.addActionListener(e2 -> {
                String num1 = postplace1.getText();
                dm.deleteShare(username.toString(), num1);
                success();
            });
        });
        follow.addActionListener(e1 -> {
            // 创建一个新的 JFrame 对象，作为显示用户关注列表的界面
            JFrame subframe = new JFrame();
            subframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            subframe.setSize(400, 300);
            subframe.setVisible(true);
            subframe.setLocationRelativeTo(null);
            // 显示用户的关注列表
            subframe.add(new JLabel(dm.checkUserFollow(username.toString())));
            // 添加一个面板，用于放置取消关注按钮和退出按钮
            JPanel rpanel = new JPanel(new FlowLayout());
            subframe.getContentPane().add(rpanel, BorderLayout.SOUTH);
            // 添加一个标签和输入框，用于输入要取消关注的用户名
            JLabel label = new JLabel("请输入要取消关注的用户名：");
            rpanel.add(label);
            JTextField followField = new JTextField(10);
            rpanel.add(followField);
            // 添加一个按钮，用于取消关注
            JButton deleteButton = new JButton("DELETE");
            rpanel.add(deleteButton);
            // 添加一个退出按钮
            JButton quitButton = new JButton("QUIT");
            rpanel.add(quitButton);
            // 按下 DELETE 按钮后，执行取消关注操作
            deleteButton.addActionListener(e2 -> {
                String followName = followField.getText();
                dm.minusOneFollow(username.toString(), followName);
                success();
            });
            // 按下 QUIT 按钮后，退出程序
            quitButton.addActionListener(e3 -> {
                subframe.dispose();
                System.exit(0);
            });
        });

        reply1.addActionListener(e1 -> {
            // 创建一个新的 JFrame 对象，作为显示用户一级回复列表的界面
            JFrame subframe = new JFrame();
            subframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            subframe.setSize(400, 300);
            subframe.setVisible(true);
            subframe.setLocationRelativeTo(null);
            // 显示用户的一级回复列表
            subframe.add(new JLabel(dm.checkUserReply(username)));
            // 添加一个面板，用于放置删除按钮和退出按钮
            JPanel rpanel = new JPanel(new FlowLayout());
            subframe.getContentPane().add(rpanel, BorderLayout.SOUTH);
            // 添加一个标签和输入框，用于输入要删除一级回复的编号
            JLabel label = new JLabel("请输入要删除的一级回复编号：");
            rpanel.add(label);
            JTextField replyNumField = new JTextField(10);
            rpanel.add(replyNumField);
            // 添加一个按钮，用于删除一级回复
            JButton deleteButton = new JButton("DELETE");
            rpanel.add(deleteButton);
            // 添加一个退出按钮


            // 按下 DELETE 按钮后，执行删除操作
            deleteButton.addActionListener(e2 -> {
                String num1 = replyNumField.getText();
                dm.deleteFirstReply(num1);
                success();
            });
            JButton quitButton = new JButton("QUIT");
            // 按下 QUIT 按钮后，退出程序
            rpanel.add(quitButton);
            quitButton.addActionListener(e3 -> {
                subframe.dispose();
                System.exit(0);
            });
        });


        reply2.addActionListener(e1 -> {
            // 创建一个新的 JFrame 对象，作为输入回复内容和显示用户二级回复列表的界面
            JFrame subframe = new JFrame();
            subframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            subframe.setSize(400, 300);
            subframe.setVisible(true);
            subframe.setLocationRelativeTo(null);
            // 添加一个标签和输入框，用于输入回复的内容
            JLabel label = new JLabel("请输入回复内容：");
            subframe.getContentPane().add(label, BorderLayout.NORTH);
            JTextField replyField = new JTextField(20);
            subframe.getContentPane().add(replyField, BorderLayout.CENTER);
            // 显示用户的二级回复列表
            subframe.add(new JLabel(dm.checkUserSecondReply(username)));
            // 添加一个面板，用于放置删除按钮和退出按钮
            JPanel rpanel = new JPanel(new FlowLayout());
            subframe.getContentPane().add(rpanel, BorderLayout.SOUTH);
            // 添加一个标签和输入框，用于输入要删除二级回复的编号
            JLabel label2 = new JLabel("请输入要删除的二级回复编号：");
            rpanel.add(label2);
            JTextField replyNumField = new JTextField(10);
            rpanel.add(replyNumField);
            // 添加一个按钮，用于删除二级回复
            JButton deleteButton = new JButton("DELETE");
            rpanel.add(deleteButton);
            // 添加一个退出按钮
            JButton quitButton = new JButton("QUIT");
            rpanel.add(quitButton);
            // 按下 DELETE 按钮后，执行删除操作
            deleteButton.addActionListener(e2 -> {
                String num1 = replyNumField.getText();
                dm.deleteSecondReply(num1);
                success();
            });
            // 按下 QUIT 按钮后，退出程序
            quitButton.addActionListener(e3 -> {
                subframe.dispose();
                System.exit(0);
            });
            // 添加一个按钮，用于提交回复内容
//            JButton submitButton = new JButton("SUBMIT");
//            subframe.getContentPane().add(submitButton, BorderLayout.EAST);
            // 按下 SUBMIT 按钮后，执行提交操作
//            submitButton.addActionListener(e4 -> {
//                String replyContent = replyField.getText();
                // 进行回复操作，例如：dm.replyToPost(postId, replyContent);
//                success();
//            });
        });

        posts.addActionListener(e1 -> {
            // 创建一个新的 JFrame 对象，作为显示用户发布的文章列表的界面
            JFrame subframe = new JFrame();
            subframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            subframe.setSize(400, 300);
            subframe.setVisible(true);
            subframe.setLocationRelativeTo(null);
            // 获取用户发布的文章列表
            String userPosts = dm.checkUserPost(username.toString());
            // 创建一个文本域，用于显示用户发布的文章列表
            JTextArea postList = new JTextArea(userPosts);
            subframe.add(postList);
            // 创建一个滚动条，用于滚动显示文章列表
            JScrollPane scrollPane = new JScrollPane(postList);
            subframe.getContentPane().add(scrollPane, BorderLayout.CENTER);
            // 添加一个面板，用于放置删除按钮和退出按钮
            JPanel ppanel = new JPanel(new FlowLayout());
            subframe.getContentPane().add(ppanel, BorderLayout.SOUTH);
            // 添加一个标签和输入框，用于输入要删除文章的编号
            JLabel label = new JLabel("请输入要删除的文章编号：");
            ppanel.add(label);
            JTextField postplace1 = new JTextField(10);
            ppanel.add(postplace1);
            // 添加一个按钮，用于删除发布的文章
            JButton deleteButton = new JButton("DELETE");
            ppanel.add(deleteButton);
            // 添加一个退出按钮
            JButton quitButton = new JButton("QUIT");
            ppanel.add(quitButton);
            // 按下 DELETE 按钮后，执行删除操作
            deleteButton.addActionListener(e2 -> {
                String num1 = postplace1.getText();
                dm.deletePost(num1);
                success();
            });
            String updatedPosts = dm.checkUserPost(username.toString());
            postList.setText(updatedPosts);
            // 按下 QUIT 按钮后，退出程序
            quitButton.addActionListener(e3 -> {
                subframe.dispose();
                System.exit(0);
            });
        });

        frame.pack();
    }


    public static void main(String[] args) {
//        dm = new DataFactory().createDataManipulation(args[0]);
        dm = new DatabaseManipulation(ProxoolUtil.getInstance());
        dm.openDatasource();
        AtomicReference<String> username = new AtomicReference<>("");
        AtomicReference<String> password = new AtomicReference<>("");
        AtomicReference<String> phone = new AtomicReference<>("");

        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            System.out.println("fail！");
        }
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        mainFrame.setTitle("Meta Universe");
        mainFrame.setLocationRelativeTo(null);

        JLabel label = new JLabel("Welcome to Meta");
        label.setBounds(140, 50, 100, 30);
        mainFrame.getContentPane().add(label);
        JButton registerButton = new JButton("register");
        registerButton.setBounds(160, 150, 80, 30);
        JButton loginButton = new JButton("log in");
        loginButton.setBounds(280, 150, 80, 30);
        JButton manageButton = new JButton("Manage");
        manageButton.setBounds(40, 150, 80, 30);
        mainFrame.setLayout(null);
        mainFrame.getContentPane().add(registerButton);
        mainFrame.getContentPane().add(loginButton);
        mainFrame.getContentPane().add(manageButton);
        mainFrame.setVisible(true);

        JButton into = new JButton("Get into Meta");
        JFrame sublog = new JFrame();
        sublog.setLayout(new FlowLayout(FlowLayout.CENTER));
        sublog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sublog.setSize(200, 250);
        sublog.setLocationRelativeTo(null);

        registerButton.addActionListener(e -> {
            sublog.setVisible(true);
            sublog.add(new JLabel("--------------------------------------------------------"));
            sublog.add(new JLabel("Please enter your name"));
            sublog.add(new JLabel("password and phone number."));
            sublog.add(new JLabel("--------------------------------------------------------"));

            JLabel namelabel = new JLabel("Name");
            sublog.add(namelabel);
            JTextField textField1 = new JTextField(10);
            sublog.add(textField1);
            JLabel pwdlabel = new JLabel("Password");
            sublog.add(pwdlabel);
            JTextField textField2 = new JTextField(10);
            sublog.add(textField2);
            JLabel phonelabel = new JLabel("Phone");
            sublog.add(phonelabel);
            JTextField textField3 = new JTextField(10);
            sublog.add(textField3);

            JButton okbutton = new JButton("Confirm");
            sublog.add(okbutton);
            sublog.add(into);
            okbutton.addActionListener(e1 -> {
                username.set(textField1.getText());
                password.set(textField2.getText());
                phone.set(textField3.getText());
                System.out.println(username.toString());
                try {
                    String str = "username;password;phone;level";
                    str = str.replace("username", username.toString());
                    str = str.replace("password", password.toString());
                    str = str.replace("phone", phone.toString());
                    str = str.replace("level", "user");
                    dm.addOneUser(str);
                    success();
                } catch (IllegalArgumentException e2) {
                    System.out.println("register");
                    System.err.println(e2.getMessage());
                }
                //
            });
        });
        loginButton.addActionListener(e -> {
            sublog.setVisible(true);
            sublog.add(new JLabel("--------------------------------------------------------"));
            sublog.add(new JLabel("Please enter your name"));
            sublog.add(new JLabel("and password."));
            sublog.add(new JLabel("--------------------------------------------------------"));

            JLabel namelabel = new JLabel("Name");
            sublog.add(namelabel);
            JTextField textField1 = new JTextField(10);
            sublog.add(textField1);
            JLabel pwdlabel = new JLabel("Password");
            sublog.add(pwdlabel);
            JTextField textField2 = new JTextField(10);
            sublog.add(textField2);

            JButton okbutton = new JButton("Confirm");
            sublog.add(okbutton);
            sublog.add(into);
            okbutton.addActionListener(e1 -> {
                username.set(textField1.getText());
                password.set(textField2.getText());
                try {
                    if (!dm.checkUser(username.toString(), password.toString())) { //todo
                        success();
                    } else {
                        fail();
//                        success();
                    }
                } catch (IllegalArgumentException e2) {
                    System.out.println("login");
                    System.err.println(e2.getMessage());
                }
            });
        });
        manageButton.addActionListener(e -> {
            sublog.setVisible(true);
            sublog.add(new JLabel("--------------------------------------------------------"));
            sublog.add(new JLabel("Please enter your name"));
            sublog.add(new JLabel(" and password."));
            sublog.add(new JLabel("--------------------------------------------------------"));

            JLabel namelabel = new JLabel("Name");
            sublog.add(namelabel);
            JTextField textField1 = new JTextField(10);
            sublog.add(textField1);
            JLabel pwdlabel = new JLabel("Password");
            sublog.add(pwdlabel);
            JTextField textField2 = new JTextField(10);
            sublog.add(textField2);

            JButton okbutton = new JButton("Confirm");
            sublog.add(okbutton);
            sublog.add(into);
            okbutton.addActionListener(e1 -> {
                username.set(textField1.getText());
                password.set(textField2.getText());
                System.out.println(username.toString());
                System.out.println(password.toString());
                try {
                    if (!dm.checkManager(username.toString(), password.toString())) {
                        isManager = true;
                        success();
                    } else {
                        fail();
                    }
                } catch (IllegalArgumentException e2) {
                    System.out.println("manager");
                    System.err.println(e2.getMessage());
                }
            });
        });

        into.addActionListener(ea -> {
            JFrame main = new JFrame();
            mainFrame.dispose();
            sublog.dispose();
            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            main.setSize(600, 600);
            main.setTitle("Meta Universe");
            main.setExtendedState(JFrame.MAXIMIZED_BOTH);
            main.setVisible(true);
            main.setLocationRelativeTo(null);
            JPanel mainPanel = new JPanel(new BorderLayout());
            main.getContentPane().add(mainPanel);
            mainPanel.add(new JLabel("hi"));
//            mainPanel.setSize(600, 400);

            JTextField search = new JTextField(10);
            JButton searchicon = new JButton("<html>&#x1F50D;</html>");
            JButton searchintime = new JButton("in Time");
            // 在顶部添加搜索组件
            JPanel searchPanel = new JPanel(new FlowLayout());
            JButton profile = new JButton("Profile");
            JButton mainquit = new JButton("QUIT");
            mainquit.addActionListener(e -> {
                main.dispose();
                dm.closeDatasource();
            });
            searchPanel.add(new JLabel("Search"));
            searchPanel.add(search);
            searchPanel.add(searchicon);
            searchPanel.add(searchintime);
            searchPanel.add(new JLabel("  "));
            searchPanel.add(profile);
            mainPanel.add(searchPanel, BorderLayout.NORTH);
            main.getContentPane().add(searchPanel, BorderLayout.NORTH);
            ////////////////////////////////////////////////////////////////////
            searchicon.addActionListener(eb -> {
                String searchcontent = search.getText();
                JFrame searchResult = new JFrame();
                searchResult.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                searchResult.setExtendedState(JFrame.MAXIMIZED_BOTH);
                searchResult.setLocationRelativeTo(null);
                searchResult.setVisible(true);
                JPanel spanel = new JPanel(new BorderLayout());
                searchResult.getContentPane().add(spanel);

                JPanel sUPpanel = new JPanel(new FlowLayout());
                sUPpanel.add(new JLabel("Search Results"));
                JButton quit = new JButton("QUIT");
                sUPpanel.add(quit);
                quit.addActionListener(e -> {
                    searchResult.dispose();
                });
                spanel.add(sUPpanel, BorderLayout.NORTH);

                JTextArea text = new JTextArea(5, 20);
                JScrollPane textplane = new JScrollPane(text);
                try {
                    text.setText(dm.getPostsByKeywords(searchcontent));
                    spanel.add(textplane, BorderLayout.CENTER);
                } catch (Exception e) {
                    fail();
                    System.err.println(e);
                }

                JPanel sDOWNpanel = new JPanel(new FlowLayout());
                JButton findDetail = new JButton("Find details");
                sDOWNpanel.add(findDetail);
                spanel.add(sDOWNpanel, BorderLayout.SOUTH);

                findDetail.addActionListener(e -> {
                    JFrame aim = new JFrame();
                    aim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    aim.setLayout(new FlowLayout(FlowLayout.CENTER));
                    aim.setLocationRelativeTo(null);
                    aim.setSize(50,100);
                    aim.setVisible(true);
                    JTextField field = new JTextField(10);
                    aim.add(field);
                    JButton ok = new JButton("OK");
                    aim.add(ok);
                    ok.addActionListener(e1 -> {
                        String id = field.getText();
                        content(id, username.toString());
                        System.out.println("content"+username.toString());
                        aim.dispose();
                    });
                });
            });
            searchintime.addActionListener(eb -> {
                JFrame searchResult = new JFrame();
                searchResult.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                searchResult.setLocationRelativeTo(null);
                JPanel spanel = new JPanel(new BorderLayout());
                searchResult.getContentPane().add(spanel);
                searchResult.setVisible(true);

                JPanel sUPpanel = new JPanel(new FlowLayout());
                sUPpanel.add(new JLabel("Search Results"));
                JButton quit = new JButton("QUIT");
                sUPpanel.add(quit);
                quit.addActionListener(e -> {
                    searchResult.dispose();
                });
                spanel.add(sUPpanel, BorderLayout.NORTH);

                JTextArea text = new JTextArea(5, 20);
                JScrollPane textplane = new JScrollPane(text);
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timecontent = search.getText();
                String before = timecontent.split("--")[0];
                String after = timecontent.split("--")[1];
                try {
                    if (sdf.parse(before).getTime() > sdf.parse(before).getTime()) {
                        fail();
                        System.out.println("Invalid!Checking Your Input!");
                    } else {
                        success();
                        text.setText(dm.getPostsByTimeRange(before, after));
                    }
                } catch (ParseException e3) {
                    throw new RuntimeException(e3);
                }
                spanel.add(textplane, BorderLayout.CENTER);

                JPanel sDOWNpanel = new JPanel(new FlowLayout());
                JButton findDetail = new JButton("Find details");
                sDOWNpanel.add(findDetail);
                spanel.add(sDOWNpanel, BorderLayout.SOUTH);

                findDetail.addActionListener(e -> {
                    JFrame aim = new JFrame();
                    aim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    aim.setLayout(new FlowLayout(FlowLayout.CENTER));
                    aim.setLocationRelativeTo(null);
                    aim.setVisible(true);
                    JTextField field = new JTextField(10);
                    aim.add(field);
                    JButton ok = new JButton("OK");
                    aim.add(ok);
                    ok.addActionListener(e1 -> {
                        String id = field.getText();
                        content(id, username.toString());
                    });
                });
            });
            profile.addActionListener(eb -> {
                profile(username.toString());
            });


            ///////////////////////////////////////////////////////////////
            // 在中间添加滚动面板
            JTextArea textArea = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setSize(350, 400);
            textArea.setText(dm.getTop10Posts());
            textArea.setSize(300, 400);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            //add
            JLabel posttitle = new JLabel("Title");
            JLabel postcontent = new JLabel("Content");
            JLabel category = new JLabel("Category");
            JTextField postplace1 = new JTextField(10);
            JTextField postplace2 = new JTextField(10);
            JTextField postplace3 = new JTextField(10);
//            JTextField postplace4 = new JTextField(10);
            JButton send = new JButton("SEND");
            JButton sendananomous = new JButton("<html>&#x2764;</html>");
            JPanel postPanel = new JPanel(new FlowLayout());
            postPanel.add(posttitle);
            postPanel.add(postplace1);
            postPanel.add(postcontent);
            postPanel.add(postplace2);
            postPanel.add(category);
            postPanel.add(postplace3);
            postPanel.add(send);
//            postPanel.add(postplace4);
            postPanel.add(sendananomous);
            mainPanel.add(postPanel, BorderLayout.SOUTH);

            send.addActionListener(e -> {
                String content = postplace1.getText();
                String title = postplace2.getText();
                String cate = postplace3.getText();
//                String authorID =postplace4.getText();
                int postId = dm.findMaxPostId() + 1;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                String c = formatter.format(date);
                String d = "Shen Zhe";
                String f = "China";
                String str6 = "postID;title;content;postTime;postCity;postCountry;0";
                String str7 ="authorName;authorID;phone";
                str6 = str6.replace("postID", Integer.toString(postId));
                str6 = str6.replace("title", title);
                str6 = str6.replace("content", content);
                str6 = str6.replace("postTime", c);
                str6 = str6.replace("postCity", d);
                str6 = str6.replace("postCountry", f);
                str7 =str7.replace("authorName",username.toString());
//                String authorID = dm.checkauthorid(username.toString());
                str7 =str7.replace("authorID","903507260085781632");
                str7 =str7.replace("phone",phone.toString());
                dm.userPosting(str6, cate,str7);
                success();
            });
            sendananomous.addActionListener(eb -> {
                String content = postplace1.getText();
                String title = postplace2.getText();
                String cate = postplace3.getText();
                int postId = dm.findMaxPostId() + 1;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                String c = formatter.format(date);
                String d = "Shen Zhe";
                String f = "China";
                String str6 = "postID;title;content;postTime;postCity;postCountry;1";
                str6 = str6.replace("postID", Integer.toString(postId));
                str6 = str6.replace("title", title);
                str6 = str6.replace("content", content);
                str6 = str6.replace("postTime", c);
                str6 = str6.replace("postCity", d);
                str6 = str6.replace("postCountry", f);
                String str7=" ; ; ; ";
                dm.userPosting(str6, cate,str7);
                success();
            });
            main.pack();
        });
    }
}






