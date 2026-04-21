function displayCurrentMonth() {
    const now = new Date();
    const monthNames = [
        "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec",
        "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"
    ];
    const fullLabel = `${monthNames[now.getMonth()]} ${now.getFullYear()}`;
    document.querySelector(".summary-header h3").textContent = `Dane sprzedażowe z miesiąca: ${fullLabel}`;
    document.getElementById("month").textContent = fullLabel;
}

function renderSalesMAIN(sales){

    const table = document.getElementById("products_table");
    table.innerHTML = "";

    sales.forEach((p, i) => {

        let date = new Date(p.saleDate)

        let hour
        let minute
        let second
        let year
        let month
        let day

        if(date.getHours() < 10) {
            hour = "0" + date.getHours()
        } else {
            hour = date.getHours()
        }
        if(date.getMinutes() < 10) {
            minute = "0" + date.getMinutes()
        } else {
            minute = date.getMinutes()
        }
        if(date.getSeconds() < 10) {
            second = "0" + date.getSeconds()
        } else {
            second = date.getSeconds()
        }
        if(date.getMonth() < 10) {
            month = "0" + date.getMonth()
        } else {
            month = date.getMonth()
        }
        if(date.getDay() < 10) {
            day = "0" + date.getDay()
        } else {
            day = date.getDay()
        }

        year = date.getFullYear()
        
        table.innerHTML += `
            <tr>
                <td>${p.productId.categoryId.name}</td>
                <td>${p.productId.name}</td>
                <td>${p.productId.brand}</td>
                <td>${p.productId.price} zł</td>
                <td>${p.quantity}</td>
                <td>${year}-${month}-${day} ${hour}:${minute}:${second}</td>
            </tr>
        `;
    });
}

function computeSummary(sales) {
    const now = new Date();
    const currentMonth = now.getMonth();
    const currentYear = now.getFullYear();

    const thisMonth = sales.filter(s => {
        const d = new Date(s.saleDate);
        return d.getMonth() === currentMonth && d.getFullYear() === currentYear;
    });

    const totalItems = thisMonth.reduce((sum, s) => sum + s.quantity, 0);
    const totalSales = thisMonth.reduce((sum, s) => sum + (s.productId.price * s.quantity), 0);

    document.getElementById("total_items").textContent = totalItems;
    document.getElementById("total_sales").textContent = totalSales.toFixed(2) + " zł";

    return thisMonth;
}

function buildChart(sales) {
    const now = new Date();
    const currentMonth = now.getMonth();
    const currentYear = now.getFullYear();
    const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

    const dailyTotals = Array(daysInMonth).fill(0);

    sales.forEach(s => {
        const d = new Date(s.saleDate);
        if (d.getMonth() === currentMonth && d.getFullYear() === currentYear) {
            const day = d.getDate() - 1;
            dailyTotals[day] += s.productId.price * s.quantity;
        }
    });

    const labels = Array.from({length: daysInMonth}, (_, i) => i + 1);

    const ctx = document.getElementById('salesChart');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Sprzedaż (zł)',
                data: dailyTotals,
                backgroundColor: '#cc0000',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

function goHome() {
    window.location.href = "index.html";
}

function goReport() {
    window.location.href = "salesReport.html";
}

function printPDF() {
    window.print();
}

window.onload = async function() {
    let status = await checkSession();
    if(status) {
        let roleInfo = await getLoginInfo();
        drawNavbar();
        checkRole(roleInfo);
        displayCurrentMonth();

        const res = await fetch("/MyParts/getSales");
        const sales = await res.json();

        computeSummary(sales);
        buildChart(sales);
    }
}