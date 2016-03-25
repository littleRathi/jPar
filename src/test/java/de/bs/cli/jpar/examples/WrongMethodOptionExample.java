package de.bs.cli.jpar.examples;

import de.bs.cli.jpar.CliProgram;
import de.bs.cli.jpar.Option;

@CliProgram(name="myProgram", description="some greate description")
public class WrongMethodOptionExample {
	@Option(name="singleOption", description="some create description for the option")
	public void stringMethodValue(String correct, int toMuch) {
	}
}
