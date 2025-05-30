import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello, World!");
    /**
    * @ miyachann
    */
        // JFrame frame = new JFrame("Current Date and Time");
        // frame.setSize(1500, 750);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setLayout(new BorderLayout());

        // // Create the label
        // JLabel timeLabel = new JLabel();
        // timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // timeLabel.setFont(timeLabel.getFont().deriveFont(80f));
        // frame.add(timeLabel, BorderLayout.CENTER);

        // // Timer to update the label every second
        // Timer timer = new Timer(1000, e -> {
        //     String dateTime = new SimpleDateFormat("yyyy/ MM/ dd/ EEE\nHH:mm:ss").format(new Date());
        //     timeLabel.setText("<html>" + dateTime.replaceAll("\n", "<br>") + "</html>");
        // });
        // timer.start();

        // // Show the frame
        // frame.setVisible(true);
        SwingUtilities.invokeLater(Reminder::new);
    }
        
}
