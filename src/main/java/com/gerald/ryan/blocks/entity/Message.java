package com.gerald.ryan.blocks.entity;

/**
 * Not used. Considered using as coin (blockchain) - making factory on top of instance making factory within blockchain class.
 * Open for completion 
 * @author User
 *
 */
public class Message {
	String message;
	String channel;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
