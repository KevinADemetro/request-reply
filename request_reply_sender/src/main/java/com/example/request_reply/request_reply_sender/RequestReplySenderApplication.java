package com.example.request_reply.request_reply_sender;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class RequestReplySenderApplication implements CommandLineRunner{

  @Autowired
  RabbitTemplate rabbitTemplate;
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
  
	public static void main(String[] args) {
		SpringApplication.run(RequestReplySenderApplication.class, args);
	}

  @Override
	public void run(String... args) throws Exception {
    rabbitTemplate.convertAndSend(topicExchangeName, "request.message", "test".getBytes());
  }
}
