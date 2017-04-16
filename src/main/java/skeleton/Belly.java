package skeleton;

public class Belly {
    public void eat(int cukes) {
		System.out.println("");
		System.out.println("-------------------------------------------");
		System.out.println("Eating " + String.valueOf(cukes) + " cukes");
    }
	
	public void wait(int hours){
		System.out.println("Waiting for " + String.valueOf(hours) + " hour(s)");
	}
	
	public void growl(){
		System.out.println("My belly is growling!!");
	}
	
	public void gettingFat(){
		System.out.println("I'm getting fat!");
	}
}
