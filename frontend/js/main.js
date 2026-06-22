// js/main.js
// Entry point tunggal. Tugasnya hanya: load auth dari localStorage, render
// navbar, panggil init() tiap view module (yang otomatis mendaftarkan
// loader-nya ke router via registerLoader), lalu tampilkan view awal.
//
// Untuk menambah view baru: buat js/views/namaView.js dengan pola yang sama
// (export function init() { registerLoader('nama-view', loadFn); ... }),
// lalu import + panggil init()-nya di bawah.

import { loadAuth } from './state.js';
import { renderNav, showView } from './router.js';
import { getApiBase, setApiBase } from './config.js';
import { showBanner } from './utils.js';

import * as authView from './views/auth.js';
import * as browseView from './views/browse.js';
import * as detailView from './views/detail.js';
import * as createAuctionView from './views/createAuction.js';
import * as sellerDashboardView from './views/sellerDashboard.js';
import * as buyerDashboardView from './views/buyerDashboard.js';
import * as walletView from './views/wallet.js';
import * as notificationsView from './views/notifications.js';
import * as adminView from './views/admin.js';

function initGlobalNav() {
  document.querySelectorAll('.nav-btn').forEach((btn) => {
    btn.addEventListener('click', () => showView(btn.dataset.view));
  });
  document.getElementById('btn-settings').addEventListener('click', () => {
    const current = getApiBase();
    const next = prompt('Alamat dasar API backend:', current);
    if (next && next.trim()) {
      setApiBase(next.trim());
      showBanner('API base diubah ke ' + next.trim(), 'success');
    }
  });
}

document.addEventListener('DOMContentLoaded', () => {
  loadAuth();
  renderNav();

  initGlobalNav();
  authView.init();
  browseView.init();
  detailView.init();
  createAuctionView.init();
  sellerDashboardView.init();
  buyerDashboardView.init();
  walletView.init();
  notificationsView.init();
  adminView.init();

  showView('browse');
});
