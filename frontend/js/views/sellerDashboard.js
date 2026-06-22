// js/views/sellerDashboard.js
// View 'seller-dashboard': daftar lelang milik Seller yang sedang login.

import { apiFetch } from '../api.js';
import { showBanner, formatRupiah, formatDuration, el } from '../utils.js';
import { requireAuth, registerLoader } from '../router.js';
import { openDetail } from './detail.js';

export function init() {
  registerLoader('seller-dashboard', loadSellerDashboard);
}

async function loadSellerDashboard() {
  if (!requireAuth('SELLER')) return;
  const container = document.getElementById('seller-auction-list');
  container.innerHTML = '<p>Memuat...</p>';
  try {
    const list = await apiFetch('/seller/auctions');
    container.innerHTML = '';
    if (!list || list.length === 0) {
      container.innerHTML = '<p>Belum ada lelang yang kamu buat.</p>';
      return;
    }
    list.forEach((a) => {
      const item = el(
        '<div class="list-item">' +
          '<div class="info">' +
            '<div class="main">' + a.title + ' <span class="badge ' + a.status + '">' + a.status + '</span></div>' +
            '<div class="sub">' + formatRupiah(a.currentPrice) + ' &middot; ' + a.totalBids + ' bid &middot; sisa ' + formatDuration(a.remainingTimeSeconds) + '</div>' +
          '</div>' +
          '<div class="actions"><button class="btn-secondary">Lihat Detail</button></div>' +
        '</div>'
      );
      item.querySelector('button').addEventListener('click', () => openDetail(a.auctionId));
      container.appendChild(item);
    });
  } catch (err) {
    container.innerHTML = '';
    showBanner(err.message, 'error');
  }
}
