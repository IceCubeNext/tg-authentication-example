window.addEventListener('DOMContentLoaded', async () => {
    const initData = window.Telegram.WebApp.initData;

    if (!initData) {
        document.getElementById('result').innerText = "initData not found.";
        return;
    }


    try {
        const param = new URLSearchParams({initData: initData});
        const url = `https://icecubenext.ru/init?${param}`;
        await fetch(url)
            .then(response => response.text())
            .then(html => document.body.innerHTML = html);
    } catch (err) {
        console.error(err);
        document.getElementById('err').innerText = err;
    }
});
