package com.codestates.homework;

import com.codestates.helper.StampCalculator;
import com.codestates.order.entity.Order;
import com.codestates.order.entity.OrderCoffee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StampCalculatorTest {
    @Test
    @DisplayName("실습1: 스탬프 카운트 계산 단위 테스트")
    public void calculateStampCountTest() {
        // TODO 여기에 테스트 케이스를 작성해주세요.
        //given
        int nowCount = 3;
        int earned = 5;
        //when
        int actual = StampCalculator.calculateStampCount(nowCount, earned);
        int expected = 7;
        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("실습1: 주문 후 누적 스탬프 카운트 계산 탄위 테스트")
    public void calculateEarnedStampCountTest(){
        // TODO 여기에 테스트 케이스를 작성해주세요.
        Order order = new Order();
        OrderCoffee orderCoffee1 = new OrderCoffee();
        orderCoffee1.setQuantity(3);

        OrderCoffee ordercoffee2 = new OrderCoffee();
        ordercoffee2.setQuantity(5);

        order.setOrderCoffees(List.of(orderCoffee1,ordercoffee2));

        int expected = orderCoffee1.getQuantity() + ordercoffee2.getQuantity();
        int actual = StampCalculator.calculateEarnedStampCount(order);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
