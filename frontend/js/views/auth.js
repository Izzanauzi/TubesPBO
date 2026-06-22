// js/views/auth.js
// View 'auth': tab login/register. Tidak ada data async untuk di-load saat
// view ini ditampilkan, jadi tidak ada registerLoader() di sini.

import { apiFetch } from '../api.js';
import { setAuth, clearAuth } from '../state.js';
import { showBanner } from '../utils.js';
import { showView, renderNav } from '../router.js';

export function init() {
  initTabs();
  initForms();
}

function initTabs() {
  const tabLogin = document.getElementById('tab-login');
  const tabRegister = document.getElementById('tab-register');

  tabLogin.addEventListener('click', () => {
    tabLogin.classList.add('active');
    tabRegister.classList.remove('active');
    document.getElementById('login-form').classList.remove('hidden');
    document.getElementById('register-form').classList.add('hidden');
  });

  tabRegister.addEventListener('click', () => {
    tabRegister.classList.add('active');
    tabLogin.classList.remove('active');
    document.getElementById('register-form').classList.remove('hidden');
    document.getElementById('login-form').classList.add('hidden');
  });
}

function initForms() {
  document.getElementById('login-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const email = document.getElementById('login-email').value.trim();
    const password = document.getElementById('login-password').value;
    try {
      const res = await apiFetch('/auth/login', { method: 'POST', body: JSON.stringify({ email, password }) });
      setAuth({ token: res.accessToken, userId: res.userId, role: res.role, username: res.username });
      renderNav();
      showBanner('Login berhasil, halo ' + res.username + '!', 'success');
      showView('browse');
    } catch (err) {
      showBanner(err.message, 'error');
    }
  });

  document.getElementById('register-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const body = {
      username: document.getElementById('reg-username').value.trim(),
      email: document.getElementById('reg-email').value.trim(),
      password: document.getElementById('reg-password').value,
      phone: document.getElementById('reg-phone').value.trim(),
      role: document.getElementById('reg-role').value
    };
    try {
      await apiFetch('/auth/register', { method: 'POST', body: JSON.stringify(body) });
      showBanner('Pendaftaran berhasil! Silakan login.', 'success');
      document.getElementById('register-form').reset();
      document.getElementById('tab-login').click();
    } catch (err) {
      showBanner(err.message, 'error');
    }
  });

  document.getElementById('btn-logout').addEventListener('click', () => {
    clearAuth();
    renderNav();
    showBanner('Berhasil logout.', 'success');
    showView('browse');
  });
}
