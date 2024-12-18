    package com.campusconnect.CampusConnect.dto;

    import com.fasterxml.jackson.databind.annotation.JsonSerialize;
    import jakarta.validation.constraints.NotNull;
    import lombok.Data;

    @Data
    public class UniversityNameListDTO {


        @NotNull
        @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
        private String id;

        @NotNull(message = "university name cannot be empty")
        private String nameOfUniversity;

    }
