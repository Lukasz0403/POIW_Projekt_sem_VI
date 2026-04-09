window.onload = async function() {

    let status = await checkSession()

    if(status) {
        let roleInfo = await getLoginInfo()
        drawNavbar()
        checkRole(roleInfo)
        adjustForm()
        permissionAdjust(roleInfo)
    }

}

function getUserCredentials(data){
    const params = new URLSearchParams(window.location.search);
    return params.get(data);
}

function adjustForm() {

    const select = document.getElementById("role")
    const login = document.getElementById("login")

    let username = getUserCredentials("login")
    let role = getUserCredentials("roleId")

    select.value = role
    login.value = username
}

function permissionAdjust(roleInfo) {

    if(roleInfo[0].role.roleId == 2) {

        const select = document.getElementById("role")

        select.setAttribute("disabled", "disabled")

    }
}

function removeUser() {

    let conf = confirm("Czy na pewno chcesz usunąć tego użytkownika?")

    if(conf) {

        let login = getUserCredentials("login")
        
        console.log(login)

        fetch("/MyParts/removeUserServlet", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: `login=${login}`
        }).then(res => {

        if(res.status === 202){
            alert("Usunięto użytkownika")
            window.location.href = "adminTab.html"
        } else {
            alert("Błąd usuwania użytkownika")
        }
        })
    }
}

function changeUser() {

    let npass = document.getElementById("npass").value
    let rpass = document.getElementById("rpass").value


    if(!(npass === rpass)) {
        alert("Błędnie podano nowe hasło.")
        window.location.href = `adminForm.html?login=${getUserCredentials("login")}&roleId=${getUserCredentials("roleId")}`;
    }
    else {

        let role = document.getElementById("role").value
        let login = document.getElementById("login").value
        let pass = document.getElementById("rpass").value

        fetch("/MyParts/updateUserServlet", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: `role=${role}&login=${login}&password=${pass}`
        }).then(res => {

        if(res.status === 202){
            alert("Zaktualizowano użytkownika")
            window.location.href = "adminTab.html"
        } else {
            alert("Błąd aktualizacji użytkownika")
        }
        })
    }
}

