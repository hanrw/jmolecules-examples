package com.tddworks

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    val context = runApplication<Application>(*args)

//    val repository = context.getBean(OrderRepository::class.java)
//    val order = repository.save(Order())
}

//open class Order : AggregateRoot<Order, Order.OrderIdentifier> {
//
//    override val id = OrderIdentifier(UUID.randomUUID())
//
//    data class OrderIdentifier(val id: UUID) : Identifier
//}
//
//interface OrderRepository : CrudRepository<Order, Order.OrderIdentifier>
