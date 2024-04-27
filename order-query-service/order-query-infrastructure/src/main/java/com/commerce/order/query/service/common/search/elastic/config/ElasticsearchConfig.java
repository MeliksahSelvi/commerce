package com.commerce.order.query.service.common.search.elastic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    private final ElasticsearchConfigData elasticsearchConfigData;

    public ElasticsearchConfig(ElasticsearchConfigData elasticsearchConfigData) {
        this.elasticsearchConfigData = elasticsearchConfigData;
    }

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchConfigData.connectionUrl())
                .withConnectTimeout(elasticsearchConfigData.connectionTimeoutMs())
                .withSocketTimeout(elasticsearchConfigData.socketTimeoutMs())
                .build();
    }
}
