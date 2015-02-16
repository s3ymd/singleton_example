import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

class NetUtil {
	// URLと、取得したページデータを対応付けて保存しておく
	private Map<String, String> cache = new HashMap<>();

	public String get(String address) throws IOException {
		// キャッシュを利用できる場合は利用
		if (cache.containsKey(address)) {
			System.out.println("■ネットにアクセスせず、キャッシュを利用します");
			return cache.get(address);
		} else {
			System.out.println("■ネットからデータを受信します");
		}

		// データを受信する処理
		URL url = new URL(address);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				con.getInputStream()))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append(System.lineSeparator());
			}
		}
		con.disconnect();

		// 受信データをキャッシュする処理
		String s = sb.toString();
		cache.put(address, s);
		return s;
	}
}

public class Main {
	public static void main(String[] args) throws IOException {
		step1();
		step2();
	}

	private static void step1() throws IOException {
		System.out.println("■step1");
		NetUtil util = new NetUtil();
		String s = util.get("http://www.3sss.co.jp/");
		int titleIndex = s.indexOf("<title>");
		System.out.println(s.substring(titleIndex, titleIndex + 40));
	}

	private static void step2() throws IOException {
		System.out.println("■step2");
		NetUtil util = new NetUtil();
		String s = util.get("http://www.3sss.co.jp/");
		int titleIndex = s.indexOf("<title>");
		System.out.println(s.substring(titleIndex, titleIndex + 40));
	}

}
