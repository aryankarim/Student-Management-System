//Author: ARYAN MAJEED KARIM
//Simple Student System Management Simulator
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends JFrame implements ActionListener {

    static class MyButtons extends JButton {

        static MyButtons insertBtn; // the home menu buttons
        static MyButtons searchBtn;
        static MyButtons updateBtn;
        static MyButtons deleteBtn;
        static MyButtons exitBtn;

        static MyButtons createUserBtn; // insertPanel
        static MyButtons backToHomeBtn;

        static MyButtons showAllBtn; // searchPanel
        static MyButtons searchStudentBtn;

        static MyButtons updateEmailBtn; // updatePanel

        static MyButtons deleteUserBtn; // deletePanel

        MyButtons(String text) { // CONSTRUCTOR for MyButtons
            super(text);
            if (text.compareTo("Exit") == 0)
                setBackground(new Color(233, 111, 94));
            else if (text.compareTo("back") == 0)
                setBackground(new Color(243, 169, 162));
            else
                setBackground(new Color(222, 188, 255));
        }
    }

    private static JPanel home = new JPanel();

    private JTable table = new JTable();
    private JScrollPane sp = new JScrollPane();

    private JTextField idTF = new JTextField();
    private JTextField fnameTF = new JTextField();
    private JTextField lnameTF = new JTextField();
    private JComboBox<String> gender;
    private JTextField emailTF = new JTextField();
    private JTextField averageTF = new JTextField();

    private JTextField searchidTF = new JTextField();

    private JTextField updateIdTF = new JTextField();
    private JTextField newEmailTF = new JTextField();

    private JTextField deleteUserTF = new JTextField();

    private DBC DBCOBJ = new DBC();

    public static void main(String[] args) {
        new Main();
        home.setBackground(new Color(196, 144, 255));
    }

    private Main() {
        setAlwaysOnTop(true);
        setLocation(300, 100);
        setSize(500, 500);
        setResizable(false);
        setTitle("Student Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        homeMenu();
    }

    private void homeMenu() {
        home.setLayout(null);
        MyButtons.insertBtn = new MyButtons("Insert");
        MyButtons.searchBtn = new MyButtons("Search");
        MyButtons.updateBtn = new MyButtons("Update");
        MyButtons.deleteBtn = new MyButtons("Delete");
        MyButtons.exitBtn = new MyButtons("Exit");

        MyButtons.insertBtn.addActionListener(this);
        MyButtons.searchBtn.addActionListener(this);
        MyButtons.updateBtn.addActionListener(this);
        MyButtons.deleteBtn.addActionListener(this);
        MyButtons.exitBtn.addActionListener(this);

        MyButtons.insertBtn.setBounds(100, 100, 200, 30);
        MyButtons.searchBtn.setBounds(100, 132, 200, 30);
        MyButtons.updateBtn.setBounds(100, 164, 200, 30);
        MyButtons.deleteBtn.setBounds(100, 196, 200, 30);
        MyButtons.exitBtn.setBounds(100, 250, 100, 30);

        home.add(MyButtons.insertBtn);
        home.add(MyButtons.searchBtn);
        home.add(MyButtons.updateBtn);
        home.add(MyButtons.deleteBtn);
        home.add(MyButtons.exitBtn);
        add(home);
        setVisible(true);
    }

    private void insertPanel() {
        home.removeAll();

        home.setLayout(null);
        JLabel id = new JLabel("ID:");
        JLabel fname = new JLabel("First Name:");
        JLabel lname = new JLabel("Last Name:");
        JLabel genderLabel = new JLabel("Gender:");
        gender = new JComboBox<>(new String[] { "Male", "Female" });
        JLabel email = new JLabel("Email:");
        JLabel average = new JLabel("Average:");

        MyButtons.createUserBtn = new MyButtons("create");
        MyButtons.createUserBtn.addActionListener(this);
        MyButtons.backToHomeBtn = new MyButtons("back");
        MyButtons.backToHomeBtn.addActionListener(this);

        id.setBounds(20, 30, 100, 28);
        idTF.setBounds(160, 30, 150, 28);
        fname.setBounds(20, 60, 100, 28);
        fnameTF.setBounds(160, 60, 150, 28);
        lname.setBounds(20, 90, 100, 28);
        lnameTF.setBounds(160, 90, 150, 28);
        genderLabel.setBounds(20, 120, 150, 28);
        gender.setBounds(160, 120, 150, 28);
        email.setBounds(20, 150, 100, 28);
        emailTF.setBounds(160, 150, 150, 28);
        average.setBounds(20, 180, 100, 28);
        averageTF.setBounds(160, 180, 150, 28);
        MyButtons.createUserBtn.setBounds(160, 220, 100, 30);
        MyButtons.backToHomeBtn.setBounds(20, 220, 100, 30);

        home.add(id);
        home.add(idTF);
        home.add(fname);
        home.add(fnameTF);
        home.add(lname);
        home.add(lnameTF);
        home.add(genderLabel);
        home.add(gender);
        home.add(email);
        home.add(emailTF);
        home.add(average);
        home.add(averageTF);
        home.add(MyButtons.createUserBtn);
        home.add(MyButtons.backToHomeBtn);
        add(home);

        home.revalidate();
        home.repaint();
    }

    private void searchPanel() {
        home.removeAll();
        home.setLayout(null);

        MyButtons.showAllBtn = new MyButtons("Show all students");
        MyButtons.showAllBtn.addActionListener(this);

        JLabel id = new JLabel("Enter Student ID:");

        MyButtons.searchStudentBtn = new MyButtons("Search");
        MyButtons.searchStudentBtn.addActionListener(this);
        MyButtons.backToHomeBtn = new MyButtons("back");
        MyButtons.backToHomeBtn.addActionListener(this);

        MyButtons.showAllBtn.setBounds(20, 30, 200, 30);
        id.setBounds(24, 70, 100, 30);
        searchidTF.setBounds(160, 70, 100, 30);

        MyButtons.searchStudentBtn.setBounds(160, 110, 100, 30);
        MyButtons.backToHomeBtn.setBounds(20, 110, 100, 30);

        home.add(MyButtons.showAllBtn);
        home.add(id);
        home.add(searchidTF);
        home.add(MyButtons.searchStudentBtn);
        home.add(MyButtons.backToHomeBtn);

        add(home);

        home.revalidate();
        home.repaint();
    }

    private void showInfo(ArrayList<String[]> arrayList) {
        String[][] array = new String[arrayList.size()][];
        for (int i = 0; i < arrayList.size(); i++) {
            array[i] = arrayList.get(i);
        }
        if (array[0][0].equalsIgnoreCase("empty")) {
            JOptionPane.showMessageDialog(home, "DB was empty");
            return;
        }
        if (array[0][0].equalsIgnoreCase("usernotfound")) {
            JOptionPane.showMessageDialog(home, "User does not exist!!");
            return;
        }
        if (array[0][0].equalsIgnoreCase("server")) {
            JOptionPane.showMessageDialog(home, "server seems to be down!!");
            return;
        }
        if (array[0][0].equalsIgnoreCase("query")) {
            JOptionPane.showMessageDialog(home, "failed!! use latest mySQL and JDK version!!");
            return;
        }
        if (table.getRowCount() > 0) {
            home.remove(table);
            home.remove(sp);
        }
        table = new JTable(array, new String[] { "ID", "First Name", "Last Name", "Email", "Gender", "Average" });
        table.setBounds(20, 150, 450, 200);
        sp = new JScrollPane(table);
        sp.setBounds(20, 150, 445, 200);
        home.add(sp);
        revalidate();
        repaint();
    }

    private void updatePanel() {
        home.removeAll();
        home.setLayout(null);

        JLabel id = new JLabel("Enter Student ID:");
        JLabel email = new JLabel("Enter New Email:");

        MyButtons.updateEmailBtn = new MyButtons("update");
        MyButtons.updateEmailBtn.addActionListener(this);
        MyButtons.backToHomeBtn = new MyButtons("back");
        MyButtons.backToHomeBtn.addActionListener(this);

        id.setBounds(20, 30, 100, 30);
        email.setBounds(20, 60, 100, 30);
        updateIdTF.setBounds(120, 30, 150, 30);
        newEmailTF.setBounds(120, 60, 150, 30);
        MyButtons.updateEmailBtn.setBounds(150, 100, 100, 30);
        MyButtons.backToHomeBtn.setBounds(20, 100, 100, 30);

        home.add(id);
        home.add(email);
        home.add(updateIdTF);
        home.add(newEmailTF);
        home.add(MyButtons.updateEmailBtn);
        home.add(MyButtons.backToHomeBtn);

        add(home);

        home.revalidate();
        home.repaint();
    }

    private void deletePanel() {

        home.removeAll();

        home.setLayout(null);

        JLabel id = new JLabel("Enter Student ID:");

        MyButtons.deleteUserBtn = new MyButtons("delete");
        MyButtons.deleteUserBtn.addActionListener(this);
        MyButtons.backToHomeBtn = new MyButtons("back");
        MyButtons.backToHomeBtn.addActionListener(this);

        id.setBounds(20, 30, 100, 30);
        deleteUserTF.setBounds(120, 30, 100, 30);
        MyButtons.deleteUserBtn.setBounds(120, 80, 100, 30);
        MyButtons.backToHomeBtn.setBounds(20, 80, 100, 30);

        home.add(id);
        home.add(deleteUserTF);
        home.add(MyButtons.deleteUserBtn);
        home.add(MyButtons.backToHomeBtn);

        add(home);

        home.revalidate();
        home.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == MyButtons.insertBtn) {
            insertPanel();
        }
        if (e.getSource() == MyButtons.searchBtn) {
            searchPanel();
        }
        if (e.getSource() == MyButtons.updateBtn) {
            updatePanel();
        }
        if (e.getSource() == MyButtons.deleteBtn) {
            deletePanel();
        }
        if (e.getSource() == MyButtons.exitBtn) {
            System.exit(0);
        }
        if (e.getSource() == MyButtons.deleteUserBtn) {
            if (deleteUserTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(home, "Please fill all the boxes!!");
            } else {
                int id;
                try {
                    id = Integer.parseInt(deleteUserTF.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(home, "This Box only allows Integers");
                    return;
                }
                int dialogResult = JOptionPane.showConfirmDialog(home, "Are you sure you wanna delete?", "Warning",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (dialogResult == JOptionPane.NO_OPTION || dialogResult == JOptionPane.CANCEL_OPTION) {
                    return;
                }
                JOptionPane.showMessageDialog(home, DBCOBJ.delete(id));
            }
        }
        if (e.getSource() == MyButtons.updateEmailBtn) {

            if (newEmailTF.getText().isEmpty() || updateIdTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(home, "Please fill all the boxes!!");
            } else {
                int id;
                try {
                    id = Integer.parseInt(updateIdTF.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(home, "The ID Box only allows Integers");
                    return;
                }
                String email = newEmailTF.getText();
                if (validateEmail(email)) {
                    JOptionPane.showMessageDialog(home, "invalid email!!!");
                    return;
                }
                int dialogResult = JOptionPane.showConfirmDialog(home, "Are you sure you wanna update?", "Warning",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (dialogResult == JOptionPane.NO_OPTION || dialogResult == JOptionPane.CANCEL_OPTION) {
                    return;
                }
                JOptionPane.showMessageDialog(home, DBCOBJ.updateEmail(id, email));
            }
        }
        if (e.getSource() == MyButtons.searchStudentBtn) {
            if (searchidTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(home, "Please fill the box!!");
            } else {
                int id;
                try {
                    id = Integer.parseInt(searchidTF.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(home, "This Box only allows Integers");
                    return;
                }
                showInfo(DBCOBJ.search(id));
            }
        }
        if (e.getSource() == MyButtons.showAllBtn) {
            showInfo(DBCOBJ.searchAll());
        }
        if (e.getSource() == MyButtons.backToHomeBtn) { // resetting the TFs to make the APP more neat
            idTF.setText("");
            lnameTF.setText("");
            fnameTF.setText("");
            averageTF.setText("");
            emailTF.setText("");
            searchidTF.setText("");
            updateIdTF.setText("");
            deleteUserTF.setText("");
            home.removeAll();
            homeMenu();
            home.revalidate();
            home.repaint();
        }
        if (e.getSource() == MyButtons.createUserBtn) {
            if (idTF.getText().isEmpty() || fnameTF.getText().isEmpty() || lnameTF.getText().isEmpty()
                    || emailTF.getText().isEmpty() || averageTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(home, "Please fill all the boxes!!");
            } else {
                Pattern p1 = Pattern.compile("[^a-z ]", Pattern.CASE_INSENSITIVE);
                Matcher fname = p1.matcher(fnameTF.getText());
                Matcher lname = p1.matcher(lnameTF.getText());

                if (fname.find() || lname.find()) {
                    JOptionPane.showMessageDialog(home, "Names cannot contain numbers or special characters! ");
                    return;
                }
                String email = emailTF.getText();
                if (validateEmail(email)) {
                    JOptionPane.showMessageDialog(home, "invalid email!!!");
                    return;
                }
                int id;
                float average;
                try {
                    id = Integer.parseInt(idTF.getText());
                    average = Float.parseFloat(averageTF.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(home,
                            "You cannot use letters or special characters or huge numbers in ID or Average field."
                                    + "\n                               NOTE:Average can accept decimal numbers!! ");
                    return;
                }
                if (id < 0) {
                    JOptionPane.showMessageDialog(home, "ID must be Zero  or bigger");
                    return;
                }
                if (average < 0 || average > 100) {
                    JOptionPane.showMessageDialog(home, "Average must be between 0 and 100");
                    return;
                }
                JOptionPane.showMessageDialog(home, DBCOBJ.insert(id, fnameTF.getText(), lnameTF.getText(), email,
                        Objects.requireNonNull(gender.getSelectedItem()).toString(), average));
            }
        }
    }

    // reference:
    // https://www.journaldev.com/638/java-email-validation-regex
    private boolean validateEmail(String email) {
        Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(email);
        return !matcher.matches();
    }
}
