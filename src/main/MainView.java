package main;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class MainView extends JFrame implements ActionListener{
    private static final int BUTTON_POSITION = 100;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 20;
    private static final String MODIFY_STUDENTS = "MODIFY STUDENT";
    private static final String QUERY_INFORMATION = "QUERY INFORMATION";
    private static final String QUIT_APP_ACTION = "QUIT_APP_ACTION";
    private Connection con;

    public MainView(Connection con) {
        super("Student Co-op/Intern tracking system");
        this.setWindow();
        this.setUpLabelsAndButtons();
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        this.con = con;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void setUpLabelsAndButtons() {
        JLabel selectOptionLabel = new JLabel("Please select an option: ", JLabel.CENTER);
        selectOptionLabel.setBounds(26, 10, 300, 20);
        add(selectOptionLabel);
        selectOptionLabel.setForeground(Color.black);

        JButton viewListButton = new JButton("Modify Students");
        viewListButton.setBounds(BUTTON_POSITION, 40, BUTTON_WIDTH, BUTTON_HEIGHT);
        add(viewListButton);
        viewListButton.setActionCommand(MODIFY_STUDENTS);
        viewListButton.addActionListener(this);
        viewListButton.setForeground(Color.black);

        JButton emptyListButton = new JButton("Query Information");
        emptyListButton.setBounds(BUTTON_POSITION, 80, BUTTON_WIDTH, BUTTON_HEIGHT);
        add(emptyListButton);
        emptyListButton.setActionCommand(QUERY_INFORMATION);
        emptyListButton.addActionListener(this);
        emptyListButton.setForeground(Color.black);

        JButton quitAppButton = new JButton("Quit Application");
        quitAppButton.setBounds(BUTTON_POSITION, 240, BUTTON_WIDTH, BUTTON_HEIGHT);
        add(quitAppButton);
        quitAppButton.setActionCommand(QUIT_APP_ACTION);
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
        if (action.equals(MODIFY_STUDENTS)) {
            new ModifyStudentView(con);
        } else if (action.equals(QUERY_INFORMATION)) {
            new QueryInformationView(con);
        } else if (action.equals(QUIT_APP_ACTION)) {
            dispose();
        }
    }



}
