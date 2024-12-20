package com.campusconnect.CampusConnect.cache;

import com.campusconnect.CampusConnect.entity.ConfigCampusConnectEntity;
import com.campusconnect.CampusConnect.repositories.ConfigCampusConnectRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class AppCache {

    public enum KEYS {
        SCRAPPING_LINK
    }

    @Autowired
    private ConfigCampusConnectRepository configCampusConnectRepository;

    public Map<String,String> APP_CACHE;

    @PostConstruct
    public void inti(){
        APP_CACHE = new HashMap<>();
        List<ConfigCampusConnectEntity> configs = configCampusConnectRepository.findAll();
        for (ConfigCampusConnectEntity configCampusConnectEntity : configs){
            APP_CACHE.put(configCampusConnectEntity.getKey(),configCampusConnectEntity.getValue());
        }
    }


}
