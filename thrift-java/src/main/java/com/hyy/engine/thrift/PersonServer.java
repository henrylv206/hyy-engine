package com.hyy.engine.thrift;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.hyy.engine.thrift.generated.PersonService;
import com.hyy.engine.thrift.generated.PersonService.Iface;

public class PersonServer {
   public static void main(String[] args) throws TTransportException {
      PersonService.Processor<Iface> processor = new PersonService.Processor<Iface>(
            new PersonHandler());

      TServerTransport serverTransport = new TServerSocket(9090);
      TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
      System.out.println("start server...");
      server.serve();
   }
}
