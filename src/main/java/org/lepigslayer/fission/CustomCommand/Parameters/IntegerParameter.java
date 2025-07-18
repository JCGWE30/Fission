package org.lepigslayer.fission.CustomCommand.Parameters;

import org.lepigslayer.fission.CustomCommand.CommandParameter;
import org.lepigslayer.fission.Fission;

import java.util.Arrays;

public class IntegerParameter extends CommandParameter<Integer> {
    private int min = 0;
    private int max = Integer.MAX_VALUE;

    public IntegerParameter(String name) {
        super(name);
    }

    public IntegerParameter(String name, int min, int max){
        super(name);
        this.min = min;
        this.max = max;
    }

    @Override
    public String validate(String input) {
        try{
            int number = Integer.parseInt(input);

            if(number < min)
                return "Number must be greater than " + min;

            if(number > max)
                return "Number must be less than " + max;

        }catch(NumberFormatException e){
            return "Invalid number, must be a whole number";
        }
        return "Invalid input";
    }

    @Override
    public Integer getValue(String input) {
        try{
            return Integer.parseInt(input);
        }catch(NumberFormatException e){
            Fission.log("WARNING. INTEGER PARAMETER FAILED TO FIND AN INTEGER");
            e.printStackTrace();
            return -1;
        }
    }
}
