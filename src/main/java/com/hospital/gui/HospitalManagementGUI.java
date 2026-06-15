package com.hospital.gui;

import com.hospital.service.HospitalManagementSystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import com.hospital.model.Ambulance;
import com.hospital.model.Department;
import com.hospital.model.Employee;
import com.hospital.model.Patient;
import com.hospital.model.Room;

public class HospitalManagementGUI extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Professional blue
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94); // Dark blue-gray
    private static final Color ACCENT_COLOR = new Color(46, 204, 113); // Green for success
    private static final Color ERROR_COLOR = new Color(231, 76, 60); // Red for errors
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Light gray background
    private static final Color PANEL_COLOR = Color.WHITE; // White for panels
    private static final Color TEXT_COLOR = new Color(44, 62, 80); // Dark text
    private static final Color HEADER_COLOR = new Color(52, 73, 94); // Dark blue-gray for headers
    
    // Update font constants
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font SUBHEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TABLE_HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);
    
    // Update icon constants
    private static final String ADD_PATIENT_ICON = "👤";
    private static final String ROOM_ICON = "🏠";
    private static final String DEPARTMENT_ICON = "🏥";
    private static final String EMPLOYEE_ICON = "👨‍⚕️";
    private static final String PATIENT_INFO_ICON = "📋";
    private static final String DISCHARGE_ICON = "🚪";
    private static final String SEARCH_ICON = "🔍";
    private static final String UPDATE_ICON = "✏️";
    private static final String AMBULANCE_ICON = "🚑";
    
    private final HospitalManagementSystem system;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final GridBagConstraints gbc;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    private int nextPatientId; // Add counter for sequential patient IDs

    public HospitalManagementGUI() {
        system = new HospitalManagementSystem();
        List<Patient> existingPatients = system.getAllPatients();
nextPatientId = existingPatients.stream()
    .mapToInt(p -> {
        try {
            return Integer.parseInt(p.getPatientId().replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    })
    .max()
    .orElse(0) + 1;
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Hospital Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setBackground(BACKGROUND_COLOR);

        // Add panels
        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createMainMenuPanel(), "MAIN_MENU");
        mainPanel.add(createAddPatientPanel(), "ADD_PATIENT");
        mainPanel.add(createRoomPanel(), "ROOMS");
        mainPanel.add(createDepartmentPanel(), "DEPARTMENTS");
        mainPanel.add(createEmployeePanel(), "EMPLOYEES");
        mainPanel.add(createPatientListPanel(), "PATIENT_LIST");
        mainPanel.add(createDischargePanel(), "DISCHARGE");
        mainPanel.add(createSearchRoomPanel(), "SEARCH_ROOM");
        mainPanel.add(createUpdatePatientPanel(), "UPDATE_PATIENT");
        mainPanel.add(createAmbulancePanel(), "AMBULANCE");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
        
        return button;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Create a white panel for the login form
        JPanel loginFormPanel = new JPanel(new GridBagLayout());
        loginFormPanel.setBackground(PANEL_COLOR);
        loginFormPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        // Title Label
        JLabel titleLabel = new JLabel("Hospital Management System");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        
        // Username field
        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(300, 40));
        styleTextField(usernameField);
        
        // Password field
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(300, 40));
        styleTextField(passwordField);
        
        // Login button
        JButton loginButton = createStyledButton("Login");
        loginButton.setPreferredSize(new Dimension(300, 45));

        // Create labels with consistent styling
        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        usernameLabel.setFont(LABEL_FONT);
        passwordLabel.setFont(LABEL_FONT);
        usernameLabel.setForeground(TEXT_COLOR);
        passwordLabel.setForeground(TEXT_COLOR);

        // Layout components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        loginFormPanel.add(titleLabel, gbc);

        // Add username label and field
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 5, 0);
        loginFormPanel.add(usernameLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        loginFormPanel.add(usernameField, gbc);

        // Add password label and field
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 5, 0);
        loginFormPanel.add(passwordLabel, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 30, 0);
        loginFormPanel.add(passwordField, gbc);

        // Add login button
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        loginFormPanel.add(loginButton, gbc);

        // Add action listener
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (system.login(username, password)) {
                cardLayout.show(mainPanel, "MAIN_MENU");
            } else {
                JOptionPane.showMessageDialog(this,
                    "Invalid username or password",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add enter key listener for both fields
        Action loginAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.doClick();
            }
        };
        usernameField.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);

        // Center the login form panel
        panel.add(loginFormPanel, BorderLayout.CENTER);

        return panel;
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(TABLE_FONT);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        textField.setBackground(PANEL_COLOR);
        
        // Add focus listener for better visual feedback
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(TABLE_FONT);
        comboBox.setBackground(PANEL_COLOR);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Hospital Management System");
        headerLabel.setFont(HEADER_FONT);
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Menu Grid Panel with increased spacing
        JPanel menuGrid = new JPanel(new GridLayout(0, 3, 20, 20));
        menuGrid.setBackground(BACKGROUND_COLOR);
        menuGrid.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Add menu items
        addMenuItem(menuGrid, "Add New Patient", ADD_PATIENT_ICON, e -> cardLayout.show(mainPanel, "ADD_PATIENT"));
        addMenuItem(menuGrid, "Room Management", ROOM_ICON, e -> cardLayout.show(mainPanel, "ROOMS"));
        addMenuItem(menuGrid, "Department Info", DEPARTMENT_ICON, e -> cardLayout.show(mainPanel, "DEPARTMENTS"));
        addMenuItem(menuGrid, "Employee Info", EMPLOYEE_ICON, e -> cardLayout.show(mainPanel, "EMPLOYEES"));
        addMenuItem(menuGrid, "Patient Info", PATIENT_INFO_ICON, e -> cardLayout.show(mainPanel, "PATIENT_LIST"));
        addMenuItem(menuGrid, "Patient Discharge", DISCHARGE_ICON, e -> cardLayout.show(mainPanel, "DISCHARGE"));
        addMenuItem(menuGrid, "Search Room", SEARCH_ICON, e -> cardLayout.show(mainPanel, "SEARCH_ROOM"));
        addMenuItem(menuGrid, "Update Patient", UPDATE_ICON, e -> cardLayout.show(mainPanel, "UPDATE_PATIENT"));
        addMenuItem(menuGrid, "Ambulance Service", AMBULANCE_ICON, e -> cardLayout.show(mainPanel, "AMBULANCE"));

        // Add logout button at the bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(BACKGROUND_COLOR);
        JButton logoutButton = createStyledButton("Logout");
        logoutButton.setPreferredSize(new Dimension(120, 40));
        logoutButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "LOGIN");
            usernameField.setText("");
            passwordField.setText("");
        });
        bottomPanel.add(logoutButton);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(menuGrid, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void addMenuItem(JPanel container, String title, String icon, ActionListener action) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PANEL_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));

        // Create icon panel to ensure proper centering
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconPanel.setBackground(PANEL_COLOR);
        
        // Icon label with adjusted size and padding
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        iconPanel.add(iconLabel);

        // Title label with adjusted font and spacing
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(LABEL_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        // Add components with proper spacing
        card.add(iconPanel);
        card.add(titleLabel);

        // Add hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(236, 240, 241));
                iconPanel.setBackground(new Color(236, 240, 241));
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(PANEL_COLOR);
                iconPanel.setBackground(PANEL_COLOR);
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                action.actionPerformed(null);
            }
        });

        container.add(card);
    }

    private JPanel createAddPatientPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Add New Patient");
        headerLabel.setFont(HEADER_FONT);
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JButton backButton = createStyledButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MAIN_MENU"));
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Patient ID Type field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel idTypeLabel = new JLabel("Patient ID Type:");
        idTypeLabel.setFont(LABEL_FONT);
        formPanel.add(idTypeLabel, gbc);
        
        String[] idTypes = {"Aadhar Card", "Voter ID"};
        JComboBox<String> idTypeCombo = new JComboBox<>(idTypes);
        idTypeCombo.setFont(LABEL_FONT);
        styleComboBox(idTypeCombo);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(idTypeCombo, gbc);

        // Patient ID Number field
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        JLabel idNumberLabel = new JLabel("Patient ID Number:");
        idNumberLabel.setFont(LABEL_FONT);
        formPanel.add(idNumberLabel, gbc);
        
        JTextField idNumberField = new JTextField(20);
        styleTextField(idNumberField);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(idNumberField, gbc);

        // Name field
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(LABEL_FONT);
        formPanel.add(nameLabel, gbc);
        
        JTextField nameField = new JTextField(20);
        styleTextField(nameField);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(nameField, gbc);

        // Gender field
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(LABEL_FONT);
        formPanel.add(genderLabel, gbc);
        
        String[] genders = {"Male", "Female"};
        JComboBox<String> genderCombo = new JComboBox<>(genders);
        genderCombo.setFont(LABEL_FONT);
        styleComboBox(genderCombo);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(genderCombo, gbc);

        // Disease field
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        JLabel diseaseLabel = new JLabel("Disease:");
        diseaseLabel.setFont(LABEL_FONT);
        formPanel.add(diseaseLabel, gbc);
        
        JTextField diseaseField = new JTextField(20);
        styleTextField(diseaseField);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(diseaseField, gbc);

        // Admission Time field
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        JLabel admissionTimeLabel = new JLabel("Admission Time:");
        admissionTimeLabel.setFont(LABEL_FONT);
        formPanel.add(admissionTimeLabel, gbc);
        
        JTextField admissionTimeField = new JTextField(20);
        admissionTimeField.setToolTipText("Format: yyyy-MM-dd HH:mm");
        styleTextField(admissionTimeField);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(admissionTimeField, gbc);

        // Deposit Amount field
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        JLabel depositLabel = new JLabel("Deposit Amount:");
        depositLabel.setFont(LABEL_FONT);
        formPanel.add(depositLabel, gbc);
        
        JTextField depositField = new JTextField(20);
        styleTextField(depositField);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(depositField, gbc);

        // Submit button
        JButton submitButton = new JButton("Add Patient");
        submitButton.setFont(BUTTON_FONT);
        submitButton.setBackground(PRIMARY_COLOR);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setPreferredSize(new Dimension(180, 40));
        submitButton.setBorderPainted(false);
        submitButton.setOpaque(true);
        
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(submitButton, gbc);

        // Add action listener
        submitButton.addActionListener(e -> {
            String idType = (String) idTypeCombo.getSelectedItem();
            String idNumber = idNumberField.getText();
            String name = nameField.getText();
            String gender = (String) genderCombo.getSelectedItem();
            String disease = diseaseField.getText();
            String admissionTimeStr = admissionTimeField.getText();
            String depositAmountStr = depositField.getText();
            
            if (idNumber.isEmpty() || name.isEmpty() || 
                disease.isEmpty() || depositAmountStr.isEmpty() || admissionTimeStr.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please fill in all fields");
                return;
            }

            try {
                double depositAmount = Double.parseDouble(depositAmountStr);
                LocalDateTime admissionTime = LocalDateTime.parse(admissionTimeStr, 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                
                // Generate a unique patient ID
                String patientId = String.format("P%02d", nextPatientId++);
                
                Patient patient = new Patient(patientId, idType, idNumber, name, gender, 
                                           disease, admissionTime, depositAmount);
                system.addPatient(patient);
                JOptionPane.showMessageDialog(panel, "Patient added successfully!");
                
                // Clear fields
                idTypeCombo.setSelectedIndex(0);
                idNumberField.setText("");
                nameField.setText("");
                genderCombo.setSelectedIndex(0);
                diseaseField.setText("");
                admissionTimeField.setText("");
                depositField.setText("");
                
                // Refresh the patient list panel
                refreshPatientListPanel();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Please enter a valid deposit amount");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(panel, "Please enter a valid date and time (yyyy-MM-dd HH:mm)");
            }
        });

        // Add image panel before the form
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(formPanel, BorderLayout.CENTER);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    // Add a new method to refresh the patient list panel
    private void refreshPatientListPanel() {
        // Find the patient list panel in the main panel
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.getName() != null && panel.getName().equals("PATIENT_LIST")) {
                    // Remove the old panel
                    mainPanel.remove(panel);
                    // Create and add a new panel
                    mainPanel.add(createPatientListPanel(), "PATIENT_LIST");
                    // Revalidate and repaint
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    break;
                }
            }
        }
    }

    private JPanel createRoomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setName("ROOMS"); // Add a name to identify this panel
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Room Management");
        headerLabel.setFont(HEADER_FONT);
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(PRIMARY_COLOR);

        JButton refreshButton = createStyledButton("Refresh");
        refreshButton.addActionListener(e -> refreshRoomPanel());
        buttonPanel.add(refreshButton);

        JButton backButton = createStyledButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MAIN_MENU"));
        buttonPanel.add(backButton);

        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        String[] columnNames = {"Room Number", "Availability", "Price", "Bed Type"};
        List<Room> rooms = system.getAllRooms();
        Object[][] data = new Object[rooms.size()][4];

        // Create table data
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            data[i] = new Object[]{
                room.getRoomNumber(),
                room.isAvailable() ? "Available" : "Occupied",
                String.format("₹%.2f", room.getPrice()),
                room.getBedType()
            };
        }

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        
        // Enhanced table header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 99, 177)); // Darker shade of Microsoft Blue
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR));
        header.setReorderingAllowed(false);
        
        // Add row coloring for occupied rooms
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    if (column == 1 && value.equals("Occupied")) {
                        c.setBackground(new Color(255, 200, 200)); // Light red for occupied rooms
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);

        // Add a legend panel
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        legendPanel.setBackground(Color.WHITE);
        
        JPanel availableLegend = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        availableLegend.setBackground(Color.WHITE);
        JLabel availableLabel = new JLabel("Available");
        availableLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        availableLegend.add(availableLabel);
        
        JPanel occupiedLegend = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        occupiedLegend.setBackground(new Color(255, 200, 200));
        JLabel occupiedLabel = new JLabel("Occupied");
        occupiedLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        occupiedLegend.add(occupiedLabel);
        
        legendPanel.add(availableLegend);
        legendPanel.add(occupiedLegend);

        // Add room count summary
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        summaryPanel.setBackground(Color.WHITE);
        
        Map<String, Long> roomCounts = rooms.stream()
            .collect(java.util.stream.Collectors.groupingBy(Room::getBedType, java.util.stream.Collectors.counting()));
        
        for (Map.Entry<String, Long> entry : roomCounts.entrySet()) {
            JLabel countLabel = new JLabel(entry.getKey() + ": " + entry.getValue());
            countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            summaryPanel.add(countLabel);
        }

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.WHITE);
        southPanel.add(legendPanel, BorderLayout.WEST);
        southPanel.add(summaryPanel, BorderLayout.EAST);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(southPanel, BorderLayout.SOUTH);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDepartmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Department Information");
        headerLabel.setFont(HEADER_FONT);
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JButton backButton = createStyledButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MAIN_MENU"));
        headerPanel.add(backButton, BorderLayout.EAST);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        String[] columnNames = {"Department Name", "Phone Number"};
        List<Department> departments = system.getAllDepartments();
        Object[][] data = new Object[departments.size()][2];

        for (int i = 0; i < departments.size(); i++) {
            Department dept = departments.get(i);
            data[i] = new Object[]{dept.getName(), dept.getPhoneNumber()};
        }

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        
        // Enhanced table header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 99, 177)); // Darker shade of Microsoft Blue
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR));
        header.setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Employee Information");
        headerLabel.setFont(HEADER_FONT);
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JButton backButton = createStyledButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MAIN_MENU"));
        headerPanel.add(backButton, BorderLayout.EAST);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        String[] columnNames = {"ID", "Name", "Age", "Gender", "Role", "Salary", "Phone", "Email"};
        List<Employee> employees = system.getAllEmployees();
        Object[][] data = new Object[employees.size()][8];

        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);
            data[i] = new Object[]{
                emp.getEmployeeId(),
                emp.getName(),
                emp.getAge(),
                emp.getGender(),
                emp.getRole(),
                emp.getSalary(),
                emp.getPhoneNumber(),
                emp.getEmail()
            };
        }

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        
        // Enhanced table header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(Color.BLACK); // Darker shade of Microsoft Blue
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR));
        header.setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPatientListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setName("PATIENT_LIST"); // Add a name to identify this panel
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Patient Information");
        headerLabel.setFont(HEADER_FONT);
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JButton backButton = createStyledButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MAIN_MENU"));
        headerPanel.add(backButton, BorderLayout.EAST);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        String[] columnNames = {
            "Patient ID", "ID Type", "ID Number", "Name", "Gender",
            "Disease", "Room", "Admission Time", "Deposit"
        };
        List<Patient> patients = system.getAllPatients();
        // Sort patients by ID
        patients.sort((p1, p2) -> p1.getPatientId().compareTo(p2.getPatientId()));
        Object[][] data = new Object[patients.size()][9];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            data[i] = new Object[]{
                patient.getPatientId(),
                patient.getIdType(),
                patient.getIdNumber(),
                patient.getName(),
                patient.getGender(),
                patient.getDisease(),
                patient.getRoomNumber() != null ? patient.getRoomNumber() : "Not Assigned",
                patient.getAdmissionTime().format(formatter),
                String.format("%.2f", patient.getDepositAmount())
            };
        }

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        
        // Enhanced table header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 99, 177)); // Darker shade of Microsoft Blue
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR));
        header.setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDischargePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setName("DISCHARGE"); // Add name to identify this panel
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Patient Discharge");
        headerLabel.setFont(HEADER_FONT);
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JButton backButton = createStyledButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MAIN_MENU"));
        headerPanel.add(backButton, BorderLayout.EAST);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Patient Selection Panel
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setBackground(Color.WHITE);
        selectionPanel.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            "Select Patient",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            LABEL_FONT,
            PRIMARY_COLOR
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create patient selection combo box with all patients
        List<Patient> allPatients = system.getAllPatients();
        String[] patientIds = allPatients.stream()
            .map(p -> p.getPatientId() + " - " + p.getName() + 
                     " (Room: " + (p.getRoomNumber() != null ? p.getRoomNumber() : "Not Assigned") + ")" +
                     (p.getDischargeDate() != null ? " [Discharged]" : ""))
            .toArray(String[]::new);
        
        JComboBox<String> patientCombo = new JComboBox<>(patientIds);
        patientCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        styleComboBox(patientCombo);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectionPanel.add(patientCombo, gbc);

        // Patient Details Panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            "Patient Details",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            LABEL_FONT,
            PRIMARY_COLOR
        ));

        // Create info labels with consistent styling
        JLabel[] labels = {
            new JLabel("Name:"),
            new JLabel("ID Type:"),
            new JLabel("ID Number:"),
            new JLabel("Room:"),
            new JLabel("Admission Date:"),
            new JLabel("Discharge Date:"),
            new JLabel("Deposit Amount:"),
            new JLabel("Amount Paid:"),
            new JLabel("Amount to Pay:")
        };

        JLabel[] values = {
            new JLabel(""),
            new JLabel(""),
            new JLabel(""),
            new JLabel(""),
            new JLabel(""),
            new JLabel(""),
            new JLabel(""),
            new JLabel(""),
            new JLabel("")
        };

        // Style all labels
        for (JLabel label : labels) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }
        for (JLabel value : values) {
            value.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

        // Add labels and values to details panel
        gbc.insets = new Insets(5, 10, 5, 10);
        for (int i = 0; i < labels.length; i++) {
        gbc.gridx = 0;
            gbc.gridy = i;
        gbc.gridwidth = 1;
            detailsPanel.add(labels[i], gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
            detailsPanel.add(values[i], gbc);
        }

        // Payment Panel
        JPanel paymentPanel = new JPanel(new GridBagLayout());
        paymentPanel.setBackground(Color.WHITE);
        paymentPanel.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            "Payment Details",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            LABEL_FONT,
            PRIMARY_COLOR
        ));

        JLabel amountPaidLabel = new JLabel("Amount Paid:");
        amountPaidLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JTextField amountPaidField = new JTextField(15);
        styleTextField(amountPaidField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        paymentPanel.add(amountPaidLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        paymentPanel.add(amountPaidField, gbc);

        // Discharge Button
        JButton dischargeButton = createStyledButton("Discharge Patient");
        dischargeButton.setPreferredSize(new Dimension(200, 40));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 10, 10, 10);
        paymentPanel.add(dischargeButton, gbc);

        // Layout the panels
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        centerPanel.add(selectionPanel, gbc);

        gbc.gridy = 1;
        centerPanel.add(detailsPanel, gbc);

        gbc.gridy = 2;
        centerPanel.add(paymentPanel, gbc);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Add action listeners
        patientCombo.addActionListener(e -> {
            String selected = (String) patientCombo.getSelectedItem();
            if (selected != null) {
                String patientId = selected.split(" - ")[0];
                Patient patient = system.findPatientById(patientId);
                if (patient != null) {
                    values[0].setText(patient.getName());
                    values[1].setText(patient.getIdType());
                    values[2].setText(patient.getIdNumber());
                    values[3].setText(patient.getRoomNumber() != null ? patient.getRoomNumber() : "Not Assigned");
                    values[4].setText(patient.getAdmissionTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                    values[5].setText(patient.getDischargeDate() != null ? 
                        patient.getDischargeDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "Not Discharged");
                    values[6].setText(String.format("₹%.2f", patient.getDepositAmount()));
                    values[7].setText(String.format("₹%.2f", patient.getAmountPaid()));
                    values[8].setText(String.format("₹%.2f", patient.getPendingAmount()));

                    // Enable/disable discharge button based on discharge status
                    dischargeButton.setEnabled(patient.getDischargeDate() == null);
                    amountPaidField.setEnabled(patient.getDischargeDate() == null);
                }
            }
        });

        dischargeButton.addActionListener(e -> {
            String selected = (String) patientCombo.getSelectedItem();
            if (selected != null) {
                String patientId = selected.split(" - ")[0];
                Patient patient = system.findPatientById(patientId);
                if (patient != null) {
                    try {
                        double amountPaid = Double.parseDouble(amountPaidField.getText());
                        if (amountPaid < 0) {
                            JOptionPane.showMessageDialog(panel,
                                "Amount paid cannot be negative",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        patient.setAmountPaid(amountPaid);
                        patient.setPendingAmount(patient.getDepositAmount() - amountPaid);
                        patient.setDischargeDate(LocalDateTime.now());
                        system.updatePatient(patient);
                        
                        JOptionPane.showMessageDialog(panel,
                            "Patient discharged successfully!\n" +
                            "Pending Amount: ₹" + String.format("%.2f", patient.getPendingAmount()),
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                            
                        amountPaidField.setText("");
                        refreshPatientListPanel();
                        refreshDischargePanel();
                        refreshUpdatePatientPanel();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel,
                            "Please enter a valid amount",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    // Add a new method to refresh the discharge panel
    private void refreshDischargePanel() {
        // Find the discharge panel in the main panel
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.getName() != null && panel.getName().equals("DISCHARGE")) {
                    // Remove the old panel
                    mainPanel.remove(panel);
                    // Create and add a new panel
                    mainPanel.add(createDischargePanel(), "DISCHARGE");
                    // Revalidate and repaint
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    break;
                }
            }
        }
    }

    private JPanel createSearchRoomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setName("SEARCH_ROOM");
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Search and Book Rooms");
        headerLabel.setFont(HEADER_FONT);
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JButton backButton = createStyledButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MAIN_MENU"));
        headerPanel.add(backButton, BorderLayout.EAST);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Search Criteria Panel
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Patient Search
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel patientLabel = new JLabel("Select Patient:");
        patientLabel.setFont(LABEL_FONT);
        searchPanel.add(patientLabel, gbc);

        // Get all patients and create a formatted list for the combo box
        List<Patient> allPatients = system.getAllPatients();
        String[] patientOptions = new String[allPatients.size() + 1];
        patientOptions[0] = "Select a patient...";
        
        for (int i = 0; i < allPatients.size(); i++) {
            Patient p = allPatients.get(i);
            patientOptions[i + 1] = String.format("%s - %s (%s) %s", 
                p.getPatientId(),
                p.getName(),
                p.getDisease(),
                p.getRoomNumber() != null ? "Room: " + p.getRoomNumber() : "No Room"
            );
        }
        
        JComboBox<String> patientCombo = new JComboBox<>(patientOptions);
        patientCombo.setFont(LABEL_FONT);
        styleComboBox(patientCombo);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        searchPanel.add(patientCombo, gbc);

        // Bed Type Selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel bedTypeLabel = new JLabel("Bed Type:");
        bedTypeLabel.setFont(LABEL_FONT);
        searchPanel.add(bedTypeLabel, gbc);

        String[] bedTypes = {"All", "Single", "Double", "General Ward", "ICU", "Maternity", "Pediatric", "Suite"};
        JComboBox<String> bedTypeCombo = new JComboBox<>(bedTypes);
        bedTypeCombo.setFont(LABEL_FONT);
        styleComboBox(bedTypeCombo);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        searchPanel.add(bedTypeCombo, gbc);

        // Price Range Selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JLabel priceRangeLabel = new JLabel("Price Range:");
        priceRangeLabel.setFont(LABEL_FONT);
        searchPanel.add(priceRangeLabel, gbc);

        String[] priceRanges = {"All", "Under 1500", "1500-2500", "Above 2500"};
        JComboBox<String> priceRangeCombo = new JComboBox<>(priceRanges);
        priceRangeCombo.setFont(LABEL_FONT);
        styleComboBox(priceRangeCombo);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        searchPanel.add(priceRangeCombo, gbc);

        // Search Button
        JButton searchButton = createStyledButton("Search Rooms");
        searchButton.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        searchPanel.add(searchButton, gbc);

        // Results Panel
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            "Available Rooms",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            LABEL_FONT,
            PRIMARY_COLOR
        ));

        // Create table model for results
        String[] columnNames = {"Room Number", "Bed Type", "Price", "Status"};
        Object[][] data = new Object[0][4];
        JTable resultsTable = new JTable(data, columnNames);
        resultsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultsTable.setRowHeight(30);
        
        // Enhanced table header styling
        JTableHeader header = resultsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 99, 177));
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR));
        header.setReorderingAllowed(false);
        
        // Add row coloring for occupied rooms
        resultsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    if (column == 3 && value.equals("Occupied")) {
                        c.setBackground(new Color(255, 200, 200));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(null);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);

        // Add Book Room button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        JButton bookButton = createStyledButton("Book Selected Room");
        bookButton.setPreferredSize(new Dimension(200, 40));
        buttonPanel.add(bookButton);
        resultsPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to search button
        searchButton.addActionListener(e -> {
            String selectedPatient = (String) patientCombo.getSelectedItem();
            String selectedBedType = (String) bedTypeCombo.getSelectedItem();
            String selectedPriceRange = (String) priceRangeCombo.getSelectedItem();
            
            if (selectedPatient == null || selectedPatient.equals("Select a patient...")) {
                JOptionPane.showMessageDialog(this,
                    "Please select a patient",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            List<Room> filteredRooms = system.getAllRooms();
            
            // Filter by bed type if not "All"
            if (!"All".equals(selectedBedType)) {
                filteredRooms = filteredRooms.stream()
                    .filter(r -> r.getBedType().equals(selectedBedType))
                    .collect(java.util.stream.Collectors.toList());
            }
            
            // Filter by price range
            if (!"All".equals(selectedPriceRange)) {
                filteredRooms = filteredRooms.stream()
                    .filter(r -> {
                        double price = r.getPrice();
                        switch (selectedPriceRange) {
                            case "Under 1500": return price < 1500;
                            case "1500-2500": return price >= 1500 && price <= 2500;
                            case "Above 2500": return price > 2500;
                            default: return true;
                        }
                    })
                    .collect(java.util.stream.Collectors.toList());
            }
            
            // Update table with filtered results
            Object[][] tableData = new Object[filteredRooms.size()][4];
            for (int i = 0; i < filteredRooms.size(); i++) {
                Room room = filteredRooms.get(i);
                tableData[i] = new Object[]{
                    room.getRoomNumber(),
                    room.getBedType(),
                    String.format("₹%.2f", room.getPrice()),
                    room.isAvailable() ? "Available" : "Occupied"
                };
            }
            
            resultsTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));
            
            // Show message with count of available rooms
            long availableCount = filteredRooms.stream().filter(Room::isAvailable).count();
            JOptionPane.showMessageDialog(this,
                "Found " + availableCount + " available rooms out of " + filteredRooms.size() + " total rooms.",
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE);
        });

        // Add action listener to book button
        bookButton.addActionListener(e -> {
            int selectedRow = resultsTable.getSelectedRow();
            String selectedPatient = (String) patientCombo.getSelectedItem();
            
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this,
                    "Please select a room to book",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (selectedPatient == null || selectedPatient.equals("Select a patient...")) {
                JOptionPane.showMessageDialog(this,
                    "Please select a patient",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Extract patient ID from the selected option
            String patientId = selectedPatient.split(" - ")[0];
            
            String roomNumber = (String) resultsTable.getValueAt(selectedRow, 0);
            String status = (String) resultsTable.getValueAt(selectedRow, 3);
            
            if ("Occupied".equals(status)) {
                JOptionPane.showMessageDialog(this,
                    "This room is already occupied!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Book the room
            Room room = system.findRoomByNumber(roomNumber);
            if (room != null) {
                room.setAvailable(false);
                if (system.updateRoom(room)) {
                    // Update the patient's room number
                    Patient patient = system.findPatientById(patientId);
                    if (patient != null) {
                        patient.setRoomNumber(roomNumber);
                        system.updatePatient(patient);
                    }
                    
                    // Update the status in the search results table
                    resultsTable.setValueAt("Occupied", selectedRow, 3);
                    
                    // Refresh both the room panel and search panel
                    refreshRoomPanel();
                    refreshSearchPanel();
                    
                    JOptionPane.showMessageDialog(this,
                        "Room " + roomNumber + " has been booked successfully for patient " + patientId + "!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Failed to book the room. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(resultsPanel, BorderLayout.CENTER);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    // Add a new method to refresh the room panel
    private void refreshRoomPanel() {
        // Find the room panel in the main panel
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.getName() != null && panel.getName().equals("ROOMS")) {
                    // Remove the old panel
                    mainPanel.remove(panel);
                    // Create and add a new panel
                    mainPanel.add(createRoomPanel(), "ROOMS");
                    // Revalidate and repaint
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    break;
                }
            }
        }
    }

    // Add a new method to refresh the search panel
    private void refreshSearchPanel() {
        // Find the search panel in the main panel
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.getName() != null && panel.getName().equals("SEARCH_ROOM")) {
                    // Remove the old panel
                    mainPanel.remove(panel);
                    // Create and add a new panel
                    mainPanel.add(createSearchRoomPanel(), "SEARCH_ROOM");
                    // Revalidate and repaint
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    break;
                }
            }
        }
    }

    private void refreshUpdatePatientPanel() {
        // Find the update patient panel in the main panel
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.getName() != null && panel.getName().equals("UPDATE_PATIENT")) {
                    // Remove the old panel
                    mainPanel.remove(panel);
                    // Create and add a new panel
                    mainPanel.add(createUpdatePatientPanel(), "UPDATE_PATIENT");
                    // Revalidate and repaint
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    break;
                }
            }
        }
    }

    private JPanel createUpdatePatientPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setName("UPDATE_PATIENT"); // Add name to identify this panel
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Update Patient Details");
        headerLabel.setFont(HEADER_FONT);
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JButton backButton = createStyledButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MAIN_MENU"));
        headerPanel.add(backButton, BorderLayout.EAST);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Patient Selection Panel
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setBackground(Color.WHITE);
        selectionPanel.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            "Select Patient",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            LABEL_FONT,
            PRIMARY_COLOR
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create patient selection combo box
        List<Patient> allPatients = system.getAllPatients();
        String[] patientIds = allPatients.stream()
            .map(p -> p.getPatientId() + " - " + p.getName())
            .toArray(String[]::new);
        
        JComboBox<String> patientCombo = new JComboBox<>(patientIds);
        patientCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        styleComboBox(patientCombo);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectionPanel.add(patientCombo, gbc);

        // Update Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            "Update Details",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            LABEL_FONT,
            PRIMARY_COLOR
        ));

        // Create form fields
        JLabel pendingAmountLabel = new JLabel("Pending Amount:");
        JLabel amountPaidLabel = new JLabel("Amount Paid:");
        
        JTextField pendingAmountField = new JTextField(15);
        JTextField amountPaidField = new JTextField(15);
        
        // Style components
        pendingAmountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        amountPaidLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        styleTextField(pendingAmountField);
        styleTextField(amountPaidField);
        
        // Layout form components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        formPanel.add(pendingAmountLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(pendingAmountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(amountPaidLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(amountPaidField, gbc);

        // Update Button
        JButton updateButton = createStyledButton("Update Patient");
        updateButton.setPreferredSize(new Dimension(200, 40));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 10, 10, 10);
        formPanel.add(updateButton, gbc);

        // Layout the panels
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        centerPanel.add(selectionPanel, gbc);

        gbc.gridy = 1;
        centerPanel.add(formPanel, gbc);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Add action listeners
        patientCombo.addActionListener(e -> {
            String selected = (String) patientCombo.getSelectedItem();
            if (selected != null) {
                String patientId = selected.split(" - ")[0];
                Patient patient = system.findPatientById(patientId);
                if (patient != null) {
                    pendingAmountField.setText(String.format("%.2f", patient.getPendingAmount()));
                    amountPaidField.setText(String.format("%.2f", patient.getAmountPaid()));
                }
            }
        });

        updateButton.addActionListener(e -> {
            try {
                String selected = (String) patientCombo.getSelectedItem();
                if (selected != null) {
                    String patientId = selected.split(" - ")[0];
                    Patient patient = system.findPatientById(patientId);
                    
                    if (patient != null) {
                        double pendingAmount = Double.parseDouble(pendingAmountField.getText());
                        double amountPaid = Double.parseDouble(amountPaidField.getText());
                        
                        if (pendingAmount < 0 || amountPaid < 0) {
                            JOptionPane.showMessageDialog(panel,
                                "Amounts cannot be negative",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        patient.setPendingAmount(pendingAmount);
                        patient.setAmountPaid(amountPaid);
                        system.updatePatient(patient);
                        
                        JOptionPane.showMessageDialog(panel,
                            "Patient details updated successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                            
                        pendingAmountField.setText("");
                        amountPaidField.setText("");
                        refreshPatientListPanel();
                        refreshUpdatePatientPanel();
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel,
                    "Please enter valid amounts",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createAmbulancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Ambulance Service");
        headerLabel.setFont(HEADER_FONT);
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JButton backButton = createStyledButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MAIN_MENU"));
        headerPanel.add(backButton, BorderLayout.EAST);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        String[] columnNames = {"Ambulance ID", "Plate Number", "Driver Name", "Phone Number", "Status"};
        List<Ambulance> ambulances = system.getAllAmbulances();
        Object[][] data = new Object[ambulances.size()][5];

        for (int i = 0; i < ambulances.size(); i++) {
            Ambulance ambulance = ambulances.get(i);
            data[i] = new Object[]{
                ambulance.getAmbulanceId(),
                ambulance.getPlateNumber(),
                ambulance.getDriverName(),
                ambulance.getDriverPhone(),
                ambulance.isAvailable() ? "Available" : "Ongoing"
            };
        }

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        
        // Enhanced table header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 99, 177)); // Darker shade of Microsoft Blue
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR));
        header.setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton bookButton = createStyledButton("Book Ambulance");
        buttonPanel.add(bookButton);

        bookButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String ambulanceId = (String) table.getValueAt(selectedRow, 0);
                if (system.bookAmbulance(ambulanceId)) {
                    // Update the status in the table to "Ongoing"
                    table.setValueAt("Ongoing", selectedRow, 4);
                    
                    JOptionPane.showMessageDialog(this,
                        "Ambulance booked successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Ambulance is not available!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select an ambulance to book",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    // Update table header styling in all table panels
    private void styleTableHeader(JTableHeader header) {
        header.setFont(TABLE_HEADER_FONT);
        header.setBackground(HEADER_COLOR);
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        header.setReorderingAllowed(false);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            HospitalManagementGUI gui = new HospitalManagementGUI();
            gui.setVisible(true);
        });
    }
} 