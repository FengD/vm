package hirain.itd.hmi.demo.useless;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PcapParser {

	public static Pcap unpack(InputStream is) throws IOException {
		Pcap pcap = null;
		byte[] buffer_4 = new byte[4];
		byte[] buffer_2 = new byte[2];
		pcap = new Pcap();

		PcapHeader header = new PcapHeader();
		int m = is.read(buffer_4);
		if (m != 4) {
			return null;
		}
		reverseByteArray(buffer_4);
		header.setMagic(byteArrayToInt(buffer_4, 0));
		m = is.read(buffer_2);
		reverseByteArray(buffer_2);
		header.setMagor_version(byteArrayToShort(buffer_2, 0));
		m = is.read(buffer_2);
		reverseByteArray(buffer_2);
		header.setMinor_version(byteArrayToShort(buffer_2, 0));
		m = is.read(buffer_4);
		reverseByteArray(buffer_4);
		header.setTimezone(byteArrayToInt(buffer_4, 0));
		m = is.read(buffer_4);
		reverseByteArray(buffer_4);
		header.setSigflags(byteArrayToInt(buffer_4, 0));
		m = is.read(buffer_4);
		reverseByteArray(buffer_4);
		header.setSnaplen(byteArrayToInt(buffer_4, 0));
		m = is.read(buffer_4);
		reverseByteArray(buffer_4);
		header.setLinktype(byteArrayToInt(buffer_4, 0));

		pcap.setHeader(header);

		List<PcapData> dataList = new ArrayList<PcapData>();
		while (m > 0) {
			PcapData data = new PcapData();
			m = is.read(buffer_4);
			if (m < 0) {
				break;
			}
			reverseByteArray(buffer_4);
			data.setTime_s(byteArrayToInt(buffer_4, 0));
			m = is.read(buffer_4);
			reverseByteArray(buffer_4);
			data.setTime_ms(byteArrayToInt(buffer_4, 0));
			m = is.read(buffer_4);
			reverseByteArray(buffer_4);
			data.setpLength(byteArrayToInt(buffer_4, 0));
			m = is.read(buffer_4);
			reverseByteArray(buffer_4);
			data.setLength(byteArrayToInt(buffer_4, 0));
			byte[] content = new byte[data.getpLength()];
			m = is.read(content);
			data.setContent(content);
			System.out.println(data.getContent().toString());
			dataList.add(data);
		}

		pcap.setData(dataList);
		return pcap;
	}

	private static int byteArrayToInt(byte[] b, int offset) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}

	private static short byteArrayToShort(byte[] b, int offset) {
		short value = 0;
		for (int i = 0; i < 2; i++) {
			int shift = (2 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}

	/**
	 * 反转数组
	 * 
	 * @param arr
	 */
	private static void reverseByteArray(byte[] arr) {
		byte temp;
		int n = arr.length;
		for (int i = 0; i < n / 2; i++) {
			temp = arr[i];
			arr[i] = arr[n - 1 - i];
			arr[n - 1 - i] = temp;
		}
	}

}