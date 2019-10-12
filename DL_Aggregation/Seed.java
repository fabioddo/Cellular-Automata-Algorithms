package DL_Aggregation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unipr.sowide.actodes.ca.cell.Cell;
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

public final class Seed extends Cell<String>
{
  private static final long serialVersionUID = 1L;

  private static final MessagePattern STATE = MessagePattern.contentPattern(new IsInstance(Integer.class));
  
  //fisso il valore pari alla costante del problema richiesta in input
  private static int state=Initiator.q;
  

  /**
   * Class constructor.
   *
   * @param x  the x coordinate of the cell.
   * @param y  the y coordinate of the cell.
   * @param n  the neighbor references.
   *
  **/
  public Seed(final int x, final int y, final List<Reference> n)
  {
    super(x, y, n);
  }

  /**
   * Class constructor.
   *
   * @param c  the cloned cell.
   *
  **/
  public Seed(final Cell<String> c)
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
    
  }

  
  /** {@inheritDoc} **/
  @Override
  public Behavior update()
  {
	  getNeighborhood().forEach((i)->send(i, state));
	  
	  return null;
  }
}
