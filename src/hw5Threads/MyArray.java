package hw5Threads;

public class MyArray {
    protected static final int size = 100000000;
    protected static final int h = size / 2;
    private float[] arr = new float[size];
    private float[] arr1 = new float[h];
    private float[] arr2 = new float[h];

    void addOneInArr(float[] arr) {
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
    }

    public void countValue(float[] arr, int startValue) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (i + startValue) / 5) * Math.cos(0.2f + (i + startValue) / 5) * Math.cos(0.4f + (i + startValue) / 2));
        }
    }

    public void countValue() {
        addOneInArr(arr);
        countValue(arr, 0);
//        for (float i : arr
//        ) {
//            System.out.println(i);
//        }
    }

    public void countValueViaSeparation() throws InterruptedException {
        addOneInArr(arr);
        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);
        Thread t1 = new Thread(() -> {
            countValue(arr1, 0);
        });
        Thread t2 = new Thread(() -> {
            countValue(arr2, h);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);
//        for (float i : arr
//        ) {
//            System.out.println(i);
//        }
    }

}
