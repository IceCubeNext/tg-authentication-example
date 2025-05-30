window.addEventListener('DOMContentLoaded', async () => {
    const initData = window.Telegram.WebApp.initData;

    if (!initData) {
        document.getElementById('result').innerText = "initData not found.";
        return;
    }


    try {
        const response = await fetch("https://your_server.ru/init", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({initData})
        });

        const html = await response.text();
        document.body.innerHTML = html;
    } catch (err) {
        console.error(err);
        document.getElementById('err').innerText = err;
    }
});
