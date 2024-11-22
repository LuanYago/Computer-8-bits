package computer;

public class Ram {
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

    public void insertCode(int address, byte value) {
        int effectiveAddress = codeSegmentStart + address;
        if (effectiveAddress >= dataSegmentStart || effectiveAddress < codeSegmentStart) {
            throw new IllegalArgumentException("Invalid code address: " + address);
        }
        memory[effectiveAddress] = value;
    }
    

    public byte readCode(int adress) {
        int efectiveAdress = codeSegmentStart + adress;
        return memory[efectiveAdress];
    }

    public void insertData(int address, byte value) {
        int effectiveAddress = dataSegmentStart + address;
        if (effectiveAddress >= memory.length || effectiveAddress < dataSegmentStart) {
            throw new IllegalArgumentException("Invalid data address: " + address);
        }
        memory[effectiveAddress] = value;
    }

    public byte readData(int adress) {
        int efectiveAdress = dataSegmentStart + adress;
        return memory[efectiveAdress];
    }

    public int getSize() {
        return memory.length - dataSegmentStart;
    }
}
