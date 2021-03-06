package de.bs.cli.jpar.config;

import de.bs.cli.jpar.JParException;

public class Defaults implements ExceptionMessages {
	private static String LIST_DELIMITER = Consts.LIST_DELIMITER;
	private static String OPTION_PREFIX = Consts.OPTION_PRFIX;
	private static String OPTION_DELIMITER = Consts.OPTION_DELIMITER;
	
	private Defaults() {
	}
	
	public static final String getListDelimiter() {
		return LIST_DELIMITER;
	}
	
	public static final void setListDelimiter(final String newListDelimiter) {
		if (newListDelimiter == null || newListDelimiter.trim().isEmpty()) {
			throw new JParException(EXC_DEFAULTS_INVALID_LIST_DELIMITER);
		}
		LIST_DELIMITER = newListDelimiter;
	}
	
	public static final String getOptionPrefix() {
		return OPTION_PREFIX;
	}
	
	public static final void setOptionPrefix(final String newOptionPrefix) {
		if (newOptionPrefix == null || newOptionPrefix.trim().isEmpty()) {
			throw new JParException(EXC_DEFAULTS_INVALID_OPTION_PREFIX);
		}
		OPTION_PREFIX = newOptionPrefix;
	}
	
	public static final String getOptionDelimiter() {
		return OPTION_DELIMITER;
	}
	
	public static final void setOptionDelimiter(final String newOptionDelimiter) {
		if (newOptionDelimiter == null || newOptionDelimiter.trim().isEmpty()) {
			throw new JParException(EXC_DEFAULTS_INVALID_OPTION_DELIMITER);
		}
		OPTION_DELIMITER = newOptionDelimiter;
	}
}
