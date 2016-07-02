package ar.edu.itba.kUltra;

import ar.edu.itba.kUltra.nodes.ProgramNode;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class KUltra {
	private static final Logger LOGGER = LoggerFactory.getLogger(KUltra.class);

	private static final int EXECUTION_FAILED = 1;
	private static final int INVALID_FILE_PATH = 2;
	private static final int PARSE_FAILED = 3;
	private static final String COMPILE_OP = "compile";
	private static final String LANGUAGE_FILE_EXTENSION = ".kul";

	// args[0] should contain the String 'compile'
	// args[1] should contain the String path to the file to be compiled
	public static void main(String[] args) {
		if (args.length != 2) {
			executionFailed();
		}

		final String option = args[0];
		final String pathToFile = args[1];

		if (!option.equals(COMPILE_OP)) {
			executionFailed();
		}

		final File file = new File(pathToFile);
		if (!file.isFile() || !pathToFile.endsWith(LANGUAGE_FILE_EXTENSION)) {
			invalidFilePath(pathToFile);
		}

		compile(file);
	}

	private static void invalidFilePath(final String pathToFile) {
		System.out.println("'" + pathToFile + "' is not a valid file");
		System.exit(INVALID_FILE_PATH);
	}

	private static void compile(final File file) {
		final ComplexSymbolFactory complexSymbolFactory = new ComplexSymbolFactory();
		final Scanner scanner;
		try {
			scanner = new Scanner(new FileInputStream(file), complexSymbolFactory);
		} catch (FileNotFoundException e) {
			LOGGER.debug("[FAILED] - invalidFilePath: {}. Caused by: ", file.getPath(), e);
			invalidFilePath(file.getPath());
			return; // just for the IDE compiler for not disturbing 'cause scanner's value
		}

		final Parser parser = new Parser(scanner ,complexSymbolFactory);

		final Symbol programSymbol;
		try {
			programSymbol = parser.parse();
		} catch (Exception e) {
			LOGGER.debug("[FAILED] - parser.parse(). Caused by: ", e);
			parseFailed();
			return;
		}

		final Object programSymbolValue = programSymbol.value;
		if (!(programSymbolValue instanceof ProgramNode)) {
			LOGGER.debug("[FAILED] - programSymbolValue is not instance of ProgramNode, and should be");
			parseFailed();
			return;
		}

		final ProgramNode programNode = (ProgramNode) programSymbolValue;

		final String fileName = file.getName();
		final String className = fileName.substring(0, fileName.indexOf(LANGUAGE_FILE_EXTENSION));
		programNode.compileAs(className);
	}

	private static void parseFailed() {
		System.out.println("A syntax error was detected at the given .kul file.");
		System.out.println("Please check it.");
		System.exit(PARSE_FAILED);
	}

	private static void executionFailed() {
		System.out.println("[FAIL] - Required arguments: 'compile <path/to/.kul>'");
		System.exit(EXECUTION_FAILED);
	}
}
