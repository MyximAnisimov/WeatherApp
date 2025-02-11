package org.example.weatherapp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Route("")
public class MainView extends VerticalLayout {

    private final TextField latField = new TextField("Ширина");
    private final TextField lonField = new TextField("Долгота");
    private final Button showWeatherBtn = new Button("Показать погоду");

    private final Span weatherInfoLabel = new Span();
    private final Image weatherIcon = new Image();

    private final Grid<WeatherRequest> grid = new Grid<>(WeatherRequest.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${weather.api-key}")
    private String apiKey;

    private final WeatherRequestRepository weatherRepo;

    @Autowired
    public MainView(WeatherRequestRepository weatherRepo) {
        this.weatherRepo = weatherRepo;

        latField.setValue("55.7558");
        lonField.setValue("37.6173");

        add(latField, lonField, showWeatherBtn, weatherIcon, weatherInfoLabel);

        grid.setColumns("id", "time", "latitude", "longitude", "temperature", "description");
        add(grid);

        refreshGrid();

        showWeatherBtn.addClickListener(e -> {
            String lat = latField.getValue().trim();
            String lon = lonField.getValue().trim();
            if (!lat.isEmpty() && !lon.isEmpty()) {
                showWeatherInfo(lat, lon);
            }
        });
    }

    private void showWeatherInfo(String lat, String lon) {
        try {
            String url = String.format(
                    "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric&lang=ru",
                    lat, lon, apiKey
            );

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null) {
                weatherInfoLabel.setText("Не удалось получить данные о погоде");
                return;
            }

            Map<String, Object> mainData = (Map<String, Object>) response.get("main");
            Double temp = (Double) mainData.get("temp");

            List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
            Map<String, Object> weatherFirst = weatherList.get(0);
            String description = (String) weatherFirst.get("description");
            String iconCode = (String) weatherFirst.get("icon");

            String iconUrl = "http://openweathermap.org/img/wn/" + iconCode + "@2x.png";
            weatherIcon.setSrc(iconUrl);
            weatherIcon.setAlt("Weather Icon");

            String infoText = String.format("Температура: %.1f°C, Описание: %s", temp, description);
            weatherInfoLabel.setText(infoText);

            WeatherRequest wr = new WeatherRequest();
            wr.setLatitude(lat);
            wr.setLongitude(lon);
            wr.setTime(LocalDateTime.now());
            wr.setTemperature(temp);
            wr.setDescription(description);
            weatherRepo.save(wr);
            refreshGrid();

        } catch (Exception ex) {
            ex.printStackTrace();
            weatherInfoLabel.setText("Ошибка при получении или обработке данных о погоде");
        }
    }

    private void refreshGrid() {
        List<WeatherRequest> allRequests = weatherRepo.findAll();
        grid.setItems(allRequests);
    }
}
