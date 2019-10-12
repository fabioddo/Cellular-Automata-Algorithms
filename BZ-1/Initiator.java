package Belousov_Zhabotinsky_Reaction;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random; 

import it.unipr.sowide.actodes.ca.cell.CellFactory;
import it.unipr.sowide.actodes.ca.distributed.Master;
import it.unipr.sowide.actodes.ca.distributed.Node;
import it.unipr.sowide.actodes.core.actor.passive.CycleListActor.TimeoutMeasure;
import it.unipr.sowide.actodes.core.configuration.Configuration;
import it.unipr.sowide.actodes.core.controller.Controller;
import it.unipr.sowide.actodes.core.controller.SpaceInfo;
import it.unipr.sowide.actodes.core.distribution.activemq.ActiveMqConnector;
import it.unipr.sowide.actodes.core.executor.Length;
import it.unipr.sowide.actodes.core.executor.passive.CycleScheduler;
import it.unipr.sowide.actodes.core.service.logging.BinaryWriter;
import it.unipr.sowide.actodes.core.service.logging.Logger;
//import it.unipr.sowide.actodes.examples.ca.gol.Alive;
//import it.unipr.sowide.actodes.examples.ca.gol.Dead;
import it.unipr.sowide.actodes.util.logging.LoggingFilter;
import it.unipr.sowide.actodes.util.space.IntPairVector;
import it.unipr.sowide.actodes.ca.cell.StandaloneBuilder;

/**
 *
 * The {@code  Initiator} class starts a simulation
 * of the classical "game of life".
 *
 * Its execution can involve one or more actors spaces.
 *
 * This class allows to a user to select the type of execution,
 * the number of processors and sensors, the lifetime of the application
 * and the number of nodes of the distributed application.
 *
 * When the execution is distributed on a set of actors spaces, then
 * the first must be an broker actor space, then there can be zero or more
 * node actor space, and finally the last one must be the
 * master actor space.
 *
**/

public final class Initiator
{
  private static final int WIDTH  = 50;
  private static final int HEIGHT = 50;
  private static final int LENGTH = 100;

  //fisso le probabilità di creazione delle istanze per ogni classe
  private static final Double HEALTHY = 0.3;
  private static final Double INFECTED = 0.3;
  private static final Double ILL = 0.4;
  
  private static final String COLORS=Healthy.class.getName()+":violet,"+Infected.class.getName()+":blue,"+Ill.class.getName()+":white";
  
  //dimensione delle varie celle
  private static final int SIZE = 10;
 
  
  //costanti del problema
  public static int k1;
  public static int k2;
  public static int g;
  public static int n;
 

  //private static final int SERVERPORT = 4040;

  private Initiator()
  {
  }

  /**
   * Starts an actor space running the game of life simulation.
   *
   * @param v  the arguments.
   *
   * It does not need arguments.
   *
  **/
  public static void main(final String[] v)
  {
    final int width;
    final int height;

    final long length;

    final String colors;
    final int size;

    final Map<CellFactory, Double> map = new HashMap<>();
    
    Configuration c =  SpaceInfo.INFO.getConfiguration();

    boolean f = c.load(v);

    width  = f ? c.getInt("width") : WIDTH;
    height = f ? c.getInt("height") : HEIGHT;
    length = f ? c.getInt("length") : LENGTH;
    colors = f ? c.getString("colors") : COLORS;
    size = f ? c.getInt("size") : SIZE;

    c.addProperty("width", width);
    c.addProperty("height", height);
    c.addProperty("length", length);
    c.addProperty("colors", colors);
    c.addProperty("size", size);

    
    if (f)
    {
      map.put((x, y, n) -> new Healthy(x, y, n), c.getDouble("healthy.probability"));
      map.put((x, y, n) -> new Ill(x, y, n), c.getDouble("ill.probability"));
      map.put((x, y, n) -> new Infected(x, y, n), c.getDouble("infected.probability"));
      
    }
    else
    {    //creazione delle varie istanze sulla griglia, aventi una determinata posizione (cella)
    	 map.put((x, y, n) -> new Healthy(x, y, n), HEALTHY);
    	 map.put((x, y, n) -> new Ill(x, y, n), ILL);
    	 map.put((x, y, n) -> new Infected(x, y, n), INFECTED);
    }

    c.setFilter(Logger.MESSAGEPROCESSED | Logger.STEP
        | Logger.EXECUTION | Logger.CONFIGURATION);
    //c.addWriter(new ConsoleWriter());
    
    Scanner scanner = new Scanner(System.in);
    
    //inserisco tramite input i valori interi
    System.out.println("Insert k1 value: ");
    k1=scanner.nextInt();
    System.out.println("Insert k2 value: ");
    k2=scanner.nextInt();
    System.out.println("Insert g value: ");
    g=scanner.nextInt();
    System.out.println("Insert n value: ");
    n=scanner.nextInt();
    
    scanner.close();
   
    	//fisso la cartella di destinazione per il file .grid creato
        c.addWriter(new BinaryWriter("./Reaction/react", "grid"));
        c.setLogFilter(new LoggingFilter());
        c.setExecutor(new CycleScheduler(
            new StandaloneBuilder(width, height, map),
            new Length(length), TimeoutMeasure.CY));
        
        System.out.println("Running application...");
        Controller.CONTROLLER.run();
    	System.out.println("Grid has been generated");
  }
}
