package dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Trie implementation of Dictionary
 */
public class TrieDictionary implements Dictionary {
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
      TrieNode currNode = root;
      for (char character : word.toCharArray()) {
         currNode = currNode.getChild(character);
         if (currNode == null) {
            return false;
         }
      }
      return currNode.endsWord();
   }

   /**
    * {@inheritDoc}
    * 
    */
   @Override
   public List<String> findAllWords(final String sequence, final int minLength) {
      final List<String> resultSet = new ArrayList<>();
      if (minLength > sequence.length()) {
         return resultSet;
      }
      TrieNode currNode = root;
      final StringBuilder sb = new StringBuilder();
      int index = 0;
      for (char character : sequence.toCharArray()) {
         currNode = currNode.getChild(character);
         if (currNode == null) {
            break;
         }
         sb.append(character);
         if (++index >= minLength && currNode.endsWord()) {
            resultSet.add(sb.toString());
         }
      }
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
