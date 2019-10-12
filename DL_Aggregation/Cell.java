package DL_Aggregation;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unipr.sowide.actodes.core.actor.Behavior;
import it.unipr.sowide.actodes.core.actor.CaseFactory;
import it.unipr.sowide.actodes.core.actor.MessageHandler;
import it.unipr.sowide.actodes.core.registry.Reference;
import it.unipr.sowide.actodes.util.space.IntPairVector;

/**
 *
 * The {@code Cell} interface defines a partial implementation
 * of a behavior of an automata cell actor.
 *
**/

public abstract class Cell<S extends Serializable> extends Behavior
{
  private static final long serialVersionUID = 1L;

  private int cx;
  private int cy;

  private List<Reference> neighborhood;

  /**
   * Class constructor.
   *
   * @param x  the x coordinate of the cell.
   * @param y  the y coordinate of the cell.
   * @param n  the neighbor references.
   *
  **/
  public Cell(final int x, final int y, final List<Reference> n)
  {
    this.cx = x;
    this.cy = y;

    this.neighborhood = n;
  }

  /**
   * Class constructor.
   *
   * @param c  the cloned cell.
   *
  **/
  public Cell(final Cell<S> c)
  {
    IntPairVector cs = (IntPairVector) c.log();

    this.cx = cs.getX();
    this.cy = cs.getY();

    this.neighborhood = c.getNeighborhood();
  }
  
  
  
  public Point Coordinates()
  {
	  Point coordinates=new Point();
	  coordinates.x=this.cx;
	  coordinates.y=this.cy;
	  return coordinates;
  }

  /**
   * Gets the references of the actors defining
   * the neighborhood of the cell.
   *
   * @return the references.
   *
  **/
  public List<Reference> getNeighborhood()
  {
    return this.neighborhood;
  }

  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
    MessageHandler h = (m) -> {
      return update();
    };

    c.define(CYCLE, h);
  }

  /**
   * Processes the step message received from the executor.
   *
   * @return
   *
   * either the new behavior or <code>null</code>
   * if the behavior does not change.
   *
  **/
  public abstract Behavior update();

  /** {@inheritDoc} **/
  @Override
  public Object log()
  {
    return new IntPairVector(this.cx, this.cy);
  }
}
