import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class CurrencyModule extends JPanel {
    private final JTextField inputField;
    private final JComboBox<String> fromCurrency;
    private final JComboBox<String> toCurrency;
    private final JLabel resultLabel;
    private final JButton convertButton;
    private final DecimalFormat decimalFormat;
    
    private final Map<String, Double> exchangeRates;
    private long lastUpdateTime = 0;
    private static final long CACHE_DURATION = 3600000; 

    public CurrencyModule() {
        decimalFormat = new DecimalFormat("#,##0.00");
        exchangeRates = new HashMap<>();
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setOpaque(false);
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel inputLabel = new JLabel("Jumlah:");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        inputField = new JTextField(15);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setText("1");

        inputPanel.add(inputLabel);
        inputPanel.add(inputField);

        JPanel currencyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        currencyPanel.setOpaque(false);
        currencyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel fromLabel = new JLabel("Dari:");
        fromLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        String[] currencies = {"IDR", "USD", "JPY", "EUR"};
        fromCurrency = new JComboBox<>(currencies);
        fromCurrency.setFont(new Font("Arial", Font.PLAIN, 14));
        fromCurrency.setPreferredSize(new Dimension(100, 30));

        JLabel toLabel = new JLabel("Ke:");
        toLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        toCurrency = new JComboBox<>(currencies);
        toCurrency.setFont(new Font("Arial", Font.PLAIN, 14));
        toCurrency.setSelectedIndex(1);
        toCurrency.setPreferredSize(new Dimension(100, 30));

        currencyPanel.add(fromLabel);
        currencyPanel.add(fromCurrency);
        currencyPanel.add(toLabel);
        currencyPanel.add(toCurrency);

        JPanel resultPanel = new JPanel();
        resultPanel.setOpaque(false);
        resultPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultLabel = new JLabel("Hasil: 0.00");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resultLabel.setForeground(new Color(0, 51, 102));

        resultPanel.add(resultLabel);

        convertButton = createButton("Konversi", new Color(76, 175, 80));
        convertButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        convertButton.addActionListener(e -> performConversionWithAPI());

        add(inputPanel);
        add(Box.createVerticalStrut(20));
        add(currencyPanel);
        add(Box.createVerticalStrut(20));
        add(convertButton);
        add(Box.createVerticalStrut(20));
        add(resultPanel);
        add(Box.createVerticalGlue());
        
        loadExchangeRates();
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }

    private void loadExchangeRates() {
        Thread thread = new Thread(() -> {
            try {
                long currentTime = System.currentTimeMillis();
                 
                if (exchangeRates.isEmpty() || (currentTime - lastUpdateTime) > CACHE_DURATION) {
                    convertButton.setText("Loading...");
                    convertButton.setEnabled(false);
                    
                    // Fetch dari ExchangeRate-API (free 1500 requests/month)
                    String urlString = "https://api.exchangerate-api.com/v4/latest/IDR";
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    
                    if (conn.getResponseCode() == 200) {
                        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = in.readLine()) != null) {
                                response.append(line);
                            }
                             
                            parseExchangeRates(response.toString());
                        }
                        lastUpdateTime = currentTime;
                    }
                    
                    convertButton.setText("Konversi");
                    convertButton.setEnabled(true);
                }
            } catch (IOException e) {
                convertButton.setText("Konversi");
                convertButton.setEnabled(true);
                JOptionPane.showMessageDialog(this, "Gagal fetch exchange rates. Gunakan fallback rates.", "Warning", JOptionPane.WARNING_MESSAGE);
                setFallbackRates();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void parseExchangeRates(String jsonResponse) {
        try { 
            int ratesIndex = jsonResponse.indexOf("\"rates\"");
            int startIndex = jsonResponse.indexOf("{", ratesIndex);
            int endIndex = jsonResponse.lastIndexOf("}");
            String ratesStr = jsonResponse.substring(startIndex, endIndex + 1);
             
            extractCurrencyRate(ratesStr, "USD");
            extractCurrencyRate(ratesStr, "JPY");
            extractCurrencyRate(ratesStr, "EUR");
            exchangeRates.put("IDR", 1.0);
        } catch (Exception e) {
            setFallbackRates();
        }
    }

    private void extractCurrencyRate(String json, String currency) {
        try {
            String pattern = "\"" + currency + "\":";
            int index = json.indexOf(pattern);
            if (index != -1) {
                int startIdx = index + pattern.length();
                int endIdx = json.indexOf(",", startIdx);
                if (endIdx == -1) {
                    endIdx = json.indexOf("}", startIdx);
                }
                String valueStr = json.substring(startIdx, endIdx).trim();
                double rate = Double.parseDouble(valueStr);
                exchangeRates.put(currency, rate);
            }
        } catch (NumberFormatException ignored) { 
        }
    }

    private void setFallbackRates() {
        exchangeRates.put("USD", 0.000063);
        exchangeRates.put("JPY", 0.0093);
        exchangeRates.put("EUR", 0.000060);
        exchangeRates.put("IDR", 1.0);
    }

    private void performConversionWithAPI() {
        try {
            if (exchangeRates.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Exchange rates belum loaded. Silakan coba lagi.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double inputAmount = Double.parseDouble(inputField.getText());
            String from = (String) fromCurrency.getSelectedItem();
            String to = (String) toCurrency.getSelectedItem();

            if (from.equals(to)) {
                resultLabel.setText("Hasil: " + decimalFormat.format(inputAmount));
                return;
            }

            double inIDR = convertToIDR(inputAmount, from);
            double result = convertFromIDR(inIDR, to);

            resultLabel.setText("Hasil: " + decimalFormat.format(result) + " " + to);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double convertToIDR(double amount, String currency) {
        return amount / exchangeRates.getOrDefault(currency, 1.0);
    }

    private double convertFromIDR(double amountInIDR, String currency) {
        return amountInIDR * exchangeRates.getOrDefault(currency, 1.0);
    }
}
