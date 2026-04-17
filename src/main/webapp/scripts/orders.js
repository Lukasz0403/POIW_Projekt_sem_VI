window.onload = async function(){

    let status = await checkSession();

    if(status){
        let roleInfo = await getLoginInfo()
        drawNavbar();
        checkRole(roleInfo);
        await loadCategories();
        initValidation();
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
        showToast("Wypełnij wszystkie pola","error");
        return;
    }

    if(isNaN(price) || price < 0){
        showToast("Cena musi być dodatnia","error");;
        return;
    }

    if(isNaN(quantity) || quantity <= 0){
        showToast("Ilość musi być większa od 0","error");;
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
        showToast("Produkt dodany");

        clearForm(); 
    } else {
        showToast("Nie udało się dodać produktu","error");
    }
}


function clearForm(){
    document.getElementById("prod_name").value = "";
    document.getElementById("prod_brand").value = "";
    document.getElementById("prod_category").value = "";
    document.getElementById("prod_price").value = "";
    document.getElementById("prod_quantity").value = "";
}


async function uploadCSV() {
    const fileInput = document.getElementById("csv_file");
    if (!fileInput.files[0]) {
        showToast("Wybierz plik .csv","error");
        return;
    }

    const formData = new FormData();
    formData.append("csv_file", fileInput.files[0]);

    const res = await fetch("/MyParts/uploadCSVServlet", {
        method: "POST",
        body: formData
    });

    if (res.status === 202) {
        showToast("Produkty zaimportowane");
        document.getElementById("csv_file").value = "";
    } else {
        showToast("Błąd importu","error");
    }
}


function initValidation() {
    
     document.getElementById("prod_name").addEventListener("input", function () {
        const isValid = this.value.trim() !== "";
        this.classList.toggle("input-error", !isValid);
        document.getElementById("err_name").classList.toggle("show", !isValid);
    });

    document.getElementById("prod_brand").addEventListener("input", function () {
        const isValid = this.value.trim() !== "";
        this.classList.toggle("input-error", !isValid);
        document.getElementById("err_brand").classList.toggle("show", !isValid);
    });
    
    document.getElementById("prod_price").addEventListener("input", function () {
        const val = this.value;
        const isValid = val !== "" && !isNaN(val) && parseFloat(val) >= 0;
        this.classList.toggle("input-error", !isValid);
        document.getElementById("err_price").classList.toggle("show", !isValid);
    });

    document.getElementById("prod_quantity").addEventListener("input", function () {
        const val = this.value;
        const isValid = val !== "" && !isNaN(val) && parseInt(val) >= 1;
        this.classList.toggle("input-error", !isValid);
        document.getElementById("err_quantity").classList.toggle("show", !isValid);
    });
}
