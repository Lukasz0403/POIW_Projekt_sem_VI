/**
 * @file salesReportMain.js
 * @description Obsługa logiki generowania raportów sprzedażowych w aplikacji MyParts.
 */

/** @type {Array} Globalna tablica przechowująca wszystkie pobrane dane sprzedażowe. */
let salesData = [];

/**
 * Inicjalizacja sesji, pobieranie danych z API oraz ustawianie widoku początkowego strony.
 */
window.onload = async function() {
    let status = await checkSession();
    if(status) {
        let roleInfo = await getLoginInfo();
        drawNavbar();
        checkRole(roleInfo);
        
        const res = await fetch("/MyParts/getSales");
        salesData = await res.json();
        
        initDateSelector();
        updateReport(); 
    }
}

/**
 * Inicjalizacja selektora daty (input type="month") z ustawieniem na bieżący miesiąc.
 */
function initDateSelector() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    document.getElementById("monthPicker").value = `${year}-${month}`;
}

/**
 * Główna aktualizacja zawartości strony na podstawie wybranego miesiąca.
 * Czyszczenie i przeliczanie statystyk, odświeżanie tabeli oraz wykresu.
 */
async function updateReport() {
    const picker = document.getElementById("monthPicker");
    const val = picker.value;
    
    if (val && val < "2000-01") {
        picker.value = "2000-01";
        updateReport();
        return;
    }

    if (!val) return;
    
    const [year, month] = val.split('-').map(Number);
    const targetMonth = month - 1;
    
    const filteredSales = salesData.filter(s => {
        const d = new Date(s.saleDate);
        return d.getMonth() === targetMonth && d.getFullYear() === year;
    });

    computeSummary(filteredSales);
    
    if (document.getElementById("products_table")) {
        renderSalesMAIN(filteredSales);
    }
    
    buildChart(filteredSales, year, targetMonth);
    
    const monthName = new Date(year, targetMonth).toLocaleString('pl-PL', { month: 'long' });
    const fullLabel = `${monthName} ${year}`;
    
    const header = document.querySelector(".summary-header h3");
    if (header) header.textContent = `Dane sprzedażowe z miesiąca: ${fullLabel}`;
    
    const monthSpan = document.getElementById("month");
    if (monthSpan) monthSpan.textContent = fullLabel;
}

/**
 * Przeliczanie sumarycznych danych sprzedażowych dla wybranego zakresu.
 * @param {Array} sales - Tablica obiektów sprzedaży do przetworzenia.
 */
function computeSummary(sales) {
    const totalItems = sales.reduce((sum, s) => sum + s.quantity, 0);
    const totalSales = sales.reduce((sum, s) => sum + (s.productId.price * s.quantity), 0);

    const itemsEl = document.getElementById("total_items");
    const salesEl = document.getElementById("total_sales");
    
    if (itemsEl) itemsEl.textContent = totalItems;
    if (salesEl) salesEl.textContent = totalSales.toFixed(2) + " zł";
}

/**
 * Renderowanie tabeli produktów w głównym panelu.
 * @param {Array} sales - Lista transakcji do wyświetlenia w tabeli.
 */
function renderSalesMAIN(sales) {
    const table = document.getElementById("products_table");
    if (!table) return;
    
    table.innerHTML = "";
    sales.forEach(p => {
        const date = new Date(p.saleDate);
        const d = String(date.getDate()).padStart(2, '0');
        const m = String(date.getMonth() + 1).padStart(2, '0');
        const y = date.getFullYear();
        const time = date.toLocaleTimeString('pl-PL');

        table.innerHTML += `
            <tr>
                <td>${p.productId.categoryId.name}</td>
                <td>${p.productId.name}</td>
                <td>${p.productId.brand}</td>
                <td>${p.productId.price} zł</td>
                <td>${p.quantity}</td>
                <td>${y}-${m}-${d} ${time}</td>
            </tr>
        `;
    });
}

/**
 * Generowanie i rysowanie wykresu słupkowego sprzedaży przy użyciu biblioteki Chart.js.
 * @param {Array} sales - Lista transakcji.
 * @param {number} year - Rok wyświetlanego raportu.
 * @param {number} month - Miesiąc wyświetlanego raportu (0-11).
 */
function buildChart(sales, year, month) {
    const ctx = document.getElementById('salesChart');
    if (!ctx) return;

    const existingChart = Chart.getChart("salesChart");
    if (existingChart) existingChart.destroy();

    const daysInMonth = new Date(year, month + 1, 0).getDate();
    const dailyTotals = Array(daysInMonth).fill(0);

    sales.forEach(s => {
        const d = new Date(s.saleDate);
        if (d.getMonth() === month && d.getFullYear() === year) {
            dailyTotals[d.getDate() - 1] += s.productId.price * s.quantity;
        }
    });

    const labels = Array.from({length: daysInMonth}, (_, i) => i + 1);

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Sprzedaż (zł)',
                data: dailyTotals,
                backgroundColor: '#cc0000'
            }]
        },
        options: { responsive: true }
    });
}

/**
 * Przekierowywanie użytkownika do strony listy transakcji.
 */
function goReport() { window.location.href = "salesReport.html"; }

/**
 * Wywoływanie standardowego okna drukowania przeglądarki.
 */
function printPDF() { window.print(); }