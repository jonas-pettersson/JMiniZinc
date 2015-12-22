package at.siemens.ct.jmz.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.elements.IntVar;
import at.siemens.ct.jmz.writer.IModelWriter;

/**
 * Runs a MiniZinc process and communicates with it.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class Executor implements IExecutor {

  private static final String DEFAULT_EXE_PATH = "minizinc";

  private IModelWriter modelWriter;
  private String pathToExecutable = DEFAULT_EXE_PATH;
  private Process runningProcess;
  private File temporaryModelFile;
  private String lastSolverOutput;

  public Executor(IModelWriter modelWriter) {
    super();
    this.modelWriter = modelWriter;
  }

  @Override
  public String getPathToExecutable() {
    return pathToExecutable;
  }

  @Override
  public void setPathToExecutable(String pathToExecutable) {
    this.pathToExecutable = pathToExecutable;
  }

  @Override
  public Process startProcess() throws IOException {
    temporaryModelFile = modelWriter.toTempFile();
    String command = pathToExecutable; // TODO: add more parameters, abstract parameter adding
    // TODO: use java.lang.ProcessBuilder.ProcessBuilder(List<String>)
    command += " " + temporaryModelFile.getAbsolutePath();
    runningProcess = Runtime.getRuntime().exec(command);
    return runningProcess;
  }

  @Override
  public void waitForSolution() {
    if (runningProcess == null) {
      throw new IllegalStateException("No running process.");
    }

    BufferedReader reader = new BufferedReader(
        new InputStreamReader(runningProcess.getInputStream()));

    try {
      runningProcess.waitFor();
    } catch (InterruptedException e) {
    }

    lastSolverOutput = reader.lines().collect(Collectors.joining(System.lineSeparator()));

    removeCurrentModelFile();
  }

  @Override
  public String getLastSolverOutput() {
    return lastSolverOutput;
  }

  private void removeCurrentModelFile() {
    if (temporaryModelFile != null) {
      temporaryModelFile.delete();
      temporaryModelFile = null;
    }
  }

  @Override
  public int getSolution(IntVar i) {
    throw new UnsupportedOperationException(); // TODO: implement
  }

}