
package com.springsecurity.brewery.repositories;

import com.springsecurity.brewery.domain.BeerOrder;
import com.springsecurity.brewery.domain.Customer;
import com.springsecurity.brewery.domain.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.UUID;

public interface BeerOrderRepository  extends JpaRepository<BeerOrder, UUID> {

    Page<BeerOrder> findAllByCustomer(Customer customer, Pageable pageable);

    List<BeerOrder> findAllByOrderStatus(OrderStatusEnum orderStatusEnum);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    BeerOrder findOneById(UUID id);

    @Query("select o from BeerOrder o where o.id =?1 and " +
            "(true = :#{hasAuthority('order.read')} or o.customer.id = ?#{principal?.customer?.id})")
    BeerOrder findOrderByIdSecure(UUID orderId);
}
