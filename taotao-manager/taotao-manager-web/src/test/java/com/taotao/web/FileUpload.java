package com.taotao.web;

import com.taotao.common.FtpUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileUpload {
    @Test
    public void testFtpClient() throws FileNotFoundException {

        String host = "ikros.top";
        int port = 21;
        String user = "ftpuser";
        String password = "sakura990703";
        String basePath = "/home/ftpuser";
        String filePath = "20190726";
        String fileName = "aa.txt";
        InputStream file = new FileInputStream("d:/axis.log");

        boolean ret = FtpUtil.uploadFile(host, port, user, password, basePath, filePath, fileName, file);
        Assert.assertTrue("上传失败", ret);
        System.out.println(ret);
    }
}
