(function () {
    const form = document.getElementById("seatForm");
    const seatIdInput = document.getElementById("seatIdInput");
    if (!form || !seatIdInput) return;

    const pollUrl = form.dataset.pollUrl;
    if (!pollUrl) return;

    function seatCells() {
        return Array.from(document.querySelectorAll("td.seatmap-seat"));
    }

    function setReserved(cell, reserved) {
        cell.dataset.reserved = reserved ? "true" : "false";
        cell.setAttribute("aria-disabled", reserved ? "true" : "false");

        cell.classList.toggle("seatmap-seat-reserved", reserved);
        cell.classList.toggle("seatmap-seat-free", !reserved);
    }

    document.addEventListener("click", function (e) {
        const cell = e.target.closest("td.seatmap-seat");
        if (!cell) return;
        if (cell.dataset.reserved === "true") return;

        const seatId = cell.dataset.seatId;
        if (!seatId) return;

        seatIdInput.value = seatId;
        form.submit();
    });

    async function poll() {
        try {
            const res = await fetch(pollUrl, {
                method: "GET",
                credentials: "same-origin"
            });

            if (!res.ok) return;

            const data = await res.json();
            if (!data || !Array.isArray(data.takenSeats)) return;

            const taken = new Set(
                data.takenSeats
                    .filter(s =>
                        s &&
                        typeof s.seatRow === "number" &&
                        typeof s.seatColumn === "string" &&
                        s.seatColumn.length > 0
                    )
                    .map(s => String(s.seatRow) + s.seatColumn.toUpperCase())
            );

            for (const cell of seatCells()) {
                const seatId = cell.dataset.seatId;
                if (!seatId) continue;
                setReserved(cell, taken.has(seatId));
            }

        } catch (_) {
            return;
        }
    }

    setInterval(poll, 2500);
})();
