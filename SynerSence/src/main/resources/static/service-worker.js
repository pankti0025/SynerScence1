const CACHE_NAME = "synersence-cache-v1";

const FILES_TO_CACHE = [
  "/",
  "/patients/new",
  "/settings",
  "/settings/customize",

  // CSS
  "/css/index.css",
  "/css/setting.css",
  "/css/field-customize.css",

  // JS
  "/js/index.js",
  "/js/setting.js",

  // PWA
  "/manifest.json"
];

// ================= INSTALL =================
self.addEventListener("install", event => {
  event.waitUntil(
    caches.open(CACHE_NAME).then(cache => {
      return cache.addAll(FILES_TO_CACHE);
    })
  );
  self.skipWaiting();
});

// ================= ACTIVATE =================
self.addEventListener("activate", event => {
  event.waitUntil(
    caches.keys().then(keys =>
      Promise.all(
        keys.map(key => {
          if (key !== CACHE_NAME) {
            return caches.delete(key);
          }
        })
      )
    )
  );
  self.clients.claim();
});

// ================= FETCH =================
self.addEventListener("fetch", event => {
  event.respondWith(
    fetch(event.request)
      .then(response => {
        // Cache new requests dynamically
        const clone = response.clone();
        caches.open(CACHE_NAME).then(cache => {
          cache.put(event.request, clone);
        });
        return response;
      })
      .catch(() => caches.match(event.request))
  );
});
