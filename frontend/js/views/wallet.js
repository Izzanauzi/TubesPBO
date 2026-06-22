// js/views/wallet.js
// View 'wallet': saldo, riwayat transaksi, dan form top up (khusus BUYER).

import { apiFetch } from '../api.js';
import { showBanner, formatRupiah, formatDateTime, el } from '../utils.js';
import { requireAuth, registerLoader } from '../router.js';

export function init() {
  registerLoader('wallet', loadWallet);

  document.getElementById('topup-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    if (!requireAuth('BUYER')) return;
    const amount = Number(document.getElementById('topup-amount').value);
    const paymentMethod = document.getElementById('topup-method').value;
    try {
      await apiFetch('/wallet/topup', { method: 'POST', body: JSON.stringify({ amount, paymentMethod }) });
      showBanner('Top up berhasil!', 'success');
      document.getElementById('topup-form').reset();
      loadWallet();
    } catch (err) {
      showBanner(err.message, 'error');
    }
  });
}

async function loadWallet() {
  if (!requireAuth('BUYER')) return;
  const balanceEl = document.getElementById('wallet-balance');
  const txEl = document.getElementById('wallet-transactions');
  balanceEl.innerHTML = 'Memuat...';
  txEl.innerHTML = '';
  try {
    const wallet = await apiFetch('/wallet');
    balanceEl.innerHTML = '<span class="label">Saldo Saat Ini</span>' + formatRupiah(wallet.balance);
    const txs = wallet.transactions || [];
    if (txs.length === 0) {
      txEl.innerHTML = '<p>Belum ada transaksi.</p>';
    } else {
      txs.slice().reverse().forEach((t) => {
        txEl.appendChild(el(
          '<div class="list-item">' +
            '<div class="info">' +
              '<div class="main">' + t.type + '</div>' +
              '<div class="sub">' + formatDateTime(t.createdAt) + '</div>' +
            '</div>' +
            '<div class="value">' + formatRupiah(t.amount) + '</div>' +
          '</div>'
        ));
      });
    }
  } catch (err) {
    balanceEl.innerHTML = '-';
    showBanner(err.message, 'error');
  }
}
