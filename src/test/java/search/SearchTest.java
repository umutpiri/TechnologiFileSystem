package search;

import static net.sourceforge.jwebunit.junit.JWebUnit.assertElementNotPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertTitleEquals;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertSubmitButtonPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.setBaseUrl;
import static net.sourceforge.jwebunit.junit.JWebUnit.setTestingEngineKey;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertFormPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.setTextField;
import static net.sourceforge.jwebunit.junit.JWebUnit.submit;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertTextInElement;

import org.junit.Before;
import org.junit.Test;

import net.sourceforge.jwebunit.util.TestingEngineRegistry;

public class SearchTest {
	@Before
	public void prepare() {
		setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);
		setBaseUrl("http://localhost:8080/FileSystem");
	}
	
	@Test
	public void testSearchPage() {
		beginAt("index.jsp");
		assertTitleEquals("File System Search");
		assertFormPresent();
		assertSubmitButtonPresent();
	}
	
	@Test
	public void testSearch() {
		// Example in the task 4
		String searchText = "image";
		beginAt("index.jsp");
		setTextField("text", searchText);
		submit();
		assertTextInElement("dir-1", "C:\\Documents\\Images");
		assertTextInElement("dir-2", "C:\\Documents\\Images\\Image1.jpg");
		assertTextInElement("dir-3", "C:\\Documents\\Images\\Image2.jpg");
		assertTextInElement("dir-4", "C:\\Documents\\Images\\Image3.png");
	}
	
	@Test
	public void testSearchEmptyResult() {
		// Non existing directory name
		String searchText = "umutpiri";
		beginAt("index.jsp");
		setTextField("text", searchText);
		submit();
		assertElementNotPresent("dir-1");
	}
}
