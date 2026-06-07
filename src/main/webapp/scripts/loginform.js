function login() {
    const loginVal = document.getElementById('login').value;
    const passVal = document.getElementById('password').value;

    
    document.getElementById("spinner").classList.remove("hidden");
    document.querySelector("button").disabled = true;

    fetch("/MyParts/loginProcedureServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "login=" + loginVal + "&pass=" + passVal
    })
            .then(data => {
                if (data.status == 202) {
                    window.location.href = "/MyParts/";
                } else {
                    
                    document.getElementById("spinner").classList.add("hidden");
                    document.querySelector("button").disabled = false;
                    alert("Błędny login lub hasło");
                }
            })
            .catch(err => {
                document.getElementById("spinner").classList.add("hidden");
                document.querySelector("button").disabled = false;
                console.error(err);
            });
}
window.onkeydown = function(e){
    
    if(e.keyCode === 13)
    login()
}