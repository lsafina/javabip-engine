package org.bip.engine.api;

import org.bip.api.BIPEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import akka.japi.Creator;

public class EngineFactory {
	private Logger logger = LoggerFactory.getLogger(EngineFactory.class);
	ActorSystem actorSystem;
	
	public EngineFactory(ActorSystem actorSystem) {
		this.actorSystem = actorSystem;		
	}
	
	public BIPEngine create(String id, final BIPEngine engine) {
		
		
		BIPEngine actor = TypedActor.get(actorSystem).typedActorOf(
				new TypedProps<BIPEngine>(BIPEngine.class,
						new Creator<BIPEngine>() {
							public BIPEngine create() {
								return engine;
							}
						}), id);
		System.out.println("engine actor " + actor);

		// TODO: make the DataCoordinatorImpl implement this function (after
		// refactoring the coordinators)
		// executor.setProxy(actor);
		
		return actor;
	}

	public boolean destroy(BIPEngine engine) {
		
		// TODO EXTENSION when it is possible to deregister a component from BIP engine make sure it happens here.
		// executor.engine().deregister();
		
		if (TypedActor.get(actorSystem).isTypedActor(engine)) {
			TypedActor.get(actorSystem).poisonPill(engine);
			return true;
		}
		else {
			return false;
		}

	}

}