package com.codestates.order.entity;

import com.codestates.coffee.entity.Coffee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrderCoffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderCoffeeId;

    @Column(nullable = false)
    private int quantity;

    //N:1 객체참조
    @ManyToOne
    @JoinColumn(name = "COFFEE_ID")
    private Coffee coffee;

    //N:1 객체참조
    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    public void addOrder(Order order) {
        this.order = order;
    }

    public void addCoffee(Coffee coffee) {
        this.coffee = coffee;
    }
}
