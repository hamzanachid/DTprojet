package org.example.utils.cli.note;

import java.util.HashMap;
import java.util.Map;

class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();

    public void add(String option, Command command) {
        commands.put(option, command);
    }

    public void execute(String option) {
        Command command = commands.get(option);
        if (command != null) {
            command.execute();
        } else {
            System.err.println("Invalid option. Please try again.");
        }
    }
}
