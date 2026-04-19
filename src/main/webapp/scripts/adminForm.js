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


        select.setAttribute("disabled", "disabled")

        const select = Array.from(document.getElementsByClassName("rol"))

        if(roleInfo[0].role.roleId != 3) {

            select.forEach((e) => {
                e.setAttribute("hidden", "hidden")
            })
        }

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
            showToast("Usunięto użytkownika")
            setTimeout(() => window.location.href = "adminTab.html", 1000);
        } else {
            showToast("Błąd usuwania użytkownika")
        }
        })
    }
}

function changeUser() {

    let login = document.getElementById("login").value
    let npass = document.getElementById("npass").value
    let role = document.getElementById("role").value
    let oldlogin = getUserCredentials("login")
    

    fetch("/MyParts/updateUserServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `role=${role}&newlogin=${login}&oldlogin=${oldlogin}&password=${npass}`
    }).then(res => {

        if(res.status === 202){

            showToast("Zaktualizowano użytkownika")
            setTimeout(() => window.location.href = "products.html", 1000);

        } else {
            showToast("Błąd aktualizacji użytkownika", "error")
            adjustForm()
        }
    })
    
}

function setPatternPass() {

    const npass = document.getElementById("npass")

    const rpass = document.getElementById("rpass")

    if(npass.value) {
        npass.setAttribute("pattern", "\\S{8,}")
        npass.setAttribute("required", "")
        npass.removeAttribute("disabled")
        rpass.setAttribute("pattern", npass.value)
        rpass.setAttribute("required", "required")
    } else {
        npass.removeAttribute("pattern")
        npass.removeAttribute("required")
        rpass.removeAttribute("pattern")
        rpass.removeAttribute("required")
        rpass.setAttribute("disabled", "disabled")
    }
}

