package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.*;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all persons whose names contain any of "
            + "the specified keywords and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns copy of keywords in this command.
     */
    public Set<String> getKeywords() {
        return new HashSet<>(keywords);
    }

    @Override
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithNameContainingAnyKeyword(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }

    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords, not case-sensitive.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithNameContainingAnyKeyword(Set<String> keywords) {
        final Set<ReadOnlyPerson> matchedPersons = new HashSet<ReadOnlyPerson>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> wordsInName = new HashSet<>(person.getName().getWordsInName());
            
            final Set<String> phone = new HashSet<>(Arrays.asList(person.getPhone().toString()));
            final Set<String> email = new HashSet<>(Arrays.asList(person.getEmail().toString()));
            final Set<String> wordsInAddress = new HashSet<>(person.getAddress().getWordsInAddress());
            for (String keyword : lowerCased(keywords)) {
            	for (String word : lowerCased(wordsInName)) {
            		if (word.contains(keyword)){
            			matchedPersons.add(person);
            		}
            	}
            	for (String number : lowerCased(phone)) {
            		if (number.contains(keyword)){
            			matchedPersons.add(person);
            		}
            	}
            	for (String mail : lowerCased(email)) {
            		if (mail.contains(keyword)){
            			matchedPersons.add(person);	
            		}
            	}
            	for (String word : lowerCased(wordsInAddress)) {
            		if (word.contains(keyword)){
            			matchedPersons.add(person);
            		}

            	}
            }
        }
        return new ArrayList<ReadOnlyPerson>(matchedPersons);
    }
    
    /**
     * Convert a given set of words to lower-cased.
     *
     * @param words to convert
     * @return list of lower-cased words
     */
    private static List<String> lowerCased(Set<String> words) {
    	final List<String> wordsInLowerCase = new ArrayList<>();
    	for (String word : words) {
    		wordsInLowerCase.add(word.toLowerCase());
    	}
    	return wordsInLowerCase;
    }	
}
