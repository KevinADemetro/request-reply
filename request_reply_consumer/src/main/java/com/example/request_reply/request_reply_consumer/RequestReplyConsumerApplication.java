package com.example.request_reply.request_reply_consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class RequestReplyConsumerApplication {
  static final String  topicExchangeName = "message-exchange";
  static final String queueName = "message";
  @Bean
  Queue queue(){
    return new Queue(queueName, true);
  }

  @Bean
  TopicExchange exchange(){
    return new TopicExchange(topicExchangeName);
  }

  @Bean
  Binding binding(Queue queue, TopicExchange exchange){
    return BindingBuilder.bind(queue).to(exchange).with("request.message");
  }
  
  @Bean
  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(queueName);
    container.setMessageListener(listenerAdapter);
    return container;
  }

  @Bean 
  MessageListenerAdapter listenerAdapter(Receiver receiver){
    return new MessageListenerAdapter(receiver, "receiveMessage");
  }

	public static void main(String[] args) {
		SpringApplication.run(RequestReplyConsumerApplication.class, args);
	}
}
