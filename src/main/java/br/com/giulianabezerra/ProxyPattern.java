package br.com.giulianabezerra;

public class ProxyPattern {
  public static void main(String[] args) {
    System.out.println(
        new Client().greet("Giuliana"));
  }
}

interface SubjectInterface {
  String greet(String name);
}

class RealSubject implements SubjectInterface {

  @Override
  public String greet(String name) {
    return "Hello, " + name + "!";
  }

}

class RealSubjectProxy implements SubjectInterface {
  private SubjectInterface realSubject = new RealSubject();

  @Override
  public String greet(String name) {
    System.out.println("Proxy: Logging before greeting...");
    String result = realSubject.greet(name);
    System.out.println("Proxy: Logging after greeting...");

    return result;
  }

}

class Client {
  public String greet(String name) {
    var realSubjectProxy = new RealSubjectProxy();

    return realSubjectProxy.greet(name);
  }
}