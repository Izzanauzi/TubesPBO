// js/views/buyerDashboard.js
// View 'buyer-dashboard': riwayat bid milik Buyer yang sedang login.

import { apiFetch } from '../api.js';
import { showBanner, formatRupiah, formatDateTime, el } from '../utils.js';
import { requireAuth, registerLoader } from '../router.js';
import { openDetail } from './detail.js';

export function init() {
  registerLoader('buyer-dashboard', loadBuyerDashboard);
}

async function loadBuyerDashboard() {
  if (!requireAuth('BUYER')) return;
  const container = document.getElementById('buyer-bid-list');
  container.innerHTML = '<p>Memuat...</p>';
  try {
    const list = await apiFetch('/buyer/bids');
    container.innerHTML = '';
    if (!list || list.length === 0) {
      container.innerHTML = '<p>Kamu belum pernah memasang bid.</p>';
      return;
    }
    list.forEach((b) => {
      const winBadge = b.isWinning ? '<span class="badge OPEN">Tertinggi</span>' : '<span class="badge CANCELLED">Kalah</span>';
      const item = el(
        '<div class="list-item">' +
          '<div class="info">' +
            '<div class="main">' + b.auctionTitle + ' ' + winBadge + '</div>' +
            '<div class="sub">Bid kamu: ' + formatRupiah(b.amount) + ' &middot; ' + formatDateTime(b.timestamp) + ' &middot; status lelang: ' + b.auctionStatus + '</div>' +
          '</div>' +
          '<div class="actions"><button class="btn-secondary">Lihat Lelang</button></div>' +
        '</div>'
      );
      item.querySelector('button').addEventListener('click', () => openDetail(b.auctionId));
      container.appendChild(item);
    });
  } catch (err) {
    container.innerHTML = '';
    showBanner(err.message, 'error');
  }
}
