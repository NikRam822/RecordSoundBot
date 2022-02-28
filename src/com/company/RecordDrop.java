package com.company;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.io.*;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

class JavaSoundRecorder {

        DbxClientV2 client;
        AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
        TargetDataLine line;
        DataLine.Info info;
        AudioFormat format;

        public JavaSoundRecorder(DbxClientV2 clientV2) {
            this.client = clientV2;
            format = getAudioFormat();
            info = new DataLine.Info(TargetDataLine.class, format);
        }

        public  void record(int milliseconds) {

            SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy_Hms");//текущее время и дата
            Date now = new Date();//дата
            String name =formatter.format(now);
            String pathToFile = "C:/Users/Nikita/Desktop/"+name+".wav";

             File file = new File(pathToFile);
            start(file);
            finish(file, milliseconds,name);
        }

        AudioFormat getAudioFormat() {
            float sampleRate = 16000;
            int sampleSizeInBits = 8;
            int channels = 2;
            boolean signed = true;
            boolean bigEndian = true;
            AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
            return format;
        }

        public void start(File file) {
            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                        if (!AudioSystem.isLineSupported(info)) {
                            System.out.println("Line not supported");
                            System.exit(0);
                        }
                        line = (TargetDataLine) AudioSystem.getLine(info);
                        line.open(format);
                        line.start();
                        AudioInputStream ais = new AudioInputStream(line);
                        AudioSystem.write(ais, fileType, file);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            thread.start();
        }

        public void finish(File file, int millis,String name) {
            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(millis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    line.stop();
                    line.close();
                    record(millis);

                    //TODO: upload file to Dropbox
                    try {
                    InputStream in = new FileInputStream(file);
                    System.out.println(file);

                        client.files().uploadBuilder("/"+name+".wav").uploadAndFinish(in);//задаем имя
                        System.out.println(file.delete());
                      //  System.out.println(file.delete());
                    } catch (DbxException | IOException e) {
                        e.printStackTrace();
                    }


                    // File file = new File("c:\\logfile20100131.log");


                    //TODO: delete file
                //   System.out.println(file.delete());
                }
            };
            thread.start();
        }
    }
