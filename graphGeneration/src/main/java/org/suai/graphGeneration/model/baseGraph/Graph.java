package org.suai.graphGeneration.model.baseGraph;

// it's the parent Graph class for every class from graphGenerated and graphCalculation package
public abstract class Graph {

    protected StringBuilder trackLogger;

    public Graph() {
        this.trackLogger = new StringBuilder();
    }

    public StringBuilder getTrackLogger() {
        return trackLogger;
    }

    public void setTrackLogger(StringBuilder trackLogger) {
        this.trackLogger = trackLogger;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "trackLogger=" + trackLogger +
                '}';
    }
}
