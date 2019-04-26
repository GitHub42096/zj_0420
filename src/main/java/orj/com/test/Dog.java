package orj.com.test;

public class Dog extends Animal {

    static {
        System.out.println("我是dog的static方法块");
    }

    private static void getStr(){
        System.out.println("getStr");
    }
    public Dog(String name) {
        super(name);
        System.out.println("我是dog 的带参构造方法");
    }

    public Dog() {
        System.out.println("dog的无参构造方法");
    }
    
    private static class InnerDog{
        static {
            System.out.println("inner dog static");
        }
    }

    public static void main(String[] args) {
//             Dog.getStr();
                Dog dog = new Dog("dd");
        }
}
