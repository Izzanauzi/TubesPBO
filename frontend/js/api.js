// js/api.js
// Satu pintu untuk semua panggilan ke backend BidKita.
// Otomatis menyisipkan JWT (jika ada) dan melempar Error dengan message
// dari body JSON backend (sesuai format GlobalExceptionHandler: {"message": "..."})

import { getApiBase } from './config.js';
import { authState, clearAuth } from './state.js';
import { renderNav, showView } from './router.js';
import { showBanner } from './utils.js';

export async function apiFetch(path, options = {}) {
  const headers = Object.assign({ 'Content-Type': 'application/json' }, options.headers || {});
  if (authState.token) headers['Authorization'] = 'Bearer ' + authState.token;

  let res;
  try {
    res = await fetch(getApiBase() + path, Object.assign({}, options, { headers }));
  } catch (err) {
    throw new Error(
      'Tidak bisa terhubung ke ' + getApiBase() + '. Pastikan backend jalan dan CORS mengizinkan origin ini.'
    );
  }

  const text = await res.text();
  let data = null;
  if (text) {
    try { data = JSON.parse(text); } catch (e) { data = text; }
  }

  if (!res.ok) {
    const msg = (data && typeof data === 'object' && data.message) ? data.message : ('HTTP ' + res.status);
    const err = new Error(msg);
    err.status = res.status;
    if (res.status === 401) {
      clearAuth();
      renderNav();
      showView('auth');
    } else if (res.status === 403) {
      showBanner(msg, 'error');
    }
    throw err;
  }
  return data;
}
