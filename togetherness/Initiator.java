package Solidarietà;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;
import Solidarietà.CellFactory;
import Solidarietà.StandaloneBuilder;
//import it.unipr.sowide.actodes.ca.cell.CellFactory;
import it.unipr.sowide.actodes.ca.distributed.Master;
import it.unipr.sowide.actodes.ca.distributed.Node;
import it.unipr.sowide.actodes.core.actor.passive.CycleListActor.TimeoutMeasure;
import it.unipr.sowide.actodes.core.configuration.Configuration;
import it.unipr.sowide.actodes.core.controller.Controller;
import it.unipr.sowide.actodes.core.controller.SpaceInfo;
import it.unipr.sowide.actodes.core.distribution.activemq.ActiveMqConnector;
import it.unipr.sowide.actodes.core.executor.Length;
import it.unipr.sowide.actodes.core.executor.passive.CycleScheduler;
import it.unipr.sowide.actodes.core.registry.Reference;
import it.unipr.sowide.actodes.core.service.logging.BinaryWriter;
import it.unipr.sowide.actodes.core.service.logging.Logger;
//import it.unipr.sowide.actodes.examples.ca.gol.Alive;
//import it.unipr.sowide.actodes.examples.ca.gol.Dead;
import it.unipr.sowide.actodes.util.logging.LoggingFilter;
import it.unipr.sowide.actodes.util.space.IntPairVector;
//import it.unipr.sowide.actodes.ca.cell.StandaloneBuilder;

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
  //dimensioni della griglia definite in input dall'utente (variabile s)
	
  //private static final int WIDTH  = 50;
  //private static final int HEIGHT = 50;
 
  //numero di generazioni
  private static final int LENGTH = 10000;
  //dimensione delle celle
  private static final int SIZE = 10;
 
  //variabili del problema
  public static int q;
  public static int s;
  public static int n;
  public static double concentration;
  public static int m;
  
  //semaforo globale per la mutua esclusione delle istanze delle classi
  public static int sem=1;
  
  
  /*public enum Stati{
	  State_1,
	  State_2,
	  State_3,
	  State_4,
	  State_5,
	  State_6,
	  State_7,
	  Empty;
  }*/
 
  //associazione dei colori alle (s*s istanze delle) classi
  private static final String COLORS=State_1.class.getName()+":yellow,"+State_2.class.getName()+":blue,"+
		  							 State_3.class.getName()+":cyan,"+State_4.class.getName()+":orange,"+
		  							 State_5.class.getName()+":purple,"+State_6.class.getName()+":gray,"+
		  							 State_7.class.getName()+":red,"+
		  							 Empty.class.getName()+":black";
  

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
    
    //richiesta in input dei valori
    
	//numero di stati (quindi classi) possibili delle celle
    do {
    	System.out.println("Insert q value from 1 to 7: ");
    	q=scanner.nextInt();
    }
    while(!(q>=1 && q<=7));
    	
    System.out.println("q: "+q);
  
  
    //dimensioni della griglia
    do {
    	System.out.println("Insert s value positivo: ");
    	s=scanner.nextInt();
    }
    while(!(s>0));
    	
    System.out.println("s: "+s);
    
    
    //distanza max dalla prima cella selezionata a random
    do {
    	System.out.println("Insert n value : 1<=n<="+s);
    	n=scanner.nextInt();
    }
    while(!(1<=n && n<=s));
    	
    System.out.println("n: "+n);
    
    
    //concentrazione delle celle non vuote
    Random rand = new Random();
    concentration=rand.nextDouble();
    System.out.println("concentration: "+concentration);
   // concentration=0.7; -> testing
    
    
    //valore che determinerà la probabilità (verrà fatto il suo reciproco)
    do {
    	System.out.println("Insert m value from 1 to 9999: ");
    	m=scanner.nextInt();
    }
    while(!(m>=1 && m<=9999));
    System.out.println("m: "+m);

    
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

    //a seconda del valore di q precedentemente inserito
    switch(q) {
	case 1:
		map.put((x, y, n ,m) -> new State_1(x, y, n,m), concentration/q);
		break;
	case 2:
		map.put((x, y, n ,m) -> new State_1(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_2(x, y, n,m), concentration/q);
		break;
	case 3:
		map.put((x, y, n ,m) -> new State_1(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_2(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_3(x, y, n,m), concentration/q);
		break;
	case 4:
		map.put((x, y, n ,m) -> new State_1(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_2(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_3(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_4(x, y, n,m), concentration/q);
		break;
	case 5:
		map.put((x, y, n ,m) -> new State_1(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_2(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_3(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_4(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_5(x, y, n,m), concentration/q);
		break;
	case 6:
		map.put((x, y, n ,m) -> new State_1(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_2(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_3(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_4(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_5(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_6(x, y, n,m), concentration/q);
		break;
	case 7:
		map.put((x, y, n ,m) -> new State_1(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_2(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_3(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_4(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_5(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_6(x, y, n,m), concentration/q);
		map.put((x, y, n ,m) -> new State_7(x, y, n,m), concentration/q);
		break;
	default:
		break;
	}
    //istanziazione sulla griglia delle celle vuote
    map.put((x, y, n,m) -> new Empty(x, y, n,m), 1.0-concentration);
    	

    c.setFilter(Logger.MESSAGEPROCESSED | Logger.STEP
        | Logger.EXECUTION | Logger.CONFIGURATION);
    //c.addWriter(new ConsoleWriter());

        c.addWriter(new BinaryWriter("./Solidarietà/together", "grid"));
        c.setLogFilter(new LoggingFilter());
        c.setExecutor(new CycleScheduler(
            new StandaloneBuilder(width, height, map, n),
            new Length(length), TimeoutMeasure.CY));
    //}
        
        System.out.println("Running application...");
        Controller.CONTROLLER.run();
    	System.out.println("Grid has been generated");
  }
}
