public class ControlUnit {


    // Variables defined in instructions
    public boolean isStopped = false;
    private byte instructionRegister = 0;
    private byte programCounter = 0;
    private byte memoryAddressRegister = 0;
    private byte memoryBufferRegister = 0;
    private byte accumulator = 0;
    private byte operandRegister = 0;
    private boolean isDirect = false;
    private RAM ram = null;
    private ALU alu = null;
    // Magic unicorn food
    private byte dataRegister1 = 0;
    final boolean READ = false;
    final boolean WRITE = !READ;

    // Constructor
    public ControlUnit(){
        ALU alu = new ALU();
        RAM ram = new RAM();

    }

    // Methods

    private void fetchInstruction(){
        // Place address in PC into MBR
        memoryAddressRegister = programCounter;

        // Place value at mar into mbr from ram
        ram.setMode(READ);
        memoryBufferRegister = ram.readByte(memoryAddressRegister);

        // Increment PC
        programCounter++;

        // Place value of mbr into ir
        instructionRegister = memoryBufferRegister;

    }

    private void decodeInstruction(){


        // Update isDirect - instructions with binary values less that 8 and are odd
        // are instructions in immediate mode, else they are in direct mode
        isDirect = (instructionRegister < 8) && ((instructionRegister % 2) == 1);

        // If a binary instruction, fetch operand
        if((instructionRegister > 0 && instructionRegister < 12)){
            // Place address in PC into MBR
            memoryAddressRegister = programCounter;

            // Place value at mar into mbr from ram
            ram.setMode(READ);
            memoryBufferRegister = ram.readByte(memoryAddressRegister);

            // Increment PC
            programCounter++;

            memoryBufferRegister = dataRegister1;

            if(isDirect) {
                fetchOperand();
            }

            operandRegister = dataRegister1;
        }





    }

    private void fetchOperand(){
        memoryAddressRegister = dataRegister1;
        ram.setMode(READ);
        memoryBufferRegister = ram.readByte(memoryAddressRegister);
        dataRegister1 = memoryBufferRegister;

    }

    private void execute(){
        if(instructionRegister == 0){
            stop();
        } else if (instructionRegister == 2 || instructionRegister == 3) {
            load();
        } else if (instructionRegister == 4 || instructionRegister == 5){
            store();
        } else if (instructionRegister == 6 || instructionRegister == 7){
            compare();
        }else if (instructionRegister == 8){
            jump();
        }else if (instructionRegister == 9){
            jumpIfEqual();
        }else if (instructionRegister == 10){
            jumpIfLessThan();
        }else if (instructionRegister == 11){
            jumpIfGreaterThan();
        }


    }

    private void load(){


    }

    private void store(){

    }

    private void compare(){

    }

    private void jump(){

    }

    private void jumpIfEqual(){
        if(alu.getZero()){
            programCounter = operandRegister;
        }

    }

    private void jumpIfGreaterThan(){

    }

    private void jumpIfLessThan(){

    }

    private void stop(){
        isStopped = true;

    }

    public String toString(){

        return "";
    }

    public static void main(String[] args){
        System.out.println("WTF, crae crae");
        ControlUnit cu = new ControlUnit();
        while(!(cu.isStopped)) {

        }


    }
}

