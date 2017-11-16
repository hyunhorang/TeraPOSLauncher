package teraposlauncher.lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for file inputs and outputs.
 * @author Hyun Ho Oh
 */
public class FileIO {
  
  // File seperator ("/" on the Linux system and "\\" on the Windows system)
  public final static String FS = System.getProperty("file.separator");
  
  /**
   * Empty constructor
   */
  public FileIO() {
    
  }
  
  /**
   * Writes file with a filename contains full path and
   * returns true when the file is written successfully but
   * false otherwise.
   * @param filename String
   * @param lines ArrayList<String>
   * @return boolean
   */
  public boolean writeFile(String filename, ArrayList<String> lines) {
    try(BufferedWriter bufferedWriter = 
      new BufferedWriter(new FileWriter(filename))
    ) {
      for(String line : lines) {
        bufferedWriter.write(line);
      }
    } catch (IOException ex) {
      Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
      return false;
    }
    return true;
  }
  
  /**
   * Reads contents from the file.
   * @param filename String
   * @return ArrayList<String>
   */
  public ArrayList<String> readFile(String filename) {
    ArrayList<String> lines = new ArrayList<>();
    try (BufferedReader bufferedReader = 
            new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        lines.add(line);
			}
    } catch (IOException ex) {
      return null;
    }
    return lines;
  }
  
  /**
   * Checks if the existence of the file.
   * @param filename String
   * @return boolean
   */
  public boolean fileExist(String filename) {
    File file = new File(filename);
    if(file.exists()) {
      return new File(filename).isFile();
    }
    return false;
  }
  
  /**
   * Checks if the existence of the directory.
   * @param directory String
   * @return boolean
   */
  public boolean directoryExist(String directory) {
    File file = new File(directory);
    if(file.exists()) {
      return new File(directory).isDirectory();
    }
    return false;
  }
  
  /**
   * Creates new directory if not exist.
   * @param directory String
   * @return boolean
   */
  public boolean createDirectory(String directory) {
    File file = new File(directory);
    if(!file.exists()) {
      return file.mkdir();
    }
    return false;
  }
  
  /**
   * Deletes file and directory.
   * @param filename String
   * @return boolean
   */
  public boolean deleteFile(String filename) {
    return new File(filename).delete();
  }
  
  /**
   * Gets the file size in MB format.
   * @param filename
   * @return double
   */
  public double fileSizeMB(String filename) { 
    return (((new File(filename).length()) / 1024) / 1024);
  }
  
  /**
   * Gets absolute path of this class or directory which contains the jar file
   * that contains this class.
   * @return 
   */
  public String absolutePath() {
    File path = new File(
      FileIO.class.getProtectionDomain().
      getCodeSource().getLocation().getPath()
    );
    return path.getParent();
  }
  
}
