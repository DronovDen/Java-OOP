package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame implements ActionListener {

    public static final String LOGIN = "Login";
    public static final String GO_REGISTRER = "Go registrer";
    private final JTextField txtName;
    private final MainAgarIO connection;
    public boolean loginCorrect;

    public LoginView(MainAgarIO connection) {

        this.connection = connection;
        this.loginCorrect = false;
        setTitle("Agar.io");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel lbTitle = new JLabel("Choose your nickname:", SwingConstants.CENTER);
        lbTitle.setFont(new java.awt.Font("Calibri", 1, 26));

        txtName = new JTextField();
        txtName.setFont(new java.awt.Font("Calibri", 1, 18));

        JButton butLogin = new JButton("Login");
        butLogin.setFont(new java.awt.Font("Calibri", 1, 18));
        butLogin.setActionCommand(LOGIN);
        butLogin.addActionListener(this);

        setLayout(new BorderLayout());

        JPanel auxLogin = new JPanel();
        auxLogin.setLayout(new GridLayout(9, 3));
        auxLogin.add(new JLabel());
        auxLogin.add(lbTitle);
        auxLogin.add(new JLabel());
        auxLogin.add(txtName);
        auxLogin.add(new JLabel());
        auxLogin.add(butLogin);
        add(auxLogin, BorderLayout.CENTER);
    }

    public String getName() {
        return txtName.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if (command.equals(LOGIN)) {
            connection.getController().login(getName());
            if (connection.getController().isCorrectLogin()) {
                JOptionPane.showMessageDialog(null, "Welcome to Agar.io");
                this.setVisible(false);
                connection.play();
            }
        }
    }
}
