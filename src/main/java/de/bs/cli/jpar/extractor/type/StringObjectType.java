package de.bs.cli.jpar.extractor.type;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.config.Defaults;
import de.bs.cli.jpar.extractor.ExtractedArguments;
import de.bs.cli.jpar.extractor.ExtractedOption;
import de.bs.cli.jpar.process.Parameters;

public class StringObjectType extends Type {

	public StringObjectType(Class<?> targetType, ExtractedOption option, ExtractedArguments arguments) {
		super(targetType, option, arguments);
		
		if (option.getSourceType() != null && option.getSourceType() != Void.class) {
			throw new JParException(EXC_TYPE_SOURCE_MUST_NOT_BE_SET, option.getOptionName(), option.getSourceType(), "String");
		}
		
		checkClassForStringInstanziateMethodsOrThrow(targetType);
	}

	@Override
	public String getShortDescription() {
		return getOption().getOptionName() + Defaults.getOptionDelimiter() 
			+ "<" + getTargetType().getSimpleName() + ">";
	}

	@Override
	public void getManualDescription(StringBuilder descriptionBuilder) {
		descriptionBuilder.append(getShortDescription());
		
		createValuesDescription(descriptionBuilder, false);
	}

	@Override
	public Object processArgs(String option, String argument, Parameters args) {
		if (getArguments() != null && !getArguments().validValue(argument)) {
			throw new JParException(EXC_TYPE_VALUE_NOT_VALID, argument, getOption().getOptionName());
		}
		return castTo(getTargetType(), argument);
	}

}
