package reseauSimple.producteur;

import reseauSimple.global.AbstractAgent;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProducteurBehaviourMsgListenerDepartage extends CyclicBehaviour
{
	private static final long serialVersionUID = 1L;
	
	public ProducteurBehaviourMsgListenerDepartage(Agent a)
	{
		super(a);
	}
	
	@Override
	public void action()
	{
		// Reçoit les messages suivant et ecarte les autres
		MessageTemplate mt = MessageTemplate.or(MessageTemplate.MatchPerformative(AbstractAgent.TRANSPORTEUR_ENVOI_ARGENT), MessageTemplate.MatchPerformative(AbstractAgent.PRODUCTEUR_AMENDE)); 
		ACLMessage msg = myAgent.receive(mt);
		
		/*
		 * Si on recoit une amende, ne doit jamais arriver voir FactureClient avec le while, on la paye
		 */
		if(msg != null)
		{
			switch(msg.getPerformative())
			{
				case AbstractAgent.TRANSPORTEUR_ENVOI_ARGENT :
					((ProducteurAgent) myAgent).setArgentFournisseur(((ProducteurAgent) myAgent).getArgentFournisseur() + Integer.parseInt(msg.getContent()));
					break;
				
				case AbstractAgent.PRODUCTEUR_AMENDE :
					((ProducteurAgent) myAgent).setArgentFournisseur(((ProducteurAgent) myAgent).getArgentFournisseur() - Integer.parseInt(msg.getContent()));
					break;
			}
		}
		else
			block();
	}
	
}
