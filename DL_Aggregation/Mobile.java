package DL_Aggregation;

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
import it.unipr.sowide.actodes.util.space.DoublePairVector;

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

public final class Mobile extends Cell<String>
{
  private static final long serialVersionUID = 1L;

  private static final MessagePattern STATE = MessagePattern.contentPattern(new IsInstance(Integer.class));
  
  
  private static int state=1;
  
  private int fixed;
  private int empty;
  private int mobile;//serve solo per contare i vicini della stessa classe ma eliminabile
  
  private List<Reference> vicinato;

  /**
   * Class constructor.
   *
   * @param x  the x coordinate of the cell.
   * @param y  the y coordinate of the cell.
   * @param n  the neighbor references.
   *
  **/
  
  public Mobile(final int x, final int y, final List<Reference> n)
  {
    super(x, y, n);

    
    this.fixed=0;
	this.empty=0;
	this.mobile=0;
	//creo una lista per l'inserimento delle possibili reference "Empty"
	this.vicinato=new ArrayList<>();
  }

  /**
   * Class constructor.
   *
   * @param c  the cloned cell.
   *
  **/
  public Mobile(final Cell<String> c)
  {
    super(c);

    this.fixed=0;
 	this.empty=0;
 	this.mobile=0;
 	this.vicinato=new ArrayList<>();
  }

  
  /** {@inheritDoc} **/
  @Override
  public void cases(final CaseFactory c)
  {
    super.cases(c);
 
    MessageHandler h = (m) -> {
        //invio il proprio stato = 1
    	getNeighborhood().forEach((i)->send(i, state));
        return null;
    };

    c.define(START, h);
    
    
    h = (m) -> {
        
    	//System.out.println("riconosciuto pattern vicinato");

    		if((Integer)m.getContent()==0) {//siccome abbiamo definito Empty.state come campo private
    			this.empty++;
    			//System.out.println(m.getSender());
    			this.vicinato.add((Reference)m.getSender());
    		}
    		else if((Integer)m.getContent()==1) {
    			this.mobile++;
    			//System.out.println(m.getSender());
    		}
    		else {
    			this.fixed++;
    			//System.out.println(m.getSender());
    		}
    		
    		return null;
    };
    		
    c.define(STATE, h);
    

    h = (m) -> {
    	
    	if(this.fixed>0) {
			Random rand =new Random();
			int val=rand.nextInt(Initiator.q-1)+2;
			return new Fixed(this,val);
		}
		else {
			//numero di celle vicine "Fixed" = 0
			if(this.empty>0)//se sono presenti celle vuote nel proprio vicinato
			{
				//scelgo una cella "Empty" random
				Random rand2=new Random();
				int val2=rand2.nextInt(this.vicinato.size());
				send(this.vicinato.get(val2),"Trigger");
				
				return new Empty(this);
			}
			
		}
		

    	this.vicinato.clear();
        this.fixed=0;
  	  	this.empty=0;
  	  	this.mobile=0;
  	  
  	  	getNeighborhood().forEach((i)->send(i, state));

        return null;
      };

      c.define(CYCLE, h);
      
   
  }

  /** {@inheritDoc} **/
  @Override
  public Behavior update()
  {
	  
	  this.vicinato.clear();
	  this.fixed=0;
	  this.empty=0;
	  this.mobile=0;
	  
	  getNeighborhood().forEach((i)->send(i, state));
	  
	  return null;
  }
}
