
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
            .then(res => res.json())
            .then(html => {
                document.getElementById("content").innerHTML = html;
            });
    }
    
    if(section === "dashboard"){

         }
    
}

function getLoginInfo() {

    const imie = document.getElementById("imie")
    const nazwisko = document.getElementById("nazwisko")
    const id = document.getElementById("ident")
    const login = document.getElementById("login")
    const rola = document.getElementById("rola")

    fetch("/MyParts/dashboardServlet")
    .then(res => res.json())
    .then(json => {
        console.log(" "+json)
        imie.append(" "+json.username)
        nazwisko.append(" "+json.username)
        id.append(" "+json.userId)
        login.append(" "+json.username)
        rola.append(" "+json.role.roleName)
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

/**
 * Funkcja JavaScript wywoływana jest po kliknięciu przycisku "wyloguj sie" w oknie głównym, powoduje przełączenia okna
 * na ekran logowania zawarty w index.html
 * @author Mateusz Gojny
 * @returns {undefined}
 */

function logout() {

    fetch("/MyParts/destroySession", {
        method: "POST"
    })
        .then(data => {
            console.log(data)
            if (data.status == 202) {
                window.location.href = "loginform.html";
            }
        })
        .catch(err => console.error(err))
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

function checkSession() {

    let status

    fetch("/MyParts/checkSession", {
        method: "POST"
    })
    .then(data => {
        if (data.status == 401) {
            window.location.href = "loginform.html";
        } else {
            getLoginInfo()
        }
    })
    .catch(err => console.error(err));

    return status
}

    window.onload = function() {

        checkSession()
    }