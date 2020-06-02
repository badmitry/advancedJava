package hw1OOP;

public class RunningTrack implements Obstacle {

    private int distance;

    public RunningTrack(int distance) {
        this.distance = distance;
    }

    @Override
    public void declaration() {
        System.out.println("Бег на беговой дорожке на "+ distance + " метров.");
    }

    @Override
    public void doIt(RunJumpable participent) {
        participent.run(distance);
    }
}
