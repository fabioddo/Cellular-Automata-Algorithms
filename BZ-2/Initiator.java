package BZ_Reaction2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;
import java.awt.*;

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
  //imposto le dimensioni della griglia ed il numero di generazioni
  private static final int WIDTH  = 50;
  private static final int HEIGHT = 50;
  private static final int LENGTH = 100;

  //imposto le probabilità
  private static final Double STATE1 = 0.1;
  private static final Double STATEQ = 0.3;
  private static final Double STATEINTERMEDIATE = 0.6;

  //costanti del problema
  public static int q;
  public static int k1;
  public static int k2;
  public static int g;
  
  //dimensione della cella
  private static final int SIZE = 10;
 
  //imposto i colori per le classi
  private static String COLORS=State_1.class.getName()+":red,"+State_Q.class.getName()+":green,";
  
  //creo una lista di stringhe che definiscono i vari colori
  private static final String[] ListColors = {"black", "blue", "cyan", "darkGray", "gray", "lightGray", 
  "magenta", "orange", "pink", "white", "yellow"};
  
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
    
    
    /*Map<Color, String> colorMap = new HashMap<Color, String>();
     
    colorMap.put(Color.BLACK, "black");
	colorMap.put(Color.BLUE, "blue");
	colorMap.put(Color.CYAN, "cyan");
	colorMap.put(Color.DARK_GRAY, "darkGray");
	colorMap.put(Color.GRAY, "gray");
	colorMap.put(Color.GREEN, "green");
	colorMap.put(Color.LIGHT_GRAY, "lightGray");
	colorMap.put(Color.MAGENTA, "magenta");
	colorMap.put(Color.ORANGE, "orange");
	colorMap.put(Color.PINK, "pink");
	colorMap.put(Color.RED, "red");
	colorMap.put(Color.WHITE, "white");
	colorMap.put(Color.YELLOW, "yellow");   
    */
    
	Scanner scanner = new Scanner(System.in);
    
    //Inserisco i controlli sul valore inserito dall'utente
    System.out.println("Insert q value from 2 to 255: ");
    q=scanner.nextInt();
    if(q<2 || q>255)
    {
    	System.err.println("Invalid value for q");
    	System.exit(1);
    }
    System.out.println("Insert k1 value from 1 to 8: ");
    k1=scanner.nextInt();
    if(k1<1 || k1>8)
    {
    	System.err.println("Invalid value for k1");
    	System.exit(1);
    }
    System.out.println("Insert k2 value from 1 to 8: ");
    k2=scanner.nextInt();
    if(k2<1 || k2>8)
    {
    	System.err.println("Invalid value for k2");
    	System.exit(1);
    }
    System.out.println("Insert g value from 0 to 100: ");
    g=scanner.nextInt();
    if(g<0 || g>100)
    {
    	System.err.println("Invalid value for g");
    	System.exit(1);
    }
    
    scanner.close();
	
    //associo alla classe intermedia un colore casuale preso dalla lista precedentemente creata
    int value;
    Random rand = new Random();
    value=rand.nextInt(ListColors.length);
    COLORS+=State_Intermediate.class.getName()+":"+ListColors[value];
    
  
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
    	map.put((x, y, n) -> new State_1(x, y, n), c.getDouble("state_1.probability"));	
    	map.put((x, y, n) -> new State_Q(x, y, n), c.getDouble("state_Q.probability"));
    	map.put((x, y, n) -> new State_Intermediate(x, y, n), c.getDouble("state_intermediate.probability"));

    }
    else
    {  
    	//creo le istanze localizzate
    	map.put((x, y, n) -> new State_1(x, y, n), STATE1);
    	map.put((x, y, n) -> new State_Q(x, y, n), STATEQ);
    	map.put((x, y, n) -> new State_Intermediate(x, y, n), STATEINTERMEDIATE);
 	
    }
    
    c.setFilter(Logger.MESSAGEPROCESSED | Logger.STEP
        | Logger.EXECUTION | Logger.CONFIGURATION);
    //c.addWriter(new ConsoleWriter());

        c.addWriter(new BinaryWriter("./Reaction2/react", "grid"));
        c.setLogFilter(new LoggingFilter());
        c.setExecutor(new CycleScheduler(
            new StandaloneBuilder(width, height, map),
            new Length(length), TimeoutMeasure.CY));
        
        
        System.out.println("Running application...");
        Controller.CONTROLLER.run();
    	System.out.println("Grid has being generated");
  }
}
