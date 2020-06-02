package hw1OOP;

public class Cat implements RunJumpable {
    private String name;
    private int haighJamp;
    private int lengthRun;
    private boolean onDistance = true;

    public Cat(String name, int haigJamp, int lengthRun) {
        this.name = name;
        this.haighJamp = haigJamp;
        this.lengthRun = lengthRun;
    }

    @Override
    public void jump(int wallHaigh) {
        if (haighJamp >= wallHaigh) {
            System.out.println(name + " перепрыгнул " + wallHaigh + " метров");
        } else {
            System.out.println(name + " не перепрыгнул " + wallHaigh + " метров");
            onDistance = false;
        }
    }

    @Override
    public void run(int distance) {
        if (lengthRun >= distance) {
            System.out.println(name + " пробежал " + distance + " метров");
        } else {
            System.out.println(name + " не пробежал " + distance + " метров");
            onDistance = false;
        }

    }

    @Override
    public boolean getOnDistance() {
        return onDistance;
    }
}
