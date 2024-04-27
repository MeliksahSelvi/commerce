package com.commerce.order.query.service.adapters.order.data;

import com.commerce.order.query.service.adapters.order.data.document.OrderQueryIndexModel;
import com.commerce.order.query.service.adapters.order.data.repository.OrderQueryDocumentRepository;
import com.commerce.order.query.service.order.model.OrderQuery;
import com.commerce.order.query.service.order.port.data.OrderQueryDataPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@Service
public class OrderQueryDataAdapter implements OrderQueryDataPort {

    private final OrderQueryDocumentRepository orderQueryDocumentRepository;

    public OrderQueryDataAdapter(OrderQueryDocumentRepository orderQueryDocumentRepository) {
        this.orderQueryDocumentRepository = orderQueryDocumentRepository;
    }

    @Override
    public Optional<OrderQuery> findById(Long orderId) {
        Optional<OrderQueryIndexModel> orderQueryIndexModel = orderQueryDocumentRepository.findById(orderId.toString());
        return orderQueryIndexModel.map(OrderQueryIndexModel::toModel);
    }

    @Override
    public OrderQuery save(OrderQuery orderQuery) {
        var orderQueryIndexModel=new OrderQueryIndexModel();
        orderQueryIndexModel.setId(orderQuery.id().toString());
        orderQueryIndexModel.setOrderStatus(orderQuery.orderStatus());
        return orderQueryDocumentRepository.save(orderQueryIndexModel).toModel();
    }
}
