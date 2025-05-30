import java.awt.*;
import java.text.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class Reminder {
    private JFrame frame;
    private DefaultListModel<String> reminderListModel;
    private JList<String> reminderList;
    private JTextField reminderTextField;
    private JTextField timeTextField;  // expects HH:mm format
    private java.util.List<ReminderHelper> reminders = new ArrayList<>();

    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(Reminder::new);
    // }

    public Reminder() {
        frame = new JFrame("Daily Reminder");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top panel to add reminders
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPanel.add(new JLabel("Reminder Text:"));
        reminderTextField = new JTextField();
        topPanel.add(reminderTextField);

        topPanel.add(new JLabel("Time (HH:mm):"));
        timeTextField = new JTextField();
        topPanel.add(timeTextField);

        frame.add(topPanel, BorderLayout.NORTH);

        // Center list of reminders
        reminderListModel = new DefaultListModel<>();
        reminderList = new JList<>(reminderListModel);
        JScrollPane scrollPane = new JScrollPane(reminderList);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel();
        JButton addButton = new JButton("Add Reminder");
        JButton removeButton = new JButton("Remove Selected");

        bottomPanel.add(addButton);
        bottomPanel.add(removeButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Add reminder button logic
        addButton.addActionListener(e -> addReminder());

        // Remove reminder button logic
        removeButton.addActionListener(e -> removeSelectedReminder());

        // Timer to check reminders every 30 seconds
        javax.swing.Timer timer = new javax.swing.Timer(30000, e -> checkReminders());
        timer.start();

        frame.setVisible(true);
    }

    private void addReminder() {
        String text = reminderTextField.getText().trim();
        String timeStr = timeTextField.getText().trim();

        if (text.isEmpty() || timeStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both reminder text and time.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Date reminderTime = timeFormat.parse(timeStr);
            reminders.add(new ReminderHelper(text, timeStr));
            reminderListModel.addElement(text + " at " + timeStr);

            reminderTextField.setText("");
            timeTextField.setText("");

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Time format should be HH:mm (e.g., 14:30).", "Format Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeSelectedReminder() {
        int selectedIndex = reminderList.getSelectedIndex();
        if (selectedIndex != -1) {
            reminders.remove(selectedIndex);
            reminderListModel.remove(selectedIndex);
        }
    }

    private void checkReminders() {
        String now = timeFormat.format(new Date());

        // Collect reminders to alert (avoid concurrent modification)
        List<ReminderHelper> toAlert = new ArrayList<>();
        for (ReminderHelper r : reminders) {
            if (r.time.equals(now) && !r.alerted) {
                toAlert.add(r);
            }
        }

        // Show alerts and mark as alerted
        if (!toAlert.isEmpty()) {
        StringBuilder message = new StringBuilder("You have reminders:\n\n");
        for (ReminderHelper r : toAlert) {
            message.append("- ").append(r.text).append(" at ").append(r.time).append("\n");
            r.alerted = true;
        }

    SwingUtilities.invokeLater(() -> {
        JOptionPane.showMessageDialog(frame, message.toString());
    });
}
    }

    private static class ReminderHelper {
        String text;
        String time;
        boolean alerted = false;

        ReminderHelper(String text, String time) {
            this.text = text;
            this.time = time;
        }
    }
}