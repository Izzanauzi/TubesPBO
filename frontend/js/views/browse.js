// js/views/browse.js
// View 'browse': grid lelang + filter kategori/status/harga.

import { apiFetch } from '../api.js';
import { showBanner, formatRupiah, formatDuration, el } from '../utils.js';
import { registerLoader } from '../router.js';
import { openDetail } from './detail.js';

export function init() {
  document.getElementById('btn-apply-filter').addEventListener('click', loadBrowse);
  document.getElementById('btn-reset-filter').addEventListener('click', () => {
    document.getElementById('filter-category').value = '';
    document.getElementById('filter-status').value = '';
    document.getElementById('filter-min').value = '';
    document.getElementById('filter-max').value = '';
    loadBrowse();
  });
  registerLoader('browse', loadBrowse);
}

async function loadBrowse() {
  const grid = document.getElementById('auction-grid');
  grid.innerHTML = '<p>Memuat...</p>';
  const params = new URLSearchParams();
  const cat = document.getElementById('filter-category').value;
  const status = document.getElementById('filter-status').value;
  const min = document.getElementById('filter-min').value;
  const max = document.getElementById('filter-max').value;
  if (cat) params.set('category', cat);
  if (status) params.set('status', status);
  if (min) params.set('minPrice', min);
  if (max) params.set('maxPrice', max);

  try {
    const list = await apiFetch('/auctions' + (params.toString() ? '?' + params.toString() : ''));
    grid.innerHTML = '';
    if (!list || list.length === 0) {
      grid.innerHTML = '<p>Belum ada lelang yang cocok dengan filter ini.</p>';
      return;
    }
    list.forEach((a) => grid.appendChild(renderAuctionCard(a)));
  } catch (err) {
    grid.innerHTML = '';
    showBanner(err.message, 'error');
  }
}

function renderAuctionCard(a) {
  const thumb = a.imageBase64
    ? '<img class="thumb" src="' + a.imageBase64 + '" alt="">'
    : '<div class="thumb-placeholder">Tanpa foto</div>';
  const card = el(
    '<div class="auction-card">' +
      thumb +
      '<div class="body">' +
        '<span class="badge cat">' + a.category + '</span> ' +
        '<span class="badge ' + a.status + '">' + a.status + '</span>' +
        '<div class="title">' + a.title + '</div>' +
        '<div class="price">' + formatRupiah(a.currentPrice) + '</div>' +
        '<div class="meta">' + a.totalBids + ' bid &middot; oleh ' + a.sellerName + '</div>' +
        '<div class="meta">Sisa: ' + formatDuration(a.remainingTimeSeconds) + '</div>' +
        '<button class="btn-secondary">Lihat Detail</button>' +
      '</div>' +
    '</div>'
  );
  card.querySelector('button').addEventListener('click', () => openDetail(a.auctionId));
  return card;
}
