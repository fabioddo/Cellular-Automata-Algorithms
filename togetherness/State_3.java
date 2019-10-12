package Solidarietà;


import Solidarietà.Struct;

import java.util.List;
import java.util.Random;
//import javafx.scene.paint.Color;
import it.unipr.sowide.actodes.util.gui.AutomataViewer;
import it.unipr.sowide.actodes.util.gui.CellShape;
import Solidarietà.CellFactory;
import Solidarietà.Cell;
import it.unipr.sowide.actodes.core.actor.Behavior;
import it.unipr.sowide.actodes.core.actor.BehaviorState;
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


public final class State_3 extends Cell<String>{
	
	private static final long serialVersionUID = 1L;
	
	private static final MessagePattern STRUCT = MessagePattern.contentPattern(new IsInstance(Struct.class));
	
	private static final MessagePattern STATE = MessagePattern.contentPattern(new IsInstance(Integer.class));

	private static final MessagePattern STRING = MessagePattern.contentPattern(new IsInstance(String.class));
	
	//setto lo stato comune a tutte le istanze
	public static int state=3;
	
	private int sum1, sum2, sum3, sum4, sum5, sum6, sum7, sum8;
	
	private Struct contentMessage;
	
	//private Stati newState;
	
	
	public State_3(final int x, final int y,final List<Reference> n ,final List<Reference> m)
	{
		    super(x, y, n, m);
		    
		    //this.newState=Stati.State_3;
		    
		    this.sum1=0;
		    this.sum2=0;
		    this.sum3=0;
		    this.sum4=0;
		    this.sum5=0;
		    this.sum6=0;
		    this.sum7=0;
		    this.sum8=0;
		
		    this.contentMessage=new Struct();

	}
		
	public State_3(final Cell<String> c)
	{
	  super(c);
	
	  //this.newState=Stati.State_3;

	  this.sum1=0;
	  this.sum2=0;
	  this.sum3=0;
	  this.sum4=0;
	  this.sum5=0;
	  this.sum6=0;
	  this.sum7=0;
	  this.sum8=0;

	  this.contentMessage=new Struct();
	  
	}	
	
	
	
	/** {@inheritDoc} **/
	@Override
	public void cases(final CaseFactory c)
	{
	  super.cases(c);

	  //invio ai miei vicini il mio stato
	  
	  MessageHandler h = (m) -> {		
	    getNeighborhood().forEach((i) -> send(i, state));
	    return null;
	  };

	  c.define(START, h);
	  

	  h = (m) -> {
		  
		  if((int)m.getContent()==State_1.state)
			{
				this.sum1++;
			}
			else if((int)m.getContent()==State_2.state)
			{
				this.sum2++;
			}
			else if((int)m.getContent()==State_3.state)
			{
				this.sum3++;
			}
			else if((int)m.getContent()==State_4.state)
			{
				this.sum4++;
			}
			else if((int)m.getContent()==State_5.state)
			{
				this.sum5++;
			}
			else if((int)m.getContent()==State_6.state)
			{
				this.sum6++;
			}
			else if((int)m.getContent()==State_7.state)
			{
				this.sum7++;
			}
			else if((int)m.getContent()==Empty.state)
			{
				this.sum8++;
			}
		  return null;
	  };

	  c.define(STATE, h);
	  

	  
	  
	  h = (m) -> {
		    String appo=(String)m.getContent();
		    
		Initiator.sem=1;
		   
		if(appo.equals(State_1.class.getName())) {
				return new State_1(this);
  			//this.newState=Stati.State_1;
  		}
  		else if(appo.equals(State_2.class.getName())) {
  			return new State_2(this);
  			//this.newState=Stati.State_2;
  		}
  		else if(appo.equals(State_3.class.getName())) {//rimovibile -> non può essere della stessa classe
  			return new State_3(this);
  			//this.newState=Stati.State_3;
  		}
  		else if(appo.equals(State_4.class.getName())) {
  			return new State_4(this);
  			//this.newState=Stati.State_4;
  		}
  		else if(appo.equals(State_5.class.getName())) {
  			return new State_5(this);
  			//this.newState=Stati.State_5;
  		}
  		else if(appo.equals(State_6.class.getName())) {
  			return new State_6(this);
  			//this.newState=Stati.State_6;
  		}
  		else if(appo.equals(State_7.class.getName())) {
  			return new State_7(this);
  			//this.newState=Stati.State_7;
  		}
  		else if(appo.equals(Empty.class.getName())) {
  			return new Empty(this);
  			//this.newState=Stati.Empty;
  		}
		    
		    return null;
	};

		  c.define(STRING, h);
	  
	  
	  
		  
	  h = (m) -> {
		
		  Struct app=(Struct)m.getContent();
		
	
			  if( this.getClass().getName().equals(app.name) || this.sum3==this.getNeighborhood().size())
			  //if( this.getClass().getName().equals(app.name) || this.sum3==8)
			  {
				  Initiator.sem=1;
				  return null;
			  }
			  else
			  {
				  //passo e
				  //cella selezionata NON empty -> State_3
				  //calcolo il guadagno combinato g2
				  
				  int g2 =0;      
				  
				  if(app.name.equals(State_1.class.getName())) {
		    		  g2=(this.sum1+app.sum3)-(this.sum3+app.sum1);
		    		   
		    		}
		    	  else if(app.name.equals(State_2.class.getName())) {
		    		  g2=(this.sum2+app.sum3)-(this.sum3+app.sum2);
		    		    
		    		}
		    	  else if(app.name.equals(State_4.class.getName())) {
		    		  g2=(this.sum4+app.sum3)-(this.sum3+app.sum4);
		    		    
		    		}
		    	  else if(app.name.equals(State_5.class.getName())) {
		    		  g2=(this.sum5+app.sum3)-(this.sum3+app.sum5);
		    		  
		    		}
		    	  else if(app.name.equals(State_6.class.getName())) {
		    		  g2=(this.sum6+app.sum3)-(this.sum3+app.sum6);
		    		}
		    	  else if(app.name.equals(State_7.class.getName())) {
		    		  g2=(this.sum7+app.sum3)-(this.sum3+app.sum7);
		    		}
				  //non può mai essere una cella "Empty" la prima
				  
		    	  //else if(app.name.equals(Empty.class.getName())) {
		    		  //g2=(this.sum8+app.sum2)-(this.sum2+app.sum8);
		    	  //}
		  
				  if(g2>=0) {
					  //le celle si scambiano di posizione
					  
					  //quindi invio la stringa col nome della mia classe al primo che ha inviato la struct e setto il mio nuovo stato
					  send(app.Ref,this.getClass().getName());

					  if(app.name.equals(State_1.class.getName())) {
		    				return new State_1(this);
			    			//this.newState=Stati.State_1;
			    		}
			    		else if(app.name.equals(State_2.class.getName())) {
			    			return new State_2(this);
			    			//this.newState=Stati.State_2;
			    		}
			    		//else if(app.name.equals(State_3.class.getName())) {
			    			//return new State_3(this);
			    			//this.newState=Stati.State_3;
			    		//}
			    		else if(app.name.equals(State_4.class.getName())) {
			    			return new State_4(this);
			    			//this.newState=Stati.State_4;
			    		}
			    		else if(app.name.equals(State_5.class.getName())) {
			    			return new State_5(this);
			    			//this.newState=Stati.State_5;
			    		}
			    		else if(app.name.equals(State_6.class.getName())) {
			    			return new State_6(this);
			    			//this.newState=Stati.State_6;
			    		}
			    		else if(app.name.equals(State_7.class.getName())) {
			    			return new State_7(this);
			    			//this.newState=Stati.State_7;
			    		}
			    		//else if(app.name.equals(Empty.class.getName())) {
			    			//return new Empty(this);
			    			//this.newState=Stati.Empty;
			    		//}
				  }
				  else if(g2==-1 || g2==-2) {
					//le celle si scambiano di posizione ma con una probabilità 1/m
					  	Random rand2 =new Random();
		    			double val2 =rand2.nextDouble();
		    			if(val2<(double)1/Initiator.m) {
		    				
		    				send(app.Ref,this.getClass().getName());

		    				if(app.name.equals(State_1.class.getName())) {
			    				return new State_1(this);
				    			//this.newState=Stati.State_1;
				    		}
				    		else if(app.name.equals(State_2.class.getName())) {
				    			return new State_2(this);
				    			//this.newState=Stati.State_2;
				    		}
				    		//else if(app.name.equals(State_3.class.getName())) {
				    			//return new State_3(this);
				    			//this.newState=Stati.State_3;
				    		//}
				    		else if(app.name.equals(State_4.class.getName())) {
				    			return new State_4(this);
				    			//this.newState=Stati.State_4;
				    		}
				    		else if(app.name.equals(State_5.class.getName())) {
				    			return new State_5(this);
				    			//this.newState=Stati.State_5;
				    		}
				    		else if(app.name.equals(State_6.class.getName())) {
				    			return new State_6(this);
				    			//this.newState=Stati.State_6;
				    		}
				    		else if(app.name.equals(State_7.class.getName())) {
				    			return new State_7(this);
				    			//this.newState=Stati.State_7;
				    		}
				    		//else if(app.name.equals(Empty.class.getName())) {
				    			//return new Empty(this);
				    			//this.newState=Stati.Empty;
				    		//}
			    			
		    			}
  
				  }

			  }

			  Initiator.sem=1;		  
			  return null;
		  };

	  c.define(STRUCT, h);
	  
	  
	  
	  
 h = (m) -> {

	    if(!(this.sum1==getNeighborhood().size() || this.sum2==getNeighborhood().size() || 
				   this.sum3==getNeighborhood().size() || this.sum4==getNeighborhood().size() ||
				   this.sum5==getNeighborhood().size() || this.sum6==getNeighborhood().size() ||
				   this.sum7==getNeighborhood().size() || this.sum8==getNeighborhood().size()) && Initiator.sem==1)
	    {
	    	
			  		Initiator.sem=0;
			  		
			  		
				  //compilo la struct da inviare	
			  		
				  this.contentMessage.name=this.getClass().getName();
				  this.contentMessage.Ref=this.getReference();
				
				  this.contentMessage.sum1=this.sum1;
				  this.contentMessage.sum2=this.sum2;
				  this.contentMessage.sum3=this.sum3;
				  this.contentMessage.sum4=this.sum4;
				  this.contentMessage.sum5=this.sum5;
				  this.contentMessage.sum6=this.sum6;
				  this.contentMessage.sum7=this.sum7;
				  this.contentMessage.sum8=this.sum8;
				  				  
				  //invio la struct ad un vicino scelto a random
				  
				  Random rand=new Random();
				  int val=rand.nextInt(this.getNeighborhood2().size());
				  send(this.getNeighborhood2().get(val), this.contentMessage);
				  
	
			  }
	    
	    
	    this.sum1=0;
	    this.sum2=0;
	    this.sum3=0;
	    this.sum4=0;
	    this.sum5=0;
	    this.sum6=0;
	    this.sum7=0;
	    this.sum8=0;
	    getNeighborhood().forEach((i) -> send(i, state));
		
	    
		
		return null;
	};

	  
	  c.define(CYCLE, h);

	}
	


	/** {@inheritDoc} **/
	@Override
	public Behavior update()
	
	{ 
		
		//System.out.println(this.newState);
		/*switch(this.newState) {
		case State_1:
			return new State_1(this);
		case State_2:
			return new State_2(this);
		case State_3:
			return new State_3(this);
		case State_4:
			return new State_4(this);
		case State_5:
			return new State_5(this);
		case State_6:
			return new State_6(this);
		case State_7:
			return new State_7(this);
		case Empty:
			return new Empty(this);
		default:
			break;
		}*/
		

		this.sum1=0;
	    this.sum2=0;
	    this.sum3=0;
	    this.sum4=0;
	    this.sum5=0;
	    this.sum6=0;
	    this.sum7=0;
	    this.sum8=0;
	    getNeighborhood().forEach((i) -> send(i, state));
		  	
		
		return null;
		  	
		}
		
}
