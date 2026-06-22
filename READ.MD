bidkita-backend/
├── docs/                       # Dokumentasi (API Contract, DDL Database SQL)
├── postman/                    # Collection Postman untuk testing API
├── src/
│   ├── main/
│   │   ├── java/com/bidkita/bidkita_backend/
│   │   │   ├── config/         # Konfigurasi aplikasi (contoh: DataSeeder)
│   │   │   ├── controller/     # Endpoint REST API (Auth, Auction, Bid, Wallet, dll)
│   │   │   ├── dto/            # Data Transfer Object (Pembungkus request & response)
│   │   │   │   ├── request/    # Format data masuk dari client
│   │   │   │   └── response/   # Format data keluar ke client
│   │   │   ├── exception/      # Custom Exception & GlobalExceptionHandler
│   │   │   ├── model/          # Entitas Database & Struktur OOP (Abstract Item, Buyer, dll)
│   │   │   │   └── enums/      # Enumerasi (AuctionStatus, NotificationType, dll)
│   │   │   ├── repository/     # Interface Spring Data JPA (Akses CRUD ke Database)
│   │   │   ├── scheduler/      # Cron Jobs (Auto-update status lelang)
│   │   │   ├── security/       # Konfigurasi Security & Filter JWT
│   │   │   ├── service/        # Logika Bisnis & Design Pattern (contoh: ItemFactory)
│   │   │   └── BidkitaBackendApplication.java  # Main Class Spring Boot
│   │   │
│   │   └── resources/
│   │       └── application.properties          # Konfigurasi port & koneksi PostgreSQL
│   │
│   └── test/                   # Direktori untuk Unit Testing Java
│
├── BidKita.postman_collection.json  # Export Postman untuk Demo/Test
├── Demo.postman_collection.json     # Export Postman khusus Demo Singkat
├── pom.xml                     # Konfigurasi dependensi (Maven)
└── README.md                   # Dokumentasi Utama
