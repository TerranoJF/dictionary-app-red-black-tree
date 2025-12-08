import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class SearchPanel extends JPanel {
    private DictionaryApp mainApp;
    private JTextField searchField;
    private JButton searchButton;

    private JList<String> suggestionsList;
    private DefaultListModel<String> suggestionsModel;
    private JScrollPane suggestionsScrollPane;
    private Timer debounceTimer;
    private JPanel suggestionsContainer;

    private static final int DEBOUNCE_DELAY = 150; // ms
    private static final int MAX_SUGGESTIONS = 8;
    private static final int MAX_VISIBLE_ITEMS = 5;

    public SearchPanel(DictionaryApp mainApp) {
        this.mainApp = mainApp;
        setBackground(new Color(173, 216, 230));
        setLayout(new BorderLayout(0, 0));

        // ===== TITLE =====
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("DictionaryApp");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(new Color(0, 51, 102));
        titlePanel.add(titleLabel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        topPanel.add(titlePanel);
        topPanel.add(Box.createVerticalStrut(10));

        // ===== SEARCH BAR =====
        JPanel searchBarPanel = new JPanel();
        searchBarPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchBarPanel.setOpaque(false);

        searchField = new JTextField(30);
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setPreferredSize(new Dimension(400, 40));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 51, 102), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        debounceTimer = new Timer(DEBOUNCE_DELAY, e -> updateSuggestions());
        debounceTimer.setRepeats(false);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { schedule(); }
            @Override
            public void removeUpdate(DocumentEvent e) { schedule(); }
            @Override
            public void changedUpdate(DocumentEvent e) { schedule(); }

            private void schedule() {
                debounceTimer.restart();
            }
        });

        // ===== SEARCH BUTTON =====
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));
        searchButton.setPreferredSize(new Dimension(100, 40));
        searchButton.setBackground(new Color(0, 51, 102));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorderPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchButton.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch());

        searchBarPanel.add(searchField);
        searchBarPanel.add(searchButton);

        topPanel.add(searchBarPanel);

        // ===== SUGGESTION LIST =====
        suggestionsModel = new DefaultListModel<>();
        suggestionsList = new JList<>(suggestionsModel);
        suggestionsList.setFont(new Font("Arial", Font.PLAIN, 14));
        suggestionsList.setBackground(Color.WHITE);
        suggestionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suggestionsList.setFixedCellHeight(30);
        suggestionsList.setBorder(new EmptyBorder(5, 10, 5, 10));

        suggestionsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                label.setBorder(new EmptyBorder(5, 10, 5, 10));

                if (isSelected) {
                    label.setBackground(new Color(0, 51, 102));
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(Color.WHITE);
                    label.setForeground(new Color(60, 60, 60));
                }

                return label;
            }
        });

        suggestionsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) selectSuggestion();
            }
        });

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN && suggestionsModel.size() > 0) {
                    suggestionsList.setSelectedIndex(0);
                    suggestionsList.requestFocus();
                }
            }
        });

        suggestionsScrollPane = new JScrollPane(suggestionsList);
        suggestionsScrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102), 2));
        suggestionsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        suggestionsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        suggestionsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        suggestionsContainer.setOpaque(false);
        suggestionsContainer.add(suggestionsScrollPane);
        suggestionsContainer.setVisible(false);

        topPanel.add(suggestionsContainer);

        add(topPanel, BorderLayout.PAGE_START);

        // click outside = hide suggestion
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isClickInsideSuggestions(e.getPoint())) hideSuggestions();
            }
        });
    }

    private void updateSuggestions() {
        String searchText = searchField.getText().trim().toLowerCase();
        suggestionsModel.clear();

        if (!searchText.isEmpty()) {
            List<String> results = mainApp.getDictionaryData().prefixSearch(searchText);

            int count = Math.min(results.size(), MAX_SUGGESTIONS);
            for (int i = 0; i < count; i++) {
                suggestionsModel.addElement(results.get(i));
            }

            if (suggestionsModel.size() > 0) showSuggestions();
            else hideSuggestions();
        } else hideSuggestions();
    }

    private void showSuggestions() {
        int visibleItems = Math.min(suggestionsModel.size(), MAX_VISIBLE_ITEMS);
        int height = visibleItems * 30 + 10;
        suggestionsScrollPane.setPreferredSize(new Dimension(415, height));

        suggestionsContainer.setVisible(true);
        suggestionsContainer.revalidate();
        suggestionsContainer.repaint();
    }

    private void hideSuggestions() {
        suggestionsContainer.setVisible(false);
        suggestionsContainer.revalidate();
        suggestionsContainer.repaint();
    }

    private void selectSuggestion() {
        String selected = suggestionsList.getSelectedValue();
        if (selected != null) {
            searchField.setText(selected);
            hideSuggestions();
            performSearch();
        }
    }

    private boolean isClickInsideSuggestions(Point p) {
        Point converted = SwingUtilities.convertPoint(this, p, suggestionsContainer);
        return suggestionsContainer.isVisible() &&
                suggestionsContainer.contains(converted);
    }

    private void performSearch() {
        String word = searchField.getText().trim();
        if (!word.isEmpty()) {
            hideSuggestions();
            mainApp.displayResult(word);
        }
    }
}
