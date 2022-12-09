package com.codestates.homework;

import com.codestates.exception.BusinessLogicException;
import com.codestates.order.entity.Order;
import com.codestates.order.repository.OrderRepository;
import com.codestates.order.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OrderServiceHomeworkTest {
    @Mock
    OrderRepository orderRepository;
    @InjectMocks
    private OrderService orderService;
    @Test
    public void cancelOrderTest() {
        // TODO OrderService의 cancelOrder() 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        // TODO Mockito를 사용해야 합니다. ^^
        Order order = new Order();
        long orderId = 1L;
        order.setOrderId(orderId);
        order.setOrderStatus(Order.OrderStatus.ORDER_CONFIRM);

        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        Assertions.assertThrows(BusinessLogicException.class, () -> orderService.cancelOrder(orderId));
    }
}
