package Viral;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import javax.swing.*; 
import java.awt.event.*;
import java.awt.color.*;
//import javafx.scene.paint.Color;
import Viral.Initiator;
import java.util.Random;
//import javafx.scene.paint.Color;
import it.unipr.sowide.actodes.util.gui.AutomataViewer;
import it.unipr.sowide.actodes.util.gui.CellShape;
import it.unipr.sowide.actodes.ca.cell.Cell;
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


public final class Fully_Infected extends Cell<String>{
	
	private static final long serialVersionUID = 1L;
	
	private static final MessagePattern PATTERN_INT = MessagePattern.contentPattern(new IsInstance(Integer.class));
	
	//definizione del valore condiviso da tutte le istanze della classe
	public static final int value=1;
	//lista associata a ciascuna istanza
	private List<Reference> ref;
	

public Fully_Infected(final int x, final int y, final List<Reference> n)
{
	    super(x, y, n);
	    this.ref=new ArrayList<Reference>();	    
}


public Fully_Infected(final Cell<String> c)
{
  super(c);
  this.ref=new ArrayList<Reference>();
}


/** {@inheritDoc} **/
@Override
public void cases(final CaseFactory c)
{
  super.cases(c);

  MessageHandler h = (m) -> {
    getNeighborhood().forEach((i) -> send(i, value));

    return null;
  };

  c.define(START, h);

  h = (m) -> {
	  
	  if((int)m.getContent()==Healthy.value)
	  {
		  //aggiungo alla lista la reference del vicino "Healthy"
		  this.ref.add((Reference)m.getSender());
	  }
 
	  else if((int)m.getContent()!=Empty.value && (int)m.getContent()!=Fully_Infected.value)
		  	
		  	//aggiungo alla lista la reference del vicino "Infected"
		  	this.ref.add((Reference)m.getSender());
		  
	  
	  System.out.println("Contenuto del messaggio: "+(int)m.getContent());
	  
    return null;
  };

  
  c.define(PATTERN_INT, h);
  
  
  h = (m) -> {
					Random rand = new Random();
					if (rand.nextDouble() < Initiator.k1/100)
					{
						//invio ad ogni mio vicino che non è nel mio stato la mia istanza
						for(int i=0;i<this.ref.size();i++)
						{
							send(this.ref.get(i), this);
						}	
					}
					//sparisco (regola (e) )
				return new Empty(this);
				
  };
 
  c.define(CYCLE, h);

}


/** {@inheritDoc} **/
@Override
public Behavior update()

{ 
	  //pulisco la lista di reference
	  this.ref.clear();
	  //invio il valore a tutti i vicini della cella
	  getNeighborhood().forEach((i) -> send(i, value));

	  return null;
	
}
	
	
	
	
	

}
