import javax.swing.*;
import java.awt.*;

public class Test {

    public static JButton subframe1(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        JFrame sublog = new JFrame();
        sublog.setLayout(new FlowLayout(FlowLayout.CENTER));
        sublog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int windowWidth = sublog.getWidth();
        int windowHeight = sublog.getHeight();
        int x = (screenWidth - windowWidth) / 2;
        int y = (screenHeight - windowHeight) / 2;
        sublog.setLocation(x,y);
        sublog.setVisible(true);
        sublog.setSize(240,  220);

        sublog.add(new JLabel("--------------------------------------------------------"));
        sublog.add(new JLabel("Please enter your name and password."));
        sublog.add(new JLabel("--------------------------------------------------------"));

        JLabel namelabel = new JLabel("Name");sublog.add(namelabel);
        JTextField textField1 = new JTextField(10);sublog.add(textField1);

        JLabel pwdlabel = new JLabel("Password");sublog.add(pwdlabel);
        JTextField textField2 = new JTextField(10);sublog.add(textField2);
        JButton okbutton = new JButton("Confirm");
        sublog.add(okbutton);
        return okbutton;
    }

    public static void main(String[] args) {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch(Exception e) {
            //TODO exception
            System.out.println("加载炫彩皮肤失败！");
        }
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e) {
            System.out.println(e);
        }

        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400,  300);
        mainFrame.setTitle("Meta Universe");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        // 计算窗口位置
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int windowWidth = mainFrame.getWidth();
        int windowHeight = mainFrame.getHeight();
        int x = (screenWidth - windowWidth) / 2;
        int y = (screenHeight - windowHeight) / 2;
        // 设置窗口位置
        mainFrame.setLocation(x, y);

        JLabel label = new JLabel("Welcome to Meta");
        label.setBounds(140,50,100,30);
        mainFrame.getContentPane().add(label);

        JButton registerButton = new JButton("register");
        registerButton.setBounds(160,150, 80, 30);
        JButton loginButton = new JButton("log in");
        loginButton.setBounds(280, 150,80, 30);
        JButton manageButton = new JButton("Manage");
        manageButton.setBounds(40,150,80,30);

        registerButton.addActionListener(e -> {
            JButton okbutton = subframe1();
            okbutton.addActionListener(e1 -> {
                //
            });
        });
        loginButton.addActionListener(e -> {
            JButton okbutton = subframe1();
            okbutton.addActionListener(e1 -> {

            });
        });
        manageButton.addActionListener(e -> {
            JButton okbutton = subframe1();
            okbutton.addActionListener(e1 -> {

            });
        });
        mainFrame.setLayout(null);
        mainFrame.getContentPane().add(registerButton);
        mainFrame.getContentPane().add(loginButton);
        mainFrame.getContentPane().add(manageButton);
        mainFrame.setVisible(true);
    }
}
