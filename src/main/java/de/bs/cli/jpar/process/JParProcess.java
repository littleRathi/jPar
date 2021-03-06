package de.bs.cli.jpar.process;

import java.util.HashMap;
import java.util.Map;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.config.Consts;
import de.bs.cli.jpar.config.Defaults;
import de.bs.cli.jpar.config.ExceptionMessages;
import de.bs.cli.jpar.extractor.JParExtractor;
import de.bs.cli.jpar.extractor.ExtractedOption;

public class JParProcess implements ExceptionMessages {
	
	private JParExtractor argExtractor;
	private Parameters arguments;
	
	private Map<String, ExtractedOption> required = new HashMap<String, ExtractedOption>();

	public boolean processArgs(final Object program, final String[] args) {
		argExtractor = new JParExtractor(program.getClass());
		required = argExtractor.getRequiredExtractedOptions();
		arguments = new Parameters(args);
		return processArgsToOptions(program);
	}
	
	private boolean processArgsToOptions(final Object program) {
		boolean run = mapArgsToOption(program);
		checkForRequired();
		return run;
	}
	
	private boolean mapArgsToOption(final Object program) {
		while (arguments.next()) {
			String parameter = arguments.get();
			String option = getOption(parameter);
			String argument = (parameter.length() > option.length()) ? parameter.substring(option.length() + 1) : Consts.EMPTY;
			
			ExtractedOption op = argExtractor.getExtractedOptionForForOptionName(option);
			if (op != null) {
				op.processArg(program, option, argument, arguments);
				required.remove(option);
			} else {
				System.out.println("Unknown Option[" + option + "]: " + argument + " (complete:" + parameter + ")");
			}
		}
		return true;
	}
	
	private String getOption(final String argPart) {
		String[] split = argPart.split(Defaults.getOptionDelimiter());
		return split[0];
	}
	
	private void checkForRequired() {
		if (required.size() > 0) {
			StringBuilder sb = null;
			for (ExtractedOption option: required.values()) {
				if (sb == null) {
					sb = new StringBuilder(option.getOptionName());
				} else {
					sb.append(", ").append(option.getOptionName());
				}
			}
			
			throw new JParException(EXC_PROCESS_MISSING_REQUIRED_ARGUMENTS, sb.toString());
		}
	}
}
