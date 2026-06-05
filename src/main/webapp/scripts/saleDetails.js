/**
 * @file saleDetails.js
 * @description Obsługa wyświetlania szczegółów konkretnej transakcji na podstawie jej ID przekazanego w URL.
 */

/**
 * Pobieranie identyfikatora transakcji z parametrów zapytania w adresie URL.
 * @returns {string|null} Identyfikator transakcji lub null, jeśli nie został podany.
 */
function getTransactionIdFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return params.get("id");
}

/**
 * Pobieranie i renderowanie szczegółowych danych transakcji oraz jej elementów (produktów).
 * Funkcja filtruje dane z głównej listy sprzedaży i wypełnia elementy HTML strony.
 */
async function loadTransactionDetails() {
    const params = new URLSearchParams(window.location.search);
    const transactionId = params.get("id");

    try {
        const res = await fetch(window.location.origin + "/MyParts/getSales");
        const allSales = await res.json();
        
        // Filtrowanie sprzedaży dla danej transakcji
        const items = allSales.filter(s => String(s.transactionId.transactionId) === String(transactionId));

        if (items.length === 0) {
            document.querySelector(".details-card").innerHTML = "<p class='error'>Brak danych transakcji.</p>";
            return;
        }

        // Wypełnianie nagłówka szczegółów
        document.getElementById("trans_id").textContent = transactionId;
        document.getElementById("trans_date").textContent = new Date(items[0].saleDate).toLocaleString();
        document.getElementById("trans_user").textContent = items[0].userId?.username || "Nieznany";

        // Wypełnianie tabeli produktów
        const tbody = document.getElementById("receipt-body");
        let grandTotal = 0;

        items.forEach(s => {
            const rowTotal = s.productId.price * s.quantity;
            grandTotal += rowTotal;
            tbody.innerHTML += `
                <tr>
                    <td>${s.productId.name}</td>
                    <td>${s.productId.brand}</td>
                    <td>${s.productId.price.toFixed(2)} zł</td>
                    <td>${s.quantity}</td>
                    <td>${rowTotal.toFixed(2)} zł</td>
                </tr>`;
        });

        // Aktualizacja sumy końcowej
        document.getElementById("trans_total").textContent = grandTotal.toFixed(2);
    } catch (error) {
        console.error("Błąd ładowania:", error);
    }
}

/**
 * Inicjalizacja strony po jej załadowaniu.
 * Sprawdza sesję użytkownika, rysuje pasek nawigacji i ładuje dane transakcji.
 */
window.onload = async () => {
    if (await checkSession()) {
        drawNavbar();
        const loginInfo = await getLoginInfo();
        await checkRole(loginInfo);
        await loadTransactionDetails();
    }
};