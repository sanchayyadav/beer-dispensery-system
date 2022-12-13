package com.beer.dispenser.apis.constants;

/**
* Interface containing all the error constant variables and their values and on the base of the value able 
* to fetch custom error messages from error_messages.properties files.
* 
* @author   Sanchay Yadav
* @since	28th November 2022
*
*/
public interface ErrorMessageConstants {

	String ERROR_UNEXPECTED_API = "error.unexpected.api";
	
	String ERROR_DISPENSER_ALREADY_OPEN_OR_CLOSE = "error.dispenser.already.open.or.close";
	
	String ERROR_DISPENSER_DOES_NOT_EXISTS = "error.dispenser.does.not.exists";

	String ERROR_DISPENSER_DOES_NOT_EXISTS_WITH_ID = "error.dispenser.does.not.exists.with-id";
	
	String ERROR_DISPENSER_NOT_OPEN_YET = "error.dispenser.not.opened.yet";

	String ERROR_DISPENSER_SAME_AS_PREVOUS_STATUS = "error.dispenser.same.status";
	 
	String ERROR_DISPENSER_CLOSING_TIME_LESS_THAN_OPENING_TIME = "error.dispenser.wrong.time";
	
	String ERROR_INVALID_USERNAME_OR_PASSWORD = "error.invalid.credentials";
}
