<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.maybach7.formhandler.validator.InputError" %>
<%@ page import="java.util.List" %>
<%@ page import="com.maybach7.formhandler.util.CookiesUtil" %>

<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Сайтик</title>
    <link
            href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap"
            rel="stylesheet">

    <link rel="stylesheet" href="/static/form/styles.css">
    <link rel="stylesheet" type="text/css" href="/static/form/slick-1.8.1/slick/slick.css">
    <link rel="stylesheet" type="text/css" href="/static/form/slick-1.8.1/slick/slick-theme.css">

    <script defer src="/static/form/jquery-3.4.1.min.js"></script>
    <script defer src="/static/form/slick-1.8.1/slick/slick.min.js"></script>

    <script defer src="/static/form/script.js"></script>
</head>

<body>
<header class="header" id="header">
    <div class="wrapper">
        <div class="logo">
            <img id="logo" src="/static/form/content/img/chill.png" alt="logo">
        </div>

        <nav class="nav">
            <ul>
                <li><a href="#header" onclick="closeMenu()">Home</a></li>
                <li><a href="#memes" onclick="closeMenu()">Мемасы</a></li>
                <li><a href="#motivation" onclick="closeMenu()">Мотиваторы</a></li>
                <li><a href="#form" onclick="closeMenu()">Форма</a></li>
                <li class="dropdown">
                    <span class="drop" onclick="showMenu()">Контакты ▸</span>
                    <div class="dropdown-content" id="dropdown-content">
                        <a href="https://t.me/Maybachh7">Telegram</a>
                        <a href="https://vk.com/benzesdenes">ВКонтакте</a>
                    </div>
                </li>
                <li>
                    <% if (CookiesUtil.getCookie(request, "session_id").isPresent()) { %>
                    <a href="/logout">Выйти</a>
                    <% } else { %>
                    <a href="/login">Войти</a>
                    <% } %>
                </li>
            </ul>
        </nav>

        <div class="burger">
            <span></span>
        </div>
    </div>
</header>

<div class="video-bg">
    <video src="/static/form/content/video/rain.mp4" autoplay muted loop></video>
    <div class="effects"></div>
    <div class="wrapper">
        <div class="video-bg-content">
            <h2>Сайт, чтобы погрустить холодным дождливым вечером</h2>
            <a class="btn" href="#memes">Смотреть картинки</a>
        </div>
    </div>
</div>

<main id="content">
    <div class="slider main" id="memes">
        <div class="slider-name">
            <div class="slider-main-name">
                <p>Мемчики</p>
            </div>
            <div class="slider-description">
                <p>Тут ты можешь просто забыть про все свои дела и посмотреть смешные
                    картинки.</p>
            </div>
        </div>
        <div class="gallery">
            <div>
                <img class="gallery-image" src="/static/form/content/img/memes/cats1.gif"
                     alt="kanye west">
            </div>
            <div>
                <img class="gallery-image" src="/static/form/content/img/memes/cats2.gif"
                     alt="kanye west">
            </div>
            <div>
                <img class="gallery-image" src="/static/form/content/img/memes/cats3.png"
                     alt="kanye west">
            </div>
            <div>
                <img class="gallery-image" src="/static/form/content/img/memes/cats4.gif"
                     alt="kanye west">
            </div>
            <div>
                <img class="gallery-image" src="/static/form/content/img/memes/memes5.gif"
                     alt="kanye west">
            </div>
            <div>
                <img class="gallery-image" src="/static/form/content/img/memes/memes6.gif"
                     alt="kanye west">
            </div>
        </div>
    </div>

    <div class="background">
        <div class="slider main" id="motivation">
            <div class="slider-name">
                <div class="slider-main-name">
                    <p>Мотиваторы</p>
                </div>
                <div class="slider-description">
                    <p>Как известно: "Мотиуацию надо паааднять!". Данная подборка
                        фотокарточек позволит тебе
                        сделать
                        перерыв и переосмыслить твои дальнейшие планы.</p>
                </div>
            </div>
            <div class="gallery">
                <div>
                    <img class="gallery-image" src="/static/form/content/img/motivation/1.jpg"
                         alt="kanye west">
                </div>
                <div>
                    <img class="gallery-image" src="/static/form/content/img/motivation/2.jpg"
                         alt="dwight">
                </div>
                <div>
                    <img class="gallery-image" src="/static/form/content/img/motivation/3.jpg"
                         alt="billie eilish">
                </div>
                <div>
                    <img class="gallery-image" src="/static/form/content/img/motivation/4.jpg"
                         alt="mr trump">
                </div>
                <div>
                    <img class="gallery-image" src="/static/form/content/img/motivation/5.jpg"
                         alt="siu">
                </div>
                <div>
                    <img class="gallery-image" src="/static/form/content/img/motivation/6.jpg"
                         alt="sigma face">
                </div>
                <div>
                    <img class="gallery-image" src="/static/form/content/img/motivation/7.jpg"
                         alt="the great gatsby">
                </div>
                <div>
                    <img class="gallery-image" src="/static/form/content/img/motivation/8.jpg"
                         alt="joker">
                </div>
            </div>
        </div>
    </div>

    <div class="mood-form main" id="form">
        <div class="mood-form-main-name">
            <p>Формочка</p>
        </div>
        <div class="mood-form-description">
            <p>Здесь ты можешь поделиться своим настроением, рассказать, что интересного
                произошло с тобой за
                последнее
                время или просто написать свои мысли.</p>
        </div>


        <form id="real-form" action="/form" method="POST">

            <% boolean hasErrors = false;
                List<InputError> errors = (List<InputError>) request.getAttribute("errors");
                if (errors != null) {
                    hasErrors = true;
                    for (var error : errors) {
            %>
            <p style="color:red; padding: 10px">
                <%=error.getMessage()%>
            </p>
            <% }
            } %>

            <label for="fullname">ФИО <span class="required">*</span></label>
            <input type="text" id="fullname" name="fullname" required
                   value="<%= request.getAttribute("fullname") !=null ?
                                                        request.getAttribute("fullname") : "" %>"
                <%= hasErrors && errors.stream().anyMatch(error ->
                                                        error.getId().equals("fullname")) ? "style=\"border: 2px solid red;\"" : ""%>>

            <label for="email">Email <span class="required">*</span></label>
            <input type="email" id="email" name="email" required
                   value="<%= request.getAttribute("email") !=null ?
                                                            request.getAttribute("email") : "" %>"
                <%= hasErrors && errors.stream().anyMatch(error -> error.getId().equals("email")) ? "style=\"border: 2px solid red;\"" : ""%>>

            <label for="phone">Телефон <span
                    class="required">*</span></label>
            <input type="text" id="phone" name="phone" required
                   value="<%= request.getAttribute("phone") !=null ?
                                                                request.getAttribute("phone") : "" %>"
                <%= hasErrors && errors.stream().anyMatch(error -> error.getId().equals("phone")) ? "style=\"border: 2px solid red;\"" : ""%>>

            <label for="birthday">Дата рождения <span
                    class="required">*</span></label>
            <input type="date" id="birthday" name="birthday"
                   required value="<%= request.getAttribute("birthday") !=null ? request.getAttribute("birthday")
                                                                    : "" %>">

            <label>Пол <span class="required">*</span></label>
            <div class="radio-group">
                <input type="radio" required name="gender"
                       value="male" id="male"
                        <%=request.getAttribute("gender") != null
                           && "male"
                                   .equalsIgnoreCase(request.getAttribute("gender").toString())
                                ? "checked" : "" %> />
                <label for="male">Мужской</label>
            </div>

            <div class="radio-group">
                <input type="radio" required name="gender"
                       value="female" id="female"
                        <%=request.getAttribute("gender") != null
                           && "female"
                                   .equalsIgnoreCase(request.getAttribute("gender").toString())
                                ? "checked" : "" %> />
                <label for="female">Женский</label>
            </div>


            <label for="languages">Любимые языки <span
                    class="required">*</span></label>
            <select id="languages" name="languages"
                    multiple="multiple" required>
                <% String selectedLanguagesRaw = (String)
                        request.getAttribute("languages");
                    String[]
                            selectedLanguages = selectedLanguagesRaw != null ?
                            selectedLanguagesRaw.split(",") : new String[0];
                    String[] allLanguages = {"Pascal", "C", "C++"
                            , "JavaScript", "PHP", "Python", "Java"
                            , "Haskel", "Clojure", "Prolog", "Scala"};
                    for (String lang : allLanguages) {
                        boolean
                                isSelected = Arrays.asList(selectedLanguages).contains(lang.toLowerCase());
                %>
                <option value="<%= lang.toLowerCase() %>"
                        <%=isSelected ? "selected" : "" %>><%= lang
                %>
                </option>
                <% } %>
            </select>

            <label for="biography">Биография <span
                    class="required">*</span></label>
            <textarea id="biography" name="biography" rows="5"
                      required
                      placeholder="Я родился в Москве в 70-м на краю города"><%= request.getAttribute("biography") != null ? request.getAttribute("biography") : "" %></textarea>

            <button type="submit" class="btn">Отправить</button>
            <div id="form-message">
                <% if (request.getAttribute("login") != null) {%>
                <p>Ваш логин: ${login}</p>
                <%}%>

                <% if (request.getAttribute("password")
                       != null) {%>
                <p>Ваш пароль: ${password}</p>
                <%}%>
            </div>
        </form>
    </div>
</main>

<footer>
    <div class="wrapper">
        <div class="footer-name">
            <span>ООО "От печали до радости"</span>
        </div>

        <div class="footer-menu">
            <ul>
                <li><a href="#header">Home</a></li>
                <li class="mobile-hide"><a href="#memes">Мемасы</a></li>
                <li class="mobile-hide"><a href="#motivation">Мотиваторы</a></li>
                <li class="mobile-hide"><a href="#form">Рассказать что-нибудь</a></li>
            </ul>
        </div>
    </div>
</footer>
</body>

</html>