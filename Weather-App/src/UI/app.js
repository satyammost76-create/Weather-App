// 🕒 Live clock
setInterval(() => {
  const now = new Date();
  document.getElementById("clock").textContent = now.toLocaleString();
}, 1000);

async function getForecast() {
  stopAllSounds(); // stop previous sounds
  const city = document.getElementById("city").value || "BIHAR";
  const days = document.getElementById("days").value || 3;
  const main = document.getElementById("main");

  main.innerHTML = `<div class="loading">Fetching weather for <b>${city}</b>...</div>`;

  try {
    const res = await fetch(`http://localhost:8080/weather/forecast?city=${city}&days=${days}`);
    if (!res.ok) throw new Error("API not responding");
    const data = await res.json();

    const w = data.weatherResponse;
    const condition = w.condition.toLowerCase();

    // Background + Sound 🎵
    const body = document.body;
    if (condition.includes("rain")) {
      body.style.background = "var(--rainy)";
      playSound("rainSound");
    } else if (condition.includes("thunder")) {
      body.style.background = "var(--rainy)";
      playSound("thunderSound");
    } else if (condition.includes("cloud") || condition.includes("mist")) {
      body.style.background = "var(--cloudy)";
      playSound("windSound");
    } else if (new Date().getHours() >= 18 || new Date().getHours() < 6) {
      body.style.background = "var(--night)";
      playSound("windSound");
    } else {
      body.style.background = "var(--day)";
      playSound("sunnySound");
    }

    // ☁️ Main weather box
    const icon = getWeatherIcon(condition);
    const summary = `
      <div class="weather-box">
        <img src="${icon}" alt="icon" />
        <h2>${w.city}, ${w.country}</h2>
        <p>${w.condition}</p>
        <div class="temp">${w.temperature}°C</div>
        <p>${w.region}</p>
      </div>
    `;

    // 📅 Forecast cards
    const forecast = data.daytamp.map(day => `
      <div class="day-card">
        <h4>${day.date}</h4>
        <p>🌡️ Max: ${day.maxtemp}°C</p>
        <p>❄️ Min: ${day.mintemp}°C</p>
        <p>☁️ Avg: ${day.avgtamp}°C</p>
      </div>
    `).join('');

    main.innerHTML = summary + `<div class="forecast-grid">${forecast}</div>`;
  } catch (e) {
    main.innerHTML = `<div class="loading" style="color:red;">❌ ${e.message}<br>Check backend/CORS.</div>`;
  }
}

// 📍 Use GPS location
function useLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      (pos) => {
        const lat = pos.coords.latitude;
        const lon = pos.coords.longitude;
        document.getElementById("city").value = `lat=${lat},lon=${lon}`;
        getForecast(lat, lon);
      },
      () => alert("Location permission denied ❌")
    );
  } else {
    alert("Geolocation not supported.");
  }
}

// ☁️ Weather icons
function getWeatherIcon(condition) {
  if (condition.includes("sun") || condition.includes("clear"))
    return "https://cdn-icons-png.flaticon.com/512/869/869869.png";
  if (condition.includes("rain"))
    return "https://cdn-icons-png.flaticon.com/512/1163/1163624.png";
  if (condition.includes("mist") || condition.includes("fog"))
    return "https://cdn-icons-png.flaticon.com/512/4005/4005901.png";
  if (condition.includes("cloud"))
    return "https://cdn-icons-png.flaticon.com/512/414/414825.png";
  if (condition.includes("snow"))
    return "https://cdn-icons-png.flaticon.com/512/642/642102.png";
  return "https://cdn-icons-png.flaticon.com/512/1779/1779940.png";
}

// 🎵 Play/Stop weather sounds
function playSound(id) {
  stopAllSounds();
  const sound = document.getElementById(id);
  if (sound) {
    sound.volume = 0.3;
    sound.play();
  }
}

function stopAllSounds() {
  ["rainSound", "sunnySound", "windSound", "thunderSound"].forEach(id => {
    const s = document.getElementById(id);
    if (s) s.pause();
  });
}
