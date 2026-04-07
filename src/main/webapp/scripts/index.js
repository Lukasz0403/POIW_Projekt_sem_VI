async function drawDashboard (json) {

    const id = document.getElementById("ident")
    const login = document.getElementById("login")
    const rola = document.getElementById("rola")
    const data = document.getElementById("data")

    let formatMonth;

    if(json[1].monthValue < 10) {
        formatMonth = "0"+json[1].monthValue
    }

    id.append(" "+json[0].userId)
    login.append(" "+json[0].username)
    rola.append(" "+json[0].role.roleName)
    data.append(" "+json[1].dayOfMonth+"-"+formatMonth+"-"+json[1].year)
}

window.onload = async function() {

    let status = await checkSession()

    if(status) {
        let roleInfo = await getLoginInfo()

        drawNavbar()
        checkRole(roleInfo)
        drawDashboard(roleInfo)
    }

}


