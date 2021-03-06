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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, Object> getInput() {
		return input;
	}

	public void setInput(HashMap<String, Object> input) {
		this.input = input;
	}

	public HashMap<String, Object> getOutput() {
		return output;
	}

	public void setOutput(HashMap<String, Object> output) {
		this.output = output;
	}

}
