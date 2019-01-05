package model.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PrivateQuote {
  private static int hc = 0;
  private static PrivateQuote instance = null;

  public PrivateQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static PrivateQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new PrivateQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof PrivateQuote;
  }

  public String toString() {

    return "<Private>";
  }
}
