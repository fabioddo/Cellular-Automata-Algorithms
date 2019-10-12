package Solidarietà;


import Solidarietà.Struct;

import java.util.List;

import Solidarietà.Initiator;

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

//classe definita per la gestione delle celle vuote
public final class Empty extends Cell<String>{
	
	private static final long serialVersionUID = 1L;
	
	private static final MessagePattern STRUCT = MessagePattern.contentPattern(new IsInstance(Struct.class));
	
	private static final MessagePattern STATE = MessagePattern.contentPattern(new IsInstance(Integer.class));

	//pattern non utilizzato siccome non invia mai una struct
	//private static final MessagePattern STRING = MessagePattern.contentPattern(new IsInstance(String.class));
	
	//valore associato alle istanze delle celle vuote
	public static int state=8;
	
	//variabili private
	private int sum1, sum2, sum3, sum4, sum5, sum6, sum7;
	
	//private Struct contentMessage=new Struct();
	
	//private Stati newState;
	
	
	public Empty(final int x, final int y,final List<Reference> n ,final List<Reference> m)
	{
		    super(x, y, n, m);
		    
		    //this.newState=Stati.Empty;
		 
		    this.sum1=0;
		    this.sum2=0;
		    this.sum3=0;
		    this.sum4=0;
		    this.sum5=0;
		    this.sum6=0;
		    this.sum7=0;
		    //this.sum8=0;

	}
		
	public Empty(final Cell<String> c)
	{
	  super(c);
	
	  //this.newState=Stati.Empty;
	
	    this.sum1=0;
	    this.sum2=0;
	    this.sum3=0;
	    this.sum4=0;
	    this.sum5=0;
	    this.sum6=0;
	    this.sum7=0;
	    //this.sum8=0;
	  
	}	
	
	
	
	/** {@inheritDoc} **/
	@Override
	public void cases(final CaseFactory c)
	{
	  super.cases(c);

	  //invio ai miei vicini il mio stato
	  //lo gestisco nel MessageHandler di tipo CYCLE
	  
	  MessageHandler h = (m) -> {		
	    getNeighborhood().forEach((i) -> send(i, state));
	    return null;
	  };

	  c.define(START, h);
	  
		  

	  h = (m) -> {
		  
		  //per ogni valore che arriva all'istanza incremento uno dei suoi contatori 
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
			//else if((int)m.getContent()==Empty.state)
			//{
				//this.sum8++;
			//}
		  
		  
		  return null;
	  };

	  c.define(STATE, h);
	  
	  //non utilizzata siccome esegue un return in base alla classe di appartenenza della prima cella che ha inviato la struct,
	  //la quale non può mai essere "Empty"
  
	  /*h = (m) -> {
		    String appo=(String)m.getContent();
		    if(appo.equals(State_1.class.getName())) {
		    	return new State_1(this);
		    	//this.newState=Stati.State_1;
		    }
		    else if(appo.equals(State_2.class.getName())) {
		    	return new State_2(this);
		    	//this.newState=Stati.State_2;
		    }
		    else if(appo.equals(State_3.class.getName())) {
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

		  c.define(STRING, h);*/
	  
	  

	  
	  h = (m) -> {
		//struct ricevuta, creo una variabile d'appoggio
		  Struct app=(Struct)m.getContent();

				//passo d
				//calcolo il guadagno g
				  int g=0;
				  //osservo in quale stato si trova l'istanza che mi ha inviato la struct
		    	  if(app.name.equals(State_1.class.getName())) {
		    			g = this.sum1-app.sum1;
		    		}
		    	  else if(app.name.equals(State_2.class.getName())) {
		    			g = this.sum2-app.sum2;
		    		}
		    	  else if(app.name.equals(State_3.class.getName())) {
		    			g = this.sum3-app.sum3;
		    		}
		    	  else if(app.name.equals(State_4.class.getName())) {
		    			g = this.sum4-app.sum4;
		    		}
		    	  else if(app.name.equals(State_5.class.getName())) {
		    			g = this.sum5-app.sum5;
		    		}
		    	  else if(app.name.equals(State_6.class.getName())) {
		    			g = this.sum6-app.sum6;
		    		}
		    	  else if(app.name.equals(State_7.class.getName())) {
		    			g = this.sum7-app.sum7;
		    		}
		    	  //commentato siccome una cella "Empty" non può mai inviare una struct
		    	  
		    	  //else if(app.name.equals(Empty.class.getName())) {
		    			//g = this.sum8-app.sum8;
		    		//}
		    	  
		    	  
		    	  if(g>=0) {
		    		    //invio alla prima cella un messaggio che triggera il suo cambio di stato in "Empty"
		    		    send(app.Ref,this.getClass().getName());
		    		    
		    		    //cambio il mio stato da "Empty" a quello della prima cella (che aveva precedentemente)
		    			if(app.name.equals(State_1.class.getName())) {
		    				return new State_1(this);
			    			//this.newState=Stati.State_1;
			    		}
			    		else if(app.name.equals(State_2.class.getName())) {
			    			return new State_2(this);
			    			//this.newState=Stati.State_2;
			    		}
			    		else if(app.name.equals(State_3.class.getName())) {
			    			return new State_3(this);
			    			//this.newState=Stati.State_3;
			    		}
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
		    		else if(g==-1) {
		    			//eseguo le stesse operazioni di prima ma con una certa probabilità di 1 su m
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
				    		else if(app.name.equals(State_3.class.getName())) {
				    			return new State_3(this);
				    			//this.newState=Stati.State_3;
				    		}
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
		    //indico che è finito lo scambio tra le due celle e riattivo il semaforo
		    Initiator.sem=1;
		    return null;
		  };

	  c.define(STRUCT, h);
	  
	  
	  
	  
	  
	  h = (m) -> { //codice al posto di update()
		    
		   
		    this.sum1=0;
		    this.sum2=0;
		    this.sum3=0;
		    this.sum4=0;
		    this.sum5=0;
		    this.sum6=0;
		    this.sum7=0;
		    //this.sum8=0;
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
			    //this.sum8=0;
			    getNeighborhood().forEach((i) -> send(i, state));
		
		    
	  	return null;
		
	}
	
	
		
}
