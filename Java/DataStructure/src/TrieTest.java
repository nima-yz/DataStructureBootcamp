import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

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

	@Test
	public void testInsertIsEnd() {
		Trie tree = new Trie();

		// Lets insert some string
		// Some of these strings include each
		// other like Joe and JoeIsDeveloper
		tree.insert("");
		tree.insert("Joe");
		tree.insert("JoeIsDevloper");
		tree.insert("JoeIsDeveloperAndGeek");

		assertEquals(3, tree.search("j").size());
		assertEquals(3, tree.search("Joe").size());
		assertEquals(2, tree.search("JoeIs").size());
	}

	@Test
	public void testSearch() {
		Trie tree = new Trie();

		// The main focus is search so make sure that
		// search result returns the right value.
		ArrayList<String> emptySearch = (ArrayList<String>) tree.search("");
		assertEquals(0, emptySearch.size());

		tree.insert("Joe");
		ArrayList<String> zeroSearch = (ArrayList<String>) tree.search("");
		ArrayList<String> oneSearch = (ArrayList<String>) tree.search("j");
		ArrayList<String> twoSearch = (ArrayList<String>) tree.search("jo");
		ArrayList<String> searchWhole = (ArrayList<String>) tree.search("joe");

		assertEquals(1, zeroSearch.size());
		assertEquals(1, oneSearch.size());
		assertEquals(1, twoSearch.size());
		assertEquals(1, searchWhole.size());

		assertTrue(zeroSearch.contains("joe"));
		assertTrue(oneSearch.contains("joe"));
		assertTrue(twoSearch.contains("joe"));
		assertTrue(searchWhole.contains("joe"));
	}

	@Test
	public void testSearchMultiple() {
		Trie tree = new Trie();

		// Insert multiple of strings and do random searches
		tree.insert("J");
		tree.insert("Joe");
		tree.insert("JoeIsDriver");
		tree.insert("JoeIsDeveloper");
		tree.insert("JoeIsDeveloperAndDriver");

		ArrayList<String> jSearch = (ArrayList<String>) tree.search("j");
		assertEquals(5, jSearch.size());
		assertTrue(jSearch.contains("J".toLowerCase()));
		assertTrue(jSearch.contains("Joe".toLowerCase()));
		assertTrue(jSearch.contains("JoeIsDriver".toLowerCase()));
		assertTrue(jSearch.contains("JoeIsDeveloper".toLowerCase()));
		assertTrue(jSearch.contains("JoeIsDeveloperAndDriver".toLowerCase()));

		ArrayList<String> joeSearch = (ArrayList<String>) tree.search("joe");
		assertEquals(4, joeSearch.size());
		assertTrue(joeSearch.contains("Joe".toLowerCase()));
		assertTrue(joeSearch.contains("JoeIsDriver".toLowerCase()));
		assertTrue(joeSearch.contains("JoeIsDeveloper".toLowerCase()));
		assertTrue(joeSearch.contains("JoeIsDeveloperAndDriver".toLowerCase()));

		ArrayList<String> jDeSearch = (ArrayList<String>) tree
				.search("joeisde");
		assertEquals(2, jDeSearch.size());
		assertTrue(jDeSearch.contains("JoeIsDeveloper".toLowerCase()));
		assertTrue(jDeSearch.contains("JoeIsDeveloperAndDriver".toLowerCase()));
	}

}
