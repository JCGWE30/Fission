package org.lepigslayer.fission.CustomCommand.ArgumentSteps;

import org.lepigslayer.fission.CustomCommand.ArgumentContext;
import org.lepigslayer.fission.CustomCommand.CustomCommand;

public interface ArgumentStep {
    void process(ArgumentContext context);
    void reset();
}
