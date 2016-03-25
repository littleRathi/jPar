package de.bs.cli.jpar.manual;

import de.bs.cli.jpar.extractor.JParExtractor;

import java.util.Map;

import de.bs.cli.jpar.extractor.ExtractedOption;
import de.bs.cli.jpar.extractor.ExtractedProgram;

public class JParManual {
	private static final String OPTIONAL_CLOSE = "]";
	private static final String OPTIONAL_OPEN = "[";
	private static final String SPACE = " ";
	public static final String PART_PROG_NAME = "NAME";
	public static final String PART_SYNOPSIS = "SYNOPSIS";
	public static final String PART_DESCRIPTION = "DESCRIPTION";
	public static final String PART_OPTIONS = "OPTIONS";
	public static final String PART_AUTHORS = "AUTHORS";
	public static final String PART_COPYRIGHT = "";
	
	public static final String NEXT_LINE = "\n";
	public static final String NEXT_PART = "\n\n";
	public static final String INDENT = "    ";
	
	public static final String USAGE = " Usage: ";
	public static final String REQUIRED_OPTION = "(required option) ";
	
	private JParExtractor argExtractor;
	private ExpressionLanguage el;
	
	public String createHelpDescription(final Class<?> programClass) {
		argExtractor = new JParExtractor(programClass);
		el = new ExpressionLanguage(argExtractor);
		return buildManual(programClass);
	}
	
	private String buildManual(final Class<?> programClass) {
		StringBuilder sbInfo = new StringBuilder();
		
		buildManualForProgramNamePart(sbInfo);
		buildManualForSynopsisPart(sbInfo);
		buildManualForDescription(sbInfo);
		buildManualForOptionsPart(sbInfo, programClass);
//		buildManualForSignature(sbInfo);
		// Authors TODO: authors part
		// Copyright TODO: copyright part
		
		return sbInfo.toString();
	}
	
	private void buildManualForProgramNamePart(final StringBuilder sbInfo) {
		ExtractedProgram program = argExtractor.getProgram();
		
		sbInfo.append(PART_PROG_NAME).append(NEXT_LINE);
		sbInfo.append(INDENT).append(program.getProgramName()).append(NEXT_PART);
	}
	
	private void buildManualForSynopsisPart(final StringBuilder sbInfo) {
//		ExtractedProgram program = argExtractor.getProgram();
		
		StringBuilder synCmdLine = new StringBuilder("<progname>").append(SPACE);
		
		Map<String, ExtractedOption> requiredOptions = argExtractor.getRequiredExtractedOptions();
		for (ExtractedOption opt: requiredOptions.values()) {
			synCmdLine.append(opt.getType().getShortDescription())
				.append(SPACE);
		}
		
		for (ExtractedOption opt: argExtractor.getExtractedOptions()) {
			if (!requiredOptions.containsKey(opt.getOptionName())) {
				synCmdLine.append(OPTIONAL_OPEN)
					.append(opt.getType().getShortDescription())
				.append(OPTIONAL_CLOSE).append(SPACE);
			}
		}
		
		
		sbInfo.append(PART_SYNOPSIS).append(NEXT_LINE);
		indentBlockWithWidth(synCmdLine.toString(), 80, sbInfo, INDENT);
		sbInfo.append(NEXT_PART);
	}
	
	private void buildManualForDescription(final StringBuilder sbInfo) {
		ExtractedProgram program = argExtractor.getProgram();

		sbInfo.append(PART_DESCRIPTION).append(NEXT_LINE);
		
		String text = program.getDescription();
		text = el.replaceReferencesIn(text);
		indentBlockWithWidth(text, 80, sbInfo, INDENT);
		sbInfo.append(NEXT_PART);
	}
	
	private void buildManualForOptionsPart(final StringBuilder sbInfo, final Class<?> programClass) {

		sbInfo.append("\n").append(PART_OPTIONS).append("\n");
		
		for (ExtractedOption option: argExtractor.getExtractedOptions()) {
			StringBuilder sbOption = new StringBuilder(option.getOptionName()).append(": ");
			
			if (option.isRequired()) {
				sbOption.append(REQUIRED_OPTION);
			}
			
			sbOption.append(option.getManualDescription()).append(USAGE);
			option.getType().getManualDescription(sbOption);
			
			String optionText = el.replaceReferencesIn(sbOption.toString());
			
			indentBlockWithWidth(optionText, 80, sbInfo, INDENT);
			sbInfo.append(NEXT_PART);
		}
	}
	
	private static void indentBlockWithWidth(final String text, final int blockSize, final StringBuilder sb, final String indent) {
		int length = text.length();
		int dynBlockSize = blockSize - indent.length();
		int to = blockSize;
		
		for (int i = 0; i < length;) {
			// don't cut words
			if (i + dynBlockSize >= length) { // reststring shorter then the clocksize
				to = length;
			} else {
				to = i + dynBlockSize;
				while (text.charAt(to) != ' ' && to > (i + (dynBlockSize / 2))) {
					to--;
				}
				to++; // so that the space is the last char on the line before
			}
			
			sb.append(indent);
			
			sb.append(text.substring(i, to));
			if (to < length) {
				sb.append(NEXT_LINE);
			}
			i = to;
		}
	}
	
//	private static void withBlockWidthIntoStringBuilder(final String text, final int blockSize, final StringBuilder sb, final String prefix) {
//		boolean withPrefix = prefix != null && !prefix.isEmpty();
//		int length = text.length();
//		int dynBlockSize = blockSize;
//		int to = blockSize;
//		
//		for (int i = 0; i < length;) {
//			if (i + dynBlockSize >= length) {
//				to = length;
//			} else {
//				to = i + dynBlockSize;
//				while (text.charAt(to) != ' ' && to > (i + (dynBlockSize / 2))) {
//					to--;
//				}
//				to++; // so that the space is the last char on the line before
//			}
//			if (withPrefix && i == 0) {
//				dynBlockSize = dynBlockSize - prefix.length();
//			}
//			if (withPrefix && i > 0) {
//				sb.append(prefix);
//			}
//			sb.append(text.substring(i, to)).append("\n");
//			i = to;
//		}
//	}
}
