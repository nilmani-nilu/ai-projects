package sag.nilu.Weather_Report.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Weather API request")
public record WeatherRequest(@JsonProperty(required = true,
value = "location") @JsonPropertyDescription("The city and state e.g. Bhubaneswar, ODISHA") String location,
@JsonProperty(required = false) @JsonPropertyDescription("Optional State for India Cities Only. Use full name of State") String state,
@JsonProperty(required = false) @JsonPropertyDescription("Optional Country name") String country) {

}
