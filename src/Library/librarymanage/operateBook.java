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
        status.addItem("删除图书和修改图书");
        status.addItem("浏览图书和查询图书");
        status.addItem("图书排序");
        status.addItem("插入图书");
        JButton jButton = new JButton("确认");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = Objects.requireNonNull(status.getSelectedItem()).toString();
                switch (s){
                    case "添加图书":new AddBook();break;
                    case "删除图书和修改图书":new BookDelete();break;
                    case "浏览图书和查询图书":new BookEnquiry();break;
                    case "图书排序":new BookSort();break;
                    case "插入图书":new InsertBook();break;
                }
                dispose();
            }
        });
        JButton jButton1=new JButton("返回");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LibraryMenu();
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
