package itcarlow.c00112726.vote.gui;

import itcarlow.c00112726.vote.entity.Candidate;
import itcarlow.c00112726.vote.util.GUIUtilities;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewCandidateDialog extends JDialog {

    private JPanel contentPane;

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JComboBox<String> cmbParty;

    // Temporary
    private final String[] parties = {
            "- SELECT -",
            "Fine Fail",
            "Fine Gael",
            "Sinn Fein",
            "Labour",
            "Independent"
    };

    public AddNewCandidateDialog(JFrame parent) {
        super(parent, "Add New Candidate", true);
        setLayout(new BorderLayout());

        generateGUI();

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void generateGUI() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        final JPanel imagePanel = new JPanel() {
            @Override
        public Dimension getPreferredSize() {
                return new Dimension(128,128);
            }
        };
        imagePanel.setBorder(new TitledBorder("Candidate Image"));
        GUIUtilities.addGridItem(mainPanel, imagePanel, 0, 0, 1, 3, GridBagConstraints.CENTER);

        GUIUtilities.addGridItem(mainPanel, new JLabel("First Name:"), 1, 0, 1, 1, GridBagConstraints.EAST);
        GUIUtilities.addGridItem(mainPanel, new JLabel("Last Name:"), 1, 1, 1, 1, GridBagConstraints.EAST);
        GUIUtilities.addGridItem(mainPanel, new JLabel("Party:"), 1, 2, 1, 1, GridBagConstraints.EAST);

        txtFirstName = new JTextField(15);
        GUIUtilities.addGridItem(mainPanel, txtFirstName, 2, 0, 1, 1, GridBagConstraints.WEST);

        txtLastName = new JTextField(15);
        GUIUtilities.addGridItem(mainPanel, txtLastName, 2, 1, 1, 1, GridBagConstraints.WEST);

        cmbParty = new JComboBox<>(parties);
        cmbParty.setSelectedIndex(-1);
        GUIUtilities.addGridItem(mainPanel, cmbParty, 2, 2, 300, 1, GridBagConstraints.WEST);

        final JButton btnBrowse = new JButton("Browse");
        GUIUtilities.addGridItem(mainPanel, btnBrowse, 0, 3, 1, 1, GridBagConstraints.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Add the buttons to the form, OK and Cancel. Require a different layout
        // so we add them to their own panel.
        final JPanel btnPanel = new JPanel(); // Components added right to left
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        final JButton  btnOK = new JButton("OK");
        btnOK.addActionListener(e -> submit());

        final JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());

        btnPanel.add(btnCancel);
        btnPanel.add(btnOK);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private boolean validateInput() {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        int party = cmbParty.getSelectedIndex();
        if (firstName.equals("") || lastName.equals("") || party < 0) {
            return false;
        }
        return true;
    }

    private void submit() {
        if(!validateInput()) {
            JOptionPane.showMessageDialog(this, "One or more fields are invalid.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            final String firstName = txtFirstName.getText().trim();
            final String lastName = txtLastName.getText().trim();
            final String party = cmbParty.getSelectedItem().toString();
            final Candidate candidate = new Candidate(firstName, lastName, party);
            Candidate.addCandidate(candidate);
            final String message = "The following Candidate has been added\n" +
                    "\nName: " + firstName + " " + lastName + "\nParty: " + party;
            JOptionPane.showMessageDialog(this, message, "Candidate Added", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}
