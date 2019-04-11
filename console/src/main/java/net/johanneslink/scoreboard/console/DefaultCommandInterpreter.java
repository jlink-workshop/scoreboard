package net.johanneslink.scoreboard.console;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultCommandInterpreter implements CommandInterpreter {
    private final List<Command> commands;

    public DefaultCommandInterpreter(final TranslationService translationService) {
	commands = new ArrayList<>();

	commands.add(new Command(Action.SELECT_A, translationService.translate("help_select_a"), "select a", "a"));
	commands.add(new Command(Action.SELECT_B, translationService.translate("help_select_b"), "select b", "b"));
	commands.add(new Command(Action.SCORE_1, translationService.translate("help_score_1"), "score 1", "1"));
	commands.add(new Command(Action.SCORE_2, translationService.translate("help_score_2"), "score 2", "2"));
	commands.add(new Command(Action.SCORE_3, translationService.translate("help_score_3"), "score 3", "3"));
	commands.add(new Command(Action.INCREMENT, translationService.translate("help_increment"), "increment", "+"));
	commands.add(new Command(Action.DECREMENT, translationService.translate("help_decrement"), "decrement", "-"));
	commands.add(new Command(Action.QUIT, translationService.translate("help_quit"), "quit", "q"));
	commands.add(new Command(Action.HELP, translationService.translate("help_help"), "help", "h", "?"));
    }

    @Override
    public Action parse(final String line) {

	for (final Command command : commands) {
	    if (command.handles(line.toLowerCase())) {
		return command.getAction();
	    }
	}

	return Action.UNKNOWN;
    }

    @Override
    public List<String> getCommandHelp() {
	return commands.stream().map(Command::getCommandHelp).collect(Collectors.toList());
    }

}
