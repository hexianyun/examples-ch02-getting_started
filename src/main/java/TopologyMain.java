import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import bolts.WordCounter;
import bolts.WordNormalizer;
import grouping.ModuleGrouping;
import spouts.WordReader;


public class TopologyMain {
	public static void main(String[] args) throws InterruptedException {

		//Topology definition
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word-reader",new WordReader());
		builder.setBolt("word-normalizer", new WordNormalizer())
//				.shuffleGrouping("word-reader");
				.customGrouping("word-reader", new ModuleGrouping());
		builder.setBolt("word-counter", new WordCounter(),2)
//				.customGrouping("word-normalizer", new ModuleGrouping());
				.fieldsGrouping("word-normalizer", new Fields("word"));


		//Configuration
		Config conf = new Config();
		conf.put("wordsFile", "D:/workspace/storm/git_storm/examples-ch02-getting_started/src/main/resources/words.txt");
		conf.setDebug(false);
		//Topology run
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Getting-Started-Toplogie", conf, builder.createTopology());
		Thread.sleep(1000);
		cluster.shutdown();
	}
}
