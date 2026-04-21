let allSales = []; // Tutaj trzymamy dane z bazy

// Ładowanie danych i kategorii
async function loadSales() {
    try {
        const res = await fetch("/MyParts/getSales");
        allSales = await res.json();
        
        populateCategories(allSales); // Automatyczne wypełnienie listy kategorii
        renderSales(allSales);
    } catch (error) {
        console.error("Błąd ładowania:", error);
    }
}

// Funkcja pomocnicza do wypełnienia selecta kategoriami
function populateCategories(sales) {
    const select = document.getElementById("kategoria");
    const categories = [...new Set(sales.map(s => s.productId.categoryId.name))];
    
    categories.forEach(cat => {
        const opt = document.createElement("option");
        opt.value = cat;
        opt.textContent = cat;
        select.appendChild(opt);
    });
}

// Główna funkcja filtrująca i sortująca
function filtr() {
    const kategoria = document.getElementById("kategoria").value;
    const nazwa = document.getElementById("search_name").value.toLowerCase();
    const sort = document.getElementById("sort_select").value;

    // FILTROWANIE
    let filtered = allSales.filter(s => {
        const product = s.productId;
        
        // Sprawdź kategorię (jeśli pusta, przepuść wszystko)
        const matchCategory = !kategoria || product.categoryId.name === kategoria;
        
        // Sprawdź nazwę (jeśli pusta, przepuść wszystko)
        const matchName = !nazwa || product.name.toLowerCase().includes(nazwa);

        return matchCategory && matchName;
    });

    // SORTOWANIE
    if (sort === "name_asc") {
        filtered.sort((a, b) => a.productId.name.localeCompare(b.productId.name));
    } else if (sort === "name_desc") {
        filtered.sort((a, b) => b.productId.name.localeCompare(a.productId.name));
    } else if (sort === "price_asc") {
        filtered.sort((a, b) => a.productId.price - b.productId.price);
    } else if (sort === "price_desc") {
        filtered.sort((a, b) => b.productId.price - a.productId.price);
    } else if (sort === "date_asc") {
        filtered.sort((a, b) => new Date(a.saleDate) - new Date(b.saleDate));
    } else if (sort === "date_desc") {
        filtered.sort((a, b) => new Date(b.saleDate) - new Date(a.saleDate));
    }

    renderSales(filtered);
}

// Renderowanie tabeli
function renderSales(sales) {
    const table = document.getElementById("products_table");
    table.innerHTML = "";
    const pad = (n) => String(n).padStart(2, '0');

    sales.forEach(s => {
        const d = new Date(s.saleDate);
        const formattedDate = `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())} ${pad(d.getHours())}:${pad(d.getMinutes())}:${pad(d.getSeconds())}`;

        table.innerHTML += `
            <tr>
                <td>${s.productId.categoryId.name}</td>
                <td>${s.productId.name}</td>
                <td>${s.productId.brand}</td>
                <td>${s.productId.price.toFixed(2)} zł</td>
                <td>${s.quantity}</td>
                <td>${formattedDate}</td>
            </tr>
        `;
    });
}

// Inicjalizacja zdarzeń
function initFilters() {
    document.getElementById("search_name").addEventListener("input", filtr);
    document.getElementById("kategoria").addEventListener("change", filtr);
    document.getElementById("sort_select").addEventListener("change", filtr);
}

window.onload = async function() {
    let status = await checkSession();
    if (status) {
        drawNavbar();
        await loadSales();
        initFilters();
    }
};