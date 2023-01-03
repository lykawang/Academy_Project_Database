package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HardCodeQueryView extends JFrame implements ActionListener {
    private static final String CLOSE = "CLOSE";
    private String[][] data;
    private String[] columns;

    public HardCodeQueryView(String queryTitle, String[][] data, String[] columns) {
        super(queryTitle);
        this.data = data;
        this.columns = columns;
        this.setWindow();
        this.setUpLabelsAndButtons();
        this.pack();
        this.setDefaultCloseOperation(2);
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.setResizable(false);
    }

    private void setUpLabelsAndButtons() {
        JTable result_table = new JTable(data, columns);
        result_table.setBounds(30, 40, 200, 300);
        result_table.getTableHeader().setBackground(Color.GRAY);
        JScrollPane sp = new JScrollPane(result_table);
        this.add(sp);
        sp.setVisible(true);
        JButton close = new JButton("close");
        close.setBounds(100, 230, 300, 20);
        this.add(close);
        close.setActionCommand("CLOSE");
        close.addActionListener(this);
        close.setForeground(Color.black);
    }

    private void setWindow() {
        this.setPreferredSize(new Dimension(500, 300));
        ((JPanel)this.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        this.setLayout(new GridLayout(2,1));
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("CLOSE")) {
            this.dispose();
        }
    }
}

