package de.bs.cli.jpar.extractor.type;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.bs.cli.jpar.JParException;
import de.bs.cli.jpar.config.Defaults;
import de.bs.cli.jpar.extractor.ExtractedArguments;
import de.bs.cli.jpar.extractor.ExtractedOption;
import de.bs.cli.jpar.process.Parameters;

public class CollectionType extends Type {
	public CollectionType(final Class<?> targetType, final ExtractedOption option, final ExtractedArguments arguments) {
		super(targetType, option, arguments);
		Class<?> genericType = option.getSourceType();
//		Needed, because, the generic type of Set gets erased (so this information is missing)
		if (genericType == null || genericType == Void.class || genericType == void.class) {
			throw new JParException(EXC_TYPE_MISSING_SOURCE_TYPE, option.getOptionName(), genericType);
		}
		
		if (arguments == null) {
			throw new JParException(EXC_TYPE_MISSING_VALUES, getOption().getOptionName());
		}
		
		getCollectionObject(targetType);
		checkGenericType(genericType);
	}
	
	@Override
	public String getShortDescription() {
		String listType = getOption().getSourceType().getSimpleName();
		return getOption().getOptionName() + Defaults.getOptionDelimiter() + "<" + listType + ">[" + getArguments().getDelimiter() + "<" + listType + ">]";
	}

	@Override
	public void getManualDescription(final StringBuilder descriptionBuilder) {
		descriptionBuilder.append(getShortDescription());
		createValuesDescription(descriptionBuilder, true);
	}

	@Override
	public Object processArgs(String option, String argument, Parameters args) {
		String[] argValues = argument.split(getArguments().getDelimiter());
		
		if (getArguments() != null && !getArguments().validValues(argValues)) {
			throw new JParException(EXC_TYPE_INVALID_VALUE, argument, getOption().getOptionName());
		}
		
		Class<?> sourceType = getOption().getSourceType();
		Collection<Object> collection = getCollectionObject(getTargetType());
		for (String value: argValues) {
			collection.add(castTo(sourceType, value));
		}
		
		return collection;
	}
	
	private void checkGenericType(final Class<?> type) {
		if (Collection.class.isAssignableFrom(type)) {
			throw new JParException(EXC_TYPE_GENERIC_TYPE_COLLECTION, getOption().getOptionName(), type);
		}
		
		checkClassForStringInstanziateMethodsOrThrow(type);
	}
	
	@SuppressWarnings("unchecked")
	private Collection<Object> getCollectionObject(final Class<?> collectionType) {
		if (Set.class.equals(collectionType)) {
			return new HashSet<Object>();
		}
		
		if (List.class.equals(collectionType)) {
			return new LinkedList<Object>();
		}
		
		if (Collection.class.equals(collectionType)) {
			return new HashSet<Object>();
		}
		
		if (Collection.class.isAssignableFrom(collectionType)) {
			try {
				return (Collection<Object>)collectionType.newInstance();
			} catch (InstantiationException e) {
				throw new JParException(EXC_TYPE_COLLECTION_NOT_INSTANCIABLE, collectionType, getOption().getOptionName());
			} catch (IllegalAccessException e) {
				throw new JParException(EXC_TYPE_COLLECTION_NOT_INSTANCIABLE, collectionType, getOption().getOptionName());
			}
		}
		throw new JParException(EXC_TYPE_UNKNOWN_COLLECTION_TYPE, collectionType);
	}
}
