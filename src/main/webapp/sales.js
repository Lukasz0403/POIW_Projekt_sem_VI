function loadCash(){
    document.getElementById("content").innerHTML = `

        <h1>💰 Kasa fiskalna</h1>

        <div style="display:flex; gap:30px; margin-top:20px;">

            <!-- LEWA STRONA - KOSZYK -->
            <div style="flex:1; background:#e0e0e0; padding:20px; border-radius:10px; min-height:400px;">
                
                <h2>Koszyk</h2>

                <div id="cart_list">
                    <!-- TU będą dodawane produkty -->
                </div>

                <hr>

                <h3 id="cart_total">Suma: 0 zł</h3>

                <button id="confirm_sale_btn" 
                    style="margin-top:10px; background:#1e7e34; color:white; padding:10px; border:none; border-radius:5px; width:100%;">
                    Zatwierdź sprzedaż
                </button>
            </div>

            <!-- PRAWA STRONA - WYSZUKIWANIE -->
            <div style="width:350px; background:#f2f2f2; padding:20px; border-radius:10px;">
                
                <h2>Dodaj produkt</h2>

                <!-- PODGLĄD PRODUKTU -->
                <div id="product_preview" style="background:white; padding:10px; border-radius:5px; min-height:80px;">
                    <p>Brak wybranego produktu</p>
                </div>

                <br>

                <!-- FORMULARZ -->
                <label>ID produktu:</label><br>
                <input type="number" id="cash_product_id"><br><br>

                <label>Ilość:</label><br>
                <input type="number" id="cash_quantity" value="1"><br><br>

                <!-- PRZYCISKI -->
                <button id="search_product_btn" style="width:100%; margin-bottom:10px;">
                    Szukaj
                </button>

                <button id="add_to_cart_btn" style="width:100%; background:#007bff; color:white;">
                    OK
                </button>

            </div>

        </div>
    `;
}

