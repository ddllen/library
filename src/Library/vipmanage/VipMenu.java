package Library.vipmanage;

import Library.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VipMenu extends JFrame {
    public VipMenu(){
        Container container = this.getContentPane();
        JComboBox status=new JComboBox();
        status.addItem(null);
        status.addItem("录入会员信息");
        status.addItem("操作会员");
        status.addItem("将会员信息写入文件中");
        status.addItem("从文件中导入会员信息");
        JButton jButton = new JButton("确认");
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
