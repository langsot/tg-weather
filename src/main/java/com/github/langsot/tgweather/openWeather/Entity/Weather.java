package com.github.langsot.tgweather.openWeather.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Weather {


    private Main main;

    private Descript descript;
}
