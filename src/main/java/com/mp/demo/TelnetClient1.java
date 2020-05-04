package com.mp.demo;

import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ycn
 */
public class TelnetClient1 {

    private static final Logger LOG = LoggerFactory.getLogger(TelnetClient1.class);

    public String telnetClientStart(String telnetIP, int telnetPort, int ne4110Reboot) {
        String telnetErr = "work";
        Socket socket = new Socket();
        boolean isConnected = false;
        if (isIP(telnetIP)) {
            //标志为1重启4110S
            if (ne4110Reboot == 1) {
                LOG.info("*****************重启4110S*******************************");
                //指明Telnet终端类型，否则会返回来的数据中文会乱码
                TelnetClient telnetClient = new TelnetClient("VT200");
                //socket延迟时间：3000ms
                telnetClient.setDefaultTimeout(3000);
                try {
                    //建立一个连接,默认端口是23
                    LOG.info("IP:{} Port:{}", telnetIP, telnetPort);
                    telnetClient.connect(telnetIP, telnetPort);
                    //读取命令的流
                    InputStream inputStream = telnetClient.getInputStream();
                    StringBuffer sBuffer = new StringBuffer(300);
                    int size;
                    byte[] b = new byte[1024];
                    while (true) {
                        //读取Server返回来的数据，直到读到登陆标识，这个时候认为可以输入用户名
                        size = inputStream.read(b);
                        if (-1 != size) {
                            sBuffer.append(new String(b, 0, size));
                            if (sBuffer.toString().indexOf("your") > 0) {
                                break;
                            }
                        }
                    }
                    //将命令发送到telnet Server
                    PrintStream pStream = new PrintStream(telnetClient.getOutputStream());
                    pStream.println("s");
                    pStream.flush();
                    LOG.info(sBuffer.toString());
                    pStream.println("y");
                    pStream.flush();
                    while (true) {
                        //读取Server返回来的数据，直到读到登陆标识，这个时候认为可以输入用户名
                        size = inputStream.read(b);
                        if (-1 != size) {
                            sBuffer.append(new String(b, 0, size));
                            if (sBuffer.toString().indexOf(":y") > 0) {
                                break;
                            }
                        }
                    }
                    LOG.info("设备应答:{}", size);
                    if (null != pStream) {
                        pStream.close();
                    }
                    //telnetErr="reboot";
                    telnetErr = sBuffer.toString();
                    LOG.info("读取设备的字符:{}", telnetErr);
                    telnetClient.disconnect();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    telnetErr = e.getMessage();
                    LOG.warn("不能从设备中读到文字或网断开:{}", telnetErr);
                }
            } else {
                LOG.info("对IP:{}，Port:{}尝试连接", telnetIP, telnetPort);
                try {
                    // 建立连接
                    socket.connect(new InetSocketAddress(telnetIP, telnetPort), 3000);
                    // 通过现有方法查看连通状态
                    isConnected = socket.isConnected();
                    LOG.info("连通{}", isConnected);
                    // true为连通
                    telnetErr = "连通";
                } catch (IOException e) {
                    // 当连不通时，直接抛异常，异常捕获即可
                    telnetErr = e.getMessage();
                    LOG.info("超时:{}", telnetErr);
                } finally {
                    try {
                        socket.close();   // 关闭连接
                    } catch (IOException e) {
                        telnetErr = e.getMessage();
                        LOG.warn("无法关闭:{}", telnetErr);
                    }
                }

            }
        }else{
            telnetErr = "ip错";
        }

        return telnetErr;
    }

    private boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }
}
