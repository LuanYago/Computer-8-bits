package computer;

import java.util.ArrayList;

public class Assembler {

    //Comandos
    byte LOAD = 0b0000001;
    byte ADD = 0b0000010;
    byte SUB = 0b0000011;
    byte STR = 0b0000100;
    byte HALT = 0b0000101;
    int mn = 0;
    
    public Assembler(){

    }

    public void Compilador(String codigo) {
        ArrayList<String> tokens = this.Lexer(codigo);
        int currentMn = mn; // Store the current instruction pointer
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if ("LOAD".equals(token)) {
                Cpu.memory.insertCode(currentMn++, LOAD);
            } else if ("ADD".equals(token)) {
                Cpu.memory.insertCode(currentMn++, ADD);
            } else if ("STR".equals(token)) {
                Cpu.memory.insertCode(currentMn++, STR);
            } else if ("SUB".equals(token)) {
                Cpu.memory.insertCode(currentMn++, SUB);
            } else if ("HALT".equals(token)) {
                Cpu.memory.insertCode(currentMn++, HALT);
            } else {
                Cpu.memory.insertCode(currentMn++, Byte.parseByte(token));
            }
        }
        mn = currentMn; // Update mn after compilation
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
