package com.example.route.conf;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class RestTemplateConfig {
    private org.slf4j.Logger log;

    @Bean(name = "sslRestTemplate")
    public RestTemplate sslRestTemplate()  {
        RestTemplate restTemplate = new RestTemplate();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            SSLContext context;
            context = SSLContext.getInstance("TLSv1.2");
            context.init(null, null, null);
            org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(context);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
            sr.register(new Scheme("https", 443, ssf));
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.warn("Could not load the TLS version 1.2 due to => ", e);
        }
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        return restTemplate;
    }
}