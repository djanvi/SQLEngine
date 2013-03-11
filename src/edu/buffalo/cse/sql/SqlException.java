
package edu.buffalo.cse.sql;

public class SqlException extends Exception {

  public SqlException(String err) { super("Sql Error: "+err); }

}