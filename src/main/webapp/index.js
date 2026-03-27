
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
        loadDashboard();
    }
    
    if(section === "order"){
        loadOrder();
    }

    if(section === "sales"){
        loadCash();
    }

    if(section === "report"){
        loadReport();
    }
    
}

function getLoginInfo() {

    const imie = document.getElementById("imie")
    const nazwisko = document.getElementById("nazwisko")
    const id = document.getElementById("ident")
    const login = document.getElementById("login")
    const rola = document.getElementById("rola")
    const data = document.getElementById("data")

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
    
    
    
document.addEventListener("keydown", function (e) {
    if (e.key === "Backspace") {

        const target = e.target;

        // pozwól działać tylko w inputach
        if (
                target.tagName === "INPUT" ||
                target.tagName === "TEXTAREA"
                ) {
            return;
        }

        e.preventDefault();
    }
});