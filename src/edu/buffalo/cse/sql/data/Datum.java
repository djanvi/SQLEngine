
package edu.buffalo.cse.sql.data;

import java.util.Stack;

import edu.buffalo.cse.sql.SqlException;
import edu.buffalo.cse.sql.Schema;

public abstract class Datum {  
  public abstract String toString();
  public abstract boolean toBool() throws CastError;
  public abstract int toInt() throws CastError;
  public abstract float toFloat() throws CastError;
  public abstract Schema.Type getType();
  public abstract boolean equalsImpl(Datum d) throws CastError;
  public boolean equals(Datum d) { 
    try { return d.getType() == getType() && equalsImpl(d); }
    catch (CastError e) { return false; }
  }
  public boolean equals(Object d) { 
    try { return equals((Datum)d); }
    catch (ClassCastException e) { return false; }
  }
  
  public static class CastError extends SqlException {
    String from, to;
    public CastError(String from, String to) {
      super("Cast Error " + from + " -> " + to);
      this.from = from; this.to = to;
    }
  }
  
  public static class Int extends Datum {
    int i;
    public Int(int i) { this.i = i; }
    public String toString() { return Integer.toString(i); }
    public boolean toBool() throws CastError 
          { throw new CastError("Int", "Bool"); }
    public int toInt() { return i; }
    public float toFloat() { return (float)i; }
    public Schema.Type getType() { return Schema.Type.INT; }
    public boolean equalsImpl(Datum d) throws CastError 
      { return d.toInt() == i; }
    public int hashCode() { return i; }
  }
  
  public static class Flt extends Datum {
    float f;
    public Flt(float f) { this.f = f; }
    public Flt(double f) { this.f = (float)f; }
    public String toString() { return Float.toString(f); }
    public boolean toBool() throws CastError 
          { throw new CastError("Float", "Bool"); }
    public int toInt() throws CastError { throw new CastError("Float", "Int"); }
    public float toFloat() { return f; }
    public Schema.Type getType() { return Schema.Type.FLOAT; }
    public boolean equalsImpl(Datum d) throws CastError 
      { return d.toFloat() == f; }
    public int hashCode() { return (int)f; }
  }
  
  public static class Str extends Datum {
    String s;
    public Str(String s) { this.s = s; }
    public String toString() { return "'" + s + "'"; }
    public boolean toBool() throws CastError 
          { throw new CastError("String", "Bool"); }
    public int toInt() throws CastError
         { throw new CastError("String", "Int"); }
    public float toFloat() throws CastError
         { throw new CastError("String", "Float"); }
    public Schema.Type getType() { return Schema.Type.STRING; }
    public boolean equalsImpl(Datum d) throws CastError 
      { return d.toString().equals(toString()); }
    public int hashCode() { return s.hashCode(); }
  }
  
  public static class Bool extends Datum {
    boolean b;
    
    public static Bool TRUE = new Bool(true);
    public static Bool FALSE = new Bool(false);
    
    public Bool(boolean b) { this.b = b; }
    public String toString() { return b ? "true" : "false"; }
    public boolean toBool() { return b; }
    public int toInt() throws CastError
         { throw new CastError("Bool", "Int"); }
    public float toFloat() throws CastError
         { throw new CastError("Bool", "Float"); }
    public Schema.Type getType() { return Schema.Type.BOOL; }
    public boolean equalsImpl(Datum d) throws CastError 
      { return d.toBool() == b; }
    public int hashCode() { return b ? 1 : 0; }
  }
}