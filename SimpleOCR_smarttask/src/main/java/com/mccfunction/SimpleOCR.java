package com.mccfunction;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimpleOCR implements ISimpleOCR {
    final String LIBRARY_PATH_ENV_KEY = "LD_LIBRARY_PATH";
    final String LIBRARY_PATH_ENV_VALUE = "/var/task/lib";
    final String TESSDATA_PREFIX_KEY = "TESSDATA_PREFIX";
    final String TESSDATA_PREFIX_VALUE = "/var/task/";
    final String TEMP_FILE_PATH = "/tmp/temp.png";
    @Override
    public SimpleOCROutput process(SimpleOCRInput request) {
        String result = "";
        try {
            temporarilySaveFile(request.getPayload());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ProcessBuilder ps = new ProcessBuilder("/var/task/tesseract", TEMP_FILE_PATH, "stdout","-l " + OCRLang.getLanguage(request.getLanguage()), "-psm 8");
        Map<String, String> envs = ps.environment();
        envs.put(LIBRARY_PATH_ENV_KEY, LIBRARY_PATH_ENV_VALUE);
        envs.put(TESSDATA_PREFIX_KEY, TESSDATA_PREFIX_VALUE);
        ps.redirectErrorStream(false);

        Process pr = null;
        try {
            pr = ps.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;
        try {
            while ((line = in.readLine()) != null) {
                System.out.println("~~" + line);
                result += line;
            }
            pr.waitFor();
            in.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new SimpleOCROutput(result);
    }
    private void temporarilySaveFile(byte [] bytes) throws IOException {
        FileOutputStream stream = new FileOutputStream(TEMP_FILE_PATH);
        try {
            stream.write(bytes);

            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            perms.add(PosixFilePermission.OTHERS_READ);
            perms.add(PosixFilePermission.OTHERS_WRITE);
            perms.add(PosixFilePermission.OTHERS_EXECUTE);
            perms.add(PosixFilePermission.GROUP_READ);
            perms.add(PosixFilePermission.GROUP_WRITE);
            perms.add(PosixFilePermission.GROUP_EXECUTE);

            Files.setPosixFilePermissions(Paths.get(TEMP_FILE_PATH), perms);
        } finally {
            stream.close();
        }
    }
}

