package sag.nilu.First_Ai_Rag.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCityResponse(@JsonPropertyDescription("This is the city name") String Answer) {

}
