function loadProducts() {
    fetch("/MyParts/productListServlet")
        .then(res => res.json())
        .then(products => {

            let html = `
            <div style="display:flex;">
                
                <div style="width:250px; background:#e0e0e0; padding:15px;">
                    <h3>Filtry</h3>

                    Kategoria:<br>
                    <select id="kategoria">
                        <option value="">-- wszystkie --</option>
                    </select><br><br>

                    Cena maks:<br>
                    <input id="cena" type="number"><br><br>

                    <button onclick="filtr()">Filtruj</button>
                </div>

                <div style="flex:1; padding:20px;">
                    <h2>Produkty</h2>

                    <table style="width:100%; border-collapse:collapse;">
                        <tr>
                            <th>Kategoria</th>
                            <th>Nazwa</th>
                            <th>Marka</th>
                            <th>Cena</th>
                            <th>Ilość</th>
                        </tr>
            `;

            products.forEach(p => {
                html += `
                    <tr>
                        <td>${p.categoryId.name}</td>
                        <td>${p.name}</td>
                        <td>${p.brand}</td>
                        <td>${p.price} zł</td>
                        <td>${p.quantity}</td>
                    </tr>
                `;
            });

            html += `
                    </table>
                </div>
            </div>
            `;

            document.getElementById("content").innerHTML = html;

            loadCategories(products); 
        });
}


function loadCategories(products) {
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

function filtr() {
    const kategoria = document.getElementById("kategoria").value;
    const cena = document.getElementById("cena").value;

    fetch("/MyParts/productListServlet")
        .then(res => res.json())
        .then(products => {

            let filtered = products.filter(p => {

                let matchCategory = !kategoria || p.categoryId.name === kategoria;
                let matchPrice = !cena || p.price <= cena;

                return matchCategory && matchPrice;
            });

            renderFiltered(filtered);
        });
}

function renderFiltered(products) {

    let rows = "";

    products.forEach(p => {
        rows += `
            <tr>
                <td>${p.categoryId.name}</td>
                <td>${p.name}</td>
                <td>${p.brand}</td>
                <td>${p.price} zł</td>
                <td>${p.quantity}</td>
            </tr>
        `;
    });

    document.querySelector("table").innerHTML = `
        <tr>
            <th>Kategoria</th>
            <th>Nazwa</th>
            <th>Marka</th>
            <th>Cena</th>
            <th>Ilość</th>
        </tr>
        ${rows}
    `;
}