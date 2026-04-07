function renderSales(sales){

    const table = document.getElementById("products_table");
    table.innerHTML = "";

    sales.forEach((p, i) => {

        let date = new Date(p.saleDate)

        let hour
        let minute
        let second
        let year
        let month
        let day

        if(date.getHours() < 10) {
            hour = "0" + date.getHours()
        } else {
            hour = date.getHours()
        }
        if(date.getMinutes() < 10) {
            minute = "0" + date.getMinutes()
        } else {
            minute = date.getMinutes()
        }
        if(date.getSeconds() < 10) {
            second = "0" + date.getSeconds()
        } else {
            second = date.getSeconds()
        }
        if(date.getMonth() < 10) {
            month = "0" + date.getMonth()
        } else {
            month = date.getMonth()
        }
        if(date.getDay() < 10) {
            day = "0" + date.getDay()
        } else {
            day = date.getDay()
        }

        year = date.getFullYear()
        
        table.innerHTML += `
            <tr>
                <td>${p.productId.categoryId.name}</td>
                <td>${p.productId.name}</td>
                <td>${p.productId.brand}</td>
                <td>${p.productId.price} zł</td>
                <td>${p.quantity}</td>
                <td>${year}-${month}-${day} ${hour}:${minute}:${second}</td>
            </tr>
        `;
    });
}

window.onload = async function() {

    let status = await checkSession()

    if(status) {
        let roleInfo = await getLoginInfo()
        drawNavbar()
        checkRole(roleInfo)
        await loadSales();   
        initFilters();  
    }

}

async function loadSales(){

    const res = await fetch("/MyParts/getSales");
    allSales = await res.json();

    renderSales(allSales);
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

