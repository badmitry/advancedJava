package hw2Exception;

public class Main2 {

    private static String[][] arr = {
            {"1", "2", "3", "4"},
            {"5", "6", "7", "8"},
            {"9", "10", "10", "11"},
            {"12", "13", "14", "100"}
    };

    private static String[][] arr1 = {
            {"1", "2", "32543", "sdf"},
            {"123", "234", "32543", "2"},
            {"123", "234", "32543", "2"},
            {"123", "234", "32543", "2"}
    };

    private static String[][] arr2 = {
            {"123", "234", "32543", "2"},
            {"123", "234", "111", "2"},
            {"123", "234", "32543", "2"},
            {"123", "234", "32543"}
    };

    public static void main(String[] args) throws MyArraySizeException, MyArrayDataException {
        printSumNumbersOfArray(arr);
        printSumNumbersOfArray(arr1);
        printSumNumbersOfArray(arr2);
    }

    private static int sumNumbersOfArray(String[][] arr) throws MyArrayDataException, MyArraySizeException {
        checkArrLength(arr);
        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (checkStringIsNumber(arr[i][j])) {
                    sum += Integer.parseInt(arr[i][j]);
                } else {
                    throw new MyArrayDataException("В массиве неверные данные", i, j);
                }

            }
        }
        return sum;
    }

    private static boolean checkStringIsNumber(String s) {
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }

    private static void checkArrLength(String[][] arr) throws MyArraySizeException {
        if (arr.length != 4) {
            throw new MyArraySizeException("Количество строк меньше 4");
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length != 4) {
                throw new MyArraySizeException("Длина строки " + (i + 1) + " не равна 4");
            }
        }
    }

    private static void printSumNumbersOfArray(String[][] arr) throws MyArraySizeException {
        try {
            System.out.println(sumNumbersOfArray(arr));
        } catch (MyArrayDataException e) {
            System.out.printf("неверные данные в строке %d, номер элемента %d %n", e.getX(), e.getY());
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        }
    }
}
