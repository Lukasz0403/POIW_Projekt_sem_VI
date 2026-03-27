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
                    <option value="">-- wybierz --</option>
                </select><br><br>

                <label>Cena:</label><br>
                <input type="number" id="prod_price"><br><br>

                <label>Ilość:</label><br>
                <input type="number" id="prod_quantity"><br><br>

                <button id="add_product_btn">Dodaj produkt</button>
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
}

