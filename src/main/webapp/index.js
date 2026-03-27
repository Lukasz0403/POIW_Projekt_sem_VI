async function drawDashboard () {

    const id = document.getElementById("ident")
    const login = document.getElementById("login")
    const rola = document.getElementById("rola")

    let json = await getLoginInfo()

    id.append(" "+json.userId)
    login.append(" "+json.username)
    rola.append(" "+json.role.roleName)

}

window.onload = async function() {

    await checkSession()
    drawNavbar()
    drawDashboard()
}