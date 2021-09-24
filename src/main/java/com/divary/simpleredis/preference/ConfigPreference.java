package com.divary.simpleredis.preference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigPreference {

    @Value("${redis.hostname}")
    public String redisHostname;

    @Value("${redis.database.index}")
    public String redisDatabaseIndex;

    @Value("${redis.port}")
    public String redisPort;

    @Value("${redis.password}")
    public String redisPassword;

}
