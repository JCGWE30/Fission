package org.lepigslayer.fission.Testing.CustomCommand;

import org.bukkit.command.CommandSender;
import org.lepigslayer.fission.CustomCommand.CommandData;
import org.lepigslayer.fission.CustomCommand.CustomCommand;
import org.lepigslayer.fission.CustomCommand.Parameters.FloatParameter;
import org.lepigslayer.fission.CustomCommand.Parameters.OptionParameter;
import org.lepigslayer.fission.CustomCommand.Parameters.StringParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestCustomCommand extends CustomCommand {
    private StringParameter param1;
    private StringParameter param2;
    private StringParameter param3;
    private StringParameter param4;
    private StringParameter flag1;
    private StringParameter flag2;
    private FloatParameter floatFlag;
    private OptionParameter optionFlag;

    private List<String> uuidList;

    public TestCustomCommand() {
        uuidList = new ArrayList<>();
        regenerateList();

        param1 = new StringParameter("param1");
        param2 = new StringParameter("param2");
        param3 = new StringParameter("param3");
        param4 = new StringParameter("param4");
        flag1 = new StringParameter("flag1", 5);
        flag2 = new StringParameter("flag2");
        floatFlag = new FloatParameter("floatFlag",5,10);
        optionFlag = new OptionParameter("optionFlag");

        registerParameter(param1);
        registerParameter(param2);
        registerParameter(param3);
        registerParameter(param4);
        registerOptional(new StringParameter("optionalParameter"));
        registerFlag("f", flag1);
        registerFlag("s", flag2);
        registerFlag("fl", floatFlag);
        registerFlag("o", optionFlag, this::updateOptions);
    }

    private void updateOptions(){
        optionFlag.setOptions(uuidList);
    }

    @Override
    public void executeCommand(CommandData data) {
        regenerateList();

        String param1Value = data.getValue(param1);
        String param2Value = data.getValue(param2);
        String param3Value = data.getValue(param3);
        String param4Value = data.getValue(param4);
        String flag1Value = data.getValue(flag1,"No value");
        String flag2Value = data.getValue(flag2, "No value");

        CommandSender sender = data.getCommandSender();

        sender.sendMessage("§e========Object Values========");
        sender.sendMessage("§eParam 1: "+param1Value);
        sender.sendMessage("§eParam 2: "+param2Value);
        sender.sendMessage("§eParam 3: "+param3Value);
        sender.sendMessage("§eParam 4: "+param4Value);
        sender.sendMessage("§eFlag 1: "+flag1Value);
        sender.sendMessage("§eFlag 2: "+flag2Value);
    }

    private void regenerateList() {
        uuidList.clear();
        for(int i = 0; i < 10; i++){uuidList.add(UUID.randomUUID().toString());}
    }
}
