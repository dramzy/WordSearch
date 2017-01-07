package wordsearch;

import java.util.List;

/**
 * Represents a word search puzzle dictionary
 */
public interface Dictionary {

   /**
    * Checks if the Dictionary contains word
    * 
    * @param word
    *           The word to check for
    * 
    * @return true if word is contained within the Dictionary
    * @throws NullPointerException
    *            if passed a null word
    */
   public boolean containsWord(final String word);

   /**
    * Finds all Dictionary words contained within string. Only words that are
    * prefixes within string are checked for. For example, given the string
    * 'startle', the result could be: <code>['star','startle']</code> given
    * <code>minLength > 3</code>
    * 
    * @param string
    *           The String to find Dictionary words within
    * @param minLength
    *           The minimum word length to look for
    * 
    * @return A list of Dictionary words found within string
    * 
    * @throws NullPointerException
    *            if passed a null string
    * 
    * @throws IllegalArgumentException
    *            if passed a negative minLength
    * 
    */
   public List<String> findAllWords(final String string, final int minLength);

   /**
    * Inserts word into the Dictionary
    * 
    * @param word
    *           The word to insert into the Dictionary
    * 
    * @throws NullPointerException
    *            if passed a null string
    */
   public void insertWord(final String word);

}
