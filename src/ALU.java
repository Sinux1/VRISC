public class ALU{

    // Variables
    private boolean isNegative = false;
    private boolean isOverflow = false;
    private boolean isZero = false;
    private boolean isCarry = false;
    private byte leftOperand = 0;
    private byte rightOperand = 0;
    private byte output = 0;
    private byte controlSignal = 0;

    // Constructor
    public ALU(){

    }

    // Methods
    public boolean getNegative(){
        return false;
    }

    public boolean getOverflow(){
        return false;
    }

    public boolean getZero(){
        return isZero;
    }

    public boolean getCarry(){
        return false;
    }

    public byte getOutput(){
        return 0;
    }

    public byte setLeftOperand(){
        return 0;
    }

    public byte setRightOperand(){
        return 0;
    }

    public byte setControlSignal(){
        return 0;
    }

    private void execute(){

    }

    private void add(){

    }

    private void compare(){

    }

    private void passThrough(){

    }


}