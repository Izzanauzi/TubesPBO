// js/config.js
// Konfigurasi runtime: alamat dasar API backend, disimpan di localStorage
// supaya bisa diubah lewat tombol gear tanpa edit kode.

export const DEFAULT_API_BASE = 'http://localhost:8080/api';
const API_BASE_KEY = 'bidkita_api_base';

export function getApiBase() {
  try {
    return localStorage.getItem(API_BASE_KEY) || DEFAULT_API_BASE;
  } catch (e) {
    return DEFAULT_API_BASE;
  }
}

export function setApiBase(url) {
  try {
    localStorage.setItem(API_BASE_KEY, url);
  } catch (e) {
    // localStorage bisa tidak tersedia (mis. dibuka via file://) - aman diabaikan
  }
}
