package cn.neud.itms.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;

@Configuration
//@ConfigurationProperties("spring.elasticsearch.rest")
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.rest.uris}")
    private String uris;

    @Value("${spring.elasticsearch.rest.connection-request-timeout}")
    private int connectionRequestTimeout;

    @Value("${spring.elasticsearch.rest.socket-timeout}")
    private int socketTimeout;

    @Value("${spring.elasticsearch.rest.max-connections}")
    private int maxConnections;

    @Value("${spring.elasticsearch.rest.max-connections-per-route}")
    private int maxConnectionsPerRoute;

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder builder = RestClient.builder(HttpHost.create(uris))
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(connectionRequestTimeout)
                        .setSocketTimeout(socketTimeout))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setMaxConnTotal(maxConnections)
                        .setMaxConnPerRoute(maxConnectionsPerRoute));

        return new RestHighLevelClient(builder);
    }
}