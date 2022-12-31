package Library.librarymanage;

import Library.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LibraryMenu extends JFrame {
    public LibraryMenu() {
        Container container = this.getContentPane();
        JComboBox status=new JComboBox();
        status.addItem(null);
        status.addItem("操作图书");
        status.addItem("登录");
        status.addItem("借书");
        status.addItem("还书");
        status.addItem("将图书信息写入文件中");
        status.addItem("从文件中导入图书信息");
        JButton jButton = new JButton("确认");
        JButton jButton1=new JButton("返回");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenu();
                dispose();
            }
        });
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = Objects.requireNonNull(status.getSelectedItem()).toString();
                switch (s){
                    case "操作图书":new operateBook();break;
                    case "登录":new LoginFrm();break;
                    case "借书":break;
                    case "还书":break;
                    case "将图书信息写入文件中":break;
                    case "从文件中导入图书信息":break;
                }
                dispose();
            }
        });
        container.setLayout(new FlowLayout());
        container.add(status);
        container.add(jButton);
        container.add(jButton1);
        setBounds(1000,500,200,200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
