package computer;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cpu cpu8b = new Cpu(30, 10);
        
        //Terminal
        System.out.println("> Bem vindo ao 8-BIT");
        String input;
        do {
            input = lerEntrada(scanner);
            cpu8b.Compilar(input);
        } while (!"HALT".equalsIgnoreCase(input));
        
        cpu8b.exe();
        cpu8b.Print();
        scanner.close();
    }

    private static String lerEntrada(Scanner scanner) {
        System.out.print("> ");
        return scanner.nextLine();
    }
}