package com.hyy.engine.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.hyy.engine.thrift.generated.PersonService;

public class PersonClient {
   public static void main(String[] args) throws TException {
      TTransport transport = new TSocket("localhost", 9090);
      transport.open();

      TProtocol protocol = new TBinaryProtocol(transport);
      PersonService.Client client = new PersonService.Client(protocol);

      client.say("hello");
   }
}
