package hw1OOP;

public class Main1 {

    public static void main(String[] args) {
        Obstacle [] obstacles = {
            new Wall(2),
            new RunningTrack(300)
        };
        RunJumpable [] runJumpables = {
                new Human("John", 2, 500),
                new Cat("Пушок", 3, 200),
                new Robot("JS80", 0, 999999999)
        };
        for (Obstacle o: obstacles) {
            o.declaration();
            for (RunJumpable r: runJumpables) {
                if (r.getOnDistance()){
                    o.doIt(r);
                }
            }

        }
    }
}
