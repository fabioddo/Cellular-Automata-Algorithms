package Viral;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import Viral.Initiator;
import javax.swing.*; 
import java.awt.event.*;
import java.awt.color.*;
//import javafx.scene.paint.Color;

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


public final class Infected extends Cell<String>{
	
	private static final long serialVersionUID = 1L;

	//message pattern che reagisce alle istanze di una determinata classe
	private static final MessagePattern PATTERN_FULLY = MessagePattern.contentPattern(new IsInstance(Fully_Infected.class));
	
	//valore dell'istanza
	public int value;
	

public Infected(final int x, final int y, final List<Reference> n)
{
	    super(x, y, n);
	    
	    //associo un valore casuale all'istanza posizionata
	    Random rand = new Random();
	    this.value=rand.nextInt(Initiator.q-2)+2;
	    
	    System.out.println("Infected cell random value is: "+this.value);
}


public Infected(final Cell<String> c, int valore)
{
  super(c);
  
  this.value=valore;
  
}


/** {@inheritDoc} **/
@Override
public void cases(final CaseFactory c)
{
  super.cases(c);

  MessageHandler h = (m) -> {
    getNeighborhood().forEach((i) -> send(i, this.value));

    return null;
  };

  c.define(START, h);

  //non necessito della conta del vicinato
  
  //si attiva quando mi arriva un'istanza della classe "Fully_Infected"
  h = (m) -> {
	  if(this.value>2)
	  return new Infected(this, this.value-1);
	  else
		  return new Fully_Infected(this);
  };

  
  c.define(PATTERN_FULLY, h);
  
  //codice eseguito ciclicamente
  h = (m) -> {
	 
	  this.value--;
	  if(this.value==1) return new Fully_Infected(this);
	  //altrimenti è ancora una cella infetta
	  getNeighborhood().forEach((i) -> send(i, this.value));
	  return null;
  };

  
  c.define(CYCLE, h);

}


/** {@inheritDoc} **/
@Override
public Behavior update()

{ 
	  getNeighborhood().forEach((i) -> send(i, this.value));
	return null;	
}
	

}
