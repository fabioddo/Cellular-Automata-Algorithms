
package Solidarietà;

import java.util.List;
import java.util.Map;

import it.unipr.sowide.actodes.core.configuration.Builder;
import it.unipr.sowide.actodes.core.registry.Reference;

/**
 *
 * The {@code AutomataBuilder} class provides a partial implementation
 * of a builder that creates an initial set of actors of a 2D cellular automata.
 *
**/

public abstract class AutomataBuilder extends Builder
{
  protected final int width;
  protected final int height;
  protected final Map<CellFactory, Double> map;
  protected final Data[][] actors;
  
  protected final int radius;

  /**
   * Class constructor.
   *
   * @param w  the cellular automata width.
   * @param h  the cellular automata height.
   * @param m  the cell factory - probability map.
   * @param r  the researching radius
  **/
  public AutomataBuilder(final int w, final int h,
      final Map<CellFactory, Double> m, final int r)
  {
    this.width  = w;
    this.height = h;
    this.map    = m;
    this.actors = new Data[this.width][this.height];
    this.radius = r;
  }

  /**
   *
   * The {@code Data} inner class defines a simple data
   * structure maintaining the information associate with
   * a cell actor.
   *
  **/
  protected class Data
  {
    /**
     * Class constructor.
     *
     * @param r  the reference of current cell actor.
     * @param n  the references of the neighbor cell actors.
     * @param neighbors2 
     * @param m  the references of the neighbor2 cell actors.
     *
    **/
    public Data(final Reference r, final List<Reference> n, List<Reference> m)
    {
      this.reference    = r;
      this.neighborhood = n;
      this.neighborhood2 = m;
     
    }

    private Reference reference;
    private List<Reference> neighborhood;
	private List<Reference> neighborhood2;

    /**
     * Gets the reference of the cell actor.
     *
     * @return the reference.
     *
    **/
    public Reference getReference()
    {
      return this.reference;
    }

    /**
     * Gets the references of the neighbor cell actors.
     *
     * @return the references.
     *
    **/
    public List<Reference> getNeighborhood()
    {
      return this.neighborhood;
    }

	public List<Reference> getNeighborhood2() {
		return this.neighborhood2;
	}
    
 
  }
}
