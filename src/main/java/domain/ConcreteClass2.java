package domain;

public class ConcreteClass2 {
    public ConcreteClass1 concreteClass1;

    public ConcreteClass2(ConcreteClass1 concreteClass1) {
        this.concreteClass1 = concreteClass1;
    }

    public ConcreteClass1 method1() {
        return new ConcreteClass1();
    }
}
