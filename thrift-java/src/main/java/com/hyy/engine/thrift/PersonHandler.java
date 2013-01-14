package com.hyy.engine.thrift;

import org.apache.thrift.TException;

import com.hyy.engine.thrift.generated.PersonService;

public class PersonHandler implements PersonService.Iface {

   @Override
   public void say(String content) throws TException {
      System.out.println(content);

   }

}
