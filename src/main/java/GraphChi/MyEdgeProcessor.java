package GraphChi;
import edu.cmu.graphchi.preprocessing.EdgeProcessor;

public class MyEdgeProcessor implements EdgeProcessor<Float>{
		@Override
		public Float receiveEdge(int from, int to, String token) {
			return Float.parseFloat(token);
		}
    }