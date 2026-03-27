function renderProducts(products){

    const table = document.getElementById("products_table");
    table.innerHTML = "";

    products.forEach((p, i) => {
        table.innerHTML += `
            <tr>
                <td>${p.categoryId.name}</td>
                <td>${p.name}</td>
                <td>${p.brand}</td>
                <td>${p.price} zł</td>
                <td>${p.quantity}</td>
                <td>
                    <a href="#"">
                        Edytuj
                    </a>
                </td>
            </tr>
        `;
    });
}

window.onload = async function() {

    let status = await checkSession()

    if(status) {

        drawNavbar()
        checkRole()
        await loadProducts();   
        initFilters();  
    }

}

async function loadProducts(){

    const res = await fetch("/MyParts/productListServlet");
    allProducts = await res.json();

    renderProducts(allProducts);
    loadCategories(allProducts);
}

function filtr(){

    const kategoria = document.getElementById("kategoria").value;
    const cena = document.getElementById("cena").value;
    const nazwa = document.getElementById("search_name").value.toLowerCase();
    const sort = document.getElementById("sort_select").value;

    let filtered = allProducts.filter(p => {

        let matchCategory = !kategoria || p.categoryId.name === kategoria;
        let matchPrice = !cena || p.price <= cena;
        let matchName = !nazwa || p.name.toLowerCase().includes(nazwa);

        return matchCategory && matchPrice && matchName;
    });

    
    if(sort === "name_asc"){
        filtered.sort((a, b) => a.name.localeCompare(b.name));
    }
    if(sort === "name_desc"){
        filtered.sort((a, b) => b.name.localeCompare(a.name));
    }
    if(sort === "price_asc"){
        filtered.sort((a, b) => a.price - b.price);
    }
    if(sort === "price_desc"){
        filtered.sort((a, b) => b.price - a.price);
    }
    if(sort === "quantity_desc"){
        filtered.sort((a, b) => b.quantity - a.quantity);
    }

    renderProducts(filtered);
}


function initFilters(){

    document.getElementById("search_name").addEventListener("input", filtr);
    document.getElementById("cena").addEventListener("input", filtr);
    document.getElementById("kategoria").addEventListener("change", filtr);
    document.getElementById("sort_select").addEventListener("change", filtr);
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