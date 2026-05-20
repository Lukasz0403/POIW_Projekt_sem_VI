let allSales = [];
let currentSort = { column: null, asc: true };

// Ładowanie danych i kategorii
async function loadSales() {
    try {
        const res = await fetch("/MyParts/getSales");
        allSales = await res.json();
        populateCategories(allSales);
        renderSales(allSales);
    } catch (error) {
        console.error("Błąd ładowania:", error);
    }
}

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
                <td><a href="saleDetails.html?id=${s.transactionId.transactionId}">Szczegóły</a></td>
            </tr>
        `;
    });

    document.getElementById("sales_counter").textContent =
        `Wyświetlono ${sales.length} z ${allSales.length} transakcji`;
}

function filtr() {
    const kategoria = document.getElementById("kategoria").value;
    const nazwa = document.getElementById("search_name").value.toLowerCase();

    let filtered = allSales.filter(s => {
        const matchCategory = !kategoria || s.productId.categoryId.name === kategoria;
        const matchName = !nazwa || s.productId.name.toLowerCase().includes(nazwa);
        return matchCategory && matchName;
    });

    if (currentSort.column) {
        sortArray(filtered, currentSort.column, currentSort.asc);
    }

    renderSales(filtered);
}

function columnSort(column) {
    if (currentSort.column === column) {
        currentSort.asc = !currentSort.asc;
    } else {
        currentSort.column = column;
        currentSort.asc = true;
    }

    // Resetuj wszystkie strzałki
    document.querySelectorAll("thead th span").forEach(s => s.innerHTML = "↕");

    // Ustaw strzałkę na klikniętej kolumnie
    const thMap = {
        category: "th_category",
        name: "th_name",
        brand: "th_brand",
        price: "th_price",
        quantity: "th_quantity",
        date: "th_date"
    };
    document.getElementById(thMap[column]).querySelector("span").innerHTML =
        currentSort.asc ? "↑" : "↓";

    filtr();
}

function sortArray(arr, column, asc) {
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
        if (typeof valA === "string") {
            return asc ? valA.localeCompare(valB) : valB.localeCompare(valA);
        }
        return asc ? valA - valB : valB - valA;
    });
}

function initFilters() {
    document.getElementById("search_name").addEventListener("input", filtr);
    document.getElementById("kategoria").addEventListener("change", filtr);
}

window.onload = async function() {
    let status = await checkSession();
    if (status) {
        drawNavbar();
        await loadSales();
        initFilters();
    }
};