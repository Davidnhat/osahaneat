package com.daviaNhat.osahaneat.repository;

import com.daviaNhat.osahaneat.entity.OrderItem;
import com.daviaNhat.osahaneat.entity.keys.KeyOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, KeyOrderItem> {
}
