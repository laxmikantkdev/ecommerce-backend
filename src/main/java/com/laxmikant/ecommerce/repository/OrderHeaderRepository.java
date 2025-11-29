package com.laxmikant.ecommerce.repository;

import com.laxmikant.ecommerce.entity.OrderHeader;
import com.laxmikant.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
    List<OrderHeader> findByUser(User user);
}
