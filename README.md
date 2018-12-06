# Captcha

**ОПИСАНИЕ**

HTTP- сервис, обеспечивающий простой механизм выявления ботов.
Для доступа пользователя на скрытую страницу сервис предоставляет пользователю картинку с символами, которые пользователь должен ввести вручную.

Сервис выполнен в соответствии с ТЗ.pdf
(см. файл в папке проекта).

**Замечания:**
- Время жизни теста на данный момент задается константой.
- Web-приложение создано для иллюстрации работы сервиса, поэтому оно несовершенно с точки зрения информационной безопасности и юзабилити.

**ИНСТРУКЦИЯ ПО ЗАПУСКУ**

**Коротко:**

- Скачайте и запустите файл target/captcha-\*.war.
- Перейдите по адресу: http://localhost:8080/.

**Подробно:**

- На вашем компьютере должна быть установлена Java 8 (вероятнее всего, с более поздними версиями Java приложение тоже будет работать).
Если у вас не установлена Java, загрузите её с сайта https://www.java.com/ru/download/ и установите на свой компьютер.
- Скачайте и запустите приложение:

Вариант 1: Нажмите на зеленую кнопку "Clone or download" и затем нажмите "Download ZIP". Начнется скачивание. Скачав архив, распакуйте его и, если вы работаете в Windows, запустите двойным кликом файл run.bat.

Вариант 2: Зайдите в проекте в папку target, нажмите на файл с расширением .war, затем нажмите на кнопку "Download". Начнется скачивание. В командной строке перейдите в папку, в которую вы скачали файл. Затем выполните команду: "java -jar captcha-\*.war" (вместо "captcha-\*" введите полное имя файла).

- В окне консоли начнется запуск приложения. Дождитесь, когда он отработает (обычно меньше минуты) и в одной из последних строк (см. последние 10 строк) появится фраза: "Started CaptchaApplication...".
- В вашем браузере введите в адресную строку: http://localhost:8080/ и нажмите Enter (порт 8080 должен быть свободен). Должна открыться страница с заголовком "Стартовая страница".
- Для завершения работы приложения закройте окно консоли.
