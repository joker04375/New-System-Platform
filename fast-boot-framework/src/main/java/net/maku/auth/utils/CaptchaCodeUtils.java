package net.maku.auth.utils;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;


public class CaptchaCodeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaCodeUtils.class);
    static final char CODE_CHARS[] = "23456789ABCDEFGHJKMNPQRSTUVWXYZ".toCharArray();
    static final int CODE_UPPER = 0x01;
    static final int CODE_NUMBER = 0x02;

    static public class CapchaCodeResult implements Serializable {
        private String code;
        private byte[] data;
        private String contentType;
        private String extension;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }
    }

    static public CapchaCodeResult generateRandomCode(int height, int codeCount, int lineCount) {
        ValueHolder<CapchaCodeResult> result = ValueHolder.nil();
        generateRandomCode(height, codeCount, lineCount, (code, buffImg) -> {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                ImageIO.write(buffImg, "png", os);
                CapchaCodeResult captcha = new CapchaCodeResult();
                captcha.setCode(code);
                captcha.setContentType("image/png");
                captcha.setExtension("png");
                captcha.setData(os.toByteArray());
                IOUtils.closeQuietly(os);
                result.setValue(captcha);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
        return result.get();
    }


    static public void generateRandomCode(int height, int codeCount, int lineCount, BiConsumer<String, BufferedImage> action) {
        StringBuilder sb = new StringBuilder();
        int with = (height - 8) * (codeCount + 1);
        while (codeCount >= 4) {
            SecureRandom r = new SecureRandom();
            int flags = 0;
            while (sb.length() < codeCount) {
                int idx = r.nextInt(CODE_CHARS.length);
                char c = CODE_CHARS[idx];
                if (c >= '0' && c <= '9') {
                    flags |= CODE_NUMBER;
                } else if (c >= 'A' && c <= 'Z') {
                    flags |= CODE_UPPER;
                }
                sb.append(c);
            }
            if (flags == 0x3) {
                String code = sb.toString();
                // 生成图片
                int x = 0, fontHeight = 0, codeY = 0;
                int red = 0, green = 0, blue = 0;
                x = with / (codeCount + 1);
                fontHeight = height - 2;
                codeY = height - 4;
                BufferedImage buffImg = new BufferedImage(with, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = buffImg.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, with, height);
                Font font = new Font("Times New Roman", Font.PLAIN, fontHeight);
                g.setFont(font);
                for (int i = 0; i < lineCount; i++) {
                    int xs = r.nextInt(with);
                    int ys = r.nextInt(height);
                    int xe = xs + r.nextInt(with / 8);
                    int ye = ys + r.nextInt(height / 8);

                    red = r.nextInt(255);
                    green = r.nextInt(255);
                    blue = r.nextInt(255);
                    g.setColor(new Color(red, green, blue));
                    g.drawLine(xs, ys, xe, ye);
                }
                int n = x / 2;
                List<Font> plainFonts = new ArrayList<>();
                plainFonts.add(new Font("Ravie", Font.PLAIN, fontHeight));
                plainFonts.add(new Font("Antique Olive Compact", Font.PLAIN, fontHeight));
                plainFonts.add(new Font("Fixedsys", Font.PLAIN, fontHeight));
                plainFonts.add(new Font("Wide Latin", Font.PLAIN, fontHeight));
                plainFonts.add(new Font("Gill Sans Ultra Bold", Font.PLAIN, fontHeight));
                for (int i = 0; i < code.length(); i++) {
                    String strRand = String.valueOf(code.charAt(i));
                    red = r.nextInt(255);
                    green = r.nextInt(255);
                    blue = r.nextInt(255);
                    Color clr = new Color(red, green, blue);
                    g.setColor(clr);
                    int off = r.nextInt(8);
                    off -= 4;
                    int fontIdx = r.nextInt(plainFonts.size());
                    Font curFont = plainFonts.get(fontIdx);
                    g.setFont(curFont);
//                    g.drawString(strRand, n, codeY + off);
                    g.drawString(strRand, n + 1, codeY + off);
                    g.drawString(strRand, n - 1, codeY + off);
                    g.drawString(strRand, n, codeY + off + 1);
                    g.drawString(strRand, n, codeY + off - 1);

                    g.setColor(Color.white);
                    g.drawString(strRand, n, codeY + off);
                    n += x;
                }
                action.accept(code, buffImg);
                return;
            } else {
                sb.delete(0, sb.length());
            }
        }
    }
}
