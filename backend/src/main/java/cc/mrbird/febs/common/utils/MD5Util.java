package cc.mrbird.febs.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5Util {

	protected MD5Util(){

	}

	private static final String ALGORITH_NAME = "md5";

	private static final int HASH_ITERATIONS = 2;

	public static String encrypt(String password) {
		return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(password), HASH_ITERATIONS).toHex();
	}

	public static String encrypt(String username, String password) {
		//MD5加盐加密
		// algorithmName代表进行加密的算法名称、
		//source代表需要加密的元数据，如密码、
		//salt代表盐，需要加进一起加密的数据、
		//hashIterations代表hash迭代次数。
		return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(username.toLowerCase() + password),
				HASH_ITERATIONS).toHex();  //转成十六进制
	}

	public static void main(String[] args) {
		System.out.println(encrypt("mrbird","1234qwer"));
	}

}
