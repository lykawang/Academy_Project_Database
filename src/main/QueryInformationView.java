package main;

import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class QueryInformationView extends JFrame implements ActionListener {
    private Connection con;
    private static final String[] queries = {"Select Information", "Filter Information", "Join Information",
            "All postal codes for which their numbers of jobs is the most over all postal codes",
            "#offers by each student", "#offers by each student who have >= 3 offers",
            "All Students who has all skills"};

    public QueryInformationView(Connection con){
        super("Perform queries");
        this.setWindow();
        this.con = con;
        this.setUpLabelsAndButtons();
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void setWindow() {
        setPreferredSize(new Dimension(500, 300));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new GridLayout(4,2));
    }

    private void setUpLabelsAndButtons() {
        for (String s : queries) {
            JButton viewListButton = new JButton(s);
            add(viewListButton);
            viewListButton.setActionCommand(s);
            viewListButton.addActionListener(this);
            viewListButton.setForeground(Color.black);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals(queries[0])) {
            new SelectInfoWindow(con);
        } else if (action.equals(queries[1])) {
            new ProjectInfoWindow(con);
        } else if (action.equals(queries[2])) {
            new JoinInfoWindow(con);
        } else if (action.equals(queries[3])){
            Pair<String[][], String[]>  table = performQuery(con, 1);
            new HardCodeQueryView(action, table.getKey(), table.getValue());
        } else if (action.equals(queries[4])){
            Pair<String[][], String[]>  table = performQuery(con, 2);
            new HardCodeQueryView(action, table.getKey(), table.getValue());
        } else if (action.equals(queries[5])){
            Pair<String[][], String[]>  table = performQuery(con, 3);
            new HardCodeQueryView(action, table.getKey(), table.getValue());
        } else if (action.equals(queries[6])){
            Pair<String[][], String[]>  table = performQuery(con, 4);
            new HardCodeQueryView(action, table.getKey(), table.getValue());
        }
    }

    private Pair<String[][], String[]> performQuery(Connection con, int i) {
        ArrayList<ArrayList<String>> tableData = new ArrayList<>();
        ArrayList<String> columnNames = new ArrayList<>();
        StringBuilder statement;
        Statement st;
        ResultSet rs;
        if (i == 1) {
            statement = new StringBuilder("SELECT postal_code FROM Job GROUP BY postal_code HAVING COUNT(*) >= all (" +
                    "SELECT COUNT(*) FROM Job GROUP BY postal_code)");
            columnNames.add("postal_code");
            try {
                st = con.createStatement();
                rs = st.executeQuery(statement.toString());
                while(rs.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    row.add(rs.getString(1));
                    tableData.add(row);
                }
            } catch (SQLException e) {
                System.out.println("invalid");
            }
        } else if (i == 2) {
            statement = new StringBuilder("SELECT studentID, COUNT(*) FROM Offer GROUP BY studentID");
            columnNames.add("studentID");
            columnNames.add("#offers");
            try {
                st = con.createStatement();
                rs = st.executeQuery(statement.toString());
                while(rs.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    row.add(String.valueOf(rs.getInt(1)));
                    row.add(String.valueOf(rs.getInt(2)));
                    tableData.add(row);
                }
            } catch (SQLException e) {
                System.out.println("invalid");
            }
        } else if (i == 3) {
            statement = new StringBuilder("SELECT studentID, COUNT(*) FROM Offer GROUP BY studentID HAVING COUNT(*)" +
                    " >= 3");
            columnNames.add("studentID");
            columnNames.add("#offers");
            try {
                st = con.createStatement();
                rs = st.executeQuery(statement.toString());
                while(rs.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    row.add(String.valueOf(rs.getInt(1)));
                    row.add(String.valueOf(rs.getInt(2)));
                    tableData.add(row);
                }
            } catch (SQLException e) {
                System.out.println("invalid");
            }
        } else {
            statement = new StringBuilder("SELECT s1.studentID FROM Student s1 WHERE NOT EXISTS " +
                    "(SELECT Skill.skill_name FROM Skill MINUS (SELECT h1.skill_name FROM HasSkill h1 WHERE" +
                    " h1.studentID = s1.studentID))");
            columnNames.add("studentID");
            try {
                st = con.createStatement();
                rs = st.executeQuery(statement.toString());
                while(rs.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    row.add(String.valueOf(rs.getInt(1)));
                    tableData.add(row);
                }
            } catch (SQLException e) {
                System.out.println("invalid");
            }
        }
        Pair<String[][], String[]> p =
                new Pair<>(tableData.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new),
                columnNames.toArray(new String[0]));
        return p;
    }
}
