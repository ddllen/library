package Library.librarymanage;

import Library.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class operateBook extends JFrame {
    public operateBook(){
        Container container = this.getContentPane();
        container.setLayout(new FlowLayout());
        JComboBox status=new JComboBox();
        status.addItem(null);
        status.addItem("添加图书");
        status.addItem("删除图书");
        status.addItem("浏览图书");
        status.addItem("查询图书");
        status.addItem("图书排序");
        status.addItem("插入图书");
        JButton jButton = new JButton("确认");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = Objects.requireNonNull(status.getSelectedItem()).toString();
                switch (s){
                    case "添加图书":new AddBook();
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

    public static void main(String[] args) {
        new operateBook();
    }
}
