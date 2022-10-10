package ch.heigvd.api.labio.impl;

import ch.heigvd.api.labio.impl.transformers.LineNumberingCharTransformer;
import ch.heigvd.api.labio.impl.transformers.NoOpCharTransformer;
import ch.heigvd.api.labio.impl.transformers.UpperCaseCharTransformer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class transforms files. The transform method receives an inputFile.
 * It writes a copy of the input file to an output file, but applies a
 * character transformer before writing each character.
 *
 * @author Juergen Ehrensberger
 */
public class FileTransformer {
  private static final Logger LOG = Logger.getLogger(FileTransformer.class.getName());

  public void transform(File inputFile) {
    /*
     * This method opens the given inputFile and copies the
     * content to an output file.
     * The output file has a file name <inputFile-Name>.out, for example:
     *   quote-2.utf --> quote-2.utf.out
     * Both files must be opened (read or write) with encoding "UTF-8".
     * Before writing each character to the output file, the transformer calls
     * a character transformer to transform the character before writing it to the output.
     *
     * Elliot is trying to fix this
     */

    /* TODO: first start with the NoOpCharTransformer which does nothing.
     *  Later, replace it by a combination of the UpperCaseCharTransformer
     *  and the LineNumberCharTransformer.
     *
     * Update : Elliot solved it for now but could be improved
     */
    // ... transformer = ...
    NoOpCharTransformer           noOp    = new NoOpCharTransformer();
    UpperCaseCharTransformer      upperOp = new UpperCaseCharTransformer();
    LineNumberingCharTransformer  lineOp  = new LineNumberingCharTransformer();

    /* TODO: implement the following logic here:
     *  - open the inputFile and an outputFile
     *    Use UTF-8 encoding for both.
     *    Filename of the output file: <inputFile-Name>.out (that is add ".out" at the end)
     *  - Copy all characters from the input file to the output file.
     *  - For each character, apply a transformation: start with NoOpCharTransformer,
     *    then later replace it with a combination of UpperCaseFCharTransformer and LineNumberCharTransformer.
     *
     *  Edit : Elliot solved it for now but could be improved
     */
    try {
      FileReader fis = new FileReader(inputFile, StandardCharsets.UTF_8);
      FileWriter fw = new FileWriter(inputFile + ".out", StandardCharsets.UTF_8);
      int charCode;
      while((charCode = fis.read()) != -1) {
        String content = String.valueOf((char)charCode);
        content = noOp.transform(content);
        content = upperOp.transform(content);
        content = lineOp.transform(content);
        fw.write(content);
      }
      fis.close();
      fw.close();
    } catch (Exception ex) {
      LOG.log(Level.SEVERE, "Error while reading, writing or transforming file.", ex);
    }
  }
}