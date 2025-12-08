public class DictionaryData {
    
    public static class Entry {
        private String word;
        private String description;
        private String toolName;

        public Entry(String word, String description, String toolName) {
            this.word = word;
            this.description = description;
            this.toolName = toolName;
        }

        public Entry(String word, String description) {
            this(word, description, null);
        }

        public String getWord() { return word; }
        public String getDescription() { return description; }
        public String getToolName() { return toolName; }
        public boolean hasTool() { return toolName != null && !toolName.isEmpty(); }
    }

    private RedBlackTree<String, Entry> dictionary;

    public DictionaryData() {
        dictionary = new RedBlackTree<>();
        initializeDictionary();
    }

    private void initializeDictionary() {
        dictionary.put("kalkulator", new Entry(
            "Kalkulator",
            "Alat untuk menghitung dari perhitungan sederhana seperti penjumlahan, pengurangan, perkalian dan pembagian sampai kepada kalkulator ilmiah yang dapat menghitung rumus matematika tertentu.",
            "kalkulator"
        ));
        
        dictionary.put("jam", new Entry(
            "Jam",
            "Alat untuk menunjukkan waktu dengan menampilkan jam, menit, dan detik secara real-time. Jam berguna untuk membantu Anda mengatur jadwal harian dan memantau waktu dengan akurat.",
            "jam"
        ));
        
        dictionary.put("cuaca", new Entry(
            "Cuaca",
            "Menampilkan informasi cuaca seperti suhu, kondisi langit, dan kelembapan berdasarkan lokasi tertentu.",
            "text"
        ));

        dictionary.put("catatan", new Entry(
            "Catatan",
            "Fitur sederhana untuk menyimpan catatan pribadi, to-do list, atau daftar kegiatan harian.",
            "text"
        ));

        dictionary.put("konversi", new Entry(
            "Konversi Satuan",
            "Alat untuk mengubah satuan seperti panjang, berat, suhu, dan volume ke satuan lain.",
            "text"
        ));

        dictionary.put("kurs", new Entry(
            "Kurs Mata Uang",
            "Menampilkan nilai tukar berbagai mata uang secara real-time untuk membantu perhitungan finansial.",
            "kurs"
        ));

        dictionary.put("stopwatch", new Entry(
            "Stopwatch",
            "Alat untuk menghitung waktu mundur atau maju secara presisi, cocok untuk olahraga atau kegiatan berjangka waktu.",
            "stopwatch"
        ));

        dictionary.put("reminder", new Entry(
            "Pengingat",
            "Fitur untuk menjadwalkan pengingat agar tidak lupa kegiatan atau tugas penting.",
            "text"
        ));
        
        dictionary.put("red black tree", new Entry(
            "Red Black Tree (RBT)",
            "Struktur data pohon biner yang seimbang dengan properti warna node (merah atau hitam). Red Black Tree menjamin operasi pencarian, penyisipan, dan penghapusan dalam waktu O(log n). Properti RBT: (1) Setiap node berwarna merah atau hitam, (2) Root selalu hitam, (3) Semua leaf adalah hitam, (4) Node merah tidak memiliki anak merah, (5) Semua path dari node ke leaf memiliki jumlah node hitam yang sama. RBT digunakan untuk implementasi set dan map dalam banyak bahasa pemrograman.",
            "rbt"
        ));
    }

    public Entry getEntry(String word) {
        return dictionary.get(word.toLowerCase());
    }

    public boolean containsWord(String word) {
        return dictionary.containsKey(word.toLowerCase());
    }

    public java.util.Map<String, Entry> getAllEntries() {
        java.util.Map<String, Entry> result = new java.util.LinkedHashMap<>();
        dictionary.forEach((key, value) -> result.put(key, value));
        return result;
    }

    public void addEntry(String word, String description, String toolName) {
        dictionary.put(word.toLowerCase(), new Entry(word, description, toolName));
    }

    public void addEntry(String word, String description) {
        addEntry(word, description, null);
    }

    public RedBlackTree<String, Entry> getTree() {
        return dictionary;
    }

    public java.util.List<String> prefixSearch(String prefix) {
        return dictionary.prefixSearch(prefix);
    }
}
