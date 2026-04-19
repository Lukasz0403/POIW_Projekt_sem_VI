let allProducts = null
let filtered = null

window.onload = async function() {

    let status = await checkSession()

    if(status) {
        roleInfo = await getLoginInfo()
        allProducts = await getUsers(roleInfo)
        drawNavbar()
        checkRole(roleInfo)
        printUsers(allProducts)
        checkRoleForPermissionFilter(roleInfo)
        initFilters()
    }

}

async function getUsers(roleInfo) {


    return new Promise((resolve) => {

        if(roleInfo[0].role.roleId == 3) {
            fetch("/MyParts/getUsersServlet")
            .then(res => res.json())
            .then(json => resolve(json))
            .catch(err => console.error(err))
        }

        else if(roleInfo[0].role.roleId == 2) {

            fetch("/MyParts/getWorkersServlet")
            .then(res => res.json())
            .then(json => resolve(json))
            .catch(err => console.error(err))
        }
    })
}

async function printUsers(json) {

    const tbody = document.getElementById("tbody")

    tbody.innerHTML = ""

    json.forEach(element => {

        let row = document.createElement("tr")
        row.innerHTML = `<td>
                            ${element.userId}
                        </td>
                        <td>
                            ${element.username}
                        </td>
                        <td>
                            ${element.role.roleName}
                        </td>
                        <td>
                            <a href="/MyParts/html/adminForm.html?login=${element.username}&roleId=${element.role.roleId}">Edytuj</a>
                        </td>`
        tbody.appendChild(row)
    })
}


function addUser() {

    let role = document.getElementById("rol").value
    let login = document.getElementById("login").value
    let pass = document.getElementById("pass").value


    let conf = confirm("Czy na pewno chcesz dodać nowego uzytkownika?")

    if(!conf) {
        exit()
    }

    fetch("/MyParts/addUserServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `role=${role}&login=${login}&password=${pass}`
    }).then(async res => {

    if(res.status === 202){
        showToast("Dodano użytkownika")
        allProducts = await getUsers(roleInfo)
        printUsers(allProducts)
    } else {
        showToast("Błąd dodowania użytkownika", "error")
    }
    })
    
}

async function checkRoleForPermissionFilter(roleInfo) {

        const addroleSelect = Array.from(document.getElementsByClassName("role"))
        const filterroleSelect = Array.from(document.getElementsByClassName("filterRole"))

        if(roleInfo[0].role.roleId != 3) {

            addroleSelect.forEach((e) => {
                e.setAttribute("hidden", "hidden")
            })
            filterroleSelect.forEach((e) => {
                e.setAttribute("hidden", "hidden")
            })
        }
}

function filtr(){

    const roleFilter = document.getElementById("roleFilter").value
    const loginFilter = document.getElementById("loginFilter").value.toLowerCase()
    const userId = document.getElementById("userId").value


    filtered = allProducts.filter(p => {

        let matchRole = !roleFilter || p.role.roleName === roleFilter
        let matchLogin = !loginFilter || p.username.toLowerCase().includes(loginFilter)
        let matchId = !userId || p.userId == userId

        return matchRole && matchLogin && matchId
    })

    printUsers(filtered)
}

function nameSort() {
    const login = document.getElementById("loginSort")

    filtr()

    if(login.lastChild.innerHTML === "↑"){

        filtered.sort((a, b) => a.username.localeCompare(b.username))
        login.lastChild.innerHTML = "↓"

    } else if(login.lastChild.innerText === "↓"){

        filtered.sort((a, b) => b.username.localeCompare(a.username))
        login.lastChild.innerHTML = "↑"
    }

    printUsers(filtered)
}

function idSort() {

    const id = document.getElementById("idSort")

    filtr()

    if(id.lastChild.innerHTML === "↑"){
        filtered.sort((a, b) => a.userId - b.userId)
        id.lastChild.innerHTML = "↓"
    } else if(id.lastChild.innerHTML === "↓"){
        filtered.sort((a, b) => b.userId - a.userId)
        id.lastChild.innerHTML = "↑"
    }

    printUsers(filtered)
}



function initFilters(){

    document.getElementById("loginFilter").addEventListener("input", filtr)
    document.getElementById("roleFilter").addEventListener("change", filtr)
    document.getElementById("userId").addEventListener("input", filtr)
}

function missInputInfo() {

    const form = document.getElementsByClassName("card")[0]

    console.log("dupa")

    form.append(`<p>Nie podano loginu lub hasła.</p>`)

}