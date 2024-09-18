package sag.nilu.First_Ai_Rag.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetTitleResponse(@JsonPropertyDescription("This is the title name") String  answer) {

}
