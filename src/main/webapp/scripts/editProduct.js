function getProductId(){
    const params = new URLSearchParams(window.location.search);
    return params.get("id");
}

window.onload = async function(){

    let status = await checkSession();

    if(status){
        drawNavbar();
        await loadProduct();
        let roleInfo = await getLoginInfo();
        checkRole(roleInfo)
        initValidation();
    }
}

async function loadProduct(){

    const id = getProductId();

    const res = await fetch(`/MyParts/getProductServlet?id=${id}`);
    const p = await res.json();

    document.getElementById("name").value = p.name;
    document.getElementById("brand").value = p.brand;
    document.getElementById("price").value = p.price;
    document.getElementById("quantity").value = p.quantity;

    loadCategories(p.categoryId.name);
}

async function loadCategories(selected){

    const res = await fetch("/MyParts/productListServlet");
    const products = await res.json();

    const select = document.getElementById("category");
    let categories = new Set();

    products.forEach(p => {
        categories.add(p.categoryId.name);
    });

    categories.forEach(cat => {
        let option = document.createElement("option");
        option.value = cat;
        option.textContent = cat;

        if(cat === selected){
            option.selected = true;
        }

        select.appendChild(option);
    });
}

async function updateProduct(){

    const id = getProductId();

    const name = document.getElementById("name").value;
    const brand = document.getElementById("brand").value;
    const category = document.getElementById("category").value;
    const price = document.getElementById("price").value;
    const quantity = document.getElementById("quantity").value;

    const res = await fetch("/MyParts/updateProductServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body:
            `id=${id}&name=${name}&brand=${brand}&category=${category}&price=${price}&quantity=${quantity}`
    });

    if(res.status === 202){
        showToast("Zaktualizowano produkt");
        setTimeout(() => window.location.href = "products.html", 1000);
    } else {
        showToast("Błąd aktualizacji","error");
        
    }
}

async function deleteProduct(){

    const confirmDelete = confirm("Czy na pewno chcesz usunąć ten przedmiot z systemu?");

    if(!confirmDelete){
        return;
    }

    const id = getProductId();

    const res = await fetch("/MyParts/deleteProductServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `id=${id}`
    });

    if(res.status === 202){
        showToast("Usunięto produkt");
        setTimeout(() => window.location.href = "products.html", 1000);
    } else {
        showToast("Nie udało się usunąć","error");
    }
}

function initValidation() {
    document.getElementById("name").addEventListener("input", function () {
        const isValid = this.value.trim() !== "";
        this.classList.toggle("input-error", !isValid);
        document.getElementById("err_name").classList.toggle("show", !isValid);
    });

    document.getElementById("brand").addEventListener("input", function () {
        const isValid = this.value.trim() !== "";
        this.classList.toggle("input-error", !isValid);
        document.getElementById("err_brand").classList.toggle("show", !isValid);
    });

    document.getElementById("price").addEventListener("input", function () {
        const val = this.value;
        const isValid = val !== "" && !isNaN(val) && parseFloat(val) >= 0;
        this.classList.toggle("input-error", !isValid);
        document.getElementById("err_price").classList.toggle("show", !isValid);
    });

    document.getElementById("quantity").addEventListener("input", function () {
        const val = this.value;
        const isValid = val !== "" && !isNaN(val) && parseInt(val) >= 1;
        this.classList.toggle("input-error", !isValid);
        document.getElementById("err_quantity").classList.toggle("show", !isValid);
    });
}