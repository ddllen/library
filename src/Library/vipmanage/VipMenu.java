package Library.vipmanage;

import Library.MainMenu;
import Library.librarymanage.LoginFrm;
import Library.librarymanage.operateBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class VipMenu extends JFrame {
    public VipMenu(){
        Container container = this.getContentPane();
        JLabel jLabel = new JLabel("会员界面");
        container.add(jLabel);
        JComboBox status=new JComboBox();
        status.addItem(null);
        status.addItem("操作会员信息");
        status.addItem("将会员信息写入文件中");
        status.addItem("从文件中导入会员信息");
        JButton jButton = new JButton("确认");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = Objects.requireNonNull(status.getSelectedItem()).toString();
                switch (s){
                    case "操作会员信息":new operateVip();break;
                    case "将会员信息写入文件中":;break;
                    case "从文件中导入会员信息":;break;
                }
                dispose();
            }
        });
        JButton jButton1=new JButton("返回");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenu();
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
