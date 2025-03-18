let popup = document.querySelector(".popup");
let popupContent = document.querySelector(".popup-content");
let form = document.querySelector("#form");

function showPopup() {
    popup.style.display = "block";
    // document.body.style.overflowY = "hidden";
    window.history.pushState({popup: true}, "", "#popup");
}

function hidePopup() {
    popup.style.display = "none";
    // document.body.style.overflowY = "hidden";
    window.history.back();
}

function saveInLocalStorage() {
    let inputs = form.querySelectorAll("input");
    inputs.forEach(function (input) {
        localStorage.setItem(input.id, input.value);
    });
}

window.addEventListener("DOMContentLoaded", function () {
    let inputs = form.querySelectorAll("input");
    inputs.forEach(function (input) {
        const storedValue = localStorage.getItem(input.id);

        if (storedValue) {
            input.value = storedValue;
        }
    });

    let buttonOpenForm = document.querySelector("#open-form");
    buttonOpenForm.addEventListener("click", showPopup);

    let buttonCLoseForm = document.querySelector("#close-form");
    buttonCLoseForm.addEventListener("click", hidePopup);

    window.addEventListener("popstate", function () {
        if (this.window.location.hash.match(/^#popup$/)) {
            popup.style.display = "block";
        } else {
            popup.style.display = "none";
        }
    });
});
