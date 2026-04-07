window.onload = async function(){

    let status = await checkSession();

    if(status){
        drawNavbar();
        checkRole();
        await loadCategories();
    }
}


async function loadCategories(){

    const res = await fetch("/MyParts/productListServlet");
    const products = await res.json();

    const select = document.getElementById("prod_category");
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


async function addProduct(){

    const name = document.getElementById("prod_name").value;
    const brand = document.getElementById("prod_brand").value;
    const category = document.getElementById("prod_category").value;
    const price = parseFloat(document.getElementById("prod_price").value);
    const quantity = parseInt(document.getElementById("prod_quantity").value);

    
    if(!name || !brand || !category){
        alert("Wypełnij wszystkie pola!");
        return;
    }

    if(isNaN(price) || price < 0){
        alert("Cena musi być dodatnia!");
        return;
    }

    if(isNaN(quantity) || quantity <= 0){
        alert("Ilość musi być większa od 0!");
        return;
    }

    
    const res = await fetch("/MyParts/addProductServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body:
            `name=${name}&brand=${brand}&category=${category}&price=${price}&quantity=${quantity}`
    });

    if(res.status === 202){
        alert("Produkt dodany!");

        clearForm(); // 🔥 czyszczenie
    } else {
        alert("Błąd dodawania produktu");
    }
}


function clearForm(){
    document.getElementById("prod_name").value = "";
    document.getElementById("prod_brand").value = "";
    document.getElementById("prod_category").value = "";
    document.getElementById("prod_price").value = "";
    document.getElementById("prod_quantity").value = "";
}


function uploadCSV(){
    const file = document.getElementById("csv_file").files[0];

    if(!file){
        alert("Wybierz plik!");
        return;
    }

    alert("Import CSV - do implementacji");
}