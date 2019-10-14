import java.util.function.DoubleToIntFunction;

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
    int runningCount = 0;

    final int PASS = 0;
    final int ADD = 1;
    final int COMPARE = 2;
    // For debugging
    private boolean isDebug = false;

    // Constructor
    public ControlUnit() {
        alu = new ALU();
        ram = new RAM();

    }

    // Methods

    private void fetchInstruction() {
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

    private void decodeInstruction() {


        // Update isDirect - instructions with binary values less that 8 and are odd
        // are instructions in immediate mode, else they are in direct mode
        isDirect = (instructionRegister < 8) && ((instructionRegister % 2) == 1);

        // If a binary instruction, fetch operand
        if ((instructionRegister > 0 && instructionRegister < 12)) {
            // Place address in PC into MAR
            memoryAddressRegister = programCounter;

            // Place value at MAR into MBR from RAM
            ram.setMode(READ);
            memoryBufferRegister = ram.readByte(memoryAddressRegister);

            // Increment PC
            programCounter++;
            // Place MBR into data register
            dataRegister1 = memoryBufferRegister;
            // If instruction is direct then fetch operand
            if (isDirect) {
                fetchOperand();
            }
            // Place value in data register into operand register
            operandRegister = dataRegister1;
        }


    }

    private void fetchOperand() {
        memoryAddressRegister = dataRegister1;
        ram.setMode(READ);
        memoryBufferRegister = ram.readByte(memoryAddressRegister);
        dataRegister1 = memoryBufferRegister;

    }

    private void execute() {
        if (instructionRegister == 0) {
            stop();
        } else if (instructionRegister == 2 || instructionRegister == 3) {
            load();
        } else if (instructionRegister == 4 || instructionRegister == 5) {
            store();
        } else if (instructionRegister == 6 || instructionRegister == 7) {
            compare();
        } else if (instructionRegister == 8) {
            jump();
        } else if (instructionRegister == 9) {
            jumpIfEqual();

        } else if (instructionRegister == 10) {
            jumpIfLessThan();

        } else if (instructionRegister == 11) {
            jumpIfGreaterThan();

        }


    }

    private void load() {
        if (isDebug) {
            System.out.println("LOAD");
        }
        alu.setControlSignal((byte) PASS);
        accumulator = alu.setLeftOperand(operandRegister);

    }

    private void store() {
        if (isDebug) {
            System.out.println("STORE");

        }

    }

    private void compare() {
        if (isDebug) {
            System.out.println("Compare");

        }
    }

    private void jump() {
        if (isDebug) {
            System.out.println("JUMP");

        }
    }

    private void jumpIfEqual() {
        if (isDebug) {
            System.out.println("JUMPEQ");
        }

    }

    private void jumpIfGreaterThan() {
        if (isDebug) {
            System.out.println("JUMPGT");
        }

    }

    private void jumpIfLessThan() {
        if (isDebug) {
            System.out.println("JUMPLT");
        }
    }

    private void stop() {
        isStopped = true;
        if (isDebug) {
            System.out.println("STOP");
        }

    }

    public String toString() {
        String formatString = "%5s %-8s %7s %-8s %12s %-8s %n%5s %-8s %7s %-8s %12s %-8s %n";
        System.out.printf(formatString,
                "IR: ", Byte.toString(instructionRegister),
                "OPR: ", Byte.toString(operandRegister),
                "PC: ", Byte.toString(programCounter),
                "MAR: ", Byte.toString(memoryAddressRegister),
                "MBR: ", Byte.toString(memoryBufferRegister),
                "Accumulator: ", Byte.toString(accumulator));


        return "";
    }

    public static void main(String[] args) {
        System.out.println("WTF, crae crae");
        ControlUnit cu = new ControlUnit();

        /*cu.isDebug = true;
        while(!(cu.isStopped)) {
            cu.fetchInstruction();
            System.out.println("Fetch Instruction: ");
            cu.toString();
            cu.decodeInstruction();
            System.out.println("Decode Instruction: ");
            cu.toString();
            cu.execute();
            System.out.println("Execute Instruction: ");
            cu.toString();

         */


    }
}

