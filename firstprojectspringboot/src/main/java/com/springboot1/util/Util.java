package com.springboot1.util;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class Util {
    

    public static String saveFile(MultipartFile multipartFile, String path){
        // Getting original file name
        String originalName = multipartFile.getOriginalFilename();
        // Replace ' ' by '-'
        originalName = originalName.replace(" ", "-");

        // Add a random 8 string length
        String finalName = randomAlphaNumeric(8)+originalName;

        try {
            // Creating the new name of the file to save it in hd
            File File = new File(path + finalName);
            // Saving the file
            multipartFile.transferTo(File);

            return finalName;
        } catch (Exception e) {
            System.out.println("ERROR:"+e.getMessage());
            return null;
        }
    }

    public static String randomAlphaNumeric(int count){
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while(count -- != 0){
            int character = (int) (Math.random() * CHARACTERS.length());
            builder.append(CHARACTERS.charAt(character));
        }
        return builder.toString();
    }
}
