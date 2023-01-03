package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteView extends JFrame implements ActionListener {
    private Connection con;
    private JTextField studentNumberField;
    private static final String DELETE = "delete";

    public DeleteView(Connection con) {

        super("Delete a student");
        this.setWindow();
        this.setLabelsFieldsButtons();
        this.con = con;
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void setLabelsFieldsButtons() {
        JPanel panel = new JPanel();
        JLabel student_number = new JLabel("Enter Student number: ");
        student_number.setBounds(25, 100, 200, 20);
        panel.add(student_number);
        student_number.setForeground(Color.darkGray);
        studentNumberField = new JTextField(30);
        studentNumberField.setBounds(200, 100, 100, 20);
        panel.add(studentNumberField);
        add(panel);
        JButton deButton = new JButton("confirm");
        deButton.addActionListener(this);
        deButton.setBounds(150,100,200,20);
        add(deButton);
        deButton.setActionCommand(DELETE);
    }

    private void setWindow() {
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(500, 300));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new GridLayout(2,1));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals(DELETE)) {
            try {
                int snum = Integer.parseInt(studentNumberField.getText());
                PreparedStatement ps = con.prepareStatement("DELETE FROM Student WHERE studentID = ?");
                ps.setInt(1, snum);
                ps.executeUpdate();
                dispose();
            } catch (SQLException ex) {
                System.out.println("error");
            } catch (NumberFormatException ex) {
                System.out.println("invalid Student number");
            }
        }
    }
}
