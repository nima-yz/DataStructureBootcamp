import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TrieTest {

	@Test
	public void testInitialization() {
		Trie tree = new Trie();
		// Nothing should be found
		assertEquals(0, tree.search("").size());
		assertNull(tree.search("NoPrefix"));
		assertNull(tree.search(null));
	}

	@Test
	public void testInsert() {
		Trie tree = new Trie();

		// Lets insert some strings
		tree.insert("JoeIsDriver");
		tree.insert("JoeIsDeveloper");
		tree.insert("NimaIsAlsoDevloper");
		tree.insert("HelloWorld");

		assertEquals(2, tree.search("Joe").size());
		assertEquals(1, tree.search("JoeIsDev").size());
		assertEquals(4, tree.search("").size());
		assertEquals(1, tree.search("helloworld").size());
	}
}
