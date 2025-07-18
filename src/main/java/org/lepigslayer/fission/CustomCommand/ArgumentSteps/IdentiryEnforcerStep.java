package org.lepigslayer.fission.CustomCommand.ArgumentSteps;

import org.lepigslayer.fission.CustomCommand.ArgumentContext;

public class IdentiryEnforcerStep implements ArgumentStep{
    @Override
    public void process(ArgumentContext context) {
        if(!context.isIdentified())
            context.executeError("§cToo many parameters");
    }

    @Override
    public void reset() {

    }
}
