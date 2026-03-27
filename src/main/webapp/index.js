
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
            loadProducts();
    }
    
    if(section === "dashboard"){
    document.getElementById("content").innerHTML = `
        <h1>Witamy w aplikacji 👋</h1>
        <div class="card">
            <h3>Dane użytkownika:</h3>
            <p id="imie"><b>Imię:</b></p>
            <p id="nazwisko"><b>Nazwisko:</b></p>
            <p id="ident"><b>ID:</b></p>
            <p id="login"><b>Login:</b></p>
            <hr>
            <p id="rola"><b>Zakres uprawnień:</b></p>
        </div>
    `;

    getLoginInfo();
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