package hw1OOP;

public class Wall implements Obstacle{
    private int haigh;

    public Wall(int haigh) {
        this.haigh = haigh;
    }

    @Override
    public void declaration() {
        System.out.println("Прыжки через стену высотой "+ haigh + " метров.");
    }

    @Override
    public void doIt (RunJumpable participent) {
        participent.jump(haigh);
    }

}
