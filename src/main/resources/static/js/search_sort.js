function computeSortValues(sortBy, sortDir) {
    if (sortBy === "time") {
        return {
            primary: "takeoffDate," + sortDir,
            secondary: "takeoffTime," + sortDir,
            hasSecondary: true
        };
    }
    return {
        primary: "baseFare," + sortDir,
        secondary: "",
        hasSecondary: false
    };
}

function getCheckedValue(name) {
    const el = document.querySelector(`input[name="${name}"]:checked`);
    return el ? el.value : null;
}

function setSortHiddenInputs() {
    const sortBy = getCheckedValue("sortBy") || "fare";
    const sortDir = getCheckedValue("sortDir") || "asc";
    const sort = computeSortValues(sortBy, sortDir);

    const primary = document.getElementById("sortPrimary");
    const secondary = document.getElementById("sortSecondary");

    primary.value = sort.primary;

    if (sort.hasSecondary) {
        secondary.disabled = false;
        secondary.value = sort.secondary;
    } else {
        secondary.disabled = true;
        secondary.value = "";
    }
}

function initSearchSortForm() {
    const form = document.getElementById("searchForm");
    if (!form) return;

    setSortHiddenInputs();

    const triggers = [
        ...document.querySelectorAll('input[name="sortBy"]'),
        ...document.querySelectorAll('input[name="sortDir"]'),
        document.getElementById("size")
    ].filter(Boolean);

    for (const el of triggers) {
        el.addEventListener("change", () => {
            setSortHiddenInputs();
        });
    }

    form.addEventListener("submit", () => {
        const page = document.getElementById("page");
        if (page) page.value = "0";
        setSortHiddenInputs();
    });
}

document.addEventListener("DOMContentLoaded", initSearchSortForm);
