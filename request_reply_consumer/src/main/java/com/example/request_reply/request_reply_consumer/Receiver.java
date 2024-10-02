package com.example.request_reply.request_reply_consumer;

import java.nio.charset.Charset;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

  public void receiveMessage(byte[] bytes){
    try{
      System.out.println(new String(bytes, Charset.forName("UTF-8")));
    }catch(Exception e){
      System.err.println(e.getMessage());
    }

  }
}
