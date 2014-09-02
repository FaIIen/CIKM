import edu.cmu.graphchi.preprocessing.VertexProcessor;

public class MyVertexProcessor implements VertexProcessor<Float>{
		@Override
		public Float receiveVertexValue(int vertexId, String token) {
			return Float.parseFloat(token);
		}
    }