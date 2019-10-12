package DL_Aggregation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DL_Aggregation.Cell;
import it.unipr.sowide.actodes.core.actor.Behavior;
import it.unipr.sowide.actodes.core.actor.CaseFactory;
import it.unipr.sowide.actodes.core.actor.MessageHandler;
import it.unipr.sowide.actodes.core.actor.MessagePattern;
import it.unipr.sowide.actodes.core.configuration.Configuration;
import it.unipr.sowide.actodes.core.configuration.PropertiesGroup;
import it.unipr.sowide.actodes.core.controller.SpaceInfo;
import it.unipr.sowide.actodes.core.filtering.constraint.IsEqual;
import it.unipr.sowide.actodes.core.filtering.constraint.IsInstance;
import it.unipr.sowide.actodes.core.interaction.Status;
import it.unipr.sowide.actodes.core.registry.Reference;

/**
 *
 * The {@code Fixed} class defines a behavior identifying the dead
 * state of a cell of the game of life.
 *
 * During the simulation this behavior processes the messages coming
 * from the living neighbors until their number allows it to move
 * to the living state.
 *
 * Default minimal and maximal values for moving to the alive state
 * are both 3.
 *
 * These values can be modifies through the use of configuration properties.
 *
**/

public final class Empty extends Cell<String>
{
  private static final long serialVersionUID = 1L;

  private static final MessagePattern STRING = MessagePattern.contentPattern(new IsInstance(String.class));
  
  
  private static int state=0;
  
  /**
   * Class constructor.
   *
   * @param x  the x coordinate of the cell.
   * @param y  the y coordinate of the cell.
   * @param n  the neighbor references.
   *
  **/
  
  public Empty(final int x, final int y, final List<Reference> n)
  {
	//costruisco la cella
    super(x, y, n);
  }

  /**
   * Class constructor.
   *
   * @param c  the cloned cell.
   *
  **/
  
  public Empty(final Cell<String> c)
  {
    super(c);
  }

  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
    super.cases(c);
 
    MessageHandler h = (m) -> {
    	getNeighborhood().forEach((i)->send(i, state));
        return null;
    };

    c.define(START, h);
    

    
    
    h = (m) -> {
        
    	if((String)m.getContent()=="Trigger") {//controllo if superfluo perchè viene inviato soltanto un tipo di stringa
    		int size=Initiator.s;//dichiaro e attribuisco un valore ad una variabile di appoggio
    		Point coordinates=Coordinates();//ritorna le coordinate della cella "Empty" presa in esame (x,y)
    		int x=Math.abs(coordinates.x-(size/2));
    		int y=Math.abs(coordinates.y-(size/2));
    		double distance=Math.sqrt((x*x)+(y*y));//calcolo la distanza dalla posizione centrale della griglia
    		if(distance>(Initiator.s/2)) return null;//se la cella vuota risiede oltre il cerchio di raggio size/2 non cambia il proprio stato
    		else return new Mobile(this);//se la cella risiede all'interno del cerchio cambia il proprio stato
    	}
    	
     	return null;
    };
    
    
    c.define(STRING, h);
  }

  
  
 
  /** {@inheritDoc} **/
  @Override
  public Behavior update()
  {
	  //indico ai miei vicini che sono una cella di tipo "Empty"
	  getNeighborhood().forEach((i)->send(i,state));
	  
	  return null;
  }
}
