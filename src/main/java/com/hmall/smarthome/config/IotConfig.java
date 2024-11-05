package com.hmall.smarthome.config;


import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.region.Region;
import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "iot")
public class IotConfig {

    private String projectid;

    private String endpoint;

    private String token;

    private String ak;

    private String sk;

    private String userName;

    @Bean
    public IoTDAClient ioTDAClient() {
        ICredential auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk)
                .withProjectId(projectid);

        return IoTDAClient.newBuilder()
                .withCredential(auth)
                .withRegion(new Region("cn-north-4", endpoint))  // Ensure the region matches your IoT platform region
                .build();
    }
}
