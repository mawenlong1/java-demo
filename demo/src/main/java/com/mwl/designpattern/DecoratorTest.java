package com.mwl.designpattern;

/**
 * @author mawenlong
 * @date 2019-06-26 19:45
 * <p>
 * 装饰者模式
 */
// 抽象接口，用来规范将要被附加一些操作的对象
interface People {
    void wear();
}

// 具体的对象，该对象将被附加一些额外的操作
class Jane implements People {
    public void wear() {
        System.out.println("今天该穿什么呢?");
    }
}

// 装饰者类，持有一个将要被装饰的接口对象的实例
class Decorator implements People {

    private People people;

    Decorator(People people) {
        this.people = people;
    }

    public void wear() {
        people.wear();
    }
}

// 具体的装饰者类，负责给增加附加的操作：穿衬衫
class DecoratorShirt extends Decorator {

    DecoratorShirt(People people) {
        super(people);
    }

    public void wear() {
        super.wear();
        System.out.println("穿个衬衫");
    }
}

// 具体的装饰者类，负责给增加附加的操作：穿西服
class DecoratorSuit extends Decorator {

    DecoratorSuit(People people) {
        super(people);
    }

    public void wear() {
        super.wear();
        System.out.println("穿个西服");
    }
}

// 具体的装饰者类，负责给增加附加的操作：穿T-Shirt
class DecoratorTShirt extends Decorator {

    DecoratorTShirt(People people) {
        super(people);
    }

    public void wear() {
        super.wear();
        System.out.println("穿个T-Shirt");
    }
}

// 具体的装饰者类，负责给增加附加的操作：穿裤子
class DecoratorPants extends Decorator {

    DecoratorPants(People people) {
        super(people);
    }

    public void wear() {
        super.wear();
        System.out.println("穿裤子");
    }
}

// 具体的装饰者类，负责给增加附加的操作：穿鞋子
class DecoratorShoes extends Decorator {

    public DecoratorShoes(People people) {
        super(people);
    }

    public void wear() {
        super.wear();
        System.out.println("鞋子");
    }
}

public class DecoratorTest {

    public static void main(String[] args) {
        People p1 = new DecoratorSuit(new DecoratorShirt(new Jane()));
        p1.wear();
        System.out.println("--------------");
        People p2 = new DecoratorTShirt(new DecoratorPants(new Jane()));
        p2.wear();
        System.out.println("--------------");
        People p3 = new DecoratorTShirt(new DecoratorPants(new DecoratorShoes(new Jane())));
        p3.wear();
        System.out.println("--------------");
        People p4 = new DecoratorShoes(new DecoratorPants(new DecoratorTShirt(new Jane())));
        p4.wear();
    }
}