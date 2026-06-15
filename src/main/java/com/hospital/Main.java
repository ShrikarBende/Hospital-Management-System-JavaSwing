package com.hospital;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.hospital.gui.HospitalManagementGUI;

public class Main {
    public static void main(String[] args) {
        try {
            // Set system look and feel
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