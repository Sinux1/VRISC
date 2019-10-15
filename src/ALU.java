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

    // Methods
    // Getters
    public boolean getNegative(){
        return isNegative;
    }

    public boolean getOverflow(){
        return isOverflow;
    }

    public boolean getZero(){
        return isZero;
    }

    public boolean getCarry(){
        return isCarry;
    }

    public byte getOutput(){
        return output;
    }

    // Setters
    public byte setLeftOperand(byte operand)
    {
        leftOperand = operand;
        execute();
        return leftOperand;
    }

    // Setting right operand and calling execute
    public byte setRightOperand(byte operand){
        rightOperand = operand;
        execute();
        return rightOperand;
    }
    // Setting read and write control signals
    public byte setControlSignal(byte signal){
        controlSignal = signal;
        return signal;
    }

    // ALU instruction methods
    // Execute calls correct method corresponding to control signal
    private void execute(){
        switch (controlSignal){
            case (0):
                passThrough();
                break;
            case (1):
                add();
                break;
            case (2):
                compare();
                break;
        }
    }
    // Add method clears flags, calculates output from left and right
    // operands, then sets all flags
    private void add(){
        isOverflow = false;
        isCarry = false;
        output = (byte)(leftOperand + rightOperand);
        // Sign extended byte values of operands
        String lO = toByteString(leftOperand);
        String rO = toByteString(rightOperand);
        // Carry flag set
        isCarry = calculateCarry(lO, rO);
        // Overflow flag set
        calculateOverflow();
        // NZ flags set
        setNZFlags();



    }
    // Compare method uses signed addition to subtract
    // right operand by assigning the 2s compliment negative value
    // to it then calling add to calculate and set output and all flags
    private void compare(){
        int right = rightOperand;
        right = -right;
        rightOperand = (byte)right;
        add();

    }
        // calculateOverflow uses the sign extended byte size binary string
    // representations of the left and right operands to set the carry flag
    // by checking each bitwise column for a carry
    private boolean calculateOverflow() {
        isOverflow = false;
        byte a = leftOperand;
        byte b = rightOperand;
        byte c = (byte) (a + b);
        if( ((a < 0) && (b < 0) && c > 0) || ((a > 0) && (b > 0) && (c < 0))) {
            return true;
        }
        else{
            return false;
        }

    }
    // Passes value to output and sets flags
    private void passThrough(){
        output = leftOperand;
        setNZFlags();


    }
    // NZ flag setting method
    private void setNZFlags(){
        isNegative = false;
        isZero = false;
        if (output < 0){
            isNegative = true;
        }else if(output == 0){
            isZero = true;
        }
    }
    // Helper method - sign extends BinaryString of positive numbers to byte size
    // and shortens negative numbers to byte size for bitwise comparison when calculating carry out flag
    private String toByteString ( byte byt){
        String bin_b = Integer.toBinaryString(byt);

        if (bin_b.length() > 8){
            return bin_b.substring(bin_b.length() - 8);
        }else{
            int diff = 8 - bin_b.length();
            for (int p = 0; p < diff;p++){
                bin_b = 0 + bin_b;

            }
            return bin_b;
        }


    }

    // Calculates the carry out by comparing the 2 bytes bitwise
    // Starting with a carry of zero, if the sum of the carry and
    // and the 2 bits from the corresponding column is greater than 1,
    // there is a carry over to the next column, for eight columns.
    private boolean calculateCarry(String b1, String b2) {
        int carry = 0;
        for (int i = 7; i >= 0; i--) {
            int c1 = Character.getNumericValue(b1.charAt(i));
            int c2 = Character.getNumericValue(b2.charAt(i));
            if((carry + c1 + c2) > 1){
                carry = 1;
            }else {
                carry = 0;
            }


        }
        return (carry == 1);
    }


}