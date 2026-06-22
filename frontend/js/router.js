// js/router.js
// Mengatur perpindahan antar <section class="view">, guard role, dan render
// navbar. Setiap view module mendaftarkan loader-nya sendiri lewat
// registerLoader() saat di-init, jadi router.js TIDAK perlu tahu/import
// modul view manapun (menghindari circular import).

import { authState } from './state.js';
import { showBanner } from './utils.js';

export const VIEWS = [
  'auth', 'browse', 'detail', 'create-auction',
  'seller-dashboard', 'buyer-dashboard', 'wallet', 'notifications', 'admin'
];

const loaders = {};
const leaveHooks = [];

/** View module memanggil ini sekali saat init untuk daftarkan fungsi load datanya. */
export function registerLoader(viewName, fn) {
  loaders[viewName] = fn;
}

/**
 * Daftarkan cleanup yang dijalankan SEKALI saat user pindah ke view lain.
 * Dipakai mis. oleh detail.js untuk clearInterval() countdown saat keluar dari view detail.
 */
export function onLeaveView(fn) {
  leaveHooks.push(fn);
}

export function showView(name) {
  while (leaveHooks.length) leaveHooks.pop()();

  VIEWS.forEach((v) => {
    const sec = document.getElementById('view-' + v);
    if (sec) sec.classList.toggle('hidden', v !== name);
  });
  document.querySelectorAll('.nav-btn').forEach((b) => b.classList.remove('active'));
  const btn = document.getElementById('btn-' + name);
  if (btn) btn.classList.add('active');
  window.scrollTo(0, 0);

  if (loaders[name]) loaders[name]();
}

/** Guard: pastikan user login (dan opsional, punya role tertentu) sebelum lanjut. */
export function requireAuth(role) {
  if (!authState.token) {
    showBanner('Silakan login terlebih dahulu.', 'error');
    showView('auth');
    return false;
  }
  if (role && authState.role !== role) {
    showBanner('Fitur ini khusus untuk role ' + role + '.', 'error');
    showView('browse');
    return false;
  }
  return true;
}

export function renderNav() {
  const loggedIn = !!authState.token;
  document.getElementById('btn-login').classList.toggle('hidden', loggedIn);
  document.getElementById('nav-user').classList.toggle('hidden', !loggedIn);
  document.getElementById('btn-notifications').classList.toggle('hidden', !loggedIn);
  document.getElementById('btn-wallet').classList.toggle('hidden', authState.role !== 'BUYER');
  document.getElementById('btn-buyer-dashboard').classList.toggle('hidden', authState.role !== 'BUYER');
  document.getElementById('btn-seller-dashboard').classList.toggle('hidden', authState.role !== 'SELLER');
  document.getElementById('btn-create-auction').classList.toggle('hidden', authState.role !== 'SELLER');
  document.getElementById('btn-admin').classList.toggle('hidden', authState.role !== 'ADMIN');
  if (loggedIn) {
    document.getElementById('user-label').textContent = authState.username + ' \u00b7 ' + authState.role;
  }
}
