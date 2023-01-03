package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JoinInfoWindow extends JFrame implements ActionListener {

    private static final String DISPLAY_RESULT = "DISPLAY_RESULT";
    private Connection con;
    JTextField text_con;

    public JoinInfoWindow(Connection con) {
        super("Join Information");
        this.con = con;
        this.setJoinTable();
        this.setPreferredSize(new Dimension(400, 600));
        this.pack();
        this.setLayout(new GridLayout(4,1));
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setJoinTable() {
        JLabel label = new JLabel("<html>find the name of students and their resume file names, " +
                "who has a particular advisor</html>");
        add(label);
        JLabel label_con = new JLabel("Condition: workerID = ");
        text_con = new JTextField(5);

        JPanel con_p = new JPanel();
        con_p.setLayout(new FlowLayout());
        con_p.add(label_con);
        con_p.add(text_con);
        this.add(con_p);

        JButton button = new JButton("SELECT>>");
        button.setActionCommand(DISPLAY_RESULT);
        button.addActionListener(this);
        this.add(button);
    }

    private void displayResult() {
        // Data to be displayed in the JTable
        StringBuilder statement = new StringBuilder("SELECT Student.sname, Resume.file_name FROM Student," +
                " Resume, Coop WHERE Student.studentID = Coop.studentID and Student.studentID = Resume.studentID and " +
                "Coop.workerID = ");
        statement.append(text_con.getText());
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement.toString());
            ArrayList<ArrayList<String>> data = new ArrayList<>();
            ArrayList<String> columnNames = new ArrayList<>();
            columnNames.add("sname");
            columnNames.add("file_name");
            while(rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                data.add(row);
            }
            JTable result_table = new JTable(data.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new),
                    columnNames.toArray());
            result_table.setBounds(30, 40, 200, 300);
            result_table.getTableHeader().setBackground(Color.GRAY);
            JScrollPane sp = new JScrollPane(result_table);
            this.add(sp);
            setVisible(true);
        } catch (SQLException e) {
            System.out.println("invalid");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals(DISPLAY_RESULT)) {
            displayResult();
        }
    }

}
