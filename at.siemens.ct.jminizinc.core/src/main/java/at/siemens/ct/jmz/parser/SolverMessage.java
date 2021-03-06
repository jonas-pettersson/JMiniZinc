/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.parser;

/**
 * Represents a message from MiniZinc indicating whether the search for solutions was successful, complete, ...
 * 
 * @author Copyright Siemens AG, 2016
 */
public enum SolverMessage {

  COMPLETE("=========="),
  UNBOUNDED("=====UNBOUNDED====="),
  UNSATISFIABLE("=====UNSATISFIABLE====="),
  UNKNOWN("=====UNKNOWN=====");

  private String message;

  private SolverMessage(String message) {
    this.message = message;
  }

  /**
   * Gets the message string that is written by MiniZinc in its default settings.
   */
  public String getMessage() {
    return message;
  }

  public static SolverMessage fromAllOutput(String solverOutput) {
    if (solverOutput == null) {
      return UNKNOWN;
    }
    for (SolverMessage solverMessage : values()) {
      if (solverOutput.endsWith(solverMessage.message)) {
        return solverMessage;
      }
    }
    return null;
  }

  public static SolverMessage fromLastLine(String lastLine) {
    for (SolverMessage solverMessage : values()) {
      if (lastLine.equals(solverMessage.message)) {
        return solverMessage;
      }
    }
    return null;
  }

}
