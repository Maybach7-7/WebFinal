
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

function getCookie(name) {
    const cookieValue = document.cookie
        .split('; ')
        .find(row => row.startsWith(name + '='));
    return cookieValue ? decodeURIComponent(cookieValue.split('=')[1]) : null;
}

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("real-form");
    const formErrors = document.getElementById("form-errors");
    const formMessage = document.getElementById("form-message");

    const regexes = {
        fullname: /^[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)? [А-ЯЁ][а-яё]+( [А-ЯЁ][а-яё]+)?$/,
        email: /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/,
        phone: /^((\+7|7|8)+([0-9]){10})$/
    };

    form.addEventListener("submit", async function (e) {
        e.preventDefault();
        formErrors.innerHTML = "";
        formMessage.textContent = "";

        const inputs = form.querySelectorAll("input, textarea, select");
        inputs.forEach(input => input.style.border = "");

        const errors = [];

        const fullname = form.fullname;
        const email = form.email;
        const phone = form.phone;
        const birthday = form.birthday;
        const genderRadios = form.querySelectorAll("input[name='gender']");
        const gender = [...genderRadios].find(r => r.checked)?.value;
        const languages = form.languages;
        const message = form.biography;

        // Валидация
        if (!regexes.fullname.test(fullname.value.trim())) {
            fullname.style.border = "2px solid red";
            errors.push("Введите корректное ФИО");
        }

        if (!regexes.email.test(email.value.trim())) {
            email.style.border = "2px solid red";
            errors.push("Введите корректный Email");
        }

        if (!regexes.phone.test(phone.value.trim())) {
            phone.style.border = "2px solid red";
            errors.push("Введите корректный телефон");
        }

        if (!gender) {
            genderRadios.forEach(r => r.parentElement.style.border = "2px solid red");
            errors.push("Выберите пол");
        }

        const selectedLanguages = [...languages.options].filter(o => o.selected).map(o => o.value);
        if (selectedLanguages.length === 0) {
            languages.style.border = "2px solid red";
            errors.push("Выберите хотя бы один язык");
        }

        if (message.value.trim() === "") {
            message.style.border = "2px solid red";
            errors.push("Напишите биографию");
        }

        if (errors.length > 0) {
            errors.forEach(msg => {
                const p = document.createElement("p");
                p.textContent = msg;
                p.style.color = "red";
                p.style.padding = "10px";
                formErrors.appendChild(p);
            });
            return;
        }

        const jsonData = {
            fullName: fullname.value.trim(),
            email: email.value.trim(),
            phone: phone.value.trim(),
            birthday: birthday.value.trim(),
            gender: gender,
            programmingLanguages: selectedLanguages,
            biography: message.value.trim()
        };

        try {
            const sessionId = getCookie("session_id"); // <-- Заменить на актуальное имя
            const method = sessionId ? "PUT" : "POST";
            const url = sessionId ? `/form/${sessionId}` : "/form";

            const response = await fetch(url, {
                method,
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json"
                },
                body: JSON.stringify(jsonData)
            });

            if (response.status === 202) {
                formMessage.innerHTML = "Форма успешно изменена";
                return;
            }

            const result = await response.json();

            if (response.ok) {
                form.reset();
                formMessage.innerHTML = `
                        <p>Ваш логин: ${result.login}</p>
                        <p>Ваш пароль: ${result.password}</p>
                    `;
                formMessage.style.color = "green";
            } else {
                formMessage.textContent = "Ошибка при отправке формы.";
                formMessage.style.color = "red";
            }
        } catch (error) {
            formMessage.textContent = "Ошибка соединения с сервером.";
            formMessage.style.color = "red";
        }
    });
});
