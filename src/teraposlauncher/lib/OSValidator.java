package teraposlauncher.lib;

import teraposlauncher.customtype.OSType;

/**
 * 
 * @author Hyun Ho Oh
 */
public class OSValidator {
  
  private final String OS = System.getProperty("os.name").toLowerCase();

  public OSType getOSType() {
    if(this.isWindows()) { return OSType.WINDOWS; }
    else if(this.isMac()) { return OSType.MAC; }
    else if(this.isUnix()) { return OSType.UNIX; }
    else if(this.isSolaris()) { return OSType.SUN; }
    else { return null; }
  }

  private boolean isWindows() {
    return (OS.contains("win"));
  }

  private boolean isMac() {
    return (OS.contains("mac"));
  }

  private boolean isUnix() {
    return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
  }

  private boolean isSolaris() {
    return (OS.contains("sunos"));
  }
}
