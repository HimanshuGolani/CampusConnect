package com.campusconnect.CampusConnect.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("config_campus_connect")
@Data
@NoArgsConstructor
public class ConfigCampusConnectEntity {

    private String key;
    private String value;


}
