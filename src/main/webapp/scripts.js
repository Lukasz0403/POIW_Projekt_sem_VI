
/**
 * Funkcja JavaScript wykorzystywana w głównym oknie aplikacji.
 * Umożliwia przełączanie zawartości klasy HTML "content" poprzez wywołanie odpowiednie servleta
 * po naciśnięciu przycisku na stronie HTML.
 * Wciśnięcie przycisku powoduje wywołanie tej funkcji z odpowiednim parametrem.
 * @author Mateusz Gojny
 * @param {type} section 
 * @returns {undefined}
 */

function go(section){
    
    if(section === "products"){
        
         fetch("/MyParts/productListServlet")
            .then(res => res.text())
            .then(html => {
                document.getElementById("content").innerHTML = html;
            });
    }
    
    if(section === "dashboard"){
        fetch("/MyParts/dashboardServlet")
            .then(res => res.text())
            .then(html => {
                document.getElementById("content").innerHTML = html;
            });
         }
    
}

/**
 * Funkcja JavaScript odpowiadająca za logowanie. Funkcja wywoływana jest po wciśnięciu przycisku "zaloguj sie".
 * Odczytuje ona zawartość pól do logowania i przypisuje do zmiennym login i pass.
 * Wywoływana jest funkcja fetch która odwołuje się do servleta loginProcedure przekazując mu parametry metody jaką
 * ma wykonać oraz wartość argumentów. Zwrócone dane przez servlet są sprawdzane i wrazie poprawności
 * wyświetlane jest okno główne aplikacji.
 * @author Mateusz Gojny
 * @type null.value|Element.value
 */
function login(){
    
    const login = document.getElementById('login').value;
    const pass =  document.getElementById('password').value;
    
  fetch("/MyParts/loginProcedureServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "login=" + login + "&pass=" + pass
    })
    .then(res => res.text())
    .then(data => {
        if (data === "OK") {
            window.location.href = "mainWindow.html";
        } else {
            alert("Błędny login lub hasło");
        }
    })
    .catch(err => console.error(err));
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

/**
 * Funkcja JavaScript wywoływana jest po kliknięciu przycisku "wyloguj sie" w oknie głównym, powoduje przełączenia okna
 * na ekran logowania zawarty w index.html
 * @author Mateusz Gojny
 * @returns {undefined}
 */

function logout() {
    
    window.location.href = "index.html";
    
}

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