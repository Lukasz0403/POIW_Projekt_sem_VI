function go(section){
    
    if(section === "products"){
        
         fetch("/MyParts_DEMO/productListWindowServlet")
            .then(res => res.text())
            .then(html => {
                document.getElementById("content").innerHTML = html;
            });
    }
    
    if(section === "dashboard"){
        fetch("/MyParts_DEMO/mainWindowServlet")
            .then(res => res.text())
            .then(html => {
                document.getElementById("content").innerHTML = html;
            });
         }
    
}


function login(){
    
    const login = document.getElementById('login').value;
    const pass =  document.getElementById('password').value;
    
  fetch("/MyParts_DEMO/loginProcedure", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "login=" + login + "&pass=" + pass
    })
    .then(res => res.text())
    .then(data => {
        if (data === "OK") {
            window.location.href = "mainWindow.html";
        } else {
            alert("Błędny login lub hasło");
        }
    })
    .catch(err => console.error(err));
}

function filtr() {
    const kategoria = document.getElementById("kategoria").value;
    const cena = document.getElementById("cena").value;

    fetch(`/MyParts_DEMO/productListWindowServlet?kategoria=${kategoria}&cena=${cena}`)
        .then(res => res.text())
        .then(html => {
            document.getElementById("content").innerHTML = html;
        });
}

function logout() {
    
    window.location.href = "index.html";
    
}

function productInfo(id) {
    fetch("/MyParts_DEMO/productInfoServlet?id=" + id)
        .then(res => res.text())
        .then(html => {
            document.getElementById("content").innerHTML = html;
        });
}