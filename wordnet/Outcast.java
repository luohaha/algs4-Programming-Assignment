import java.util.HashMap;
import java.util.Map;


public class Outcast {
	private WordNet net;
	public Outcast(WordNet wordnet) {
		if (wordnet == null) throw new NullPointerException();
		this.net = wordnet;
	}
	
	public String outcast(String[] nouns) {
		if (nouns.length < 2) throw new IllegalArgumentException();
		for (String s : nouns) {
			if (!this.net.isNoun(s)) {
				throw new IllegalArgumentException();
			}
		}
		Map<String, Integer> d = new HashMap<String, Integer>();
		for (String s : nouns) {
			int sum = 0;
			for (String tmp : nouns) {
				sum += this.net.distance(s, tmp);
			}
			d.put(s, sum);
		}
		int max = Integer.MIN_VALUE;
		String maxs = null;
		for (String s : nouns) {
			if (d.get(s) > max) {
				max = d.get(s);
				maxs = s;
			}
		}
		return maxs;
	}
	
	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
	}
}
