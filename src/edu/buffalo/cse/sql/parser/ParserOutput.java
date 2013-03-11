package edu.buffalo.cse.sql.parser;

import java.util.Map;

import edu.buffalo.cse.sql.Schema;
import edu.buffalo.cse.sql.plan.PlanNode;

public class ParserOutput {

public Map<String, Schema.TableFromFile> tables;
public PlanNode q;

public ParserOutput(Map<String, Schema.TableFromFile> tables,PlanNode q)
{
	this.tables=tables;
	this.q=q;
	}

}
