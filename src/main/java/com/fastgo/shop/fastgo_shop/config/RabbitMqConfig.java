package com.fastgo.shop.fastgo_shop.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.exchange.sync:sync-exchange}")
    private String syncExchange;
    
    private final String ROUTING_KEY_SHOP_SYNC = "shop.sync.request";
    private final String QUEUE_NAME_SHOP_SYNC = "shop.sync.request.queue";

 
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

  
    @Bean
    public DirectExchange syncExchange() {
        return new DirectExchange(syncExchange);
    }

   
    @Bean
    public Queue shopSyncQueue() {
        return new Queue(QUEUE_NAME_SHOP_SYNC);
    }

    @Bean
    public Binding shopSyncBinding(Queue SHOPtSyncQueue, DirectExchange syncExchange) {
        return BindingBuilder.bind(shopSyncQueue())
                .to(syncExchange)
                .with(ROUTING_KEY_SHOP_SYNC);
    }
}
