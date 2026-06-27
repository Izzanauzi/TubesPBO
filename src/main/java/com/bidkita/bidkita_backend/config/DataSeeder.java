package com.bidkita.bidkita_backend.config;

import com.bidkita.bidkita_backend.model.*;
import com.bidkita.bidkita_backend.model.enums.AuctionStatus;
import com.bidkita.bidkita_backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final ItemRepository itemRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      AuctionRepository auctionRepository,
                      ItemRepository itemRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
        this.itemRepository = itemRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return; // skip jika sudah ada data

        // ============================================================
        // 1. ADMIN
        // ============================================================
        Admin admin = new Admin("admin", "admin@bidkita.com",
                passwordEncoder.encode("admin123"), "081234567890", "ADMIN-001");
        userRepository.save(admin);

        // ============================================================
        // 2. SELLERS
        // ============================================================
        Seller seller1 = new Seller("tokoElektronik", "seller1@bidkita.com",
                passwordEncoder.encode("seller123"), "081234567891");
        Seller seller2 = new Seller("fashionLover", "seller2@bidkita.com",
                passwordEncoder.encode("seller123"), "081234567892");
        userRepository.save(seller1);
        userRepository.save(seller2);

        // ============================================================
        // 3. BUYERS (masing-masing deposit Rp 10.000.000)
        // ============================================================
        Buyer buyer1 = new Buyer("ahmadRizky", "buyer1@bidkita.com",
                passwordEncoder.encode("buyer123"), "081234567893");
        buyer1.getWallet().deposit(10_000_000);

        Buyer buyer2 = new Buyer("sitiNurhaliza", "buyer2@bidkita.com",
                passwordEncoder.encode("buyer123"), "081234567894");
        buyer2.getWallet().deposit(10_000_000);

        Buyer buyer3 = new Buyer("budihartono", "buyer3@bidkita.com",
                passwordEncoder.encode("buyer123"), "081234567895");
        buyer3.getWallet().deposit(15_000_000);

        userRepository.save(buyer1);
        userRepository.save(buyer2);
        userRepository.save(buyer3);

        // ============================================================
        // 4. AUCTION 1 — iPhone 13 Pro (OPEN, sudah ada 2 bid)
        // ============================================================
        ElectronicItem item1 = new ElectronicItem();
        item1.setTitle("iPhone 13 Pro 256GB Mulus");
        item1.setDescription("Kondisi mulus fullset, baterai 89%");
        item1.setCondition("BEKAS_BAIK");
        item1.setBrand("Apple");
        item1.setType("Smartphone");
        item1.setWarrantyStatus(false);
        item1.setStartingPrice(8_500_000);
        itemRepository.save(item1);

        Auction auction1 = new Auction(item1, seller1, 8_500_000, 7);
        auction1.setStatus(AuctionStatus.OPEN);
        auctionRepository.save(auction1);
        auction1.placeBid(buyer1, 8_700_000);
        auction1.placeBid(buyer2, 8_900_000);
        auctionRepository.save(auction1);
        userRepository.save(buyer1);
        userRepository.save(buyer2);

        // ============================================================
        // 5. AUCTION 2 — MacBook Air M1 (OPEN, ada buyNowPrice, 1 bid)
        // ============================================================
        ElectronicItem item2 = new ElectronicItem();
        item2.setTitle("MacBook Air M1 8GB 256GB");
        item2.setDescription("Unit resmi iBox, kondisi 95%, charger original");
        item2.setCondition("BEKAS_BAIK");
        item2.setBrand("Apple");
        item2.setType("Laptop");
        item2.setWarrantyStatus(true);
        item2.setStartingPrice(12_200_000);
        itemRepository.save(item2);

        Auction auction2 = new Auction(item2, seller1, 12_200_000, 5);
        auction2.setStatus(AuctionStatus.OPEN);
        auction2.setBuyNowPrice(15_000_000.0);
        auctionRepository.save(auction2);
        auction2.placeBid(buyer3, 12_500_000);
        auctionRepository.save(auction2);
        userRepository.save(buyer3);

        // ============================================================
        // 6. AUCTION 3 — Nike Air Max 90 (PENDING, butuh approve admin)
        // ============================================================
        FashionItem item3 = new FashionItem();
        item3.setTitle("Nike Air Max 90 Size 42");
        item3.setDescription("Ori beli di store, pemakaian 3x, box lengkap");
        item3.setCondition("BEKAS_BAIK");
        item3.setBrand("Nike");
        item3.setSize("42");
        item3.setMaterial("Mesh & Rubber");
        item3.setStartingPrice(500_000);
        itemRepository.save(item3);

        Auction auction3 = new Auction(item3, seller2, 500_000, 3);
        // status default PENDING, tidak perlu di-set
        auctionRepository.save(auction3);

        // ============================================================
        // 7. AUCTION 4 — Atomic Habits (OPEN)
        // ============================================================
        BookItem item4 = new BookItem();
        item4.setTitle("Atomic Habits - James Clear");
        item4.setDescription("Edisi bahasa Indonesia, kondisi baik, tidak ada coretan");
        item4.setCondition("BEKAS_BAIK");
        item4.setAuthor("James Clear");
        item4.setPublisher("Gramedia");
        item4.setPublishYear(2019);
        item4.setStartingPrice(75_000);
        itemRepository.save(item4);

        Auction auction4 = new Auction(item4, seller2, 75_000, 4);
        auction4.setStatus(AuctionStatus.OPEN);
        auctionRepository.save(auction4);

        // ============================================================
        // 8. AUCTION 5 — Honda Beat 2020 (PENDING, butuh approve admin)
        // ============================================================
        VehicleItem item5 = new VehicleItem();
        item5.setTitle("Honda Beat 2020 Pajak Hidup");
        item5.setDescription("Kilometer rendah, mesin sehat, cat original");
        item5.setCondition("BEKAS_BAIK");
        item5.setBrand("Honda");
        item5.setYear(2020);
        item5.setMileage(15000);
        item5.setPlatNumber("B 1234 XYZ");
        item5.setStartingPrice(12_000_000);
        itemRepository.save(item5);

        Auction auction5 = new Auction(item5, seller1, 12_000_000, 7);
        // status default PENDING
        auctionRepository.save(auction5);

        System.out.println("========================================");
        System.out.println("  Seed data berhasil dimasukkan!");
        System.out.println("  Admin    : admin@bidkita.com / admin123");
        System.out.println("  Seller 1 : seller1@bidkita.com / seller123");
        System.out.println("  Seller 2 : seller2@bidkita.com / seller123");
        System.out.println("  Buyer 1  : buyer1@bidkita.com / buyer123");
        System.out.println("  Buyer 2  : buyer2@bidkita.com / buyer123");
        System.out.println("  Buyer 3  : buyer3@bidkita.com / buyer123");
        System.out.println("========================================");
    }
}