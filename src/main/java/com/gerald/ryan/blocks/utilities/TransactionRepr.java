package com.gerald.ryan.blocks.utilities;

import java.util.HashMap;

import com.gerald.ryan.blocks.entity.Transaction;

public class TransactionRepr {
	
	String id;
	HashMap<String, Object> input;	
	HashMap<String, Object> output;

	public TransactionRepr(Transaction t) {
		this.id = t.getUuid();
		this.input = t.getInput();
		this.output = t.getOutput();
	}
	
}
