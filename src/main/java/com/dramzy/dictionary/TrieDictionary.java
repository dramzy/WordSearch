package dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Trie implementation of Dictionary
 */
public class TrieDictionary implements Dictionary {

   private static final Logger LOGGER = LoggerFactory.getLogger(TrieDictionary.class);
   // The root of the Trie
   private final TrieNode root;

   public TrieDictionary() {
      // The root node is the null character
      root = new TrieNode('\0');
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void insertWord(final String word) {
      LOGGER.debug("Instering word: {}", word);
      TrieNode currNode = root;
      for (char character : word.toCharArray()) {
         currNode = currNode.getOrAddChild(character);
      }
      currNode.setEndsWord(true);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean containsWord(final String word) {
      LOGGER.debug("Checking for {}", word);
      TrieNode currNode = root;
      for (char character : word.toCharArray()) {
         currNode = currNode.getChild(character);
         if (currNode == null) {
            LOGGER.debug("{} is not in the dictionary", word);
            return false;
         }
      }
      final boolean found = currNode.endsWord();
      if (LOGGER.isDebugEnabled()) {
         if (found) {
            LOGGER.debug("{} was found in the dictionary", word);
         } else {
            LOGGER.debug("{} is not in the dictionary", word);
         }
      }
      return found;
   }

   /**
    * {@inheritDoc}
    * 
    */
   @Override
   public List<String> findAllWords(final String sequence, final int minLength) {
      final List<String> resultSet = new ArrayList<>();
      if (minLength > sequence.length()) {
         LOGGER.warn("Sequence length ({}) is less than the minimum word length ({}), returning an empty list");
         return resultSet;
      }
      TrieNode currNode = root;
      final StringBuilder sb = new StringBuilder();
      for (char character : sequence.toCharArray()) {
         currNode = currNode.getChild(character);
         if (currNode == null) {
            break;
         }
         sb.append(character);
         if (sb.length() >= minLength && currNode.endsWord()) {
            final String foundWord = sb.toString();
            LOGGER.debug("Found the word {} in {}, adding to result set.", foundWord, sequence);
            resultSet.add(foundWord);
         }
      }
      LOGGER.debug("Found the following words in {}: {}", sequence, resultSet);
      return resultSet;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void removeWord(final String word) {
      if (!containsWord(word)) {
         return;
      }
      TrieNode currNode = root;
      for (char character : word.toCharArray()) {
         if (currNode.getChild(character).getNumChildren() < 2) {
            currNode.removeChild(character);
            break;
         }
         currNode = currNode.getChild(character);
      }
      LOGGER.debug("{} was removed from the dictionary", word);
   }

   /*
    * Represents a node in the Trie
    */
   private static final class TrieNode {
      final private char character;
      // Does this node mark the end of a Dictionary word
      private boolean endsWord;
      final private Map<Character, TrieNode> children;

      public TrieNode(final char character) {
         this.character = character;
         this.children = new HashMap<>();
      }

      public char getCharacter() {
         return character;
      }

      public TrieNode getOrAddChild(final char child) {
         TrieNode childNode = getChild(child);
         if (childNode == null) {
            childNode = new TrieNode(child);
            children.put(child, childNode);
         }
         return childNode;
      }

      public TrieNode getChild(final char child) {
         return children.get(child);
      }

      public int getNumChildren() {
         return children.size();
      }

      public void removeChild(final char child) {
         children.remove(child);
      }

      public boolean endsWord() {
         return endsWord;
      }

      public void setEndsWord(final boolean endsWord) {
         this.endsWord = endsWord;
      }

   }
}
