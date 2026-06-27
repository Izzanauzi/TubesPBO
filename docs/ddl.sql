-- ============================================================
-- BidKita Database DDL
-- PostgreSQL
-- PBO IF-48-12 Kelompok 2 — Universitas Telkom 2026
-- ============================================================

-- Setup user & database (jalankan sebagai superuser postgres)
-- CREATE DATABASE bidkita;
-- CREATE USER bidkita_user WITH PASSWORD 'bidkita123';
-- GRANT ALL PRIVILEGES ON DATABASE bidkita TO bidkita_user;

-- ============================================================
-- PASTIKAN kamu sudah connect ke database bidkita sebelum
-- menjalankan script di bawah ini.
-- ============================================================

-- Drop semua tabel jika sudah ada (urutan penting karena FK)
DROP TABLE IF EXISTS watchlist CASCADE;
DROP TABLE IF EXISTS notifications CASCADE;
DROP TABLE IF EXISTS bids CASCADE;
DROP TABLE IF EXISTS auctions CASCADE;
DROP TABLE IF EXISTS electronic_items CASCADE;
DROP TABLE IF EXISTS fashion_items CASCADE;
DROP TABLE IF EXISTS book_items CASCADE;
DROP TABLE IF EXISTS vehicle_items CASCADE;
DROP TABLE IF EXISTS items CASCADE;
DROP TABLE IF EXISTS wallet_transactions CASCADE;
DROP TABLE IF EXISTS wallets CASCADE;
DROP TABLE IF EXISTS buyers CASCADE;
DROP TABLE IF EXISTS sellers CASCADE;
DROP TABLE IF EXISTS admins CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ============================================================
-- TABEL USERS (induk semua user, pakai JOINED inheritance)
-- ============================================================
CREATE TABLE users (
    user_id       VARCHAR(36)  PRIMARY KEY,
    dtype         VARCHAR(31)  NOT NULL,          -- 'Buyer' / 'Seller' / 'Admin'
    username      VARCHAR(100) NOT NULL UNIQUE,
    email         VARCHAR(150) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    phone_number  VARCHAR(20),
    registered_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- ============================================================
-- TABEL WALLETS (dimiliki Buyer, dibuat sebelum buyers)
-- ============================================================
CREATE TABLE wallets (
    wallet_id VARCHAR(36)     PRIMARY KEY,
    balance   DOUBLE PRECISION NOT NULL DEFAULT 0.0
);

-- ============================================================
-- TABEL WALLET_TRANSACTIONS
-- ============================================================
CREATE TABLE wallet_transactions (
    id        BIGSERIAL        PRIMARY KEY,
    wallet_id VARCHAR(36)      NOT NULL REFERENCES wallets(wallet_id) ON DELETE CASCADE,
    amount    DOUBLE PRECISION NOT NULL,
    type      VARCHAR(20)      NOT NULL,           -- DEPOSIT / WITHDRAW / REFUND
    created_at TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- ============================================================
-- TABEL BUYERS
-- ============================================================
CREATE TABLE buyers (
    user_id   VARCHAR(36) PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
    wallet_id VARCHAR(36) REFERENCES wallets(wallet_id)
);

-- ============================================================
-- TABEL SELLERS
-- ============================================================
CREATE TABLE sellers (
    user_id    VARCHAR(36)      PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
    rating     DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    total_sold INTEGER          NOT NULL DEFAULT 0
);

-- ============================================================
-- TABEL ADMINS
-- ============================================================
CREATE TABLE admins (
    user_id    VARCHAR(36) PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
    admin_code VARCHAR(50)
);

-- ============================================================
-- TABEL ITEMS (induk semua item, pakai JOINED inheritance)
-- ============================================================
CREATE TABLE items (
    item_id       VARCHAR(36)      PRIMARY KEY,
    dtype         VARCHAR(31)      NOT NULL,       -- nama subclass
    title         VARCHAR(200)     NOT NULL,
    description   TEXT,
    condition     VARCHAR(50)      NOT NULL,       -- BARU / BEKAS_BAIK / BEKAS_RUSAK
    image_base64  TEXT,
    starting_price DOUBLE PRECISION NOT NULL
);

-- ============================================================
-- TABEL ELECTRONIC_ITEMS
-- ============================================================
CREATE TABLE electronic_items (
    item_id        VARCHAR(36) PRIMARY KEY REFERENCES items(item_id) ON DELETE CASCADE,
    brand          VARCHAR(100),
    type           VARCHAR(100),
    warranty_status BOOLEAN NOT NULL DEFAULT FALSE
);

-- ============================================================
-- TABEL FASHION_ITEMS
-- ============================================================
CREATE TABLE fashion_items (
    item_id  VARCHAR(36) PRIMARY KEY REFERENCES items(item_id) ON DELETE CASCADE,
    brand    VARCHAR(100),
    size     VARCHAR(20),
    material VARCHAR(100)
);

-- ============================================================
-- TABEL BOOK_ITEMS
-- ============================================================
CREATE TABLE book_items (
    item_id      VARCHAR(36) PRIMARY KEY REFERENCES items(item_id) ON DELETE CASCADE,
    author       VARCHAR(150),
    publisher    VARCHAR(150),
    publish_year INTEGER
);

-- ============================================================
-- TABEL VEHICLE_ITEMS
-- ============================================================
CREATE TABLE vehicle_items (
    item_id    VARCHAR(36) PRIMARY KEY REFERENCES items(item_id) ON DELETE CASCADE,
    brand      VARCHAR(100),
    year       INTEGER,
    mileage    INTEGER,
    plat_number VARCHAR(20)
);

-- ============================================================
-- TABEL AUCTIONS
-- ============================================================
CREATE TABLE auctions (
    auction_id        VARCHAR(36)      PRIMARY KEY,
    item_id           VARCHAR(36)      NOT NULL REFERENCES items(item_id),
    seller_id         VARCHAR(36)      NOT NULL REFERENCES users(user_id),
    current_price     DOUBLE PRECISION NOT NULL,
    buy_now_price     DOUBLE PRECISION,            -- nullable
    start_time        TIMESTAMP,
    end_time          TIMESTAMP,
    status            VARCHAR(20)      NOT NULL DEFAULT 'PENDING', -- PENDING/OPEN/CLOSING/SOLD/CANCELLED
    highest_bidder_id VARCHAR(36)      REFERENCES users(user_id)   -- nullable
);

-- ============================================================
-- TABEL BIDS
-- ============================================================
CREATE TABLE bids (
    bid_id     VARCHAR(36)      PRIMARY KEY,
    bidder_id  VARCHAR(36)      NOT NULL REFERENCES users(user_id),
    auction_id VARCHAR(36)      NOT NULL REFERENCES auctions(auction_id) ON DELETE CASCADE,
    amount     DOUBLE PRECISION NOT NULL,
    timestamp  TIMESTAMP        NOT NULL DEFAULT NOW()
);

-- ============================================================
-- TABEL NOTIFICATIONS
-- ============================================================
CREATE TABLE notifications (
    notif_id     VARCHAR(36) PRIMARY KEY,
    recipient_id VARCHAR(36) NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    message      TEXT,
    type         VARCHAR(50),                      -- OUTBID / AUCTION_WON / dll
    is_read      BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at   TIMESTAMP   NOT NULL DEFAULT NOW()
);

-- ============================================================
-- TABEL WATCHLIST (many-to-many Buyer <-> Auction)
-- ============================================================
CREATE TABLE watchlist (
    buyer_id   VARCHAR(36) NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    auction_id VARCHAR(36) NOT NULL REFERENCES auctions(auction_id) ON DELETE CASCADE,
    PRIMARY KEY (buyer_id, auction_id)
);

-- ============================================================
-- INDEX (opsional tapi membantu performa query)
-- ============================================================
CREATE INDEX idx_auctions_status      ON auctions(status);
CREATE INDEX idx_auctions_seller      ON auctions(seller_id);
CREATE INDEX idx_bids_auction         ON bids(auction_id);
CREATE INDEX idx_bids_bidder          ON bids(bidder_id);
CREATE INDEX idx_notif_recipient      ON notifications(recipient_id);
CREATE INDEX idx_wallet_tx_wallet     ON wallet_transactions(wallet_id);

-- ============================================================
-- SELESAI
-- ============================================================