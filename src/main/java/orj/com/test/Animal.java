package orj.com.test;

public class Animal {
    private static String dd = "22";
    private String name = "animal";
    static {
        System.out.println("我是animal的static方法块");
    }

    {
        System.out.println("我是普通方法块");
        name = "22";
    }

    public Animal() {
        System.out.println("我是animal的构造方式");
    }

    public Animal(String name) {
        System.out.println("我是animal的带参构造方式");
        this.name = name;
    }

}
