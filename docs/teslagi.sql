-- ============================================================
-- BidKita — Script Reset Database untuk Testing Ulang
-- ============================================================
-- CARA PAKAI:
-- 1. Buka DBeaver, koneksi ke database 'bidkita' (bukan 'bidkita_db' atau yang lain
--    -- pastikan kamu di database yang benar, cek di breadcrumb atas: bidkita_db > bidkita)
-- 2. Buka SQL Editor baru (klik kanan database -> SQL Editor -> New SQL Script)
--    atau pakai tab yang sudah ada seperti "bidkita.sql"
-- 3. Copy-paste SELURUH isi file ini
-- 4. Select semua teks (Ctrl+A), lalu jalankan dengan tombol Execute Script
--    (BUKAN Execute SQL Statement biasa -- karena ini multi-statement)
--    Shortcut DBeaver biasanya: Alt+X atau klik ikon "play" dengan banyak garis
-- 5. Setelah selesai, STOP aplikasi Spring Boot kalau sedang jalan, lalu START ulang
--    -- DataSeeder akan otomatis isi ulang data awal karena tabel users sudah kosong
-- ============================================================

-- Hapus data dari tabel anak/dependent dulu (urutan ini penting untuk menghindari
-- foreign key violation, walau CASCADE seharusnya sudah menghandle otomatis)

TRUNCATE TABLE
    watchlist,
    bids,
    wallet_transactions,
    notifications,
    auctions
RESTART IDENTITY CASCADE;

TRUNCATE TABLE
    electronic_items,
    fashion_items,
    book_items,
    vehicle_items
RESTART IDENTITY CASCADE;

TRUNCATE TABLE
    items
RESTART IDENTITY CASCADE;

TRUNCATE TABLE
    wallets
RESTART IDENTITY CASCADE;

TRUNCATE TABLE
    buyers,
    sellers,
    admins
RESTART IDENTITY CASCADE;

TRUNCATE TABLE
    users
RESTART IDENTITY CASCADE;

-- ============================================================
-- Verifikasi semua tabel sudah kosong (opsional, jalankan terpisah)
-- ============================================================
-- SELECT
--   (SELECT COUNT(*) FROM users) AS total_users,
--   (SELECT COUNT(*) FROM auctions) AS total_auctions,
--   (SELECT COUNT(*) FROM items) AS total_items,
--   (SELECT COUNT(*) FROM bids) AS total_bids,
--   (SELECT COUNT(*) FROM wallets) AS total_wallets;
-- Semua harus menunjukkan 0 setelah TRUNCATE berhasil.

-- ============================================================
-- SETELAH INI: restart aplikasi Spring Boot.
-- DataSeeder.java akan otomatis jalan lagi (karena userRepository.count() == 0)
-- dan mengisi ulang data awal: 1 admin, 2 seller, 3 buyer, 5 auction.
-- ============================================================