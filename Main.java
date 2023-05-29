import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MagicCarpet m = new MagicCarpet();
        System.out.println("Enter a location to travel to");
        String location = scanner.nextLine();
        m.travel(location);
        scanner.close();
    }
}