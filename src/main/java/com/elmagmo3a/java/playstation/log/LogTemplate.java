package com.elmagmo3a.java.playstation.log;

public interface LogTemplate {
	String CREATE_X_START = "creating: {} start";
	String CREATE_X_START_WITH = "creating: {} start with {}";
	String CREATE_X_START_WITH1 = "creating: {} start with {} = {}";
	String CREATE_X_START_WITH2 = "creating: {} start with {} = {}  and  {} = {} ";

	String CREATE_X_END = "creating: {} ended";
	String CREATE_X_END_WITH = "creating: {} ended with {}";
	String CREATE_X_END_WITH1 = "creating: {} ended with {} = {}";
	String CREATE_X_END_WITH2 = "creating: {} ended with {} = {}  and  {} = {} ";

	String DELETE_X_START = "creating: {} start";
	String DELETE_X_START_WITH = "creating: {} start with {}";
	String DELETE_X_START_WITH1 = "creating: {} start with {} = {}";
	String DELETE_X_START_WITH2 = "creating: {} start with {} = {}  and  {} = {} ";

	String DELETE_X_END = "creating: {} ended";
	String DELETE_X_END_WITH = "creating: {} ended with {}";
	String DELETE_X_END_WITH1 = "creating: {} ended with {} = {}";
	String DELETE_X_END_WITH2 = "creating: {} ended with {} = {}  and  {} = {} ";

	String GET_ALL_X_START = "start getting all {}";

	String GET_ALL_X_END_WITH = "getting all {} end with {}";

}
