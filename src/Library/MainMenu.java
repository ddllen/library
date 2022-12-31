package Library;

import Library.librarymanage.LibraryMenu;
import Library.vipmanage.VipMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu(){
        Container container = getContentPane();
        this.setTitle("图书管理系统");
        setLayout(new GridLayout(2,1));
        setBounds(1000,500,200,200);
        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel("欢迎来到此图书管理系统",JLabel.CENTER);
        jLabel.setFont(new Font("微软雅黑", Font.BOLD, 13));
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jLabel,BorderLayout.CENTER);
        jPanel.setSize(250,500);
        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();jPanel2.setBounds(50,115,10,50);
        JPanel jPanel3 = new JPanel();jPanel3.setBounds(350,115,10,50);
        jPanel1.setSize(250,500);
        jPanel1.setLayout(new GridLayout(1,2));
        JButton bu1 = new JButton("图书管理");bu1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LibraryMenu();
                dispose();
            }
        });
        JButton bu2 = new JButton("会员管理");bu2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VipMenu();
                dispose();
            }
        });
        JButton bu3 = new JButton("退出");
        jPanel2.add(bu1);
        jPanel3.add(bu2);
        jPanel1.add(jPanel2);
        jPanel1.add(jPanel3);
        container.add(jPanel);
        container.add(jPanel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
