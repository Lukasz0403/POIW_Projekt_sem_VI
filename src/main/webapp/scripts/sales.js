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