package reseauSimple.horloge;

import reseauSimple.global.AbstractAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class HorlogeBehaviourListener extends CyclicBehaviour
{
	private static final long serialVersionUID = 1L;
	
	private int phaseActuelle = AbstractAgent.HORLOGE_PHASE_NEGOCIATION;
	private int nbReponseRecu = 0;
	
	@Override
	public void action()
	{
		// Reçoit les messages envoyés à l'horloge
		ACLMessage msg = myAgent.receive();
		
		if(msg != null)
		{
			if(msg.getPerformative())
			switch(msg.getPerformative())
			{
				case AbstractAgent.HORLOGE_PHASE_NEGOCIATION :
					
					if(nbReponseRecu < ((AbstractAgent) myAgent).getAnnuairePerso().length)
					{
						nbReponseRecu++;
					}
					else
					{
						myAgent.addBehaviour(new HorlogeBehaviourTalker(myAgent, AbstractAgent.HORLOGE_PHASE_FACTURATION));
						nbReponseRecu = 0;
					}
					break;
				
				case AbstractAgent.HORLOGE_PHASE_FACTURATION :
					
					if(nbReponseRecu < ((AbstractAgent) myAgent).getAnnuairePerso().length)
					{
						nbReponseRecu++;
					}
					else
					{
						myAgent.addBehaviour(new HorlogeBehaviourTalker(myAgent, AbstractAgent.HORLOGE_PHASE_DEPARTAGE));
						nbReponseRecu = 0;
					}
					break;
				
				case AbstractAgent.HORLOGE_PHASE_DEPARTAGE :
					
					if(nbReponseRecu < ((AbstractAgent) myAgent).getAnnuairePerso().length)
					{
						nbReponseRecu++;
					}
					else
					{
						myAgent.addBehaviour(new HorlogeBehaviourTalker(myAgent, AbstractAgent.HORLOGE_PHASE_NEGOCIATION));
						nbReponseRecu = 0;
					}
					break;
					
				default :
					System.err.println("Message reçu inconnu.");
					break;
			}
		}
		else
			block();
	}
	
}