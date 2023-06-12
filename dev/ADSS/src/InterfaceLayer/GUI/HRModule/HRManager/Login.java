package InterfaceLayer.GUI.HRModule.HRManager;
import BussinessLayer.HRModule.Controllers.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class Login {
    private JTextField idTextField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private final Facade _facade = Facade.getInstance();

    public Login() {
        // Initialize UI components
        idTextField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");

        // Set up the GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Set up the layout and add components to a container (e.g., JFrame)
        JFrame frame = new JFrame("Login");
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Add labels and text boxes
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("ID"), gbc);
        gbc.gridy = 1;
        panel.add(idTextField, gbc);
        gbc.gridy = 2;
        panel.add(new JLabel("Password"), gbc);
        gbc.gridy = 3;
        panel.add(passwordField, gbc);

        // Add login button
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(loginButton, gbc);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = -1;
                if(onlyDigits(idTextField.getText(),idTextField.getText().length())){
                    id = Integer.parseInt(idTextField.getText());
                }
                else{
                    JOptionPane.showMessageDialog(null, "ID must contain numbers only", "Error", JOptionPane.ERROR_MESSAGE);
                }

                String password = new String(passwordField.getPassword());

                if (_facade.login(id, password)) {
                    // Open the HRmenu screen
                    HRmenu hrMenu = new HRmenu();
                    hrMenu.setVisible(true);
                    frame.setVisible(false);
                }else {
                    // Show an error message if the login is incorrect
                    JOptionPane.showMessageDialog(null, "Invalid ID or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });
    }
    public static boolean onlyDigits(String str, int n)
    {

        for (int i = 0; i < n; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}