package com.mth.resume_app.models.records;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record ErrorDetails(String error, String message) {
}
