import edu.cmu.graphchi.datablocks.BytesToValueConverter;


public class MyBytesToValueConverter implements BytesToValueConverter<Float>{

	public int sizeOf() {
        return 4;
    }

    public Float getValue(byte[] array) {
        int x = ((array[3]  & 0xff) << 24) + ((array[2] & 0xff) << 16) + ((array[1] & 0xff) << 8) + (array[0] & 0xff);
        return Float.intBitsToFloat(x);
    }

    public void setValue(byte[] array, Float val) {
        int x = Float.floatToIntBits(val);
        array[3] = (byte) ((x >>> 24) & 0xff);
        array[2] = (byte) ((x >>> 16) & 0xff);
        array[1] = (byte) ((x >>> 8) & 0xff);
        array[0] = (byte) ((x >>> 0) & 0xff);
    }

}
