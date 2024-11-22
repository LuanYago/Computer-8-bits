
import java.util.ArrayList;



public class Main {

    public static void main(String[] args) {
        String codigo = "LOAD 5 ADD 5 STR 2 LOAD 2 ADD 7 STR 1 HALT";
        Cpu cpu8b = new Cpu(30,15);
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
    public Cpu(int totalSize,int codeSize) {
        memory = new Ram(totalSize,codeSize);
        compilador = new Assembler();
    }

    public void exe(){
        for (; memory.readCode(pc) != HALT; pc++) {
            if(memory.readCode(pc) == LOAD){
                RX1 = memory.readData(memory.readCode(pc+1));
                pc++;
            }else if(memory.readCode(pc) == ADD){
                RX1 += memory.readCode(pc + 1);
                pc++;
            }else if(memory.readCode(pc) == SUB){
                RX1 -= memory.readCode(pc + 1);
                pc++;
            }else if(memory.readCode(pc) == STR){
                memory.insertData(memory.readCode(pc + 1), RX1);
                RX1 = 0;
                pc++;
            }
        }
    }

    public void Compilar(String codigo){
        this.compilador.Compilador(codigo);
    }

    public void Print() {
        for (int i = 0; i < this.memory.getSize(); i++) {
            System.out.println(this.memory.readData(i));
        }
    }
}

class Ram {

    private byte[] memory;
    private int codeSegmentStart;  // Início do segmento de código
    private int dataSegmentStart;  // Início do segmento de dados

    public Ram(int totalSize, int codeSize) {
        if (codeSize >= totalSize) {
            throw new IllegalArgumentException("Tamanho do segmento de código deve ser menor que o total.");
        }
        memory = new byte[totalSize];
        codeSegmentStart = 0;
        dataSegmentStart = codeSize;  // Dados começam após o segmento de código
    }

    public void insertCode(int adress, byte value) {
        int efectiveAdress = codeSegmentStart + adress;
        if(efectiveAdress > dataSegmentStart){
            throw new IllegalArgumentException("Tamanho de codigo ultrapassado");
        }
        memory[efectiveAdress] = value;
    }

    public byte readCode(int adress) {
        int efectiveAdress = codeSegmentStart + adress;
        return memory[efectiveAdress];
    }

    public void insertData(int adress, byte value) {
        int efectiveAdress = dataSegmentStart + adress;
        memory[efectiveAdress] = value;
    }

    public byte readData(int adress) {
        int efectiveAdress = dataSegmentStart + adress;
        return memory[efectiveAdress];
    }

    public int getSize() {
        return memory.length - dataSegmentStart;
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
                Cpu.memory.insertCode(i, LOAD);
            } else if ("ADD".equals(tokens.get(i))) {
                Cpu.memory.insertCode(i, ADD);
            } else if ("STR".equals(tokens.get(i))) {
                Cpu.memory.insertCode(i, STR);
            } else if ("SUB".equals(tokens.get(i))) {
                Cpu.memory.insertCode(i, SUB);
            } else if ("HALT".equals(tokens.get(i))) {
                Cpu.memory.insertCode(i, HALT);
            } else {
                Cpu.memory.insertCode(i, Byte.parseByte(tokens.get(i)));
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
