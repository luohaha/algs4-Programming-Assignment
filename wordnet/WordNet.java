import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class WordNet {
	private SAP sap;
	private final Map<Integer, String> idToSynset;
	private final Map<String, Set<Integer>> nounToIds;
	 // constructor takes the name of the two input files
	   public WordNet(String synsets, String hypernyms) {
		   if (synsets == null ||hypernyms == null ) throw new NullPointerException();
		   
		   idToSynset = new HashMap<Integer, String>();
		   nounToIds = new HashMap<String, Set<Integer>>();
		   
		   initSynset(synsets);
		   Digraph graph = initHypernyms(hypernyms);
		   
		   DirectedCycle cycle = new DirectedCycle(graph);
		   if (cycle.hasCycle() || !rootedDAG(graph)) throw new IllegalArgumentException();
		   
		   this.sap = new SAP(graph);
	   }
	   
	   private boolean rootedDAG(Digraph g) {
		   int roots = 0;
		   for (int i = 0; i < g.V(); i++) {
			   if (!g.adj(i).iterator().hasNext()) {
				   roots++;
				   if (roots > 1) {
					   return false;
				   }
			   }
		   }
		   return roots == 1;
	   }
	   
	   // returns all WordNet nouns
	   public Iterable<String> nouns() {
		   return this.nounToIds.keySet();
	   }
	   
	   // is the word a WordNet noun?
	   public boolean isNoun(String word) {
		   if (word == null) throw new NullPointerException();
		   return this.nounToIds.containsKey(word);
	   }
	   
	   // distance between nounA and nounB (defined below)
	   public int distance(String nounA, String nounB) {
		   if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
		   return this.sap.length(this.nounToIds.get(nounA), this.nounToIds.get(nounB));
	   }
	   
	   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	   // in a shortest ancestral path (defined below)
	   public String sap(String nounA, String nounB) {
		   if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
		   int i =  this.sap.ancestor(this.nounToIds.get(nounA), this.nounToIds.get(nounB));
		   return this.idToSynset.get(i);
	   }
	   
	   // do unit testing of this class
	   public static void main(String[] args) {
		   WordNet net = new WordNet(args[0], args[1]);
		   while (!StdIn.isEmpty()) {
		        int v = StdIn.readInt();
		        int w = StdIn.readInt();
		        int length   = net.distance(net.idToSynset.get(v), net.idToSynset.get(w));
		        String ancestor = net.sap(net.idToSynset.get(v), net.idToSynset.get(w));
		        StdOut.printf("length = %d, ancestor = %s\n", length, ancestor);
		    }
	   }
	   
	   private void initSynset(String sss) {
		   In in = new In(sss);
		   while (in.hasNextLine()) {
			   String[] getLine = in.readLine().split(",");
			   Integer id = Integer.valueOf(getLine[0]);
			   String synset = getLine[1];
			   idToSynset.put(id, synset);
			   
			   String[] nouns = synset.split(" ");
			   for (int i = 0; i < nouns.length; i++) {
				   Set<Integer> set = this.nounToIds.get(nouns[i]);
				   if (set == null) {
					    set = new HashSet<Integer>();
				   }
				   set.add(id);
				   nounToIds.put(nouns[i], set);
			   }
		   }
	   }
	   
	   private Digraph initHypernyms(String sss) {
		   In in = new In(sss);
		   Digraph graph = new Digraph(this.idToSynset.size());
		   
		   while (in.hasNextLine()) {
			   String[] getLines = in.readLine().split(",");
			   Integer id = Integer.valueOf(getLines[0]);
			   for (int i = 1; i < getLines.length; i++) {
				   graph.addEdge(id, Integer.valueOf(getLines[i]));
			   }
			   
		   }
		   return graph;
	   }
}
