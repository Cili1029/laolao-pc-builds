package com.laolao.common.utils;

import com.aliyun.credentials.models.Config;
import com.aliyun.tea.TeaException;
import com.laolao.common.properties.AliyunDirectMailProperties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class AliyunDirectMailUtil {

    @Resource
    private AliyunDirectMailProperties properties;

    private String getAccessKeyId() {
        return properties.getAccessKeyId();
    }

    private String getAccessKeySecret() {
        return properties.getAccessKeySecret();
    }

    public com.aliyun.dm20151123.Client createClient() throws Exception {
        Config authConfig = new Config()
                .setAccessKeyId(getAccessKeyId())
                .setAccessKeySecret(getAccessKeySecret());
        authConfig.setType("access_key");

        com.aliyun.credentials.Client credential = new com.aliyun.credentials.Client(authConfig);

        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config().setCredential(credential);
        config.endpoint = "dm.aliyuncs.com";
        return new com.aliyun.dm20151123.Client(config);
    }

    public Boolean sendEmail(String address, String content) throws Exception {
        com.aliyun.dm20151123.Client client = createClient();
        com.aliyun.dm20151123.models.SingleSendMailRequest singleSendMailRequest = new com.aliyun.dm20151123.models.SingleSendMailRequest()
                .setAccountName("laolao@laolao123.fun")
                .setAddressType(1)
                .setTagName("Email-Code")
                .setReplyToAddress(true)
                .setToAddress(address)
                .setSubject("哈喽!")
                .setHtmlBody(content)
                .setFromAlias("劳劳")
                .setReplyAddress("3317309371@qq.com")
                .setReplyAddressAlias("劳劳")
                .setClickTrace("0")
                .setUnSubscribeLinkType("default")
                .setUnSubscribeFilterLevel("disabled");
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            client.singleSendMailWithOptions(singleSendMailRequest, runtime);
        } catch (TeaException error) {
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return true;
    }
}
