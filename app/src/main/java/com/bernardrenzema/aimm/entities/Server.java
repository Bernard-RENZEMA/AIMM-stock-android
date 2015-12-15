package com.bernardrenzema.aimm.entities;

public class Server {

	//	public static final String		ADRESS = "http://aimm.bernardrenzema.com";
	public static final String		ADRESS = "http://aimm.herokuapp.com";
	public static final int			PORT = 80;

	public static final String		R_ROOT = ADRESS + ":" + PORT;
	public static final String		R_LOGIN = R_ROOT + "/login";
	public static final String		R_LOGOUT = R_ROOT + "/logout";

	/* USER */
	public static final String		R_USERS = R_ROOT + "/users";

	/* WORK */
	public static final String		R_WORKS = R_ROOT + "/works";
	public static final String		R_RUNNING_WORKS = R_ROOT + "/works/running";

	/* CLIENT */
	public static final String		R_CLIENTS = R_ROOT + "/clients";
	public static String			R_CLIENT_BY_ID(int id) {
		return R_CLIENTS + "/" + id;
	}

	/* STOCKMATERIAL */
	public static final String		R_STOCKMATERIALS = R_ROOT + "/stockmaterials";
	public static String			R_STOCKMATERIALS_BY_KEYWORDS(String query) {
		return R_STOCKMATERIALS + "/search?description=" + query;
	}
	public static String			R_STOCKMATERIALS_BY_BARCODE(String barcode) {
		return R_STOCKMATERIALS + "/barcode/" + barcode;
	}

	public static final String		R_STOCK_ENTRY = R_ROOT + "/stock/entry";
	public static final String		R_STOCK_EXIT = R_ROOT + "/stock/exit";

	/* WORKMATERIAL */
	public static final String		R_WORKMATERIALS = R_ROOT + "/workmaterials";
	public static String			R_WORKMATERIALS_BY_WORKID(int workId) {
		return R_WORKMATERIALS + "/work/" + workId;
	}

	/* ERRORS */
}
