package de.bs.cli.jpar.examples;

import de.bs.cli.jpar.CliProgram;
import de.bs.cli.jpar.Option;

@CliProgram(name="program name", description="some genial description")
public class WrongFieldOptionExample {
	@Option(name="$wrongName", description="some great definition")
	private String stringFieldValue;
}
