package Solidarietà;

import java.io.Serializable;
import java.util.List;

import it.unipr.sowide.actodes.core.actor.Behavior;
import it.unipr.sowide.actodes.core.registry.Reference;

/**
 *
 * The {@code CellFactory} interface defines a method for
 * creating the possible behaviors of an automata cell actor.
 *
**/

public interface CellFactory extends Serializable
{
  /**
   * Creates the behavior of a cell actor.
   *
   * @param n  the references of the neighbor cell actors.
   * @param x  the x coordinate of the cell.
   * @param y  the y coordinate of the cell.
   * @param m  the references of the neighbor2 cell actors.
   *
   * @return the behavior.
   *
  **/
  Behavior create(final int x, final int y, final List<Reference> n, List<Reference> m);
}
