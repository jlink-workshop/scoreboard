package net.johanneslink.scoreboard.console;

import java.util.List;

public interface CommandInterpreter {
    Action parse(String line);

    List<String> getCommandHelp();
}
