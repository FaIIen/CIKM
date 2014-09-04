package GraphChi;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.apache.pig.parser.QueryParser.foreach_clause_complex_return;

import Tool.FileOp;
import edu.cmu.graphchi.ChiFilenames;
import edu.cmu.graphchi.ChiLogger;
import edu.cmu.graphchi.ChiVertex;
import edu.cmu.graphchi.EdgeDirection;
import edu.cmu.graphchi.preprocessing.FastSharder;
import edu.cmu.graphchi.preprocessing.VertexIdTranslate;
import edu.cmu.graphchi.util.IdCount;
import edu.cmu.graphchi.walks.DrunkardContext;
import edu.cmu.graphchi.walks.DrunkardJob;
import edu.cmu.graphchi.walks.DrunkardMobEngine;
import edu.cmu.graphchi.walks.WalkUpdateFunction;
import edu.cmu.graphchi.walks.distributions.DrunkardCompanion;
import edu.cmu.graphchi.walks.distributions.RemoteDrunkardCompanion;


public class PersonalizedPageRank implements WalkUpdateFunction<Float, Float> {

    private static Logger logger = ChiLogger.getLogger("personalized-pagerank");
    private DrunkardMobEngine<Float, Float>  drunkardMobEngine;
    private String baseFilename;
    private int firstSource;
    private int numSources;
    private int numWalksPerSource;
    private String companionUrl;
    
    
    public PersonalizedPageRank(String companionUrl, String baseFilename, int nShards, int firstSource, int numSources, int walksPerSource) throws Exception{
        this.baseFilename = baseFilename;
        this.drunkardMobEngine = new DrunkardMobEngine<Float, Float>(baseFilename, nShards);
        this.companionUrl = companionUrl;
        this.firstSource = firstSource;
        this.numSources = numSources;
        this.numWalksPerSource = walksPerSource;
    }

    private void execute(int numIters) throws Exception {
        File graphFile = new File(baseFilename);

        /** Use local drunkard mob companion. You can also pass a remote reference
         *  by using Naming.lookup("rmi://my-companion")
         */
        RemoteDrunkardCompanion companion;
        if (companionUrl.equals("local")) {
            companion = new DrunkardCompanion(4, Runtime.getRuntime().maxMemory() / 3);
        }  else {
            companion = (RemoteDrunkardCompanion) Naming.lookup(companionUrl);
        }

        /* Configure walk sources. Note, GraphChi's internal ids are used. */
        DrunkardJob drunkardJob = this.drunkardMobEngine.addJob("personalizedPageRank",
                EdgeDirection.IN_AND_OUT_EDGES, this, companion);

        drunkardJob.configureSourceRangeInternalIds(firstSource, numSources, numWalksPerSource);
        drunkardMobEngine.setEdataConverter(new MyBytesToValueConverter());
        drunkardMobEngine.setVertexDataConverter(new MyBytesToValueConverter());
        drunkardMobEngine.run(numIters);

        /* Ask companion to dump the results to file */
        int nTop = 100;
        companion.outputDistributions(baseFilename + "_ppr_" + firstSource + "_"
                + (firstSource + numSources - 1) + ".top" + nTop, nTop);

        /* For debug */
        VertexIdTranslate vertexIdTranslate = this.drunkardMobEngine.getVertexIdTranslate();
        IdCount[] topForFirst = companion.getTop(firstSource, 10);
        outputResult("stResult.txt", topForFirst);

        /* If local, shutdown the companion */
        if (companion instanceof DrunkardCompanion) {
            ((DrunkardCompanion) companion).close();
        }
    }

    private void outputResult(String outFile,IdCount[] topForFirst) throws IOException{
    	if(topForFirst.length==0)
    		return;
    	VertexIdTranslate vertexIdTranslate = this.drunkardMobEngine.getVertexIdTranslate();
    	BufferedWriter bw=new BufferedWriter(new FileWriter(new File(FileOp.graphPath+outFile),true));
    	bw.write("Top visits from source vertex " + vertexIdTranslate.forward(firstSource) + " (internal id=" + firstSource + ")");
    	bw.newLine();
    	float total=0.0f;
    	for(IdCount idc : topForFirst) {
    		total+=idc.count;
    	}
    	for(IdCount idc : topForFirst) {
            bw.write(vertexIdTranslate.backward(idc.id) + ": " + idc.count/total);
            bw.newLine();
        }
    	bw.close();
    }
    /**
     * WalkUpdateFunction interface implementations
     */
    public void processWalksAtVertex(int[] walks,
                                     ChiVertex<Float, Float> vertex,
                                     DrunkardContext drunkardContext,
                                     Random randomGenerator) {
        int numWalks = walks.length;
        int numOutEdges = vertex.numOutEdges();
        if (numOutEdges >= 0) {
        	List<Float> tempList=new ArrayList<>();
        	for(int j=0;j<numOutEdges;j++)
        		tempList.add(vertex.outEdge(j).getValue());
        	Float[] tempArray=tempList.toArray(new Float[tempList.size()]);
            for(int i=0; i < numWalks; i++) {
                int walk = walks[i];
                int nextHop  = -1;
                boolean needTrack=false;
                if(numOutEdges ==0){
                	needTrack=true;
                	nextHop=vertex.getId();
                }else{
                	int nextVertext=percentRandom(tempArray.clone());
                	nextHop=vertex.getOutEdgeId(nextVertext);
                }
                drunkardContext.forwardWalkTo(walk, nextHop, needTrack);
            }
        }
    }

    private static int percentRandom(Float[] edgeCount){
    	Float[] percent = new Float[edgeCount.length];
    	Float total=0.0f;
    	for(int i=0;i<edgeCount.length;i++){
    		total+=edgeCount[i];
    	}
    	for(int i=0;i<edgeCount.length;i++){
    		percent[i]=edgeCount[i]/total;
    	}
    	double randomNumber=Math.random();
    	if(percent==null || percent.length==0)
    		throw new RuntimeException("Illegal percentage!");
    	for(int i=1;i<percent.length;i++){
    		percent[i]+=percent[i-1];
    	}
    	for(int i=0;i<percent.length;i++){
    		if(randomNumber<percent[i])
    			return i;
    	}
    	return -1;
    }

    @Override
    /**
     * Instruct drunkardMob not to track visits to this vertex's immediate out-neighbors.
     */
    public int[] getNotTrackedVertices(ChiVertex<Float, Float> vertex) {
        int[] notCounted = new int[1];
        return notCounted;
    }

    protected static FastSharder createSharder(String graphName, int numShards) throws IOException {
        return new FastSharder<Float, Float>(graphName, numShards, new MyVertexProcessor(), new MyEdgeProcessor(), new MyBytesToValueConverter(), new MyBytesToValueConverter());
    }

    public static void main(String[] args) throws Exception {
        try {
        	List<String> markQuery=FileOp.readList("train-data/countqt.txt");
            /**
             * Preprocess graph if needed
             */
            String baseFilename = FileOp.graphPath+"graph.txt";
            int nShards = 1;
            String fileType = "edgelist";

            /* Create shards */
            if (baseFilename.equals("pipein")) {     // Allow piping graph in
                FastSharder sharder = createSharder(baseFilename, nShards);
                sharder.shard(System.in, fileType);
            } else {
                FastSharder sharder = createSharder(baseFilename, nShards);
                if (!new File(ChiFilenames.getFilenameIntervals(baseFilename, nShards)).exists()) {
                    sharder.shard(new FileInputStream(new File(baseFilename)), fileType);
                } else {
                    logger.info("Found shards -- no need to pre-process");
                }
            }
 
            // Run
            int numSources = 1;
            int walksPerSource = 1000;
            int nIters = 10;
            String companionUrl = "local";
            for(String query:markQuery){
            	int firstSource=Integer.valueOf(query);
            	PersonalizedPageRank pp = new PersonalizedPageRank(companionUrl, baseFilename, nShards,
                        firstSource, numSources, walksPerSource);
                pp.execute(nIters);
            }
            

        } catch (Exception err) {
            err.printStackTrace();
        }
        System.exit(0);
    }
}
