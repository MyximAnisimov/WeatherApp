Визуализатор погоды
======

Способы запуска приложения
---

1. Можно запустить программу через IDE при помощи кнопки "Run"
(перед запуском необходимо в resources/application.properties в строке weather.api-key=WEATHER_API_KEY
внести действительный ключ, иначе программа выдаст ошибку 401)

2. Запуск готового джарника через команду:

    ```
    java -jar target/WeatherApp-0.0.1-SNAPSHOT.jar
    ```

Приложение работает по адресу ```http://localhost:8080/```

Скриншоты с примерами работы программы
---

1. Экран при первой загрузке


<img src="screenshots/1.PNG" alt="Описание изображения" style="border: 2px solid #ccc;">


2. Экран после введённого запроса
<img src="screenshots/2.PNG" alt="Описание изображения" style="border: 2px solid #ccc;">

3. Экран после введённого запроса
<img src="screenshots/3.PNG" alt="Описание изображения" style="border: 2px solid #ccc;">

4. Пример сохранения истории запросов в таблице
<img src="screenshots/4.PNG" alt="Описание изображения" style="border: 2px solid #ccc;">

5. Пример сохранения истории запросов в таблице
<img src="screenshots/5.PNG" alt="Описание изображения" style="border: 2px solid #ccc;">


 
