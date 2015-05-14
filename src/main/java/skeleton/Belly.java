package skeleton;

public class Belly {
    private int cukes = 0;
    
    public void eat(int moreCukes) {
        this.cukes += moreCukes;
    }

    public int getCukes() {
        return cukes;
    }
}
