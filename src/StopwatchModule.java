import java.awt.*;
import javax.swing.*;

public class StopwatchModule extends JPanel {

    private JLabel timeLabel;
    private JButton startButton, stopButton, resetButton;
    private Timer timer;

    private long startTime = 0;
    private long elapsedTime = 0;
    private boolean isRunning = false;

    public StopwatchModule() {
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setMaximumSize(new Dimension(400, 300));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        // ---- LABEL UTAMA ----
        timeLabel = new JLabel("00:00:00");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 64));
        timeLabel.setForeground(new Color(0, 51, 102));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ---- PANEL BUTTON ----
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonsPanel.setOpaque(false);

        startButton = createButton("Mulai", new Color(76, 175, 80));
        stopButton  = createButton("Berhenti", new Color(244, 67, 54));
        resetButton = createButton("Reset", new Color(33, 150, 243));

        stopButton.setEnabled(false);

        startButton.addActionListener(e -> startStopwatch());
        stopButton.addActionListener(e -> stopStopwatch());
        resetButton.addActionListener(e -> resetStopwatch());

        buttonsPanel.add(startButton);
        buttonsPanel.add(stopButton);
        buttonsPanel.add(resetButton);

        add(timeLabel);
        add(Box.createVerticalStrut(30));
        add(buttonsPanel);
        add(Box.createVerticalGlue());

        // Timer update 60 FPS (smooth)
        timer = new Timer(16, e -> updateTime());
    }

    // ---- UTILITY BUTTON ----
    private JButton createButton(String text, Color color) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(100, 40));
        return b;
    }

    // ---- START ----
    private void startStopwatch() {
        if (!isRunning) {
            isRunning = true;
            startTime = System.currentTimeMillis() - elapsedTime;
            timer.start();

            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
    }

    // ---- STOP ----
    private void stopStopwatch() {
        if (isRunning) {
            isRunning = false;
            timer.stop();

            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }

    // ---- RESET ----
    private void resetStopwatch() {
        stopStopwatch();
        elapsedTime = 0;
        timeLabel.setText("00:00:00");
    }

    // ---- UPDATE WAKTU ----
    private void updateTime() {
        if (!isRunning) return;

        elapsedTime = System.currentTimeMillis() - startTime;

        long hours = elapsedTime / 3600000;
        long minutes = (elapsedTime % 3600000) / 60000;
        long seconds = (elapsedTime % 60000) / 1000;

        String formatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeLabel.setText(formatted);
    }

    // ---- SAFE CLEANUP ----
    public void cleanup() {
        timer.stop();
    }
}
