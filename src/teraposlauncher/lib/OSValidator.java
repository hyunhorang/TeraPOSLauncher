package teraposlauncher.lib;

import teraposlauncher.customtype.OSType;

/**
 * This class is for checking OS type.
 * @author Hyun Ho Oh
 */
public class OSValidator {
  
  private final String OS;
  
  /**
   * Empty constructor
   */
  public OSValidator() {
    this.OS = System.getProperty("os.name").toLowerCase();
  }

  /**
   * Get OS type
   * @return OSType
   */
  public OSType getOSType() {
    if(this.isWindows()) { return OSType.WINDOWS; }
    else if(this.isMac()) { return OSType.MAC; }
    else if(this.isUnix()) { return OSType.UNIX; }
    else if(this.isSolaris()) { return OSType.SUN; }
    else { return null; }
  }

  /**
   * Check if it is Windows
   * @return 
   */
  private boolean isWindows() {
    return this.OS.contains("win");
  }

  /**
   * Check if it is MacOS
   * @return 
   */
  private boolean isMac() {
    return this.OS.contains("mac");
  }

  /**
   * Check if it is UNIX/LINUX
   * @return 
   */
  private boolean isUnix() {
    return (this.OS.contains("nix") || this.OS.contains("nux") || this.OS.contains("aix"));
  }

  /**
   * Check if it is SunOS
   * @return 
   */
  private boolean isSolaris() {
    return this.OS.contains("sunos");
  }
  
}
