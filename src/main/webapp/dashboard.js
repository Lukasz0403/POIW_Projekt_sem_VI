function loadDashboard(){

    document.getElementById("content").innerHTML = `
        <h1>Witamy w aplikacji 👋</h1>
        <div class="card">
            <h3>Dane użytkownika:</h3>
            <p id="imie"><b>Imię:</b></p>
            <p id="nazwisko"><b>Nazwisko:</b></p>
            <p id="ident"><b>ID:</b></p>
            <p id="login"><b>Login:</b></p>
            <hr>
            <p id="data"><b>Data logowania:</b></p>
            <p id="rola"><b>Zakres uprawnień:</b></p>
        </div>
    `;

    getLoginInfo();
}


