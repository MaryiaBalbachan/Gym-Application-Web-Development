package controllers;

import play.*;
import play.mvc.*;


public class About extends Controller
{
  //Renders About page
  public static void index()
  {
    Logger.info("Rendering about");
    render("about.html");
  }
}
