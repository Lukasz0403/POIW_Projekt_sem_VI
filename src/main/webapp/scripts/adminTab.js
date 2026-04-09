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

    let tbody = document.getElementById("tbody")

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

    let role = document.getElementById("role").value
    let login = document.getElementById("login").value
    let pass = document.getElementById("pass").value

    if(login == "" || pass == "") {

        alert("Nie podano loginu lub hasła.")
    }
    else {

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
            alert("Dodano użytkownika")
            allProducts = await getUsers(roleInfo)
            printUsers(allProducts)
        } else {
            alert("Błąd dodawania użytkownika")
        }
        })
    }
}

async function checkRoleForPermissionFilter(roleInfo) {

        const addroleSelect = document.getElementById("role")
        const filterroleSelect = document.getElementById("roleFilter")

        if(roleInfo[0].role.roleId != 3) {

            addroleSelect.setAttribute("disabled", "disabled")
            filterroleSelect.setAttribute("disabled", "disabled")
        }
}

function filtr(){

    const roleFilter = document.getElementById("roleFilter").value
    const loginFilter = document.getElementById("loginFilter").value.toLowerCase()
    const userId = document.getElementById("userId").value
    const sort = document.getElementById("sort").value

    console.log(roleFilter)

    let filtered = allProducts.filter(p => {

        let matchRole = !roleFilter || p.role.roleName === roleFilter
        let matchLogin = !loginFilter || p.username.toLowerCase().includes(loginFilter)
        let matchId = !userId || p.userId == userId

        return matchRole && matchLogin && matchId
    })

    
    if(sort === "az"){
        filtered.sort((a, b) => a.username.localeCompare(b.username))
    }
    if(sort === "za"){
        filtered.sort((a, b) => b.username.localeCompare(a.username))
    }
    if(sort === "increment"){
        filtered.sort((a, b) => a.userId - b.userId)
    }
    if(sort === "decrement"){
        filtered.sort((a, b) => b.userId - a.userId)
    }

    printUsers(filtered)
}


function initFilters(){

    document.getElementById("loginFilter").addEventListener("input", filtr)
    document.getElementById("roleFilter").addEventListener("change", filtr)
    document.getElementById("userId").addEventListener("input", filtr)
    document.getElementById("sort").addEventListener("input", filtr)
}