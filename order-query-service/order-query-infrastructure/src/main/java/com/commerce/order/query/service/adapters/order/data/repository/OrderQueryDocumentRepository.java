package com.commerce.order.query.service.adapters.order.data.repository;

import com.commerce.order.query.service.adapters.order.data.document.OrderQueryIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@Repository
public interface OrderQueryDocumentRepository extends ElasticsearchRepository<OrderQueryIndexModel, String> {
}
