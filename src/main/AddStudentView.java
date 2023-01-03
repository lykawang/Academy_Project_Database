package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class AddStudentView extends JFrame implements ActionListener {
    JTextField advisorField;
    JTextField yearLevelField;
    JTextField degreeField;


    JTextField coopTermField;
    JTextField majorField;
    JTextField studentNameField;

    private Connection con;
    JTextField studentNumberField;
    JCheckBox checkBox1;

    private static final String FINISH_ACTION = "done";

    private static final String UPDATE = "update";
    private JLabel titleCoop;
    private JLabel yearLevel;
    private JLabel coopTerm;
    private JLabel advisor;

    public AddStudentView(Connection con) {

        super("add a new student to system");
        this.setWindow();
        this.setLabelsFieldsButtons();
        this.con = con;
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //EFFECTS: set lables fields buttons for college application elements;
    private void setLabelsFieldsButtons() {
        JLabel student_name = new JLabel("Enter Student name: ");
        student_name.setBounds(25, 50, 200, 20);
        add(student_name);
        student_name.setForeground(Color.darkGray);
        studentNameField = new JTextField(30);
        studentNameField.setBounds(200, 50, 100, 20);
        add(studentNameField);

        JLabel student_number = new JLabel("Enter Student number: ");
        student_number.setBounds(25, 100, 200, 20);
        add(student_number);
        student_number.setForeground(Color.darkGray);
        studentNumberField = new JTextField(30);
        studentNumberField.setBounds(200, 100, 100, 20);
        add(studentNumberField);

        JLabel major = new JLabel(
                "Enter Student Major");
        major.setBounds(25, 150, 200, 20);
        add(major);
        major.setForeground(Color.darkGray);
        majorField = new JTextField(30);
        majorField.setBounds(200, 150, 100, 20);
        add(majorField);

        checkBox1 = new JCheckBox("Is Co-op?");
        checkBox1.setBounds(50, 180, 200, 50);
        add(checkBox1);

        JLabel titleNG = new JLabel("For New Grad students");

        titleNG.setBounds(300, 30, 400, 20);
        add(titleNG);
        JLabel degree = new JLabel("Enter your degree: ");
        degree.setBounds(300, 100, 400, 20);
        add(degree);
        degree.setForeground(Color.darkGray);
        degreeField = new JTextField(30);
        degreeField.setBounds(450, 100, 300, 20);
        add(degreeField);
        setLabelsCoopFieldsButtons();
        checkBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    titleNG.setVisible(false);
                    degree.setVisible(false);
                    degreeField.setVisible(false);
                    titleCoop.setVisible(true);
                    yearLevel.setVisible(true);
                    yearLevelField.setVisible(true);
                    coopTerm.setVisible(true);
                    coopTermField.setVisible(true);
                    advisor.setVisible(true);
                    advisorField.setVisible(true);
                } else {
                    titleCoop.setVisible(false);
                    yearLevel.setVisible(false);
                    yearLevelField.setVisible(false);
                    coopTerm.setVisible(false);
                    coopTermField.setVisible(false);
                    advisor.setVisible(false);
                    advisorField.setVisible(false);
                    titleNG.setVisible(true);
                    degree.setVisible(true);
                    degreeField.setVisible(true);

                }
                ;
            }
        });

        JButton addButton = new JButton("Add");
        addButton.setBounds(210, 240, 100, 20);
        add(addButton);
        addButton.setActionCommand(FINISH_ACTION);
        //finishButton.addActionListener((ActionListener) this);
        addButton.setForeground(Color.darkGray);
        addButton.addActionListener(this::actionPerformed);

        JButton updateButton = new JButton("update");
        updateButton.setBounds(410, 240, 100, 20);
        add(updateButton);
        updateButton.setActionCommand(UPDATE);
        //finishButton.addActionListener((ActionListener) this);
        updateButton.setForeground(Color.darkGray);
        updateButton.addActionListener(this::actionPerformed);
    }


    private void setLabelsCoopFieldsButtons() {
        titleCoop = new JLabel("For coop students");
        titleCoop.setBounds(300, 30, 400, 20);
        add(titleCoop);
        titleCoop.setVisible(false);

        yearLevel = new JLabel("Enter year level: ");
        yearLevel.setBounds(300, 50, 400, 20);
        add(yearLevel);
        yearLevel.setVisible(false);
        yearLevel.setForeground(Color.darkGray);
        yearLevelField = new JTextField(30);
        yearLevelField.setBounds(400, 50, 300, 20);
        add(yearLevelField);
        yearLevelField.setVisible(false);
        coopTerm = new JLabel("Enter #coop term: ");
        coopTerm.setBounds(300, 100, 400, 20);
        add(coopTerm);
        coopTerm.setVisible(false);
        coopTerm.setForeground(Color.darkGray);
        coopTermField = new JTextField(30);
        coopTermField.setBounds(420, 100, 300, 20);
        add(coopTermField);
        coopTermField.setVisible(false);

        advisor = new JLabel(
                "Enter advisor name:");
        advisor.setBounds(300, 150, 600, 20);
        add(advisor);
        advisor.setVisible(false);
        advisor.setForeground(Color.darkGray);
        advisorField = new JTextField(30);
        advisorField.setBounds(420, 150, 300, 20);
        add(advisorField);
        advisorField.setVisible(false);
    }


    //:EFFECTS: set windows elements and set up;
    private void setWindow() {
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 300));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(null);
    }

    //EFFECTS: do corresponding actions when clicked a certain button;
    public void actionPerformed(ActionEvent e) {
        {
            String action = e.getActionCommand();
            String studentName = studentNameField.getText();
            String major = majorField.getText();
            String studentNumber = studentNumberField.getText();
            if (action.equals(FINISH_ACTION)) {
                int snum = 0;
                try {
                    snum = Integer.parseInt(studentNumber);
                    PreparedStatement ps = con.prepareStatement("INSERT INTO Student " +
                            "(studentID, sname, major) VALUES (?, ?, ?)");
                    ps.setInt(1, snum);
                    ps.setString(2, studentName);
                    ps.setString(3, major);

                    ps.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("error");
                } catch (NumberFormatException ex) {
                    System.out.println("invalid Student Number");
                }
                if (checkBox1.isSelected()) {
                    String numTerms = coopTermField.getText();
                    String yearLevel = yearLevelField.getText();
                    String advisor = advisorField.getText();
                    try {
                        int yl = Integer.parseInt(yearLevel);
                        int ny = Integer.parseInt(numTerms);
                        int ad = Integer.parseInt(advisor);
                        PreparedStatement ps = con.prepareStatement("INSERT INTO Coop " +
                                "(studentID, year_level, completed_coop_term, workerID) VALUES (?, ?, ?, ?)");
                        ps.setInt(1, snum);
                        ps.setInt(2, yl);
                        ps.setInt(3, ny);
                        ps.setInt(4, ad);
                        ps.executeUpdate();
                    } catch (SQLException ex) {
                        System.out.println("error");
                    } catch (NumberFormatException ex) {
                        System.out.println("invalid Number");
                    }
                } else {
                    String degree = degreeField.getText();
                    try {
                        PreparedStatement ps = con.prepareStatement("INSERT INTO NewGrads " +
                                "(studentID, degree) VALUES (?, ?)");
                        ps.setInt(1, snum);
                        ps.setString(2, degree);

                        ps.executeUpdate();
                    } catch (SQLException ex) {
                        System.out.println("error");
                    }
                }
            } else if (action.equals(UPDATE)) {
                try {
                    Statement st = con.createStatement();
                    StringBuffer statement = new StringBuffer("Update Student SET");
                    int numSelected = 0;
                    if (!studentName.equals("")) {
                        statement.append(" sname = '").append(studentName).append("'");
                        numSelected++;
                    }
                    if (!major.equals("")) {
                        if (numSelected > 0) {
                            statement.append(", major = '").append(major).append("'");
                        } else {
                            statement.append(" major = '").append(major).append("'");
                        }
                    }
                    statement.append(" WHERE studentID = ").append(studentNumber);
                    st.executeUpdate(statement.toString());
                    if (checkBox1.isSelected()) {
                        String numTerms = coopTermField.getText();
                        String yearLevel = yearLevelField.getText();
                        String advisor = advisorField.getText();
                        StringBuffer statement2 = new StringBuffer("Update Coop SET");
                        numSelected = 0;
                        if (!yearLevel.equals("")) {
                            statement2.append(" year_level = ").append(yearLevel);
                            numSelected++;
                        }
                        if (!numTerms.equals("")) {
                            if (numSelected > 0) {
                                statement2.append(", completed_coop_term = ").append(numTerms);
                            } else {
                                statement2.append(" completed_coop_term = ").append(numTerms);
                                numSelected++;
                            }
                        }
                        if (!advisor.equals("")) {
                            if (numSelected > 0) {
                                statement2.append(", workerID = ").append(advisor);
                            } else {
                                statement2.append(" workerID ").append(advisor);
                            }
                        }
                        statement2.append(" WHERE studentID = ").append(studentNumber);
                        st.executeUpdate(statement2.toString());
                    } else {
                        String degree = degreeField.getText();
                        StringBuffer statement2 = new StringBuffer("Update NewGrads SET");
                        if (!degree.equals("")) {
                            statement2.append(" degree = '").append(degree).append("'");
                        }
                        statement2.append(" WHERE studentID = ").append(studentNumber);
                        st.executeUpdate(statement2.toString());
                    }
                } catch (SQLException exception) {
                    System.out.println("invalid");
                }
            }
            dispose();
        }
    }
}

