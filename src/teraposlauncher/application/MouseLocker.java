package teraposlauncher.application;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import teraposlauncher.TeraPOSLauncher;
import teraposlauncher.config.ApplicationConfig;

public class MouseLocker implements Runnable {
  
  public static double X;
  public static double Y;
  
  private Thread thread;
  private Robot robot;
  
  public MouseLocker() {
    try {
      this.robot = new Robot();
    } catch(AWTException e) {
      System.out.println(e.getMessage());
    }
  }
  
  public void currentMouseLocation() {
    MouseLocker.X = MouseInfo.getPointerInfo().getLocation().getX();
    MouseLocker.Y = MouseInfo.getPointerInfo().getLocation().getY();
  }
  
  public void moveMouse(int x, int y) {
    this.robot.mouseMove(x, y);
  }
  
  public void start() {
    this.thread = new Thread(this);
    this.thread.start();
  }

  @Override
  public void run() {
    while(true) {
      this.currentMouseLocation();
      this.moveMouse((int)(ApplicationConfig.SCREEN_RESOLUTION_WIDTH + 100), (int)(ApplicationConfig.SCREEN_RESOLUTION_HEIGHT / 2));
      if(MouseLocker.X < ApplicationConfig.SCREEN_RESOLUTION_WIDTH) {
        this.moveMouse((int)MouseLocker.X, (int)MouseLocker.Y);
      }
      TeraPOSLauncher.sleep(60000);
    }
  }
  
}
