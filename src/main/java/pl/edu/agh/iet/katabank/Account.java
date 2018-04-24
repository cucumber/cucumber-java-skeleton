package pl.edu.agh.iet.katabank;

public class Account {

    private final Customer owner;

    public Account(Customer owner) {
        this.owner = owner;
    }

    public Customer getOwner() {
        return this.owner;
    }
}
