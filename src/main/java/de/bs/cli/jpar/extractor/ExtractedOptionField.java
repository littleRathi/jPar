package de.bs.cli.jpar.extractor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import de.bs.cli.jpar.Option;
import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.process.Parameters;

class ExtractedOptionField extends ExtractedOption {
	
	private Field field;
	
	public ExtractedOptionField(final Field field, final Option option, final ExtractedArguments arguments) {
		super(option, arguments, field.getType());
		this.field = field;
		
		if (Modifier.isStatic(field.getModifiers())) {
			throw new JParException(EXC_EXTRACTOR_FIELD_NOT_STATIC, field.toString());
		}
	}
	@Override
	public String getTargetName() {
		return field.getName().toUpperCase();
	}

	@Override
	public void processArg(final Object program, final String option, final String argument, final Parameters args) {
		Object value = null;
		try {
			value = getType().processArgs(option, argument, args);
			field.set(program, value);
		} catch (Exception e) {
			throw new JParException(e, EXC_EXTRACTOR_COULD_NOT_SET, value, argument, option);
		}
	}
}
