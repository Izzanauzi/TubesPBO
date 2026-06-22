// js/views/createAuction.js
// View 'create-auction': form Buat Lelang (khusus SELLER), field kondisional
// per kategori (Elektronik/Fashion/Buku/Kendaraan) sesuai CreateAuctionRequestDTO.

import { apiFetch } from '../api.js';
import { showBanner } from '../utils.js';
import { showView, requireAuth } from '../router.js';

const CATEGORIES = ['ELEKTRONIK', 'FASHION', 'BUKU', 'KENDARAAN'];
let uploadedImageData = null;

export function init() {
  const categorySelect = document.getElementById('ca-category');

  function toggleFields() {
    const selected = categorySelect.value;
    CATEGORIES.forEach((cat) => {
      document.getElementById('fields-' + cat).classList.toggle('hidden', cat !== selected);
    });
  }
  categorySelect.addEventListener('change', toggleFields);
  toggleFields();

  document.getElementById('ca-image').addEventListener('change', (e) => {
    const file = e.target.files[0];
    if (!file) { uploadedImageData = null; return; }
    const reader = new FileReader();
    reader.onload = () => {
      uploadedImageData = reader.result;
      const preview = document.getElementById('ca-image-preview');
      preview.src = uploadedImageData;
      preview.classList.remove('hidden');
    };
    reader.readAsDataURL(file);
  });

  document.getElementById('create-auction-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    if (!requireAuth('SELLER')) return;

    const category = categorySelect.value;
    const dto = buildDto(category);

    try {
      await apiFetch('/auctions', { method: 'POST', body: JSON.stringify(dto) });
      showBanner('Lelang berhasil dipublikasikan!', 'success');
      document.getElementById('create-auction-form').reset();
      uploadedImageData = null;
      document.getElementById('ca-image-preview').classList.add('hidden');
      toggleFields();
      showView('seller-dashboard');
    } catch (err) {
      showBanner(err.message, 'error');
    }
  });
}

function buildDto(category) {
  const dto = {
    category,
    title: document.getElementById('ca-title').value.trim(),
    description: document.getElementById('ca-description').value.trim(),
    condition: document.getElementById('ca-condition').value,
    imageBase64: uploadedImageData,
    startingPrice: Number(document.getElementById('ca-starting-price').value),
    duration: Number(document.getElementById('ca-duration').value)
  };
  const buyNowVal = document.getElementById('ca-buynow-price').value;
  if (buyNowVal) dto.buyNowPrice = Number(buyNowVal);

  if (category === 'ELEKTRONIK') {
    dto.brand = document.getElementById('ca-elec-brand').value;
    dto.type = document.getElementById('ca-elec-type').value;
    dto.warrantyStatus = document.getElementById('ca-elec-warranty').checked;
  } else if (category === 'FASHION') {
    dto.brand = document.getElementById('ca-fashion-brand').value;
    dto.size = document.getElementById('ca-fashion-size').value;
    dto.material = document.getElementById('ca-fashion-material').value;
  } else if (category === 'BUKU') {
    dto.author = document.getElementById('ca-book-author').value;
    dto.publisher = document.getElementById('ca-book-publisher').value;
    const yr = document.getElementById('ca-book-year').value;
    if (yr) dto.publishYear = Number(yr);
  } else if (category === 'KENDARAAN') {
    dto.brand = document.getElementById('ca-vehicle-brand').value;
    const yr = document.getElementById('ca-vehicle-year').value;
    if (yr) dto.year = Number(yr);
    const mileage = document.getElementById('ca-vehicle-mileage').value;
    if (mileage) dto.mileage = Number(mileage);
    dto.platNumber = document.getElementById('ca-vehicle-plat').value;
  }
  return dto;
}
