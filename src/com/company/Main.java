package com.company;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {

        //JavaSoundRecorder ghbdf = new JavaSoundRecorder()

        String ACCESS_TOKEN = "fGlGWMKWsA0AAAAAAAAAAQT8obMYaTCyiTmT9G6_yFocrjbhKxKPDdIA2u7-fsDU";//токен
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();//подключение к drop
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);//создание клюента для отправки файлов

        JavaSoundRecorder newFile = new JavaSoundRecorder(client);

          newFile.record(5000);
          //newFile.;

    }
}

