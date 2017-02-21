import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An implementation of a prefix tree (Trie) for strings. This Trie structure
 * can only hold strings that contain any of the 26 lower case letters in
 * English. However since it calls .toLowerCase() on strings, it can be used
 * with capital letters as well.
 * 
 * @author Nima Yahyazadeh
 * @version 02.19.2017
 */
public class Trie {
	private static final int CHILD_SIZE = 26;

	/**
	 * Each Node represents a single character in the Trie. Also Each node has a
	 * specific number of children and a pointer to them, to show the
	 * descendants of this character.
	 */
	private class Node {

		/**
		 * Each node will hold an HashMap of its children. This pattern will
		 * make the the Trie a recursive structure here.
		 */
		private HashMap<Character, Node> children;

		/**
		 * The character of this Node.
		 */
		private Character value;

		/**
		 * Constructor foe the node class. Uses the parameter VAL to initialize
		 * the member `value`.
		 * 
		 * @param val
		 */
		public Node(Character val) {
			this.value = val;
			this.children = new HashMap<>();
		}

		/**
		 * Return the children member.
		 * 
		 * @return Children member.
		 */
		public HashMap<Character, Node> getChildren() {
			return this.children;
		}

		/**
		 * Method to retrieve all the string that this Node is the prefix of. If
		 * the node doesn't have any children it is counted as leaf an will
		 * return its own value in the ArrayList
		 * 
		 * @return
		 */
		public List<String> accumulateChildren() {
			ArrayList<String> accumulated = new ArrayList<String>();

			// Get all the string gathered up from child nodes.
			boolean hasChild = false;
			for (Node child : this.children.values()) {
				if (child != null) {
					hasChild = true;
					accumulated.addAll(child.accumulateChildren());
				}
			}

			if (this.value != null) { // Would only be false at root.
				if (!hasChild) { // Happens when at the leaf
					accumulated.add(this.value.toString());
				} else {
					// Add self.value to all string accumulated so far.
					for (int i = 0; i < accumulated.size(); i++) {
						accumulated.set(i, this.value + accumulated.get(i));
					}
				}
			}

			return accumulated;
		}
	}

	/**
	 * The only member of the Trie is a root. Root Node value is set to Null.
	 */
	private Node root;

	/**
	 * Constructor for the Trie. Initialized the root.
	 */
	public Trie() {
		this.root = new Node(null);
	}

	/**
	 * Will insert the following string to the tree.
	 * 
	 * @param newStr
	 */
	public void insert(String newStr) {
		// Don't bother with the miscellaneous calls
		if (newStr == null) {
			return;
		}
		try {
			insert_helper(newStr.toLowerCase(), this.root);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The helper that will be used recursively to insert new nodes and layers
	 * to the Trie.
	 * 
	 * @param newStr
	 *            The new String to be inserted to the Trie
	 * @param insertIn
	 *            The Node for this string to be inserted to.
	 * @throws Exception
	 */
	private void insert_helper(String newStr, Node root)
			throws NullPointerException {
		// First we have to make sure that the Node that we
		// are going to use to insert this string is not Null.
		if (root == null) {
			throw new NullPointerException("Expected a Node but got 'null'.");
		}
		// Lets handle the base case.
		else if (newStr.length() == 0) {
			return;
		}

		char firstChar = newStr.charAt(0);
		int indexForChar = charIndex(firstChar);
		if (indexForChar == -1) {
			System.err.println(
					"Following character is not supported in this Trie: "
							+ firstChar);
			return;
		}

		// Get the child Node from the root node and if null,
		// simply create a new one with the new value.
		Node childNode = null;
		if (!root.getChildren().containsKey(firstChar)) {
			childNode = new Node(firstChar);
			root.getChildren().put(firstChar, childNode);
		} else {
			childNode = root.getChildren().get(firstChar);
		}

		// Call the insert method with the new Node and the
		// string.
		this.insert_helper(newStr.substring(1), childNode);
	}

	/**
	 * Will search for strings with the given prefix.
	 * 
	 * @param prefix
	 *            The prefix to search for in strings.
	 * @return An ArrayList of strings with that Prefix
	 */
	public List<String> search(String prefix) {
		// Don't bother with the miscellaneous calls
		if (prefix == null) {
			return null;
		}

		ArrayList<String> searchResult = (ArrayList<String>) this
				.search_helper(prefix.toLowerCase(), this.root);
		if (searchResult != null) {
			for (int i = 0; i < searchResult.size(); i++) {
				searchResult.set(i, prefix + searchResult.get(i));
			}
		}

		return searchResult;
	}

	/**
	 * Helper Method that will recursively traverse down the Trie and find the
	 * last node in the Trie that is common with the prefix. Will then return
	 * all the substrings that have the same common prefix.
	 * 
	 * @param prefix
	 *            The prefix for all the strings to find.
	 * @param root
	 *            The node to search in to find the strings.
	 * @return ArrayList of all the string with prefix PREFIX.
	 */
	private List<String> search_helper(String prefix, Node root) {
		// Just like insert we don't want to have to search in
		// a null element.
		if (root == null) {
			throw new NullPointerException("Expected a Node but got a 'null'.");
		} else if (prefix.length() == 0) {
			return root.accumulateChildren();
		}

		char firstChar = prefix.charAt(0);
		int indexForChar = charIndex(firstChar);
		if (indexForChar == -1) {
			System.err.println(
					"Following character is not supported in this Trie: "
							+ firstChar);
			return null;
		}

		// If string trying to route down the tree
		Node childNode = null;
		if (!root.getChildren().containsKey(firstChar)) {
			// Can't find any search result.
			return null;
		} else {
			childNode = root.getChildren().get(firstChar);
		}

		// Go even further to find the right node.
		return search_helper(prefix.substring(1), childNode);
	}

	/**
	 * Returns the index or the integer value of a character if the character is
	 * between a-z otherwise will return -1.
	 * 
	 * @param toBeIndexed
	 *            Character to get index of.
	 * @return The index of that character for 0 - Trie.CHILD_SIZE. -1 if not
	 *         within the correct range.
	 */
	private int charIndex(char toBeIndexed) {
		int index = (int) toBeIndexed - (int) 'a';
		if (index >= 0 && index < Trie.CHILD_SIZE) {
			return index;
		} else {
			return -1;
		}
	}
}
