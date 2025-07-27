package org.lepigslayer.fission.CustomCommand.Parameters;

import org.lepigslayer.fission.CustomCommand.CommandParameter;
import org.lepigslayer.fission.Fission;

public class FloatParameter extends CommandParameter<Float> {
    private float min = 0f;
    private float max = Float.MAX_VALUE;

    public FloatParameter(String name) {
        super(name);
    }

    public FloatParameter(String name, float min, float max) {
        super(name);
        this.min = min;
        this.max = max;
    }

    @Override
    public String validate(String input) {
        try{
            float number = Float.parseFloat(input);

            if(number < min)
                return "Number must be greater than " + min;

            if(number > max)
                return "Number must be less than " + max;

        }catch(NumberFormatException e){
            return "Invalid number, must be a number";
        }
        return null;
    }

    @Override
    public Float getValue(String input) {
        try{
            return Float.parseFloat(input);
        }catch(NumberFormatException e){
            Fission.log("WARNING. FLOAT PARAMETER FAILED TO FIND AN FLOAT");
            e.printStackTrace();
            return -1f;
        }
    }
}
