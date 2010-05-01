import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

public class PrototypeWriter {
	public static void writeTrainingFile(ArrayList<Prototype> prototypes, String path) 
			throws Exception {
		Writer output = new BufferedWriter(new FileWriter(new File(path)));
		String aContents = toString(prototypes);
		try {
			output.write(aContents);
			output.flush();
		} finally {
			output.close();
		}

	}

	private static String toString(ArrayList<Prototype> prototypes) {
		Iterator<Prototype> it = prototypes.iterator();
		String s = "";
		while(it.hasNext()) {
			Prototype next = it.next();
			double[] v = next.getV();
			s = String.valueOf(v[0]);
			for(int i = 1; i < v.length; i++) {
				s += " " + String.valueOf(v[i]);
			}
			s += next.getCls();
			s += "\n";
		}
		return s;
	}
	
	/**
	  * Change the contents of text file in its entirety, overwriting any
	  * existing text.
	  *
	  * This style of implementation throws all exceptions to the caller.
	  *
	  * @param aFile is an existing file which can be written to.
	  * @throws IllegalArgumentException if param does not comply.
	  * @throws FileNotFoundException if the file does not exist.
	  * @throws IOException if problem encountered during write.
	  */
	  static public void setContents(File aFile, String aContents)
	                                 throws FileNotFoundException, IOException {
	    if (aFile == null) {
	      throw new IllegalArgumentException("File should not be null.");
	    }
	    if (!aFile.exists()) {
//	      throw new FileNotFoundException ("File does not exist: " + aFile);
	    	aFile.createNewFile();
	    }
	    if (!aFile.isFile()) {
	      throw new IllegalArgumentException("Should not be a directory: " + aFile);
	    }
	    if (!aFile.canWrite()) {
	      throw new IllegalArgumentException("File cannot be written: " + aFile);
	    }

	    //use buffering
	    Writer output = new BufferedWriter(new FileWriter(aFile));
	    try {
	      //FileWriter always assumes default encoding is OK!
	      output.write( aContents );
	    }
	    finally {
	      output.close();
	    }
	  }

}
