package qStateLife;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random; 

import it.unipr.sowide.actodes.ca.cell.CellFactory;
import it.unipr.sowide.actodes.ca.cell.StandaloneBuilder;
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
//import qStateLife.StandaloneBuilder;

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

  //imposto le probabilità di creazione per ogni classe (sum=1)
  private static final Double ALIVE_first = 0.1;
  private static final Double ALIVE_second = 0.2;
  private static final Double ALIVE_third = 0.1;
  private static final Double ALIVE_fourth = 0.5;
  private static final Double ALIVE_fifth = 0.1;
  
  //imposto il colore relativo ad ogni classe
  private static final String COLORS=Alive_first.class.getName()+":red,"+Alive_second.class.getName()+":blue,"+Alive_third.class.getName()+":green,"+Alive_fourth.class.getName()+":yellow,"+Alive_fifth.class.getName()+":black";
  
  
  private static final int SIZE = 10;
  
  //dichiarazione costanti del sistema
  public static int q;
  public static int k1;
  public static int k2;
  public static int k3;
  public static int k4;
  
 

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

    
    //inizializzazione costanti in modo random
    Random rand=new Random();
    k2= rand.nextInt(901);
    k1=rand.nextInt(k2);
    k4= rand.nextInt(901);
    k3=rand.nextInt(k4);
    
    
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("Insert q value:");
    q=scanner.nextInt();
    
    scanner.close();
    
    System.out.println("k1:"+k1);
    System.out.println("k2:"+k2);
    System.out.println("k3:"+k3);
    System.out.println("k4:"+k4);
       
    
    //set-up iniziale del sistema (creazione e posizionamento istanze sulla griglia)   
    if (f)
    {
      map.put((x, y, n) -> new Alive_first(x, y, n), c.getDouble("alive_first.probability"));
      map.put((x, y, n) -> new Alive_second(x, y, n), c.getDouble("alive_second.probability"));
      map.put((x, y, n) -> new Alive_third(x, y, n), c.getDouble("alive_third.probability"));
      map.put((x, y, n) -> new Alive_fourth(x, y, n), c.getDouble("alive_fourth.probability"));
      map.put((x, y, n) -> new Alive_fifth(x, y, n), c.getDouble("alive_fifth.probability"));
      
    }
    else
    {  
    	 map.put((x, y, n) -> new Alive_first(x, y, n), ALIVE_first);
    	 map.put((x, y, n) -> new Alive_second(x, y, n), ALIVE_second);
    	 map.put((x, y, n) -> new Alive_third(x, y, n), ALIVE_third);
    	 map.put((x, y, n) -> new Alive_fourth(x, y, n), ALIVE_fourth);
    	 map.put((x, y, n) -> new Alive_fifth(x, y, n), ALIVE_fifth);
    }
      

    c.setFilter(Logger.MESSAGEPROCESSED | Logger.STEP
        | Logger.EXECUTION | Logger.CONFIGURATION);
    //c.addWriter(new ConsoleWriter());
    
        c.addWriter(new BinaryWriter("./qStateLife/qState", "grid"));
        c.setLogFilter(new LoggingFilter());
        c.setExecutor(new CycleScheduler(
        	
            new StandaloneBuilder(width, height, map),
            new Length(length), TimeoutMeasure.CY));
   
        
        System.out.println("Running application...");
        Controller.CONTROLLER.run();
    	System.out.println("Grid has being generated");
  }
}
