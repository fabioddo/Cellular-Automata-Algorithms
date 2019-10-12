package Viral;

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
  
  private static final int LENGTH = 100;

  
  private static final Double HEALTHY = 0.6;
  private static final Double INFECTED = 0.2;
  private static final Double FULLY_INFECTED = 0.1;
  private static final Double EMPTY = 0.1;
  
  //associazione dei colori alle classi
  private static final String COLORS=Healthy.class.getName()+":white,"+Fully_Infected.class.getName()+":blue,"+Infected.class.getName()+":red,"
		  +Empty.class.getName()+":black";
  
  
  private static final int SIZE = 10;
 
  
  public static int k1;
  public static int k2;
  public static int k3;
  public static int q;
  private static int s;


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
    
    
    Scanner scanner = new Scanner(System.in);

    //chiedo all'utente di inserire le costanti del problema
    System.out.println("Insert k1 value from 0 to 100: ");
    k1=scanner.nextInt();
    if(k1<0 || k1>100)
    {
    	System.out.println("Valore non consentito per k1");
    	System.exit(1);
    }
    System.out.println("Insert k2 value from 0 to 9999: ");
    k2=scanner.nextInt();
    if(k2<0 || k2>9999)
    {
    	System.out.println("Valore non consentito per k2");
    	System.exit(1);
    }
    System.out.println("Insert k3 value from 0 to 100: ");
    k3=scanner.nextInt();
    if(k3<0 || k3>100)
    {
    	System.out.println("Valore non consentito per k3");
    	System.exit(1);
    }
    
    //inserisco anche le dimensioni della griglia (height x width -> s x s)
    System.out.println("Insert s value: ");
    s=scanner.nextInt();
    if(s<1)//non esisterebbe la griglia
    {
    	System.out.println("Valore non consentito per s");
    	System.exit(1);
    }
    //inserisco il numero di stati possibili per le celle
    System.out.println("Insert q value from 2 to 254: ");
    q=scanner.nextInt();
    if(q<2 || q>254)
    {
    	System.out.println("Valore non consentito per q");
    	System.exit(1);
    }
    
    scanner.close();
    

    
    Configuration c =  SpaceInfo.INFO.getConfiguration();

    boolean f = c.load(v);

    width  = f ? c.getInt("width") : s;
    height = f ? c.getInt("height") : s;
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
      map.put((x, y, n) -> new Fully_Infected(x, y, n), c.getDouble("fully_infected.probability"));
      map.put((x, y, n) -> new Infected(x, y, n), c.getDouble("infected.probability"));
      map.put((x, y, n) -> new Empty(x, y, n), c.getDouble("empty.probability"));
      
    }
    else
    {  
    	 map.put((x, y, n) -> new Healthy(x, y, n), HEALTHY);
    	 map.put((x, y, n) -> new Fully_Infected(x, y, n), FULLY_INFECTED);
    	 map.put((x, y, n) -> new Infected(x, y, n), INFECTED);
    	 map.put((x, y, n) -> new Empty(x, y, n), EMPTY); //istanzio anche le celle vuote ed associo a loro un certo behavior
    	 //(sempre di colore nero)
    }
    
    c.setFilter(Logger.MESSAGEPROCESSED | Logger.STEP
        | Logger.EXECUTION | Logger.CONFIGURATION);
    //c.addWriter(new ConsoleWriter());
    

        c.addWriter(new BinaryWriter("./Viral/rep", "grid"));
        c.setLogFilter(new LoggingFilter());
        c.setExecutor(new CycleScheduler(      	
            new StandaloneBuilder(width, height, map),
            new Length(length), TimeoutMeasure.CY));
      
        
        System.out.println("Running application...");
        Controller.CONTROLLER.run();
    	System.out.println("Grid has been generated");
  }
}
