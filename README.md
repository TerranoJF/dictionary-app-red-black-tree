# 📚 DictionaryApp - Red Black Tree

<p align="center">
  <b>Implementasi Struktur Data Red-Black Tree dalam Aplikasi Dictionary Berbasis Java</b>
</p>

---

## 📖 Deskripsi Project

**DictionaryApp** adalah aplikasi desktop berbasis **Java Swing** yang dikembangkan sebagai bagian dari tugas mata kuliah **Struktur Data**.

Aplikasi ini mengimplementasikan struktur data **Red-Black Tree (RBT)** sebagai mekanisme utama untuk menyimpan, mengelola, dan melakukan pencarian data kata dalam dictionary.

Red-Black Tree dipilih karena merupakan salah satu struktur data pohon biner yang memiliki kemampuan **self-balancing**, sehingga proses pencarian dan penyisipan data dapat dilakukan dengan kompleksitas waktu yang efisien, yaitu **O(log n)**.

Selain fitur dictionary, aplikasi ini juga menyediakan beberapa modul interaktif yang dapat diakses berdasarkan kata yang dicari oleh pengguna.

---

## 🌳 Tentang Red-Black Tree

Red-Black Tree adalah salah satu jenis **Self-Balancing Binary Search Tree**.

Setiap node dalam Red-Black Tree memiliki warna:

* 🔴 Merah (Red)
* ⚫ Hitam (Black)

Struktur ini memiliki beberapa aturan utama:

1. Setiap node memiliki warna merah atau hitam.
2. Root selalu berwarna hitam.
3. Node merah tidak boleh memiliki child merah.
4. Setiap jalur dari node menuju leaf memiliki jumlah node hitam yang sama.
5. Proses balancing dilakukan menggunakan **rotation** dan **recoloring**.

Dengan mekanisme tersebut, Red-Black Tree dapat menjaga tinggi pohon agar tetap seimbang.

### Kompleksitas Operasi

| Operasi   | Kompleksitas |
| --------- | ------------ |
| Search    | O(log n)     |
| Insert    | O(log n)     |
| Traversal | O(n)         |

---

# ✨ Fitur Aplikasi

## 🔎 Dictionary Search

Pengguna dapat mencari kata yang tersedia dalam dictionary.

Data dictionary disimpan menggunakan struktur:

```text
RedBlackTree<String, Entry>
```

Contoh kata yang tersedia:

* Kalkulator
* Jam
* Cuaca
* Catatan
* Konversi
* Kurs
* Stopwatch
* Reminder
* Red Black Tree

---

## 💡 Search Suggestion

Aplikasi menyediakan fitur rekomendasi kata ketika pengguna mengetikkan kata pada kolom pencarian.

Fitur ini menggunakan mekanisme:

```text
Prefix Search
```

untuk memberikan daftar kata yang memiliki awalan sesuai dengan input pengguna.

---

## 🧮 Kalkulator

Modul kalkulator sederhana yang dapat digunakan untuk melakukan perhitungan matematika dasar.

---

## 🕒 Jam Digital

Menampilkan waktu secara real-time, meliputi:

* Jam
* Menit
* Detik
* Tanggal

---

## ⏱️ Stopwatch

Fitur stopwatch dengan beberapa fungsi:

* Mulai
* Berhenti
* Reset

---

## 💱 Konversi Mata Uang

Aplikasi menyediakan fitur konversi mata uang.

Beberapa mata uang yang tersedia antara lain:

* IDR
* USD
* JPY
* EUR

Data nilai tukar diperoleh melalui API.

---

# 🛠️ Teknologi yang Digunakan

Project ini dikembangkan menggunakan:

* Java
* Java Swing
* Object-Oriented Programming (OOP)
* Red-Black Tree
* Java Collections
* HTTP API

---

# 📂 Struktur Project

```text
DictionaryApp
│
├── src
│   │
│   ├── DictionaryApp.java
│   ├── DictionaryData.java
│   ├── RedBlackTree.java
│   ├── SearchPanel.java
│   ├── ResultPanel.java
│   │
│   ├── CalculatorModule.java
│   ├── ClockModule.java
│   ├── CurrencyModule.java
│   └── StopwatchModule.java
│
└── README.md
```

---

# 🧩 Penjelasan File

### `DictionaryApp.java`

Merupakan file utama aplikasi.

Berfungsi untuk:

* Menjalankan aplikasi.
* Mengatur tampilan utama.
* Mengatur perpindahan halaman menggunakan `CardLayout`.

---

### `DictionaryData.java`

Berfungsi sebagai penyedia dan pengelola data dictionary.

Data disimpan menggunakan:

```java
RedBlackTree<String, Entry>
```

Class ini juga menangani:

* Penyimpanan kata.
* Pencarian kata.
* Penambahan data dictionary.
* Prefix search.

---

### `RedBlackTree.java`

Merupakan implementasi utama struktur data **Red-Black Tree**.

Beberapa operasi yang tersedia:

```java
put()
get()
containsKey()
prefixSearch()
firstKey()
lastKey()
forEach()
```

Class ini juga mengimplementasikan proses balancing menggunakan:

* Left Rotation
* Right Rotation
* Recoloring

---

### `SearchPanel.java`

Menampilkan halaman utama untuk melakukan pencarian kata.

Fitur yang tersedia:

* Input pencarian.
* Tombol search.
* Search suggestion.
* Debounce pada input pencarian.

---

### `ResultPanel.java`

Menampilkan hasil pencarian dictionary.

Apabila kata memiliki modul tertentu, aplikasi dapat menampilkan fitur interaktif yang sesuai.

---

# ⚙️ Cara Menjalankan Project

## 1. Clone Repository

```bash
git clone https://github.com/TerranoJF/dictionary-app-red-black-tree.git
```

Masuk ke folder project:

```bash
cd dictionary-app-red-black-tree
```

---

## 2. Compile Program

Masuk ke folder `src`:

```bash
cd src
```

Kemudian lakukan compile:

```bash
javac *.java
```

---

## 3. Jalankan Aplikasi

```bash
java DictionaryApp
```

Pastikan Java sudah terinstal pada komputer.

Untuk memeriksa versi Java:

```bash
java -version
```

---

# 🧠 Implementasi Red-Black Tree

Struktur utama dictionary pada aplikasi menggunakan:

```java
private RedBlackTree<String, Entry> dictionary;
```

Data dictionary dimasukkan menggunakan method:

```java
dictionary.put(key, value);
```

Ketika pengguna melakukan pencarian, aplikasi menggunakan:

```java
dictionary.get(key);
```

Untuk mengecek apakah suatu kata tersedia:

```java
dictionary.containsKey(key);
```

Dengan menggunakan Red-Black Tree, data dictionary dapat dikelola secara terstruktur dan efisien.

---

# 👥 Anggota Kelompok

Project ini dikembangkan oleh mahasiswa **Program Studi Informatika Angkatan 2024**, Institut Teknologi Kalimantan.

| No | Nama                       |
| -- | -------------------------- |
| 1  | Terrano Jazil Fadiatmoko   |
| 2  | Ahmad Anwar Abdul Qowi     |
| 3  | Brian Frisco Simanjuntak   |
| 4  | Chrisella Sefriana Tilukay |
| 5  | Syahri Nasta'in            |

---

# 🎓 Informasi Akademik

**Mata Kuliah**

Struktur Data

**Program Studi**

Informatika

**Angkatan**

2024

**Institusi**

Institut Teknologi Kalimantan

---

# 👨‍🏫 Dosen Pengampu

**Muchammad Chandra Cahyo Utomo, M.Kom.**

---

# 🎯 Tujuan Project

Project ini bertujuan untuk:

* Memahami konsep struktur data pohon.
* Mempelajari Binary Search Tree.
* Memahami mekanisme Self-Balancing Tree.
* Mengimplementasikan Red-Black Tree dari dasar.
* Memahami proses rotation pada tree.
* Menerapkan struktur data dalam aplikasi nyata.
* Mengembangkan kemampuan pemrograman Java.

---

# 📌 Catatan

Project ini dibuat untuk keperluan akademik sebagai bagian dari pembelajaran dan pemenuhan tugas pada mata kuliah **Struktur Data** di **Institut Teknologi Kalimantan**.

---

<p align="center">

<b>DictionaryApp - Red Black Tree</b>

<br>

Made with ❤️ by Informatics Students of Institut Teknologi Kalimantan

</p>
