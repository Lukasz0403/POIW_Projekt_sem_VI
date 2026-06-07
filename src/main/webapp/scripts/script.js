
async function logout() {

    fetch("/MyParts/destroySession", {
        method: "POST"
    })
        .then(data => {
            console.log(data)
            if (data.status == 202) {
                window.location.href = "/MyParts/html/loginform.html";
            }
        })
        .catch(err => console.error(err))
}

async function checkSession() {

    return new Promise((resolve) => {

        let status = null

        fetch("/MyParts/checkSession", {
            method: "POST"
        })
        .then(data => {
            if (data.status == 401) {
                status = false
                window.location.href = "/MyParts/html/loginform.html";
            } else {
                status = true
            }
            resolve(status)
        })
        .catch(err => console.error(err))

        
    })
}

function drawNavbar() {

    const body = document.querySelector("body")

    let navBody =
    `<nav>
        <div class="nav-left">
            <a href="/MyParts/">Dashboard</a>
            <a href="/MyParts/html/products.html">Produkty</a>
            <a href="/MyParts/html/orders.html">Przyjęcie Zamówienia</a>
            <a href="/MyParts/html/sales.html">Kasa fiskalna</a>
            <a href="/MyParts/html/salesReportMAIN.html">Raport sprzedażowy</a>
        </div>
        <div class="nav-right">
            <a href="#" onclick="logout()">Wyloguj</a>
        </div>
    </nav>`

    body.insertAdjacentHTML("afterbegin", navBody)
}

async function getLoginInfo() {

    return new Promise((resolve) => {

        fetch("/MyParts/dashboardServlet")
        .then(res => res.json())
        .then(json => resolve(json))
        .catch(err => console.error(err))
    })
}

async function checkRole(json) {

    const navRight = document.querySelector(".nav-right")

    if(json[0].role.roleId >= 2) {

        let nav = document.createElement("a")
        nav.setAttribute("href", "/MyParts/html/adminTab.html")
        nav.innerText = "Pracownicy"

        navRight.insertAdjacentElement("afterbegin", nav)
    }

}


function showToast(message, type = "success", duration = 3000) {
    const toast = document.createElement("div");
    toast.className = `toast ${type}`;
    toast.textContent = message;
    document.body.appendChild(toast);

    // pokaż
    setTimeout(() => toast.classList.add("show"), 10);

    // ukryj i usuń
    setTimeout(() => {
        toast.classList.remove("show");
        setTimeout(() => toast.remove(), 400);
    }, duration);
}