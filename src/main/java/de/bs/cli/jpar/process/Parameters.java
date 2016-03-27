package de.bs.cli.jpar.process;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.config.ExceptionMessages;

public class Parameters implements ExceptionMessages {
	private String[] args;
	private int position = -1;
	
	public Parameters(final String[] args) {
		if (args == null) {
			throw new JParException(EXC_PROCESS_MISSING_ARGS);
		}
		this.args = args;
	}
	
	public boolean next() {
		position++;
		return position < args.length;
	}
	
	public String get() {
		if (position >= 0 && position < args.length) {
			return args[position];
		}
		return null;
	}
	
	public boolean before() {
		--position;
		return position >= 0;
	}
	
	public void reset() {
		position = 0;
	}
}
