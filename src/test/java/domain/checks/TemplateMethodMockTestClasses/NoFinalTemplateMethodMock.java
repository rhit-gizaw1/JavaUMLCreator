package domain.checks.TemplateMethodMockTestClasses;

public abstract class NoFinalTemplateMethodMock {
    public abstract void doNothing();
    public void doMoreNothing() {
        System.out.println("Nothing");
    }
    public void aNothingHook(){
    }
}