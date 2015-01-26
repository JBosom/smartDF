package reseauSimple.transporteur;

import java.util.TreeMap;

import reseauSimple.consommateur.ConsommateurAgent;
import reseauSimple.global.AbstractAgent;
import reseauSimple.global.GlobalBehaviourHorlogeTalker;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class TransporteurBehaviourMsgListenerAllPhases extends CyclicBehaviour {
	private static final long serialVersionUID = 1L;
	private int capaciteRestante = ((TransporteurAgent) myAgent).getCapaciteTransporteur();
	private int prixTransporteur = ((TransporteurAgent) myAgent).getPrixKWhTransporteur();
	private TreeMap<AID, Integer> demandeEnAttente = new TreeMap<AID, Integer>();
	
	public void termineTour(){
		capaciteRestante = ((TransporteurAgent) myAgent).getCapaciteTransporteur();
		prixTransporteur = ((TransporteurAgent) myAgent).getPrixKWhTransporteur();
	}

	@Override
	public void action() {
		// Recoit la quantite que veut transporter chaque producteur sur son reseau
		MessageTemplate mt = null;
		AID[] producteurpossible = ((AbstractAgent) myAgent).getAnnuairePerso();
		if (producteurpossible != null && producteurpossible.length != 0) {
			for (AID aid : producteurpossible) {
				if (mt == null)
					mt = MessageTemplate.MatchSender(aid);
				else
					mt = MessageTemplate.or(mt,
							MessageTemplate.MatchSender(aid));
			}
		}
		ACLMessage msg = myAgent.receive(mt);

		// TODO Traitement du message
		if (msg != null) {
			// 2 type de messages, celui pour connaitre le prix, celui pour facturer
			if (msg.getPerformative() == AbstractAgent.PRIX_TRANSPORTEUR_DEMANDE) {
				ACLMessage reply = msg.createReply();
				reply.setPerformative(AbstractAgent.PRIX_TRANSPORTEUR_REPONSE);
				reply.setContent(Integer.toString(prixTransporteur));
				myAgent.send(reply);
			}
			else if (msg.getPerformative() == AbstractAgent.CAPACITE_TRANSPORTEUR_DEMANDE){
				ACLMessage reply = msg.createReply();
				reply.setPerformative(AbstractAgent.CAPACITE_TRANSPORTEUR_REPONSE);
				reply.setContent(Integer.toString(capaciteRestante));
				myAgent.send(reply);
			}
			
			//regarder si c'est mon agent
			//si ca l'est pas attendre que mon agent demande la facturation et stocker la requete
			//une fois que j'ai la demande de mon agent je choisi une demande dans mes capacite 
			//ou faire la difference avec la capacite qu'il me reste
			//augmenter ou diminuer le prix
			else if (msg.getPerformative() == AbstractAgent.DEMANDE_FACTURATION){
				if(msg.getSender() == ((TransporteurAgent) myAgent).getFournisseurID()){
					capaciteRestante -= ((TransporteurAgent) myAgent).getCapaciteTransporteur();
					
					//recherche d'une demande qui corresponde a sa capacit� restante, et on lui facture
					//sinon contre proposition
					//sinon on le refuse
					//on augmente le prix si on a trop de demande
					//on diminue le prix si on a pas de demande en attente
				}
				//ajout du message � la pile des demandes en attendant de recevoir la demande de son producteur
				else{
					AID demandeAidTransport = msg.getSender();
					int demandePrixTransport = Integer.parseInt(msg.getContent());
					demandeEnAttente.put(demandeAidTransport, demandePrixTransport);
				}
			}
		}
				block();
	}
}