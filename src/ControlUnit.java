public class ControlUnit {


    // Variables
    public boolean isStopped = false;
    private byte instructionRegister = 0;
    private byte programCounter = 0;
    private byte memoryAddressRegister = 0;
    private byte memoryBufferRegister = 0;
    private byte accumulator = 0;
    private byte operandRegister = 0;
    private boolean isDirect = false;
    private RAM memory = null;
    private ALU alu = null;

    // Constructor
    public ControlUnit(){
        ALU alu = new ALU();

    }

    // Methods

    private void fetchInstruction(){
        memoryAddressRegister = programCounter;

    }

    private void decodeInstruction(){

    }

    private void fetchOperand(){

    }

    private void execute(){

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
    }
}

