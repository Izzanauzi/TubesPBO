// js/views/admin.js
// View 'admin': tab Lelang (approve/delete via detail) dan tab Pengguna.

import { apiFetch } from '../api.js';
import { showBanner, formatRupiah, el } from '../utils.js';
import { requireAuth, registerLoader } from '../router.js';
import { openDetail } from './detail.js';

export function init() {
  registerLoader('admin', loadAdminAuctions);

  document.getElementById('admin-tab-auctions').addEventListener('click', () => {
    document.getElementById('admin-tab-auctions').classList.add('active');
    document.getElementById('admin-tab-users').classList.remove('active');
    document.getElementById('admin-auctions').classList.remove('hidden');
    document.getElementById('admin-users').classList.add('hidden');
    loadAdminAuctions();
  });

  document.getElementById('admin-tab-users').addEventListener('click', () => {
    document.getElementById('admin-tab-users').classList.add('active');
    document.getElementById('admin-tab-auctions').classList.remove('active');
    document.getElementById('admin-users').classList.remove('hidden');
    document.getElementById('admin-auctions').classList.add('hidden');
    loadAdminUsers();
  });
}

async function loadAdminAuctions() {
  if (!requireAuth('ADMIN')) return;
  const container = document.getElementById('admin-auctions');
  container.innerHTML = '<p>Memuat...</p>';
  try {
    const list = await apiFetch('/admin/auctions');
    container.innerHTML = '';
    if (!list || list.length === 0) {
      container.innerHTML = '<p>Belum ada lelang.</p>';
      return;
    }
    list.forEach((a) => {
      const item = el(
        '<div class="list-item">' +
          '<div class="info">' +
            '<div class="main">' + a.title + ' <span class="badge ' + a.status + '">' + a.status + '</span></div>' +
            '<div class="sub">' + formatRupiah(a.currentPrice) + ' &middot; penjual: ' + a.sellerName + '</div>' +
          '</div>' +
          '<div class="actions"><button class="btn-secondary">Detail / Kelola</button></div>' +
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

async function loadAdminUsers() {
  if (!requireAuth('ADMIN')) return;
  const container = document.getElementById('admin-users');
  container.innerHTML = '<p>Memuat...</p>';
  try {
    const list = await apiFetch('/admin/users');
    container.innerHTML = '';
    if (!list || list.length === 0) {
      container.innerHTML = '<p>Belum ada pengguna.</p>';
      return;
    }
    list.forEach((u) => {
      container.appendChild(el(
        '<div class="list-item">' +
          '<div class="info">' +
            '<div class="main">' + (u.username || '-') + ' <span class="badge cat">' + (u.role || '-') + '</span></div>' +
            '<div class="sub">' + (u.email || '-') + '</div>' +
          '</div>' +
        '</div>'
      ));
    });
  } catch (err) {
    container.innerHTML = '';
    showBanner(err.message, 'error');
  }
}
