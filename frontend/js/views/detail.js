// js/views/detail.js
// View 'detail': info lengkap satu lelang + aksi (bid, buy now, approve, delete)
// sesuai role yang sedang login. openDetail() diexport karena dipanggil dari
// browse.js, sellerDashboard.js, buyerDashboard.js, dan admin.js.

import { apiFetch } from '../api.js';
import { authState } from '../state.js';
import { showBanner, formatRupiah, formatDuration } from '../utils.js';
import { showView, registerLoader, onLeaveView } from '../router.js';

let currentDetailId = null;
let countdownInterval = null;

export function init() {
  document.getElementById('btn-back-browse').addEventListener('click', () => showView('browse'));
  registerLoader('detail', loadDetail);
}

export function openDetail(id) {
  currentDetailId = id;
  showView('detail');
}

async function loadDetail() {
  const container = document.getElementById('detail-card');
  container.innerHTML = '<p>Memuat...</p>';
  try {
    const a = await apiFetch('/auctions/' + currentDetailId);
    renderDetail(a);
  } catch (err) {
    container.innerHTML = '<p>Gagal memuat detail lelang.</p>';
    showBanner(err.message, 'error');
  }
}

function renderDetail(a) {
  const container = document.getElementById('detail-card');
  const thumb = a.imageBase64 ? '<img class="thumb-large" src="' + a.imageBase64 + '" alt="">' : '';

  let actionsHtml = '';
  const isOpenish = (a.status === 'OPEN' || a.status === 'CLOSING');

  if (authState.token && authState.role === 'BUYER' && isOpenish) {
    actionsHtml += '<form id="bid-form" class="form">' +
      '<label>Pasang Bid (lebih dari ' + formatRupiah(a.currentPrice) + ')</label>' +
      '<input type="number" id="bid-amount" min="' + (a.currentPrice + 1) + '" required>' +
      '<button type="submit" class="btn-primary">Pasang Bid</button>' +
      '</form>';
  }

  if (authState.token && authState.role === 'BUYER' && a.status === 'OPEN' && a.buyNowPrice) {
    actionsHtml += '<button class="btn-primary" id="btn-buynow" style="margin-top:12px;">Beli Sekarang (' + formatRupiah(a.buyNowPrice) + ')</button>';
  }

  if (authState.token && authState.role === 'ADMIN') {
    actionsHtml += '<div style="margin-top:16px; display:flex; gap:10px;">';
    if (a.status === 'PENDING') {
      actionsHtml += '<button class="btn-secondary" id="btn-approve">Setujui Lelang</button>';
    }
    actionsHtml += '<button class="btn-danger" id="btn-delete">Hapus Lelang</button></div>';
  }

  container.innerHTML =
    thumb +
    '<span class="badge cat">' + a.category + '</span> ' +
    '<span class="badge ' + a.status + '">' + a.status + '</span>' +
    '<h2>' + a.title + '</h2>' +
    '<div class="detail-grid">' +
      '<div><div class="label">Harga Saat Ini</div><div class="value">' + formatRupiah(a.currentPrice) + '</div></div>' +
      '<div><div class="label">Harga Beli Langsung</div><div class="value">' + (a.buyNowPrice ? formatRupiah(a.buyNowPrice) : '-') + '</div></div>' +
      '<div><div class="label">Penjual</div><div class="value">' + a.sellerName + '</div></div>' +
      '<div><div class="label">Total Bid</div><div class="value">' + a.totalBids + '</div></div>' +
      '<div><div class="label">Bid Tertinggi oleh</div><div class="value">' + (a.highestBidderUsername || '-') + '</div></div>' +
      '<div><div class="label">Sisa Waktu</div><div class="value countdown" id="detail-countdown">' + formatDuration(a.remainingTimeSeconds) + '</div></div>' +
    '</div>' +
    '<div id="detail-actions">' + actionsHtml + '</div>';

  startCountdown(a.remainingTimeSeconds);
  wireDetailActions(a);
}

function wireDetailActions(a) {
  const bidForm = document.getElementById('bid-form');
  if (bidForm) {
    bidForm.addEventListener('submit', async (e) => {
      e.preventDefault();
      const amount = Number(document.getElementById('bid-amount').value);
      try {
        await apiFetch('/auctions/' + currentDetailId + '/bid', { method: 'POST', body: JSON.stringify({ amount }) });
        showBanner('Bid berhasil dipasang!', 'success');
        loadDetail();
      } catch (err) {
        showBanner(err.message, 'error');
      }
    });
  }

  const buyNowBtn = document.getElementById('btn-buynow');
  if (buyNowBtn) {
    buyNowBtn.addEventListener('click', async () => {
      if (!confirm('Beli barang ini sekarang dengan harga ' + formatRupiah(a.buyNowPrice) + '?')) return;
      try {
        await apiFetch('/auctions/' + currentDetailId + '/buynow', { method: 'POST' });
        showBanner('Pembelian berhasil!', 'success');
        loadDetail();
      } catch (err) {
        showBanner(err.message, 'error');
      }
    });
  }

  const approveBtn = document.getElementById('btn-approve');
  if (approveBtn) {
    approveBtn.addEventListener('click', async () => {
      try {
        await apiFetch('/auctions/' + currentDetailId + '/approve', { method: 'PUT' });
        showBanner('Lelang disetujui.', 'success');
        loadDetail();
      } catch (err) {
        showBanner(err.message, 'error');
      }
    });
  }

  const deleteBtn = document.getElementById('btn-delete');
  if (deleteBtn) {
    deleteBtn.addEventListener('click', async () => {
      if (!confirm('Hapus lelang ini secara permanen?')) return;
      try {
        await apiFetch('/auctions/' + currentDetailId, { method: 'DELETE' });
        showBanner('Lelang dihapus.', 'success');
        showView('browse');
      } catch (err) {
        showBanner(err.message, 'error');
      }
    });
  }
}

function startCountdown(initialSeconds) {
  if (countdownInterval) clearInterval(countdownInterval);
  let remaining = Math.max(0, Math.floor(initialSeconds));
  countdownInterval = setInterval(() => {
    remaining = Math.max(0, remaining - 1);
    const node = document.getElementById('detail-countdown');
    if (!node) { clearInterval(countdownInterval); return; }
    node.textContent = formatDuration(remaining);
  }, 1000);
  onLeaveView(() => { if (countdownInterval) clearInterval(countdownInterval); });
}
