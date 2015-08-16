package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.enums.OrderState;

public class Transition {
	private OrderState startState;
	private OrderState endState;
	public Transition(OrderState startState, OrderState endState) {
		super();
		this.startState = startState;
		this.endState = endState;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endState == null) ? 0 : endState.hashCode());
		result = prime * result
				+ ((startState == null) ? 0 : startState.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transition other = (Transition) obj;
		if (endState != other.endState)
			return false;
		if (startState != other.startState)
			return false;
		return true;
	}
	
	
}
