package computer;

public class Cpu {
    
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
        while (memory.readCode(pc) != HALT){
            if(memory.readCode(pc) == LOAD){
                RX1 = memory.readData(memory.readCode(pc+1));
                pc += 2;
            }else if(memory.readCode(pc) == ADD){
                RX1 += memory.readCode(pc + 1);
                pc += 2;
            }else if(memory.readCode(pc) == SUB){
                RX1 -= memory.readCode(pc + 1);
                pc += 2;
            }else if(memory.readCode(pc) == STR){
                memory.insertData(memory.readCode(pc + 1), RX1);
                RX1 = 0;
                pc += 2;
            }else{
                System.out.println("Executing instruction at pc: " + pc + ", instruction: " + memory.readCode(pc));
            }
        }
    }

    public void Compilar(String codigo){
        this.compilador.Compilador(codigo);
    }

    public void Print() {
        System.out.println("Data Segment:");
        for (int i = 0; i < memory.getSize(); i++) {
            System.out.println("Address " + i + ": " + Cpu.memory.readData(i));
        }
    }
    
}
