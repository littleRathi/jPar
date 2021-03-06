package de.bs.cli.jpar.extractor;

import java.lang.annotation.Annotation;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.Option;
import de.bs.cli.jpar.config.Consts;
import de.bs.cli.jpar.process.Parameters;

public class HelpOption extends ExtractedOption {
	public HelpOption() {
		super(new Option() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return Option.class;
			}

			@Override
			public String name() {
				return Consts.NAME_HELP;
			}

			@Override
			public String description() {
				return "show this help information.";
			}

			@Override
			public boolean required() {
				return false;
			}

			@Override
			public Class<?> sourceType() {
				return Void.class;
			}
		}, null, Void.class);
	}

	@Override
	public String getTargetName() {
		return Consts.PHANTOM_TARGET_NAME_PREFIX + Consts.NAME_HELP;
	}

	@Override
	public void processArg(Object program, String option, String argument, Parameters args) {
		if (argument != null && !argument.isEmpty()) {
			throw new JParException(EXC_PROCESS_NO_ARGUMENT_ALLOWED, getOptionName());
		}
		System.out.println(de.bs.cli.jpar.JPar.manual(program.getClass()));
		System.exit(0);
	}

}
