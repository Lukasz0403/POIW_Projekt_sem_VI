function loadReport(){
    document.getElementById("content").innerHTML = `

        <h1>📊 Raport sprzedażowy</h1>

        <!-- GÓRNY PANEL -->
        <div style="display:flex; justify-content:space-between; margin-top:20px;">

            <!-- STATYSTYKI -->
            <div style="flex:1; background:#f2f2f2; padding:20px; border-radius:10px;">
                <h2>Podsumowanie</h2>

                <p><b>Całkowity przychód:</b> <span id="total_revenue">0 zł</span></p>
                <p><b>Liczba sprzedanych produktów:</b> <span id="total_items">0</span></p>
            </div>

            <!-- PANEL WYBORU -->
            <div style="width:300px; background:#e0e0e0; padding:20px; border-radius:10px;">

                <h3>Zakres raportu</h3>

                <label>Miesiąc:</label><br>
                <select id="report_month">
                    <option value="1">Styczeń</option>
                    <option value="2">Luty</option>
                    <option value="3">Marzec</option>
                    <option value="4">Kwiecień</option>
                    <option value="5">Maj</option>
                    <option value="6">Czerwiec</option>
                    <option value="7">Lipiec</option>
                    <option value="8">Sierpień</option>
                    <option value="9">Wrzesień</option>
                    <option value="10">Październik</option>
                    <option value="11">Listopad</option>
                    <option value="12">Grudzień</option>
                </select><br><br>

                <label>Rok:</label><br>
                <input type="number" id="report_year" value="2024"><br><br>

                <button id="generate_report_btn" style="width:100%;">
                    Generuj raport
                </button>

                <hr>

                <a href="/MyParts/archiveSales.html" id="archive_link">
                    📂 Archiwum sprzedaży
                </a>

                <br><br>

               

            </div>
        </div>

        <!-- WYKRESY -->
        <div style="display:flex; gap:30px; margin-top:30px;">

            <!-- KOŁOWY -->
            <div style="flex:1; background:#f2f2f2; padding:20px; border-radius:10px;">
                <h3>Udział kategorii w sprzedaży</h3>
                <canvas id="pie_chart" height="200"></canvas>
            </div>

            <!-- SŁUPKOWY -->
            <div style="flex:1; background:#f2f2f2; padding:20px; border-radius:10px;">
                <h3>Sprzedaż dzienna w skali miesiąca</h3>
                <canvas id="bar_chart" height="200"></canvas>
            </div>

        </div>
    `;
}
