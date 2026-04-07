function getProductId(){
    const params = new URLSearchParams(window.location.search);
    return params.get("id");
}

window.onload = async function(){

    let status = await checkSession();

    if(status){
        drawNavbar();
        await loadProduct();
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
        alert("Zaktualizowano produkt");
        window.location.href = "products.html";
    } else {
        alert("Błąd aktualizacji");
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
        alert("Produkt usunięty");
        window.location.href = "products.html";
    } else {
        alert("Błąd usuwania");
    }
}