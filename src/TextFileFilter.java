package covid.lucene;

import java.io.File;
import java.io.FileFilter;

// Setting the filepath into lower case
public class TextFileFilter implements FileFilter{
	@Override 
	public boolean accept(File pathname) 
	{ 
		return pathname.getName().toLowerCase().endsWith(".txt"); 
	}
}
