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
import java.util.HashMap;
import java.util.Map;

public class ProjectInfoWindow extends JFrame implements ActionListener {

    private static final String CONFIRM_TABLE = "CONFIRM_TABLE";
    private static final String DISPLAY_RESULT = "DISPLAY_RESULT";
    private Connection con;
    private Map<String, ArrayList<String>> columnsForEachTable = new HashMap<>();
    JComboBox<String> cb;
    JList jList;

    public ProjectInfoWindow(Connection con) {
        super("Project Information");
        this.con = con;
        this.setProjectTable();
        this.setPreferredSize(new Dimension(400, 600));
        this.pack();
        this.setLayout(new GridLayout(4,1));
        this.setLocationRelativeTo(null);
        setVisible(true);
        setupTables();
    }

    private void setupTables() {
        ArrayList<String> sColumns = new ArrayList<>();
        sColumns.add("studentID");
        sColumns.add("sname");
        sColumns.add("major");
        columnsForEachTable.put("Student", sColumns);

        ArrayList<String> aColumns = new ArrayList<>();
        aColumns.add("workerID");
        aColumns.add("aname");
        aColumns.add("focus_major");
        columnsForEachTable.put("Advisor", aColumns);

        ArrayList<String> ngColumns = new ArrayList<>();
        ngColumns.add("studentID");
        ngColumns.add("degree");
        columnsForEachTable.put("NewGrads", ngColumns);

        ArrayList<String> cColumns = new ArrayList<>();
        cColumns.add("studentID");
        cColumns.add("year_level");
        cColumns.add("completed_coop_term");
        cColumns.add("workerID");
        columnsForEachTable.put("Coop", cColumns);

        ArrayList<String> jColumns = new ArrayList<>();
        jColumns.add("jobID");
        jColumns.add("jname");
        jColumns.add("companyID");
        jColumns.add("postal_code");
        columnsForEachTable.put("Job", jColumns);

        ArrayList<String> skColumns = new ArrayList<>();
        skColumns.add("skill_name");
        columnsForEachTable.put("Skill", skColumns);

        ArrayList<String> hColumns = new ArrayList<>();
        hColumns.add("skill_name");
        hColumns.add("studentID");
        hColumns.add("hProficiency");
        columnsForEachTable.put("HasSkill", hColumns);
    }

    private void setProjectTable() {
        JLabel label = new JLabel("Please select a table:");

        String[] choices = {"Student","Advisor", "NewGrads","Coop", "Job", "Skill", "HasSkill"};
        cb = new JComboBox<String>(choices);

        JButton button = new JButton("Confirm");
        button.setActionCommand(CONFIRM_TABLE);
        button.addActionListener(this);

        JPanel select_table = new JPanel();
        select_table.setLayout(new FlowLayout());
        select_table.add(label);
        select_table.add(cb);
        select_table.add(button);
        this.add(select_table);
    }

    private void setSelectAttributes() {
        JLabel label_attr = new JLabel("Please choose attributes:    ");
        String choice = (String) cb.getSelectedObjects()[0];
        String[] listItems = columnsForEachTable.get(choice).toArray(new String[0]);
        jList = new JList(listItems);
        jList.setFixedCellWidth(100);
        jList.setVisibleRowCount(4);
        jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JPanel select_attributes1 = new JPanel();
        select_attributes1.add(label_attr);
        select_attributes1.add(new JScrollPane(jList));
        this.add(select_attributes1);


        JButton button = new JButton("PROJECT>>");
        button.setActionCommand(DISPLAY_RESULT);
        button.addActionListener(this);

        JPanel select_attributes2 = new JPanel();
        select_attributes2.add(button);
        this.add(select_attributes2);

        setVisible(true);
    }

    private void displayResult() {
        StringBuilder statement = new StringBuilder("SELECT ");
        String column = (String) jList.getSelectedValuesList().get(0);
        String choice = (String) cb.getSelectedObjects()[0];
        statement.append(column).append(" From ").append(choice);
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement.toString());
            ArrayList<ArrayList<String>> data = new ArrayList<>();
            ArrayList<String> columnNames = new ArrayList<>();
            columnNames.add(column);
            while(rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                row.add(rs.getString(1));
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
        if (action.equals(CONFIRM_TABLE)) {
            setSelectAttributes();
        } else if (action.equals(DISPLAY_RESULT)) {
            displayResult();
        }
    }
}
