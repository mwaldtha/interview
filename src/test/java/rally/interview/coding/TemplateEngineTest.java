package rally.interview.coding;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TemplateEngineTest extends TestCase {
	
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public TemplateEngineTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(TemplateEngineTest.class);
	}

	/**
	 * This tests the inclusion of more than one token in the template and using
	 * all of the defined variables and tokens.
	 */
	public void testMultipleTokens() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("tokenOne", "more");
		varMap.put("tokenTwo", "than");
		varMap.put("tokenThree", "one");
		String template = "This includes ${tokenOne} ${tokenTwo} ${tokenThree} token.";

		TemplateEngine engine = new TemplateEngine();

		try {
			String output = engine.substitute(varMap, template);
			assertTrue(output.equals("This includes more than one token."));
		} catch (TemplateEngineException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * This tests passing in one more variable than there are tokens in the
	 * template The unused variable is simply ignored and not used, not an error
	 */
	public void testOneExtraVariable() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("tokenOne", "a single");
		varMap.put("tokenTwo", "not used");
		String template = "This includes ${tokenOne} token and one unused variable.";

		TemplateEngine engine = new TemplateEngine();

		try {
			String output = engine.substitute(varMap, template);
			assertTrue(output
					.equals("This includes a single token and one unused variable."));
		} catch (TemplateEngineException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * A simple test with one variable and the template is just one token,
	 * nothing else.
	 */
	public void testOnlyAToken() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("tokenOne", "This is nothing but a single token.");
		String template = "${tokenOne}";

		TemplateEngine engine = new TemplateEngine();

		try {
			String output = engine.substitute(varMap, template);
			assertTrue(output.equals("This is nothing but a single token."));
		} catch (TemplateEngineException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * A simple test with one variable and one token included in the template.
	 */
	public void testSingleToken() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("tokenOne", "a single");
		String template = "This includes ${tokenOne} token.";

		TemplateEngine engine = new TemplateEngine();

		try {
			String output = engine.substitute(varMap, template);
			assertTrue(output.equals("This includes a single token."));
		} catch (TemplateEngineException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * Testing the ability to use a variable more than once in the defined
	 * template.
	 */
	public void testSingleTokenRepeated() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("tokenOne", "one");
		String template = "This includes ${tokenOne} token repeated more than ${tokenOne} time.";

		TemplateEngine engine = new TemplateEngine();

		try {
			String output = engine.substitute(varMap, template);
			assertTrue(output
					.equals("This includes one token repeated more than one time."));
		} catch (TemplateEngineException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * Testing using the token at the very end of the template
	 */
	public void testTokenAtEnd() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("tokenOne", "at the end.");
		String template = "This includes a single token ${tokenOne}";

		TemplateEngine engine = new TemplateEngine();

		try {
			String output = engine.substitute(varMap, template);
			assertTrue(output
					.equals("This includes a single token at the end."));
		} catch (TemplateEngineException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * Testing using the token at the start of the template.
	 */
	public void testTokenAtStart() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("tokenOne", "This includes");
		String template = "${tokenOne} a single token at the start.";

		TemplateEngine engine = new TemplateEngine();

		try {
			String output = engine.substitute(varMap, template);
			assertTrue(output
					.equals("This includes a single token at the start."));
		} catch (TemplateEngineException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * Testing a token wrapped in the characters used to indicate a token in the
	 * template.
	 */
	public void testTokenChars1() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("tokenOne", "a single");
		String template = "This includes ${${tokenOne}} token.";

		TemplateEngine engine = new TemplateEngine();

		try {
			String output = engine.substitute(varMap, template);
			assertTrue(output.equals("This includes ${a single} token."));
		} catch (TemplateEngineException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * Tests a token wrapped in multiple 'layers' of characters used to indicate
	 * a token in the template.
	 */
	public void testTokenChars2() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("tokenOne", "a single");
		String template = "This includes ${${${tokenOne}}} token.";

		TemplateEngine engine = new TemplateEngine();

		try {
			String output = engine.substitute(varMap, template);
			assertTrue(output.equals("This includes ${${a single}} token."));
		} catch (TemplateEngineException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * Tests the inclusion of a string that looks like a token in the value of a
	 * variable.
	 */
	public void testTokenChars3() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("tokenOne", "a ${single}");
		String template = "This includes ${tokenOne} token.";

		TemplateEngine engine = new TemplateEngine();

		try {
			String output = engine.substitute(varMap, template);
			assertTrue(output.equals("This includes a ${single} token."));
		} catch (TemplateEngineException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * Tests the inclusion of a string that looks like a token in the vale of a
	 * variable with another token included after it in the template.
	 */
	public void testTokenChars4() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("tokenOne", "a ${single}");
		varMap.put("tokenTwo", "another");
		String template = "This includes ${tokenOne} token and ${tokenTwo}.";

		TemplateEngine engine = new TemplateEngine();

		try {
			String output = engine.substitute(varMap, template);
			assertTrue(output
					.equals("This includes a ${single} token and another."));
		} catch (TemplateEngineException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * Tests the existence of an unused token after a used token in the
	 * template.
	 */
	public void testUnusedTokenFollowedByUsedToken() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("usedToken", "and a used token.");
		String template = "This includes an ${unusedToken} token ${usedToken}";

		TemplateEngine engine = new TemplateEngine();

		try {
			// calling substitute() should throw an exception in this case
			String output = engine.substitute(varMap, template);
			fail("Expected an exception to be thrown and it wasn't.");
		} catch (TemplateEngineException e) {
			assertTrue(e
					.getMessage()
					.equals("A substitution key (unusedToken) was not found in the supplied variables."));
		}
	}

	/*
	 * Tests the existence of an unused token before a used token in the
	 * template.
	 */
	public void testUnusedTokenPrecededByUsedToken() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("usedToken", "used token.");
		String template = "This includes a ${usedToken} token and an ${unusedToken}";

		TemplateEngine engine = new TemplateEngine();

		try {
			// calling substitute() should throw an exception in this case
			String output = engine.substitute(varMap, template);
			fail("Expected an exception to be thrown and it wasn't.");
		} catch (TemplateEngineException e) {
			assertTrue(e
					.getMessage()
					.equals("A substitution key (unusedToken) was not found in the supplied variables."));
		}
	}

	/*
	 * Tests the passing of a variable in a template that includes zero tokens.
	 */
	public void testZeroTokensOneVariable() {
		Map<String, String> varMap = new HashMap<String, String>();
		varMap.put("tokenOne", "a single");
		String template = "This includes zero tokens and one unused variable.";

		TemplateEngine engine = new TemplateEngine();

		try {
			String output = engine.substitute(varMap, template);
			assertTrue(output
					.equals("This includes zero tokens and one unused variable."));
		} catch (TemplateEngineException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * Tests the scenario of zero variables and zero tokens
	 */
	public void testZeroTokensZeroVariables() {
		Map<String, String> varMap = new HashMap<String, String>();
		String template = "This includes zero tokens and zero variables.";

		TemplateEngine engine = new TemplateEngine();

		try {
			String output = engine.substitute(varMap, template);
			assertTrue(output
					.equals("This includes zero tokens and zero variables."));
		} catch (TemplateEngineException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * Tests a single unused token and zero variables.
	 */
	public void testZeroVariablesOneToken() {
		Map<String, String> varMap = new HashMap<String, String>();
		String template = "This includes an ${unusedToken} token.";

		TemplateEngine engine = new TemplateEngine();

		try {
			// calling substitute() should throw an exception in this case
			String output = engine.substitute(varMap, template);
			fail("Expected an exception to be thrown and it wasn't.");
		} catch (TemplateEngineException e) {
			assertTrue(e
					.getMessage()
					.equals("A substitution key (unusedToken) was not found in the supplied variables."));
		}
	}
}
