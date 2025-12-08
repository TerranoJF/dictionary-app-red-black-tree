import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockModule extends JPanel {
    private JLabel timeLabel;
    private JLabel dateLabel;
    private Timer timer;

    public ClockModule() {
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setMaximumSize(new Dimension(400, 300));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        timeLabel = new JLabel("00:00:00");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 72));
        timeLabel.setForeground(new Color(0, 51, 102));
        timeLabel.setHorizontalAlignment(JLabel.CENTER);

        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        dateLabel.setForeground(new Color(80, 80, 80));
        dateLabel.setHorizontalAlignment(JLabel.CENTER);

        add(Box.createVerticalStrut(10));
        add(timeLabel);
        add(Box.createVerticalStrut(20));
        add(dateLabel);
        add(Box.createVerticalGlue());

        startClock();
    }

    private void startClock() {
        timer = new Timer(1000, e -> updateTime());
        timer.start();
        updateTime();
    }

    private void updateTime() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");

        timeLabel.setText(now.format(timeFormatter));
        dateLabel.setText(LocalDate.now().format(dateFormatter));
    }

    public void stopClock() {
        if (timer != null) {
            timer.stop();
        }
    }
}
