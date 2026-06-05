/**
 * @file salesReport.js
 * @description Obsługa list transakcji oraz sprzedaży produktów z funkcjami sortowania, filtrowania i przełączania widoków.
 */

let allTransactions = [];
let allSales = [];
let currentSort = { column: null, asc: true };
let currentSortSales = { column: null, asc: true };
let showingSales = false;

// ─── TRANSAKCJE ───────────────────────────────────────────

/**
 * Pobieranie listy transakcji z API oraz agregacja danych do formatu tabelarycznego.
 */
async function loadTransactions() {
    try {
        const res = await fetch(window.location.origin + "/MyParts/getSales");
        const rawSales = await res.json();

        const map = new Map();
        rawSales.forEach(s => {
            const tid = s.transactionId.transactionId;
            if (!map.has(tid)) {
                map.set(tid, {
                    id:   tid,
                    date: new Date(s.transactionId.date),
                    sum:  s.transactionId.transactionSum,
                    items: 0,
                    username: s.userId?.username || "Nieznany"
                });
            }
            map.get(tid).items++;
        });

        allTransactions = Array.from(map.values());
        renderTransactions(allTransactions);
    } catch (error) {
        console.error("Błąd ładowania:", error);
    }
}

/**
 * Renderowanie listy transakcji w tabeli HTML.
 * @param {Array} list - Lista transakcji do wyświetlenia.
 */
function renderTransactions(list) {
    const tbody = document.getElementById("products_table");
    tbody.innerHTML = "";
    const pad = n => String(n).padStart(2, '0');

    list.forEach(t => {
        const d = t.date;
        const formatted = `${d.getFullYear()}-${pad(d.getMonth()+1)}-${pad(d.getDate())} `
                        + `${pad(d.getHours())}:${pad(d.getMinutes())}:${pad(d.getSeconds())}`;
        tbody.innerHTML += `
            <tr>
                <td>${t.id}</td>
                <td>${formatted}</td>
                <td>${t.sum.toFixed(2)} zł</td>
                <td>${t.items}</td>
                <td>${t.username}</td>
                <td><a href="saleDetails.html?id=${t.id}">Szczegóły</a></td>
            </tr>`;
    });

    document.getElementById("sales_counter").textContent =
        `Wyświetlono ${list.length} z ${allTransactions.length} transakcji`;
}

/**
 * Filtrowanie transakcji według nazwy użytkownika.
 */
function filtrTransactions() {
    const user = document.getElementById("search_user").value.toLowerCase();
    let filtered = allTransactions.filter(t =>
        !user || t.username.toLowerCase().includes(user)
    );
    if (currentSort.column) sortArray(filtered, currentSort.column, currentSort.asc);
    renderTransactions(filtered);
}

/**
 * Obsługa sortowania kolumn w tabeli transakcji.
 * @param {string} column - Nazwa kolumny, według której ma nastąpić sortowanie.
 */
function columnSort(column) {
    currentSort.asc = currentSort.column === column ? !currentSort.asc : true;
    currentSort.column = column;
    document.querySelectorAll("#table_transactions thead th span").forEach(s => s.innerHTML = "↕");
    const thMap = { id: "th_id", date: "th_date", sum: "th_sum", items: "th_items", user: "th_user" };
    document.getElementById(thMap[column]).querySelector("span").innerHTML =
        currentSort.asc ? "↑" : "↓";
    filtrTransactions();
}

/**
 * Logika sortowania tablicy transakcji.
 * @param {Array} arr - Tablica do posortowania.
 * @param {string} column - Klucz sortowania.
 * @param {boolean} asc - Kierunek sortowania (true - rosnąco, false - malejąco).
 */
function sortArray(arr, column, asc) {
    arr.sort((a, b) => {
        let valA, valB;
        switch (column) {
            case "id":    valA = a.id;       valB = b.id;       break;
            case "date":  valA = a.date;     valB = b.date;     break;
            case "sum":   valA = a.sum;      valB = b.sum;      break;
            case "items": valA = a.items;    valB = b.items;    break;
            case "user":  valA = a.username; valB = b.username; break;
        }
        if (typeof valA === "string") return asc ? valA.localeCompare(valB) : valB.localeCompare(valA);
        return asc ? valA - valB : valB - valA;
    });
}

// ─── SPRZEDANE PRODUKTY ───────────────────────────────────

/**
 * Pobieranie danych o wszystkich sprzedanych produktach.
 */
async function loadSalesTable() {
    try {
        const res = await fetch(window.location.origin + "/MyParts/getSales");
        allSales = await res.json();
        populateCategories(allSales);
        renderSalesTable(allSales);
    } catch (error) {
        console.error("Błąd ładowania:", error);
    }
}

/**
 * Wypełnianie listy rozwijanej kategoriami produktów.
 * @param {Array} sales - Lista sprzedaży używana do wyodrębnienia unikalnych kategorii.
 */
function populateCategories(sales) {
    const select = document.getElementById("search_category");
    select.innerHTML = '<option value="">-- wszystkie --</option>';
    const categories = [...new Set(sales.map(s => s.productId.categoryId.name))];
    categories.forEach(cat => {
        const opt = document.createElement("option");
        opt.value = cat;
        opt.textContent = cat;
        select.appendChild(opt);
    });
}

/**
 * Renderowanie tabeli sprzedanych produktów.
 * @param {Array} sales - Lista sprzedaży do wyświetlenia.
 */
function renderSalesTable(sales) {
    const tbody = document.getElementById("sales_table");
    tbody.innerHTML = "";
    const pad = n => String(n).padStart(2, '0');

    sales.forEach(s => {
        const d = new Date(s.saleDate);
        const formatted = `${d.getFullYear()}-${pad(d.getMonth()+1)}-${pad(d.getDate())} `
                        + `${pad(d.getHours())}:${pad(d.getMinutes())}:${pad(d.getSeconds())}`;
        tbody.innerHTML += `
            <tr>
                <td>${s.productId.categoryId.name}</td>
                <td>${s.productId.name}</td>
                <td>${s.productId.brand}</td>
                <td>${s.productId.price.toFixed(2)} zł</td>
                <td>${s.quantity}</td>
                <td>${formatted}</td>
                <td><a href="saleDetails.html?id=${s.transactionId.transactionId}">Szczegóły</a></td>
            </tr>`;
    });

    document.getElementById("sales_counter").textContent =
        `Wyświetlono ${sales.length} z ${allSales.length} pozycji`;
}

/**
 * Filtrowanie listy sprzedaży na podstawie nazwy, kategorii i marki.
 */
function filtrSales() {
    const nazwa    = document.getElementById("search_name").value.toLowerCase();
    const kategoria = document.getElementById("search_category").value;
    const marka    = document.getElementById("search_brand").value.toLowerCase();

    let filtered = allSales.filter(s => {
        const matchName      = !nazwa      || s.productId.name.toLowerCase().includes(nazwa);
        const matchCategory = !kategoria || s.productId.categoryId.name === kategoria;
        const matchBrand    = !marka      || s.productId.brand.toLowerCase().includes(marka);
        return matchName && matchCategory && matchBrand;
    });

    if (currentSortSales.column) sortArraySales(filtered, currentSortSales.column, currentSortSales.asc);
    renderSalesTable(filtered);
}

/**
 * Obsługa sortowania kolumn w tabeli sprzedaży.
 * @param {string} column - Nazwa kolumny.
 */
function columnSortSales(column) {
    currentSortSales.asc = currentSortSales.column === column ? !currentSortSales.asc : true;
    currentSortSales.column = column;
    document.querySelectorAll("#table_sales thead th span").forEach(s => s.innerHTML = "↕");
    const thMap = {
        category: "th_category", name: "th_name", brand: "th_brand",
        price: "th_price", quantity: "th_quantity", date: "th_sale_date"
    };
    document.getElementById(thMap[column]).querySelector("span").innerHTML =
        currentSortSales.asc ? "↑" : "↓";
    filtrSales();
}

/**
 * Logika sortowania tablicy sprzedaży.
 * @param {Array} arr - Tablica do posortowania.
 * @param {string} column - Klucz sortowania.
 * @param {boolean} asc - Kierunek sortowania.
 */
function sortArraySales(arr, column, asc) {
    arr.sort((a, b) => {
        let valA, valB;
        switch (column) {
            case "category": valA = a.productId.categoryId.name; valB = b.productId.categoryId.name; break;
            case "name":     valA = a.productId.name;            valB = b.productId.name;            break;
            case "brand":    valA = a.productId.brand;           valB = b.productId.brand;           break;
            case "price":    valA = a.productId.price;           valB = b.productId.price;           break;
            case "quantity": valA = a.quantity;                  valB = b.quantity;                  break;
            case "date":     valA = new Date(a.saleDate);        valB = new Date(b.saleDate);        break;
        }
        if (typeof valA === "string") return asc ? valA.localeCompare(valB) : valB.localeCompare(valA);
        return asc ? valA - valB : valB - valA;
    });
}

// ─── PRZEŁĄCZNIK ──────────────────────────────────────────

/**
 * Przełączanie widoku między tabelą transakcji a tabelą sprzedanych przedmiotów.
 */
function toggleView() {
    showingSales = !showingSales;

    document.getElementById("table_transactions").style.display = showingSales ? "none" : "";
    document.getElementById("table_sales").style.display        = showingSales ? "" : "none";
    document.getElementById("filters_transactions").style.display = showingSales ? "none" : "";
    document.getElementById("filters_sales").style.display        = showingSales ? "" : "none";

    document.getElementById("toggle_btn").textContent = showingSales
        ? "Pokaż transakcje"
        : "Pokaż sprzedane przedmioty";

    if (showingSales && allSales.length === 0) {
        loadSalesTable();
    }

    document.getElementById("sales_counter").textContent = "";
}

// ─── INIT ─────────────────────────────────────────────────

/**
 * Inicjalizacja nasłuchiwaczy zdarzeń dla filtrów wyszukiwania.
 */
function initFilters() {
    document.getElementById("search_user").addEventListener("input", filtrTransactions);
    document.getElementById("search_name").addEventListener("input", filtrSales);
    document.getElementById("search_category").addEventListener("change", filtrSales);
    document.getElementById("search_brand").addEventListener("input", filtrSales);
}

/**
 * Uruchomienie skryptów po załadowaniu treści strony (sprawdzenie sesji i ról).
 */
window.addEventListener("DOMContentLoaded", async function () {
    if (await checkSession()) {
        drawNavbar();
        const loginInfo = await getLoginInfo();
        await checkRole(loginInfo);
        await loadTransactions();
        initFilters();
    }
});