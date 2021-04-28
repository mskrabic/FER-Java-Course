package hr.fer.oprpp1.hw05.shell;

import java.io.IOException;


/**
 * Razred predstavlja implementaciju jednostavne konzole (naredbenog retka).
 * 
 * @author mskrabic
 *
 */
public class MyShell {
	
	public static void main(String[] args) throws IOException {
		
		Environment environment = new ShellEnvironment();
		ShellStatus status = ShellStatus.CONTINUE;

		try {
			while (status != ShellStatus.TERMINATE) {
				String line = environment.readLine();
				String commandName = Util.parseCommand(line);
				String arguments = line.substring(commandName.length()).trim();
				ShellCommand command = environment.commands().get(commandName);
				
				if (command == null) {
					environment.writeln("Unknown command: " + commandName);
					environment.write(environment.getPromptSymbol() + " ");
				} else {
					status = command.executeCommand(environment, arguments);
				}
			}	
		} catch (ShellIOException e) {
			environment.writeln("Terminating due to I/O error.");
			
		}
	}
}
