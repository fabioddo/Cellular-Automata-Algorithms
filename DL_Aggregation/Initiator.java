
package DL_Aggregation;

import java.util.HashMap;
import java.lang.Math.*;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

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
import it.unipr.sowide.actodes.util.logging.LoggingFilter;
import it.unipr.sowide.actodes.util.space.IntPairVector;

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
  //numero di generazioni
  private static final int LENGTH = 100;

  private static final String COLORS =
      Fixed.class.getName() + ":green," + Mobile.class.getName() + ":black,"+ Empty.class.getName() + ":black,"+ Seed.class.getName()+":white";

  private static final int SIZE = 10;
  
  public static int q;
  public static int s;
  public static int k1;
  public static int k2;


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
    
    //richiedo un valore in input ed eseguo un controllo su di esso
    do {
    	System.out.println("Seleziona un valore di q tra 2,4,8,64");
    	q=scanner.nextInt();
    }
    while(q!=2 && q!=4 && q!=8 && q!=64);
    System.out.println("q: "+q);
    
    System.out.println("Inserire dimensione della griglia s:");
	s=scanner.nextInt();
	System.out.println("s: "+s);
	
    
	Random rand = new Random();
	//adattato al range desiderato
	k1=rand.nextInt(96)+5;
	System.out.println("k1: "+k1+"% (celle presenti sulla griglia inizialmente)");
	
	int app=Math.min(s,250);
	k2=rand.nextInt(app)+1;
	System.out.println("k2: "+k2+" (celle seme-seed)");
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
      map.put((x, y, n) ->
        new Fixed(x, y, n), c.getDouble("alive.probability"));
      map.put((x, y, n) ->
        new Mobile(x, y, n), c.getDouble("dead.probability"));
    }
    else
    {
      float appo=(float)k2/(s*s);
      float appo2=(float)k1/100;
      System.out.println("PROB FIXED: "+(double)0);
      System.out.println("PROB SEED: "+(double)appo);
      System.out.println("PROB MOBILE: "+(double)(appo2-appo));
      
      System.out.println("PROB Empty: "+(double)(1-appo2));
      
      
      
      map.put((x, y, n) -> new Fixed(x, y, n), (double) 0);//=0.0
      
      map.put((x, y, n) -> new Seed(x, y, n), (double) appo);
      
      map.put((x, y, n) -> new Mobile(x, y, n), (double) (appo2-appo));
      
      map.put((x, y, n) -> new Empty(x, y, n), (double) (1-appo2));
      
      
    }

    c.setFilter(Logger.MESSAGEPROCESSED | Logger.STEP
        | Logger.EXECUTION | Logger.CONFIGURATION);
    //c.addWriter(new ConsoleWriter());

    
    c.addWriter(new BinaryWriter("./DL_Aggregation/dl", "grid"));
    c.setLogFilter(new LoggingFilter());
    c.setExecutor(new CycleScheduler(
        new StandaloneBuilder(width, height, map),
        new Length(length), TimeoutMeasure.CY));

    
    System.out.println("Running application...");
    Controller.CONTROLLER.run();
	System.out.println("Grid has been generated");
  }
}
