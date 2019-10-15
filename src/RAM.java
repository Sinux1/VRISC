import jdk.dynalink.linker.MethodTypeConversionStrategy;

import javax.swing.event.ListDataEvent;

public class RAM{
    class ModeMismatchException extends Exception{
        public ModeMismatchException(String message){
            super(message);
        }
    }

    private boolean isWriteMode = false;
    public byte[] memory = {2,7,7,64,9,20,10,25,2,3,4,70,0,0,0,0,0,0,0,0,2,1,4,70,0,2,2,4,70,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,85,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};


    public void setMode(boolean mode){
        isWriteMode = mode;
    }

    public byte readByte(byte address) throws ModeMismatchException {
        if(!isWriteMode){return memory[address];}
        else {throw new ModeMismatchException(" Mode Mismatch Exception: Check Write Mode");}



    }

    public void writeByte(byte add, byte value) throws ModeMismatchException {
        if(isWriteMode){ memory[add] = value; }
        else{ throw new ModeMismatchException(" Mode Mismatch Exception: Check Write Mode");}


    }

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