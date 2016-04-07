package com.yada.wechatbank.util;

/** 
 * Title: �����ͼ����ɳ���Ĵ��� Description: 
 * Ϊ�˼�ǿ���簲ȫ����ֹ�ڿι��߶�ϵͳ�Ĺ�������ͨ�������4���������⣬��Ϊÿһ�β��������������ģ�����ʹ����ٷ��ƽ�����Ĺ��߾�����Ϊf�� 
 * Copyright: Copyright (c) 2004-2010 Company: SDLDAP 
 * 
 * @author LiChanglai 
 * @version 1.0 
 */
import java.util.*;
import java.awt.*;
import java.awt.image.*;

public class RandomChar
{
	/**
	 * ��Χ��������ɫ
	 * 
	 * @param fc
	 *            int ����1
	 * @param bc
	 *            int ����2
	 * @return Color ���ص�color����
	 */
	private String strRandomString; // ��ɵ������
	/**
	 * ȡ����ɵ������
	 * 
	 * @return String
	 */
	public String getRandomString()
	{
		return strRandomString;
	}
	/**
	 * ��������ɵ�����
	 * 
	 * @param randomString
	 *            String
	 */
	public void setRandomString(String randomString)
	{
		this.strRandomString = randomString;
	}
	/**
	 * ȡ��ĳһ��Χ��color
	 * 
	 * @param fc
	 *            int ��Χ����1
	 * @param bc
	 *            int ��Χ����2
	 * @return Color
	 */
	private Color getRandColor(int fc, int bc)
	{
		// ȡ�������ɫ
		Random random = new Random();
		if (fc > 255)
		{
			fc = 255;
		}
		if (bc > 255)
		{
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	/**
	 * ��������������
	 * 
	 * @param nRandomCount
	 *            int ������ܳ���
	 * @return String ���ص������
	 */
	private String getRandomString(int nRandomCount)
	{
		/*
		 * String[] a = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
		 * "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M", "N", "O",
		 * "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c",
		 * "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
		 * "r", "s", "t", "u", "v", "w", "x", "y", "z" };
		 */
		String[] a =
		{
				"a", "b", "c", "d", "e", "f", "g", "h", "g", "k", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
				"w", "x", "y", "z"
		};
		String strRand = "";
		int nRand;
		// ȡ������������
		int LengthOfRandom = a.length;
		// ��������
		Random random = new Random();
		// ѭ��ȡ���ȡ������,�������������Ӧ�Ľ������ۼ�
		for (int i = 0; i < nRandomCount; i++)
		{
			nRand = random.nextInt(LengthOfRandom);
			strRand += a[nRand];
		}
		// �����ۼӺ�Ľ��
		return strRand;
	}
	/**
	 * ���������ͼ��
	 * 
	 * @param nWidth
	 *            int ���ͼ�εĿ��
	 * @param nHeight
	 *            int ���ͼ�εĸ߶�
	 * @param nLengthOfRandom
	 *            int �����ĸ���
	 * @param nDisturbLineCount
	 *            int �����ߵ�����
	 * @return BufferedImage
	 */
	public BufferedImage getRandomImage(int nWidth, int nHeight, int nLengthOfRandom, int nDisturbLineCount)
	{
		// ���ڴ��д���ͼ��
		BufferedImage image = new BufferedImage(nWidth, nHeight, BufferedImage.TYPE_INT_RGB);
		// ��ȡͼ��������
		Graphics g = image.getGraphics();
		String strRand = "";
		nLengthOfRandom = (nLengthOfRandom <= 0) ? 6 : nLengthOfRandom;
		nDisturbLineCount = (nDisturbLineCount <= 0) ? 0 : nDisturbLineCount;
		// ��������
		Random random = new Random();
		// �趨����ɫ
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, nWidth, nHeight);
		// �趨����
		g.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		// ���߿�
		g.setColor(new Color(255, 255, 255));
		g.drawRect(0, 0, nWidth - 1, nHeight - 1);
		// ������nDisturbLineCount������ߣ�ʹͼ���е���֤�벻�ױ��������̽�⵽
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < nDisturbLineCount; i++)
		{
			int x = random.nextInt(nWidth);
			int y = random.nextInt(nHeight);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// ȡ���������֤��(6λ)
		strRand = this.getRandomString(nLengthOfRandom);
		// ����������randomString��ֵ
		this.strRandomString = strRand;
		for (int i = 0; i < nLengthOfRandom; i++)
		{
			// ����֤����ʾ��ͼ����
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			// ���ú����4����ɫ��ͬ����������Ϊ����̫�ӽ�����ֻ��ֱ�����
			g.drawString(strRand.substring(i, i + 1), 13 * i + 5, 14);
		}
		// ͼ����Ч
		g.dispose();
		return image;
	}
}
