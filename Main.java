
import java.util.ArrayList;



public class Main {

    public static void main(String[] args) {
        String codigo = "LOAD 10 ADD 5 STR 10 HALT";
        Cpu cpu8b = new Cpu(15);
        cpu8b.Compilar(codigo);
        cpu8b.exe();
        cpu8b.Print();
    }

}

class Cpu {

    byte RX1 = 0;
    int pc = 0;

    //Comandos
    byte LOAD = 0b0000001;
    byte ADD = 0b0000010;
    byte SUB = 0b0000011;
    byte STR = 0b0000100;
    byte HALT = 0b0000101;

    static Ram memory;
    Assembler compilador;
    public Cpu(int memorySize) {
        memory = new Ram(memorySize);
        compilador = new Assembler();
    }

    public void exe(){
        for (; memory.read(pc) != HALT; pc++) {
            if(memory.read(pc) == LOAD){
                RX1 = memory.read(memory.read(pc+1));
                pc++;
            }else if(memory.read(pc) == ADD){
                RX1 += memory.read(pc + 1);
                pc++;
            }else if(memory.read(pc) == SUB){
                RX1 -= memory.read(pc + 1);
                pc++;
            }else if(memory.read(pc) == STR){
                memory.insert(memory.read(pc + 1), RX1);
                pc++;
            }
        }
    }

    public void Compilar(String codigo){
        this.compilador.Compilador(codigo);
    }

    public void Print() {
        for (int i = 0; i < this.memory.getSize(); i++) {
            System.out.println(this.memory.read(i));
        }
    }
}

class Ram {

    private byte[] memory;

    public Ram(int memorySize) {
        memory = new byte[memorySize];
    }

    public void insert(int adress, byte value) {
        memory[adress] = value;
    }

    public byte read(int adress) {
        return memory[adress];
    }

    public int getSize() {
        return memory.length;
    }

}

class Assembler {

    //Comandos
    byte LOAD = 0b0000001;
    byte ADD = 0b0000010;
    byte SUB = 0b0000011;
    byte STR = 0b0000100;
    byte HALT = 0b0000101;

    public Assembler(){

    }

    public void Compilador(String codigo) {
        ArrayList<String> tokens = this.Lexer(codigo);
        for (short i = 0; i < tokens.toArray().length; i++) {
            if ("LOAD".equals(tokens.get(i))) {
                Cpu.memory.insert(i, LOAD);
            } else if ("ADD".equals(tokens.get(i))) {
                Cpu.memory.insert(i, ADD);
            } else if ("STR".equals(tokens.get(i))) {
                Cpu.memory.insert(i, STR);
            } else if ("SUB".equals(tokens.get(i))) {
                Cpu.memory.insert(i, SUB);
            } else if ("HALT".equals(tokens.get(i))) {
                Cpu.memory.insert(i, HALT);
            } else {
                Cpu.memory.insert(i, Byte.parseByte(tokens.get(i)));
            }
        }
    }

    private ArrayList<String> Lexer(String codigo) {
        ArrayList<String> tokens = new ArrayList<>();
        StringBuilder funcao = new StringBuilder();
        StringBuilder valor = new StringBuilder();

        for (short i = 0; i < codigo.length(); i++) {
            char charA = codigo.charAt(i);
            char charN = (i + 1 < codigo.length()) ? codigo.charAt(i + 1) : '\0';

            if (Character.isUpperCase(charA)) {
                funcao.append(charA);
                if (!Character.isUpperCase(charN)) {
                    tokens.add(funcao.toString());
                    funcao.setLength(0);
                }
            } else if (Character.isDigit(charA)) {
                valor.append(charA);
                if (!Character.isDigit(charN)) {
                    tokens.add(valor.toString());
                    valor.setLength(0);
                }
            }
        }
        return tokens;
    }

}
