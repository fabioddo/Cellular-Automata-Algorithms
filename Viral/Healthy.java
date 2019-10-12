package Viral;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import javax.swing.*;

import Viral.Initiator;

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
	
	private static final MessagePattern PATTERN_FULLY = MessagePattern.contentPattern(new IsInstance(Fully_Infected.class));
	
	//valore unico per tutte le istanze della classe
	public static final int value=Initiator.q;
	
	private int empty;
	private int infected;
	private int healthy;
	private int fully_infected;
	//lista di reference che conterrà le reference dei suoi vicini "Empty"
	private List<Reference> ref;
	

public Healthy(final int x, final int y, final List<Reference> n)
{
	    super(x, y, n);
	    
	    this.empty=0;
	    this.infected=0;
	    this.healthy=0;
	    this.fully_infected=0;
	    //inizializzo anche l'array list
	    this.ref=new ArrayList<Reference>();
}


public Healthy(final Cell<String> c)
{
  super(c);
  
  this.empty=0;
  this.infected=0;
  this.healthy=0;
  this.fully_infected=0;
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
	  
	  //conto i vicini differenziandoli per classi
	  if((int)m.getContent()==Healthy.value)
		  this.healthy++;
	  
	  else if((int)m.getContent()==Empty.value)
		  {
		  	this.empty++;
		  	//inserisco la reference dell'istanza "Empty" che mi ha inviato il valore intero nella lista
		  	this.ref.add((Reference)m.getSender());
		  }
	  else if((int)m.getContent()==Fully_Infected.value) this.fully_infected++;
	  else this.infected++;
	  
	  System.out.println("Contenuto del messaggio: "+(int)m.getContent());
	  
    return null;
  };

  
  c.define(PATTERN_INT, h);
  
  
  h = (m) -> {
	  //cambia il behavior
	  return new Infected(this, Initiator.q-1);
  };

  
  c.define(PATTERN_FULLY, h);

  h = (m) -> {
	  
	  Random rand = new Random();
	  //con una certa probabilità esegue la regola
		if (rand.nextDouble() < Initiator.k2/100000) {
			return new Infected(this, Initiator.q-1);
		}
		else {
			//se tale cella "Healthy" ha dei vicini che si trovano nello stato "Empty"
			if(this.empty>0)//oppure this.ref.size()>0
			{
				//con una certa probabilità viene eseguito il codice successivo
				if (rand.nextDouble() < Initiator.k3/100) {
					
					//prendo a random l'indice di una reference nella lista
					Random rand1=new Random();
					int val=rand1.nextInt(this.ref.size());
					
					//invio ad un vicino "Empty" scelto in modo casuale l'istanza "Healthy"
					send(this.ref.get(val), this);
					
					return new Healthy(this);//re-istanzio la stessa cella con l'istanza "figlia"
			}
	
		}
	}
	    
	  this.ref.clear();
	  this.empty=0;
	  this.infected=0;
	  this.healthy=0;
	  this.fully_infected=0;
	
	  getNeighborhood().forEach((i) -> send(i, value));

	  return null;
  };

  
  c.define(CYCLE, h);

}


/** {@inheritDoc} **/
@Override
public Behavior update()

{ 				

	  this.empty=0;
	  this.infected=0;
	  this.healthy=0;
	  this.fully_infected=0;
	  //svuoto la lista
	  this.ref.clear();
	
	  getNeighborhood().forEach((i) -> send(i, value));

	  return null;
	
}
	

}
