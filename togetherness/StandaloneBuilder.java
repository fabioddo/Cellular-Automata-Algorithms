
package Solidarietà;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unipr.sowide.actodes.core.actor.Actor;
import it.unipr.sowide.actodes.core.configuration.Chooser;
import it.unipr.sowide.actodes.core.executor.Executor;
import it.unipr.sowide.actodes.core.registry.Reference;

/**
 *
 * The {@code StandaloneBuilder} class builds the initial
 * set of actors of a standalone 2D cellular automata.
 *
**/

public final class StandaloneBuilder extends AutomataBuilder
{
  /**
   * Class constructor.
   *
   * @param w  the cellular automata width.
   * @param h  the cellular automata height.
   * @param m  the cell factory - probability map.
   * @param r  the researching radius
  **/
  public StandaloneBuilder(final int w, final int h,
      final Map<CellFactory, Double> m, final int r)
  {
    super(w, h, m, r);
  }

  /** {@inheritDoc} **/
  @Override
  public void build(final Executor<? extends Actor> e)
  {
    Data[][] actors = new Data[this.width][this.height];

    Chooser<CellFactory> chooser = new Chooser<>(this.map);

    for (int x = 0; x < this.width; x++)
    {
      for (int y = 0; y < this.height; y++)
      {
        CellFactory factory = chooser.choose();

        List<Reference> neighbors = new ArrayList<>();
        List<Reference> neighbors2 = new ArrayList<>();

        Reference r = e.actor(factory.create(x, y, neighbors,neighbors2));

        actors[x][y] = new Data(r, neighbors ,neighbors2);
      }
    }

    for (int x = 0; x < this.width; x++)
    {
      for (int y = 0; y < this.height; y++)
      {
        setNeighbors(x, y, actors);
        setNeighbors2(x, y, actors, this.radius);
      }
    }
  }

  // (x, y) : |x - x0| <= r |y - y0| <= r
  private void setNeighbors(final int x, final int y, final Data[][] d)
  {
    final int[] cx = {0, 1, 1, -1, 1, -1, 0, -1};
    final int[] cy = {1, 0, 1, -1, -1, 1, -1, 0};

    List<Reference> n = d[x][y].getNeighborhood();

    for (int i = 0; i < cx.length; i++)
    {
      n.add(d[index(x + cx[i], this.width)][index(y + cy[i], this.height)]
          .getReference());
    }
  }
  
  private void setNeighbors2(final int x, final int y, final Data[][] d, final int r)
  {
	  
	  //calcolo della lunghezza dei vettori
	  int indice=0;
	  
	  for(int i=-r; i<=r;i++)
	  {
		  for(int l=-r; l<=r; l++)
		  {
			  if(i!=0 || l!=0)
			  {
				  if(Math.abs(i)+Math.abs(l)<=r)
				  {
					  
					  indice++;
				  }
			  }
			    
		  }
		    
	  }
	  
	  //System.out.println("Index: "+indice);
	  
	  final int[] cx= new int[indice];
	  final int[] cy= new int[indice];
	  
	  indice=0;
	 
	  //riempimento dei due vettori
	  for(int i=-r; i<=r;i++)
	  {
		  for(int l=-r; l<=r; l++)
		  {
			  if(i!=0 || l!=0)
			  {
				  if(Math.abs(i)+Math.abs(l)<=r)
				  {
					  
					  //System.out.println("i: "+i +" l: "+l);
					  
					  cx[indice]=i;
					  cy[indice]=l;
					  indice++;
				  }
			  }
			    
		  }
		    
	  }
	  
	  //System.out.println("lunghezza: "+cy.length);  
	
    //final int[] cx = {0, 1,  0, -1};
    //final int[] cy = {1, 0, -1, 0};

    List<Reference> n = d[x][y].getNeighborhood2();

    for (int i = 0; i < cx.length; i++)
    {
    
      n.add(d[index(x + cx[i], this.width)][index(y + cy[i], this.height)]
          .getReference());
    
    }
     
  }

  private int index(final int v, final int l)
  {
	  
	//casi particolari
	  
    /*if (v == -1)
    {
      return l - 1;
    }
    if (v == -2)
    {
      return l - 2;
    }*/
	  
	//implementazione del caso generale  
    for(int i=-1;i>=-l;i--)
    {
    	if (v == i)
    	{
    		return l+i;
    	}
    }
    
    for(int i=l;i<2*l;i++)
    {
    	if (v == i)
    	{
    		return i-l;
    	}
    }
	
    /*if (v == l)
    {
      return 0;
    }*/

    return v;
  }
}
