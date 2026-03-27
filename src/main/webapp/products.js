/**
 * Funkcja JavaScript odpowiadająca za wywołanie servleta odpowiadjące za wyświetlanie zawartości
 * okna ze szczegółami danego produktu. Funkcja przyjmuje id produktu. Id produktu określane jest na ten moment 
 * poprzez numer na liście 
 * @author Mateusz Gojny
 * @param {type} id
 * @returns {undefined}
 */

function productInfo(id) {
    fetch("/MyParts/prodInfoServlet?id=" + id)
        .then(res => res.text())
        .then(html => {
            document.getElementById("content").innerHTML = html;
        });
}

/**
 * Funkcja JavaScript odpowiadająca za odczyt danych z pól sortujących/filtrujących w zakładce produkty.
 * Po wciśnęciu przycisku "filtruj" odczytywane są wartości pól i zapisywane są one do zmiennych.
 * funkcja fetch przekazuje te zmienne jako parametry do servleta productListWindowServlet.
 * @author Mateusz Gojny
 * @returns {undefined}
 */
function filtr() {
    const kategoria = document.getElementById("kategoria").value;
    const cena = document.getElementById("cena").value;

    fetch(`/MyParts/productListServlet?kategoria=${kategoria}&cena=${cena}`)
        .then(res => res.text())
        .then(html => {
            document.getElementById("content").innerHTML = html;
        });
}

window.onload = async function() {

     await checkSession()
     drawNavbar()
}