import java.awt.*;
import javax.swing.*;

public class DictionaryApp extends JFrame {
    private JPanel mainContainer;
    private SearchPanel searchPanel;
    private ResultPanel resultPanel;
    private DictionaryData dictionaryData;

    public DictionaryApp() {
        setTitle("DictionaryApp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        dictionaryData = new DictionaryData();

        mainContainer = new JPanel();
        mainContainer.setLayout(new CardLayout());

        searchPanel = new SearchPanel(this);
        resultPanel = new ResultPanel(this);

        mainContainer.add(searchPanel, "SEARCH");
        mainContainer.add(resultPanel, "RESULT");

        add(mainContainer);

        CardLayout cardLayout = (CardLayout) mainContainer.getLayout();
        cardLayout.show(mainContainer, "SEARCH");
        setVisible(true);
    }

    public void displayResult(String word) {
        if (dictionaryData.containsWord(word)) {
            DictionaryData.Entry entry = dictionaryData.getEntry(word);
            resultPanel.setWordData(entry);
            CardLayout cardLayout = (CardLayout) mainContainer.getLayout();
            cardLayout.show(mainContainer, "RESULT");
        } else {
            JOptionPane.showMessageDialog(this, "Kata tidak ditemukan!", "Hasil Pencarian", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void showSearchPanel() {
        CardLayout cardLayout = (CardLayout) mainContainer.getLayout();
        cardLayout.show(mainContainer, "SEARCH");
    }

    public DictionaryData getDictionaryData() {
        return dictionaryData;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DictionaryApp());
    }
}
