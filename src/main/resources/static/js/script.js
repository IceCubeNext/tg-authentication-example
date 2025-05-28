window.addEventListener('DOMContentLoaded', async () => {
    const initData = Telegram.WebApp.initData;

    if (!initData) {
        document.getElementById('result').innerText = "initData not found.";
        return;
    }

    try {
        const response = await fetch("https://icecubenext.ru/test/init", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ initData })
        });

        const json = await response.json();

        document.getElementById('result').innerText = JSON.stringify(json, null, 2);
    } catch (err) {
        console.error(err);
        document.getElementById('result').innerText = "Error during authentication.";
    }
});
