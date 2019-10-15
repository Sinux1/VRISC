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

    final byte PASS = 0;
    final byte ADD = 1;
    final byte COMPARE = 2;
    // For debugging
    private boolean isDebug = false;

    // Constructor initializes ALU and RAM
    // Is private because no intention as of yet to extend class
    private ControlUnit() {
        alu = new ALU();
        ram = new RAM();

    }

    // Methods
    // Fetch instruction reads an instruction from the address pointed to by the
    // PC amd places it into the IR by way of the MAR and MBR, and increments the PC
    private void fetchInstruction() throws RAM.ModeMismatchException {
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

    private void decodeInstruction() throws RAM.ModeMismatchException {

        // Update isDirect - instructions with binary values less that 8 and are odd
        // are instructions in immediate mode, else they are in direct mode
        isDirect = ((instructionRegister < 8) && ((instructionRegister % 2) == 1));

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

    // Straight forward, retrieves the operand from memory and places
    // it in corresponding register
    private void fetchOperand() throws RAM.ModeMismatchException {
        memoryAddressRegister = dataRegister1;
        ram.setMode(READ);
        memoryBufferRegister = ram.readByte(memoryAddressRegister);
        dataRegister1 = memoryBufferRegister;

    }

    // Using a series of switch statements for determining method call for instruction
    private void execute() throws RAM.ModeMismatchException {
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

    // Passing opReg value to leftOperand in ALU
    // which then sends it through to output. The accumulator
    // stores the result from output
    private void load() {
        if (isDebug) {
            System.out.println("LOAD");
        }
        alu.setControlSignal(PASS);
        accumulator = alu.setLeftOperand(operandRegister);


    }


    private void store() throws RAM.ModeMismatchException {
        if (isDebug) {
            System.out.println("STORE");

        }
        ram.setMode(WRITE);
        ram.writeByte(operandRegister, accumulator);
    }

    private void compare() {
        if (isDebug) {
            System.out.println("Compare");

        }
        alu.setControlSignal(COMPARE);
        alu.setRightOperand(operandRegister);
    }

    private void jump() {
        if (isDebug) {
            System.out.println("JUMP");
        }
        programCounter = operandRegister;
    }

    private void jumpIfEqual() {
        if (isDebug) {
            System.out.println("JUMPEQ");
        }
        if (alu.getZero()) {
            programCounter = operandRegister;
        }
    }

    private void jumpIfGreaterThan() {
        if (isDebug) {
            System.out.println("JUMPGT");
        }
        if((alu.getNegative() && !alu.getOverflow()) || !alu.getNegative() && alu.getOverflow() )
        {
            programCounter = operandRegister;
        }




    }

    private void jumpIfLessThan() {
        if (isDebug) {
            System.out.println("JUMPLT");
        }
        if ((!(alu.getOverflow() && alu.getNegative()) || (alu.getNegative() && alu.getOverflow()))){
            programCounter = operandRegister;
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
                "Accumulator:", Byte.toString(accumulator));


        return "";
    }

    public static void main(String[] args) throws RAM.ModeMismatchException {
        ControlUnit cu = new ControlUnit();
        System.out.println(cu.ram.memory.length);
        cu.isDebug = true;
        while (!(cu.isStopped)) {
            System.out.println("FETCHING ");
            cu.fetchInstruction();

            // decodeInstruction calls fetchOperand if appropriate
            cu.decodeInstruction();

            cu.execute();
            System.out.println(cu);



        }
        cu.ram.toString();




    }

}


