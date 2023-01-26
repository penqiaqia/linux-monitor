package pjx.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class Utils {

	public static void main(String[] args) {
		Utils util = new Utils();
		try {
			util.generateCode("我亲爱的老婆，我爱你", "/Users/panjiexian/Desktop", "code");
		} catch (WriterException | IOException e) {
			e.printStackTrace();
			System.out.println("二维码生成失败");
		}
		util.getConn("./src/main/resources/123.db");
	}

	/*
	 * 生成二维码
	 */
	public static void generateCode(String url, String path, String codeName) throws WriterException, IOException {
		final int BLACK = 0xFF000CE0;
		final int WHITE = 0xFFF6FF3F;
		int width = 400;
		int height = 400;
		// 二维码图片格式
		String format = "jpg";
		// 设置编码，防止中文乱码
		Hashtable<EncodeHintType, Object> ht = new Hashtable<EncodeHintType, Object>();
		ht.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 设置二维码参数(编码内容，编码类型，图片宽度，图片高度,格式)
		BitMatrix bitMatrix = null;
		bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, ht);
		// 生成二维码(定义二维码输出服务器路径)
		File outputFile = new File(path);
		if (!outputFile.exists()) {
			// 创建文件夹
			outputFile.mkdir();
		}

		int b_width = bitMatrix.getWidth();
		int b_height = bitMatrix.getHeight();
		// 建立图像缓冲器
		BufferedImage image = new BufferedImage(b_width, b_height, BufferedImage.TYPE_3BYTE_BGR);
		for (int x = 0; x < b_width; x++) {
			for (int y = 0; y < b_height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
			}
		}
		// 生成二维码
		ImageIO.write(image, format, new File(path + "/" + codeName + "." + format));

	}

	/*
	 * 连接sqlite
	 */
	public static Connection getConn(String path) {
		Connection conn = null;
		String url = "jdbc:sqlite:" + path;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;

	}

	public static void closeConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
