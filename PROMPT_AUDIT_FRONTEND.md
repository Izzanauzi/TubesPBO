# PROMPT_AUDIT_FRONTEND.md

Prompt siap-pakai untuk Claude Code. Copy-paste isi di bawah "## PROMPT" ke
Claude Code setelah backend selesai ditest penuh (70+ skenario Postman),
supaya frontend dicocokkan ulang dengan kontrak API yang sudah TERBUKTI benar
di backend — bukan cuma asumsi dari dokumen pembagian kerja awal.

Pastikan sebelum menjalankan ini:
- [ ] Backend sudah jalan & lolos seluruh 70+ skenario Postman
- [ ] `BidKita_DEMO.postman_collection.json` ada di root project
- [ ] Folder frontend hasil refactor (`index.html`, `style.css`, `js/`) sudah ada
- [ ] `FRONTEND_CLAUDE.md` ada di folder frontend

---

## PROMPT

Saya sudah merefactor frontend test BidKita jadi modular (lihat `FRONTEND_CLAUDE.md`
di folder frontend untuk struktur & konvensinya). Backend juga sudah selesai
ditest dengan 70+ skenario di `BidKita_DEMO.postman_collection.json` dan beberapa
bug sudah diperbaiki (GlobalExceptionHandler, AuthenticationEntryPoint, validasi role,
validasi title kosong saat create auction).

Tolong audit frontend terhadap backend yang SUDAH ditest ini, dengan langkah:

1. **Baca kontrak nyata, bukan dokumen lama.** Buka source code Controller +
   Service + DTO backend langsung (jangan hanya percaya dokumen pembagian kerja),
   lalu bandingkan field-by-field dengan yang dipakai di tiap `js/views/*.js`.
   Fokus ke:
   - Nama field response JSON (case-sensitive) vs yang dibaca di `renderDetail()`,
     `renderAuctionCard()`, dan render function lain.
   - Field request DTO yang dikirim frontend (`buildDto()` di `createAuction.js`,
     body di `auth.js`, `wallet.js`) vs yang divalidasi backend.
   - HTTP status code yang sekarang dikembalikan tiap exception (sesudah fix
     GlobalExceptionHandler) vs penanganan generik di `apiFetch()` — pastikan
     401 dan 403 dibedakan dengan benar di sisi UX (mis. redirect ke login untuk
     401, banner "tidak punya akses" untuk 403).

2. **Jalankan tiap skenario di Postman collection lalu reproduksi manual lewat
   UI** untuk endpoint-endpoint berikut, catat selisihnya:
   - POST /auth/register, POST /auth/login, GET /auth/me
   - GET /auctions (semua kombinasi filter), GET /auctions/{id}
   - POST /auctions (tiap kategori: ELEKTRONIK/FASHION/BUKU/KENDARAAN)
   - POST /auctions/{id}/bid (termasuk skenario gagal: BidTooLow, AuctionNotOpen,
     InsufficientBalance)
   - POST /auctions/{id}/buynow
   - PUT /auctions/{id}/approve, DELETE /auctions/{id}
   - GET /wallet, POST /wallet/topup
   - GET /buyer/bids, GET /seller/auctions
   - GET /admin/users, GET /admin/auctions
   - GET /notifications

3. **Untuk setiap selisih yang ditemukan**, jelaskan dulu ke saya: field/endpoint
   mana, ekspektasi frontend vs realita backend, dan dampaknya ke UI (error
   silent, data tidak muncul, dll) — SEBELUM langsung mengubah kode. Saya mau
   review dulu sebelum di-apply, terutama kalau perubahan menyentuh banyak file.

4. Setelah saya setuju, perbaiki di file yang relevan saja, ikuti pola arsitektur
   di `FRONTEND_CLAUDE.md` (jangan tambah fetch() langsung di luar `api.js`,
   jangan bikin view module saling import kecuali ke `detail.js`).

5. Tutup dengan ringkasan: daftar bug yang ditemukan + fix yang di-apply, dan
   daftar yang masih perlu saya cek manual di browser (kalau ada yang butuh
   visual judgment, mis. layout rusak di field kondisional kategori).

Jangan ubah `style.css` kecuali memang dibutuhkan untuk menampilkan field/state
baru yang sebelumnya tidak ada elemennya di HTML.
