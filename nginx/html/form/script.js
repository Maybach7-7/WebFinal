
document.querySelector('.burger').addEventListener('click', function () {
    this.classList.toggle('active');
    $('body').toggleClass('noscroll');
    document.querySelector('.nav').classList.toggle('open');
});



function showMenu() {
    document.getElementById('dropdown-content').classList.toggle("show");
}

function closeMenu() {
    if (document.querySelector('.burger').classList.contains('active')) {
        document.querySelector('.burger').classList.remove('active');
        document.querySelector('body').classList.remove('noscroll');
        document.querySelector('.nav').classList.toggle('open');
    }
}


window.onclick = function (event) {
    if (!event.target.matches('.drop')) {
        var dropdowns = document.getElementsByClassName('dropdown-content');
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}

$(document).ready(function () {
    $(".gallery").slick({
        dots: true,
        lazyLoad: "ondemand",
        responsive: [{
            breakpoint: 768,
            settings: {
                arrows: false,
                slidesToShow: 1
            }
        }],
        slidesToScroll: 1,
        slidesToShow: 1
    });
});

// document.addEventListener("DOMContentLoaded", () => {
//     const form = document.getElementById("real-form");
//     const formMessage = document.getElementById("form-message");

//     const regexes = {
//         fullname: /(^[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)? [А-ЯЁ][а-яё]+( [А-ЯЁ][а-яё]+)?$)/,
//         email: /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/,
//         phone: /^((\+7|7|8)+([0-9]){10})$/
//     };

//     form.addEventListener("submit", async function (e) {
//         e.preventDefault();
//         formMessage.textContent = "";

//         // Удаляем старые ошибки
//         form.querySelectorAll(".error-message").forEach(e => e.remove());

//         let valid = true;

//         const fullname = form.fullname;
//         const email = form.email;
//         const phone = form.phone;
//         const genderRadios = form.querySelectorAll("input[name='gender']");
//         const genderSelected = [...genderRadios].some(r => r.checked);
//         const languages = form.languages;
//         const message = form.message;

//         const inputs = form.querySelectorAll("input, textarea, select");
//         inputs.forEach(input => input.style.border = "");

//         // Показ ошибки под элементом
//         function showError(inputElement, message) {
//             inputElement.style.border = "2px solid red";
//             const errorDiv = document.createElement("div");
//             errorDiv.className = "error-message";
//             errorDiv.style.color = "red";
//             errorDiv.style.fontSize = "0.85em";
//             errorDiv.textContent = message;
//             inputElement.insertAdjacentElement("afterend", errorDiv);
//         }

//         // ФИО
//         if (!regexes.fullname.test(fullname.value.trim())) {
//             showError(fullname, "Введите корректное ФИО");
//             valid = false;
//         }

//         // Email
//         if (!regexes.email.test(email.value.trim())) {
//             showError(email, "Введите корректный Email");
//             valid = false;
//         }

//         // Телефон
//         if (!regexes.phone.test(phone.value.trim())) {
//             showError(phone, "Введите корректный телефон");
//             valid = false;
//         }

//         // Gender
//         if (!genderSelected) {
//             const genderGroup = genderRadios[genderRadios.length - 1];
//             showError(genderGroup, "Выберите пол");
//             valid = false;
//         }

//         // Языки
//         const selectedLanguages = [...languages.options].filter(o => o.selected).map(o => o.value);
//         if (selectedLanguages.length === 0) {
//             showError(languages, "Выберите хотя бы один язык");
//             valid = false;
//         }

//         // Биография
//         if (message.value.trim() === "") {
//             showError(message, "Напишите биографию");
//             valid = false;
//         }

//         if (!valid) return;

//         // Формируем JSON
//         const gender = [...genderRadios].find(r => r.checked)?.value;
//         const jsonData = {
//             fullname: fullname.value.trim(),
//             email: email.value.trim(),
//             phone: phone.value.trim(),
//             gender: gender,
//             languages: selectedLanguages,
//             message: message.value.trim()
//         };

//         try {
//             const response = await fetch("/form", {
//                 method: "POST",
//                 headers: {
//                     "Content-Type": "application/json",
//                     "Accept": "application/json"
//                 },
//                 body: JSON.stringify(jsonData)
//             });

//             const result = await response.json();

//             if (response.ok) {
//                 form.reset();
//                 formMessage.textContent = result.message || "Форма успешно отправлена!";
//                 formMessage.style.color = "green";
//             } else {
//                 formMessage.textContent = result.error || "Ошибка при отправке формы.";
//                 formMessage.style.color = "red";
//             }
//         } catch (error) {
//             formMessage.textContent = "Ошибка соединения с сервером.";
//             formMessage.style.color = "red";
//         }
//     });
// });