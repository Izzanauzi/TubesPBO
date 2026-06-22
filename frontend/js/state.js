// js/state.js
// Single source of truth untuk status login (token, userId, role, username).
// Modul lain import { authState } dan baca langsung - binding ES module bersifat
// "live", jadi begitu setAuth()/clearAuth() dipanggil di sini, semua modul lain
// otomatis melihat nilai terbaru tanpa perlu getter manual.

const AUTH_KEY = 'bidkita_auth';
const EMPTY_AUTH = { token: null, userId: null, role: null, username: null };

export let authState = { ...EMPTY_AUTH };

export function loadAuth() {
  try {
    const raw = localStorage.getItem(AUTH_KEY);
    authState = raw ? JSON.parse(raw) : { ...EMPTY_AUTH };
  } catch (e) {
    authState = { ...EMPTY_AUTH };
  }
}

export function setAuth(next) {
  authState = next;
  try {
    localStorage.setItem(AUTH_KEY, JSON.stringify(authState));
  } catch (e) {
    // ignore
  }
}

export function clearAuth() {
  authState = { ...EMPTY_AUTH };
  try {
    localStorage.removeItem(AUTH_KEY);
  } catch (e) {
    // ignore
  }
}
