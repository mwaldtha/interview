package rally.interview.coding;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateEngine {

	private static final String TOKEN_START = "${";
	private static final String TOKEN_END = "}";
	/*
	 * The following regular expression looks for the following: 
	 * - The literal characters "${" 
	 * - Looks ahead for to see if there is NOT a group of characters matching 
	 *   - The literal characters "${" 
	 *   - Followed by any number of characters not including "}" 
	 * - Any number of characters not including "}" 
	 * - The literal "}" character
	 * 
	 * This allows a token to be wrapped in the token start "${" and token end
	 * "}" characters. e.g. ${tokenOne} will be found inside ${${${tokenOne}}}
	 */
	private static final String VAR_TOKEN_REGEX = "\\$\\{(?!\\$\\{[^}]*)[^}]*}";
	private static final Pattern VAR_TOKEN_PATTERN = Pattern
			.compile(VAR_TOKEN_REGEX);

	/**
	 * Returns a string with the values of the variables passed in substituted
	 * for the respective tokens found in the supplied template string. For
	 * example the value of a variable named "varOne" can be replaced by using a
	 * token of "${varOne}" in the templlate string.
	 * 
	 * @param varMap
	 *            a map of key/value pairs representing variable names and
	 *            values respectively
	 * @param template
	 *            a String that includes tokens used for variable substitution.
	 *            Tokens are specified using opening characters of "${" and a
	 *            closing character of "}".
	 * @return the template string with variable values replaced for the
	 *         corresponding tokens
	 * @throws TemplateEngineException
	 *             when a token is specified in the template string but no
	 *             corresponding variable can be found
	 */
	public String substitute(Map<String, String> varMap, String template)
			throws TemplateEngineException {

		// create a matcher to compare the template string against the
		// VAR_TOKEN_REGEX regular expression
		Matcher m = VAR_TOKEN_PATTERN.matcher(template);

		// loop through all of the matches found in the template
		while (m.find()) {
			// get the value of the match
			String foundToken = m.group();

			// remove the start and end token tags from the match in order to
			// compare with the variable key name
			String tokenKey = foundToken.substring(
					foundToken.indexOf(TOKEN_START) + TOKEN_START.length(),
					foundToken.indexOf(TOKEN_END));

			// see if the variable map contains the found token
			if (varMap.containsKey(tokenKey)) {
				// replace the token in the template with the variable value
				template = template.replace(foundToken, varMap.get(tokenKey));
			} else {
				// throw an exception when a token in the template is not found
				// in the list of variables
				throw new TemplateEngineException("A substitution key ("
						+ tokenKey
						+ ") was not found in the supplied variables.");
			}
		}

		// return the template string with the replaced variable values
		return template;
	}

}
