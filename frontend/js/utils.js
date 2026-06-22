// js/utils.js
// Helper murni (tidak menyimpan state apapun): notifikasi banner, format
// tampilan, dan pembuatan elemen DOM dari string HTML.

export function showBanner(message, type) {
  const bannerEl = document.getElementById('banner');
  bannerEl.textContent = message;
  bannerEl.className = 'banner ' + (type || 'error');
  clearTimeout(bannerEl._timer);
  bannerEl._timer = setTimeout(() => bannerEl.classList.add('hidden'), 4500);
}

export function formatRupiah(amount) {
  if (amount === null || amount === undefined) return '-';
  return 'Rp ' + Number(amount).toLocaleString('id-ID');
}

export function formatDateTime(value) {
  if (!value) return '-';
  const d = new Date(value);
  if (isNaN(d.getTime())) return String(value);
  return d.toLocaleString('id-ID');
}

export function formatDuration(totalSeconds) {
  const s = Math.max(0, Math.floor(totalSeconds));
  if (s <= 0) return 'Sudah berakhir';
  const h = Math.floor(s / 3600);
  const m = Math.floor((s % 3600) / 60);
  const sec = s % 60;
  return h + 'j ' + m + 'm ' + sec + 'd';
}

/** Buat satu elemen DOM dari string HTML, mis. el('<div>...</div>') */
export function el(html) {
  const tmp = document.createElement('div');
  tmp.innerHTML = html.trim();
  return tmp.firstChild;
}
