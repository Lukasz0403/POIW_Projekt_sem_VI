function loadProducts() {
    fetch("/MyParts/productListServlet")
        .then(res => res.json())
        .then(products => {

            let html = `
                <div style="display:flex;">

                    <!-- LEWA -->
                    <div style="width:250px; background:#e0e0e0; padding:15px;">
                        <h3>Filtry</h3>

                        Kategoria:<br>
                        <select id="kategoria">
                            <option value="">-- wszystkie --</option>
                        </select><br><br>

                        Nazwa produktu:<br>
                        <input type="text" id="search_name"><br><br>

                        Cena maks:<br>
                        <input id="cena" type="number"><br><br>

                        <button onclick="filtr()">Filtruj</button>
                    </div>

                    <!-- PRAWA -->
                    <div style="flex:1; padding:20px;">

                        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:10px;">
                            <h2>Produkty</h2>

                            <div>
                                Sortuj:
                                <select id="sort_select">
                                    <option value="">-- brak --</option>
                                    <option value="name_asc">Nazwa A-Z</option>
                                    <option value="name_desc">Nazwa Z-A</option>
                                    <option value="price_asc">Cena rosnąco</option>
                                    <option value="price_desc">Cena malejąco</option>
                                    <option value="quantity_desc">Największa dostępność</option>
                                </select>
                            </div>
                        </div>

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
            document.getElementById("search_name").addEventListener("input", filtr);
            document.getElementById("sort_select").addEventListener("change", filtr);
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
    const nazwa = document.getElementById("search_name").value.toLowerCase();
    const sort = document.getElementById("sort_select").value;

    fetch("/MyParts/productListServlet")
        .then(res => res.json())
        .then(products => {

            let filtered = products.filter(p => {

                let matchCategory = !kategoria || p.categoryId.name === kategoria;
                let matchPrice = !cena || p.price <= cena;
                let matchName = !nazwa || p.name.toLowerCase().includes(nazwa);
                return matchCategory && matchPrice && matchName;
                });
                
                if (sort === "name_asc"){
                    filtered.sort((a, b) => a.name.localeCompare(b.name));
                            }

                    if (sort === "name_desc"){
                    filtered.sort((a, b) => b.name.localeCompare(a.name));
                            }

                    if (sort === "price_asc"){
                    filtered.sort((a, b) => a.price - b.price);
                            }

                    if (sort === "price_desc"){
                    filtered.sort((a, b) => b.price - a.price);
                            }

                    if (sort === "quantity_desc"){
                    filtered.sort((a, b) => b.quantity - a.quantity);
                            }
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