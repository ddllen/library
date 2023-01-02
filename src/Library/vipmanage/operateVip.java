package Library.vipmanage;

import Library.MainMenu;
import Library.librarymanage.AddBook;
import Library.librarymanage.BookEnquiry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class operateVip extends JFrame {
    public operateVip(){
        Container container = this.getContentPane();
        container.setLayout(new FlowLayout());
        JComboBox status=new JComboBox();
        status.addItem(null);
        status.addItem("添加会员");
        status.addItem("删除会员");
        status.addItem("浏览会员和查询会员");
        status.addItem("会员排序");
        status.addItem("插入会员");
        JButton jButton = new JButton("确认");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = Objects.requireNonNull(status.getSelectedItem()).toString();
                switch (s){
                    case "添加会员":new AddVip();break;
                    case "删除会员":;break;
                    case "浏览会员和查询会员":new VipEnquiry();break;
                    case "会员排序":;break;
                    case "插入会员":;break;
                }
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
        container.add(status);
        container.add(jButton);
        container.add(jButton1);
        this.setLocation(1000,500);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
