import jdk.dynalink.linker.MethodTypeConversionStrategy;

import javax.swing.event.ListDataEvent;

public class RAM{
    // Package-private class for custom mode mismatch error
    class ModeMismatchException extends Exception{
        public ModeMismatchException(String message){
            super(message);
        }
    }

    private boolean isWriteMode = false;
    public byte[] memory = {12,1,12,2,12,3,12,4,12,5,12,6,12,7,12,8,12,9,12,10,12,11,12,12,12,13,12,14,12,15,4,101,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0};

    // Mode set for read/write
    public void setMode(boolean mode){
        isWriteMode = mode;
    }

    // Returns value at memory address if mode is set correctly, throws error if not
    public byte readByte(byte address) throws ModeMismatchException {
        if(!isWriteMode){return memory[address];}
        else {throw new ModeMismatchException(" Mode Mismatch Exception: Check Write Mode");}
    }

    // Writes a value to momory location if mode is set correctly, else throws error
    public void writeByte(byte add, byte value) throws ModeMismatchException {
        if(isWriteMode){ memory[add] = value; }
        else{ throw new ModeMismatchException(" Mode Mismatch Exception: Check Write Mode");}
    }
    // Contents of memory are output to terminal in rows of 16
    public String toString(){
        for(int index = 0; index <256; index++)
        {
            System.out.print(  "|" + index + ": " +memory[index] +  "| ");
            if((index + 1) % 16 ==0)
                System.out.println("\n");
        }
        return "";
    }


}