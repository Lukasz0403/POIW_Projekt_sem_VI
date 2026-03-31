function login(){
    
    let login = document.getElementById('login').value;
    let pass =  document.getElementById('password').value;
    

    fetch("/MyParts/loginProcedureServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "login=" + login + "&pass=" + pass
    })
        .then(data => {
            console.log(data)
            if (data.status == 202) {
                window.location.href = "/MyParts/";
            } else {
                alert("Błędny login lub hasło");
            }
        })
        .catch(err => console.error(err));
}
window.onkeydown = function(e){
    
    if(e.keyCode === 13)
    login()
}