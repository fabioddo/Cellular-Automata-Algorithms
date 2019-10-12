package Solidarietà;


import Solidarietà.Struct;

import Solidarietà.Initiator;

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


public final class State_1 extends Cell<String>{
	
	private static final long serialVersionUID = 1L;
	
	private static final MessagePattern STRUCT = MessagePattern.contentPattern(new IsInstance(Struct.class));
	
	private static final MessagePattern STATE = MessagePattern.contentPattern(new IsInstance(Integer.class));

	private static final MessagePattern STRING = MessagePattern.contentPattern(new IsInstance(String.class));
	
	//stato associato ad ogni istanza della classe
	public static int state=1;
	private int sum1, sum2, sum3, sum4, sum5, sum6, sum7, sum8;
	
	//struct da inviare ad una cella selezionata in modo casuale all'interno di n passi di distanza dalla prima selezionata
	private Struct contentMessage;
	
	//private Stati newState;
	
	//costruzione della cella e dell'istanza associata
	public State_1(final int x, final int y,final List<Reference> n ,final List<Reference> m)
	{
		    super(x, y, n, m);
		    
		    //this.newState=Stati.State_1;
		   
		    //inizializzazione delle variabili private
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
		
	public State_1(final Cell<String> c)
	{
	  super(c);
	
	  //this.newState=Stati.State_1;
	  
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

	  //invio ai miei vicini il mio stato una volta creata l'istanza
	  
	  MessageHandler h = (m) -> {		
	    getNeighborhood().forEach((i) -> send(i, state));
	    return null;
	  };

	  c.define(START, h);

	  
	  
	  h = (m) -> {
		  
		  //aggiorno i contatori privati
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
	  
		    //dichiaro una variabile d'appoggio che conterrà il nome della classe della seconda cella selezionata in modo random
		    String appo=(String) m.getContent();
		   
		//indico che è possibile proseguire -> lo scambio è terminato siccome dopo si eseguirà un return  
		Initiator.sem=1;
		
		//utilizzo del costrutto else if per ottimizzare la velocità di esecuzione
		
		if(appo.equals(State_1.class.getName())) {//eliminabile -> non può ricevere una stringa da una cella appartenente alla stessa classe
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
		    
		    return null;//non ci arriva mai -> entra di sicuro in una delle condizioni precedenti
		};

		c.define(STRING, h);
	  
	  
	  
	  h = (m) -> {
		  
		Struct app=(Struct)m.getContent();
	

		  //se sono io ad aver inviato la struct non posso esser selezionato e quindi incremento e riinvio 
		  /*if(app.steps==0 && app.Ref==this.getReference()) {
		    	app.steps++;
		    	send(m.getSender(),app);
		    	return null;
		    }*/
		  //se NON sono io ad aver inviato la struct per primo allora vado bene come cella 2 (punto 6.b)
		  //else if(app.steps==0 && app.Ref!=this.getReference())
		    //{
			  //6.c
			  //se sono di tipo empty posso ometterlo perchè sono una cella vuota
			  // mi trovo nello stesso stato della cella di partenza (!=Empty di sicuro)
		      //o sono circondato da vicini che si trovano nello stesso mio stato?
			  
			  
			  if( this.getClass().getName().equals(app.name) || this.sum1==this.getNeighborhood().size())
			  //if( this.getClass().getName().equals(app.name) || this.sum1==8) -> non è detto che abbia proprio 8 vicini
			  {
				  Initiator.sem=1; //concludo la transazione
				  return null;
			  }
			  else {
				  //passo e
				  //cella selezionata NON empty -> State_1
				  //calcolo il guadagno combinato g2
				  
				  int g2=0;
				  
				  //osservo in quale stato si trova la cella di partenza che mi ha inviato la struct
		    	  if(app.name.equals(State_2.class.getName())) {
		    		  g2=(this.sum2+app.sum1)-(this.sum1+app.sum2);
		    		    //g2 = this.sum2-app.sum1;
		    		}
		    	  else if(app.name.equals(State_3.class.getName())) {
		    		  g2=(this.sum3+app.sum1)-(this.sum1+app.sum3);
		    		    //g2 = this.sum3-app.sum1;
		    		}
		    	  else if(app.name.equals(State_4.class.getName())) {
		    		  g2=(this.sum4+app.sum1)-(this.sum1+app.sum4);
		    		    //g2 = this.sum4-app.sum1;
		    		}
		    	  else if(app.name.equals(State_5.class.getName())) {
		    		  g2=(this.sum5+app.sum1)-(this.sum1+app.sum5);
		    		  //g2 = this.sum5-app.sum1;
		    		}
		    	  else if(app.name.equals(State_6.class.getName())) {
		    		  g2=(this.sum6+app.sum1)-(this.sum1+app.sum6);
		    		}
		    	  else if(app.name.equals(State_7.class.getName())) {
		    		  g2=(this.sum7+app.sum1)-(this.sum1+app.sum7);
		    		}
		    	  
		    	  //una cella "Empty" non può iniziare uno scambio
		    	  
		    	  //else if(app.name.equals(Empty.class.getName())) {
		    		  //g2=(this.sum8+app.sum1)-(this.sum1+app.sum8);
		    	  //}
		    	  
				  if(g2>=0) {
					  //le celle si scambiano di posizione -> swapping
					  
					  //quindi invio la stringa col nome della mia classe al primo che ha inviato la struct e setto il mio nuovo stato
					  
					  
					  send(app.Ref,this.getClass().getName());
		    			
		    			
					  //siccome sarei giù uscito dall'handler precedentemente (condizione 6.c) -> stesso stato delle due celle
					  
					  //if(app.name.equals(State_1.class.getName())) {
		    				//return new State_1(this);
			    			//this.newState=Stati.State_1;
			    		//}
			    		if(app.name.equals(State_2.class.getName())) {
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
				  else if(g2==-1 || g2==-2) {
					//le celle si scambiano di posizione ma con una probabilità 1/m
					  	Random rand2 =new Random();
		    			double val2 =rand2.nextDouble();
		    			if(val2<(double)1/Initiator.m) {
		    				
		    				send(app.Ref,this.getClass().getName());
		    				
		    				//if(app.name.equals(State_1.class.getName())) {//rimovibile
			    				//return new State_1(this);
				    			//this.newState=Stati.State_1;
				    		//}
				    		if(app.name.equals(State_2.class.getName())) {
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

			  }//fine scambio
			  Initiator.sem=1;
		
	  
		//se steps è positivo devo solo reinviare la struct ad uno dei miei vicini scelto a caso decrementando però steps
		/*else if(app.steps>0){
		
			app.steps--;
			Random rand=new Random();
			int val=rand.nextInt(getNeighborhood2().size());
			    
			send(this.getNeighborhood2().get(val),app);
		    }*/
			  
		return null;
		  };

	  c.define(STRUCT, h);
	  
  
	  
	  h = (m) -> {//al posto di utilizzare il metodo update()
		  
		  	//aggiorno i contatori in base al nuovo vicinato dell'istanza
		   
			
		    //se soddisfa la prima condizione e non c'è nessuno scambio in corso allora entro e lo inizio
		    if(!(this.sum1==getNeighborhood().size() || this.sum2==getNeighborhood().size() || 
					   this.sum3==getNeighborhood().size() || this.sum4==getNeighborhood().size() ||
					   this.sum5==getNeighborhood().size() || this.sum6==getNeighborhood().size() ||
					   this.sum7==getNeighborhood().size() || this.sum8==getNeighborhood().size()) && Initiator.sem==1)
		    {
		    	
		    		//indico alle altre istanze che non possono proseguire siccome è già stata scelta questa cella (istanza)
				  		Initiator.sem=0;
				  		
					  //compilo la struct da inviare
					  
					  this.contentMessage.name=this.getClass().getName();
					  this.contentMessage.Ref=this.getReference();
					  //this.contentMessage.steps=Initiator.n-1;
					  this.contentMessage.sum1=this.sum1;
					  this.contentMessage.sum2=this.sum2;
					  this.contentMessage.sum3=this.sum3;
					  this.contentMessage.sum4=this.sum4;
					  this.contentMessage.sum5=this.sum5;
					  this.contentMessage.sum6=this.sum6;
					  this.contentMessage.sum7=this.sum7;
					  this.contentMessage.sum8=this.sum8;
					  				  
					  //invio la struct ad un vicino scelto a random nel vicinato di Von Neumann di raggio n
					  //vedere funzione setNeighbors2 nella classe "StandaloneBuilder" -> nota: non può essere selezionata la cella stessa quindi non si
					  //esegue alcun controllo all'interno del message handler attivato dalla seconda cella selezionata
					  
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
		
    
	    /*if(!(this.sum1==getNeighborhood().size() || this.sum2==getNeighborhood().size() || 
				   this.sum3==getNeighborhood().size() || this.sum4==getNeighborhood().size() ||
				   this.sum5==getNeighborhood().size() || this.sum6==getNeighborhood().size() ||
				   this.sum7==getNeighborhood().size() || this.sum8==getNeighborhood().size()))
	    {
	    	
	    	
		do {
		
				if(Initiator.sem==1) 
		  		{
				  System.out.println("SONO ENTRATO");
			  		//this.count=1;
			  		Initiator.sem=0;
				  //compilo la struct da inviare
			  		this.entrato=1;
				  
				  this.contentMessage.name=this.getClass().getName();
				  this.contentMessage.Ref=this.getReference();
				  //this.contentMessage.steps=Initiator.n-1;
				  this.contentMessage.sum1=this.sum1;
				  this.contentMessage.sum2=this.sum2;
				  this.contentMessage.sum3=this.sum3;
				  this.contentMessage.sum4=this.sum4;
				  this.contentMessage.sum5=this.sum5;
				  this.contentMessage.sum6=this.sum6;
				  this.contentMessage.sum7=this.sum7;
				  this.contentMessage.sum8=this.sum8;
				  				  
				  //invio la struct ad un vicino a caso tra N-S-E-W
				  
				  Random rand=new Random();
				  int val=rand.nextInt(this.getNeighborhood2().size());
				  send(this.getNeighborhood2().get(val), this.contentMessage);
				  //this.count=0;
				  //return new State_1(this);

			  }
		
			}while(this.entrato==0);
		
	    }*/
		  	return null;
		  	
	}
	
	
		
}
