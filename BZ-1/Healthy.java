package Belousov_Zhabotinsky_Reaction;


import java.util.ArrayList;
import java.util.List;
import java.awt.*;

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


public final class Healthy extends Cell<String>{
	
	private static final long serialVersionUID = 1L;
	
	private static final MessagePattern PATTERN_INT = MessagePattern.contentPattern(new IsInstance(Integer.class));
	
	//fisso una variabile intera che assume il valore 0 come costante per tutte le istanze di questa classe
	public static final int value;
	
	//definisco le variabili private
	private int ill;
	private int infected;
	private int healthy;
	private int sum;
	
	
static {
		//avrei potuto inizializzare la variabile a 0 direttamente nella dichiarazione della variabile
		value=0;
		
}	
	

public Healthy(final int x, final int y, final List<Reference> n)
{
	    super(x, y, n);
	    //inizializzazione variabili private per ogni cella (istanza)
	    this.ill=0;
	    this.infected=0;
	    this.healthy=0;
	    this.sum=0;
	    
}


public Healthy(final Cell<String> c)
{
  super(c);
  //inizializzazione che avviene al cambio di classe per un'istanza (change behavior)
  this.ill=0;
  this.infected=0;
  this.healthy=0;
  this.sum=0;
}


/** {@inheritDoc} **/
@Override
public void cases(final CaseFactory c)
{
  super.cases(c);

  MessageHandler h = (m) -> {
	//invio il valore intero che caratterizza la classe a tutti i vicini della cella
    getNeighborhood().forEach((i) -> send(i, value));

    return null;
  };

  c.define(START, h);

  h = (m) -> {
	  this.sum+=(int)m.getContent();
	  
	  //salvo nelle variabili private il numero di vicini che appartengono ad ogni classe 
	  if((int)m.getContent()==Ill.n)
		  this.ill++;
	  
	  else if((int)m.getContent()==value) this.healthy++;
	  else this.infected++;
	  
	  System.out.println("Contenuto del messaggio: "+(int)m.getContent());
	  
    return null;
  };

  
  c.define(PATTERN_INT, h);
  
  
  //viene eseguito indefinitivamente
  h = (m) -> {
	  //creo una variabile d'appoggio che conterrà il nuovo stato
	  int valore;
	  //calcolo il nuovo stato che dovrà assumere la cella
		valore=(int)(this.infected/Initiator.k1)+(int)(this.ill/Initiator.k2);
		
		if(valore==0)
		{
			return new Healthy(this);
			//avrei potuto inserire al suo posto il seguente codice:
			
			/*this.ill=0;
			  this.infected=0;
			  this.healthy=0;
			  this.sum=0;
			  getNeighborhood().forEach((i) -> send(i, value));
			  return null;*/
		}
		
		
		if(valore==Ill.n)
		{
			
			return new Ill(this);
		}
		
		return new Infected(this, valore);
		 
  };

  
  c.define(CYCLE, h); 

}


/** {@inheritDoc} **/
@Override
public Behavior update()
{ 
	
	  this.ill=0;
	  this.infected=0;
	  this.healthy=0;
	  this.sum=0;
	  getNeighborhood().forEach((i) -> send(i, value));
	  return null;
	
}
	
}
