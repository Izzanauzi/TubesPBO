// js/views/notifications.js
// View 'notifications': daftar notifikasi user (login apapun, semua role).

import { apiFetch } from '../api.js';
import { showBanner, formatDateTime, el } from '../utils.js';
import { requireAuth, registerLoader } from '../router.js';

export function init() {
  registerLoader('notifications', loadNotifications);
}

async function loadNotifications() {
  if (!requireAuth()) return;
  const container = document.getElementById('notification-list');
  container.innerHTML = '<p>Memuat...</p>';
  try {
    const list = await apiFetch('/notifications');
    container.innerHTML = '';
    if (!list || list.length === 0) {
      container.innerHTML = '<p>Belum ada notifikasi.</p>';
      return;
    }
    list.forEach((n) => {
      container.appendChild(el(
        '<div class="list-item ' + (n.isRead ? '' : 'unread') + '">' +
          '<div class="info">' +
            '<div class="main">' + n.message + '</div>' +
            '<div class="sub">' + n.type + ' &middot; ' + formatDateTime(n.createdAt) + '</div>' +
          '</div>' +
        '</div>'
      ));
    });
  } catch (err) {
    container.innerHTML = '';
    showBanner(err.message, 'error');
  }
}
