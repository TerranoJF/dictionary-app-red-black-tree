import java.awt.*;
import javax.swing.*;

public class ResultPanel extends JPanel {
    private DictionaryApp mainApp;
    private JLabel wordLabel;
    private JLabel descriptionLabel;
    private JPanel modulePanel;
    private DictionaryData.Entry currentEntry;

    private ClockModule clockModule;
    private StopwatchModule stopwatchModule;

    public ResultPanel(DictionaryApp mainApp) {
        this.mainApp = mainApp;

        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        // =================== CONTENT BASE ===================
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // TITLE
        wordLabel = new JLabel("Kata");
        wordLabel.setFont(new Font("Arial", Font.BOLD, 30));
        wordLabel.setForeground(new Color(0, 40, 90));
        wordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // DESCRIPTION WRAPPED
        descriptionLabel = new JLabel();
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(Color.DARK_GRAY);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel descWrap = new JPanel();
        descWrap.setOpaque(false);
        descWrap.setLayout(new BorderLayout());
        descWrap.add(descriptionLabel, BorderLayout.CENTER);

        // MODULE HOLDER
        modulePanel = new JPanel();
        modulePanel.setOpaque(false);
        modulePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        // ADD COMPONENTS
        content.add(Box.createVerticalStrut(5));
        content.add(wordLabel);
        content.add(Box.createVerticalStrut(10));
        content.add(descWrap);
        content.add(Box.createVerticalStrut(15));
        content.add(modulePanel);
        content.add(Box.createVerticalGlue());

        // ================= BACK BUTTON =================
        JButton backButton = new JButton("Kembali");
        backButton.setFont(new Font("Arial", Font.BOLD, 13));
        backButton.setBackground(new Color(200, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(120, 35));
        backButton.setFocusPainted(false);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setOpaque(false);
        bottom.add(backButton);

        backButton.addActionListener(e -> {
            cleanup();
            mainApp.showSearchPanel();
        });

        // ADD TO MAIN LAYOUT
        add(content, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    // ====================== SET WORD DATA ======================
    public void setWordData(DictionaryData.Entry entry) {
        this.currentEntry = entry;

        wordLabel.setText(entry.getWord());
        descriptionLabel.setText("<html><body style='width:630px;'>"
                + entry.getDescription() +
                "</body></html>");

        displayModule(entry.getToolName());
    }

    // ====================== MODULE DISPLAY ======================
    private void displayModule(String toolName) {
        modulePanel.removeAll();

        if (toolName != null) {
            switch (toolName.toLowerCase()) {

                case "kalkulator":
                    modulePanel.add(new CalculatorModule());
                    break;

                case "jam":
                    if (clockModule != null) clockModule.stopClock();
                    clockModule = new ClockModule();
                    modulePanel.add(clockModule);
                    break;

                case "stopwatch":
                    if (stopwatchModule != null) stopwatchModule.cleanup();
                    stopwatchModule = new StopwatchModule();
                    modulePanel.add(stopwatchModule);
                    break;

                case "kurs":
                    modulePanel.add(new CurrencyModule());
                    break;

            }
        }

        modulePanel.revalidate();
        modulePanel.repaint();
    }

    // ====================== CLEANUP ======================
    private void cleanup() {
        if (clockModule != null) clockModule.stopClock();
        if (stopwatchModule != null) stopwatchModule.cleanup();
    }
}
