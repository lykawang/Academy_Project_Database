package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

public class ModifyStudentView extends JFrame implements ActionListener {
    private static final int BUTTON_POSITION = 100;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 20;
    private static final String ADD_STUDENT = "Add Student";
    private static final String UPDATE_STUDENT = "Update Student";
    private static final String DELETE_STUDENT = "Delete Student";
    private ModifyStudentView modifyStudentView;

    private QueryInformationView queryInformationView;
    private Image backgroundImage;
    private AddStudentView addStudentView;
    private Connection con;

    public ModifyStudentView(Connection con){
            super("Modify students");
            this.setWindow();
            this.con = con;
            this.setUpLabelsAndButtons();
            pack();
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
            setResizable(false);
        }

        private void setUpLabelsAndButtons() {
            JLabel selectOptionLabel = new JLabel("Please select an option: ", JLabel.CENTER);
            selectOptionLabel.setBounds(26, 10, 300, 20);
            add(selectOptionLabel);
            selectOptionLabel.setForeground(Color.black);

            JButton viewListButton = new JButton("Add Student");
            viewListButton.setBounds(BUTTON_POSITION, 40, BUTTON_WIDTH, BUTTON_HEIGHT);
            add(viewListButton);
            viewListButton.setActionCommand(ADD_STUDENT);
            viewListButton.addActionListener(this);
            viewListButton.setForeground(Color.black);

            //JButton emptyListButton = new JButton("Update Student");
            //emptyListButton.setBounds(BUTTON_POSITION, 80, BUTTON_WIDTH, BUTTON_HEIGHT);
            //add(emptyListButton);
            //emptyListButton.setActionCommand(UPDATE_STUDENT);
            //emptyListButton.addActionListener(this);
            //emptyListButton.setForeground(Color.black);

            JButton quitAppButton = new JButton("Delete Student");
            quitAppButton.setBounds(BUTTON_POSITION, 240, BUTTON_WIDTH, BUTTON_HEIGHT);
            add(quitAppButton);
            quitAppButton.setActionCommand(DELETE_STUDENT);
            quitAppButton.addActionListener(this);
            quitAppButton.setForeground(Color.black);
        }






        private void setWindow() {
            setPreferredSize(new Dimension(500, 300));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(null);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            if (action.equals(ADD_STUDENT)) {
                addStudentView = new AddStudentView(con);
            } else if (action.equals(UPDATE_STUDENT)) {
                queryInformationView = new QueryInformationView(con);
            } else if (action.equals(DELETE_STUDENT)) {
                new DeleteView(con);
            }
        }

    }



