package sag.nilu.Weather_Report;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class WeatherReportApplication {
	


	public static void main(String[] args) {
		SpringApplication.run(WeatherReportApplication.class, args);
	}
	

}
