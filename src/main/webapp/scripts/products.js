let allProducts = [];
let currentRoleId = 1;
let currentSort = { column: null, asc: true };

function renderProducts(products, roleId) {
    const table = document.getElementById("products_table");
    table.innerHTML = "";
    products.forEach((p) => {
        table.innerHTML += `
            <tr class="${p.quantity <= 5 ? 'low-stock' : ''}">
                <td>${p.categoryId.name}</td>
                <td>${p.name}</td>
                <td>${p.brand}</td>
                <td>${p.price} zł</td>
                <td>${p.quantity <= 5 ? `${p.quantity} ⚠️` : p.quantity}</td>
                ${roleId >= 2 ? `<td><a href="editProduct.html?id=${p.productId}">Edytuj</a></td>` : ""}
            </tr>
        `;
    });
    
    document.getElementById("products_counter").textContent =
            `Wyświetlono ${products.length} z ${allProducts.length} produktów`;
}

window.onload = async function() {
    let status = await checkSession();
    if(status) {
        drawNavbar();
        let roleInfo = await getLoginInfo();
        currentRoleId = roleInfo[0].role.roleId;
        checkRole(roleInfo)
        await loadProducts();
        initFilters();
        if (currentRoleId < 2) {
            document.getElementById("th_edit").style.display = "none";
        }
    }
}

async function loadProducts() {
    const res = await fetch("/MyParts/productListServlet");
    allProducts = await res.json();
    renderProducts(allProducts, currentRoleId);
    loadCategories(allProducts);
}

function filtr() {
    const kategoria = document.getElementById("kategoria").value;
    const cena = document.getElementById("cena").value;
    const nazwa = document.getElementById("search_name").value.toLowerCase();

    let filtered = allProducts.filter(p => {
        let matchCategory = !kategoria || p.categoryId.name === kategoria;
        let matchPrice = !cena || p.price <= cena;
        let matchName = !nazwa || p.name.toLowerCase().includes(nazwa);
        return matchCategory && matchPrice && matchName;
    });

    // jeśli jest aktywne sortowanie kolumnowe — zachowaj je
    if (currentSort.column) {
        filtered.sort((a, b) => {
            let valA, valB;
            switch (currentSort.column) {
                case "category":
                    valA = a.categoryId.name;
                    valB = b.categoryId.name;
                    break;
                case "name":
                    valA = a.name;
                    valB = b.name;
                    break;
                case "brand":
                    valA = a.brand;
                    valB = b.brand;
                    break;
                case "price":
                    valA = a.price;
                    valB = b.price;
                    break;
                case "quantity":
                    valA = a.quantity;
                    valB = b.quantity;
                    break;
            }
            if (typeof valA === "string") {
                return currentSort.asc ? valA.localeCompare(valB) : valB.localeCompare(valA);
            }
            return currentSort.asc ? valA - valB : valB - valA;
        });
    }

    renderProducts(filtered, currentRoleId);
}


function initFilters(){

    document.getElementById("search_name").addEventListener("input", filtr);
    document.getElementById("cena").addEventListener("input", filtr);
    document.getElementById("kategoria").addEventListener("change", filtr);
    
}


function loadCategories(products){

    const select = document.getElementById("kategoria");
    let categories = new Set();

    products.forEach(p => {
        categories.add(p.categoryId.name);
    });

    categories.forEach(cat => {
        let option = document.createElement("option");
        option.value = cat;
        option.textContent = cat;
        select.appendChild(option);
    });
}

function columnSort(column) {
    // jeśli ta sama kolumna — odwróć kierunek
    if (currentSort.column === column) {
        currentSort.asc = !currentSort.asc;
    } else {
        currentSort.column = column;
        currentSort.asc = true;
    }

    // resetuj strzałki na wszystkich nagłówkach
    document.querySelectorAll("thead th span").forEach(s => s.innerHTML = "↕");

    // ustaw strzałkę na klikniętej kolumnie
    const thMap = {
        category: "th_category",
        name: "th_name",
        brand: "th_brand",
        price: "th_price",
        quantity: "th_quantity"
    };
    const th = document.getElementById(thMap[column]);
    th.querySelector("span").innerHTML = currentSort.asc ? "↑" : "↓";

    // pobierz aktualnie przefiltrowane produkty i posortuj
    const kategoria = document.getElementById("kategoria").value;
    const cena = document.getElementById("cena").value;
    const nazwa = document.getElementById("search_name").value.toLowerCase();

    let sorted = allProducts.filter(p => {
        let matchCategory = !kategoria || p.categoryId.name === kategoria;
        let matchPrice = !cena || p.price <= cena;
        let matchName = !nazwa || p.name.toLowerCase().includes(nazwa);
        return matchCategory && matchPrice && matchName;
    });

    sorted.sort((a, b) => {
        let valA, valB;
        switch (column) {
            case "category":
                valA = a.categoryId.name;
                valB = b.categoryId.name;
                break;
            case "name":
                valA = a.name;
                valB = b.name;
                break;
            case "brand":
                valA = a.brand;
                valB = b.brand;
                break;
            case "price":
                valA = a.price;
                valB = b.price;
                break;
            case "quantity":
                valA = a.quantity;
                valB = b.quantity;
                break;
        }
        if (typeof valA === "string") {
            return currentSort.asc ? valA.localeCompare(valB) : valB.localeCompare(valA);
        }
        return currentSort.asc ? valA - valB : valB - valA;
    });

    renderProducts(sorted, currentRoleId);
}

