function loadOrder(){

    document.getElementById("content").innerHTML = `
        <h1>📦 Przyjęcie zamówienia</h1>

        <div style="display:flex; gap:40px; margin-top:20px;">

            <div style="flex:1; background:#f2f2f2; padding:20px; border-radius:10px;">
                <h2>Dodaj produkt ręcznie</h2>

                <label>Nazwa produktu:</label><br>
                <input type="text" id="prod_name"><br><br>

                <label>Marka:</label><br>
                <input type="text" id="prod_brand"><br><br>

                <label>Kategoria:</label><br>
                <select id="prod_category">
                    
                </select><br><br>

                <label>Cena:</label><br>
                <input type="number" id="prod_price" min="0" step="0.01">

                <label>Ilość:</label><br>
                <input type="number" id="prod_quantity" min="1" step="1">

                <button onclick="addProduct()">Dodaj produkt</button>
            </div>

            <div style="flex:1; background:#f2f2f2; padding:20px; border-radius:10px;">
                <h2>Import z pliku CSV</h2>

                <input type="file" id="csv_file" accept=".csv"><br><br>

                <button id="upload_csv_btn">Importuj plik</button>

                <hr>

                <p style="font-size:14px; color:gray;">
                    Format: nazwa,marka,cena,ilość,kategoria
                </p>
            </div>

        </div>
    `;
    
    loadCatInOrder();
}

function loadCatInOrder(){

    const select = document.getElementById("prod_category");

    fetch("/MyParts/productListServlet")
        .then(res => res.json())
        .then(products => {

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

        });
}

function addProduct(){

    const name = document.getElementById("prod_name").value;
    const brand = document.getElementById("prod_brand").value;
    const category = document.getElementById("prod_category").value;
    const price = document.getElementById("prod_price").value;
    const quantity = document.getElementById("prod_quantity").value;

    fetch("/MyParts/addProductServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `name=${name}&brand=${brand}&category=${category}&price=${price}&quantity=${quantity}`
    })
    .then(res => {
        if(res.status === 202){
            alert("Produkt dodany!");
    
            document.getElementById("prod_name").value = "";
            document.getElementById("prod_brand").value = "";
            document.getElementById("prod_price").value = "";
            document.getElementById("prod_quantity").value = "";
        } else {
            alert("Błąd dodawania produktu");
        }
    });
}
    


