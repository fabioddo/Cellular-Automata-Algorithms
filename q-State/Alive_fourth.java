package qStateLife;


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



public final class Alive_fourth extends Cell<String>{
	
	private static final long serialVersionUID = 1L;
		
	private static final MessagePattern PATTERN_INT = MessagePattern.contentPattern(new IsInstance(Integer.class));
	
	private int value;
	private int sum;
	private int count;


	
public Alive_fourth(final int x, final int y, final List<Reference> n)
{
	    super(x, y, n);
	    
	    Random rand=new Random();
	    this.value=rand.nextInt(Initiator.q/5)+3*(Initiator.q/5)+1;
	    this.sum=0;
		this.count=0;
}

	


public Alive_fourth(final Cell<String> c, int valore)
{
  super(c);
  
  this.sum=0;
  this.count=0;
  this.value = valore;
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

	    h = (m) -> {
	    	if(this.count<this.getNeighborhood().size()) {
	    		   this.sum+=(int)m.getContent();
	      	}
	    	this.count++;
	    	return null;
	    };

	    c.define(PATTERN_INT, h);	    
}

/** {@inheritDoc} **/
@Override
public Behavior update()
{ 
	int val=this.value;
	if(val>Initiator.q/2)
	{
		if(Initiator.k1<=this.sum && this.sum<=Initiator.k2)
		{
			if(val<Initiator.q)
				val++;
			
			
		}
		else if(val>1)
		{
			val--;
		}
	
	}else {
		
		if(Initiator.k3<=this.sum && this.sum<=Initiator.k4 ) {
			if(val<Initiator.q)
				val++;
		
		
		}else if(val>1)	
		{
			val--;
		}
		
	}
	

	if(val>2*Initiator.q/5 && val<=3*Initiator.q/5)
	{
		
		return new Alive_third(this, val);	
		
	}
	else if(val>3*Initiator.q/5 && val<=4*Initiator.q/5)
	{
		this.sum=0;
	this.count=0;
	this.value=val;
	getNeighborhood().forEach((i) -> send(i, this.value));
	return null;
	}
	else return new Alive_fifth(this, val);	
	
}

}
