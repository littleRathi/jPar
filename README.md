# jPar
Little lib for handling parameter given on the commandline

## API

Relevant for usage is only the package de.bs.cli.jpar, all other subpackages are for internal usage:

- @CliProgram annotation: to mark a class, that contains all the options
- @Option: mark a method or field as a option, method should only have one parameter
- @Arguments: that to mark possible values for an option, can be on a field/method that also marked with @Option or on a method, that has no parameters and returns String\[][]
- JPar: method that take care of processing parameters or creating manual information
- Values: contains static method for creating String\[][] arrays, useful for methods annotated with @Arguments
- JParException: which is thrown inside of the library

## Todo
- Tests
- stabile API (through tests)


### next version
- Überarbietung der ExpressionLanguage (no good name for it)
  * Latex variant:
  	- Parameters for commands, "explain" replacement from "latex" commands to text
  		- needs some description objects
  	- default behavior for not found commands -> empty replacement? (configurable)
- ObjectType; create simple instances from targetType, with String as a parameter: example would be new File(arg)
- extends de.bs.cli.jpar.process.Parameters with methods:
 - public interface (for lib user)
  * isNextOption(): boolean
  * stop(): boolean (counterpart to exit(), but only return will, and set nothing!)
  * allConsumed(): boolean
  * getUnconsumed(): String[]
 - internal interface
  * exit(): boolean (also set exit to true)

## Structures

### Annotation extracted structures

ExtractedOption (dependencies)
	-> Type
	-> ExtractedArguments
Type (dependencies to)
	-> ExtractedOption
	-> ExtractedArguments
ExtractedArguments (dependencies to)
	-> nothing
