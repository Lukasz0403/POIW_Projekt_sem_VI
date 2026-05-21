let currentCart = [];
let allProducts = [];

window.onload = async function() {
    
    let status = await checkSession();
    
    if(status){
        let roleInfo = await getLoginInfo();
        drawNavbar();
        checkRole(roleInfo);
    }
    
    const res = await fetch("/MyParts/productListServlet");
    allProducts = await res.json();

    const datalist = document.getElementById("products-list");
    allProducts.forEach(p => {
        let option = document.createElement("option");
        option.value = `${p.name} - ${p.brand} (${p.price} PLN)`; 
        datalist.appendChild(option);
    });
};

    document.getElementById("add-to-cart-btn").onclick = function() {
    const inputVal = document.getElementById("selected-product-info").value;
    const qty = parseInt(document.getElementById("prod_qty").value);

    const product = allProducts.find(p => `${p.name} - ${p.brand} (${p.price} PLN)` === inputVal);

    if (!product) {
//        alert("Wybierz poprawny produkt z listy!");
        showToast("Wybierz poprawny produkt z listy!", "error", 5000);
        
        return;
    }
    if (product.quantity < qty) {
//        alert(`Błąd! Brak towaru. Dostępne tylko: ${product.quantity} szt.`);
        showToast(`Błąd! Brak towaru. Dostępne tylko: ${product.quantity} szt.`, "error", 5000);
        return;
    }

    currentCart.push({
        productId: product.productId,
        name: product.name,
        price: product.price,
        quantity: qty
    });

    renderCart();
    document.getElementById("selected-product-info").value = "";
    document.getElementById("prod_qty").value = "1";
};

function renderCart() {
    const list = document.getElementById("cart-list");
    const totalEl = document.getElementById("total-price");
    const finalizeBtn = document.getElementById("finalize-sale-btn");
    
    list.innerHTML = "";
    let suma = 0;

    currentCart.forEach((item, index) => {
        suma += (item.price * item.quantity);
        list.innerHTML += `
            <li> 
                <div class="cart-item-info">
                    <b>${item.name}</b><br> 
                    ${item.quantity} szt. x ${item.price} PLN = ${(item.price * item.quantity).toFixed(2)} PLN 
                </div>
                <span class="remove-btn" onclick="removeFromCart(${index})" title="Usuń z paragonu">&#10006;</span>
            </li>`;
    });

    totalEl.innerText = suma.toFixed(2);
    finalizeBtn.style.display = currentCart.length > 0 ? "block" : "none";
}

window.removeFromCart = function(index) {
    currentCart.splice(index, 1);
    renderCart();
};

document.getElementById('finalize-sale-btn').onclick = async function() {
    const savedCart = [...currentCart];
    const totalAmount = document.getElementById("total-price").innerText;

    const response = await fetch('/MyParts/processSaleServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(currentCart)
    });

    if(response.ok) {
        showToast("Sprzedaż zakończona pomyślnie! Raport został zaktualizowany.", "success", 5000);
        
        let transactionId = null;
        try {
            const resData = await response.json();
            transactionId = resData.transactionId; 
        } catch(e) {
            console.warn("Error", e);
        }

        showSummaryModal(savedCart, totalAmount, transactionId);

        currentCart = [];
        renderCart();
        const res = await fetch("/MyParts/productListServlet");
        allProducts = await res.json();
    } else {
        showToast("Błąd podczas procesowania sprzedaży.", "error", 5000);
    }
};

function showSummaryModal(cart, total, transactionId) {
    const modal = document.getElementById("sale-summary-modal");
    const listContainer = document.getElementById("modal-items-list");
    const totalContainer = document.getElementById("modal-total-price");
    const detailsLink = document.getElementById("modal-details-link");

    listContainer.innerHTML = "";
    cart.forEach(item => {
        listContainer.innerHTML += `
            <li>
                <span><b>${item.name}</b> x ${item.quantity} szt.</span>
                <span>${(item.price * item.quantity).toFixed(2)} PLN</span>
            </li>
        `;
    });

    totalContainer.innerText = total;

    if (transactionId) {
        detailsLink.href = `/MyParts/html/saleDetails.html?id=${transactionId}`;
        detailsLink.style.display = "block";
    } else {
        detailsLink.href = `/MyParts//html/salesReportMAIN.html`; 
    }
    modal.style.display = "flex";
}

    document.getElementById("modal-close-btn").onclick = function() {
        document.getElementById("sale-summary-modal").style.display = "none";
    };
    
async function loadCategories(){
    const res = await fetch("/MyParts/productListServlet");
    const products = await res.json();
    const select = document.getElementById("prod_category");
    let categories = new Set();
    products.forEach(p => { if(p.categoryId) categories.add(p.categoryId.name); });
    categories.forEach(cat => {
        let option = document.createElement("option");
        option.value = cat; option.textContent = cat;
        select.appendChild(option);
    });
}

async function loadSales(){

    const res = await fetch("/MyParts/getSales");
    allSales = await res.json();
    renderSales(allSales);
}

